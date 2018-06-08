package com.fscuat.mobiletrading;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.FontsOverride;
import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.connector.ConnectServer;
import com.fss.mobiletrading.function.AppConfig;
import com.fss.mobiletrading.function.AppData;
import com.fss.mobiletrading.function.ChooseAfacctno;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.LoginItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.StockItem;
import com.fss.mobiletrading.service.LoginService;
import com.fscuat.mobiletrading.design.CustomLoadingDialog;
import com.fscuat.mobiletrading.design.MyButton;
import com.fscuat.mobiletrading.design.MyContextMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_SSO extends FragmentActivity implements INotifier {

    static final String GETLINKGUIDE = "GetLinkGuideJsonProcess#GETLINKGUIDE";
    static final String GETLINKGUIDEBROKER = "GetLinkGuideJsonProcess#GETLINKGUIDEBROKER";
    static final String GETLINKPAYMENTGUIDE = "GetLinkGuideJsonProcess#GETLINKPAYMENTGUIDE";
    static final String GETACCOUNTDEMO = "GetLinkGuideJsonProcess#GETACCOUNTDEMO";
    static final String GETERRORMESSAGE = "GetTransDesc#GETERRORMESSAGE";
    static final String LOGINSERVICE = "LoginServiceJsonProcess#LOGINSERVICE";
    static final String GETACCOUNTINFOSERVICE = "LoginServiceJsonProcess#GETACCOUNTINFOSERVICE";
    static final String ATSERVICE = "ATService";
    static final String RESETPASS = "SuccessService#RESETPASS";

//    public final static String LOCALE_VI_VN = "vi-VN";
//    public final static String LOCALE_VI = "vi";
//    public final static String LOCALE_EN = "en";

    public static String MobileTradingPrefs = "MobileTradingPrefs";
    public static String cus_DeviceToken = "cus_DeviceToken";

    public static String brok_DeviceToken = "brok_DeviceToken";
    /**
     * language nhận 1 trong 2 giá trị Login.LOCALE_VI_VN hoặc Login.LOCALE_EN
     */
//    public static String language = LOCALE_VI_VN;
    public int versionCode = 0;
    ImageView logo;

    Dialog dialog_forgetpass;
    EditText edt_forgetpassword_account;
    EditText edt_forgetpassword_idpeople;
    EditText edt_forgetpassword_phone;
    EditText edt_forgetpassword_email;

    MyButton btn_Login;
    Button btn_ENlang;
    Button btn_VIlang;
    Button btn_ZHlang;
    MyButton btn_forgetpassword_reset;
    Button btn_forgetpassword_close;

    TextView tv_ForgerPassword;
    FrameLayout lay_FindBranch;
    TextView tv_scinfo;
    TextView tv_Guide;
    TextView tv_FindBranch;
    TextView tv_chooseLang;
    ImageButton checkBox;
    View line_pin;
    LinearLayout lay_pin;
    LinearLayout webviewContainer;

    Dialog dialog_ShowError;
    TextView tv_message;
    TextView tv_Title;
    TextView tv_Positive;
    TextView tv_Negative;
    MyContextMenu contextMenu_Guide;
    CustomLoadingDialog loading;


    public static Configuration newConfig;
    File file, fileAT, logFile;
    Intent mapIntent;
    LoginOverWeb loginOverWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(this, "DEFAULT", "AvenirNext_Regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE",
                "AvenirNext_Regular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "AvenirNext_Regular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF",
                "AvenirNext_Regular.ttf");

        DeviceProperties.isTablet = Common.isTablet(getApplicationContext());
        // isTablet = false;
        if (DeviceProperties.isTablet) {
            setContentView(R.layout.t_login_sso);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setContentView(R.layout.login_sso);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        StaticObjectManager.initSystemBeforeLogin(Login_SSO.this);
//        ConnectServer.checkConnection(getApplicationContext());

        initData();
        initView();
        initListener();
    }

    private void initData() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (newConfig == null) {
            newConfig = new Configuration();
            newConfig.locale = new Locale(AppData.LOCALE_VI);
        }

        getBaseContext().getResources().updateConfiguration(newConfig,
                getBaseContext().getResources().getDisplayMetrics());
        file = new File(Environment.getExternalStorageDirectory()
                + "/MTrading.txt");
        fileAT = new File(Environment.getExternalStorageDirectory() + "/AT.txt");
        logFile = new File(Environment.getExternalStorageDirectory()
                + "/LogFile.txt");
        readLogcat();
        CreateDialogResetPass();
        mapIntent = new Intent(this, MapActivity.class);
        createDialogMessage();
        contextMenu_Guide = new MyContextMenu(this);
        List<String> listGuide = new ArrayList<String>();
        listGuide.add(getResources().getString(R.string.HDKHSD));
        listGuide.add(getResources().getString(R.string.HDMGSD));
        listGuide.add(getResources().getString(R.string.HuongDanNopTien));
        contextMenu_Guide.setChoises(listGuide);
        final View activityRootView = findViewById(R.id.login);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int heightDiff = activityRootView.getRootView()
                                .getHeight() - activityRootView.getHeight();
                        Log.i("Login",
                                activityRootView.getRootView().getHeight()
                                        + " " + activityRootView.getHeight()
                                        + " " + heightDiff);
                        if (heightDiff > 100) {

                        }
                    }
                });
        if (DeviceProperties.isTablet) {
            LoginService.CallGetLinkTabletGuide(this, GETLINKGUIDE);
            LoginService.CallGetLinkTabletGuideBroker(this, GETLINKGUIDEBROKER);
        } else {
            LoginService.CallGetLinkGuide(this, GETLINKGUIDE);
            LoginService.CallGetLinkGuideBroker(this, GETLINKGUIDEBROKER);
        }

        LoginService.CallGetLinkPaymentGuide(this, GETLINKPAYMENTGUIDE);
    }

    private void initView() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        logo = (ImageView) findViewById(R.id.img_login_logo);
        btn_Login = (MyButton) findViewById(R.id.btn_login_Login);
        tv_chooseLang = (TextView) findViewById(R.id.lbl_login_changepassword);
        btn_ENlang = (Button) findViewById(R.id.btn_login_ENlang);
        btn_VIlang = (Button) findViewById(R.id.btn_login_VIlang);
        btn_ZHlang = (Button) findViewById(R.id.btn_login_ZHlang);
        tv_ForgerPassword = (TextView) findViewById(R.id.lbl_login_forgetpassword);
        lay_FindBranch = (FrameLayout) findViewById(R.id.lay_login_findbranch);
        tv_FindBranch = (TextView) findViewById(R.id.lbl_login_findbranch);
        tv_Guide = (TextView) findViewById(R.id.lbl_login_guide);
        checkBox = (ImageButton) findViewById(R.id.img_login_remember);
        tv_scinfo = (TextView) findViewById(R.id.lbl_login_stockcompanyinfo);
        line_pin = findViewById(R.id.line_login_pin);
        lay_pin = (LinearLayout) findViewById(R.id.lay_login_pin);
        webviewContainer = (LinearLayout) findViewById(R.id.ll_loginsso_container);

        if (loading == null) {
            loading = new CustomLoadingDialog(this);
        }
        updateText();
        if (AppData.LOCALE_EN.equals(AppData.language)) {
            btn_ENlang.setSelected(true);
            btn_VIlang.setSelected(false);
            btn_ZHlang.setSelected(false);
        }
        else if(AppData.LOCALE_ZH.equals(AppData.language)){
            btn_ENlang.setSelected(false);
            btn_VIlang.setSelected(false);
            btn_ZHlang.setSelected(true);
        }
        else {
            btn_ENlang.setSelected(false);
            btn_VIlang.setSelected(true);
            btn_ZHlang.setSelected(false);
        }
        Common.setupUI(findViewById(R.id.login), this);
    }

    int dem = 0;

    private void initListener() {
        // tv_chooseLang.setOnTouchListener(new OnTouchListener() {
        //
        // @Override
        // public boolean onTouch(View v, MotionEvent event) {
        // dem++;
        // if (dem >= 6) {
        // MSTradeAppConfig.address_server =
        // MSTradeAppConfig.address_server_msb;
        // Toast.makeText(Login.this, "da chuyen sang msb server",
        // Toast.LENGTH_LONG).show();
        // }
        // return false;
        // }
        // });

        btn_Login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                webviewContainer.setVisibility(View.VISIBLE);
            }
        });

        contextMenu_Guide
                .setOnItemSelectedListener(new MyContextMenu.OnItemSelectedListener() {

                    @Override
                    public void onItemSelect(Object obj, int position) {
                        if (position == 0) {
                            // hướng dẫn khách hàng sử dụng ms-trade
                            boolean response = StaticObjectManager.callAnotherAppManager
                                    .goToUrl(MSTradeAppConfig.link_Guide);
                            if (!response) {
                                showDialogMessage(
                                        getResources().getString(
                                                R.string.thong_bao),
                                        getResources().getString(
                                                R.string.KhongMoDuocLink));
                            }
                        } else if (position == 1) {
                            // hướng dẫn môi giới sử dụng ms-trade
                            boolean response = StaticObjectManager.callAnotherAppManager
                                    .goToUrl(MSTradeAppConfig.link_Guide_Broker);
                            if (!response) {
                                showDialogMessage(
                                        getResources().getString(
                                                R.string.thong_bao),
                                        getResources().getString(
                                                R.string.KhongMoDuocLink));
                            }
                        } else if (position == 2) {
                            // hướng dẫn nộp tiền
                            boolean response = StaticObjectManager.callAnotherAppManager
                                    .goToUrl(MSTradeAppConfig.link_PaymentGuide);
                            if (!response) {
                                showDialogMessage(
                                        getResources().getString(
                                                R.string.thong_bao),
                                        getResources().getString(
                                                R.string.KhongMoDuocLink));
                            }
                        }
                    }
                });

        btn_ENlang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!btn_ENlang.isSelected()) {
                    changeLanguage(AppData.LOCALE_EN);
                    btn_ENlang.setSelected(true);
                    btn_VIlang.setSelected(false);
                    btn_ZHlang.setSelected(false);
                }
            }
        });
        btn_VIlang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!btn_VIlang.isSelected()) {
                    changeLanguage(AppData.LOCALE_VI);
                    btn_ENlang.setSelected(false);
                    btn_VIlang.setSelected(true);
                    btn_ZHlang.setSelected(false);
                }
            }
        });
        btn_ZHlang.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!btn_ZHlang.isSelected()){
                    changeLanguage(AppData.LOCALE_ZH);
                    btn_ENlang.setSelected(false);
                    btn_VIlang.setSelected(false);
                    btn_ZHlang.setSelected(true);
                }
            }
        });

        tv_ForgerPassword.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog_forgetpass != null) {
                    edt_forgetpassword_account.getText().clear();
                    edt_forgetpassword_idpeople.getText().clear();
                    edt_forgetpassword_phone.getText().clear();
                    edt_forgetpassword_email.getText().clear();

                    isResetPassLoaded();
                    dialog_forgetpass.show();
                }
            }
        });
        lay_FindBranch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(mapIntent);
            }
        });
        tv_Guide.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                contextMenu_Guide.show();
            }
        });
    }

    public void clearNotification(Context context) {
        NotificationManager manager = (NotificationManager) context
                .getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    protected void CreateDialogResetPass() {

        dialog_forgetpass = new Dialog(this, R.style.cusDialog);
        dialog_forgetpass.setCancelable(false);
        dialog_forgetpass.setTitle(getResources().getString(
                R.string.QuenMatKhau));
        dialog_forgetpass.setContentView(R.layout.forget_password);
        WindowManager.LayoutParams lp = dialog_forgetpass.getWindow()
                .getAttributes();
        if (DeviceProperties.isTablet) {
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog_forgetpass.getWindow().setBackgroundDrawableResource(
                    R.drawable.bg_dialogfragment_bottom);
        } else {
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog_forgetpass.getWindow().setBackgroundDrawable(
                    new ColorDrawable(0));
        }

        edt_forgetpassword_account = (EditText) dialog_forgetpass
                .findViewById(R.id.edt_forgetpassword_account);
        edt_forgetpassword_idpeople = (EditText) dialog_forgetpass
                .findViewById(R.id.edt_forgetpassword_idpeople);
        edt_forgetpassword_phone = (EditText) dialog_forgetpass
                .findViewById(R.id.edt_forgetpassword_phone);
        edt_forgetpassword_email = (EditText) dialog_forgetpass
                .findViewById(R.id.edt_forgetpassword_email);
        btn_forgetpassword_reset = (MyButton) dialog_forgetpass
                .findViewById(R.id.btn_forgetpassword_reset);
        btn_forgetpassword_close = (Button) dialog_forgetpass
                .findViewById(R.id.btn_forgetpassword_back);
        Common.setupUI(dialog_forgetpass.findViewById(R.id.forget_password),
                dialog_forgetpass);
        btn_forgetpassword_reset.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CallResetPass(edt_forgetpassword_account.getText().toString().toUpperCase(),
                        edt_forgetpassword_idpeople.getText().toString(),
                        edt_forgetpassword_phone.getText().toString(),
                        edt_forgetpassword_email.getText().toString());
            }
        });
        btn_forgetpassword_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_forgetpass.cancel();
            }
        });

    }

    public void changeLanguage(String lang) {
        if (AppData.LOCALE_VI.equals(lang)) {
            AppData.language = AppData.LOCALE_VI_VN;
        }
        else if(AppData.LOCALE_ZH.equals(lang)){
            AppData.language=AppData.LOCALE_ZH;
        }
        else {
            AppData.language = AppData.LOCALE_EN;
        }
        newConfig.locale = new Locale(lang);
        onConfigurationChanged(newConfig);
        Intent refresh = new Intent(this, Login_SSO.class);
        finish();
        startActivity(refresh);
    }

    private void updateText() {
        btn_Login.setText(getResources().getString(R.string.login_btn_Login));
        tv_ForgerPassword.setText(getResources()
                .getString(R.string.QuenMatKhau));
        tv_FindBranch.setText(getResources()
                .getString(R.string.ChiNhanhGanNhat));
        tv_Guide.setText(getResources().getString(R.string.HuongDan));
        tv_chooseLang
                .setText(getResources().getString(R.string.LuaChonNgonNgu));
        tv_scinfo.setText(getResources().getString(R.string.stockcompany_info));
        if (AppData.language.equals(AppData.LOCALE_EN)) {
            logo.setImageResource(R.drawable.ic_fsc_logo);
        }
    }
    private void refeshWebview(){
        loginOverWeb = new LoginOverWeb();
        getFragmentManager().beginTransaction().replace(R.id.ll_loginsso_container, loginOverWeb).addToBackStack(null).commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        btn_Login.setLoading(false);
        refeshWebview();
    }

    private void CallAT() {
        loading.setContentText(getString(R.string.LoadThongTinThiTruong));
        StaticObjectManager.connectServer.callGetATService(ATSERVICE,
                Login_SSO.this, getResources().getString(R.string.controller_AT));
    }

    // private void CallChangeLanguage() {
    // CallWebService.ChangeLanguage(3, getResources().getString(2131034493),
    // language, this);
    // }


    private void CallResetPass(String custodycd, String personId, String phone,
                               String email) {
        boolean rs = LoginService.CallResetPass(custodycd, personId, phone,
                email, AppData.language, this, RESETPASS);
        isResetPassLoading();
    }

    @Override
    public void notifyChangeAcctNo() {

    }

    @Override
    public void notifyResult(String key, ResultObj rObj) {

        switch (key) {
            case GETACCOUNTINFOSERVICE:
                switch (rObj.EC) {
                    case -1:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_connect_server));
                        btn_Login.setLoading(false);
                        loading.dismiss();
                        refeshWebview();
                        break;
                    case -2:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_timeout));
                        btn_Login.setLoading(false);
                        loading.dismiss();
                        refeshWebview();
                        break;
                    case -3:
                        showDialogMessage(getResources().getString(R.string.thong_bao),
                                rObj.EM);
                        btn_Login.setLoading(false);
                        loading.dismiss();
                        refeshWebview();
                        break;
                    case -4:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_processjson));
                        btn_Login.setLoading(false);
                        loading.dismiss();
                        refeshWebview();
                        break;
                    case -5:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_NoData));
                        btn_Login.setLoading(false);
                        loading.dismiss();
                        refeshWebview();
                        break;

                    default:
                        StaticObjectManager.loginInfo = (LoginItem) rObj.obj;
                        if (StaticObjectManager.loginInfo.IsBroker) {
                            StaticObjectManager.defaultbrokeracctno = new AcctnoItem(
                                    MSTradeAppConfig.USERNAME_DEFAULT);
                            StaticObjectManager.acctnoItem = StaticObjectManager.defaultbrokeracctno;
                        } else {
                            int currentAcctno = StaticObjectManager.getPositionAcctno();
                            ChooseAfacctno.selectedPosition = currentAcctno;
                            StaticObjectManager.acctnoItem = StaticObjectManager.loginInfo.contractList
                                    .get(currentAcctno);
                        }
                        String[] strArr = Common.readFile(fileAT);
                        if (strArr != null
                                && strArr[0] != null
                                && strArr[1] != null
                                ) {
                            String txDate = strArr[0];
                            String listStock = strArr[1];
                            StaticObjectManager.listStock = readAT(listStock);
                            loading.dismiss();
                            if (txDate.equals(StaticObjectManager.loginInfo.TxDateString)
                                    && StaticObjectManager.listStock.size() > 0) {
                                startMainActivity();
                                return;
                            }
                        }
                        CallAT();
                        break;
                }
                break;

            case ATSERVICE:
                switch (rObj.EC) {
                    case -1:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_connect_server));
                        btn_Login.setLoading(false);
                        loading.dismiss();
                        break;
                    case -2:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_timeout));
                        btn_Login.setLoading(false);
                        loading.dismiss();
                        break;
                    case -3:
                        showDialogMessage(getResources().getString(R.string.thong_bao),
                                rObj.EM);
                        btn_Login.setLoading(false);
                        loading.dismiss();
                        break;
                    case -4:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_processjson));
                        btn_Login.setLoading(false);
                        loading.dismiss();
                        break;
                    case -5:
                        showDialogMessage(getResources().getString(R.string.Loi),
                                getResources().getString(R.string.error_NoData));
                        btn_Login.setLoading(false);
                        loading.dismiss();
                        break;

                    default:
                        loading.dismiss();
                        Common.writeFile(fileAT, StaticObjectManager.loginInfo.TxDateString
                                + "\n" + (String) rObj.obj);
                        StaticObjectManager.listStock = readAT((String) rObj.obj);
                        startMainActivity();
                        break;
                }

                break;
            case RESETPASS:
                isResetPassLoaded();
                if (rObj.EC == 0) {
                    showDialogMessage(getResources().getString(R.string.thong_bao),
                            rObj.EM);
                    dialog_ShowError.setOnDismissListener(new OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (dialog_forgetpass != null
                                    && dialog_forgetpass.isShowing()) {
                                dialog_forgetpass.dismiss();
                            }
                            dialog_ShowError.setOnDismissListener(null);
                        }
                    });
                } else {
                    showDialogMessage(getResources().getString(R.string.thong_bao),
                            rObj.EM);

                }
                break;
            case GETERRORMESSAGE:
                if (rObj != null) {
                    showDialogMessage(getResources().getString(R.string.thong_bao),
                            rObj.EM);

                }
                break;
            case GETACCOUNTDEMO:
                if (rObj != null) {
                    if (rObj.obj != null) {
                        String str = ((String) rObj.obj);
                        String[] demoAccountInfo = str.split("\\|");
                        if (demoAccountInfo.length >= 3) {
                            MSTradeAppConfig.username_demo = demoAccountInfo[0]
                                    .trim();
                            MSTradeAppConfig.pass_demo = demoAccountInfo[1].trim();
                            MSTradeAppConfig.pin_demo = demoAccountInfo[2].trim();
                        }
                    } else {
                    }
                }
                break;
            case GETLINKGUIDE:
                if (rObj != null) {
                    if (rObj.obj != null) {
                        MSTradeAppConfig.link_Guide = (String) rObj.obj;
                    }
                }
                break;
            case GETLINKGUIDEBROKER:
                if (rObj != null) {
                    if (rObj.obj != null) {
                        MSTradeAppConfig.link_Guide_Broker = (String) rObj.obj;
                    }
                }
                break;
            case GETLINKPAYMENTGUIDE:
                if (rObj != null) {
                    if (rObj.obj != null) {
                        MSTradeAppConfig.link_PaymentGuide = (String) rObj.obj;
                    }
                }
                break;

            default:
                break;
        }
    }

    private void ClearData() {
    }

    private void startMainActivity() {
        if (DeviceProperties.isTablet) {
            startActivity(new Intent(this, MainActivity_Tablet.class));
        } else {
            Intent intentMobile = new Intent(this, MainActivity_Mobile.class);
            startActivity(intentMobile);
        }
        ClearData();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    private void isResetPassLoading() {
        try {
            btn_forgetpassword_reset.setLoading(true);
            btn_forgetpassword_close.setEnabled(false);
        } catch (Exception e) {
        }

    }

    private void isResetPassLoaded() {
        try {
            btn_forgetpassword_reset.setLoading(false);
            btn_forgetpassword_close.setEnabled(true);
        } catch (Exception e) {
        }

    }

    private List<StockItem> readAT(String DT) {

        List<StockItem> listAT = new ArrayList<StockItem>();
        try {
            MyJsonArray array = new MyJsonArray(DT);
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    listAT.add(new StockItem(array.getJSONObject(i).getString(
                            "label"),
                            array.getJSONObject(i).getString("value"), array
                            .getJSONObject(i).getString("m")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listAT;
    }

    public void readLogcat() {
        StringBuilder log = null;
        try {
            Process process = Runtime.getRuntime()
                    .exec("logcat -d -v time *:E");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            log = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append("\n" + line);
            }
            Common.writeFile(logFile, log.toString());
        } catch (IOException e) {
        }
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
        tv_Positive.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_ShowError.dismiss();
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

    private String getVersionCode() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            versionCode = pInfo.versionCode;
            return "A." + String.valueOf(versionCode);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "A.1";
    }

    public void handleLoginSSOSuccessfully() {
        webviewContainer.setVisibility(View.GONE);

        if (StaticObjectManager.sessionCookie == null) {
            Toast.makeText(this, getString(R.string.DangNhapThatBai), Toast.LENGTH_SHORT).show();
            refeshWebview();
            return;
        }
        processCookie();
        if (StaticObjectManager.accessTokenName == null) {
            Toast.makeText(this, getString(R.string.DangNhapThatBai), Toast.LENGTH_SHORT).show();
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            refeshWebview();
            return;
        }
        ConnectServer connectServer = new ConnectServer();
        StaticObjectManager.connectServer = connectServer;
        loading.setContentText(getString(R.string.LoadThongTinTaiKhoan));
        loading.show();
        getInitializationInfo();
    }

    private void processCookie() {
        String cookies = StaticObjectManager.sessionCookie;
        String accessToken = null;
        if (cookies.charAt(cookies.length() - 1) != ';') {
            cookies = cookies + ";";
        }
        // String to be scanned to find the pattern.
        String pattern = "AccessTokenName=([^;]*);";
        String pattern1 = "AppData=([^;]*);";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        Pattern patternLanguage = Pattern.compile(pattern1);

        // Now create matcher object.
        Matcher m = r.matcher(cookies);
        if (m.find() && m.groupCount() > 0) {
            accessToken = m.group(1);
        }
        StaticObjectManager.accessTokenName = accessToken;
        Matcher language = patternLanguage.matcher(cookies);
        if (language.find() && language.groupCount() > 0) {
            try {
                JSONObject jsonLang = new JSONObject(language.group(1));
                String lang = jsonLang.getString("Language");
                if (AppData.LOCALE_VI_VN.equals(lang)) {
                    AppData.language = AppData.LOCALE_VI_VN;
                }
                else if(AppData.LOCALE_ZH.equals(lang)){
                    AppData.language=AppData.LOCALE_ZH;
                }
                else {
                    AppData.language = AppData.LOCALE_EN;
                }
//                newConfig.locale = new Locale(AppData.language);
//                onConfigurationChanged(newConfig);
//                myLocale = new Locale(lang);
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = new Locale(AppData.language);
                res.updateConfiguration(conf, dm);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getInitializationInfo() {
        getAccountInfo("username", StaticObjectManager.accessTokenName, MSTradeAppConfig.VersionApp);
    }

    private void getAccountInfo(String username, String pass, String version) {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(MSTradeAppConfig.getCustomerInfo);
        }
        {
            list_key.add("username");
            list_value.add(username);
        }
        {
            list_key.add("password");
            list_value.add(pass);
        }
        {
            list_key.add("version");
            list_value.add(version);
        }
        {
            list_key.add("language");
            list_value.add(AppData.language);
        }
        StaticObjectManager.connectServer.callHttpPostService(GETACCOUNTINFOSERVICE,
                Login_SSO.this, list_key, list_value);

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count == 1) {
            webviewContainer.setVisibility(View.GONE);
            loginOverWeb = new LoginOverWeb();
            getFragmentManager().beginTransaction().replace(R.id.ll_loginsso_container, loginOverWeb).addToBackStack(null).commit();
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        loading.dismiss();
    }
}
