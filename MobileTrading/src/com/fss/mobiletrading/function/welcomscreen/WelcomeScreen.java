package com.fss.mobiletrading.function.welcomscreen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.connector.ConnectServer;
import com.fss.mobiletrading.function.AppConfig;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.Login;
import com.tcscuat.mobiletrading.Login_SSO;
import com.tcscuat.mobiletrading.MSTradeAppConfig;
import com.tcscuat.mobiletrading.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class WelcomeScreen extends Activity implements INotifier {
    static final String GETCONFIGSERVER = "GetConfigServerService#GETCONFIGSERVER";
    Dialog dialog_ShowError;
    TextView tv_Title;
    TextView tv_message;
    TextView tv_Positive;
    boolean hasconfig = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        StaticObjectManager.connectServer = new ConnectServer();
        callGetConfigServer(this, GETCONFIGSERVER);
        final Handler handler = new Handler();
        createDialogMessage();
    }

    @Override
    public void notifyChangeAcctNo() {

    }

    @Override
    public void notifyResult(String key, ResultObj rObj) {
        switch (key) {
            case GETCONFIGSERVER:
                switch (rObj.EC) {
                    case -1:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_connect_server));
                        break;
                    case -2:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_timeout));
                        break;
                    case -3:
                        showDialogMessage(getResources().getString(R.string.thong_bao),
                                rObj.EM);
                        break;
                    case -4:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_processjson));
                        break;
                    case -5:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_NoData));
                        break;
                    default:

                        AppConfig appConfig = AppConfig.getInstance();
                        MyJsonObject DT = (MyJsonObject) rObj.obj;
                        try {
                            if (DT.get("enableSSO").equals("true")) {
                                appConfig.setEnableSSO(true);
                            } else if (DT.get("enableSSO").equals("false")) {
                                appConfig.setEnableSSO(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hasconfig = true;
                        //toannds comment
                        startLogin(AppConfig.getInstance().enableSSO);
                        //startLogin(true);
                        break;
                }
                break;

        }
    }

    public static void callGetConfigServer(INotifier notifier, String key) {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(MSTradeAppConfig.getConfigServer);
        }
        StaticObjectManager.connectServer.callHttpPostService(key, notifier,
                list_key, list_value);
    }

    private void startLogin(Boolean enablePush) {
        if (enablePush) {
            startActivity(new Intent(this, Login_SSO.class));
        } else {
            startActivity(new Intent(this, Login.class));
        }
        finish();
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
        tv_Title.setText(getString(R.string.thong_bao));
        tv_message.setText(getString(R.string.msg_LoiKetNoi));
        tv_Positive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_ShowError.dismiss();
                finish();
//                startActivity(getIntent());
            }
        });
    }

    public void showDialogMessage(String title, String msg) {
        if (title != null) {
            tv_Title.setText(title);
        }
        if (msg != null) {
            tv_message.setText(msg);
        }
        dialog_ShowError.show();
    }
}
