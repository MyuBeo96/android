package com.tcscuat.mobiletrading;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.connector.CheckSessionFragment;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.AboutUs;
import com.fss.mobiletrading.function.AccountBalance;
import com.fss.mobiletrading.function.Advance;
import com.fss.mobiletrading.function.Alert_ViewPager;
import com.fss.mobiletrading.function.AnalysisNews;
import com.fss.mobiletrading.function.AppConfig;
import com.fss.mobiletrading.function.BlankView;
import com.fss.mobiletrading.function.BoLocCP_ViewPager;
import com.fss.mobiletrading.function.CapBaoLanh;
import com.fss.mobiletrading.function.CashStatement;
import com.fss.mobiletrading.function.ChooseAfacctno;
import com.fss.mobiletrading.function.MSBSMessage;
import com.fss.mobiletrading.function.MarketIndex;
import com.fss.mobiletrading.function.NTSListener.NTSHandler;
import com.fss.mobiletrading.function.NTSListener.NTSUpdateInfoListener;
import com.fss.mobiletrading.function.OrderHistory;
import com.fss.mobiletrading.function.Statement;
import com.fss.mobiletrading.function.StockStatement;
import com.fss.mobiletrading.function.accountconfig.AccountConfig;
import com.fss.mobiletrading.function.accountconfig.ChangePassword;
import com.fss.mobiletrading.function.accountconfig.ChangeTradingPassword;
import com.fss.mobiletrading.function.authorizationinfo.AuthorizationInfo;
import com.fss.mobiletrading.function.authorizationinfo.PermissionTable;
import com.fss.mobiletrading.function.cashtransfer.BankCashTransfer;
import com.fss.mobiletrading.function.cashtransfer.BankCashTransferConfirm;
import com.fss.mobiletrading.function.cashtransfer.BankCashTransferRegister;
import com.fss.mobiletrading.function.cashtransfer.CashTransfer;
import com.fss.mobiletrading.function.cashtransfer.CashTransferList;
import com.fss.mobiletrading.function.cashtransfer.CashTransferRegister;
import com.fss.mobiletrading.function.cashtransfer.InternalCashTransfer;
import com.fss.mobiletrading.function.cashtransfer.InternalCashTransferConfirm;
import com.fss.mobiletrading.function.cashtransfer.InternalCashTransferRegister;
import com.fss.mobiletrading.function.cashtransfer.SCCashTransfer;
import com.fss.mobiletrading.function.cashtransfer.SCCashTransferRegister;
import com.fss.mobiletrading.function.confirmorder.ConfirmOrder;
import com.fss.mobiletrading.function.coporateactions.CoporateActions;
import com.fss.mobiletrading.function.guaranteelist.GuaranteeList;
import com.fss.mobiletrading.function.news.NewsBySymbol;
import com.fss.mobiletrading.function.news.NewsBySymbolDetail;
import com.fss.mobiletrading.function.news.NewsDetail;
import com.fss.mobiletrading.function.notify.CommonUtilities;
import com.fss.mobiletrading.function.notify.NotificationService;
import com.fss.mobiletrading.function.notify.NotifyDetails;
import com.fss.mobiletrading.function.notify.NotifyFragment;
import com.fss.mobiletrading.function.notify.NotifyItem;
import com.fss.mobiletrading.function.notify.WakeLocker;
import com.fss.mobiletrading.function.oddorderregister.OddOrderRegister;
import com.fss.mobiletrading.function.orderlist.AmendOrder;
import com.fss.mobiletrading.function.orderlist.GTCOrderDetail;
import com.fss.mobiletrading.function.orderlist.GTCOrderList;
import com.fss.mobiletrading.function.orderlist.NormalOrderList;
import com.fss.mobiletrading.function.orderlist.OrderDetail;
import com.fss.mobiletrading.function.pastplaceorder.PastPlaceOrder;
import com.fss.mobiletrading.function.placeorder.GTCPlaceOrder;
import com.fss.mobiletrading.function.placeorder.OrderConfirm;
import com.fss.mobiletrading.function.placeorder.OrderSetParams;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.function.portfolio.Porfolio;
import com.fss.mobiletrading.function.report.Report;
import com.fss.mobiletrading.function.rightoffregister.RightOffRegister;
import com.fss.mobiletrading.function.rightoffregister.RightOffRegister_Confirm;
import com.fss.mobiletrading.function.rightoffregister.RightOffRegister_Info;
import com.fss.mobiletrading.function.searchstock.SearchStock;
import com.fss.mobiletrading.function.stocktransfer.StockTransfer;
import com.fss.mobiletrading.function.stocktransfer.StockTransferConfirm;
import com.fss.mobiletrading.function.watchlist.FullWatchList;
import com.fss.mobiletrading.function.watchlist.ListAllStocks;
import com.fss.mobiletrading.function.watchlist.ListFavWatchList;
import com.fss.mobiletrading.function.watchlist.StockIndex;
import com.fss.mobiletrading.function.watchlist.StockItemInformation;
import com.fss.mobiletrading.function.watchlist.WatchList;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.service.ServiceManager;
import com.google.android.gcm.GCMRegistrar;
import com.tcscuat.mobiletrading.MyActionBar.Action;
import com.tcscuat.mobiletrading.design.CustomLoadingDialog;
import com.tcscuat.mobiletrading.design.DialogLoading;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class MainActivity extends FragmentActivity implements
        Serializable {

    static final String LOGOUT = "LogoutService";
    static final String GETUNREAD = "GetUnReadService#GETUNREAD";
    static final long UPDATE_INTERVAL = 10000;
    public static final String deviceToken = "deviceToken";
    public static final int INTENT_NEWNOTIFY = 0;
    public static final int INTENT_REGISTEDTOKEN = 1;
    public static final String INTENTTYPE = "IntentType";

    SharedPreferences settings;

    public String currentFragment;
    public String currentDialog = StringConst.EMPTY;
    public String currentSecondFragment;
    public List<String> navigateFragments;
    List<AbstractFragment> resumingFragments;
    public HashMap<String, AbstractFragment> mapFragment = new HashMap<String, AbstractFragment>();
    // public HashMap<String, AbstractFragment> runningFragments = new
    // HashMap<String, AbstractFragment>();
    public int screenWidth;
    public int screenHeight;
    MenuItem ChonTK_ActionBarItem;
    public Handler delay;

    Dialog dialog_ShowError;
    TextView tv_message;
    TextView tv_Title;
    TextView tv_Positive;
    TextView tv_Negative;
    public InputMethodManager imm;
    public FragmentManager fm;
    AbstractFragment checkSession;
    Timer timer_checkSession;
    public DialogLoading dlg_loading;
    public ServiceManager serviceManager;
    MarketIndex marketIndex;
    int height;
    int width;
    NTSHandler ntsHandler;

    public int getWidthScreen() {
        return width;
    }

    public int getHeightScreen() {
        return height;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        createDialogMessage();
        serviceManager = new ServiceManager();
        StaticObjectManager.initSystemAfterLogin();
        fm = getSupportFragmentManager();
        initCommonData();
        initCheckSession();
        getSizeScreen();
        registerGCMDeviceToken();
        AsynchTaskTimer();
        if (AppConfig.getInstance().enableSSO) {
            startNTSListener();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.my.app.blinkled");
        registerReceiver(receiver, intentFilter);
        resumingFragments = new ArrayList<AbstractFragment>();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
    }

    private void startNTSListener() {

        ntsHandler = NTSHandler.newInstance();
        ntsHandler.setNtsUpdateInfoListener(new NTSUpdateInfoListener() {
            @Override
            public void receiveODEvent() {
                if (NTSHandler.orderChangeFragment.contains(currentFragment) || NTSHandler.orderChangeFragment.contains(currentDialog)) {
                    AbstractFragment frag = getFragmentByName(currentFragment);
                    if (currentDialog.length() > 0) {
                        AbstractFragment dialog = getFragmentByName(currentDialog);
                        dialog.refresh();
                    }
                    frag.refresh();

                }
            }

            @Override
            public void receiveSEEvent() {
                if (NTSHandler.stockBalanceChangeFragment.contains(currentFragment) || NTSHandler.stockBalanceChangeFragment.contains(currentDialog)) {
                    AbstractFragment frag = getFragmentByName(currentFragment);
                    frag.refresh();
                    if (currentDialog.length() > 0) {
                        AbstractFragment dialog = getFragmentByName(currentDialog);
                        dialog.refresh();
                    }
                }
            }

            @Override
            public void receiveCIEvent() {
                if (NTSHandler.cashBalanceChangeFragment.contains(currentFragment) || NTSHandler.cashBalanceChangeFragment.contains(currentDialog)) {
                    AbstractFragment frag = getFragmentByName(currentFragment);
                    frag.refresh();
                    if (currentDialog.length() > 0) {
                        AbstractFragment dialog = getFragmentByName(currentDialog);
                        dialog.refresh();
                    }
                }
            }
        });
        ntsHandler.connect();
    }

    public void initCommonData() {
        // if (android.os.Build.VERSION.SDK_INT >= 14) {
        // myActionBar = getActionBar();
        // myActionBar.setDisplayHomeAsUpEnabled(true);
        // myActionBar.setHomeButtonEnabled(true);
        // myActionBar.setIcon(R.drawable.image_back);
        // }
        // else {
        // actionBarV7 = getSupportActionBar();
        // actionBarV7.setDisplayHomeAsUpEnabled(true);
        // actionBarV7.setHomeButtonEnabled(true);
        // actionBarV7.setIcon(R.drawable.image_back);
        // }

        navigateFragments = new ArrayList<String>();
        delay = new Handler();
        dlg_loading = new DialogLoading(this);
        mapFragment.put(PlaceOrder.class.getName(),
                PlaceOrder.newInstance(this));
        mapFragment.put(WatchList.class.getName(), WatchList.newInstance(this));
        mapFragment.put(FullWatchList.class.getName(),
                FullWatchList.newInstance(this));
        mapFragment.put(RightOffRegister.class.getName(),
                RightOffRegister.newInstance(this));
        mapFragment.put(AccountBalance.class.getName(),
                AccountBalance.newInstance(this));
        mapFragment.put(Porfolio.class.getName(), Porfolio.newInstance(this));
        mapFragment.put(BlankView.class.getName(), BlankView.newInstance(this));
        mapFragment.put(NormalOrderList.class.getName(),
                NormalOrderList.newInstance(this));
        mapFragment.put(CoporateActions.class.getName(),
                CoporateActions.newInstance(this));
        mapFragment.put(Advance.class.getName(), Advance.newInstance(this));
        mapFragment.put(NewsDetail.class.getName(),
                NewsDetail.newInstance(this));
        mapFragment.put(AccountConfig.class.getName(),
                AccountConfig.newInstance(this));
        mapFragment.put(BoLocCP_ViewPager.class.getName(),
                BoLocCP_ViewPager.newInstance(this, 0));
        mapFragment.put(Alert_ViewPager.class.getName(),
                Alert_ViewPager.newInstance(this, 0));
        mapFragment.put(StockTransfer.class.getName(),
                StockTransfer.newInstance(this));
        mapFragment.put(ChooseAfacctno.class.getName(),
                ChooseAfacctno.newInstance(this));
        mapFragment.put(PastPlaceOrder.class.getName(),
                PastPlaceOrder.newInstance(this));
        // mapFragment.put(MenuFunction.class.getName(),
        // MenuFunction.newInstance(this));
        mapFragment.put(Report.class.getName(), Report.newInstance(this));
        mapFragment.put(AboutUs.class.getName(), AboutUs.newInstance(this));
        mapFragment.put(AnalysisNews.class.getName(),
                AnalysisNews.newInstance(this));
        mapFragment.put(MSBSMessage.class.getName(),
                MSBSMessage.newInstance(this));
        mapFragment.put(CashTransferList.class.getName(),
                CashTransferList.newInstance(this));
        mapFragment.put(GTCOrderList.class.getName(),
                GTCOrderList.newInstance(this));
        mapFragment.put(OddOrderRegister.class.getName(),
                OddOrderRegister.newInstance(this));
        mapFragment.put(ConfirmOrder.class.getName(),
                ConfirmOrder.newInstance(this));
        mapFragment.put(AuthorizationInfo.class.getName(),
                AuthorizationInfo.newInstance(this));
        mapFragment.put(PermissionTable.class.getName(),
                PermissionTable.newInstance(this));
        mapFragment.put(SearchStock.class.getName(),
                SearchStock.newInstance(this));
        mapFragment.put(CashTransfer.class.getName(),
                CashTransfer.newInstance(this));
        mapFragment.put(CashTransferRegister.class.getName(),
                CashTransferRegister.newInstance(this));
        mapFragment.put(NewsBySymbol.class.getName(),
                NewsBySymbol.newInstance(this));
        mapFragment.put(StockIndex.class.getName(),
                StockIndex.newInstance(this));
        mapFragment.put(StockItemInformation.class.getName(),
                StockItemInformation.newInstance(this));
        mapFragment.put(OrderDetail.class.getName(),
                OrderDetail.newInstance(this));
        mapFragment.put(GTCOrderDetail.class.getName(),
                GTCOrderDetail.newInstance(this));
        mapFragment.put(InternalCashTransfer.class.getName(),
                InternalCashTransfer.newInstance(this));
        mapFragment.put(BankCashTransfer.class.getName(),
                BankCashTransfer.newInstance(this));
        mapFragment.put(SCCashTransfer.class.getName(),
                SCCashTransfer.newInstance(this));
        mapFragment.put(InternalCashTransferRegister.class.getName(),
                InternalCashTransferRegister.newInstance(this));
        mapFragment.put(BankCashTransferRegister.class.getName(),
                BankCashTransferRegister.newInstance(this));
        mapFragment.put(SCCashTransferRegister.class.getName(),
                SCCashTransferRegister.newInstance(this));
        mapFragment.put(Statement.class.getName(), Statement.newInstance(this));
        mapFragment.put(CashStatement.class.getName(),
                CashStatement.newInstance(this));
        mapFragment.put(StockStatement.class.getName(),
                StockStatement.newInstance(this));
        mapFragment.put(MarketIndex.class.getName(),
                MarketIndex.newInstance(this));
        mapFragment.put(NotifyFragment.class.getName(),
                NotifyFragment.newInstance(this));
        mapFragment.put(NotifyDetails.class.getName(),
                NotifyDetails.newInstance(this));
        mapFragment.put(OrderHistory.class.getName(),
                OrderHistory.newInstance(this));
        mapFragment.put(ListFavWatchList.class.getName(),
                ListFavWatchList.newInstance(this));
        mapFragment.put(ListAllStocks.class.getName(),
                ListAllStocks.newInstance(this));
        mapFragment.put(InternalCashTransferConfirm.class.getName(),
                InternalCashTransferConfirm.newInstance(this));
        mapFragment.put(BankCashTransferConfirm.class.getName(),
                BankCashTransferConfirm.newInstance(this));
        mapFragment.put(ChangePassword.class.getName(),
                ChangePassword.newInstance(this));
        mapFragment.put(ChangeTradingPassword.class.getName(),
                ChangeTradingPassword.newInstance(this));
        mapFragment.put(StockTransferConfirm.class.getName(),
                StockTransferConfirm.newInstance(this));
        mapFragment.put(RightOffRegister_Confirm.class.getName(),
                RightOffRegister_Confirm.newInstance(this));
        mapFragment.put(OrderConfirm.class.getName(),
                OrderConfirm.newInstance(this));
        mapFragment.put(AmendOrder.class.getName(),
                AmendOrder.newInstance(this));
        mapFragment.put(RightOffRegister_Info.class.getName(),
                RightOffRegister_Info.newInstance(this));
        mapFragment.put(GTCPlaceOrder.class.getName(),
                GTCPlaceOrder.newInstance(this));
        mapFragment.put(NewsBySymbolDetail.class.getName(),
                NewsBySymbolDetail.newInstance(this));
        mapFragment.put(CapBaoLanh.class.getName(),
                CapBaoLanh.newInstance(this));

        mapFragment.put(GuaranteeList.class.getName(),
                GuaranteeList.newInstance(this));
    }

    public abstract void initListener();

    private void initCheckSession() {
        checkSession = CheckSessionFragment.newIntances(this);
    }

    public void AsynchTaskTimer() {
        TimerTask timertask = new TimerTask() {

            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();

            {
                list_key.add("link");
                list_value.add(MSTradeAppConfig.controller_checkSession);
            }

            {
                list_key.add("username");
                list_value.add(StaticObjectManager.loginInfo.UserName);
            }

            @Override
            public void run() {
                delay.post(new Runnable() {
                    public void run() {
                        try {
                            StaticObjectManager.connectServer
                                    .callHttpPostService(
                                            CheckSessionFragment.CHECKSESSION,
                                            checkSession, list_key, list_value);
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer_checkSession = new Timer();
        timer_checkSession.schedule(timertask, 0, UPDATE_INTERVAL);
    }

    private void cancelTimer() {
        timer_checkSession.cancel();
    }

    /**
     * written by hiep.nguyen on 21/12/2014 method register device token with
     * GCM
     */
    private void registerGCMDeviceToken() {
        StaticObjectManager.isLogin = true;
        // Make sure the device has the proper dependencies.
//        GCMRegistrar.checkDevice(this);
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);
        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                CommonUtilities.DISPLAY_MESSAGE_ACTION));
        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(this);
        Log.e("Device token 1", regId);
        StaticObjectManager.deviceToken = regId;
        // Check if regid already presents
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
            Log.e("Device token 2", regId);
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                // Skips registration.
                Log.e("Dang ky GCM", "Already registered with GCM " + regId);
            }
        }
    }

    private void getSizeScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    /**
     * written by hiep.nguyen on 21/12/2014 method receive package from
     * GCMIntentService to display NotifyFragment
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String menuFragment = intent.getExtras().getString("NotifyFragment");
        NotifyItem notifyItem = new NotifyItem();
        notifyItem.setID(menuFragment);
        sendArgumentToFragment(NotifyDetails.class.getName(), notifyItem);
        displayFragment(NotifyDetails.class.getName());
    }

    /**
     * written by hiep.nguyen on 21/12/2014 Receiving push messages
     */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());
            // Releasing wake lock
            WakeLocker.release();
        }
    };

    /**
     * written by hiep.nguyen on 21/12/2014 method cancel thread if registered
     * with php server
     */
    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
            unregisterReceiver(receiver);
        } catch (Exception e) {
            Log.e("UnRegister Receiver Err", "> " + e.getMessage());
        }
        super.onDestroy();
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int intentType = intent.getIntExtra(MainActivity.INTENTTYPE, 0);
            if (intentType == MainActivity.INTENT_REGISTEDTOKEN) {
                savedSharePreferences(StaticObjectManager.deviceToken);
                getUnRead();
            } else if (intentType == MainActivity.INTENT_NEWNOTIFY) {
                getUnRead();
                NotifyFragment notify = (NotifyFragment) getFragmentByName(NotifyFragment.class
                        .getName());
                if (notify.isShow) {
                    notify.refresh();
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // lấy deviceToken trong SharePreference
        readSharePreferences();
        if (StaticObjectManager.deviceToken == null
                || StaticObjectManager.deviceToken.length() == 0) {
        } else {
            getUnRead();
        }
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        MyApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
    }

    private void getUnRead()
    {
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        NotificationService.CallUnRead(StaticObjectManager.deviceToken, android_id,
                StaticObjectManager.loginInfo.UserName, new AbstractFragment() {

                    @Override
                    protected void process(String key, ResultObj rObj) {
                        int unread = (int) rObj.obj;
                        showUnReadNotify(unread);
                    }
                }, NotifyFragment.GETUNREAD);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    // private void addRunningFragment(AbstractFragment frag) {
    // String className = frag.getClass().getName();
    // if (!runningFragments.containsKey(className)) {
    // runningFragments.put(className, frag);
    // }
    // }
    //
    // private void removeNotRunningFragment(AbstractFragment frag) {
    // String className = frag.getClass().getName();
    // if (runningFragments.containsKey(className)) {
    // runningFragments.remove(className);
    // }
    // }

    public void showFragment(AbstractFragment frag) {
        FragmentTransaction ft = fm.beginTransaction();
        if (frag != null && frag.isAdded()) {
            ft.show(frag);
            ft.commit();
        }
    }

    public void hideFragment(AbstractFragment frag) {
        FragmentTransaction ft = fm.beginTransaction();
        if (frag != null && frag.isAdded()) {
            ft.hide(frag);
            frag.onHide();
            ft.commit();
        }
    }

    public void addFragment(int container, AbstractFragment frag) {
        FragmentTransaction ft = fm.beginTransaction();
        if (frag != null) {
            if (frag.isAdded()) {
                ft.show(frag);
            } else {
                // View v = findViewById(container);
                // if (v != null) {
                // if (v instanceof ViewGroup) {
                // ((ViewGroup) v).removeAllViews();
                // }
                // }
                ft.add(container, frag, null);
            }
            ft.commit();
        }
    }

    public void replaceFragment(int container, AbstractFragment frag) {
        FragmentTransaction ft = fm.beginTransaction();
        if (frag != null) {
            if (frag.isAdded()) {
                ft.show(frag);
                frag.onShowed();
            } else {
                ft.replace(container, frag, null);
            }
            ft.commit();
        }
    }

    public void removeFragment(AbstractFragment frag) {
        FragmentTransaction ft = fm.beginTransaction();
        if (frag != null) {
            ft.remove(frag);
            ft.commit();
        }
    }

    public abstract void displayFragment(String className);

    public abstract void navigateFragment(String className);

    public abstract void navigateFragment(String className, Object argument);

    /**
     * quay trở lại màn hình trước
     */
    public abstract void backNavigateFragment();

    /**
     * quay trở lại màn hình trước
     *
     * @param index thứ tự của màn hình trước trong stack lưu các màn
     *              hình(navigationFragments)
     */
    public void backNavigateFragment(int index) {
    }

    /**
     * quay trở lại màn hình trước
     *
     * @param className tên class của màn hình muốn quay về <br>
     *                  nếu className không tồn tại trong stack thì không làm gì cả
     */
    public void backNavigateFragment(String className) {
        if (navigateFragments.contains(className)) {
            backNavigateFragment(navigateFragments.indexOf(className));
        }
    }

    public abstract void displayFragment(String className, int container);

    public void finish() {
        super.finish();
        cancelTimer();
        StaticObjectManager.ClearData();
        clearNotification(this);
    }

    public AbstractFragment initFragment(String className) {
        AbstractFragment fragment = null;
        if (MarketIndex.class.getName().equals(className)) {
            marketIndex = MarketIndex.newInstance(this);
            fragment = marketIndex;
            return fragment;
        }
        if (mapFragment.containsKey(className)) {
            fragment = ((AbstractFragment) mapFragment.get(className));
        } else {
        }
        return fragment;
    }

    /**
     * Lấy AbstractFragment trong mảng mapFragment
     *
     * @param className
     * @return trả về AbstractFragment tương ứng, hoặc trả về null nếu không tìm
     * thấy
     */
    public AbstractFragment getFragmentByName(String className) {
        if (MarketIndex.class.getName().equals(className)) {
            return marketIndex;
        }
        if (mapFragment.containsKey(className)) {
            return ((AbstractFragment) mapFragment.get(className));
        } else {
            return null;
        }
    }

    public abstract void setTitleActionBar(String title);

    /**
     * Thiết lập ẩn hiện ActionBar
     *
     * @param showed Nếu showed là true thì hiện ActionBar, nếu là false thì ẩn
     *               ActionBar
     */
    public abstract void setShowActionBar(boolean showed);

    public void logout() {

        // if (dialog_ShowError == null) {
        // dialog_ShowError = new Dialog(this);
        // dialog_ShowError.setCancelable(true);
        // dialog_ShowError.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog_ShowError.setContentView(R.layout.alert_dialog);
        // WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        // lp.copyFrom(dialog_ShowError.getWindow().getAttributes());
        // lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        // lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // dialog_ShowError.getWindow().setAttributes(lp);
        // dialog_ShowError.getWindow().setBackgroundDrawable(
        // new ColorDrawable(0));
        //
        // tv_message = (TextView) dialog_ShowError
        // .findViewById(R.id.text_alertdialog_message);
        // tv_Title = (TextView) dialog_ShowError
        // .findViewById(R.id.text_alertdialog_title);
        // tv_Positive = (TextView) dialog_ShowError
        // .findViewById(R.id.text_alertdialog_possitive);
        // tv_Negative = (TextView) dialog_ShowError
        // .findViewById(R.id.text_alertdialog_negative);
        // dialog_ShowError.findViewById(R.id.line_alertdialog_1)
        // .setVisibility(View.VISIBLE);
        // tv_Negative.setVisibility(View.VISIBLE);
        // tv_Title.setText(getResources().getString(R.string.logout_title));
        // tv_message.setText(getResources().getString(R.string.logout_msg));
        // tv_Positive.setText(getResources().getString(R.string.Yes));
        // tv_Negative.setText(getResources().getString(R.string.No));
        // tv_Positive.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // StaticObjectManager.connectServer.callHttpGetService(
        // LOGOUT, null,
        // getResources()
        // .getString(R.string.controller_Logoff));
        // dialog_ShowError.dismiss();
        // finish();
        //
        // }
        // });
        // tv_Negative.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // dialog_ShowError.dismiss();
        // }
        // });
        // }
        // dialog_ShowError.show();
        try {
            if (AppConfig.getInstance().enableSSO) {
                final CustomLoadingDialog loading = new CustomLoadingDialog(this);
                loading.setContentText(getStringResource(R.string.logout_title));
                loading.show();
                ntsHandler.closeConnectWebsocket();
                WebView wv = new WebView(this);
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        Log.e("testlog", "onPageStarted:.url webview:" + url);
                        if (url.equals(MSTradeAppConfig.returnLoginUrl)) {
                            loading.dismiss();
                            finish();
                        }
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);

                    }
                });
//                wv.loadUrl(MSTradeAppConfig.address_server + MSTradeAppConfig.logout_SSO);
                wv.loadUrl(BuildConfig.address_server + MSTradeAppConfig.logout_SSO);
            } else {
                StaticObjectManager.connectServer.callHttpGetService(LOGOUT, null,
                        getResources().getString(R.string.controller_Logoff));
                ntsHandler.closeConnectWebsocket();
                finish();
            }
        } catch (Exception e) {
            finish();
        }
    }

    public abstract void hideBottomBar();

    public abstract void showBottomBar();

    public String getStringResource(int id) {
        try {
            return getResources().getString(id);
        } catch (Exception e) {
        }
        return StringConst.EMPTY;
    }

    public abstract void onChangeAfacctno(String str);

    public abstract void setHomeLogoAction(Action action);

    public abstract void clearHomeLogoAction();

    public abstract void showMenu(boolean isShow);

    public abstract void removeAllAction();

    public abstract void removeAction(Action action);

    public abstract void addAction(Action action);

    /**
     * ẩn hiện bàn phím nhập giá
     *
     * @param isShow : true thì hiển thị, false thì ẩn
     * @param v      : edittext nhập mã chứng khoán
     */
    public abstract void showKBoardPrice(boolean isShow, View v);

    /**
     * ẩn hiện bàn phím nhập số lượng
     *
     * @param isShow : true thì hiển thị, false thì ẩn
     * @param v      : edittext nhập mã chứng khoán
     */
    public abstract void showKBoardQuantity(boolean isShow, View v);

    /**
     * ẩn hiện bàn phím nhập mã chứng khoán
     *
     * @param isShow : true thì hiển thị, false thì ẩn
     * @param v      : edittext nhập mã chứng khoán
     */
    public abstract void showKBoardSymbol(boolean isShow, View v);

    /**
     * ẩn hiện bàn phím nhập mã chứng khoán
     *
     * @param isShow : true thì hiển thị, false thì ẩn
     * @param v      : edittext nhập mã chứng khoán
     */
    public abstract void showKBoardHook(boolean isShow, View v,
                                        String[] listStock);

    /**
     * Truy�?n vào thông tin của lệnh tới màn hình đặt lệnh, nếu màn hình đặt
     * lệnh chưa hiển thị thì hiển thị màn hình đặt lệnh
     */
    public abstract void setOrderToPlaceOrder(OrderSetParams p);

    /**
     * Truy�?n tham số tới 1 drawMap
     *
     * @param fragmentName : Tên drawMap, nếu không tìm thấy fragmet nào với tên tương
     *                     ứng thì k làm gì cả
     * @param obj          : tham số cần truy�?n
     */
    public void sendArgumentToFragment(String fragmentName, Object obj) {
        AbstractFragment fragment = mapFragment.get(fragmentName);
        if (fragment != null) {
            fragment.getArgument(obj);
        }
    }

    public abstract void showUnReadNotify(int amount);

    public void clearNotification(Context context) {
        NotificationManager manager = (NotificationManager) context
                .getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    public void hideKeyboard(View view) {
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }

    public void showKeyboard(View view) {
        if (view != null) {
            imm.showSoftInput(view, 0);
            view.requestFocus();
        }
    }

    private void savedSharePreferences(String pr_deviceToken) {
        if (settings == null) {
            settings = getSharedPreferences(Login.MobileTradingPrefs,
                    Context.MODE_PRIVATE);
        }
        Editor editor = settings.edit();
        editor.putString(deviceToken, pr_deviceToken);
        editor.commit();
    }

    private void readSharePreferences() {
        if (settings == null) {
            settings = getSharedPreferences(Login.MobileTradingPrefs,
                    Context.MODE_PRIVATE);
        }
        if (settings.contains(deviceToken)) {
            StaticObjectManager.deviceToken = settings.getString(deviceToken,
                    StringConst.EMPTY);
        }
    }

    public void loadingScreen(boolean isloading) {
        if (isloading) {
            dlg_loading.show();
        } else {
            dlg_loading.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        showKBoardSymbol(false, null);
        showKBoardHook(false, null, null);
        showKBoardPrice(false, null);
        showKBoardQuantity(false, null);
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

    public void addResumingFragment(AbstractFragment frag) {
        if (frag != null && !resumingFragments.contains(frag)) {
            resumingFragments.add(frag);
        }
    }

    public void removeResumingFragment(AbstractFragment frag) {
        if (frag != null && resumingFragments.contains(frag)) {
            resumingFragments.remove(frag);
        }
    }
}
