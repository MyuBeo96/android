package com.tcscuat.mobiletrading;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fss.designcontrol.keyboardhook.KeyboardHook;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.AppData;
import com.fss.mobiletrading.function.ChooseAfacctno;
import com.fss.mobiletrading.function.coporateactions.CoporateActionsDetail;
import com.fss.mobiletrading.function.market.MarketInfo;
import com.fss.mobiletrading.function.notify.NotifyFragment;
import com.fss.mobiletrading.function.orderlist.NormalOrderList;
import com.fss.mobiletrading.function.placeorder.OrderSetParams;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.function.watchlist.StockIndex;
import com.fss.mobiletrading.function.watchlist.WatchList;
import com.fss.mobiletrading.keyboard.KBoardPrice;
import com.fss.mobiletrading.keyboard.KBoardQuantity;
import com.fss.mobiletrading.keyboard.KBoardSymbol;
import com.fss.mobiletrading.menu.SlideMenu;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.MyActionBar.Action;

import java.util.List;
import java.util.Locale;

public class MainActivity_Mobile extends MainActivity {

    static final String LOGOUT = "LogoutService";

    static final String TAG_MAINACTIVITY = "MainActivity_Mobile";

    private ImageButton btn_placeorder;
    private ImageButton btn_orderlist;
    private ImageButton btn_watchlist;
    private ImageButton btn_notify;
    private ImageButton btn_Menu;
    private RelativeLayout lay_placeorder;
    private RelativeLayout lay_orderlist;
    private RelativeLayout lay_watchlist;
    private RelativeLayout lay_notify;
    private RelativeLayout lay_Menu;
    private TextView tv_placeorder;
    private TextView tv_orderlist;
    private TextView tv_watchlist;
    private TextView tv_notify;
    private TextView tv_Menu;
    private TextView tv_count_notify_unread;
    private LinearLayout lay_Footer;
    public View container;
    KeyboardHook kboardHookNew_CBL;
    KBoardSymbol kboardSymbol;
    KBoardQuantity kBoardQuantity;
    KBoardPrice kBoardPrice;
    private DrawerLayout mDrawerLayout;
    private SlideMenu slideMenu;
    private MyActionBar myActionBar;
    Runnable closeMenu;

    public void superOnCreate(Bundle bundle) {
        super.onCreate(bundle);

    }

    public MainActivity_Mobile() {
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activitymain_mobile);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getBaseContext().getResources().updateConfiguration(Login.newConfig,
                getBaseContext().getResources().getDisplayMetrics());
        initView();
        initCommonData();
        initialiseActionBar();
        if (StaticObjectManager.flagNotifyComeAtNotLogin && bundle == null) {
            displayFragment(NotifyFragment.class.getName());
            StaticObjectManager.flagNotifyComeAtNotLogin = false;
        } else if (StaticObjectManager.flagNotifyComeAtNotLogin == false
                && bundle == null) {
            displayFragment(MarketInfo.class.getName());
        }
        // backup, không được xóa
        // if (bundle == null) {
        // displayFragment(MarketInfo.class.getName());
        // }

