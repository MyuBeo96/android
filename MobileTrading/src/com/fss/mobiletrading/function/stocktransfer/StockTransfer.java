package com.fss.mobiletrading.function.stocktransfer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.ChuyenKhoanCK_Item;
import com.fss.mobiletrading.object.FindStockSenAccObj;
import com.fss.mobiletrading.object.ItemString;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.design.LabelContentLayout;
import com.msbuat.mobiletrading.design.MyContextMenu;
import com.msbuat.mobiletrading.design.MySpinner;

import java.util.ArrayList;
import java.util.List;

public class StockTransfer extends AbstractFragment {
    private static final int DELAYED_UPDATE = 3000;
    static final String FINDSTOCKSENACC = "FindStockSenAccService#FINDSTOCKSENACCSERVICE";
    static final String STOCKTRANSFER = "SuccessService#1";

    Button btn_Accept;
    Button btn_Clear;

    MySpinner spn_Stock;
    MySpinner spn_TKnhan;
    MySpinner spn_senderAfacctno;

    LabelContentLayout edt_SoLuong;
    LabelContentLayout tv_HienCo;

    List<ChuyenKhoanCK_Item> list_Stock;
    List<AcctnoItem> list_TKgui;
    List<ItemString> list_TKnhan;
    Runnable runable_update;

