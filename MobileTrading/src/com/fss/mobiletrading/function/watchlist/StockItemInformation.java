package com.fss.mobiletrading.function.watchlist;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.function.news.NewsBySymbol;
import com.fss.mobiletrading.function.news.NewsBySymbolDetail;
import com.fss.mobiletrading.function.news.NewsDetail;
import com.fss.mobiletrading.function.placeorder.OrderSetParams;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.MyActionBar.Action;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;
import com.tcscuat.mobiletrading.design.SearchTextUI;
import com.mtrading.mobile.graph.IndexVolumeChartPoint;
import com.mtrading.mobile.graph.TradeLog;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StockItemInformation extends AbstractFragment {
	StockIndex stockIndex;
	NewsBySymbol newsBySymbol;
	NewsDetail newsDetail;
	LinearLayout container_stockIndex;
	LinearLayout container_news;
	String symbol;
	//ScrollView scrollview;
	/**
	 * only tablet
	 */
	Button btn_graph;
	Button btn_news;
	LinearLayout container_graph;
	LinearLayout container_newsdetail;
	private StockDetailsGraph graphChart;
	RelativeLayout lay_news;
	SearchTextUI searchTextUI;
	ImageView img_expand;
	ImageView img_refresh;
	FragmentManager fm;
	TradeLog tradeLog;

	public static StockItemInformation newInstance() {
		StockItemInformation self = new StockItemInformation();
		return self;
	}

	protected int getLayoutId() {
		return R.layout.stockiteminformation;
	}

	public static StockItemInformation newInstance(MainActivity mActivity) {
		StockItemInformation self = new StockItemInformation();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.SymbolDetails);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(), container, false);
		container_stockIndex = (LinearLayout) view
				.findViewById(R.id.container_stockindex);
//		scrollview = (ScrollView) view
//				.findViewById(R.id.scrollview_stockiteminformation);
		btn_graph = (Button) view.findViewById(R.id.btn_stockinfo_graph);
		btn_news = (Button) view.findViewById(R.id.btn_stockinfo_news);
		container_graph = (LinearLayout) view
				.findViewById(R.id.container_graph_tradelog);
		container_news = (LinearLayout) view
				.findViewById(R.id.container_newsbysymbol);
		container_newsdetail = (LinearLayout) view
				.findViewById(R.id.stockitem_information_container_newsdetails);
		tradeLog = (TradeLog) view.findViewById(R.id.listview_tradelog);
		img_expand = (ImageView) view.findViewById(R.id.img_stockinfo_expand);
		img_refresh = (ImageView) view.findViewById(R.id.img_refresh);
		searchTextUI = (SearchTextUI) view
				.findViewById(R.id.searchtext_listview);
		searchTextUI.setVisibleButton(false);
		searchTextUI.setVisibleClearField(true);
		lay_news = (RelativeLayout) view.findViewById(R.id.lay_news);
		//setActivated(btn_news);
		//20190121 Toannds set lai active nut xem theo TG
		setActivated(btn_graph);
		init();
		initListener();
		return view;
	}

	private void init() {
		fm = getChildFragmentManager();
	}

	private void initListener() {
		btn_graph.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setActivated(v);
			}
		});

		btn_news.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setActivated(v);
			}
		});
		img_expand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				expandNewsDetail(!isExpandNewsDetail);
			}
		});
		img_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newsBySymbol.refresh();
			}
		});
