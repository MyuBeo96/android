package com.fss.mobiletrading.function.orderlist;

import android.app.Dialog;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tcscuat.mobiletrading.MainActivity_Mobile;
import com.tcscuat.mobiletrading.design.CustomPassLayout;
import com.fss.mobiletrading.adapter.Solenh_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.AppConfig;
import com.fss.mobiletrading.function.ChooseAfacctno;
import com.fss.mobiletrading.function.notify.NotificationService;
import com.fss.mobiletrading.function.placeorder.OrderSetParams;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.Order;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.SolenhItem;
import com.fss.mobiletrading.object.StockItem;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.DeviceProperties;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.MyActionBar.Action;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.design.SearchStockUI;
import com.tcscuat.mobiletrading.design.SearchTextUI;
import com.tcscuat.mobiletrading.design.SearchTextUI.OnHideListener;
import com.tcscuat.mobiletrading.design.SelectorImageView;
import com.tcscuat.mobiletrading.design.TabSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NormalOrderList extends AbstractFragment {
    private static final int UPDATE_INTERVAL = 3000;
    static final String AMENDORDER = "SuccessService#AMENDORDER";
    static final String CANCELORDER = "SuccessService#CANCELORDER";
    static final String DOCANCELORDER = "DoCancelOrderService#DOCANCELORDER";
    static final String MORDER = "MorderService#MORDER";
    static final String MORDERREALTIME = "MorderService#MORDERREALTIME";
    static final String ORDERDETAILS = "OrderDetailsService#ORDERDETAILS";
    final String GENOTPSMS = "SuccessService#GENOTPSMS";
    public static final String GETUNREAD = "GetUnReadService#GETUNREAD";
    int unread = 0;
    TabSelector tabSelector;
    ListView lv_Solenh;
    SearchStockUI search_Symbol;
    /**
     * only tablet
     */
    SearchTextUI searchAllText;
    /**
     * data của adapter sổ lệnh bắt buộc khi update dữ liệu mới phải đi qua hàm
     * filter
     */
    List<SolenhItem> listSolenhItem;
    Solenh_Adapter adapterSolenh;
    Action action_CancelFilter;
    Action action_CancelAllOrder;

    int currentTextView;
    int SelectedItemPosition;
    SolenhItem longClickItem = null;
    SolenhItem amendOrderItem = null;
    boolean isFiltering = false;

    String statusOrder1 = "All"; // status 1
    String statusOrder2 = "All"; // status 2
    String filterSymbol = StringConst.EMPTY; // filter by symbol
    String filterText = StringConst.EMPTY; // filter by text
    Dialog dialog_resultcancelallorderTab;
    Dialog dialog_resultcancelallorder;
    TextView tv_resultcancelallorder_OK;
    ListView lv_resultcancelallorder;
    SelectorImageView selectCancelAll;
    TextView tv_cancelAll;

    List<SolenhItem> list_resultcancelallorder;
    ResultCancelAllOrderAdapter adapter_resultcancelallorder;

    Point p;
    Timer timer;
    SolenhItem cancelItem;
    private Runnable updateMOrderRunable;
    private static boolean isUpdating;
    //Custom dialog
    protected CustomPassLayout edt_dialog_TradingPw;
    protected CustomPassLayout edt_dialog_OTPCode;
    protected TextView tv_XacNhan;
    protected TextView tv_Huy;
    protected LinearLayout linearLayout_input;
    Dialog dialog;
    protected ImageButton checkboxTradingpass;
    protected ImageButton checkboxOTPCode;
    protected EditText etOTPCode;

    boolean isOTP= StaticObjectManager.loginInfo.IsOTPOrder == "true";
    boolean saveOTP =false;
    long disableOTPTime= 01;


    public static NormalOrderList newInstance(MainActivity mActivity) {

        NormalOrderList self = new NormalOrderList();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.OrderList);
        return self;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle bundle) {
        View view = inflater
                .inflate(R.layout.normalorderlist, viewGroup, false);
        initView(view);
        initData();
        initListener();
        Common.setupUI(view, getActivity());
        return view;
    }

    private void initView(View view) {
        lv_Solenh = ((ListView) view.findViewById(R.id.listview_SoLenhThuong));
        tabSelector = (TabSelector) view.findViewById(R.id.tab_solenhthuong);
        search_Symbol = (SearchStockUI) view
                .findViewById(R.id.searchtext_solenhthuong_symbol);
        searchAllText = (SearchTextUI) view
                .findViewById(R.id.searchtext_solenhthuong_all);
        selectCancelAll = (SelectorImageView) view
                .findViewById(R.id.img_nororderlist_item_iscancel);
        tv_cancelAll = (TextView) view
                .findViewById(R.id.text_normalorderlist_cancelAll);
        if (searchAllText != null) {
            searchAllText.setVisibleButton(false);
            searchAllText.setVisibleClearField(true);
            searchAllText.setTextColor(getColorResource(R.color.white_text));
        }
        if (dialog_resultcancelallorder == null) {
            dialog_resultcancelallorder = new Dialog(mainActivity);
            dialog_resultcancelallorder.setCancelable(false);
            dialog_resultcancelallorder
                    .requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog_resultcancelallorder
                    .setContentView(R.layout.resultcancelallorder_layout);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog_resultcancelallorder.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog_resultcancelallorder.getWindow().setAttributes(lp);
            dialog_resultcancelallorder.getWindow().setBackgroundDrawable(
                    new ColorDrawable(0));

            tv_resultcancelallorder_OK = (TextView) dialog_resultcancelallorder
                    .findViewById(R.id.text_resultcancelallorder_positive);
            lv_resultcancelallorder = (ListView) dialog_resultcancelallorder
                    .findViewById(R.id.listview_resultcancelallorder);
            tv_resultcancelallorder_OK
                    .setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog_resultcancelallorder.dismiss();
                            dialog.dismiss();
                            if(DeviceProperties.isTablet){
                                    selectCancelAll.setActivated(false);
                                    adapterSolenh
                                            .selectedCancelAllItem(false);
                                }
                        }
                    });
        }
        if (DeviceProperties.isTablet && StaticObjectManager.loginInfo.IsBroker) {
            tv_cancelAll.setEnabled(false);
            tv_cancelAll
                    .setTextColor(getColorResource(R.color.header_text_color));
            selectCancelAll.setVisibility(View.GONE);
        }

    }
    protected  void GenOTPSMS(){
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(getStringResource(R.string.controller_GenOTPSMS));
        }
        {
            list_key.add("afacctno");
            list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
        }
        {
            list_key.add("otptype");
            list_value.add(StaticObjectManager.otpType);
        }

        StaticObjectManager.connectServer.callHttpPostService(GENOTPSMS,
                this, list_key, list_value);
        CallUnRead(this);
    }
    public void CallUnRead(INotifier notifier) {
        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        NotificationService.CallUnRead(StaticObjectManager.deviceToken, android_id,
                StaticObjectManager.loginInfo.UserName, notifier, GETUNREAD);
    }
    //input trading password cancelorder
    private void inputTradingPw(boolean isShow) {
        dialog = new Dialog(mainActivity, R.style.style_dialog);
        dialog.setContentView(R.layout.input_tradingpw_dialog);
        linearLayout_input = (LinearLayout) dialog.findViewById(R.id.linearLayout_input);
        edt_dialog_TradingPw = (CustomPassLayout) dialog.findViewById(R.id.edt_dialog_tradingcode);
        edt_dialog_OTPCode = (CustomPassLayout) dialog.findViewById(R.id.edt_dialog_otpcode);
        checkboxTradingpass= edt_dialog_TradingPw.getcheckbox();
        etOTPCode = (EditText)edt_dialog_OTPCode.getEditContent();
        checkboxOTPCode = edt_dialog_OTPCode.getcheckbox();
        tv_XacNhan = (TextView) dialog.findViewById(R.id.text_dialog_possitive);
        tv_Huy = (TextView) dialog.findViewById(R.id.text_dialog_negative);

        checkboxTradingpass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkboxTradingpass.setSelected(!checkboxTradingpass.isSelected());
            }
        });
        checkboxOTPCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkboxOTPCode.setSelected(!checkboxOTPCode.isSelected());
            }
        });
        edt_dialog_OTPCode.getbtn().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((SystemClock.elapsedRealtime() - StaticObjectManager.mLastGenOTPClickTime) < disableOTPTime) {
                    return;
                }
                StaticObjectManager.mLastGenOTPClickTime=SystemClock.elapsedRealtime();
                GenOTPSMS();
            }
        });
        customDisplay();
        tv_XacNhan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });
        tv_Huy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticObjectManager.mLastGenOTPClickTime = 01;
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    // tv_cancelAll Tablet
    private void inputTradingPwAllcancel(boolean isShow){
        dialog = new Dialog(mainActivity, R.style.style_dialog);
        dialog.setContentView(R.layout.input_tradingpw_dialog);
        edt_dialog_TradingPw = (CustomPassLayout) dialog.findViewById(R.id.edt_dialog_tradingcode);
        checkboxTradingpass= edt_dialog_TradingPw.getcheckbox();
        etOTPCode = (EditText)edt_dialog_OTPCode.getEditContent();
        tv_XacNhan = (TextView) dialog.findViewById(R.id.text_dialog_possitive);
        tv_Huy = (TextView) dialog.findViewById(R.id.text_dialog_negative);
        final String orderIds = adapterSolenh
                .getSelectedCancelItem();
        tv_XacNhan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CallDoCancelOrder(orderIds);
//                selectCancelAll.setActivated(false);
//                adapterSolenh
//                        .selectedCancelAllItem(false);

            }
        });
        checkboxTradingpass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkboxTradingpass.setSelected(!checkboxTradingpass.isSelected());
            }
        });
        checkboxOTPCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkboxOTPCode.setSelected(!checkboxOTPCode.isSelected());
            }
        });
        edt_dialog_OTPCode.getbtn().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((SystemClock.elapsedRealtime() - StaticObjectManager.mLastGenOTPClickTime) < disableOTPTime) {
                    return;
                }
                StaticObjectManager.mLastGenOTPClickTime=SystemClock.elapsedRealtime();
                GenOTPSMS();
            }
        });
        customDisplay();
        tv_Huy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticObjectManager.mLastGenOTPClickTime = 01;
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }
    //Cancel order actionBar
    private void inputAllCancelActionBar(boolean isShow){
        dialog = new Dialog(mainActivity, R.style.style_dialog);
        dialog.setContentView(R.layout.input_tradingpw_dialog);
        edt_dialog_TradingPw = (CustomPassLayout) dialog.findViewById(R.id.edt_dialog_tradingcode);
        checkboxTradingpass = edt_dialog_TradingPw.getcheckbox();
        tv_XacNhan = (TextView) dialog.findViewById(R.id.text_dialog_possitive);
        tv_Huy = (TextView) dialog.findViewById(R.id.text_dialog_negative);
        tv_XacNhan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CallDoCancelOrder();
            }
        });
        checkboxTradingpass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkboxTradingpass.setSelected(!checkboxTradingpass.isSelected());
            }
        });
        checkboxOTPCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkboxOTPCode.setSelected(!checkboxOTPCode.isSelected());
            }
        });
        edt_dialog_OTPCode.getbtn().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((SystemClock.elapsedRealtime() - StaticObjectManager.mLastGenOTPClickTime) < disableOTPTime) {
                    return;
                }
                StaticObjectManager.mLastGenOTPClickTime=SystemClock.elapsedRealtime();
                GenOTPSMS();
            }
        });
        customDisplay();
        tv_Huy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticObjectManager.mLastGenOTPClickTime = 01;
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void initData() {

        if (list_resultcancelallorder == null) {
            list_resultcancelallorder = new ArrayList<SolenhItem>();
        }
        if (listSolenhItem == null) {
            listSolenhItem = new ArrayList<SolenhItem>();
        }

        if (adapter_resultcancelallorder == null) {
            adapter_resultcancelallorder = new ResultCancelAllOrderAdapter(
                    mainActivity, android.R.layout.simple_list_item_1,
                    list_resultcancelallorder);

        }

        if (adapterSolenh == null) {
            adapterSolenh = new Solenh_Adapter(mainActivity,
                    android.R.layout.simple_list_item_1, listSolenhItem);
            if (DeviceProperties.isTablet) {
                adapterSolenh.setmAmendClickAction(new SimpleAction() {

                    @Override
                    public void performAction(Object obj) {
                        if( StaticObjectManager.loginInfo.IsDigital.equals("Y"))
                        {
                            showDialogMessage(getStringResource(R.string.thong_bao),
                                    getStringResource(R.string.CheckPolicy));
                            return;

                        }
                        if (obj instanceof SolenhItem) {
                            if (((SolenhItem) obj).isModifiable
                                    .equals(StringConst.TRUE)) {
                                amendOrderItem = (SolenhItem) obj;
                                changeAccountAndAmendOrder(amendOrderItem);
                            } else {
                                showDialogMessage(R.string.thong_bao,
                                        R.string.solenh_lenhkhongduocphepsua);
                            }
                        }
                    }
                });
            }
            adapterSolenh.setItemClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if( StaticObjectManager.loginInfo.IsDigital.equals("Y"))
                    {
                        showDialogMessage(getStringResource(R.string.thong_bao),
                                getStringResource(R.string.CheckPolicy));
                        return;

                    }
                    if (obj != null && obj instanceof SolenhItem) {
                        mainActivity.sendArgumentToFragment(
                                OrderDetail.class.getName(), obj);
                        mainActivity.navigateFragment(OrderDetail.class
                                .getName());
                    }
                }
            });

            adapterSolenh.setmBuyClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if( StaticObjectManager.loginInfo.IsDigital.equals("Y"))
                    {
                        showDialogMessage(getStringResource(R.string.thong_bao),
                                getStringResource(R.string.CheckPolicy));
                        return;

                    }
                    if (obj != null && obj instanceof SolenhItem) {
                        SolenhItem item = ((SolenhItem) obj);
                        mainActivity.setOrderToPlaceOrder(new OrderSetParams(
                                item.CustodyCd, item.AfAcctno, item.Symbol,
                                Order.SIDE_NB, StringConst.EMPTY, null));
                    }

                }
            });
            adapterSolenh.setmSellClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if( StaticObjectManager.loginInfo.IsDigital.equals("Y"))
                    {
                        showDialogMessage(getStringResource(R.string.thong_bao),
                                getStringResource(R.string.CheckPolicy));
                        return;

                    }
                    if (obj != null && obj instanceof SolenhItem) {
                        SolenhItem item = ((SolenhItem) obj);
                        mainActivity.setOrderToPlaceOrder(new OrderSetParams(
                                item.CustodyCd, item.AfAcctno, item.Symbol,
                                Order.SIDE_NS, StringConst.EMPTY, null));
                    }
                }
            });
            adapterSolenh.setmCancelClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if( StaticObjectManager.loginInfo.IsDigital.equals("Y"))
                    {
                        showDialogMessage(getStringResource(R.string.thong_bao),
                                getStringResource(R.string.CheckPolicy));
                        return;

                    }
                    if (obj instanceof SolenhItem
                            && ((SolenhItem) obj).isCancellable.equals("true")) {
                        cancelItem = (SolenhItem) obj;
                        adapterSolenh.selectedCancelAllItem(false);
                        inputTradingPw(true);

                    } else {
                        showDialogMessage(R.string.thong_bao,
                                R.string.solenh_lenhkhongduocphephuy);
                    }
                }
            });
            if (StaticObjectManager.loginInfo.IsBroker) {

                adapterSolenh.setmItemLongClickAction(new SimpleAction() {
                    @Override
                    public void performAction(Object obj) {
                        if (obj instanceof SolenhItem) {
                            longClickItem = (SolenhItem) obj;
                            lv_Solenh.performLongClick();
                        }
                    }
                });
            }
        }
        lv_resultcancelallorder.setAdapter(adapter_resultcancelallorder);
        lv_Solenh.setAdapter(adapterSolenh);
        lv_Solenh.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        // registerForContextMenu(lv_Solenh);
    }

    private void initListener() {
        if (DeviceProperties.isTablet) {
            selectCancelAll.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if( StaticObjectManager.loginInfo.IsDigital.equals("Y"))
                    {
                        showDialogMessage(getStringResource(R.string.thong_bao),
                                getStringResource(R.string.CheckPolicy));
                        return;

                    }
                    selectCancelAll.setActivated(!selectCancelAll.isActivated());
                    adapterSolenh.selectedCancelAllItem(selectCancelAll
                            .isActivated());
                }
            });

            tv_cancelAll.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if( StaticObjectManager.loginInfo.IsDigital.equals("Y"))
                    {
                        showDialogMessage(getStringResource(R.string.thong_bao),
                                getStringResource(R.string.CheckPolicy));
                        return;

                    }
                    final String orderIds = adapterSolenh
                            .getSelectedCancelItem();
                    if (orderIds.length() == 0) {
                        showDialogMessage(R.string.thong_bao,
                                R.string.KhongCoLenhDeHuy);
                    } else {
                        inputTradingPwAllcancel(true);
                    }
                }
            });

            searchAllText.getEditText().addTextChangedListener(
                    new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {

                        }

                        @Override
                        public void beforeTextChanged(CharSequence s,
                                                      int start, int count, int after) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            filterText = s.toString().toLowerCase();
                            filterOrder();
                        }
                    });
        } else {
            search_Symbol.setOnHideListener(new OnHideListener() {

                @Override
                public void onhide() {
                    filterSymbol = StringConst.EMPTY;
                    filterOrder();
                }
            });

            search_Symbol.getEditText().addTextChangedListener(
                    new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {

                        }

                        @Override
                        public void beforeTextChanged(CharSequence s,
                                                      int start, int count, int after) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            filterSymbol = s.toString();
                            filterOrder();
                        }
                    });
        }

        tabSelector.setOnTabSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        statusOrder1 = "All";
                        statusOrder2 = "All";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        break;
                    case 1:
                        statusOrder1 = "8";
                        statusOrder2 = "2";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        // CallMOrder(MORDER);
                        break;
                    case 2:
                        statusOrder1 = "4";
                        statusOrder2 = "14";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        // CallMOrder(MORDER);
                        break;
                    case 3:
                        statusOrder1 = "3";
                        statusOrder2 = "3";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        // CallMOrder(MORDER);
                        break;
                    case 4:
                        statusOrder1 = "12";
                        statusOrder2 = "12";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        // CallMOrder(MORDER);
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onPause() {
        super.onPause();
        stopUpdateWatchList();
        if (DeviceProperties.isTablet) {
            searchAllText.getEditText().getText().clear();
        }
        CancelTimer();
    }
    private void customDisplay(){
        if(isOTP){
            if(StaticObjectManager.saveOTP) {
                edt_dialog_OTPCode.setText(StaticObjectManager.strOTP);
                checkboxOTPCode.setSelected(StaticObjectManager.saveOTP);
                edt_dialog_OTPCode.setVisibility(View.GONE);
                saveOTP= StaticObjectManager.saveOTP;
            }
            else{
                edt_dialog_OTPCode.setText(StringConst.EMPTY);
                edt_dialog_OTPCode.setVisibility(View.VISIBLE);
            }
        }
        else {
            if (StaticObjectManager.saveTradingPass) {
                edt_dialog_TradingPw.setText(StaticObjectManager.tradingPass);
                checkboxTradingpass.setSelected(StaticObjectManager.saveTradingPass);
                edt_dialog_TradingPw.setVisibility(View.GONE);
            } else {
                edt_dialog_TradingPw.setText(StringConst.EMPTY);
                edt_dialog_TradingPw.setVisibility(View.VISIBLE);
            }
        }
    }
    public void onResume() {
        super.onResume();
        tabSelector.setItemSelected(0);
        filterSymbol = StringConst.EMPTY;
        try {
            disableOTPTime= Long.parseLong(StaticObjectManager.loginInfo.DisableOTPTime)*1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        startUpdateWatchList();
        AsynchTaskTimer();
    }

    private void startUpdateWatchList() {
        if (!isUpdating) {
            isUpdating = true;
            CallMOrder(MORDERREALTIME);
        }
    }

    private void stopUpdateWatchList() {
        isUpdating = false;
    }


    @Override
    public void onShowed() {
        super.onShowed();
        AsynchTaskTimer();
        startUpdateWatchList();
    }

    @Override
    public void onHide() {
        super.onHide();
        CancelTimer();
        stopUpdateWatchList();
    }

    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoActionMenu();
        if (action_CancelAllOrder == null) {
            action_CancelAllOrder = new Action() {

                @Override
                public void performAction(View view) {

                    if (listSolenhItem.size() == 0) {
                        showDialogMessage(R.string.thong_bao,
                                R.string.KhongCoLenhDeHuy);
                    } else {
                        inputAllCancelActionBar(true);
                    }
                }

                @Override
                public String getText() {
                    return mainActivity.getStringResource(R.string.HuyHet);
                }

                @Override
                public int getDrawable() {
                    return 0;
                }
            };
        }
        if (action_CancelFilter == null) {
            action_CancelFilter = new Action() {

                @Override
                public void performAction(View view) {
                    if (search_Symbol.isVisible()) {
                        search_Symbol.hide();
                    } else {
                        search_Symbol.show();
                    }
                }

                @Override
                public int getDrawable() {
                    return R.drawable.ic_search32;
                }

                @Override
                public String getText() {
                    return null;
                }
            };
        }
        if (StaticObjectManager.loginInfo.IsBroker) {
            mainActivity.addAction(action_CancelFilter);
        } else {
            mainActivity.addAction(action_CancelAllOrder);
        }
    }

    private void cancelOrder() {
        if (cancelItem != null) {
            CallCancelOrder(cancelItem);
        }
    }

    private void CallCancelOrder(SolenhItem item) {
        if(isOTP) {
            if (edt_dialog_OTPCode.getText().length() == 0) {
                showDialogMessage(
                        getResources().getString(
                                R.string.thong_bao),
                        getResources().getString(
                                R.string.requireOTP));
                edt_dialog_OTPCode.requestFocus();
                return;
            }

        }
        else {
            if (edt_dialog_TradingPw.getText().length() == 0) {
                showDialogMessage(getStringResource(R.string.thong_bao), getStringResource(R.string.NhapPin));
                edt_dialog_TradingPw.requestFocus();
                return;
            }
        }
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(getStringResource(R.string.controller_CancelOrder));
        }
        {
            list_key.add("OrderId");
            list_value.add(item.OrderId);
        }
        {
            list_key.add("CustodyCd");
            list_value.add(item.CustodyCd);
        }
        {
            list_key.add("AfAcctno");
            list_value.add(item.AfAcctno);
        }
        {
            list_key.add("Symbol");
            list_value.add(item.Symbol);
        }
        {
            list_key.add("Side");
            list_value.add(item.Side);
        }
        {
            list_key.add("Qtty");
            list_value.add(item.Qtty);
        }
        {
            list_key.add("PriceType");
            list_value.add(item.PriceType);
        }
        {
            list_key.add("Price");
            list_value.add(item.Price);
        }
        {
            list_key.add("TradingPassword");
            list_value.add(isOTP ? edt_dialog_OTPCode.getText().toString() : edt_dialog_TradingPw.getText().toString());
        }
        {
            list_key.add("saveotp");
            list_value.add(saveOTP?"Y":"N");
        }
        StaticObjectManager.connectServer.callHttpPostService(CANCELORDER,
                this, list_key, list_value);


    }

    private void CallDoCancelOrder() {
        if (edt_dialog_TradingPw.getText().length() != 0) {
            StringBuilder builder = new StringBuilder();
            for (SolenhItem item : listSolenhItem) {
                builder.append(item.OrderId + ",");
            }
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value
                        .add(getStringResource(R.string.controller_doOrderCancel));
            }
            {
                list_key.add("pv_oderIDs");
                list_value.add(builder.toString());
            }
            {
                list_key.add("pv_Pin");
                list_value.add(edt_dialog_TradingPw.getText().toString());
            }
            StaticObjectManager.connectServer.callHttpPostService(DOCANCELORDER,
                    this, list_key, list_value);
        } else {
            showDialogMessage(getStringResource(R.string.thong_bao), getStringResource(R.string.NhapPin));
            edt_dialog_TradingPw.requestFocus();
        }
    }

    private void CallDoCancelOrder(String orderIds) {
        if (edt_dialog_TradingPw.getText().length() != 0) {
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value
                        .add(getStringResource(R.string.controller_doOrderCancel));
            }
            {
                list_key.add("pv_oderIDs");
                list_value.add(orderIds);
            }
            {
                list_key.add("pv_Pin");
                list_value.add(edt_dialog_TradingPw.getText().toString());
            }
            StaticObjectManager.connectServer.callHttpPostService(DOCANCELORDER,
                    this, list_key, list_value);
        } else {
            showDialogMessage(getStringResource(R.string.thong_bao), getStringResource(R.string.NhapPin));
            edt_dialog_TradingPw.requestFocus();
        }
    }

    private void CallMOrder(String key) {
        try {
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value.add(getStringResource(R.string.controller_MOrder));
            }
            {
                list_key.add("pv_custodycd");
                list_value.add(StaticObjectManager.acctnoItem.CUSTODYCD);
            }
            StaticObjectManager.connectServer.callHttpPostService(key, this,
                    list_key, list_value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AsynchTaskTimer() {
        if (!AppConfig.getInstance().enableSSO) {
            final Handler handler = new Handler();

            TimerTask timertask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            try {
                                CallMOrder(MORDERREALTIME);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };
            timer = new Timer();
            timer.schedule(timertask, 0, UPDATE_INTERVAL);
        }
    }

    private void CancelTimer() {
        if (!AppConfig.getInstance().enableSSO) {
            timer.cancel();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        StaticObjectManager.mLastGenOTPClickTime = 01;
    }

    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
    }

    @Override
    protected void process(String key, ResultObj rObj) {
        switch (key) {
            case GETUNREAD:
                unread = (int) rObj.obj;
                mainActivity.showUnReadNotify(unread);
                break;
            case MORDER:
                if (rObj.obj != null) {
                    listSolenhItem.clear();
                    listSolenhItem.addAll((List<SolenhItem>) rObj.obj);
                    filterOrder();
                }
                break;
            case GENOTPSMS:
                String[] arrayString = rObj.EM.split(";");
                showDialogMessage(getStringResource(R.string.thong_bao), arrayString[0]);
                if (rObj.EC == 0 && StaticObjectManager.loginInfo.IsFillOTP.equals("Y"))
                {
                    etOTPCode.setText(arrayString[1]);
                }
                break;
            case MORDERREALTIME:
                if (rObj.obj != null) {
                    listSolenhItem.clear();
                    listSolenhItem.addAll((List<SolenhItem>) rObj.obj);
                    filterOrder();
                }
                break;
            case CANCELORDER:
//               showDialogMessage(getStringResource(R.string.thong_bao),
//                        getStringResource(R.string.Giaodichthanhcong));
                if(isOTP){
                    StaticObjectManager.saveOTP= checkboxOTPCode.isSelected();
                    if(StaticObjectManager.saveOTP)
                        StaticObjectManager.strOTP = edt_dialog_OTPCode.getText().toString();
                    else
                        StaticObjectManager.strOTP = StringConst.EMPTY;
                }
                else {
                    StaticObjectManager.saveTradingPass = checkboxTradingpass.isSelected();
                    if (StaticObjectManager.saveTradingPass)
                        StaticObjectManager.tradingPass = edt_dialog_TradingPw.getText().toString();
                    else
                        StaticObjectManager.tradingPass = StringConst.EMPTY;
                }
                showDialogMessage(getStringResource(R.string.thong_bao),
                        getStringResource(R.string.Giaodichthanhcong),
                        new SimpleAction() {

                            @Override
                            public void performAction(Object obj) {
                                dialog.dismiss();
                            }

                        });

                break;
            case AMENDORDER:
                showDialogMessage(getStringResource(R.string.thong_bao),
                        getStringResource(R.string.Giaodichthanhcong));

                break;
            case DOCANCELORDER:
                list_resultcancelallorder.clear();
                list_resultcancelallorder.addAll((List<SolenhItem>) rObj.obj);
                adapter_resultcancelallorder.notifyDataSetChanged();
                dialog_resultcancelallorder.show();
                break;
            case ChooseAfacctno.CHANGE_ACCTNO:
                ChooseAfacctno chooseAfacctno = (ChooseAfacctno) mainActivity.mapFragment
                        .get(ChooseAfacctno.class.getName());
                chooseAfacctno.changeAfacctno(amendOrderItem.AfAcctno);
                amendOrder(amendOrderItem);
                break;
            case ChooseAfacctno.FINDCUSTODYCD:
                if (rObj.obj != null) {
                    List<AcctnoItem> list = ((List<AcctnoItem>) rObj.obj);
                    chooseAfacctno = (ChooseAfacctno) mainActivity.mapFragment
                            .get(ChooseAfacctno.class.getName());
                    if (chooseAfacctno != null) {
                        chooseAfacctno.changeCustodyCd(list);
                        ChooseAfacctno.CallChooseAcctno(amendOrderItem.AfAcctno,
                                this);
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void isReceivedResponse(String key) {
        super.isReceivedResponse(key);
        switch (key) {
            case MORDERREALTIME:
                break;
            case MORDER:
                break;
            case CANCELORDER:
                break;
            case AMENDORDER:
                break;
            case DOCANCELORDER:
                break;
            default:
                break;
        }
    }

    @Override
    protected void processNull(String key) {

        super.processNull(key);
        CancelTimer();
    }

    static String STATUS_ALL = "All";
    int filterDataHashcode;

    private void filterOrder() {
        FilterData filterData = new FilterData();
        filterData.list = listSolenhItem;
        filterData.adapterSolenh = adapterSolenh;
        filterData.statusOrder1 = statusOrder1;
        filterData.statusOrder2 = statusOrder2;
        filterData.filterText = filterText;
        filterData.hashCode = filterData.hashCode();
        filterDataHashcode = filterData.hashCode;
        filterData.execute(StringConst.EMPTY);
    }

    class FilterData extends AsyncTask<String, String, List<Integer>> {
        int hashCode;
        String statusOrder1;
        String statusOrder2;
        String filterText;
        Solenh_Adapter adapterSolenh;
        List<SolenhItem> list;

        @Override
        protected List<Integer> doInBackground(String... params) {
            List<Integer> filterItems = new ArrayList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                SolenhItem item = list.get(i);
                if (statusOrder1.equals(STATUS_ALL)) {

                } else {
                    if (!(item.statusValue.equals(statusOrder1))
                            && !(item.statusValue.equals(statusOrder2))) {
                        continue;
                    }
                }
                if (filterText.length() > 0) {
                    String latin = Common.convertUTF8ToLatin(filterText);
                    if (latin != null
                            && !(list.get(i).toString().contains(latin))) {
                        continue;
                    }
                }
                filterItems.add(i);
            }
            return filterItems;
        }

        @Override
        protected void onPostExecute(List<Integer> result) {
            super.onPostExecute(result);
            if (hashCode == filterDataHashcode) {
                adapterSolenh.setFilterItemPosition(result);
                adapterSolenh.notifyDataSetChanged();
            }
        }
    }

    private void changeAccountAndAmendOrder(SolenhItem item) {
        if (StaticObjectManager.loginInfo.IsBroker
                && item.CustodyCd != null
                && !StaticObjectManager.acctnoItem.CUSTODYCD
                .equals(item.CustodyCd)) {
            ChooseAfacctno.CallFindCustodyCd(item.CustodyCd, this);
        } else {
            if (item.AfAcctno != null
                    && !StaticObjectManager.acctnoItem.ACCTNO
                    .equals(item.AfAcctno)) {
                ChooseAfacctno.CallChooseAcctno(item.AfAcctno, this);
            } else {
                amendOrder(item);
            }
        }
    }

    private void amendOrder(SolenhItem item) {
        boolean isquantityamend;
        if (StaticObjectManager.getStockItem(item.Symbol) != null
                && StaticObjectManager.getStockItem(item.Symbol)
                .getTradePlace().equals(StockItem.HOSE)) {
            isquantityamend = false;
        } else {
            isquantityamend = true;
        }
        AmendOrderModel amendOrderModel = new AmendOrderModel(item.CustodyCd,
                item.AfAcctno, item.Symbol, item.Side, item.Price, item.Qtty,
                item.PriceType, item.Qtty, isquantityamend, true, item.OrderId);
        mainActivity.sendArgumentToFragment(AmendOrder.class.getName(),
                amendOrderModel);
        mainActivity.navigateFragment(AmendOrder.class.getName());
    }

    @Override
    public void refresh() {
        super.refresh();
        CallMOrder(MORDERREALTIME);
    }
}
