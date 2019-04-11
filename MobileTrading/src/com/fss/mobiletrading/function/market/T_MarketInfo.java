package com.fss.mobiletrading.function.market;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.fss.mobiletrading.adapter.NewsAdapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.connector.RequestRealtime;
import com.fss.mobiletrading.connector.RequestRealtime.MyRequest;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.news.NewsDetail;
import com.fss.mobiletrading.function.news.NewsService;
import com.fss.mobiletrading.object.IndicesItem;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.BuildConfig;
import com.tcscuat.mobiletrading.MSTradeAppConfig;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.design.FilterArrayAdapter.Condition;
import com.tcscuat.mobiletrading.design.Indices_View;
import com.tcscuat.mobiletrading.design.SearchTextUI;

public class T_MarketInfo extends AbstractFragment {

	private static final String GRAPH_HOSE = "GRAPH_HOSE";
	private static final String GRAPH_HNX = "GRAPH_HNX";
	private static final String GRAPH_UPCOM = "GRAPH_UPCOM";
	private static final String VN_INDEX = "VN-Index";
	private static final String HNX_INDEX = "HNX-Index";
	private static final String UPCOM_INDEX = "UPCOM-Index";
	private static final int GETCHART_INTERVAL = 20000;
	private static final int UPDATE_INTERVAL = 5000;
	private static final int CLEARHIGHLIGHT_INTERVAL = 2000;
//	private static final String link_GraphHOSE = MSTradeAppConfig.address_server
//			+ "/Chart/HOSE.jpg";
//	private static final String link_GraphHNX = MSTradeAppConfig.address_server
//			+ "/Chart/HNX.jpg";
//	private static final String link_GraphUPCOM = MSTradeAppConfig.address_server
//			+ "/Chart/UPCOM.jpg";
private static final String link_GraphHOSE = BuildConfig.address_server
		+ "/Chart/HOSE.jpg";
	private static final String link_GraphHNX = BuildConfig.address_server
			+ "/Chart/HNX.jpg";
	private static final String link_GraphUPCOM = BuildConfig.address_server
			+ "/Chart/UPCOM.jpg";

	static final String INDEX = "IndexService#INDEX";
	static final String INDEXREALTIME = "IndexService#INDEXREALTIME";
	static final String NEWS = "NewsService#NEWS";
	static final String GETNEXTNEWS = "GetNextNewsService";
	MarketInfoService marketInfoService;
	NewsService newsService;

	Indices_View indices_HOSE;
	Indices_View indices_HNX;
	Indices_View indices_UPCOM;
	ImageView img_refresh;
	ImageView img_expand;
	ListView lv_News;
	ScrollView scrollview_index;
	ImageView img_graphHOSE;
	ImageView img_graphHNX;
	ImageView img_graphUPCOM;
	SearchTextUI searchTextUI;

	NewsAdapter adapter_News;
	Runnable clearHightLight;
	List<IndicesItem> list_Indices;
	List<NewsItem> list_News;

	NewsDetail newsDetail;
	String lastNewsId;
	String lastSeq_HOSE = StringConst.EMPTY;
	String lastSeq_HNX = StringConst.EMPTY;
	String lastSeq_UPCOM = StringConst.EMPTY;
	Bitmap bm_graphHOSE;
	Bitmap bm_graphHNX;
	Bitmap bm_graphUPCOM;
	String filterText = StringConst.EMPTY;
	final Handler handler = new Handler();
	RequestRealtime requestRealtime;

