package com.fss.mobiletrading.function.placeorder;

import android.os.Bundle;
import android.os.SystemClock;
import android.test.suitebuilder.annotation.Smoke;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.orderlist.GTCOrderList;
import com.fss.mobiletrading.function.orderlist.NormalOrderList;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.LabelContentLayout;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirm extends AbstractFragment {

    final String TRADECONFIRM = "SuccessService#TRADECONFIRM";
    final String TRADEGTCCONFIRM = "SuccessService#TRADEGTCCONFIRM";

    static final String DEFAULT_PRICETYPE = "LO";

    protected LabelContentLayout tv_checkorder_Gia;
    protected TextView tv_checkorder_LoaiLenh;
    protected TextView tv_checkorder_MaCK;
    protected LabelContentLayout tv_checkorder_SoLuong;
    protected LabelContentLayout tv_checkorder_SoLuuKy;
    protected LabelContentLayout tv_checkorder_TieuKhoan;
    protected LabelContentLayout tv_checkorder_fromDate;
    protected LabelContentLayout tv_checkorder_toDate;
    protected LabelContentLayout edt_checkorder_TradingPw;
    protected TextView tv_checkorder_Side;
    protected Button btn_detail_ChapNhan;
    protected Button btn_detail_Cancel;
    OrderSetParams orderSetParams;
    long mLastConfirmClickTime = 0l;

    public static OrderConfirm newInstance(MainActivity mActivity) {

        OrderConfirm self = new OrderConfirm();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.XacNhanLenh);
        return self;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderconfirm, container, false);
        tv_checkorder_SoLuuKy = ((LabelContentLayout) view
                .findViewById(R.id.text_checkorder_SoLuuKy));
        tv_checkorder_TieuKhoan = (LabelContentLayout) view
                .findViewById(R.id.text_checkorder_TieuKhoan);
        tv_checkorder_MaCK = (TextView) view
                .findViewById(R.id.text_checkorder_MaCK);
        tv_checkorder_SoLuong = (LabelContentLayout) view
                .findViewById(R.id.text_checkorder_SoLuong);
        tv_checkorder_Gia = (LabelContentLayout) view
                .findViewById(R.id.text_checkorder_Gia);
        tv_checkorder_LoaiLenh = (TextView) view
                .findViewById(R.id.text_checkorder_LoaiLenh);
        tv_checkorder_Side = (TextView) view
                .findViewById(R.id.text_checkorder_Side);
        tv_checkorder_fromDate = (LabelContentLayout) view
                .findViewById(R.id.text_checkorder_fromDate);
        tv_checkorder_toDate = (LabelContentLayout) view
                .findViewById(R.id.text_checkorder_toDate);
        edt_checkorder_TradingPw = (LabelContentLayout) view
                .findViewById(R.id.edt_checkorder_TradingCode);
        btn_detail_ChapNhan = (Button) view
                .findViewById(R.id.btn_checkorder_Accept);

        btn_detail_ChapNhan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((SystemClock.elapsedRealtime() - mLastConfirmClickTime) < 6000) {
                    return;
                }
                mLastConfirmClickTime = SystemClock.elapsedRealtime();
                if (orderSetParams.isGTCOrder) {
                    CallGTCTradeConfirm();
                } else {
                    CallTradeConfirm();
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        int width = getDimenResource(R.dimen.t_dialogorderconfirm_width);
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(width, LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showOrder();
    }

    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoAction();
    }

    @Override
    protected void performBackAction() {
        super.performBackAction();
        if (mainActivity != null) {
            mainActivity.backNavigateFragment();
        }
    }

    protected void CallTradeConfirm() {
        if (edt_checkorder_TradingPw.getText().length() != 0) {
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value.add(getStringResource(R.string.controller_TradeConfirm));
            }
            {
                list_key.add("AFACCTNO");
                list_value.add(orderSetParams.afacctno);
            }
            {
                list_key.add("CUSTODYCD");
                list_value.add(orderSetParams.custodycd);
            }
            {
                list_key.add("SIDE");
                list_value.add(orderSetParams.sideOrder);
            }
            {
                list_key.add("SYMBOL");
                list_value.add(orderSetParams.symbolOrder);
            }
            {
                list_key.add("PRICETYPE");
                list_value.add(orderSetParams.priceType);
            }
            {
                list_key.add("QTTY");
                list_value.add(orderSetParams.quantityOrder);
            }
            {
                list_key.add("PRICE");
                list_value.add(orderSetParams.priceOrder);
            }
            {
                list_key.add("SplitQtty");
                list_value.add(orderSetParams.splitOrder);
            }
            {
                list_key.add("TradingPassword");
                list_value.add(edt_checkorder_TradingPw.getText().toString());
            }
            StaticObjectManager.connectServer.callHttpPostService(TRADECONFIRM,
                    this, list_key, list_value);
            mainActivity.loadingScreen(true);
        } else {
            showDialogMessage(
                    getResources().getString(
                            R.string.thong_bao),
                    getResources().getString(
                            R.string.NhapPin));
            edt_checkorder_TradingPw.requestFocus();
        }
    }

    protected void CallGTCTradeConfirm() {
        if (edt_checkorder_TradingPw.getText().length() != 0) {
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value
                        .add(getStringResource(R.string.controller_TradeGTCConfirm));
            }
            {
                list_key.add("AFACCTNO");
                list_value.add(orderSetParams.afacctno);
            }
            {
                list_key.add("CUSTODYCD");
                list_value.add(orderSetParams.custodycd);
            }
            {
                list_key.add("SIDE");
                list_value.add(orderSetParams.sideOrder);
            }
            {
                list_key.add("SYMBOL");
                list_value.add(orderSetParams.symbolOrder);
            }
            {
                list_key.add("PRICETYPE");
                list_value.add(DEFAULT_PRICETYPE);
            }
            {
                list_key.add("QTTY");
                list_value.add(orderSetParams.quantityOrder);
            }
            {
                list_key.add("PRICE");
                list_value.add(orderSetParams.priceOrder);
            }
            {
                list_key.add("fromDate");
                list_value.add(orderSetParams.fromDate);
            }
            {
                list_key.add("toDate");
                list_value.add(orderSetParams.toDate);
            }
            {
                list_key.add("TradingPassword");
                list_value.add(edt_checkorder_TradingPw.getText().toString());
            }
            StaticObjectManager.connectServer.callHttpPostService(TRADEGTCCONFIRM,
                    this, list_key, list_value);
            mainActivity.loadingScreen(true);
        } else {
            showDialogMessage(
                    getResources().getString(
                            R.string.thong_bao),
                    getResources().getString(
                            R.string.NhapPin));
            edt_checkorder_TradingPw.requestFocus();
        }
    }

    private void showOrder() {
        if (orderSetParams == null) {
            return;
        }
        // if (orderSetParams.isGTCOrder) {
        // tv_checkorder_fromDate.setVisibility(View.VISIBLE);
        // tv_checkorder_toDate.setVisibility(View.VISIBLE);
        // }

        tv_checkorder_TieuKhoan.getContent().setText(orderSetParams.afacctno);
        tv_checkorder_MaCK.setText(orderSetParams.symbolOrder);
        tv_checkorder_SoLuong.getContent()
                .setText(orderSetParams.quantityOrder);
        tv_checkorder_LoaiLenh.setText(orderSetParams.priceType);
        tv_checkorder_SoLuuKy.getContent().setText(orderSetParams.custodycd);
        if (orderSetParams.sideOrder.equals(PlaceOrder.SIDE_NB)) {
            tv_checkorder_Side.setText(getStringResource(R.string.Mua));
            tv_checkorder_Side
                    .setTextColor(getColorResource(R.color.color_Mua));
            tv_checkorder_LoaiLenh
                    .setTextColor(getColorResource(R.color.color_Mua));
            tv_checkorder_SoLuong.setActivated(true);
            tv_checkorder_Gia.setActivated(true);
        } else {
            tv_checkorder_Side.setText(getStringResource(R.string.Ban));
            tv_checkorder_Side
                    .setTextColor(getColorResource(R.color.color_Ban));
            tv_checkorder_LoaiLenh
                    .setTextColor(getColorResource(R.color.color_Ban));
            tv_checkorder_SoLuong.setActivated(false);
            tv_checkorder_Gia.setActivated(false);
        }
        tv_checkorder_fromDate.setText(orderSetParams.fromDate);
        tv_checkorder_toDate.setText(orderSetParams.toDate);
        //Check lenh thi truong khi đặt ko xác định dc giá đặt
        if (orderSetParams.priceType.equals(PlaceOrder.GTCORDERPRICETYPE)) {
            tv_checkorder_Gia.getContent().setText(orderSetParams.priceOrder);
        } else {
            tv_checkorder_Gia.getContent().setText(orderSetParams.priceType);
        }
        edt_checkorder_TradingPw.setText(StringConst.EMPTY);
    }

    @Override
    protected void process(String key, ResultObj rObj) {
        switch (key) {
            case TRADEGTCCONFIRM:
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showDialogMessage(
                        getStringResource(R.string.datlenh_title_DatLenhThanhCong),
                        getStringResource(R.string.datlenh_title_QuestionTradeConfirm),
                        new SimpleAction() {

                            @Override
                            public void performAction(Object obj) {
                                moveToGTCOrderList();
                            }
                        }, new SimpleAction() {

                            @Override
                            public void performAction(Object obj) {
                                backToPlaceOrder();
                            }
                        }, getStringResource(R.string.Yes),
                        getStringResource(R.string.No));
                break;
            case TRADECONFIRM:

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                showDialogMessage(
                        getStringResource(R.string.datlenh_title_DatLenhThanhCong),
                        getStringResource(R.string.datlenh_title_QuestionTradeConfirm),
                        new SimpleAction() {

                            @Override
                            public void performAction(Object obj) {
                                moveToOrderList();
                            }

                        }, new SimpleAction() {
                            @Override
                            public void performAction(Object obj) {
                                backToPlaceOrder();
                            }
                        }, getStringResource(R.string.Yes),
                        getStringResource(R.string.No));
                break;

            default:
                break;
        }
    }

    private void moveToOrderList() {
        mainActivity.displayFragment(NormalOrderList.class.getName());
        if (DeviceProperties.isTablet) {
            PlaceOrder placeOrder = (PlaceOrder) mainActivity
                    .getFragmentByName(PlaceOrder.class.getName());
            placeOrder.getDialog().dismiss();
        }
        onDismiss(getDialog());
    }

    private void moveToGTCOrderList() {
        mainActivity.displayFragment(GTCOrderList.class.getName());
        if (DeviceProperties.isTablet) {
            PlaceOrder placeOrder = (PlaceOrder) mainActivity
                    .getFragmentByName(PlaceOrder.class.getName());
            placeOrder.getDialog().dismiss();
        }
        onDismiss(getDialog());
    }

    private void backToPlaceOrder() {
        if (DeviceProperties.isTablet) {
            PlaceOrder placeOrder = (PlaceOrder) mainActivity
                    .getFragmentByName(PlaceOrder.class.getName());
            placeOrder.getDialog().dismiss();
        } else {
            if (orderSetParams.isGTCOrder) {
                GTCPlaceOrder gtcplaceOrder = (GTCPlaceOrder) mainActivity
                        .getFragmentByName(GTCPlaceOrder.class.getName());
                gtcplaceOrder.clearForm();
                gtcplaceOrder.onResume();
                mainActivity.backNavigateFragment();
            } else {
                PlaceOrder placeOrder = (PlaceOrder) mainActivity
                        .getFragmentByName(PlaceOrder.class.getName());
                placeOrder.clearForm();
                placeOrder.onResume();
                mainActivity.backNavigateFragment();
            }
        }
        onDismiss(getDialog());
    }

    @Override
    protected void isReceivedResponse(String key) {
        super.isReceivedResponse(key);
        mainActivity.loadingScreen(false);
    }

    @Override
    protected void processErrorOther(String key, ResultObj rObj) {
        super.processErrorOther(key, rObj);
    }

    @Override
    protected void processNull(String key) {
        super.processNull(key);
        btn_detail_ChapNhan.setEnabled(true);
    }

    @Override
    public void getArgument(Object obj) {
        super.getArgument(obj);
        if (obj instanceof OrderSetParams) {
            orderSetParams = (OrderSetParams) obj;
        }

    }

}
