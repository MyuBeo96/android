package com.fss.mobiletrading.function.orderlist;

import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tcscuat.mobiletrading.design.CustomPassLayout;
import com.fss.mobiletrading.adapter.SolenhCT_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.ChooseAfacctno;
import com.fss.mobiletrading.function.notify.NotificationService;
import com.fss.mobiletrading.function.placeorder.OrderSetParams;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.OrderDetailsItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.SolenhItem;
import com.fss.mobiletrading.object.StockItem;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.MyActionBar.Action;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;
import com.tcscuat.mobiletrading.design.LabelContentLayout;
import com.tcscuat.mobiletrading.design.VerticalListview;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail extends AbstractFragment {

    final String ORDERDETAILS = "OrderDetailsService#ORDERDETAILS";
    final String CANCELORDER = "SuccessService#CANCELORDER";
    final String CHECKORDER = "CheckOrderService#CHECKORDER";
    final String GENOTPSMS = "SuccessService#GENOTPSMS";
    public static final String GETUNREAD = "GetUnReadService#GETUNREAD";
    int unread = 0;
    String  priceNew ;
    Double price;
    LabelContentLayout tv_chitiet_CustodyCd;
    LabelContentLayout tv_chitiet_OrderSide;
    LabelContentLayout tv_chitiet_KLKhop;
    LabelContentLayout tv_chitiet_MaCK;
    LabelContentLayout tv_chitiet_TieuKhoan;
    LabelContentLayout tv_chitiet_TrangThai;
    LabelContentLayout tv_chitiet_PriceType;
    LabelContentLayout tv_chitiet_Gia;
    LabelContentLayout tv_chitiet_SoLuong;
    CustomPassLayout edt_TradingPw;
    CustomPassLayout edt_OTPCode;
    Button btn_chitiet_HuyLenh;
    Button btn_chitiet_SuaLenh;
    protected VerticalListview lv_SolenhCT;
    protected ImageButton checkboxTradingpass;
    protected ImageButton checkboxOTPCode;
    protected EditText etOTPCode;

    List<OrderDetailsItem> listOrderDetails;
    SolenhCT_Adapter adapterSolenhCT;

    SolenhItem item;

    long mLastConfirmClickTime = 0l;
    long disableOTPTime= 01;
    boolean isOTP= StaticObjectManager.loginInfo.IsOTPOrder == "true";
    boolean saveOTP= false;


    public static OrderDetail newInstance(MainActivity mActivity) {
        OrderDetail self = new OrderDetail();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.OrderDetail);
        return self;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderdetail, container, false);
        tv_chitiet_CustodyCd = ((LabelContentLayout) view
                .findViewById(R.id.text_solenh_chitiet_custodycd));
        tv_chitiet_TieuKhoan = ((LabelContentLayout) view
                .findViewById(R.id.text_solenh_chitiet_afacctno));
        tv_chitiet_MaCK = ((LabelContentLayout) view
                .findViewById(R.id.text_solenh_chitiet_symbol));
        tv_chitiet_OrderSide = ((LabelContentLayout) view
                .findViewById(R.id.text_solenh_chitiet_side));
        tv_chitiet_Gia = ((LabelContentLayout) view
                .findViewById(R.id.edt_solenh_chitiet_orderprice));
        tv_chitiet_SoLuong = ((LabelContentLayout) view
                .findViewById(R.id.edt_solenh_chitiet_orderquantity));
        tv_chitiet_KLKhop = ((LabelContentLayout) view
                .findViewById(R.id.text_solenh_chitiet_matchquantity));
        tv_chitiet_TrangThai = ((LabelContentLayout) view
                .findViewById(R.id.text_solenh_chitiet_status));
        tv_chitiet_PriceType = (LabelContentLayout) view
                .findViewById(R.id.text_solenh_chitiet_pricetype);
        edt_TradingPw = (CustomPassLayout) view.findViewById(R.id.edt_orderdetail_TradingCode);
        edt_OTPCode =(CustomPassLayout) view.findViewById(R.id.edt_orderdetail_OTCode);

        lv_SolenhCT = ((VerticalListview) view
                .findViewById(R.id.listview_solenhthuongCT));
        btn_chitiet_HuyLenh = ((Button) view
                .findViewById(R.id.btn_solenh_chitiet_HuyLenh));
        btn_chitiet_SuaLenh = ((Button) view
                .findViewById(R.id.btn_solenh_chitiet_SuaLenh));


        tv_chitiet_OrderSide.getContent().setAllCaps(true);
        initData();
        initListener();
        return view;
    }

    protected void initListener() {
        lv_SolenhCT.setOnTouch();

        btn_chitiet_HuyLenh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                                CallCancelOrder(item);
            }
        });
        btn_chitiet_SuaLenh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                changeAccountAndAmendOrder();
            }
        });
        checkboxTradingpass = edt_TradingPw.getcheckbox();
        checkboxTradingpass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkboxTradingpass.setSelected(!checkboxTradingpass.isSelected());
            }
        });
        checkboxOTPCode = edt_OTPCode.getcheckbox();
        etOTPCode = (EditText)edt_OTPCode.getEditContent();
        checkboxOTPCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkboxOTPCode.setSelected(!checkboxOTPCode.isSelected());
            }
        });
        edt_OTPCode.getbtn().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((SystemClock.elapsedRealtime() - StaticObjectManager.mLastGenOTPClickTime) < disableOTPTime) {
                    return;
                }
                StaticObjectManager.mLastGenOTPClickTime=SystemClock.elapsedRealtime();
                GenOTPSMS();
            }
        });
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
    protected void initData() {
        if (listOrderDetails == null) {
            listOrderDetails = new ArrayList<OrderDetailsItem>();
        } else {
            listOrderDetails.clear();
        }
        if (adapterSolenhCT == null) {
            adapterSolenhCT = new SolenhCT_Adapter(mainActivity,
                    android.R.layout.simple_list_item_1, listOrderDetails);
        }
        lv_SolenhCT.setAdapter(adapterSolenhCT);

    }
    private void customDisplay(){
        if(isOTP){
            if(StaticObjectManager.saveOTP) {
                edt_OTPCode.setText(StaticObjectManager.strOTP);
                checkboxOTPCode.setSelected(StaticObjectManager.saveOTP);
                edt_OTPCode.setVisibility(View.GONE);
                saveOTP= StaticObjectManager.saveOTP;
            }
            else
                edt_OTPCode.setText(StringConst.EMPTY);
        }
        else {
            if (StaticObjectManager.saveTradingPass) {
                edt_TradingPw.setText(StaticObjectManager.tradingPass);
                checkboxTradingpass.setSelected(StaticObjectManager.saveTradingPass);
                edt_TradingPw.setVisibility(View.GONE);
            } else {
                edt_TradingPw.setText(StringConst.EMPTY);
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        customDisplay();
        if (item != null) {
            displayOrderDetail(item);
            CallOrderDetails(item.OrderId, item.CustodyCd);
        }
        try {
            disableOTPTime= Long.parseLong(StaticObjectManager.loginInfo.DisableOTPTime)*1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoAction();
        //set gia/1000
        if(item.Price.contains(",")){
            priceNew = item.Price.replaceAll(",", "");
            price = (Double.parseDouble(priceNew)/1000);
            priceNew= String.valueOf(price);
        }
        if (!DeviceProperties.isTablet) {
            mainActivity.addAction(new Action() {

                @Override
                public void performAction(View view) {
                    mainActivity.setOrderToPlaceOrder(new OrderSetParams(
                            item.CustodyCd, item.AfAcctno, item.Symbol,
                            item.Side, priceNew, item.Qtty));
                }

                @Override
                public int getDrawable() {
                    return R.drawable.ic_datlenh_white;
                }

                @Override
                public String getText() {
                    return null;
                }
            });
        }
    }

    private void changeAccountAndAmendOrder() {
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
                amendOrder();
            }
        }
    }

    private void amendOrder() {
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

    protected void displayOrderDetail(SolenhItem item) {

        if (item.isCancellable.equals(StringConst.TRUE)) {
            btn_chitiet_HuyLenh.setEnabled(true);
        } else {
            btn_chitiet_HuyLenh.setEnabled(false);
        }
        if (item.isModifiable.equals(StringConst.TRUE)) {
            btn_chitiet_SuaLenh.setEnabled(true);
        } else {
            btn_chitiet_SuaLenh.setEnabled(false);
        }

        tv_chitiet_CustodyCd.setText(item.CustodyCd);
        tv_chitiet_TieuKhoan.setText(item.AfAcctno);
        tv_chitiet_MaCK.setText(item.Symbol);
        tv_chitiet_PriceType.setText(item.PriceType);
        tv_chitiet_Gia.setText(item.Price);
        tv_chitiet_SoLuong.setText(Common.formatAmount(item.Qtty));
        tv_chitiet_KLKhop.setText(Common.formatAmount(item.Matched));
        tv_chitiet_TrangThai.setText(item.Status);
        //edt_TradingPw.setText(StringConst.EMPTY);

        if (item.Side.equals(PlaceOrder.SIDE_NB)) {
            tv_chitiet_OrderSide.setText(getStringResource(R.string.Mua));
            tv_chitiet_OrderSide.setActivated(true);
            tv_chitiet_PriceType.setActivated(true);
            tv_chitiet_SoLuong.setActivated(true);
            tv_chitiet_KLKhop.setActivated(true);
            tv_chitiet_Gia.setActivated(true);

        } else {
            tv_chitiet_OrderSide.setText(getStringResource(R.string.Ban));
            tv_chitiet_OrderSide.setActivated(false);
            tv_chitiet_PriceType.setActivated(false);
            tv_chitiet_SoLuong.setActivated(false);
            tv_chitiet_KLKhop.setActivated(false);
            tv_chitiet_Gia.setActivated(false);
        }
    }

    protected void CallCancelOrder(SolenhItem item) {
        if(isOTP) {
            if (edt_OTPCode.getText().length() == 0) {
                if (edt_OTPCode.getVisibility() != View.VISIBLE) {
                    edt_OTPCode.setVisibility(View.VISIBLE);
                }
                showDialogMessage(
                        getResources().getString(
                                R.string.thong_bao),
                        getResources().getString(
                                R.string.requireOTP));
                edt_OTPCode.requestFocus();
                return;
            }

        }
        else {
            if (edt_TradingPw.getText().length() == 0) {
                if (edt_TradingPw.getVisibility() != View.VISIBLE) {
                    edt_TradingPw.setVisibility(View.VISIBLE);
                }
                showDialogMessage(getStringResource(R.string.thong_bao), getStringResource(R.string.NhapPin));
                edt_TradingPw.requestFocus();
                return;
            }
        }
        if (item != null) {
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value
                        .add(getStringResource(R.string.controller_CancelOrder));
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
                list_value.add(isOTP ? edt_OTPCode.getText().toString() : edt_TradingPw.getText().toString());
            }
            {
                list_key.add("saveotp");
                list_value.add(saveOTP?"Y":"N");
            }
            StaticObjectManager.connectServer.callHttpPostService(CANCELORDER,
                    this, list_key, list_value);
            btn_chitiet_HuyLenh.setEnabled(false);
        }

    }

    protected void CallOrderDetails(String orderId, String pv_custodycd) {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(getStringResource(R.string.controller_OrderDetails));
        }
        {
            list_key.add("pv_OrderId");
            list_value.add(orderId);
        }
        {
            list_key.add("pv_custodycd");
            list_value.add(pv_custodycd);
        }
        StaticObjectManager.connectServer.callHttpPostService(ORDERDETAILS,
                this, list_key, list_value);
    }

    /**
     * Truyền vào id của lệnh muốn xem chi tiết trước khi được resume
     *
     * @param obj : object của SoLenhItem, nếu truyền vào item là null thì không
     *            hiển thị gì
     */

    @Override
    public void getArgument(Object obj) {
        super.getArgument(obj);
        if (obj instanceof SolenhItem) {
            this.item = (SolenhItem) obj;
        }

        if (this.isResumed()) {
            displayOrderDetail(item);
        }

    }
    @Override
    public void onDetach() {
        super.onDetach();
        StaticObjectManager.mLastGenOTPClickTime = 01;
    }
    @Override
    protected void process(String key, ResultObj rObj) {
        switch (key) {
            case GETUNREAD:
                unread = (int) rObj.obj;
                mainActivity.showUnReadNotify(unread);
                break;
            case CANCELORDER:
                if(isOTP){
                    StaticObjectManager.saveOTP= checkboxOTPCode.isSelected();
                    if(StaticObjectManager.saveOTP)
                        StaticObjectManager.strOTP = edt_OTPCode.getText().toString();
                    else
                        StaticObjectManager.strOTP = StringConst.EMPTY;
                }
                else {
                    StaticObjectManager.saveTradingPass = checkboxTradingpass.isSelected();
                    if (StaticObjectManager.saveTradingPass)
                        StaticObjectManager.tradingPass = edt_TradingPw.getText().toString();
                    else
                        StaticObjectManager.tradingPass = StringConst.EMPTY;
                }
                showDialogMessage(getStringResource(R.string.thong_bao),
                        getStringResource(R.string.Giaodichthanhcong),
                        new SimpleAction() {

                            @Override
                            public void performAction(Object obj) {
                                if (mainActivity != null) {
                                    mainActivity.backNavigateFragment();
                                }
                            }
                        });
                break;
            case GENOTPSMS:
                String[] arrayString = rObj.EM.split(";");
                showDialogMessage(getStringResource(R.string.thong_bao), arrayString[0]);
                if (rObj.EC == 0 && StaticObjectManager.loginInfo.IsFillOTP.equals("Y"))
                {
                    etOTPCode.setText(arrayString[1]);
                }
                break;
            case ORDERDETAILS:
                if (rObj.obj != null) {
                    listOrderDetails.clear();
                    listOrderDetails.addAll((List<OrderDetailsItem>) rObj.obj);
                    adapterSolenhCT.notifyDataSetChanged();
                }
                break;
            case ChooseAfacctno.CHANGE_ACCTNO:
                ChooseAfacctno chooseAfacctno = (ChooseAfacctno) mainActivity.mapFragment
                        .get(ChooseAfacctno.class.getName());
                chooseAfacctno.changeAfacctno(item.AfAcctno);
                amendOrder();
                break;
            case ChooseAfacctno.FINDCUSTODYCD:
                if (rObj.obj != null) {
                    List<AcctnoItem> list = ((List<AcctnoItem>) rObj.obj);
                    chooseAfacctno = (ChooseAfacctno) mainActivity.mapFragment
                            .get(ChooseAfacctno.class.getName());
                    if (chooseAfacctno != null) {
                        chooseAfacctno.changeCustodyCd(list);
                        ChooseAfacctno.CallChooseAcctno(item.AfAcctno, this);
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
            case CANCELORDER:
                btn_chitiet_HuyLenh.setEnabled(true);
                break;

            default:
                break;
        }
    }

    @Override
    protected void processErrorOther(String key, ResultObj rObj) {
        super.processErrorOther(key, rObj);
    }

    @Override
    public void refresh() {
        super.refresh();
        CallOrderDetails(item.OrderId, item.CustodyCd);
    }
}
