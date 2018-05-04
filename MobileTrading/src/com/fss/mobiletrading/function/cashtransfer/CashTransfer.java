package com.fss.mobiletrading.function.cashtransfer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.AppData;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.Login;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.design.MyContextMenu.OnItemSelectedListener;
import com.msbuat.mobiletrading.design.MySpinner;

import java.util.ArrayList;
import java.util.List;

public class CashTransfer extends AbstractFragment {

    static final String trans = "Cash";
    static final int internalTransfer = 0;
    static final int bankTransfer = 1;
    static final int scTransfer = 2;
    int stateTransfer;

    String INTERNAL_TRANSFER;
    String SC_TRANSFER;
    String BANK_TRANSFER;
    public static final String GETTRANSDESC = "GetTransferDescService#GETTRANSDESC";
    static final String GETBANKACCLIST = "GetBankAccListService#GETBANKACCLIST";
    static final String GETCASHTRANSFERINFO = "GetCashTransferInfoService#GETCASHTRANSFERINFO";
    static final String GETTRANSFERFEEANDTOTAL = "GetTransferFeeAndTotalService#GETTRANSFERFEEANDTOTAL";

    MySpinner spn_switch;
    List<String> list_spinner;
    FrameLayout lay_internaltransfer;
    LinearLayout lay_sctransfer;
    LinearLayout lay_banktransfer;

    AbstractFragment internalCashTransfer;
    AbstractFragment scCashTransfer;
    AbstractFragment bankCashTransfer;

    Integer selectionSpinner = 0;

    public static CashTransfer newInstance(MainActivity mActivity) {
        CashTransfer sefl = new CashTransfer();
        sefl.mainActivity = mActivity;
        sefl.TITLE = mActivity.getStringResource(R.string.CashTransfer);
        return sefl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cashtransfer, container, false);