//		scrollview.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				Log.d("1234","4567");
//				return true;
//			}
//		});
//		scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//			@Override
//			public void onScrollChanged() {
//				Log.d("TAG", "onScrollChanged: ");
//				// DO SOMETHING WITH THE SCROLL COORDINATES
//			}
//		});
		searchTextUI.getEditText().addTextChangedListener(new TextWatcher() {
			int DELAYTIME = 500;
			Timer timer = new Timer();

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
				newsBySymbol.setFilterText(Common.convertUTF8ToLatin(s
						.toString().toLowerCase()));
				timer.cancel();
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						mainActivity.delay.post(new Runnable() {
							@Override
							public void run() {
								newsBySymbol.notifyFilter();
							}
						});
					}
				}, DELAYTIME);
			}
		});
	}

	private void initFragment_Mobile() {
		if (stockIndex == null) {
			stockIndex = (StockIndex) mainActivity.mapFragment
					.get(StockIndex.class.getName());
		}
		if (newsBySymbol == null) {
			newsBySymbol = (NewsBySymbol) mainActivity.mapFragment
					.get(NewsBySymbol.class.getName());
		}
		fm.beginTransaction()
				.replace(container_stockIndex.getId(), stockIndex,
						stockIndex.getTag()).commit();

		fm.beginTransaction()
				.replace(container_news.getId(), newsBySymbol,
						newsBySymbol.getTag()).commit();
		fm.executePendingTransactions();
		// mainActivity.displayFragment(StockIndex.class.getName(),
		// container_stockIndex.getId());
		// mainActivity.displayFragment(NewsBySymbol.class.getName(),
		// container_news.getId());
	}

	private void initFragment_Tablet() {
		initFragment_Mobile();
		graphChart = new StockDetailsGraph();
		stockIndex.addObserver(tradeLog);
		graphChart.setTradeLog(tradeLog);
		tradeLog.setGraph(graphChart);
		if (newsDetail == null) {
			newsDetail = (NewsDetail) mainActivity.mapFragment
					.get(NewsBySymbolDetail.class.getName());
		}
		FragmentManager fm = getChildFragmentManager();
		fm.beginTransaction()
				.replace(container_newsdetail.getId(), newsDetail,
						newsDetail.getTag()).commit();
		fm.beginTransaction()
				.replace(R.id.container_graphview, graphChart,
						graphChart.getTag()).commit();
		// fm.beginTransaction()
		// .replace(R.id.listview_tradelog, tradelog, tradelog.getTag())
		// .commit();
		fm.executePendingTransactions();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (DeviceProperties.isTablet) {
			initFragment_Tablet();
		} else {
			initFragment_Mobile();
		}
	}

	@Override
	public void onShowed() {
		super.onShowed();
		stockIndex.onShowed();
		newsBySymbol.onShowed();
		if (DeviceProperties.isTablet) {
			newsDetail.onShowed();
		}
	}

	@Override
	public void onHide() {
		super.onHide();
		stockIndex.onHide();
		newsBySymbol.onHide();
		if (DeviceProperties.isTablet) {
			newsDetail.onHide();
		}
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoAction();
		mainActivity.setTitleActionBar(symbol);
		if (!DeviceProperties.isTablet) {
			mainActivity.addAction(new Action() {

				@Override
				public void performAction(View view) {
					mainActivity.setOrderToPlaceOrder(new OrderSetParams(null,
							null, symbol, null, null, null));
				}

				@Override
				public int getDrawable() {
					return R.drawable.ic_datlenh_white;
				}

				@Override
				public String getText() {
					return null;
				}
			});
		}
	}

	@Override
	protected void performBackAction() {
		if (!DeviceProperties.isTablet) {
			mainActivity.displayFragment(WatchList.class.getName());
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		fm.beginTransaction().remove(stockIndex).commit();
		fm.beginTransaction().remove(newsBySymbol).commit();

		if (DeviceProperties.isTablet) {
			fm.beginTransaction().remove(newsDetail).commit();
			fm.beginTransaction().remove(graphChart).commit();
		}
		searchTextUI.getEditText().getText().clear();
	}

	/**
	 * Truy�?n vào mã chứng khoán cần lấy thông tin
	 *
	 * @param symbol
	 */
	public void receiverParameter(String symbol) {
		this.symbol = symbol;
		if (graphChart != null) {
			graphChart.resetChart();
		}
		// truyền mã chứng khoán để xem các chỉ số
		if (!isResumed()) {
			mainActivity.displayFragment(StockItemInformation.class.getName());
		}
		StockIndex stockIndex = (StockIndex) mainActivity.mapFragment
				.get(StockIndex.class.getName());
		if (stockIndex != null) {

			stockIndex.receiverparameter(symbol);
		}
		NewsBySymbol newsBySymbol = (NewsBySymbol) mainActivity.mapFragment
				.get(NewsBySymbol.class.getName());
		// truyền mã chứng khoán để xem tin tức
		if (newsBySymbol != null) {
			newsBySymbol.receiverParameter(symbol);
		}
	}

	private void setActivated(View view) {
		if (view == null) {
			return;
		}
		if (view.getId() == btn_graph.getId()) {
			if (!btn_graph.isActivated()) {
				if (isExpandNewsDetail) {
					expandNewsDetail(false);
				}
				btn_graph.setActivated(true);
				btn_news.setActivated(false);
				lay_news.setVisibility(View.GONE);
				container_graph.setVisibility(View.VISIBLE);
				tradeLog.setVisibility(View.VISIBLE);
				// drawChartView(getDataChart(stockDetailsItem.data));
				Log.d("123ss","123");
			}
		} else {
			if (!btn_news.isActivated()) {
				btn_graph.setActivated(false);
				btn_news.setActivated(true);
				lay_news.setVisibility(View.VISIBLE);
				container_graph.setVisibility(View.GONE);
				tradeLog.setVisibility(View.GONE);
				Log.d("123ss","456");
			}
		}
	}

	boolean isExpandNewsDetail = false;

	private void expandNewsDetail(boolean isExpand) {
		if (isExpand) {
			container_stockIndex.setVisibility(View.GONE);
		} else {
			container_stockIndex.setVisibility(View.VISIBLE);
		}
		isExpandNewsDetail = isExpand;
	}

	private List<IndexVolumeChartPoint> getDataChart(MyJsonObject DT) {
		List<IndexVolumeChartPoint> list = new ArrayList<IndexVolumeChartPoint>();
		MyJsonArray TL = DT.getJSONArray("TL");
		int length = TL.length();
		for (int i = 0; i < length; i++) {
			MyJsonObject obj = TL.getJSONObject(i);
			try {
				list.add(new IndexVolumeChartPoint(obj
						.getString("formattedTime"), obj
						.getDouble("formattedMatchPrice"), obj
						.getDouble("formattedVol"), obj
						.getDouble("formattedChangeValue")));
			} catch (JSONException e) {
			}
		}
		return list;
	}

	protected void processNull(String key) {
		super.processNull(key);
	};

	@Override
	protected void errorProcess() {
		super.errorProcess();
	}

	@Override
	protected void process(String key, ResultObj rObj) {

	}

}
