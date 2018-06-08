package com.fss.mobiletrading.function.notify;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.portfolio.Porfolio;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.jsonservice.GetListNotifyService.NotifySequence;
import com.fss.mobiletrading.object.ResultObj;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;

import java.util.ArrayList;

public class NotifyFragment extends AbstractFragment {
    public static final String NOTIFICATION = "GetListNotifyService#NOTIFICATION";
    public static final String GETUNREAD = "GetUnReadService#GETUNREAD";
    ArrayList<NotifyItem> listNotify = null;
    NotifySequence notifySequence;
    String lastSeq = "0";
    Notify_Adapter adapter_Notify = null;
    PullToRefreshListView listview_Notifications;
    NotifyDetails notifyDetails;

    ImageView img_back;
    TextView tv_title;
    LinearLayout container_notifydetail;
    TextView tv_notifyTitle;
    WebView wv_notifyDetail;

    public static NotifyFragment newInstance(MainActivity mActivity) {

        NotifyFragment self = new NotifyFragment();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.Notification);
        return self;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle bundle) {
        View view = inflater.inflate(R.layout.notify, viewGroup, false);
        listview_Notifications = (PullToRefreshListView) view
                .findViewById(R.id.listview_notify);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        tv_title = (TextView) view.findViewById(R.id.text_title);
        container_notifydetail = (LinearLayout) view
                .findViewById(R.id.lay_notifydetails);
        tv_notifyTitle = (TextView) view.findViewById(R.id.text_notifytitle);
        wv_notifyDetail = (WebView) view
                .findViewById(R.id.webview_notifydetail);
        init();
        initListener();
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        Window w = d.getWindow();
        w.setBackgroundDrawableResource(R.drawable.bg_dialogfragment_bottom);
        WindowManager.LayoutParams l = w.getAttributes();
        w.setGravity(Gravity.RIGHT);
        l.x = 50;
        w.setDimAmount(0.5f);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return d;
    }

    @Override
    public void onStart() {
        super.onStart();
        int width = getDimenResource(R.dimen.t_dialog_notifyfragment_width);
        int height = getDimenResource(R.dimen.t_dialog_notifyfragment_height);
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(width, height);
        }
    }

    public void init() {
        lastSeq = "0";
        if (notifyDetails == null) {
            notifyDetails = (NotifyDetails) mainActivity
                    .getFragmentByName(NotifyDetails.class.getName());
        }
        if (listNotify == null) {
            listNotify = new ArrayList<NotifyItem>();
        }
        if (adapter_Notify == null) {
            adapter_Notify = new Notify_Adapter(getActivity(),
                    android.R.layout.simple_list_item_1, this.listNotify);
        } else {
            adapter_Notify.notifyDataSetChanged();
        }
        ListView actualListView = listview_Notifications.getRefreshableView();
        actualListView.setAdapter(adapter_Notify);

        listview_Notifications.getLoadingLayoutProxy().setRefreshingLabel(
                Porfolio.Refresh);
        listview_Notifications.getLoadingLayoutProxy().setPullLabel(
                Porfolio.Refresh);
        listview_Notifications.getLoadingLayoutProxy().setReleaseLabel(
                Porfolio.Refresh);
        tv_title.setText(getStringResource(R.string.Notification));
        img_back.setVisibility(View.GONE);
    }

    private void initListener() {

        img_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showNotifyDetail(false);
            }
        });
        listview_Notifications
                .setOnRefreshListener(new OnRefreshListener<ListView>() {

                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        lastSeq = "0";
                        CallListNotify();
                    }
                });
        listview_Notifications
                .setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

                    @Override
                    public void onLastItemVisible() {
                        CallListNotify();
                    }
                });
        listview_Notifications
                .setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        NotifyItem item = listNotify.get(position - 1);
                        item.hasRead = StringConst.TRUE;
                        if (DeviceProperties.isTablet) {
                            notifyDetails.CallGetNotifyDetails(
                                    NotifyFragment.this, item.ID);
                            showNotifyDetail(true);
                        } else {
                            ShowNotifyDetails(item);
                        }
                    }
                });
    }

    private void ShowNotifyDetails(NotifyItem item) {
        mainActivity
                .sendArgumentToFragment(NotifyDetails.class.getName(), item);
        mainActivity.displayFragment(NotifyDetails.class.getName());
    }

    public void CallListNotify() {
        NotificationService.CallListNotify(StaticObjectManager.deviceToken,
                StaticObjectManager.loginInfo.UserName, lastSeq, this,
                NOTIFICATION);
    }

    public void CallUnRead(INotifier notifier) {
        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        NotificationService.CallUnRead(StaticObjectManager.deviceToken, android_id,
                StaticObjectManager.loginInfo.UserName, notifier, GETUNREAD);
    }

    @Override
    protected void process(String key, ResultObj rObj) {
        switch (key) {
            case NOTIFICATION:
                if (rObj.obj != null) {
                    if (lastSeq.equals("0")) {
                        listNotify.clear();
                    }
                    notifySequence = (NotifySequence) rObj.obj;
                    listNotify.addAll(notifySequence.getList());
                    lastSeq = notifySequence.getLastSeq();
                    adapter_Notify.notifyDataSetChanged();
                } else if (rObj.obj == null) {
                }
                break;
            case GETUNREAD:
                int unread = (int) rObj.obj;
                mainActivity.showUnReadNotify(unread);
                break;
            case NotifyDetails.GETDETAILNOTIFY:
                if (rObj.obj != null) {
                    NotifyItem notifyItem = ((NotifyItem) rObj.obj);
                    tv_notifyTitle.setText(notifyItem.getShort());
                    showDetailNewsItem(notifyItem.getContent(), wv_notifyDetail);
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoActionMenu();
    }

    @Override
    protected void isReceivedResponse(String key) {
        super.isReceivedResponse(key);
        switch (key) {
            case NOTIFICATION:
                listview_Notifications.onRefreshComplete();
                break;
            case GETUNREAD:
                break;
            case NotifyDetails.GETDETAILNOTIFY:
                break;
            default:
                break;
        }
    }

    @Override
    protected void processErrorOther(String key, ResultObj rObj) {
        super.processErrorOther(key, rObj);
        switch (key) {
            case NOTIFICATION:
                listNotify.clear();
                adapter_Notify.notifyDataSetChanged();
                break;
            case GETUNREAD:
                mainActivity.showUnReadNotify(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        CallListNotify();
        CallUnRead(this);
    }

    private void showNotifyDetail(boolean isShow) {
        if (isShow) {
            container_notifydetail.setVisibility(View.VISIBLE);
            listview_Notifications.setVisibility(View.GONE);
            img_back.setVisibility(View.VISIBLE);
            tv_title.setText(getStringResource(R.string.NotifyDetails));
            adapter_Notify.notifyDataSetChanged();
        } else {
            container_notifydetail.setVisibility(View.GONE);
            listview_Notifications.setVisibility(View.VISIBLE);
            img_back.setVisibility(View.GONE);
            tv_title.setText(getStringResource(R.string.Notification));
        }
    }

    private void showDetailNewsItem(String content, WebView wv) {
        wv.getSettings().setJavaScriptEnabled(true);
        CallUnRead(this);
        wv.loadDataWithBaseURL(StringConst.EMPTY, content, "text/html",
                "UTF-8", StringConst.EMPTY);
    }

    @Override
    public void refresh() {
        super.refresh();
        lastSeq = "0";
        CallListNotify();
    }
}
