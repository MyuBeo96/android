package com.fscuat.mobiletrading;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fss.designcontrol.keyboardhook.KeyboardHook;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.common.XmlParser;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.AboutUs;
import com.fss.mobiletrading.function.AppData;
import com.fss.mobiletrading.function.ChooseAfacctno;
import com.fss.mobiletrading.function.accountconfig.ChangePassword;
import com.fss.mobiletrading.function.accountconfig.ChangeTradingPassword;
import com.fss.mobiletrading.function.authorizationinfo.AuthorizationInfo;
import com.fss.mobiletrading.function.cashtransfer.InternalCashTransferConfirm;
import com.fss.mobiletrading.function.market.T_MarketInfo;
import com.fss.mobiletrading.function.notify.NotifyFragment;
import com.fss.mobiletrading.function.orderlist.NormalOrderList;
import com.fss.mobiletrading.function.placeorder.OrderSetParams;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.function.watchlist.FullWatchList;
import com.fss.mobiletrading.function.watchlist.ListAllStocks;
import com.fss.mobiletrading.function.watchlist.ListFavWatchList;
import com.fss.mobiletrading.function.watchlist.WatchList;
import com.fss.mobiletrading.keyboard.KBoardSymbol;
import com.fss.mobiletrading.menu.MenuItemAction;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.ContactInfoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.MyActionBar.Action;

