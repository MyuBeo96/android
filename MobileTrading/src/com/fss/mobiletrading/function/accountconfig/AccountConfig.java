package com.fss.mobiletrading.function.accountconfig;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.authorizationinfo.AuthorizationInfo;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.AccountInfo;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.Login;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.design.LabelContentLayout;

public class AccountConfig extends AbstractFragment {
    static final String ACCOUNTINFORMATION = "AccountInformationService";
    static final String UPDATEINFO = "SuccessService#UpdateInfo";

    AccountInfo accountInfo;

    LabelContentLayout text_accountinfor_fullname;
    LabelContentLayout text_accountinfor_birthday;
    LabelContentLayout text_accountinfor_gender;
    LabelContentLayout text_accountinfor_IDpepple;
    LabelContentLayout text_accountinfor_CreateDay;
    LabelContentLayout text_accountinfor_CreatePlace;
    LabelContentLayout text_accountinfor_mobile;
    LabelContentLayout edt_accountinfor_homephone;
    LabelContentLayout text_accountinfor_VSDRegistryPlace;
    LabelContentLayout edt_accountinfor_MSBSContactPlace;
    LabelContentLayout edt_accountinfor_Email;
    LabelContentLayout text_accountinfor_CareBy;
    LabelContentLayout text_accountinfor_BrokerPhone;
    LabelContentLayout text_accountinfor_CustomerClass;

    Button btn_accountinfor_Accept;
    Button btn_accountinfor_Cancel;

    Button btn_changePass;
    Button btn_ChangePin;
    Button btn_AuthorizationInfo;

