package com.msbuat.mobiletrading;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.FontsOverride;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.ChooseAfacctno;
import com.fss.mobiletrading.function.accountconfig.AccountConfig;
import com.fss.mobiletrading.function.accountconfig.ChangePassword;
import com.fss.mobiletrading.function.accountconfig.ChangeTradingPassword;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.jsonservice.Error;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.LoginItem;
import com.fss.mobiletrading.object.ResultObj;
import com.google.android.gms.plus.model.people.Person;
import com.msbuat.mobiletrading.design.LabelContentLayout;

import org.apache.http.cookie.Cookie;

import java.util.List;

/**
 * Created by giang.ngo on 11/29/2017.
 */

public class ResetPassActivity extends FragmentActivity implements INotifier {
    static final String CHANGEPASSWORD = "SuccessService#1";

    Button btn_ChangePass;
    Button btn_Cancel;
    LabelContentLayout edt_ConfirmPass;
    LabelContentLayout edt_NewPass;
    LabelContentLayout edt_OldPass;

    static final String CHANGEPIN = "SuccessService#2";
    Button btn_ChangePin;
    Button btn_Pin_Cancel;
    LabelContentLayout edt_ConfirmPin;
    LabelContentLayout edt_NewPin;
    LabelContentLayout edt_OldPin;

    Dialog dialog_ShowError;
    TextView tv_message;
    TextView tv_Title;
    TextView tv_Positive;
    TextView tv_Negative;

    Button btn_ChangePassAndPin;
    ImageButton btn_checkbox;
    ImageButton btn_back;

    View v_sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBaseContext().getResources().updateConfiguration(Login.newConfig,
                getBaseContext().getResources().getDisplayMetrics());
        FontsOverride.setDefaultFont(this, "DEFAULT", "AvenirNext_Regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE",
                "AvenirNext_Regular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "AvenirNext_Regular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF",
                "AvenirNext_Regular.ttf");

        DeviceProperties.isTablet = Common.isTablet(getApplicationContext());
        setContentView(R.layout.resetpassactivity);
        if (DeviceProperties.isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        createDialogMessage();
        initView();
        initListener();
    }



//    public static ChangePassword newInstance(MainActivity mActivity) {
//        ChangePassword self = new ChangePassword();
//        self.mainActivity = mActivity;
//        if (StaticObjectManager.loginInfo.IsBroker) {
//            self.TITLE = mActivity.getStringResource(R.string.DoiMatKhau);
//        } else {
//            self.TITLE = mActivity
//                    .getStringResource(R.string.DoiMatKhauDangNhap);
//        }
//        return self;
//    }