	public static T_MarketInfo newInstance(MainActivity mActivity) {
		T_MarketInfo market = new T_MarketInfo();
		market.mainActivity = mActivity;
		market.TITLE = mActivity.getResources().getString(R.string.MarketInfo);
		market.marketInfoService = (MarketInfoService) mActivity.serviceManager
				.getService(MarketInfoService.class.getName());
		if (market.marketInfoService == null) {
			market.marketInfoService = new MarketInfoService();
			mActivity.serviceManager.addService(market.marketInfoService);
		}
		market.newsService = (NewsService) mActivity.serviceManager
				.getService(NewsService.class.getName());
		if (market.newsService == null) {
			market.newsService = new NewsService();
			mActivity.serviceManager.addService(market.newsService);
		}
		return market;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestRealtime = new RequestRealtime(RequestRealtime.MODE_DELAY,
				UPDATE_INTERVAL);
		requestRealtime.setRequest(new MyRequest() {

			@Override
			public void execute() {
				callJIndex(INDEXREALTIME);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.t_marketinfo, container, false);
		initView(view);
		initData();
		initListener();
		return view;
	}

	private void initView(View view) {
		indices_HOSE = (Indices_View) view
				.findViewById(R.id.indices_marketinfo_HOSE);
		indices_HNX = (Indices_View) view
				.findViewById(R.id.indices_marketinfo_HNX);
		indices_UPCOM = (Indices_View) view
				.findViewById(R.id.indices_marketinfo_UPCOM);
		img_refresh = (ImageView) view.findViewById(R.id.img_refresh);
		img_expand = (ImageView) view.findViewById(R.id.img_expand);
		lv_News = (ListView) view.findViewById(R.id.listview_News);
		scrollview_index = (ScrollView) view
				.findViewById(R.id.scrollview_index);
		img_graphHOSE = (ImageView) view.findViewById(R.id.img_graphchart_HOSE);
		img_graphHNX = (ImageView) view.findViewById(R.id.img_graphchart_HNX);
		img_graphUPCOM = (ImageView) view
				.findViewById(R.id.img_graphchart_UPCOM);
		searchTextUI = (SearchTextUI) view
				.findViewById(R.id.searchtext_listview);
		searchTextUI.setVisibleButton(false);
		searchTextUI.setVisibleClearField(true);
		Common.setupUI(view, getActivity());

	}

	private void initData() {
		if (newsDetail == null) {
			newsDetail = (NewsDetail) mainActivity
					.getFragmentByName(NewsDetail.class.getName());
		}
		if (clearHightLight == null) {
			clearHightLight = new Runnable() {

				@Override
				public void run() {
					displayIndices();
				}
			};
		}
		if (list_Indices == null) {
			list_Indices = new ArrayList<IndicesItem>();
		} else {
			list_Indices.clear();
		}
		if (list_News == null) {
			list_News = new ArrayList<NewsItem>();
		} else {
			list_News.clear();
		}
		if (adapter_News == null) {
			adapter_News = new NewsAdapter(getActivity(),
					android.R.layout.simple_list_item_1, list_News);
			adapter_News.addCondition(new Condition<NewsItem>() {

				@Override
				public boolean checkCondition(NewsItem item) {
					if (item.getDesc().contains(filterText)) {
						return true;
					}
					return false;
				}
			});
		} else {
			adapter_News.notifyDataSetChanged();
		}
		lv_News.setAdapter(adapter_News);

	}

	private void initListener() {
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
				filterText = Common.convertUTF8ToLatin(s.toString()
						.toLowerCase());
				timer.cancel();
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						mainActivity.delay.post(new Runnable() {
							@Override
							public void run() {
								adapter_News.notifyDataSetChanged();
								if (0 < adapter_News.getCount()) {
									lv_News.performItemClick(
											adapter_News.getView(0, null, null),
											0, adapter_News.getItemId(0));
								} else {
									mainActivity.sendArgumentToFragment(
											NewsDetail.class.getName(), null);
								}
							}
						});
					}
				}, DELAYTIME);
			}
		});
		img_expand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				expandNews(!isNewsExpanded);
			}
		});
		img_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callGetAllNews();
			}
		});
		lv_News.setOnScrollListener(new OnScrollListener() {
			int preLast = 0;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				int lastItem = firstVisibleItem + visibleItemCount;
				if ((lastItem == totalItemCount) && (totalItemCount > 0)
						&& preLast != lastItem) {
					callGetNextNews();
				}
				preLast = lastItem;
			}
		});
		lv_News.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showNewsDetails(list_News.get(position));
			}
		});
	}

	public void onPause() {
		super.onPause();
		mainActivity.getSupportFragmentManager().beginTransaction()
				.remove(newsDetail).commit();
		searchTextUI.getEditText().getEditableText().clear();
		requestRealtime.stop();
	}

	public void onResume() {
		super.onResume();
		// mainActivity.displayFragment(NewsDetail.class.getName(),
		// R.id.t_marketinfo_container_newsdetails);
		FragmentManager fm = getChildFragmentManager();
		fm.beginTransaction()
				.replace(R.id.t_marketinfo_container_newsdetails, newsDetail,
						newsDetail.getTag()).commit();
		fm.executePendingTransactions();
		refresh();
	}

	@Override
	public void refresh() {
		super.refresh();
		callJIndex(INDEX);
		requestRealtime.run();

		//20190121 Toannds bo goi lay all new
		//callGetAllNews();

		expandNews(false);
		scrollview_index.scrollTo(0, 0);
	}

	private void callJIndex(String key) {
		marketInfoService.CallControllerJIndex(T_MarketInfo.this, key);
	}

	private void callGetAllNews() {
		newsService.CallGetAllNews(this, NEWS);
	}

	private void callGetNextNews() {
		String lastId = list_News.get(list_News.size() - 1).Id;
		newsService.CallGetNextNews(lastId, null, this, GETNEXTNEWS);
	}

	private long lastTimeHose = 0;
	private long lastTimeHnx = 0;
	private long lastTimeUpcom = 0;

	private void displayIndices() {
		IndicesItem indicesHose = null;
		IndicesItem indicesHnx = null;
		IndicesItem indicesUpcom = null;
		try {
			indicesHose = list_Indices.get(0);
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			indicesHnx = list_Indices.get(1);
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			indicesUpcom = list_Indices.get(2);
		} catch (IndexOutOfBoundsException e) {
		}
		indices_HOSE.setView(indicesHose);
		indices_HOSE.setIndexName(VN_INDEX);
		indices_HNX.setView(indicesHnx);
		indices_HNX.setIndexName(HNX_INDEX);
		indices_UPCOM.setView(indicesUpcom);
		indices_UPCOM.setIndexName(UPCOM_INDEX);

		// draw graph
		if (indicesHose != null) {
			if (!indicesHose.LS.equals(lastSeq_HOSE)) {
				long currentTime = System.currentTimeMillis();
				if ((currentTime - lastTimeHose) > GETCHART_INTERVAL) {
					drawGraphView(img_graphHOSE, link_GraphHOSE, GRAPH_HOSE);
					lastTimeHose = currentTime;
				}
				lastSeq_HOSE = indicesHose.LS;
			} else {
				if (bm_graphHOSE != null) {
					img_graphHOSE.setImageBitmap(bm_graphHOSE);
				}
			}
		}
		if (indicesHnx != null) {
			if (!indicesHnx.LS.equals(lastSeq_HNX)) {
				long currentTime = System.currentTimeMillis();
				if ((currentTime - lastTimeHnx) > GETCHART_INTERVAL) {
					drawGraphView(img_graphHNX, link_GraphHNX, GRAPH_HNX);
					lastTimeHnx = currentTime;
				}

				lastSeq_HNX = indicesHnx.LS;
			} else {
				if (bm_graphHNX != null) {
					img_graphHNX.setImageBitmap(bm_graphHNX);
				}
			}

		}
		if (indicesUpcom != null) {
			if (!indicesUpcom.LS.equals(lastSeq_UPCOM)) {
				long currentTime = System.currentTimeMillis();
				if ((currentTime - lastTimeUpcom) > GETCHART_INTERVAL) {
					drawGraphView(img_graphUPCOM, link_GraphUPCOM, GRAPH_UPCOM);
					lastTimeUpcom = currentTime;
				}
				lastSeq_UPCOM = indicesUpcom.LS;
			} else {
				if (bm_graphUPCOM != null) {
					img_graphUPCOM.setImageBitmap(bm_graphUPCOM);
				}
			}

		}
	}

	private void showNewsDetails(NewsItem newsItem) {
		newsItem.setShortNews(true);
		mainActivity.sendArgumentToFragment(NewsDetail.class.getName(),
				newsItem);
		newsDetail.refresh();
	}

	boolean isNewsExpanded = false;

	private void expandNews(boolean isExpand) {
		if (isExpand) {
			scrollview_index.setVisibility(View.GONE);
			isNewsExpanded = true;
		} else {
			scrollview_index.setVisibility(View.VISIBLE);
			isNewsExpanded = false;
		}
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		switch (key) {
		case INDEX:
			if (rObj.obj != null) {
				list_Indices.clear();
				list_Indices.addAll((List<IndicesItem>) rObj.obj);
				displayIndices();
			}
			break;
		case INDEXREALTIME:
			if (rObj.obj != null) {
				list_Indices.clear();
				list_Indices.addAll((List<IndicesItem>) rObj.obj);
				displayIndices();
			}
			break;
		case NEWS:
			if (rObj.obj != null) {
				list_News.clear();
				list_News.addAll((List<NewsItem>) rObj.obj);
				adapter_News.notifyDataSetChanged();
				if (adapter_News.getCount() > 0) {
					lv_News.performItemClick(
							adapter_News.getView(0, null, null), 0,
							adapter_News.getItemId(0));
				}
			}
			break;
		case GETNEXTNEWS:
			if (rObj.obj != null) {
				List<NewsItem> list = (List<NewsItem>) rObj.obj;
				if (list.size() > 0) {
					int oldsize = list_News.size();
					list_News.addAll(list);
					adapter_News.notifyDataSetChanged();
					lastNewsId = list_News.get(oldsize - 1).Id;
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
		case INDEXREALTIME:
			handler.postDelayed(clearHightLight, CLEARHIGHLIGHT_INTERVAL);
			requestRealtime.trigger();
			break;
		case INDEX:
			handler.postDelayed(clearHightLight, CLEARHIGHLIGHT_INTERVAL);
			break;
		case GETNEXTNEWS:
			break;
		default:
			break;
		}
	}

	private void drawGraphView(ImageView imgv, String url, String key) {
		// show The Image
		new DownloadImageTask(imgv, key).executeOnExecutor(
				AsyncTask.THREAD_POOL_EXECUTOR, url);
	}

	class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;
		String key;

		public DownloadImageTask(ImageView bmImage, String key) {
			this.bmImage = bmImage;
			this.key = key;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
			switch (key) {
			case GRAPH_HOSE:
				bm_graphHOSE = result;
				break;
			case GRAPH_HNX:
				bm_graphHNX = result;
				break;
			case GRAPH_UPCOM:
				bm_graphUPCOM = result;
				break;

			default:
				break;
			}
		}
	}
}