        spn_switch = (MySpinner) view.findViewById(R.id.spn_cashtransfer);
        lay_internaltransfer = (FrameLayout) view
                .findViewById(R.id.lay_cashtransfer_container);
        initData();
        initListener();
        return view;
    }

    private void initData() {

        INTERNAL_TRANSFER = getStringResource(R.string.InternalTransfer);
//        SC_TRANSFER = getStringResource(R.string.SCTransfer);
        BANK_TRANSFER = getStringResource(R.string.BankTransfer);
        if (list_spinner == null) {
            list_spinner = new ArrayList<String>();
            list_spinner.add(INTERNAL_TRANSFER);
//            list_spinner.add(SC_TRANSFER);
            list_spinner.add(BANK_TRANSFER);
        }

        if (internalCashTransfer == null) {
            internalCashTransfer = mainActivity.mapFragment
                    .get(InternalCashTransfer.class.getName());

        }
        if (scCashTransfer == null) {
            scCashTransfer = mainActivity.mapFragment.get(SCCashTransfer.class
                    .getName());
        }
        if (bankCashTransfer == null) {
            bankCashTransfer = mainActivity.mapFragment
                    .get(BankCashTransfer.class.getName());
        }

        spn_switch.setChoises(list_spinner);

    }

    private void initListener() {

        spn_switch.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelect(Object obj, int position) {
                if (list_spinner.get(position).equals(INTERNAL_TRANSFER)) {
                    stateTransfer = internalTransfer;
                    mainActivity.displayFragment(
                            InternalCashTransfer.class.getName(),
                            R.id.lay_cashtransfer_container);
                }
                if (list_spinner.get(position).equals(SC_TRANSFER)) {
                    stateTransfer = scTransfer;
                    mainActivity.displayFragment(
                            SCCashTransfer.class.getName(),
                            R.id.lay_cashtransfer_container);
                }
                if (list_spinner.get(position).equals(BANK_TRANSFER)) {
                    stateTransfer = bankTransfer;
                    mainActivity.displayFragment(
                            BankCashTransfer.class.getName(),
                            R.id.lay_cashtransfer_container);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        spn_switch.setSelection(0);
    }

    @Override
    public void onShowed() {
        super.onShowed();
        if (stateTransfer == internalTransfer) {
            internalCashTransfer.onShowed();
        }

        if (stateTransfer == bankTransfer) {
            bankCashTransfer.onShowed();
        }
        if (stateTransfer == scTransfer) {
            scCashTransfer.onShowed();
        }
    }

    @Override
    public void onHide() {
        super.onHide();
        if (stateTransfer == internalTransfer) {
            internalCashTransfer.onHide();
        }

        if (stateTransfer == bankTransfer) {
            bankCashTransfer.onHide();
        }
        if (stateTransfer == scTransfer) {
            scCashTransfer.onHide();
        }
    }

    public void callGetTransDesc(String trans, String key_trans,
                                 INotifier notifier, String key) {

        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(MSTradeAppConfig.controller_GetTransDesc);
        }
        {
            list_key.add("trans");
            list_value.add(trans);
        }
        {
            list_key.add("key");
            list_value.add(key_trans);
        }
        {
            list_key.add("lang");
            list_value.add(AppData.language);
        }
        StaticObjectManager.connectServer.callHttpPostService(GETTRANSDESC,
                notifier, list_key, list_value);
    }

    public void CallGetBankAccList(String paramString, INotifier notifier) {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value
                    .add(getStringResource(R.string.controller_getBankAccList));
        }
        {
            list_key.add("pv_afacctno");
            list_value.add(paramString);
        }
        StaticObjectManager.connectServer.callHttpPostService(GETBANKACCLIST,
                notifier, list_key, list_value);
    }

    public static boolean CallGetCashTransferInfo(String pv_acctno,
                                                  INotifier notifier) {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(MSTradeAppConfig.controller_GetCashTransferInfo);
        }
        {
            list_key.add("pv_acctno");
            list_value.add(pv_acctno);
        }
        StaticObjectManager.connectServer.callHttpPostService(
                GETCASHTRANSFERINFO, notifier, list_key, list_value);
        return true;
    }

    public static boolean CallGetTransferFeeAndTotal(String pv_transferType,
                                                     String pv_amount, String pv_bankid, String pv_fromAcctno,
                                                     String pv_toAcctno, INotifier notifier) {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(MSTradeAppConfig.controller_GetTransferFeeAndTotal);
        }
        {
            list_key.add("pv_transferType");
            list_value.add(pv_transferType);
        }
        {
            list_key.add("pv_amount");
            list_value.add(pv_amount);
        }
        {
            list_key.add("pv_bankid");
            list_value.add(pv_bankid);
        }
        {
            list_key.add("pv_fromAcctno");
            list_value.add(pv_fromAcctno);
        }
        {
            list_key.add("pv_toAcctno");
            list_value.add(pv_toAcctno);
        }
        StaticObjectManager.connectServer.callHttpPostService(
                GETTRANSFERFEEANDTOTAL, notifier, list_key, list_value);
        return true;
    }

    @Override
    protected void process(String key, ResultObj rObj) {
    }

    @Override
    public void onPause() {
        super.onPause();
        mainActivity.getSupportFragmentManager().beginTransaction()
                .remove(internalCashTransfer).commit();
        mainActivity.getSupportFragmentManager().beginTransaction()
                .remove(scCashTransfer).commit();
        mainActivity.getSupportFragmentManager().beginTransaction()
                .remove(bankCashTransfer).commit();
    }

    @Override
    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
        if (stateTransfer == internalTransfer) {
            internalCashTransfer.notifyChangeAcctNo();
        }

        if (stateTransfer == bankTransfer) {
            bankCashTransfer.notifyChangeAcctNo();
        }
        if (stateTransfer == scTransfer) {
            scCashTransfer.notifyChangeAcctNo();
        }
    }

    @Override
    public void getArgument(Object obj) {
        super.getArgument(obj);
        if ((obj instanceof Integer) && !this.isResumed()) {
            selectionSpinner = (Integer) obj;
        }
        if (!this.isResumed()) {
            mainActivity.displayFragment(CashTransfer.class.getName());
        }
    }

    public void onSwitchClickListener() {
        spn_switch.performClick();
    }

    @Override
    public void refresh() {
        super.refresh();

        if (stateTransfer == internalTransfer) {
            internalCashTransfer.refresh();
        }

        if (stateTransfer == bankTransfer) {
            bankCashTransfer.refresh();
        }
    }
}
