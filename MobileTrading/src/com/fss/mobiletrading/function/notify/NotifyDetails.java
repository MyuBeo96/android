package com.fss.mobiletrading.function.notify;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;

public class NotifyDetails extends AbstractFragment {
    public final static String GETDETAILNOTIFY = "GetDetailNotifyService#NOTIFYDETAIL";
    public static final String GETUNREAD = "GetUnReadService#GETUNREAD";
    public NotifyItem notifyItem;
    TextView tvNotifyDetailTittle;
    WebView webview;
    int unread = 0;
    String deviceToken = StaticObjectManager.deviceToken;
    String userName = StaticObjectManager.loginInfo.UserName;

    public static NotifyDetails newInstance(MainActivity mActivity) {

        NotifyDetails self = new NotifyDetails();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.NotifyDetails);
        return self;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle bundle) {
        View view = inflater.inflate(R.layout.notifydetail, viewGroup, false);
        tvNotifyDetailTittle = (TextView) view
                .findViewById(R.id.tvNotifyDetailTittle);
        this.webview = ((WebView) view.findViewById(R.id.webview_notifydetail));
        return view;
    }

    private void showDetailNewsItem(String content, WebView wv) {
        wv.getSettings().setJavaScriptEnabled(true);
        CallUnRead(this);
        mainActivity.showUnReadNotify(unread);
        wv.loadDataWithBaseURL(StringConst.EMPTY, content, "text/html",
                "UTF-8", StringConst.EMPTY);
    }

    @Override
    protected void process(String key, ResultObj rObj) {
        switch (key) {
            case GETDETAILNOTIFY:
                if (rObj.obj != null) {
                    NotifyItem notifyItem = ((NotifyItem) rObj.obj);
                    tvNotifyDetailTittle.setText(notifyItem.getShort());
                    showDetailNewsItem(notifyItem.getContent(), webview);
                }
                break;
            case GETUNREAD:
                unread = (int) rObj.obj;
                mainActivity.showUnReadNotify(unread);
                break;
            default:
                break;
        }
    }

    public void CallGetNotifyDetails(INotifier notifier, String Id) {

        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(MSTradeAppConfig.controller_NotifyDetail);
        }
        {
            list_key.add("token");
            list_value.add(deviceToken);
        }
        {
            list_key.add("username");
            list_value.add(userName);
        }
        {
            list_key.add("Id");
            list_value.add(Id);
        }
        StaticObjectManager.connectServer.callHttpPostService(GETDETAILNOTIFY,
                notifier, list_key, list_value);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (notifyItem != null) {
            CallGetNotifyDetails(this, notifyItem.ID);
        }
        CallUnRead(this);
    }
//
//    private void CallUnRead() {
//        ((NotifyFragment) mainActivity.getFragmentByName(NotifyFragment.class
//                .getName())).CallUnRead(this);
//    }

    public void CallUnRead(INotifier notifier) {
        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        NotificationService.CallUnRead(StaticObjectManager.deviceToken, android_id,
                StaticObjectManager.loginInfo.UserName, notifier, GETUNREAD);
    }

    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoAction();
    }

    @Override
    protected void performBackAction() {
        super.performBackAction();
        mainActivity.displayFragment(NotifyFragment.class.getName());
    }

    @Override
    public void getArgument(Object obj) {
        super.getArgument(obj);
        if (obj instanceof NotifyItem) {
            notifyItem = (NotifyItem) obj;
        }
    }
}