    public static StockTransfer newInstance(MainActivity mActivity) {

        StockTransfer self = new StockTransfer();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.StockTransfer);
        return self;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle bundle) {
        View view = inflater
                .inflate(R.layout.stocktransfer, viewGroup, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {

        spn_Stock = ((MySpinner) view.findViewById(R.id.spn_ChuyenKhoanCK_MaCK));
        spn_TKnhan = ((MySpinner) view
                .findViewById(R.id.spn_ChuyenKhoanCK_TKNhan));
        tv_HienCo = ((LabelContentLayout) view
                .findViewById(R.id.text_ChuyenKhoanCK_HienCo));
        spn_senderAfacctno = ((MySpinner) view
                .findViewById(R.id.spn_ChuyenKhoanCK_senderafacctno));
        edt_SoLuong = ((LabelContentLayout) view
                .findViewById(R.id.edt_ChuyenKhoanCK_SoLuong));
        btn_Accept = ((Button) view
                .findViewById(R.id.btn_ChuyenKhoanCK_ChapNhan));
        btn_Clear = ((Button) view.findViewById(R.id.btn_ChuyenKhoanCK_Clear));
        Common.setupUI(view.findViewById(R.id.chuyenkhoan_chungkhoan),
                getActivity());
    }

    private void initData() {

        if (list_TKgui == null) {
            list_TKgui = StaticObjectManager.loginInfo.contractList;
        }

        if (list_Stock == null) {
            list_Stock = new ArrayList<ChuyenKhoanCK_Item>();
        } else {
            list_Stock.clear();
        }

        if (list_TKnhan == null) {
            list_TKnhan = new ArrayList<ItemString>();
        } else {
            list_TKnhan.clear();
        }

        spn_TKnhan.setChoises(list_TKnhan);
        spn_senderAfacctno.setChoises(list_TKgui);
        spn_Stock.setChoises(list_Stock);

        Common.registrySeparatorNumber(edt_SoLuong.getEditContent());
    }

    private void initListener() {

        spn_Stock
                .setOnItemSelectedListener(new MyContextMenu.OnItemSelectedListener() {

                    @Override
                    public void onItemSelect(Object obj, int position) {
                        if (obj instanceof ChuyenKhoanCK_Item) {
                            ChuyenKhoanCK_Item item = (ChuyenKhoanCK_Item) obj;
                            tv_HienCo.setText(Common.formatAmount(item.TRADE));
                        }

                    }
                });
        btn_Accept.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (list_Stock.size() == 0) {
                    showDialogMessage(R.string.thong_bao,
                            R.string.chuyenkhoanck_NoStock);
                } else {
                    CallStockTransfer();
                }

            }
        });
        btn_Clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                edt_SoLuong.setText(StringConst.EMPTY);
            }
        });
        spn_senderAfacctno
                .setOnItemSelectedListener(new MyContextMenu.OnItemSelectedListener() {

                    @Override
                    public void onItemSelect(Object obj, int position) {
                        if (obj instanceof AcctnoItem) {
                            AcctnoItem acctnoItem = (AcctnoItem) obj;
                            CallFindStockSenAcc(acctnoItem.ACCTNO);
                        }

                    }
                });
    }

    public void onResume() {
        super.onResume();

        // clear dữ liệu cũ
        edt_SoLuong.setText(StringConst.EMPTY);
        // chọn theo tiểu khoản đang chọn của chương trình
        spn_senderAfacctno.setSelection(list_TKgui
                .indexOf(StaticObjectManager.acctnoItem));
    }

    @Override
    public void onShowed() {
        super.onShowed();
    }

    private void CallFindStockSenAcc(String pv_SourceAfacctno) {

        if (pv_SourceAfacctno != null) {
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value
                        .add(getStringResource(R.string.controller_FindStockSenAcc));
            }
            {
                list_key.add("pv_SourceAfacctno");
                list_value.add(pv_SourceAfacctno);
            }
            StaticObjectManager.connectServer.callHttpPostService(
                    FINDSTOCKSENACC, this, list_key, list_value);
            isLoading();
        }
    }

    private void CallStockTransfer() {
        if (spn_senderAfacctno.getContextMenu().getSelectedItem() == null
                || spn_TKnhan.getContextMenu().getSelectedItem() == null
                || spn_Stock.getContextMenu().getSelectedItem() == null) {
            return;
        }

        if (edt_SoLuong.getText().length() > 0) {
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value
                        .add(getStringResource(R.string.controller_StockTransfer));
            }
            {
                list_key.add("AfacctnoSend");
                list_value.add(((AcctnoItem) spn_senderAfacctno
                        .getContextMenu().getSelectedItem()).ACCTNO);
            }
            {
                list_key.add("AfacctnoReceive");
                list_value.add(spn_TKnhan.getContextMenu().getSelectedItem()
                        .toString());
            }
            {
                list_key.add("StockAvailable");
                list_value.add(tv_HienCo.getText().toString());
            }
            {
                list_key.add("Symbol");
                list_value.add(spn_Stock.getContextMenu().getSelectedItem()
                        .toString());
            }
            {
                list_key.add("QTTY");
                list_value.add(edt_SoLuong.getText().toString());
            }
            StaticObjectManager.connectServer.callHttpPostService(
                    STOCKTRANSFER, this, list_key, list_value);
            btn_Accept.setEnabled(false);
        } else {
            showDialogMessage(getStringResource(R.string.thong_bao),
                    getStringResource(R.string.ChuaNhapDL));
        }
    }

    public void refesh() {
        Long hienco = 0l;
        try {
            hienco = Long.valueOf(tv_HienCo.getText().toString()
                    .replace(",", StringConst.EMPTY))
                    - Long.valueOf(edt_SoLuong.getText().toString()
                    .replace(",", StringConst.EMPTY));
            tv_HienCo.setText(Common.formatAmount(String.valueOf(hienco)));
            edt_SoLuong.setText(StringConst.EMPTY);
        } catch (Exception localException) {
            tv_HienCo.setText(String.valueOf(hienco));
        }
        if (runable_update == null) {
            runable_update = new Runnable() {

                @Override
                public void run() {
                    AcctnoItem sender = (AcctnoItem) spn_senderAfacctno
                            .getContextMenu().getSelectedItem();
                    CallFindStockSenAcc(sender.ACCTNO);
                }
            };
        }
        mainActivity.delay.postDelayed(runable_update, DELAYED_UPDATE);
        edt_SoLuong.setText(StringConst.EMPTY);
    }

    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
        // clear dữ liệu cũ
        edt_SoLuong.setText(StringConst.EMPTY);
        // chọn theo tiểu khoản đang chọn của chương trình
        spn_senderAfacctno.setSelection(list_TKgui
                .indexOf(StaticObjectManager.acctnoItem));
        // CallFindStockSenAcc(StaticObjectManager.acctnoItem.ACCTNO);
    }

    @Override
    protected void process(String key, ResultObj rObj) {

        switch (key) {
            case FINDSTOCKSENACC:
                if (rObj.obj != null) {
                    FindStockSenAccObj findStockSenAccObj = (FindStockSenAccObj) rObj.obj;
                    list_Stock.clear();
                    list_TKnhan.clear();
                    list_Stock.addAll(findStockSenAccObj.getStockList());
                    list_TKnhan.addAll(findStockSenAccObj.getStockRec());
                    spn_Stock.setChoises(list_Stock);
                    spn_TKnhan.setChoises(list_TKnhan);
                    // update lại trường hiện có, vì khi adapter của spinner update,
                    // spinner k callback hàm setOnItemSelectedListener()
                    Object selecteditem = spn_Stock.getContextMenu()
                            .getSelectedItem();
                    if (selecteditem != null) {
                        tv_HienCo
                                .setText(Common
                                        .formatAmount(((ChuyenKhoanCK_Item) selecteditem).TRADE));
                    } else {
                        tv_HienCo.setText(StringConst.EMPTY);
                    }

                }
                isLoaded();
                break;
            case STOCKTRANSFER:
                showStockTransferConfirm();
                break;
            default:
                break;
        }
    }

    @Override
    protected void isReceivedResponse(String key) {
        super.isReceivedResponse(key);
        btn_Accept.setEnabled(true);
    }

    private void showStockTransferConfirm() {
        AcctnoItem senderAfacctno = null;
        ItemString beneficiaryAfacctno = null;
        String symbol = null;
        String available = null;
        String amount = null;

        if (spn_senderAfacctno.getContextMenu().getSelectedItem() == null
                || spn_TKnhan.getContextMenu().getSelectedItem() == null
                || spn_Stock.getContextMenu().getSelectedItem() == null) {
            return;
        }
        senderAfacctno = (AcctnoItem) spn_senderAfacctno.getContextMenu()
                .getSelectedItem();
        beneficiaryAfacctno = (ItemString) spn_TKnhan.getContextMenu()
                .getSelectedItem();
        symbol = spn_Stock.getContextMenu().getSelectedItem().toString();
        available = tv_HienCo.getText();
        amount = edt_SoLuong.getText().toString();

        StockTransferItem item = new StockTransferItem(senderAfacctno,
                beneficiaryAfacctno, symbol, available, amount);
        mainActivity.sendArgumentToFragment(
                StockTransferConfirm.class.getName(), item);
        mainActivity.navigateFragment(StockTransferConfirm.class.getName());
    }

    private void isLoading() {
        // tv_loading.setVisibility(TextView.VISIBLE);
    }

    private void isLoaded() {
        // tv_loading.setVisibility(TextView.GONE);
    }

    @Override
    public void refresh() {
        if (runable_update == null) {
            runable_update = new Runnable() {

                @Override
                public void run() {
                    AcctnoItem sender = (AcctnoItem) spn_senderAfacctno
                            .getContextMenu().getSelectedItem();
                    CallFindStockSenAcc(sender.ACCTNO);
                }
            };
        }
        mainActivity.delay.postDelayed(runable_update, DELAYED_UPDATE);
        edt_SoLuong.setText(StringConst.EMPTY);
    }

}