    private void initView() {

        edt_OldPass = ((LabelContentLayout) findViewById(R.id.edt_thietlaptk_OldPass));
        edt_NewPass = ((LabelContentLayout) findViewById(R.id.edt_thietlaptk_NewPass));
        edt_ConfirmPass = ((LabelContentLayout) findViewById(R.id.edt_thietlaptk_ConfirmPass));
        btn_Cancel = (Button) findViewById(R.id.btn_HuyPass);
        btn_ChangePass = ((Button)
                findViewById(R.id.btn_thietlaptk_ChangePass));
        btn_Cancel.setVisibility(View.GONE);
        btn_ChangePass.setVisibility(View.GONE);
        Common.setupUI(findViewById(R.id.changepassword),this);


        btn_ChangePin = (Button) findViewById(R.id.btn_thietlaptk_ChangePin);
        btn_ChangePin.setVisibility(View.GONE);
        btn_Pin_Cancel = (Button) findViewById(R.id.btn_HuyPin);
        btn_Pin_Cancel.setVisibility(View.GONE);
        v_sp = findViewById(R.id.viewsp);
//        v_sp.setVisibility(View.GONE);
        edt_OldPin = ((LabelContentLayout) findViewById(R.id.edt_thietlaptk_OldPin));
        edt_NewPin = ((LabelContentLayout) findViewById(R.id.edt_thietlaptk_NewPin));
        edt_ConfirmPin = ((LabelContentLayout) findViewById(R.id.edt_thietlaptk_ConfirmPin));
        Common.setupUI(findViewById(R.id.changetradingpassword),this);

        btn_ChangePassAndPin = (Button) findViewById(R.id.btn_ChangePassAndPin);
        btn_checkbox = (ImageButton) findViewById(R.id.btn_showpassword);
        btn_back = (ImageButton) findViewById(R.id.btn_Back);
    }
    private void createDialogMessage() {
        dialog_ShowError = new Dialog(this);
        dialog_ShowError.setCancelable(true);
        dialog_ShowError.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_ShowError.setContentView(R.layout.alert_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog_ShowError.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog_ShowError.getWindow().setAttributes(lp);
        dialog_ShowError.getWindow()
                .setBackgroundDrawable(new ColorDrawable(0));

        tv_message = (TextView) dialog_ShowError
                .findViewById(R.id.text_alertdialog_message);
        tv_Title = (TextView) dialog_ShowError
                .findViewById(R.id.text_alertdialog_title);
        tv_Positive = (TextView) dialog_ShowError
                .findViewById(R.id.text_alertdialog_possitive);
        tv_Negative = (TextView) dialog_ShowError
                .findViewById(R.id.text_alertdialog_negative);
        tv_Positive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_ShowError.dismiss();
            }
        });
    }
    public void showDialogMessage(String title, String msg, final View.OnClickListener onClickListener) {
        if (title != null) {
            tv_Title.setText(title);
        }
        if (msg != null) {
            tv_message.setText(msg);
        }
        if (onClickListener != null) {
            tv_Positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v);
                    dialog_ShowError.dismiss();
                }
            });
        }else {
            tv_Positive.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog_ShowError.dismiss();
                }
            });
        }
        dialog_ShowError.show();
    }
    private void initListener() {
        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_checkbox.setSelected(!btn_checkbox.isSelected());
                if (btn_checkbox.isSelected()) {
                    edt_NewPass.getEditContent().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    edt_ConfirmPass.getEditContent().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    edt_NewPin.getEditContent().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    edt_ConfirmPin.getEditContent().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    edt_NewPass.getEditContent().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edt_ConfirmPass.getEditContent().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edt_NewPin.getEditContent().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edt_ConfirmPin.getEditContent().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        btn_ChangePassAndPin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String confirmPass = String.valueOf(edt_ConfirmPass.getEditContent());
                String newPass= String.valueOf(edt_NewPass.getEditContent());
                String confirmPin = String.valueOf(edt_ConfirmPin.getEditContent());
                String newPin= String.valueOf(edt_NewPin.getEditContent());
                if (edt_ConfirmPass.getEditContent().length() == 0
                        || edt_NewPass.getEditContent().length() == 0
                        || edt_OldPass.getEditContent().length() == 0
                        || edt_ConfirmPin.getEditContent().length() == 0
                        || edt_NewPin.getEditContent().length() == 0
                        || edt_OldPin.getEditContent().length() == 0) {
                    showDialogMessage(getString(R.string.thong_bao),getString(R.string.ChuaNhapDL),null);
                }
                else if(edt_NewPass.getText().toString().trim().length()<6
                        ||edt_ConfirmPass.getText().toString().trim().length()<6
                        ||edt_NewPin.getText().toString().trim().length()<6
                        ||edt_ConfirmPin.getText().toString().trim().length()<6){
                    showDialogMessage(getString(R.string.thong_bao),getString(R.string.ChuaNhapDungDL),null);}
                else  {
                    AccountConfig.CallChangePasswordAndPin(
                            ResetPassActivity.this,
                            MSTradeAppConfig.controller_ChangeTradingPasswordAndPin,
                            edt_OldPass.getText().toString(), edt_NewPass
                                    .getText().toString(), edt_ConfirmPass
                                    .getText().toString(),edt_OldPin.getText().toString(), edt_NewPin
                                    .getText().toString(), edt_ConfirmPin
                                    .getText().toString(), CHANGEPIN);
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        clearField();
    }

    protected void clearField() {
        edt_OldPass.setText(StringConst.EMPTY);
        edt_NewPass.setText(StringConst.EMPTY);
        edt_ConfirmPass.setText(StringConst.EMPTY);
        edt_OldPin.setText(StringConst.EMPTY);
        edt_NewPin.setText(StringConst.EMPTY);
        edt_ConfirmPin.setText(StringConst.EMPTY);
    }

    @Override
    public void notifyChangeAcctNo() {

    }

    @Override
    public void notifyResult(String key, ResultObj rObj) {
        btn_ChangePass.setEnabled(true);
        switch (key) {
            case CHANGEPASSWORD:
                switch (rObj.EC) {
                    case -1:
                        showDialogMessage(getString(R.string.Loi),
                                getString(R.string.error_connect_server), null);
                        break;
                    case -2:
                        showDialogMessage(getString(R.string.Loi),
                                getString(R.string.error_timeout), null);
                        break;
                    case -3:
                        showDialogMessage(getString(R.string.thong_bao),
                                rObj.EM, null);

                        break;
                    case -4:
                        showDialogMessage(getString(R.string.Loi),
                                getString(R.string.error_processjson), null);
                        break;
                    case -5:
                        showDialogMessage(getString(R.string.Loi),
                                getString(R.string.error_NoData), null);
                        break;

                    default:
                        showDialogMessage(getString(R.string.thong_bao),rObj.EM, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                });
                        break;
                }
                break;
            case CHANGEPIN:
                switch (rObj.EC) {
                    case -1:
                        showDialogMessage(getString(R.string.Loi),
                                getString(R.string.error_connect_server), null);
                        break;
                    case -2:
                        showDialogMessage(getString(R.string.Loi),
                                getString(R.string.error_timeout), null);
                        break;
                    case -3:
                        showDialogMessage(getString(R.string.thong_bao),
                                rObj.EM, null);

                        break;
                    case -4:
                        showDialogMessage(getString(R.string.Loi),
                                getString(R.string.error_processjson), null);
                        break;
                    case -5:
                        showDialogMessage(getString(R.string.Loi),
                                getString(R.string.error_NoData), null);
                        break;

                    default:
                        showDialogMessage(getString(R.string.thong_bao),rObj.EM, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
