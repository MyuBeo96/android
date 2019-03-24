package com.fss.mobiletrading.function.orderlist;

import android.app.Dialog;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.fscuat.mobiletrading.design.CustomPassLayout;
import com.fss.mobiletrading.adapter.SolenhGTC_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.SolenhItem;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MSTradeAppConfig;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.SearchTextUI;
import com.fscuat.mobiletrading.design.TabSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GTCOrderList extends AbstractFragment {
    private static final int UPDATE_INTERVAL = 3000;
    final String AMENDORDER = "SuccessService#AMENDORDER";
    final String CANCELORDERGTC = "SuccessService#CANCELORDER";
    final String DOCANCELORDER = "SuccessService#DOCANCELORDER";
    final String MGTCORDERREALTIME = "MorderService#MGTCORDERREALTIME";
    final String ORDERGTCDETAILS = "OrderDetailsService#ORDERGTCDETAILS";
    static final String GENOTPSMS = "SuccessService#GENOTPSMS";

    ListView lv_Solenh;
    TabSelector tabSelector;
    SearchTextUI searchTextUI;

    List<SolenhItem> listSolenhItem;
    SolenhGTC_Adapter adapterSolenh;

    int currentTextView;
    int SelectedItemPosition;
    String statusOrder1 = "All"; // status 1
    String statusOrder2 = "All"; // status 2
    String filterText = StringConst.EMPTY; // filter by text

    Point p;
    Timer timer;
    SolenhItem cancelItem;
    int filterDataHashcode;
    //Custom dialog
    protected CustomPassLayout edt_dialog_TradingPw;
    protected CustomPassLayout edt_dialog_OTPCode;
    protected TextView tv_XacNhan;
    protected TextView tv_Huy;
    protected ImageButton checkboxTradingpass;
    protected ImageButton checkboxOTPCode;
    Dialog dialog;

    boolean isOTP= StaticObjectManager.loginInfo.IsOTPCondOrder == "true";
    boolean saveOTP =false;
    long disableOTPTime= 01;

    public static GTCOrderList newInstance(MainActivity mActivity) {

        GTCOrderList self = new GTCOrderList();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.GTCOrder);
        return self;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle bundle) {
        View view = inflater.inflate(R.layout.gtcorderlist, viewGroup, false);
        initView(view);
        initData();
        initListener();
        Common.setupUI(view, mainActivity);
        return view;
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
    private void inputTradingPw(boolean isShow){
        dialog = new Dialog(mainActivity, R.style.style_dialog);
        LayoutInflater li = LayoutInflater.from(mainActivity);
        dialog.setContentView(R.layout.input_tradingpw_dialog);
        edt_dialog_TradingPw = (CustomPassLayout) dialog.findViewById(R.id.edt_dialog_tradingcode);
        checkboxTradingpass= edt_dialog_TradingPw.getcheckbox();
        edt_dialog_OTPCode = (CustomPassLayout) dialog.findViewById(R.id.edt_dialog_otpcode);
        checkboxOTPCode = edt_dialog_OTPCode.getcheckbox();
        checkboxTradingpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkboxTradingpass.setSelected(!checkboxTradingpass.isSelected());
            }
        });
        tv_XacNhan = (TextView) dialog.findViewById(R.id.text_dialog_possitive);
        tv_Huy = (TextView) dialog.findViewById(R.id.text_dialog_negative);
        checkboxOTPCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkboxOTPCode.setSelected(!checkboxOTPCode.isSelected());
            }
        });
        edt_dialog_OTPCode.getbtn().setOnClickListener(new View.OnClickListener() {
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
        tv_XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });
        tv_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void initView(View view) {
        lv_Solenh = ((ListView) view.findViewById(R.id.listview_solenhgtc));
        tabSelector = (TabSelector) view.findViewById(R.id.tab_gtcorderlist);
        if (DeviceProperties.isTablet) {
            searchTextUI = (SearchTextUI) view
                    .findViewById(R.id.searchtext_gtcorderlist_all);
            searchTextUI.setVisibleButton(false);
            searchTextUI.setVisibleClearField(true);
            searchTextUI.setTextColor(getColorResource(R.color.white_text));
        }
    }

    private void initData() {
        if (listSolenhItem == null) {
            listSolenhItem = new ArrayList<SolenhItem>();
        }
        if (adapterSolenh == null) {
            adapterSolenh = new SolenhGTC_Adapter(mainActivity,
                    android.R.layout.simple_list_item_1, listSolenhItem);
            adapterSolenh.setItemClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if (obj != null && obj instanceof SolenhItem) {
                        mainActivity.sendArgumentToFragment(
                                GTCOrderDetail.class.getName(), obj);
                        mainActivity.navigateFragment(GTCOrderDetail.class
                                .getName());
                    }
                }
            });

            adapterSolenh.setmCancelClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if (obj instanceof SolenhItem
                            && ((SolenhItem) obj).isCancellable
                            .equals(StringConst.TRUE)) {

                        cancelItem = (SolenhItem) obj;
                      inputTradingPw(true);
                    } else {
                        showDialogMessage(R.string.thong_bao,
                                R.string.solenh_lenhkhongduocphephuy);
                    }
                }
            });
        }
        lv_Solenh.setAdapter(adapterSolenh);
    }

    private void initListener() {
        if (DeviceProperties.isTablet) {
            searchTextUI.getEditText().addTextChangedListener(
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
        }
        // lv_Solenh.setOnItemClickListener(new OnItemClickListener() {
        //
        // @Override
        // public void onItemClick(AdapterView<?> parent, View view,
        // int position, long id) {

        // SolenhItem item = listSolenhItem.get(position);
        // mainActivity.sendArgumentToFragment(
        // GTCOrderDetail.class.getName(), item);
        // mainActivity.navigateFragment(GTCOrderDetail.class.getName());
        // CancelTimer();
        // }
        // });
        lv_Solenh.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                SelectedItemPosition = position;
                return false;
            }
        });
        tabSelector.setOnTabSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        // tất cả các lệnh
                        statusOrder1 = "All";
                        statusOrder2 = "All";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        break;
                    case 1:
                        // chờ gửi
                        statusOrder1 = "P";
                        statusOrder2 = "P";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        break;
                    case 2:
                        // hoạt động
                        statusOrder1 = "A";
                        statusOrder2 = "A";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        break;

                    case 3:
                        // hủy
                        statusOrder1 = "R";
                        statusOrder2 = "R";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
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
        if (DeviceProperties.isTablet) {
            searchTextUI.getEditText().getText().clear();
        }
        CancelTimer();
    }

    public void onResume() {
        super.onResume();
        tabSelector.setItemSelected(0);
        CallMGTCOrder(MGTCORDERREALTIME);
        try {
            disableOTPTime= Long.parseLong(StaticObjectManager.loginInfo.DisableOTPTime)*1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onShowed() {
        super.onShowed();
        CallMGTCOrder(MGTCORDERREALTIME);
    }

    @Override
    public void onHide() {
        super.onHide();
        CancelTimer();
    }

    private void cancelOrder() {
        if (cancelItem != null) {
            CallCancelOrderGTC(cancelItem);
        }
    }

    private void CallCancelOrderGTC(SolenhItem item) {
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
            list_value
                    .add(getStringResource(R.string.controller_CancelOrderGTC));
        }
        {
            list_key.add("OrderId");
            list_value.add(item.OrderId);
        }
        {
            list_key.add("Custodycd");
            list_value.add(item.CustodyCd);
        }
        {
            list_key.add("TradingPassword");
            list_value.add(isOTP ? edt_dialog_OTPCode.getText().toString() : edt_dialog_TradingPw.getText().toString());
        }
        {
            list_key.add("saveotp");
            list_value.add(saveOTP?"Y":"N");
        }
        StaticObjectManager.connectServer.callHttpPostService(CANCELORDERGTC,
                this, list_key, list_value);

    }

    private void CallMGTCOrder(String key) {
        try {
            StaticObjectManager.connectServer.callHttpGetService(key, this,
                    MSTradeAppConfig.controller_GTCOrders);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callRealtimeDelay() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                CallMGTCOrder(MGTCORDERREALTIME);
            }
        };
        try {
            timer.schedule(timertask, UPDATE_INTERVAL);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void CancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
    }

    @Override
    protected void process(String key, ResultObj rObj) {
        switch (key) {
            case MGTCORDERREALTIME:
                if (rObj.obj != null) {
                    listSolenhItem.clear();
                    listSolenhItem.addAll((List<SolenhItem>) rObj.obj);
                    filterOrder();
                }
                break;
            case GENOTPSMS:
                showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
                break;
            case CANCELORDERGTC:
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
                showDialogMessage(R.string.thong_bao, R.string.Giaodichthanhcong);
                break;
            default:
                break;
        }
    }
    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoActionMenu();
    }


    @Override
    protected void isReceivedResponse(String key) {
        super.isReceivedResponse(key);
        switch (key) {
            case MGTCORDERREALTIME:
                callRealtimeDelay();
                break;
            case CANCELORDERGTC:
                break;
            case AMENDORDER:
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
        SolenhGTC_Adapter adapterSolenh;
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

    @Override
    public void refresh() {
        super.refresh();
        CallMGTCOrder(MGTCORDERREALTIME);
    }
}
