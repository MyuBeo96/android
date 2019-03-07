package com.fss.mobiletrading.function.watchlist;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.fss.mobiletrading.adapter.BangGia_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.connector.RequestRealtime;
import com.fss.mobiletrading.connector.RequestRealtime.MyRequest;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.BangGia_Item;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.MainActivity_Tablet;
import com.fscuat.mobiletrading.MyActionBar.Action;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.SearchStockUI;
import com.fscuat.mobiletrading.design.TabSelector;
import com.fss.mobiletrading.object.StockItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class WatchList extends AbstractFragment {

    protected static final int UPDATE_INTERVAL = 3000;
    private static final int HIGHLIGHT_INTERVAL = 1000;
    public static final String HNX = "02";
    public static final String HOSE = "10";
    public static final String UPCOM = "04";
    public static final int index_UPCOM = 3;
    public static final int index_HNX = 2;
    public static final int index_HOSE = 1;
    public static final int index_ALL = 0;

    static final String SEARCH_SYMBOL_PATTERN = "\"Symbol\":\"";

    static final String GETALLWATCHLISTDEFAULT = "GetAllWatchlistDefaultService#GETALLWATCHLISTDEFAULT";
    static final String GETUPDATEWATCHLISTPERIODIC = "GetAllWatchlistDefaultService#GETUPDATEWATCHLISTPERIODIC";
    static final String GETDEFAULTWATCHLIST = "GetDefaultWatchlistService#GETDEFAULTWATCHLIST";
    static final String RMFRWL = "SuccessService#RMFRWL";
    static final String GETWLSYMBOLS = "GetWLSymbolsParse#GETWLSYMBOLS";
    static final String GETWATCHLISTBYSYMS = "GetAllWatchlistDefaultService#GETWATCHLISTBYSYMS";

    int SelectedItemPosition;
    boolean isRealTime = false;
    boolean isUpdating = false;
    BangGia_Adapter adapterBangGia;
    Button btn_deleteList;
    List<BangGia_Item> listItemBangGia;
    List<BangGia_Item> listItemBangGiaClone;
    ListView listview_BangGia;
    Timer timer;
    EditText edt_dialog_listname;
    EditText edt_dialog_symbols;
    Button btn_dialog_accept;
    Button btn_dialog_cancel;
    String listFavId;
    Action action_SearchSymbol;
    Action action_Expand;
    Action action_addSymbols;
    TabSelector tabSelector;
    String lastSequence;
    BangGia_Item deleteItem;
    public FavWatchListItem currentFav;
    boolean changeAfacctno = false;
    Runnable clearHighLightRunable;
    SearchStockUI searchStock;

    /**
     * only tablet
     */
    ImageView img_addStock;
    /**
     * only tablet
     */
    ImageView img_expand;
    /**
     * only tablet
     */
    ImageView img_searchStock;
    /**
     * only tablet
     */
    TextView tv_favname;
    Button btn_openFav;
    RequestRealtime requestRealtime;

    public static WatchList newInstance(MainActivity mActivity) {

        WatchList self = new WatchList();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.WatchList);
        return self;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (currentFav == null) {
            currentFav = new FavWatchListItem(StringConst.EMPTY,
                    StringConst.EMPTY, StringConst.EMPTY);
        }
        requestRealtime = new RequestRealtime(RequestRealtime.MODE_DELAY,
                UPDATE_INTERVAL);
        requestRealtime.setRequest(new MyRequest() {

            @Override
            public void execute() {
                // if (lastSequence == null || lastSequence.length() == 0
                // || lastSequence.equals("0")) {
                // isReceivedResponse(GETUPDATEWATCHLISTPERIODIC);
                // return;
                // }
                CallGetAllWatchListDefaultPeriodic();
            }
        });

    }

    public View onCreateView(LayoutInflater paramLayoutInflater,
                             ViewGroup paramViewGroup, Bundle paramBundle) {
        if (mainActivity == null) {
            mainActivity = (MainActivity) getActivity();
        }
        View view = paramLayoutInflater.inflate(R.layout.watchlist,
                paramViewGroup, false);
        tabSelector = (TabSelector) view.findViewById(R.id.tab_watchlist);
        searchStock = (SearchStockUI) view
                .findViewById(R.id.searchtext_watchlist_all);
        listview_BangGia = ((ListView) view.findViewById(R.id.listview_BangGia));
        img_addStock = (ImageView) view
                .findViewById(R.id.img_watchlist_addstock);
        img_expand = (ImageView) view.findViewById(R.id.img_watchlist_expand);
        img_searchStock = (ImageView) view
                .findViewById(R.id.img_watchlist_search);
        tv_favname = (TextView) view.findViewById(R.id.text_watchlist_favname);
        btn_openFav = (Button) view.findViewById(R.id.btn_openfav);
        initialise();
        Common.setupUI(view.findViewById(R.id.banggia), getActivity());
        initialiseListener();

        return view;
    }

    public void initialise() {
        // registerForContextMenu(listview_BangGia);
        if (listItemBangGia == null) {
            listItemBangGia = new ArrayList<BangGia_Item>();
        } else {
            listItemBangGia.clear();
        }
        if (listItemBangGiaClone == null) {
            listItemBangGiaClone = new ArrayList<BangGia_Item>();
        } else {
            listItemBangGiaClone.clear();
        }
        searchStock.setVisibleButton(true);
        if (adapterBangGia == null) {
            adapterBangGia = new BangGia_Adapter(getActivity(),
                    android.R.layout.simple_list_item_1, listItemBangGiaClone);
            adapterBangGia.setItemClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if (obj != null && (obj instanceof BangGia_Item)) {
                        StockItemInformation stockItemInformation = (StockItemInformation) mainActivity.mapFragment
                                .get(StockItemInformation.class.getName());
                        if (stockItemInformation != null) {
                            stockItemInformation
                                    .receiverParameter(((BangGia_Item) obj).Symbol);
                        }
//                        StockIndex stockIndex = (StockIndex) mainActivity.mapFragment
//                                .get(StockIndex.class.getName());
//                        if(stockIndex != null){
//                            stockIndex.receiverparameter(((BangGia_Item) obj).Symbol);
//                        }
                    }
                }
            });
            adapterBangGia.setmCancelClickAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if (obj != null && (obj instanceof BangGia_Item)) {
                        deleteItem = (BangGia_Item) obj;
                        showDialogMessage(
                                mainActivity
                                        .getStringResource(R.string.XacNhan),
                                mainActivity
                                        .getStringResource(R.string.BanCoMuonXoaMa)
                                        + StringConst.SPACE
                                        + deleteItem.Symbol
                                        + "?",
                                new SimpleAction() {

                                    @Override
                                    public void performAction(Object obj) {
                                        CallRmFrWL(deleteItem.Symbol,
                                                currentFav.CriteriaId);
                                    }
                                }, new SimpleAction() {

                                    @Override
                                    public void performAction(Object obj) {

                                    }
                                }, mainActivity
                                        .getStringResource(R.string.DongY),
                                mainActivity.getStringResource(R.string.Cancel));

                    }
                }
            });
        } else {
            adapterBangGia.loadData();
        }

        listview_BangGia.setAdapter(adapterBangGia);

        if (clearHighLightRunable == null) {
            clearHighLightRunable = new Runnable() {

                @Override
                public void run() {
                    try {
                        clearHighLight();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }
    }

    public void initialiseListener() {
        if (DeviceProperties.isTablet) {
            img_addStock.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (DeviceProperties.isTablet) {
                        MainActivity_Tablet mainActivity_Tablet = (MainActivity_Tablet) mainActivity;
                        mainActivity_Tablet.showFullPriceBoard(false);
                        onClickAddSymbols();
                    } else {
                        onClickAddSymbols();
                    }

                }
            });
            img_expand.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (DeviceProperties.isTablet) {
                        MainActivity_Tablet mainActivity_Tablet = (MainActivity_Tablet) mainActivity;
                        if (mainActivity_Tablet.frag_watchlist.isResumed()) {
                            mainActivity_Tablet.showFullPriceBoard(true);
                        } else {
                            mainActivity_Tablet.showFullPriceBoard(false);
                        }

                    } else {
                        onClickExpand();
                    }

                }
            });
            img_searchStock.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onClickSearchSymbol();
                }
            });
            btn_openFav.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onOpenFavList();
                }
            });
        }
        tabSelector.setOnTabSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case index_ALL:
                        if (!StringConst.EMPTY.equals(currentFav.MarketId)) {
                            currentFav.setMarketId(StringConst.EMPTY);
                            onChangeFavListener();
                        }
                        break;
                    case index_HOSE:
                        if (!HOSE.equals(currentFav.MarketId)) {
                            currentFav.setMarketId(HOSE);
                            onChangeFavListener();
                        }
                        break;
                    case index_HNX:
                        if (!HNX.equals(currentFav.MarketId)) {
                            currentFav.setMarketId(HNX);
                            onChangeFavListener();
                        }
                        break;
                    case index_UPCOM:
                        if (!UPCOM.equals(currentFav.MarketId)) {
                            currentFav.setMarketId(UPCOM);
                            onChangeFavListener();
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listview_BangGia.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                StockItemInformation stockItemInformation = (StockItemInformation) mainActivity.mapFragment
                        .get(StockItemInformation.class.getName());
                if (stockItemInformation != null) {
                    stockItemInformation.receiverParameter(listItemBangGiaClone
                            .get(position).Symbol);
                }
//                StockIndex stockIndex = (StockIndex) mainActivity.mapFragment
//                        .get(StockIndex.class.getName());
//                if (stockIndex != null) {
//                    stockIndex.receiverparameter(listItemBangGiaClone.get(position).Symbol);
//                    Log.i("ClickItem", " "+ listItemBangGiaClone.get(position).Symbol);
//                }
            }
        });
        listview_BangGia
                .setOnItemLongClickListener(new OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {

                        SelectedItemPosition = position;
                        return false;
                    }
                });

        searchStock.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterBySymbol(s.toString());
            }
        });

    }

    public void setHomeLogoAction() {
        if (!DeviceProperties.isTablet) {
            mainActivity.setHomeLogoAction(new Action() {

                @Override
                public void performAction(View view) {
                    onOpenFavList();
                }

                @Override
                public int getDrawable() {
                    return R.drawable.ic_listwatchlist;
                }

                @Override
                public String getText() {
                    return null;
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
        lastSequence = StringConst.EMPTY;
        // edt_SearchSymbol.setText(StringConst.EMPTY);
        if (currentFav == null || currentFav.getCriteriaId() == null
                || currentFav.getCriteriaId().length() == 0) {
            CallGetDefaultWatchList();
        } else {
            onChangeFavListener();
        }
        requestRealtime.run();
    }

    @Override
    public void onShowed() {
        super.onShowed();
        if (changeAfacctno) {
            onChangeFavListener();
            changeAfacctno = false;
        } else {
            lastSequence = StringConst.EMPTY;
        }
        requestRealtime.run();
    }

    public void onPause() {
        super.onPause();
        if (!DeviceProperties.isTablet) {
            searchStock.hide();
        }
        requestRealtime.stop();
    }

    @Override
    public void onHide() {
        super.onHide();
        requestRealtime.stop();
    }

    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoActionMenu();
        setHomeLogoAction();
        if (action_SearchSymbol == null) {
            action_SearchSymbol = new Action() {

                @Override
                public void performAction(View view) {
                    onClickSearchSymbol();
                }

                @Override
                public int getDrawable() {

                    return R.drawable.ic_priceboard_search;
                }

                @Override
                public String getText() {

                    return null;
                }
            };
        }
        if (action_Expand == null) {
            action_Expand = new Action() {

                @Override
                public void performAction(View view) {
                    onClickExpand();
                }

                @Override
                public int getDrawable() {

                    return R.drawable.ic_priceboard_expand;
                }

                @Override
                public String getText() {

                    return null;
                }
            };
        }
        if (action_addSymbols == null) {
            action_addSymbols = new Action() {

                @Override
                public void performAction(View view) {
                    onClickAddSymbols();
                }

                @Override
                public String getText() {
                    return null;
                }

                @Override
                public int getDrawable() {
                    return R.drawable.ic_priceboard_edit;
                }
            };
        }
        updateActionBar();
        mainActivity.addAction(action_Expand);
        mainActivity.addAction(action_SearchSymbol);
        if (currentFav != null) {
            mainActivity.setTitleActionBar(currentFav.CName);
        }
    }

    private void onClickAddSymbols() {
        if (Integer.parseInt(currentFav.getCriteriaId()) > 0) {
            mainActivity.sendArgumentToFragment(ListAllStocks.class.getName(),
                    currentFav);
            mainActivity.displayFragment(ListAllStocks.class.getName());
        }
    }

    private void onClickExpand() {
        if (mainActivity.currentFragment.equals(WatchList.class.getName())) {
            mainActivity.sendArgumentToFragment(FullWatchList.class.getName(),
                    currentFav);
            mainActivity.displayFragment(FullWatchList.class.getName());
        } else {
            mainActivity.displayFragment(WatchList.class.getName());
        }
    }

    private void onClickSearchSymbol() {
        searchStock.setVisibility(View.VISIBLE);
    }

    private void onOpenFavList() {
        if (mainActivity != null) {
            mainActivity.displayFragment(ListFavWatchList.class.getName());
        }
    }

    protected void CallGetAllWatchListDefault(String key) {
        if (currentFav != null) {
            WatchListService.CallGetAllWatchListDefault(lastSequence,
                    currentFav.getCriteriaId(), currentFav.getMarketId(), this,
                    key);
        }
    }

    protected void CallGetAllWatchListDefaultPeriodic() {
        if (currentFav != null) {
            WatchListService.CallGetAllWatchListDefault(lastSequence,
                    currentFav.getCriteriaId(), currentFav.getMarketId(), this,
                    GETUPDATEWATCHLISTPERIODIC);
        }
    }

    protected void CallGetDefaultWatchList() {
        WatchListService.CallGetDefaultWatchList(this, GETDEFAULTWATCHLIST);
    }

    protected void CallRmFrWL(String symbol, String id) {
        WatchListService.CallRmFrWL(symbol, id, this, RMFRWL);
    }

    protected void CallCallGetWLSymbols() {
        if (currentFav != null) {
            WatchListService.CallGetWLSymbols(currentFav.getCriteriaId(),
                    currentFav.getMarketId(), this, GETWLSYMBOLS);
        }
    }

    protected void CallGetWatchListBySyms(String syms) {
        if (currentFav != null) {
            WatchListService.CallGetWatchListBySyms(currentFav.getCriteriaId(),
                    currentFav.getMarketId(), syms, this, GETWATCHLISTBYSYMS);
        }

    }

    protected void clearHighLight() {
        adapterBangGia.clearHighLight();
    }

    protected void isLoaded() {
    }

    protected void isLoading() {
    }

    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
        if (DeviceProperties.isTablet) {
            onChangeFavListener();
        } else {
            changeAfacctno = true;
        }
    }

    @Override
    protected void process(String key, ResultObj rObj) {
        String[] keyarray = key.split(StringConst.AND);
        String criteria = null;
        String marketid = null;
        if (keyarray.length > 1) {
            criteria = keyarray[1];
        }
        if (keyarray.length > 2) {
            marketid = keyarray[2];
        }
        switch (keyarray[0]) {
            case GETUPDATEWATCHLISTPERIODIC:
                if (rObj.obj != null && criteria != null
                        && criteria.equals(currentFav.CriteriaId)) {
                    if (currentFav.MarketId.equals(StringConst.EMPTY)
                            || (marketid != null && marketid
                            .equals(currentFav.MarketId))) {
                        List<BangGia_Item> result = (List<BangGia_Item>) rObj.obj;
                        if (listItemBangGia.size() == 0) {
                            updateNewDataToListView(result);
                        } else {
                            int resultSize = result.size();
                            if (resultSize > 0) {
                                for (int i = 0; i < resultSize; i++) {
                                    BangGia_Item item = result.get(i);
                                    // update vào data gốc
                                    int listItemBangGiaSize = listItemBangGia
                                            .size();
                                    for (int j = 0; j < listItemBangGiaSize; j++) {
                                        BangGia_Item item2 = listItemBangGia.get(j);
                                        if (item.json
                                                .contains(StringConst.QUOTES
                                                        + item2.Symbol
                                                        + StringConst.QUOTES)) {
                                            item.setOldItem(item2);
                                            listItemBangGia.set(j, item);
                                            break;
                                        }
                                    }
                                }
                                // update vào listview
                                uploadChangeItem();
                                lastSequence = result.get(0).LS;
                            }
                        }
                    }
                }
                break;
            case RMFRWL:
                listItemBangGia.remove(deleteItem);
                listItemBangGiaClone.remove(deleteItem);
                String syms = currentFav.FavMem;
                currentFav.FavMem = syms.replace(deleteItem.Symbol, "");
                notifyNewDataSet();
                deleteItem = null;
                isLoaded();
                break;
            case GETDEFAULTWATCHLIST:
                currentFav = (FavWatchListItem) rObj.obj;
                onChangeFavListener();
                break;
            case GETALLWATCHLISTDEFAULT:
                // mỗi 1 request CallGetAllWatchListDefault(GETALLWATCHLISTDEFAULT);
                // được gắn thêm criteria của mình
                // nếu criteria của request trả về k trùng với criteriaId của fav
                // hiện tại thì k làm gì cả

                if (criteria != null && criteria.equals(currentFav.CriteriaId)) {
                    if (currentFav.MarketId.equals(StringConst.EMPTY)
                            || (marketid != null && marketid
                            .equals(currentFav.MarketId))) {
                        List<BangGia_Item> result = (List<BangGia_Item>) rObj.obj;
                        // int checkedITemPosition = listview_BangGia
                        // .getCheckedItemPosition();
                        // if (checkedITemPosition != AbsListView.INVALID_POSITION)
                        // {
                        // listview_BangGia.setItemChecked(checkedITemPosition,
                        // false);
                        // }

                        // update dữ liệu và vẽ lại listview
                        updateNewDataToListView(result);

                        // cập nhật lastSequence
                        if (result.size() > 0) {
                            lastSequence = result.get(0).LS;
                        }

                    }
                }
                break;

            case GETWATCHLISTBYSYMS:
                // mỗi 1 request CallGetAllWatchListDefault(GETALLWATCHLISTDEFAULT);
                // được gắn thêm criteria của mình
                // nếu criteria của request trả về k trùng với criteriaId của fav
                // hiện tại thì k làm gì cả
                if (criteria != null && criteria.equals(currentFav.CriteriaId)) {
                    if (currentFav.MarketId.equals(StringConst.EMPTY)
                            || (marketid != null && marketid
                            .equals(currentFav.MarketId))) {
                        List<BangGia_Item> result = (List<BangGia_Item>) rObj.obj;
                        int checkedITemPosition = listview_BangGia
                                .getCheckedItemPosition();
                        if (checkedITemPosition != AbsListView.INVALID_POSITION) {
                            listview_BangGia.setItemChecked(checkedITemPosition,
                                    false);
                        }
                        updateNewDataToListView(result);
                    }
                }
                break;
            case GETWLSYMBOLS:
                // create list mã chứng khoán từ dữ liệu
                if (criteria != null && criteria.equals(currentFav.CriteriaId)) {
                    if (currentFav.MarketId.equals(StringConst.EMPTY)
                            || (marketid != null && marketid
                            .equals(currentFav.MarketId))) {
                        String rs = (String) rObj.obj;
                        String[] symbols = rs.split(StringConst.SEMI);
                        // createDataListView(symbols);

                        // lấy dữ liệu 2x mã đầu
                        String subsymbols = null;
                        if (rs.length() > 80) {
                            subsymbols = rs.substring(0, 80);
                        } else {
                            subsymbols = rs.substring(0, rs.length());
                        }
                        CallGetWatchListBySyms(subsymbols);

                        // Lấy dữ liệu của tất cả các mã
                        CallGetAllWatchListDefault(GETALLWATCHLISTDEFAULT);
                    }
                }
                break;
            default:
                break;
        }
    }

    final Handler handler = new Handler();

    @Override
    protected void isReceivedResponse(String key) {
        super.isReceivedResponse(key);
        String[] keyarray = key.split("&");
        switch (keyarray[0]) {
            case GETUPDATEWATCHLISTPERIODIC:
                handler.postDelayed(clearHighLightRunable, HIGHLIGHT_INTERVAL);
                requestRealtime.trigger();
                break;
            case GETALLWATCHLISTDEFAULT:
                break;
            default:
                break;
        }
    }

    @Override
    protected void processNull(String key) {
        super.processNull(key);
        String[] keyarray = key.split("&");
        switch (keyarray[0]) {
            case GETUPDATEWATCHLISTPERIODIC:
                // handler.postDelayed(clearHighLightRunable, HIGHLIGHT_INTERVAL);
                // requestRealtime.trigger();
                break;
            case GETALLWATCHLISTDEFAULT:
                break;
            default:
                break;
        }
    }

    protected void uploadChangeItem() {
        filterBySymbol(searchStock.getEditText().getText().toString());
        notifyNewDataSet();

    }

    /**
     * tạo listItemBangGia từ mảng được truyền vào, vẽ dữ liệu các trường của
     * BangGiaItem sẽ là null, chỉ có tên mã chứng khoán
     *
     * @param array mảng chứa tên các mã chứng khoán
     */
    protected void createDataListView(String[] array) {
        if (array == null) {
            return;
        }
        listItemBangGia.clear();
        int symbolsLength = array.length;
        for (int i = 0; i < symbolsLength; i++) {
            listItemBangGia.add(new BangGia_Item(array[i]));
        }
        filterBySymbol(searchStock.getEditText().getText().toString());
    }

    /**
     * update dữ liệu tới listview bảng giá
     *
     * @param list dữ liệu update vào bảng giá, nếu list == null, clear listview
     *             bảng giá
     */
    protected void updateNewDataToListView(List<BangGia_Item> list) {
        listItemBangGia.clear();
        if (list != null) {
            listItemBangGia.addAll(list);
        }
        filterBySymbol(searchStock.getEditText().getText().toString());
    }

    public void filterBySymbol(String symbol) {
        listItemBangGiaClone.clear();
        if (!symbol.isEmpty()) {
            for (BangGia_Item item : listItemBangGia) {
                if (item.Symbol == null) {
                    if (item.json.contains(SEARCH_SYMBOL_PATTERN + symbol)) {
                        listItemBangGiaClone.add(item);
                    }
                } else {
                    if (item.Symbol.startsWith(symbol)) {
                        listItemBangGiaClone.add(item);
                    }
                }
            }
        } else {
            listItemBangGiaClone.addAll(listItemBangGia);
        }
        notifyNewDataSet();
    }

    public void notifyNewDataSet() {
        adapterBangGia.loadData();
    }

    /**
     * Gọi khi có sự thay đổi Fav
     */
    protected void onChangeFavListener() {
        lastSequence = StringConst.EMPTY;
        // khi Fav thay đổi gọi CallGetWLSymbols để lấy danh sách mã
        CallCallGetWLSymbols();

        // clear listview luôn
        updateNewDataToListView(null);
        if (currentFav != null) {
            // set tab theo marketId
            String marketid = currentFav.getMarketId();
            if (marketid != null) {
                if (marketid.equals(HNX)) {
                    tabSelector.setUIItemSelected(index_HNX);
                } else if (marketid.equals(HOSE)) {
                    tabSelector.setUIItemSelected(index_HOSE);
                } else if (marketid.equals(UPCOM)) {
                    tabSelector.setUIItemSelected(index_UPCOM);
                } else if (marketid.equals(StringConst.EMPTY)) {
                    tabSelector.setUIItemSelected(index_ALL);
                }
            }
            updateActionBar();
            // check nếu là các danh mục mặc định từ -1 -> -9 thì ẩn tab all
            try {
                int criteriaId = Integer.parseInt(currentFav.getCriteriaId());
                if(criteriaId == -2 || criteriaId == -4)
                    tabSelector.setVisibility(View.GONE);
                else {
                    tabSelector.setVisibility(View.VISIBLE);
                    if (criteriaId != -10 && criteriaId < 0) {
                        // ẩn tab all
                        tabSelector.setVisibility(View.GONE, 0);
                    } else {
                        tabSelector.setVisibility(View.VISIBLE, 0);
                    }
                }
                if (adapterBangGia != null) {
                    if (criteriaId > 0) {
                        adapterBangGia.setItemScrollable(true);
                    } else {
                        adapterBangGia.setItemScrollable(false);
                    }
                }
            } catch (NumberFormatException e) {
            }
        }
    }

    /**
     * update lại title action bar
     */
    private void updateActionBar() {
        if (currentFav == null) {
            return;
        }
        // set title actionbar thành tên danh mục
        if (DeviceProperties.isTablet) {
            tv_favname.setText(currentFav.getCName());
        } else {
            mainActivity.setTitleActionBar(currentFav.getCName());
        }

        // set option thêm mã chứng khoán vào danh sách yêu thích
        int marketid = 0;
        try {
            marketid = Integer.parseInt(currentFav.CriteriaId);
        } catch (Exception e) {
        }

        if (marketid > 0) {
            // add action action_addSymbols
            mainActivity.addAction(action_addSymbols);
            if (DeviceProperties.isTablet) {
                img_addStock.setVisibility(View.VISIBLE);
            }
        } else {
            if (DeviceProperties.isTablet) {
                img_addStock.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getArgument(Object obj) {
        super.getArgument(obj);
        if (obj instanceof FavWatchListItem) {
            currentFav = (FavWatchListItem) obj;
            if (DeviceProperties.isTablet && isResumed()) {
                if (currentFav == null || currentFav.getCriteriaId() == null
                        || currentFav.getCriteriaId().length() == 0) {
                    CallGetDefaultWatchList();
                } else {
                    onChangeFavListener();
                }
            }
        }
    }
}