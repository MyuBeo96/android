package com.fss.mobiletrading.function.portfolio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tcscuat.mobiletrading.design.LabelContentLayout;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.placeorder.OrderSetParams;
import com.fss.mobiletrading.object.Order;
import com.fss.mobiletrading.object.ResultObj;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MSTradeAppConfig;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;

import java.util.ArrayList;
import java.util.List;

public class Porfolio extends AbstractFragment {
    public static final String Refresh = "Refresh...";
    static final String GETPORFOLIO = "GetPorfolioService#GETPORFOLIO";

    Porfolio_Adapter adapter;
    List<PorfolioItem> listPorfolioItem;
    PullToRefreshListView livPorfolio;
    ListView actualListView;
    PorfolioItem porfolioItem;

    LabelContentLayout text_TongGiaTriVon;
    LabelContentLayout text_TongGiaTriTT;
    LabelContentLayout text_LaiLo;
    LabelContentLayout text_PercentLaiLo;

    TextView text_totalmarketvalue;
    TextView text_giavonTotal;
    TextView text_plTotal;
    TextView text_percentplTotal;

    public static Porfolio newInstance(MainActivity mActivity) {

        Porfolio self = new Porfolio();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.Porfolio);
        return self;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle bundle) {
        View view = inflater.inflate(R.layout.porfolio, viewGroup, false);
        livPorfolio = (PullToRefreshListView) view
                .findViewById(R.id.pull_refresh_Porfolio);
        livPorfolio.getLoadingLayoutProxy().setRefreshingLabel(Refresh);
        livPorfolio.getLoadingLayoutProxy().setPullLabel(Refresh);
        livPorfolio.getLoadingLayoutProxy().setReleaseLabel(Refresh);


        text_TongGiaTriVon = (LabelContentLayout) view.findViewById(R.id.text_porfolio_TongGTriVon);
        text_TongGiaTriTT = (LabelContentLayout) view.findViewById(R.id.text_porfolio_TongGTriTT);
        text_LaiLo = (LabelContentLayout) view.findViewById(R.id.text_porfolio_LaiLo);
        text_PercentLaiLo = (LabelContentLayout) view.findViewById(R.id.text_porfolio_PercentLaiLo);

        text_totalmarketvalue = (TextView) view.findViewById(R.id.text_totalmarketvalue);
        text_giavonTotal = (TextView) view.findViewById(R.id.text_giavonTotal);
        text_plTotal = (TextView) view.findViewById(R.id.text_plTotal);
        text_percentplTotal = (TextView) view.findViewById(R.id.text_percentplTotal);


        actualListView = livPorfolio.getRefreshableView();
        actualListView.setAdapter(this.adapter);
        initialise();
        initialiseListener();
        return view;
    }

    private void initialise() {
        if (listPorfolioItem == null) {
            listPorfolioItem = new ArrayList<PorfolioItem>();
        } else {
            listPorfolioItem.clear();
        }

        if (adapter == null) {
            adapter = new Porfolio_Adapter(getActivity(),
                    android.R.layout.simple_list_item_1, listPorfolioItem);
            adapter.setmBuyAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if (obj != null && obj instanceof PorfolioItem) {
                        mainActivity.setOrderToPlaceOrder(new OrderSetParams(
                                null, null, ((PorfolioItem) obj).getSYMBOL(),
                                Order.SIDE_NB, StringConst.EMPTY, null));
                    }

                }
            });
            adapter.setmSellAction(new SimpleAction() {

                @Override
                public void performAction(Object obj) {
                    if (obj != null && obj instanceof PorfolioItem) {
                        mainActivity.setOrderToPlaceOrder(new OrderSetParams(
                                null, null, ((PorfolioItem) obj).getSYMBOL(),
                                Order.SIDE_NS, StringConst.EMPTY, null));
                    }

                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
        actualListView.setAdapter(this.adapter);
    }

    private void initialiseListener() {
        livPorfolio.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                CallGetPorfolio();
            }
        });
    }

    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoActionMenu();
    }

    public void onResume() {
        super.onResume();
        CallGetPorfolio();
    }

    private void CallGetPorfolio() {

        List<String> list_key = new ArrayList<String>();
        List<String> list_value = new ArrayList<String>();
        {
            list_key.add("link");
            if (DeviceProperties.isTablet) {
                list_value.add(MSTradeAppConfig.controller_GetFullPorfolio);
            } else {
                list_value.add(MSTradeAppConfig.controller_GetPorfolio);
            }

        }
        {
            list_key.add("Afacctno");
            list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
        }
        StaticObjectManager.connectServer.callHttpPostService(GETPORFOLIO,
                this, list_key, list_value);
        isLoading();
    }

    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
        CallGetPorfolio();
    }

    @Override
    protected void process(String key, ResultObj rObj) {

        switch (key) {
            case GETPORFOLIO:
                if (rObj.obj != null) {
                    listPorfolioItem.clear();
                    listPorfolioItem.addAll((List<PorfolioItem>) rObj.obj);
                    adapter.notifyDataSetChanged();
                    int count = adapter.getCount();
                    Log.i("getCountdanhMuc", "" + actualListView.getAdapter().getCount() + "\n" + actualListView.getCount() + "\n" + adapter.getCount() + "\n"+rObj.obj);

                    if(count>0){
                    porfolioItem = ((List<PorfolioItem>) rObj.obj).get(count - 1);
                    displayTotal();}
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void isReceivedResponse(String key) {
        super.isReceivedResponse(key);
        isLoaded();
    }

    @Override
    protected void processErrorOther(String key, ResultObj rObj) {
        super.processErrorOther(key, rObj);
        switch (key) {
            case GETPORFOLIO:
                listPorfolioItem.clear();
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void isLoading() {
    }

    private void isLoaded() {
        if (livPorfolio != null)
            livPorfolio.onRefreshComplete();
    }

    @Override
    public void refresh() {
        super.refresh();
        CallGetPorfolio();
    }

    private void displayTotal() {
        if (porfolioItem != null) {
            if (DeviceProperties.isTablet) {
                text_giavonTotal.setText(Common.formatAmount(porfolioItem.getTotalCostValue()));
                text_totalmarketvalue.setText(Common.formatAmount(porfolioItem.getTotalMarketPrice()));
                text_plTotal.setText(Common.formatAmount(porfolioItem.getTotalUnrealized_PL()));
                text_percentplTotal.setText(porfolioItem.getTotalPercent_PL());

                text_giavonTotal.setActivated(false);
                text_totalmarketvalue.setActivated(false);
                text_plTotal.setActivated(false);
                text_percentplTotal.setActivated(false);

                if(porfolioItem.getTotalPercent_PL()!=null) {
                    int color1 = Common.getColor(porfolioItem.getTotalPercent_PL());

                    text_giavonTotal.setTextColor(color1);
                    text_plTotal.setTextColor(color1);
                    text_percentplTotal.setTextColor(color1);
                }
            } else {

                text_TongGiaTriVon.getEditContent().setText(Common.formatAmount(porfolioItem.getTOTALCOSTVALUE()));
                text_TongGiaTriTT.getEditContent().setText(Common.formatAmount(porfolioItem.getTOTALMARKETPRICE()));
                text_LaiLo.getEditContent().setText(Common.formatAmount(porfolioItem.getTOTALUNREALIZED_PL()));
                text_PercentLaiLo.getEditContent().setText(porfolioItem.getTOTALPERCENT_PL());

                text_TongGiaTriVon.setEnabled(false);
                text_TongGiaTriTT.setEnabled(false);
                text_LaiLo.setEnabled(false);
                text_PercentLaiLo.setEnabled(false);
                if(porfolioItem.getTOTALPERCENT_PL()!=null) {
                    int color1 = Common.getColor(porfolioItem.getTOTALPERCENT_PL());

                    text_TongGiaTriTT.getEditContent().setTextColor(color1);
                    text_LaiLo.getEditContent().setTextColor(color1);
                    text_PercentLaiLo.getEditContent().setTextColor(color1);
                }
            }
        }else
            return;
    }
}