        initListener();
        final View activityRootView = findViewById(R.id.activitymain_mobile);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int heightDiff = activityRootView.getRootView()
                                .getHeight() - activityRootView.getHeight();
                        Log.i("MainActivity",
                                activityRootView.getRootView().getHeight()
                                        + " " + activityRootView.getHeight()
                                        + " " + heightDiff);
                        if (heightDiff > 100) {
                            isKeyboardVisible(true);
                        } else {
                            isKeyboardVisible(false);
                        }
                    }
                });

    }

    private void initView() {
        btn_placeorder = (ImageButton) findViewById(R.id.btn_activitymain_placeorder);
        btn_orderlist = (ImageButton) findViewById(R.id.btn_activitymain_orderlist);
        btn_watchlist = (ImageButton) findViewById(R.id.btn_activitymain_watchlist);
        btn_notify = (ImageButton) findViewById(R.id.btn_activitymain_notify);
        btn_Menu = (ImageButton) findViewById(R.id.btn_activitymain_Menu);

        lay_placeorder = (RelativeLayout) findViewById(R.id.lay_activitymain_placeorder);
        lay_orderlist = (RelativeLayout) findViewById(R.id.lay_activitymain_orderlist);
        lay_watchlist = (RelativeLayout) findViewById(R.id.lay_activitymain_watchlist);
        lay_notify = (RelativeLayout) findViewById(R.id.lay_activitymain_notify);
        lay_Menu = (RelativeLayout) findViewById(R.id.lay_activitymain_Menu);

        tv_placeorder = (TextView) findViewById(R.id.lbl_activitymain_placeorder);
        tv_orderlist = (TextView) findViewById(R.id.lbl_activitymain_orderlist);
        tv_watchlist = (TextView) findViewById(R.id.lbl_activitymain_watchlist);
        tv_notify = (TextView) findViewById(R.id.lbl_activitymain_notify);
        tv_Menu = (TextView) findViewById(R.id.lbl_activitymain_menu);
        tv_count_notify_unread = (TextView) findViewById(R.id.text_activitymain_notifycounter);

        lay_Footer = (LinearLayout) findViewById(R.id.lay_footer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        myActionBar = (MyActionBar) findViewById(R.id.actionbar);
        slideMenu = (SlideMenu) findViewById(R.id.mainactivity_slidermenu);
        kboardHookNew_CBL = (KeyboardHook) findViewById(R.id.kboardhooknew_cbl);
        kboardSymbol = (KBoardSymbol) findViewById(R.id.activitymain_kboardsymbol);
        kBoardQuantity = (KBoardQuantity) findViewById(R.id.activitymain_kboardsymbol_quantity);
        kBoardPrice = (KBoardPrice) findViewById(R.id.activitymain_kboardsymbol_price);
        container = findViewById(R.id.container);
    }
    public void setActionbarPlaceOrder(@ColorRes int color) {
        myActionBar.setActionbarColor(color);
    }
    private void initialiseActionBar() {

        myActionBar.setAfacctnoAction(new Action() {

            @Override
            public void performAction(View view) {
                navigateFragment(ChooseAfacctno.class.getName());
            }

            @Override
            public int getDrawable() {
                return 0;
            }

            @Override
            public String getText() {
                return null;
            }
        });
        if (StaticObjectManager.acctnoItem != null) {
            myActionBar.setAfacctno(StaticObjectManager.acctnoItem.DESCNAME);
        } else {
            myActionBar.setAfacctno(MSTradeAppConfig.USERNAME_DEFAULT);
        }

    }

    @Override
    public void initCommonData() {
        super.initCommonData();
        mapFragment.put(MarketInfo.class.getName(),
                MarketInfo.newInstance(this));
        mapFragment.put(CoporateActionsDetail.class.getName(),
                CoporateActionsDetail.newInstance(this));
        closeMenu = new Runnable() {

            @Override
            public void run() {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        };
        mapFragment.put(StockIndex.class.getName(),
                StockIndex.newInstance(this));

    }

    @Override
    public void initListener() {
        lay_placeorder.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (currentFragment != PlaceOrder.class.getName()) {
                    displayFragment(PlaceOrder.class.getName());
                }
            }
        });
        lay_orderlist.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (currentFragment != NormalOrderList.class.getName()) {
                    displayFragment(NormalOrderList.class.getName());
                }
            }
        });
        lay_watchlist.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (currentFragment != WatchList.class.getName()) {
                    displayFragment(WatchList.class.getName());
                }
            }
        });
        lay_notify.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (currentFragment != NotifyFragment.class.getName()) {
                    displayFragment(NotifyFragment.class.getName());
                }
            }
        });
        lay_Menu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    showMenu(true);
                } else {
                    showMenu(false);
                }
            }
        });
        slideMenu.setOnButtonClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                logout();
            }
        });
        mDrawerLayout.setDrawerListener(new DrawerListener() {

            @Override
            public void onDrawerStateChanged(int arg0) {

            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {

            }

            @Override
            public void onDrawerOpened(View arg0) {
                slideMenu.onOpen();
            }

            @Override
            public void onDrawerClosed(View arg0) {
                slideMenu.onClose();

            }
        });
    }

    @Override
    public void displayFragment(String className) {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            showMenu(false);
        }
        if (className != currentFragment) {
            AbstractFragment fragment = initFragment(className);
            if (fragment != null) {
                removeFragment(getFragmentByName(currentFragment));
                replaceFragment(R.id.container, fragment);
                if (navigateFragments.size() > 0) {
                    navigateFragments.clear();
                }
            }
            fragment.addActionToActionBar();
            slideMenu.setSelectedItem(className);
            currentFragment = className;
            if (currentFragment == PlaceOrder.class.getName()) {
                setActiveFooter(btn_placeorder, tv_placeorder);
                myActionBar.setActionbarColor(R.color.placeorder_buy_color);
            } else if (currentFragment == WatchList.class.getName()) {
                setActiveFooter(btn_watchlist, tv_watchlist);
                myActionBar.setActionbarColor(R.color.placeorder_sell_color);
            } else if (currentFragment == NormalOrderList.class.getName()) {
                setActiveFooter(btn_orderlist, tv_orderlist);
                myActionBar.setActionbarColor(R.color.placeorder_sell_color);
            } else if (currentFragment == NotifyFragment.class.getName()) {
                setActiveFooter(btn_notify, tv_notify);
                myActionBar.setActionbarColor(R.color.placeorder_sell_color);
            } else {
                setActiveFooter(null, null);
                myActionBar.setActionbarColor(R.color.placeorder_sell_color);
            }
        }
    }

    @Override
    public void navigateFragment(String className) {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            showMenu(false);
        }
        if (className != currentFragment) {
            AbstractFragment frag = initFragment(className);
            if (frag != null) {
                navigateFragments.add(currentFragment);
                hideFragment(getFragmentByName(currentFragment));
                addFragment(R.id.container, frag);
                frag.addActionToActionBar();
                slideMenu.setSelectedItem(className);
                currentFragment = className;
                if (currentFragment == PlaceOrder.class.getName()) {
                    setActiveFooter(btn_placeorder, tv_placeorder);
                } else if (currentFragment == WatchList.class.getName()) {
                    setActiveFooter(btn_watchlist, tv_watchlist);
                } else if (currentFragment == NormalOrderList.class.getName()) {
                    setActiveFooter(btn_orderlist, tv_orderlist);
                } else if (currentFragment == NotifyFragment.class.getName()) {
                    setActiveFooter(btn_notify, tv_notify);
                } else {
                    setActiveFooter(null, null);
                }
            }
        }
    }

    @Override
    public void navigateFragment(String className, Object argument) {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            showMenu(false);
        }
        if (className != currentFragment) {
            AbstractFragment frag = initFragment(className);
            if (frag != null) {
                frag.getArgument(argument);
                navigateFragments.add(currentFragment);
                hideFragment(getFragmentByName(currentFragment));
                addFragment(R.id.container, frag);
                frag.addActionToActionBar();
                slideMenu.setSelectedItem(className);
                currentFragment = className;
                if (currentFragment == PlaceOrder.class.getName()) {
                    setActiveFooter(btn_placeorder, tv_placeorder);
                } else if (currentFragment == WatchList.class.getName()) {
                    setActiveFooter(btn_watchlist, tv_watchlist);
                } else if (currentFragment == NormalOrderList.class.getName()) {
                    setActiveFooter(btn_orderlist, tv_orderlist);
                } else if (currentFragment == NotifyFragment.class.getName()) {
                    setActiveFooter(btn_notify, tv_notify);
                } else {
                    setActiveFooter(null, null);
                }
            }
        }
    }

    @Override
    public void backNavigateFragment() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            showMenu(false);
        }
        if (navigateFragments.size() <= 0) {
            return;
        }
        String className = navigateFragments.get(navigateFragments.size() - 1);
        AbstractFragment frag = getFragmentByName(className);
        if (frag != null) {
            removeFragment(getFragmentByName(currentFragment));
            navigateFragments.remove(navigateFragments.size() - 1);
            frag.addActionToActionBar();
            slideMenu.setSelectedItem(className);
            currentFragment = className;
            if (currentFragment == PlaceOrder.class.getName()) {
                setActiveFooter(btn_placeorder, tv_placeorder);
            } else if (currentFragment == WatchList.class.getName()) {
                setActiveFooter(btn_watchlist, tv_watchlist);
            } else if (currentFragment == NormalOrderList.class.getName()) {
                setActiveFooter(btn_orderlist, tv_orderlist);
            } else if (currentFragment == NotifyFragment.class.getName()) {
                setActiveFooter(btn_notify, tv_notify);
            } else {
                setActiveFooter(null, null);
            }
            showFragment(frag);
            frag.onShowed();
        }
    }

    // @Override
    // public void backNavigateFragment(int index) {
    // if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
    // showMenu(false);
    // }
    // if (navigateFragments.size() <= index || index < 0) {
    // return;
    // }
    // String className = navigateFragments.get(index);
    // AbstractFragment frag = getFragmentByName(className);
    // if (frag != null) {
    // if (navigateFragments.size() > index + 1) {
    // for (int i = index + 1; i < navigateFragments.size(); i++) {
    // removeFragment(getFragmentByName(navigateFragments.get(i)));
    // navigateFragments.remove(i);
    // }
    // } else {
    // removeFragment(getFragmentByName(currentFragment));
    // navigateFragments.remove(navigateFragments.size() - 1);
    // }
    // frag.addActionToActionBar();
    // slideMenu.setSelectedItem(className);
    // currentFragment = className;
    // if (currentFragment == PlaceOrder.class.getName()) {
    // setActiveFooter(btn_placeorder, tv_placeorder);
    // } else if (currentFragment == WatchList.class.getName()) {
    // setActiveFooter(btn_watchlist, tv_watchlist);
    // } else if (currentFragment == NormalOrderList.class.getName()) {
    // setActiveFooter(btn_orderlist, tv_orderlist);
    // } else if (currentFragment == NotifyFragment.class.getName()) {
    // setActiveFooter(btn_notify, tv_notify);
    // } else {
    // setActiveFooter(null, null);
    // }
    // showFragment(frag);
    // frag.onShowed();
    // }
    // }

    @Override
    public void backNavigateFragment(int index) {
        int size = navigateFragments.size();
        for (int i = index; i < size; i++) {
            backNavigateFragment();
        }
    }

    @Override
    public void displayFragment(String className, int container) {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            showMenu(false);
        }
        if (className != currentFragment) {
            AbstractFragment fragment = initFragment(className);
            if (fragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(container, fragment, null);
                transaction.commit();
            }
        }
    }

    @Override
    public void setTitleActionBar(String title) {
        myActionBar.setTitle(title);
        // if (android.os.Build.VERSION.SDK_INT >= 14) {
        // myActionBar.setTitle(title);
        // } else {
        // actionBarV7.setTitle(title);
        // }
    }

    public void hideBottomBar() {
        lay_Footer.setVisibility(LinearLayout.GONE);
    }

    public void showBottomBar() {
        lay_Footer.setVisibility(LinearLayout.VISIBLE);
    }

    @Override
    public void onChangeAfacctno(String str) {
        if (StaticObjectManager.acctnoItem != null) {
            myActionBar.setAfacctno(StaticObjectManager.acctnoItem.DESCNAME);
        } else {
            myActionBar.setAfacctno(MSTradeAppConfig.USERNAME_DEFAULT);
        }
        // notify sự kiện đổi tiểu khoản tới các màn hình đang ẩn
        // if (navigateFragments.size() > 0) {
        // for (int i = 0; i < navigateFragments.size(); i++) {
        // String className = navigateFragments.get(i);
        // AbstractFragment frag = getFragmentByName(className);
        // if (frag != null) {
        // frag.notifyChangeAcctNo();
        // }
        // }
        // }

        // notify sự kiện đổi tiểu khoản tới các màn hình đang resume
        int resumingFragSize = resumingFragments.size();
        for (int i = 0; i < resumingFragSize; i++) {
            AbstractFragment frag = resumingFragments.get(i);
            if (frag != null) {
                frag.notifyChangeAcctNo();
            }
        }
    }

    public void setHomeLogoAction(Action action) {
        myActionBar.setHomeLogoAction(action);
    }

    public void clearHomeLogoAction() {

        myActionBar.clearHomeLogoAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Configuration newConfig = new Configuration();
        newConfig.locale = new Locale(AppData.language);
        super.onConfigurationChanged(newConfig);
    }

    public void showMenu(boolean isShow) {
        if (isShow) {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        } else {
            delay.postDelayed(closeMenu, 400);

        }
    }

    public void removeAllAction() {

        myActionBar.removeAllAction();
    }

    public void removeAction(Action action) {

        myActionBar.removeAllAction();
    }

    public void addAction(Action action) {
        myActionBar.addAction(action);
    }

    @Override
    public void showKBoardHook(boolean isShow, View v, String[] listStock) {
        if (isShow) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            kboardHookNew_CBL.show(v, listStock);
            kboardHookNew_CBL.bringToFront();
        } else {
            kboardHookNew_CBL.hide();
        }
    }

    @Override
    public void showKBoardSymbol(boolean isShow, View v) {
        if (isShow) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            kboardSymbol.show(v);
            kboardSymbol.bringToFront();
        } else {
            kboardSymbol.hide();
        }
    }

    @Override
    public void showKBoardPrice(boolean isShow, View v) {
        if (isShow) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            kBoardPrice.show(v);
            kBoardPrice.bringToFront();
        } else {
            kBoardPrice.hide();
        }
    }

    @Override
    public void showKBoardQuantity(boolean isShow, View v) {
        if (isShow) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            kBoardQuantity.show(v);
            kBoardQuantity.bringToFront();
        } else {
            kBoardQuantity.hide();
        }
    }

    AbstractFragment syncChooseAfacctnoFragment;

    @Override
    public void setOrderToPlaceOrder(OrderSetParams orderParams) {
        if (orderParams != null) {
            final String link_chooseacctno = getStringResource(R.string.controller_ChooseAcctno);
            ChooseAfacctno chooseAfacctno = ((ChooseAfacctno) mapFragment
                    .get(ChooseAfacctno.class.getName()));

            if (syncChooseAfacctnoFragment == null) {
                syncChooseAfacctnoFragment = new AbstractFragment() {

                    OrderSetParams orderSetParams;
                    ChooseAfacctno chooseAfacctno;

                    @Override
                    public void getArgument(Object obj) {
                        super.getArgument(obj);
                        if (obj instanceof OrderSetParams) {
                            orderSetParams = (OrderSetParams) obj;
                        } else if (obj instanceof ChooseAfacctno) {
                            chooseAfacctno = (ChooseAfacctno) obj;
                        }
                    }

                    @Override
                    protected void process(String key, ResultObj rObj) {
                        switch (key) {
                            case ChooseAfacctno.CHANGE_ACCTNO:
                                chooseAfacctno
                                        .changeAfacctno(orderSetParams.afacctno);
                                PlaceOrder placeorder = (PlaceOrder) mapFragment
                                        .get(PlaceOrder.class.getName());
                                placeorder.setOrderInit(orderSetParams);
                                if (!placeorder.isResumed()) {
                                    displayFragment(PlaceOrder.class.getName());
                                }
                                break;
                            case ChooseAfacctno.FINDCUSTODYCD:
                                if (rObj.obj != null) {
                                    List<AcctnoItem> list = ((List<AcctnoItem>) rObj.obj);
                                    if (chooseAfacctno != null) {
                                        chooseAfacctno.changeCustodyCd(list);
                                        chooseAfacctno.CallSyncChooseAcctno(
                                                link_chooseacctno,
                                                orderSetParams.afacctno,
                                                syncChooseAfacctnoFragment);
                                    }
                                }
                                break;
                            default:
                                break;
                        }

                    }
                };
                syncChooseAfacctnoFragment.getArgument(orderParams);
                syncChooseAfacctnoFragment.getArgument(chooseAfacctno);
            } else {
                syncChooseAfacctnoFragment.getArgument(orderParams);
                syncChooseAfacctnoFragment.getArgument(chooseAfacctno);
            }

            if (StaticObjectManager.loginInfo.IsBroker
                    && orderParams.custodycd != null
                    && !StaticObjectManager.acctnoItem.CUSTODYCD
                    .equals(orderParams.custodycd)) {
                ((ChooseAfacctno) mapFragment.get(ChooseAfacctno.class
                        .getName())).CallSyncFindCustodyCd(getResources()
                                .getString(R.string.controller_FindCustodyCd),
                        orderParams.custodycd, syncChooseAfacctnoFragment);
            } else {
                if (orderParams.afacctno != null
                        && !StaticObjectManager.acctnoItem.ACCTNO
                        .equals(orderParams.afacctno)) {
                    chooseAfacctno.CallSyncChooseAcctno(getResources()
                                    .getString(R.string.controller_ChooseAcctno),
                            orderParams.afacctno, syncChooseAfacctnoFragment);

                } else {
                    PlaceOrder placeorder = (PlaceOrder) mapFragment
                            .get(PlaceOrder.class.getName());
                    placeorder.setOrderInit(orderParams);
                    if (!placeorder.isResumed()) {
                        displayFragment(PlaceOrder.class.getName());
                    }
                }
            }
        }
    }

    /**
     * Quản lý tab footer nào đang được ch�?n thì sẽ chuyển sang màu xanh
     *
     * @param v  : button footer được ch�?n
     * @param tv : text footer được ch�?n
     */
    private void setActiveFooter(View v, TextView tv) {
        btn_placeorder.setActivated(false);
        btn_watchlist.setActivated(false);
        btn_orderlist.setActivated(false);
        btn_notify.setActivated(false);
        tv_placeorder.setTextColor(getResources().getColor(R.color.grey_text));
        tv_watchlist.setTextColor(getResources().getColor(R.color.grey_text));
        tv_orderlist.setTextColor(getResources().getColor(R.color.grey_text));
        tv_notify.setTextColor(getResources().getColor(R.color.grey_text));
        if (v != null && tv != null) {
            v.setActivated(true);
            tv.setTextColor(getResources().getColor(R.color.blue_text));
        }

    }

    @Override
    public void setShowActionBar(boolean showed) {
        if (showed) {
            myActionBar.setVisibility(View.VISIBLE);
        } else {
            myActionBar.setVisibility(View.GONE);
        }
    }

    public void showUnReadNotify(int amount) {
        if (amount == 0) {
            tv_count_notify_unread.setVisibility(View.GONE);
        } else {
            tv_count_notify_unread.setVisibility(View.VISIBLE);
        }
        tv_count_notify_unread.setText(String.valueOf(amount));
    }

    protected void isKeyboardVisible(boolean isvisible) {
        if (isvisible || (kboardHookNew_CBL.getVisibility() == View.VISIBLE)
                || (kboardSymbol.getVisibility() == View.VISIBLE)
                || (kBoardPrice.getVisibility() == View.VISIBLE)
                || (kBoardQuantity.getVisibility() == View.VISIBLE)) {
            lay_Footer.setVisibility(View.GONE);
        } else {
            lay_Footer.setVisibility(View.VISIBLE);
        }
    }
}