import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity_Tablet extends MainActivity {

    final String LOGOUT = "LogoutService";
    LinearLayout menulevelone;
    LinearLayout menulevelsecond;
    LinearLayout menulevelthird;
    LinearLayout contain_function;
    LinearLayout contain_watchlist;
    LinearLayout contain_watchlist_price;
    LinearLayout lay_chooseacc;
    LinearLayout lay_agentinfo;
    LinearLayout lay_customerclass;
    LinearLayout lay_Footer;

    ImageView logo;
    Button btn_Buy;
    Button btn_Sell;
    Button btn_OrderList;
    TextView tv_afacctno;
    TextView tv_customername;
    TextView tv_customerclass;
    TextView tv_agent;
    TextView tv_agentmobile;
    TextView tv_count_notify_unread;

    KBoardSymbol kboardSymbol;
    KeyboardHook kboardHookNew;
    public Dialog dlg_keyboardsymbol;
    public WatchList frag_watchlist;
    AboutUs aboutUs;
    PlaceOrder placeOrder;
    TabMenu tabMenu;

    ImageButton btn_logout;
    ImageButton btn_changepass;
    RelativeLayout notifyClick;
    ImageView img_phone1;
    ImageView img_phone2;
    ImageView img_email;
    ImageView img_face;
    ImageView img_skype;
    ImageView img_yahoo;
    TextView tv_phone1;
    TextView tv_phone2;
    TextView tv_email;
    TextView tv_face;
    TextView tv_skype;
    TextView tv_yahoo;

    String declareDialogFragments = "#" + ChooseAfacctno.class.getName() + "#"
            + InternalCashTransferConfirm.class.getName() + "#"
            + PlaceOrder.class.getName() + "#"
            + ListFavWatchList.class.getName() + "#"
            + ListAllStocks.class.getName() + "#"
            + ChangePassword.class.getName() + "#"
            + ChangeTradingPassword.class.getName() + "#"
            + AuthorizationInfo.class.getName() + "#"
            + NotifyFragment.class.getName() + "#";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activitymain_tablet);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getBaseContext().getResources().updateConfiguration(Login.newConfig,
                getBaseContext().getResources().getDisplayMetrics());

        initCommonData();
        initView();
        initData();
        if (bundle == null) {
            displayFragment(T_MarketInfo.class.getName());
        }
        aboutUs = (AboutUs) mapFragment.get(AboutUs.class.getName());
        frag_watchlist = (WatchList) mapFragment.get(WatchList.class.getName());
        placeOrder = (PlaceOrder) mapFragment.get(PlaceOrder.class.getName());
        displayFragment(WatchList.class.getName(), contain_watchlist.getId());
        initListener();
        final View activityRootView = findViewById(R.id.activitymain_tablet);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int heightDiff = activityRootView.getRootView()
                                .getHeight() - activityRootView.getHeight();
                        Log.i("MainActivityTabLet", activityRootView
                                .getRootView().getHeight()
                                + " "
                                + activityRootView.getHeight()
                                + " "
                                + heightDiff);
                        if (heightDiff > 100) {
                            isKeyboardVisible(true);
                        } else {
                            isKeyboardVisible(false);
                        }
                    }
                });
    }

    private void initView() {
        menulevelone = (LinearLayout) findViewById(R.id.lay_tablet_activitymain_menulevel1);
        menulevelsecond = (LinearLayout) findViewById(R.id.lay_tablet_activitymain_menulevel2);
        menulevelthird = (LinearLayout) findViewById(R.id.lay_tablet_activitymain_menulevel3);
        contain_function = (LinearLayout) findViewById(R.id.container_tablet_activitymain_function);
        contain_watchlist = (LinearLayout) findViewById(R.id.container_tablet_activitymain_watchlist);
contain_watchlist_price = (LinearLayout) findViewById(R.id.container_tablet_activitymain_watchlist_price);
        logo = (ImageView) findViewById(R.id.img_tablet_activitymain_logo);
        btn_Buy = (Button) findViewById(R.id.btn_tablet_activitymain_buy);
        btn_Sell = (Button) findViewById(R.id.btn_tablet_activitymain_sell);
        btn_OrderList = (Button) findViewById(R.id.btn_tablet_activitymain_orderlist);
        lay_Footer = (LinearLayout) findViewById(R.id.large_activitymain_footer);
        tv_afacctno = (TextView) findViewById(R.id.text_tablet_activitymain_afacctno);
        tv_customername = (TextView) findViewById(R.id.text_tablet_activitymain_name);
        tv_customerclass = (TextView) findViewById(R.id.text_tablet_activitymain_customerclass);
        tv_agent = (TextView) findViewById(R.id.text_tablet_activitymain_broker);
        tv_agentmobile = (TextView) findViewById(R.id.text_tablet_activitymain_brokerphone);
        lay_chooseacc = (LinearLayout) findViewById(R.id.lay_chooseacc);
        lay_customerclass = (LinearLayout) findViewById(R.id.lay_customerclass);
        lay_agentinfo = (LinearLayout) findViewById(R.id.lay_agentinfo);

        btn_logout = (ImageButton) findViewById(R.id.btn_activitymain_tablet_logout);
        notifyClick = (RelativeLayout) findViewById(R.id.lay_activitymain_notify);
        tv_count_notify_unread = (TextView) findViewById(R.id.text_activitymain_notifycounter);
        img_phone1 = (ImageView) findViewById(R.id.img_phone1);
        img_phone2 = (ImageView) findViewById(R.id.img_phone2);
        img_email = (ImageView) findViewById(R.id.img_email);
        img_face = (ImageView) findViewById(R.id.img_face);
        img_skype = (ImageView) findViewById(R.id.img_skype);
        img_yahoo = (ImageView) findViewById(R.id.img_yahoo);

        tv_phone1 = (TextView) findViewById(R.id.text_phone1);
        tv_phone2 = (TextView) findViewById(R.id.text_phone2);
        tv_email = (TextView) findViewById(R.id.text_email);
        tv_face = (TextView) findViewById(R.id.text_face);
        tv_skype = (TextView) findViewById(R.id.text_skype);
        tv_yahoo = (TextView) findViewById(R.id.text_yahoo);

        // gone
        btn_changepass = (ImageButton) findViewById(R.id.btn_changepassword);

        if (StaticObjectManager.loginInfo.IsBroker) {
            btn_changepass.setVisibility(View.VISIBLE);
            btn_OrderList.setVisibility(View.GONE);
            lay_customerclass.setVisibility(View.GONE);
            lay_agentinfo.setVisibility(View.GONE);
        }

    }

    private void initData() {

        List<MenuItemAction> list = parseData();
        tabMenu = new TabMenu(menulevelone, menulevelsecond, menulevelthird,
                list, this);
        createDialogKeyboard();
        setSubAccountInfo(StaticObjectManager.acctnoItem);
        if (AppData.language.equals(AppData.LOCALE_EN)) {
            logo.setImageResource(R.drawable.ic_fsc);
        }
    }

    @Override
    public void initCommonData() {
        super.initCommonData();
        mapFragment.put(T_MarketInfo.class.getName(),
                T_MarketInfo.newInstance(this));
    }

    long mLastBuyClickTime = 0;
    long mLastSellClickTime = 0;

    @Override
    public void initListener() {
        btn_changepass.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                displayFragment(ChangePassword.class.getName());
            }
        });
        btn_logout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                logout();
            }
        });
        btn_Buy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastBuyClickTime < 2000) {
                    return;
                }
                mLastBuyClickTime = SystemClock.elapsedRealtime();
                OrderSetParams orderParams = new OrderSetParams(null, null,
                        null, PlaceOrder.SIDE_NB, null, null, null, null);
                placeOrder.setOrderInit(orderParams);
                if (placeOrder.isResumed()) {
                } else {
                    displayFragment(PlaceOrder.class.getName());
                }
            }
        });

        btn_Sell.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastSellClickTime < 2000) {
                    return;
                }
                mLastSellClickTime = SystemClock.elapsedRealtime();
                OrderSetParams orderParams = new OrderSetParams(null, null,
                        null, PlaceOrder.SIDE_NS, null, null, null, null);
                placeOrder.setOrderInit(orderParams);
                if (placeOrder.isResumed()) {
                } else {
                    displayFragment(PlaceOrder.class.getName());
                }
            }
        });
        btn_OrderList.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                displayFragment(NormalOrderList.class.getName());
            }
        });
        lay_chooseacc.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showChooseAccountFragment();
                return false;
            }
        });

        notifyClick.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                displayFragment(NotifyFragment.class.getName());
            }
        });

        OnClickListener phone1ClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                aboutUs.callPhone(tv_phone1.getText().toString());
            }
        };
        img_phone1.setOnClickListener(phone1ClickListener);
        tv_phone1.setOnClickListener(phone1ClickListener);
        OnClickListener phone2ClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                aboutUs.callPhone(tv_phone2.getText().toString());
            }
        };
        img_phone2.setOnClickListener(phone2ClickListener);
        tv_phone2.setOnClickListener(phone2ClickListener);
        OnClickListener emailClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                aboutUs.openEmailApp();
            }
        };
        img_email.setOnClickListener(emailClickListener);
        tv_email.setOnClickListener(emailClickListener);
        OnClickListener faceClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                aboutUs.openFaceBook();
            }
        };
        img_face.setOnClickListener(faceClickListener);
        tv_face.setOnClickListener(faceClickListener);
        OnClickListener skypeClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg = aboutUs.openSkype();
                if (msg.length() > 0) {
                    showDialogMessage(getStringResource(R.string.thong_bao),
                            msg);
                }
            }
        };
        img_skype.setOnClickListener(skypeClickListener);
        tv_skype.setOnClickListener(skypeClickListener);
        OnClickListener yahooClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg = aboutUs.openYahoo();
                if (msg.length() > 0) {
                    showDialogMessage(getStringResource(R.string.thong_bao),
                            msg);
                }
            }
        };
        img_yahoo.setOnClickListener(yahooClickListener);
        tv_yahoo.setOnClickListener(yahooClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppData.language.equals(AppData.LOCALE_EN)) {
            logo.setImageResource(R.drawable.ic_fsc);
        }
        Configuration newConfig = new Configuration();
        newConfig.locale = new Locale(AppData.language);
        super.onConfigurationChanged(newConfig);
        callContactInfo();
    }

    private void callContactInfo() {
        aboutUs.CallContactInfo(new AbstractFragment() {
            @Override
            protected void process(String key, ResultObj rObj) {
                ContactInfoItem contactInfo = (ContactInfoItem) rObj.obj;
                aboutUs.initIntent(contactInfo);
                String[] phones = aboutUs.getPhones();
                if (phones != null) {
                    if (phones.length >= 1) {
                        tv_phone1.setText(phones[0]);
                    }
                    if (phones.length >= 2) {
                        tv_phone2.setText(phones[1]);
                    }

                }
            }
        });
    }

    @Override
    public void displayFragment(String className) {
        if (className.equals(WatchList.class.getName())) {
            // AbstractFragment watchlist = getFragmentByName(className);
            return;
        }
        if (declareDialogFragments.contains("#" + className + "#")) {
            AbstractFragment dialogFrag = getFragmentByName(className);
            if (dialogFrag != null
                    && (dialogFrag.getDialog() == null || !dialogFrag
                    .getDialog().isShowing())) {
                dialogFrag.show(fm, className);
                currentDialog = className;
            }
        } else if (className != currentFragment) {
            // nếu đang hiện bảng giá full thì thu nhỏ lại
            if (frag_watchlist != null && !frag_watchlist.isResumed()) {
                showFullPriceBoard(false);
            }
            AbstractFragment fragment = initFragment(className);
            if (fragment != null) {
                // xóa fragment cũ
                AbstractFragment oldfrag = getFragmentByName(currentFragment);
                if (oldfrag != null) {
                    oldfrag.dismiss();
                }
                removeFragment(oldfrag);
                // hien thi fragment moi
                if (!fragment.isAdded()) {
                    replaceFragment(contain_function.getId(), fragment);
                }
                if (navigateFragments.size() > 0) {
                    navigateFragments.clear();
                }
            }
            currentFragment = className;
            tabMenu.setLocationInMenu(className);
        }
    }

    @Override
    public void navigateFragment(String className) {
        if (className.equals(WatchList.class.getName())) {
            // AbstractFragment watchlist = getFragmentByName(className);
            return;
        }
        AbstractFragment dialogFrag = getFragmentByName(className);
        if (dialogFrag != null && !dialogFrag.isAdded()) {
            navigateFragments.add(currentFragment);
            navigateFragments.add(className);
            dialogFrag.show(fm, className);
            currentDialog = className;
        }
    }

    @Override
    public void navigateFragment(String className, Object argument) {

    }

    @Override
    public void backNavigateFragment() {
        if (navigateFragments.size() <= 0) {
            return;
        }
        String dialogName = navigateFragments.get(navigateFragments.size() - 1);
        String className = navigateFragments.get(navigateFragments.size() - 2);
        AbstractFragment frag = getFragmentByName(className);
        AbstractFragment dialogFrag = getFragmentByName(dialogName);
        if (dialogFrag != null) {
            dialogFrag.onDismiss(dialogFrag.getDialog());
        }
        navigateFragments.remove(navigateFragments.size() - 1);
        if (frag != null) {
            frag.onShowed();
        }
        navigateFragments.remove(navigateFragments.size() - 1);
    }

    @Override
    public void displayFragment(String className, int container) {
        if (className != currentFragment) {
            AbstractFragment fragment = initFragment(className);
            if (fragment != null) {
                fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(container, fragment, fragment.getTag())
                        .commit();
                currentDialog = className;
            }
        }
    }

    @Override
    public void setTitleActionBar(String title) {
    }

    public void hideBottomBar() {

    }

    public void showBottomBar() {

    }

    public void setHomeLogoAction(Action action) {

    }

    public void clearHomeLogoAction() {

    }

    public void showMenu(boolean isShow) {
    }

    public void removeAllAction() {

    }

    public void removeAction(Action action) {

    }

    public void addAction(Action action) {
    }

    private List<MenuItemAction> parseData() {

        XmlParser xmlParser = new XmlParser(this);
        List<MenuItemAction> list = new ArrayList<MenuItemAction>();
        try {
            if (AppData.language == AppData.LOCALE_VI_VN) {
                if (StaticObjectManager.loginInfo.IsBroker) {
                    list = xmlParser.parseStringAction(getResources().getXml(
                            R.xml.declare_menu_tablet_broker_vi));
                } else {
                    list = xmlParser.parseStringAction(getResources().getXml(
                            R.xml.declare_menu_tablet_vi));
                }
            } else if(AppData.language==AppData.LOCALE_EN)
            {
                if (StaticObjectManager.loginInfo.IsBroker) {
                    list = xmlParser.parseStringAction(getResources().getXml(
                            R.xml.declare_menu_tablet_broker_en));
                } else {
                    list = xmlParser.parseStringAction(getResources().getXml(
                            R.xml.declare_menu_tablet_en));
                }
            }else {
                if (StaticObjectManager.loginInfo.IsBroker) {
                    list = xmlParser.parseStringAction(getResources().getXml(
                            R.xml.declare_menu_tablet_broker_en));
                } else {
                    list = xmlParser.parseStringAction(getResources().getXml(
                            R.xml.declare_menu_tablet_zh));
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void createDialogPlaceOrder() {

        // dialogPlaceOrder = new Dialog(this);
        // dialogPlaceOrder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialogPlaceOrder.setCancelable(true);
        // dialogPlaceOrder.setContentView(R.layout.dialog_placeorder);
        // WindowManager.LayoutParams localLayoutParams = new
        // WindowManager.LayoutParams();
        // localLayoutParams
        // .copyFrom(dialogPlaceOrder.getWindow().getAttributes());
        // localLayoutParams.width = LayoutParams.WRAP_CONTENT;
        // localLayoutParams.height = LayoutParams.WRAP_CONTENT;
        // dialogPlaceOrder.getWindow().setAttributes(localLayoutParams);
        // dialogPlaceOrder.getWindow().setGravity(Gravity.TOP);
        //
        // Common.setupUI(dialogPlaceOrder.findViewById(R.id.window_placeorder),
        // dialogPlaceOrder);

    }

    private void createDialogKeyboard() {

        dlg_keyboardsymbol = new Dialog(this);
        dlg_keyboardsymbol.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg_keyboardsymbol.setCancelable(true);
        dlg_keyboardsymbol.setContentView(R.layout.dialog_keyboard);
        dlg_keyboardsymbol.getWindow()
                .setGravity(Gravity.BOTTOM | Gravity.LEFT);
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.copyFrom(dlg_keyboardsymbol.getWindow()
                .getAttributes());
        localLayoutParams.width = LayoutParams.MATCH_PARENT;
        localLayoutParams.height = LayoutParams.WRAP_CONTENT;
        localLayoutParams.y = 0; // botton margin
        localLayoutParams.x = 0; // botton margin
        dlg_keyboardsymbol.getWindow().setAttributes(localLayoutParams);
        dlg_keyboardsymbol.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dlg_keyboardsymbol.getWindow().setBackgroundDrawable(
                new ColorDrawable(0));

        kboardSymbol = (KBoardSymbol) dlg_keyboardsymbol
                .findViewById(R.id.kboardsymbol);
        kboardHookNew = (KeyboardHook) dlg_keyboardsymbol
                .findViewById(R.id.kboardhooknew);
        dlg_keyboardsymbol.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//				kboardHook.clearEdittextFocus();
                kboardSymbol.clearEdittextFocus();
            }
        });
    }

    public void dismissDialogOrderPlace() {
        // dialogPlaceOrder.dismiss();
    }

    // public boolean isPlaceOrderResume() {
    // return dialogPlaceOrder.isShowing();
    // }

    @Override
    public void setOrderToPlaceOrder(OrderSetParams p) {
        if (p == null) {
            return;
        }
        placeOrder.setOrderInit(p);
        if (placeOrder.isResumed()) {
        } else {
            displayFragment(PlaceOrder.class.getName());
        }

        // PlaceOrder_Tablet placeorder = (PlaceOrder_Tablet) mapFragment
        // .get(PlaceOrder_Tablet.class.getName());
        // placeorder.receiverparameter(p.symbolOrder, p.sideOrder,
        // p.priceOrder,
        // p.quantityOrder);
        // if (!isPlaceOrderResume()) {
        // dialogPlaceOrder.show();
        // }

    }

    @Override
    public void setShowActionBar(boolean showed) {
    }

    protected void isKeyboardVisible(boolean isvisible) {
        if (isvisible) {
            lay_Footer.setVisibility(View.GONE);
        } else {
            lay_Footer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showKBoardHook(boolean isShow, View v, String[] listStock) {
        if (isShow) {
            kboardHookNew.show(v, listStock);
            dlg_keyboardsymbol.show();
        } else {
            dlg_keyboardsymbol.dismiss();
        }
    }

    @Override
    public void showKBoardSymbol(boolean isShow, View v) {
        if (isShow) {
            kboardSymbol.setEdittextFocus(v);
            dlg_keyboardsymbol.show();
        } else {
            dlg_keyboardsymbol.dismiss();
        }
    }

    @Override
    public void onChangeAfacctno(String acctno) {
        // frag_watchlist.notifyChangeAcctNo();
        // FullWatchList fullWatchList = (FullWatchList)
        // getFragmentByName(FullWatchList.class
        // .getName());
        // fullWatchList.notifyChangeAcctNo();
        setSubAccountInfo(StaticObjectManager.acctnoItem);
        // notify sự kiện đổi tiểu khoản tới các màn hình đang resume
        int resumingFragSize = resumingFragments.size();
        for (int i = 0; i < resumingFragSize; i++) {
            AbstractFragment frag = resumingFragments.get(i);
            if (frag != null) {
                frag.notifyChangeAcctNo();
            }
        }
    }

    boolean isShowFullPriceBoard = false;

    public void showFullPriceBoard(boolean isshowfull) {
        LayoutParams pr_price = contain_watchlist_price.getLayoutParams();
        LayoutParams pr = contain_watchlist.getLayoutParams();
        if (isshowfull) {
            if (!isShowFullPriceBoard) {
                displayFragment(FullWatchList.class.getName(),
                        contain_watchlist.getId());
                fm.executePendingTransactions();
                pr_price.width = LayoutParams.MATCH_PARENT;
                pr.width = LayoutParams.MATCH_PARENT;
                isShowFullPriceBoard = true;
            }
        } else {
            if (isShowFullPriceBoard) {
                displayFragment(WatchList.class.getName(),
                        contain_watchlist.getId());
                fm.executePendingTransactions();
                pr_price.width = getResources().getDimensionPixelSize(
                        R.dimen.t_framewatchlist_width);;
                pr.width = getResources().getDimensionPixelSize(
                        R.dimen.t_framewatchlist_width);
                isShowFullPriceBoard = false;
            }
        }
    }

    private void setSubAccountInfo(AcctnoItem acctnoItem) {
        if (acctnoItem != null) {
            tv_afacctno.setText(acctnoItem.DESCNAME);
            tv_customername.setText(acctnoItem.FULLNAME);
            tv_customerclass.setText(acctnoItem.Clazz);
            tv_agent.setText(acctnoItem.Agent);
            tv_agentmobile.setText(acctnoItem.Mobile);
        } else {
            tv_customerclass.setText(StringConst.EMPTY);
            tv_agent.setText(StringConst.EMPTY);
            tv_agentmobile.setText(StringConst.EMPTY);
            tv_afacctno.setText(MSTradeAppConfig.USERNAME_DEFAULT);
            tv_customername.setText(StringConst.EMPTY);
        }
        if (StaticObjectManager.loginInfo.IsBroker) {
            tv_customername.setText(StaticObjectManager.loginInfo.UserName);
        }
    }


    @Override
    public void showKBoardPrice(boolean isShow, View v) {

    }

    @Override
    public void showKBoardQuantity(boolean isShow, View v) {

    }

    public void showUnReadNotify(int amount) {
        if (amount == 0) {
            tv_count_notify_unread.setVisibility(View.GONE);
        } else {
            tv_count_notify_unread.setVisibility(View.VISIBLE);
        }
        tv_count_notify_unread.setText(String.valueOf(amount));
    }

    public void showChooseAccountFragment() {
        navigateFragment(ChooseAfacctno.class.getName());
    }

    public void showChooseAccountFragmentWithPosition(int x, int y) {
        ChooseAfacctno frag = (ChooseAfacctno) getFragmentByName(ChooseAfacctno.class
                .getName());
        frag.setPosition(x, y);
        navigateFragment(ChooseAfacctno.class.getName());
    }
}
