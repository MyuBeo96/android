package com.fss.mobiletrading.function.orderlist;

    import android.app.Dialog;
    import android.graphics.Point;
    import android.graphics.drawable.ColorDrawable;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.os.Handler;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.View.OnClickListener;
    import android.view.View.OnLongClickListener;
    import android.view.ViewGroup;
    import android.view.Window;
    import android.view.WindowManager;
    import android.widget.AdapterView;
    import android.widget.AdapterView.OnItemSelectedListener;
    import android.widget.ListView;
    import android.widget.TextView;

    import com.fss.mobiletrading.adapter.Solenh_Adapter;
    import com.fss.mobiletrading.common.Common;
    import com.fss.mobiletrading.common.SimpleAction;
    import com.fss.mobiletrading.common.StaticObjectManager;
    import com.fss.mobiletrading.consts.StringConst;
    import com.fss.mobiletrading.function.AppConfig;
    import com.fss.mobiletrading.function.ChooseAfacctno;
    import com.fss.mobiletrading.function.placeorder.OrderSetParams;
    import com.fss.mobiletrading.object.AcctnoItem;
    import com.fss.mobiletrading.object.Order;
    import com.fss.mobiletrading.object.ResultObj;
    import com.fss.mobiletrading.object.SolenhItem;
    import com.fss.mobiletrading.object.StockItem;
    import com.msbuat.mobiletrading.AbstractFragment;
    import com.msbuat.mobiletrading.DeviceProperties;
    import com.msbuat.mobiletrading.MainActivity;
    import com.msbuat.mobiletrading.MyActionBar.Action;
    import com.msbuat.mobiletrading.R;
    import com.msbuat.mobiletrading.design.SearchStockUI;
    import com.msbuat.mobiletrading.design.SearchTextUI;
    import com.msbuat.mobiletrading.design.SearchTextUI.OnHideListener;
    import com.msbuat.mobiletrading.design.SelectorImageView;
    import com.msbuat.mobiletrading.design.TabSelector;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Timer;
    import java.util.TimerTask;

    public class NormalOrderList extends AbstractFragment {
        private static final int UPDATE_INTERVAL = 3000;
        static final String AMENDORDER = "SuccessService#AMENDORDER";
        static final String CANCELORDER = "SuccessService#CANCELORDER";
        static final String DOCANCELORDER = "DoCancelOrderService#DOCANCELORDER";
        static final String MORDER = "MorderService#MORDER";
        static final String MORDERREALTIME = "MorderService#MORDERREALTIME";
    static final String ORDERDETAILS = "OrderDetailsService#ORDERDETAILS";

    TabSelector tabSelector;
    ListView lv_Solenh;
    SearchStockUI search_Symbol;
    /**
     * only tablet
     */
    SearchTextUI searchAllText;
    /**
     * data của adapter sổ lệnh bắt buộc khi update dữ liệu mới phải đi qua hàm
     * filter
     */
    List<SolenhItem> listSolenhItem;
    Solenh_Adapter adapterSolenh;
    Action action_CancelFilter;
    Action action_CancelAllOrder;

    int currentTextView;
    int SelectedItemPosition;
    SolenhItem longClickItem = null;
    SolenhItem amendOrderItem = null;
    boolean isFiltering = false;

    String statusOrder1 = "All"; // status 1
    String statusOrder2 = "All"; // status 2
    String filterSymbol = StringConst.EMPTY; // filter by symbol
    String filterText = StringConst.EMPTY; // filter by text

    Dialog dialog_resultcancelallorder;
    TextView tv_resultcancelallorder_OK;
    ListView lv_resultcancelallorder;
    SelectorImageView selectCancelAll;
    TextView tv_cancelAll;

    List<SolenhItem> list_resultcancelallorder;
    ResultCancelAllOrderAdapter adapter_resultcancelallorder;

    Point p;
    Timer timer;
    SolenhItem cancelItem;
    private Runnable updateMOrderRunable;
    private static boolean isUpdating;

    public static NormalOrderList newInstance(MainActivity mActivity) {

        NormalOrderList self = new NormalOrderList();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.OrderList);
        return self;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle bundle) {
        View view = inflater
                .inflate(R.layout.normalorderlist, viewGroup, false);
        initView(view);
        initData();
        initListener();
        Common.setupUI(view, getActivity());
        return view;
    }

    private void initView(View view) {
        lv_Solenh = ((ListView) view.findViewById(R.id.listview_SoLenhThuong));
        tabSelector = (TabSelector) view.findViewById(R.id.tab_solenhthuong);
        search_Symbol = (SearchStockUI) view
                .findViewById(R.id.searchtext_solenhthuong_symbol);
        searchAllText = (SearchTextUI) view
                .findViewById(R.id.searchtext_solenhthuong_all);
        selectCancelAll = (SelectorImageView) view
                .findViewById(R.id.img_nororderlist_item_iscancel);
        tv_cancelAll = (TextView) view
                .findViewById(R.id.text_normalorderlist_cancelAll);
        if (searchAllText != null) {
            searchAllText.setVisibleButton(false);
            searchAllText.setVisibleClearField(true);
            searchAllText.setTextColor(getColorResource(R.color.white_text));
        }
        if (dialog_resultcancelallorder == null) {
            dialog_resultcancelallorder = new Dialog(mainActivity);
            dialog_resultcancelallorder.setCancelable(false);
            dialog_resultcancelallorder
                    .requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog_resultcancelallorder
                    .setContentView(R.layout.resultcancelallorder_layout);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog_resultcancelallorder.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog_resultcancelallorder.getWindow().setAttributes(lp);
            dialog_resultcancelallorder.getWindow().setBackgroundDrawable(
                    new ColorDrawable(0));

            tv_resultcancelallorder_OK = (TextView) dialog_resultcancelallorder
                    .findViewById(R.id.text_resultcancelallorder_positive);
            lv_resultcancelallorder = (ListView) dialog_resultcancelallorder
                    .findViewById(R.id.listview_resultcancelallorder);
            tv_resultcancelallorder_OK
                    .setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog_resultcancelallorder.dismiss();
                        }
                    });
        }
        if (DeviceProperties.isTablet && StaticObjectManager.loginInfo.IsBroker) {
            tv_cancelAll.setEnabled(false);
            tv_cancelAll
                    .setTextColor(getColorResource(R.color.header_text_color));
            selectCancelAll.setVisibility(View.GONE);
        }
    }

    private void initData() {

        if (list_resultcancelallorder == null) {
            list_resultcancelallorder = new ArrayList<SolenhItem>();
        }
        if (listSolenhItem == null) {
            listSolenhItem = new ArrayList<SolenhItem>();
        }

        if (adapter_resultcancelallorder == null) {
            adapter_resultcancelallorder = new ResultCancelAllOrderAdapter(
                    mainActivity, android.R.layout.simple_list_item_1,
                    list_resultcancelallorder);

        }

        if (adapterSolenh == null) {
            adapterSolenh = new Solenh_Adapter(mainActivity,
                    android.R.layout.simple_list_item_1, listSolenhItem);
            if (DeviceProperties.isTablet) {
                adapterSolenh.setmAmendClickAction(new SimpleAction() {

                    @Override
                    public void performAction(Object obj) {
                        if (obj instanceof SolenhItem) {
                            if (((SolenhItem) obj).isModifiable
                                    .equals(StringConst.TRUE)) {
                                amendOrderItem = (SolenhItem) obj;
                                changeAccountAndAmendOrder(amendOrderItem);
                            } else {
                                showDialogMessage(R.string.thong_bao,
                                        R.string.solenh_lenhkhongduocphepsua);
                            }
                        }
                    }
                });
            }
            adapterSolenh.setItemClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if (obj != null && obj instanceof SolenhItem) {
                        mainActivity.sendArgumentToFragment(
                                OrderDetail.class.getName(), obj);
                        mainActivity.navigateFragment(OrderDetail.class
                                .getName());
                    }
                }
            });

            adapterSolenh.setmBuyClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if (obj != null && obj instanceof SolenhItem) {
                        SolenhItem item = ((SolenhItem) obj);
                        mainActivity.setOrderToPlaceOrder(new OrderSetParams(
                                item.CustodyCd, item.AfAcctno, item.Symbol,
                                Order.SIDE_NB, StringConst.EMPTY, null));
                    }

                }
            });
            adapterSolenh.setmSellClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if (obj != null && obj instanceof SolenhItem) {
                        SolenhItem item = ((SolenhItem) obj);
                        mainActivity.setOrderToPlaceOrder(new OrderSetParams(
                                item.CustodyCd, item.AfAcctno, item.Symbol,
                                Order.SIDE_NS, StringConst.EMPTY, null));
                    }
                }
            });
            adapterSolenh.setmCancelClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if (obj instanceof SolenhItem
                            && ((SolenhItem) obj).isCancellable.equals("true")) {
                        cancelItem = (SolenhItem) obj;
                        adapterSolenh.selectedCancelAllItem(false);
                        showDialogMessage(
                                getStringResource(R.string.thong_bao),
                                getStringResource(R.string.BanCoMuonHuyLenhKhong),
                                new SimpleAction() {

                                    @Override
                                    public void performAction(Object obj) {
                                        // positive
                                        cancelOrder();
                                    }
                                }, new SimpleAction() {

                                    @Override
                                    public void performAction(Object obj) {
                                        // negative
                                    }
                                }, getStringResource(R.string.Yes),
                                getStringResource(R.string.No));
                    } else {
                        showDialogMessage(R.string.thong_bao,
                                R.string.solenh_lenhkhongduocphephuy);
                    }
                }
            });
            if (StaticObjectManager.loginInfo.IsBroker) {

                adapterSolenh.setmItemLongClickAction(new SimpleAction() {
                    @Override
                    public void performAction(Object obj) {
                        if (obj instanceof SolenhItem) {
                            longClickItem = (SolenhItem) obj;
                            lv_Solenh.performLongClick();
                        }
                    }
                });
            }
        }
        lv_resultcancelallorder.setAdapter(adapter_resultcancelallorder);
        lv_Solenh.setAdapter(adapterSolenh);
        lv_Solenh.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        // registerForContextMenu(lv_Solenh);
    }

    private void initListener() {
        if (DeviceProperties.isTablet) {
            selectCancelAll.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    selectCancelAll.setActivated(!selectCancelAll.isActivated());
                    adapterSolenh.selectedCancelAllItem(selectCancelAll
                            .isActivated());
                }
            });

            tv_cancelAll.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    final String orderIds = adapterSolenh
                            .getSelectedCancelItem();
                    if (orderIds.length() == 0) {
                        showDialogMessage(R.string.thong_bao,
                                R.string.KhongCoLenhDeHuy);
                    } else {
                        showDialogMessage(
                                getStringResource(R.string.HuyLenh),
                                getStringResource(R.string.BanCoMuonHuyCacLenhDaChonKhong),
                                new SimpleAction() {

                                    @Override
                                    public void performAction(Object obj) {
                                        CallDoCancelOrder(orderIds);
                                        selectCancelAll.setActivated(false);
                                        adapterSolenh
                                                .selectedCancelAllItem(false);
                                    }
                                }, new SimpleAction() {

                                    @Override
                                    public void performAction(Object obj) {
                                    }
                                }, getStringResource(R.string.Yes),
                                getStringResource(R.string.No));
                    }

                }
            });

            searchAllText.getEditText().addTextChangedListener(
                    new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {

                        }

                        @Override
                        public void beforeTextChanged(CharSequence s,
                                                      int start, int count, int after) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            filterText = s.toString().toLowerCase();
                            filterOrder();
                        }
                    });
        } else {
            search_Symbol.setOnHideListener(new OnHideListener() {

                @Override
                public void onhide() {
                    filterSymbol = StringConst.EMPTY;
                    filterOrder();
                }
            });

            search_Symbol.getEditText().addTextChangedListener(
                    new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {

                        }

                        @Override
                        public void beforeTextChanged(CharSequence s,
                                                      int start, int count, int after) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            filterSymbol = s.toString();
                            filterOrder();
                        }
                    });
        }

        tabSelector.setOnTabSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        statusOrder1 = "All";
                        statusOrder2 = "All";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        break;
                    case 1:
                        statusOrder1 = "8";
                        statusOrder2 = "2";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        // CallMOrder(MORDER);
                        break;
                    case 2:
                        statusOrder1 = "4";
                        statusOrder2 = "14";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        // CallMOrder(MORDER);
                        break;
                    case 3:
                        statusOrder1 = "3";
                        statusOrder2 = "3";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        // CallMOrder(MORDER);
                        break;
                    case 4:
                        statusOrder1 = "12";
                        statusOrder2 = "12";
                        adapterSolenh.isUpdate = true;
                        filterOrder();
                        // CallMOrder(MORDER);
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onPause() {
        super.onPause();
        stopUpdateWatchList();
        if (DeviceProperties.isTablet) {
            searchAllText.getEditText().getText().clear();
        }
        CancelTimer();
    }

    public void onResume() {
        super.onResume();
        tabSelector.setItemSelected(0);
        filterSymbol = StringConst.EMPTY;
        startUpdateWatchList();
        AsynchTaskTimer();
    }

    private void startUpdateWatchList() {
        if (!isUpdating) {
            isUpdating = true;
            CallMOrder(MORDERREALTIME);
        }
    }

    private void stopUpdateWatchList() {
        isUpdating = false;
    }

    @Override
    public void onShowed() {
        super.onShowed();
        AsynchTaskTimer();
        startUpdateWatchList();
    }

    @Override
    public void onHide() {
        super.onHide();
        CancelTimer();
        stopUpdateWatchList();
    }

    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        if (action_CancelAllOrder == null) {
            action_CancelAllOrder = new Action() {

                @Override
                public void performAction(View view) {

                    if (listSolenhItem.size() == 0) {
                        showDialogMessage(R.string.thong_bao,
                                R.string.KhongCoLenhDeHuy);
                    } else {
                        showDialogMessage(
                                getStringResource(R.string.HuyTatCaLenh),
                                getStringResource(R.string.BanCoMuonHuyTatCaKhong),
                                new SimpleAction() {

                                    @Override
                                    public void performAction(Object obj) {
                                        CallDoCancelOrder();
                                    }
                                }, new SimpleAction() {

                                    @Override
                                    public void performAction(Object obj) {
                                    }
                                }, getStringResource(R.string.Yes),
                                getStringResource(R.string.No));
                    }

                }

                @Override
                public String getText() {
                    return mainActivity.getStringResource(R.string.HuyHet);
                }

                @Override
                public int getDrawable() {
                    return 0;
                }
            };
        }

        if (action_CancelFilter == null) {
            action_CancelFilter = new Action() {

                @Override
                public void performAction(View view) {
                    if (search_Symbol.isVisible()) {
                        search_Symbol.hide();
                    } else {
                        search_Symbol.show();
                    }
                }

                @Override
                public int getDrawable() {
                    return R.drawable.ic_search32;
                }

                @Override
                public String getText() {
                    return null;
                }
            };
        }
        if (StaticObjectManager.loginInfo.IsBroker) {
            mainActivity.addAction(action_CancelFilter);
        } else {
            mainActivity.addAction(action_CancelAllOrder);
        }
    }

    private void cancelOrder() {
        if (cancelItem != null) {
            CallCancelOrder(cancelItem);
        }
    }

    private void CallCancelOrder(SolenhItem item) {
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value.add(getStringResource(R.string.controller_CancelOrder));
        }
        {
            list_key.add("OrderId");
            list_value.add(item.OrderId);
        }
        {
            list_key.add("CustodyCd");
            list_value.add(item.CustodyCd);
        }
        {
            list_key.add("AfAcctno");
            list_value.add(item.AfAcctno);
        }
        {
            list_key.add("Symbol");
            list_value.add(item.Symbol);
        }
        {
            list_key.add("Side");
            list_value.add(item.Side);
        }
        {
            list_key.add("Qtty");
            list_value.add(item.Qtty);
        }
        {
            list_key.add("PriceType");
            list_value.add(item.PriceType);
        }
        {
            list_key.add("Price");
            list_value.add(item.Price);
        }

        StaticObjectManager.connectServer.callHttpPostService(CANCELORDER,
                this, list_key, list_value);
    }

    private void CallDoCancelOrder() {
        StringBuilder builder = new StringBuilder();
        for (SolenhItem item : listSolenhItem) {
            builder.append(item.OrderId + ",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value
                    .add(getStringResource(R.string.controller_doOrderCancel));
        }
        {
            list_key.add("pv_oderIDs");
            list_value.add(builder.toString());
        }
        {
            list_key.add("pv_Pin");
            list_value.add(null);
        }
        StaticObjectManager.connectServer.callHttpPostService(DOCANCELORDER,
                this, list_key, list_value);
    }

    private void CallDoCancelOrder(String orderIds) {

        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            list_value
                    .add(getStringResource(R.string.controller_doOrderCancel));
        }
        {
            list_key.add("pv_oderIDs");
            list_value.add(orderIds);
        }
        {
            list_key.add("pv_Pin");
            list_value.add(null);
        }
        StaticObjectManager.connectServer.callHttpPostService(DOCANCELORDER,
                this, list_key, list_value);
    }

    private void CallMOrder(String key) {
        try {
            List<String> list_key = new ArrayList<String>();
            List<String> list_value = new ArrayList<String>();
            {
                list_key.add("link");
                list_value.add(getStringResource(R.string.controller_MOrder));
            }
            {
                list_key.add("pv_custodycd");
                list_value.add(StaticObjectManager.acctnoItem.CUSTODYCD);
            }
            StaticObjectManager.connectServer.callHttpPostService(key, this,
                    list_key, list_value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AsynchTaskTimer() {
        if (!AppConfig.getInstance().enableSSO) {
            final Handler handler = new Handler();

            TimerTask timertask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            try {
                                CallMOrder(MORDERREALTIME);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };
            timer = new Timer();
            timer.schedule(timertask, 0, UPDATE_INTERVAL);
        }
    }

    private void CancelTimer() {
        if (!AppConfig.getInstance().enableSSO) {
            timer.cancel();
        }
    }

    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
    }

    @Override
    protected void process(String key, ResultObj rObj) {
        switch (key) {
            case MORDER:
                if (rObj.obj != null) {
                    listSolenhItem.clear();
                    listSolenhItem.addAll((List<SolenhItem>) rObj.obj);
                    filterOrder();
                }
                break;
            case MORDERREALTIME:
                if (rObj.obj != null) {
                    listSolenhItem.clear();
                    listSolenhItem.addAll((List<SolenhItem>) rObj.obj);
                    filterOrder();
                }
                break;
            case CANCELORDER:
                showDialogMessage(getStringResource(R.string.thong_bao),
                        getStringResource(R.string.Giaodichthanhcong));
                break;
            case AMENDORDER:
                showDialogMessage(getStringResource(R.string.thong_bao),
                        getStringResource(R.string.Giaodichthanhcong));
                break;
            case DOCANCELORDER:
                // showDialogMessage(getStringResource(R.string.thong_bao),
                // rObj.EM);
                list_resultcancelallorder.clear();
                list_resultcancelallorder.addAll((List<SolenhItem>) rObj.obj);
                adapter_resultcancelallorder.notifyDataSetChanged();
                dialog_resultcancelallorder.show();
                break;
            case ChooseAfacctno.CHANGE_ACCTNO:
                ChooseAfacctno chooseAfacctno = (ChooseAfacctno) mainActivity.mapFragment
                        .get(ChooseAfacctno.class.getName());
                chooseAfacctno.changeAfacctno(amendOrderItem.AfAcctno);
                amendOrder(amendOrderItem);
                break;
            case ChooseAfacctno.FINDCUSTODYCD:
                if (rObj.obj != null) {
                    List<AcctnoItem> list = ((List<AcctnoItem>) rObj.obj);
                    chooseAfacctno = (ChooseAfacctno) mainActivity.mapFragment
                            .get(ChooseAfacctno.class.getName());
                    if (chooseAfacctno != null) {
                        chooseAfacctno.changeCustodyCd(list);
                        ChooseAfacctno.CallChooseAcctno(amendOrderItem.AfAcctno,
                                this);
                    }
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
            case MORDERREALTIME:
                break;
            case MORDER:
                break;
            case CANCELORDER:
                break;
            case AMENDORDER:
                break;
            case DOCANCELORDER:
                break;

            default:
                break;
        }
    }

    @Override
    protected void processNull(String key) {

        super.processNull(key);
        CancelTimer();
    }

    static String STATUS_ALL = "All";
    int filterDataHashcode;

    private void filterOrder() {
        FilterData filterData = new FilterData();
        filterData.list = listSolenhItem;
        filterData.adapterSolenh = adapterSolenh;
        filterData.statusOrder1 = statusOrder1;
        filterData.statusOrder2 = statusOrder2;
        filterData.filterText = filterText;
        filterData.hashCode = filterData.hashCode();
        filterDataHashcode = filterData.hashCode;
        filterData.execute(StringConst.EMPTY);
    }

    class FilterData extends AsyncTask<String, String, List<Integer>> {
        int hashCode;
        String statusOrder1;
        String statusOrder2;
        String filterText;
        Solenh_Adapter adapterSolenh;
        List<SolenhItem> list;

        @Override
        protected List<Integer> doInBackground(String... params) {
            List<Integer> filterItems = new ArrayList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                SolenhItem item = list.get(i);
                if (statusOrder1.equals(STATUS_ALL)) {

                } else {
                    if (!(item.statusValue.equals(statusOrder1))
                            && !(item.statusValue.equals(statusOrder2))) {
                        continue;
                    }
                }
                if (filterText.length() > 0) {
                    String latin = Common.convertUTF8ToLatin(filterText);
                    if (latin != null
                            && !(list.get(i).toString().contains(latin))) {
                        continue;
                    }
                }
                filterItems.add(i);
            }
            return filterItems;
        }

        @Override
        protected void onPostExecute(List<Integer> result) {
            super.onPostExecute(result);
            if (hashCode == filterDataHashcode) {
                adapterSolenh.setFilterItemPosition(result);
                adapterSolenh.notifyDataSetChanged();
            }
        }
    }

    private void changeAccountAndAmendOrder(SolenhItem item) {
        if (StaticObjectManager.loginInfo.IsBroker
                && item.CustodyCd != null
                && !StaticObjectManager.acctnoItem.CUSTODYCD
                .equals(item.CustodyCd)) {
            ChooseAfacctno.CallFindCustodyCd(item.CustodyCd, this);
        } else {
            if (item.AfAcctno != null
                    && !StaticObjectManager.acctnoItem.ACCTNO
                    .equals(item.AfAcctno)) {
                ChooseAfacctno.CallChooseAcctno(item.AfAcctno, this);
            } else {
                amendOrder(item);
            }
        }
    }

    private void amendOrder(SolenhItem item) {
        boolean isquantityamend;
        if (StaticObjectManager.getStockItem(item.Symbol) != null
                && StaticObjectManager.getStockItem(item.Symbol)
                .getTradePlace().equals(StockItem.HOSE)) {
            isquantityamend = false;
        } else {
            isquantityamend = true;
        }
        AmendOrderModel amendOrderModel = new AmendOrderModel(item.CustodyCd,
                item.AfAcctno, item.Symbol, item.Side, item.Price, item.Qtty,
                item.PriceType, item.Qtty, isquantityamend, true, item.OrderId);
        mainActivity.sendArgumentToFragment(AmendOrder.class.getName(),
                amendOrderModel);
        mainActivity.navigateFragment(AmendOrder.class.getName());
    }

    @Override
    public void refresh() {
        super.refresh();
        CallMOrder(MORDERREALTIME);
    }
}