    public static AccountConfig newInstance(MainActivity mActivity) {

        AccountConfig self = new AccountConfig();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.AccountConfig);
        return self;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle bundle) {
        View view = inflater.inflate(R.layout.accountconfig, viewGroup, false);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {

        text_accountinfor_fullname = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_fullname);
        text_accountinfor_birthday = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_birthday);
        text_accountinfor_gender = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_gender);
        text_accountinfor_IDpepple = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_idpeople);
        text_accountinfor_CreateDay = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_idpeoplecreateday);
        text_accountinfor_CreatePlace = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_idpeoplecreateplace);
        text_accountinfor_mobile = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_mobile);
        edt_accountinfor_homephone = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_homephone);
        text_accountinfor_VSDRegistryPlace = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_VSDregistryplace);
        edt_accountinfor_MSBSContactPlace = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_MSBScontactplace);
        edt_accountinfor_Email = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_email);
        text_accountinfor_CareBy = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_careby);
        text_accountinfor_BrokerPhone = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_brokerphone);
        text_accountinfor_CustomerClass = (LabelContentLayout) view
                .findViewById(R.id.lc_thietlaptk_customerclass);

        btn_accountinfor_Accept = (Button) view
                .findViewById(R.id.btn_accountconfig_Accept);
        btn_accountinfor_Cancel = (Button) view
                .findViewById(R.id.btn_accountconfig_Cancel);

        btn_changePass = (Button) view.findViewById(R.id.btn_changepassword);
        btn_ChangePin = (Button) view.findViewById(R.id.btn_changepin);
        btn_AuthorizationInfo = (Button) view
                .findViewById(R.id.btn_authorizationinfo);

        if (StaticObjectManager.loginInfo.IsBroker) {
            edt_accountinfor_Email.setEnabled(false);
            edt_accountinfor_Email.getEditContent().setTextColor(
                    getColorResource(R.color.green_text));
            edt_accountinfor_homephone.setEnabled(false);
            edt_accountinfor_homephone.getEditContent().setTextColor(
                    getColorResource(R.color.green_text));
            edt_accountinfor_MSBSContactPlace.setEnabled(false);
            edt_accountinfor_MSBSContactPlace.getEditContent().setTextColor(
                    getColorResource(R.color.green_text));
            btn_accountinfor_Accept.setVisibility(View.GONE);
            btn_accountinfor_Cancel.setVisibility(View.GONE);
        } else {
            btn_changePass.setVisibility(View.VISIBLE);
            btn_ChangePin.setVisibility(View.VISIBLE);
//			btn_AuthorizationInfo.setVisibility(View.VISIBLE);
        }
        Common.setupUI(view.findViewById(R.id.thietlaptk), getActivity());
    }

    private void initListener() {
        btn_changePass.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mainActivity.displayFragment(ChangePassword.class.getName());
            }
        });
        btn_ChangePin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mainActivity.displayFragment(ChangeTradingPassword.class
                        .getName());
            }
        });
        btn_AuthorizationInfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mainActivity.displayFragment(AuthorizationInfo.class.getName());
            }
        });
        edt_accountinfor_Email
                .setOnFocusChangeListener(new OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus
                                && edt_accountinfor_Email.getEditContent()
                                .length() == 0 && accountInfo != null) {
                            edt_accountinfor_Email
                                    .setText(accountInfo.EmailAdd);
                        }
                    }
                });
        edt_accountinfor_homephone
                .setOnFocusChangeListener(new OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus
                                && edt_accountinfor_homephone.getEditContent()
                                .length() == 0 && accountInfo != null) {
                            edt_accountinfor_homephone
                                    .setText(accountInfo.HomePhone);
                        }
                    }
                });
        edt_accountinfor_MSBSContactPlace
                .setOnFocusChangeListener(new OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus
                                && edt_accountinfor_MSBSContactPlace
                                .getEditContent().length() == 0
                                && accountInfo != null) {
                            edt_accountinfor_MSBSContactPlace
                                    .setText(accountInfo.MSBSAdd);
                        }
                    }
                });

        btn_accountinfor_Accept.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (accountInfo != null) {
                    CallUpdateInfo();
                }
            }
        });
        btn_accountinfor_Cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (accountInfo != null) {
                    edt_accountinfor_Email.setText(accountInfo.EmailAdd);
                    edt_accountinfor_homephone.setText(accountInfo.HomePhone);
                    edt_accountinfor_MSBSContactPlace
                            .setText(accountInfo.MSBSAdd);
                }
            }
        });

    }

    public void onResume() {
        super.onResume();
        AcctnoItem acctno = StaticObjectManager.acctnoItem;
        if (acctno != null && acctno.CUSTODYCD != null) {
            CallAccountInformation(acctno.CUSTODYCD);
        }
    }

    protected void CallAccountInformation(String custodycd) {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value
                    .add(getStringResource(R.string.controller_AccountInformation));
        }
        {
            list_key.add("custodycd");
            list_value.add(custodycd);
        }

        StaticObjectManager.connectServer.callHttpPostService(
                ACCOUNTINFORMATION, this, list_key, list_value);
    }

    private void CallUpdateInfo() {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(getStringResource(R.string.controller_UpdateInfo));
        }
        {
            list_key.add("VSDAdd");
            list_value.add(text_accountinfor_VSDRegistryPlace.getText());
        }
        {
            list_key.add("HomePhone");
            list_value.add(edt_accountinfor_homephone.getText());
        }
        {
            list_key.add("MSBSAdd");
            list_value.add(edt_accountinfor_MSBSContactPlace.getText());
        }
        {
            list_key.add("EmailAdd");
            list_value.add(edt_accountinfor_Email.getText());
        }
        StaticObjectManager.connectServer.callHttpPostService(UPDATEINFO, this,
                list_key, list_value);
        btn_accountinfor_Accept.setEnabled(false);
    }

    public static void CallChangePassword(INotifier notifier, String link,
                                          String oldPass, String newPass, String confirmPass, String key) {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(link);

        }
        {
            list_key.add("OldPassword");
            list_value.add(oldPass);
        }
        {
            list_key.add("NewPassword");
            list_value.add(newPass);
        }
        {
            list_key.add("ConfirmPassword");
            list_value.add(confirmPass);
        }
        StaticObjectManager.connectServer.callHttpPostService(key, notifier,
                list_key, list_value);
    }

    public static void CallChangePasswordAndPin(INotifier notifier, String link,
                                                String oldPass, String newPass, String confirmPass, String oldPin, String newPin, String confirmPin, String key) {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(link);
        }
        {
            list_key.add("OldPassword");
            list_value.add(oldPass);
        }
        {
            list_key.add("NewPassword");
            list_value.add(newPass);
        }
        {
            list_key.add("ConfirmPassword");
            list_value.add(confirmPass);
        }
        {
            list_key.add("OldTradingPassword");
            list_value.add(oldPin);
        }
        {
            list_key.add("NewTradingPassword");
            list_value.add(newPin);
        }
        {
            list_key.add("ConfirmTradingPassword");
            list_value.add(confirmPin);
        }
        StaticObjectManager.connectServer.callHttpPostService(key, notifier,
                list_key, list_value);
    }

    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
        AcctnoItem acctno = StaticObjectManager.acctnoItem;
        if (acctno != null && acctno.CUSTODYCD != null) {
            CallAccountInformation(acctno.CUSTODYCD);
        }
    }

    @Override
    protected void process(String key, ResultObj rObj) {

        switch (key) {
            case UPDATEINFO:
                accountInfo.MSBSAdd = edt_accountinfor_MSBSContactPlace.getText();
                accountInfo.EmailAdd = edt_accountinfor_Email.getText();
                accountInfo.HomePhone = edt_accountinfor_homephone.getText();
                showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);

                break;
            case ACCOUNTINFORMATION:
                if (rObj.obj != null) {
                    accountInfo = ((AccountInfo) rObj.obj);
                    displayAccountInformation();
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
            case UPDATEINFO:
                btn_accountinfor_Accept.setEnabled(true);
                break;
            case ACCOUNTINFORMATION:
                break;

            default:
                break;
        }
    }

    private void displayAccountInformation() {
        if (accountInfo != null) {
            text_accountinfor_fullname.setText(accountInfo.FullName);
            text_accountinfor_birthday.setText(accountInfo.Birthday);
            text_accountinfor_gender.setText(accountInfo.Gender);
            text_accountinfor_IDpepple.setText(accountInfo.PasportNumber);
            text_accountinfor_CreateDay.setText(accountInfo.IDCardDate);
            text_accountinfor_CreatePlace.setText(accountInfo.IDPlace);
            text_accountinfor_mobile.setText(accountInfo.Mobile1);
            edt_accountinfor_homephone.setText(accountInfo.HomePhone);
            text_accountinfor_VSDRegistryPlace.setText(accountInfo.VSDAdd);
            edt_accountinfor_MSBSContactPlace.setText(accountInfo.MSBSAdd);
            edt_accountinfor_Email.setText(accountInfo.EmailAdd);
            text_accountinfor_CareBy.setText(accountInfo.AgentName);
            text_accountinfor_BrokerPhone.setText(accountInfo.AgentPhone);
            text_accountinfor_CustomerClass.setText(accountInfo.Class);
        }
    }

}
