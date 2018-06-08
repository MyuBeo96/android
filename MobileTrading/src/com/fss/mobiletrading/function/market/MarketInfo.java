package com.fss.mobiletrading.function.market;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fss.mobiletrading.adapter.NewsAdapter;
import com.fss.mobiletrading.function.MarketIndex;
import com.fss.mobiletrading.function.news.NewsDetail;
import com.fss.mobiletrading.function.news.NewsService;
import com.fss.mobiletrading.object.IndicesItem;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.design.Indices_View;

public class MarketInfo extends AbstractFragment {
	private static final int UPDATE_INTERVAL = 5000;
	private static final int CLEARHIGHLIGHT_INTERVAL = 2000;
	static final String INDEX = "IndexService#INDEX";
	static final String NEWS = "NewsService#NEWS";
	static final String GETNEXTNEWS = "GetNextNewsService";
	MarketInfoService marketInfoService;
	NewsService newsService;

	TextView lbl_ChangePercent;
	TextView lbl_Volume;
	ListView lv_News;
	int index_lbl_changepercent = 0;
	int index_lbl_volume = 0;

	Indices_View indices_HOSE;
	Indices_View indices_HNX;
	Indices_View indices_UPCOM;
	List<IndicesItem> list_Indices;
	List<NewsItem> list_News;
	NewsAdapter adapter_News;
	Runnable updateJIndex;
	Runnable clearHightLight;
	boolean isUpdating = false;

	public static List<Integer> list_lbl_ChangePercent;
	public static List<Integer> list_lbl_volume;
	Timer timer;
	String lastNewsId;

	public static MarketInfo newInstance(MainActivity mActivity) {
		MarketInfo market = new MarketInfo();
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

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.marketinfo, viewGroup, false);
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
		lv_News = (ListView) view.findViewById(R.id.listview_TTTT_News);
		lbl_ChangePercent = (TextView) view
				.findViewById(R.id.lbl_marketinfo_ChangePercent);
		lbl_Volume = (TextView) view.findViewById(R.id.lbl_marketinfo_volume);
	}

	private void initData() {
		if (updateJIndex == null) {
			updateJIndex = new Runnable() {

				@Override
				public void run() {
					marketInfoService.CallControllerJIndex(MarketInfo.this,
							INDEX);
				}
			};
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
		} else {
			adapter_News.notifyDataSetChanged();
		}
		lv_News.setAdapter(adapter_News);
		if (list_lbl_ChangePercent == null) {
			list_lbl_ChangePercent = new ArrayList<Integer>();
			list_lbl_ChangePercent.add(R.string.change);
			list_lbl_ChangePercent.add(R.string.percent_change);
		}
		if (list_lbl_volume == null) {
			list_lbl_volume = new ArrayList<Integer>();
			list_lbl_volume.add(R.string.thongtinthitruong_lbl_KhoiLuong);
			list_lbl_volume.add(R.string.GiaTri);
		}
		lbl_ChangePercent.setText(getStringResource(list_lbl_ChangePercent
				.get(index_lbl_changepercent % 2)));
		lbl_Volume.setText(getStringResource(list_lbl_volume
				.get(index_lbl_volume % 2)));
	}

	private void initListener() {

		lbl_ChangePercent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				index_lbl_changepercent++;
				lbl_ChangePercent
						.setText(getStringResource(list_lbl_ChangePercent
								.get(index_lbl_changepercent % 2)));
				changeChangeAndPercentChange(index_lbl_changepercent % 2);
			}
		});
		lbl_Volume.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				index_lbl_volume++;
				lbl_Volume.setText(getStringResource(list_lbl_volume
						.get(index_lbl_volume % 2)));
				changeVolumeAndValue(index_lbl_volume % 2);
			}
		});
		indices_HOSE.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IndicesItem indicesItem = null;
				try {
					indicesItem = list_Indices.get(0);
				} catch (IndexOutOfBoundsException e) {
				}
				if (indicesItem != null) {
					MarketIndex.indicesItem = indicesItem;
					mainActivity.displayFragment(MarketIndex.class.getName());
				}
			}
		});
		indices_HNX.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IndicesItem indicesItem = null;
				try {
					indicesItem = list_Indices.get(1);
				} catch (IndexOutOfBoundsException e) {
				}
				if (indicesItem != null) {
					MarketIndex.indicesItem = indicesItem;
					mainActivity.displayFragment(MarketIndex.class.getName());
				}
			}
		});
		indices_UPCOM.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IndicesItem indicesItem = null;
				try {
					indicesItem = list_Indices.get(2);
				} catch (IndexOutOfBoundsException e) {
				}
				if (indicesItem != null) {
					MarketIndex.indicesItem = indicesItem;
					mainActivity.displayFragment(MarketIndex.class.getName());
				}
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
					CallGetNextNews();
				}
				preLast = lastItem;
				Log.d("News",""+ preLast);
			}
		});
		lv_News.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowNewsDetails(list_News.get(position));
			}
		});
	}

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
		indices_HNX.setView(indicesHnx);
		indices_UPCOM.setView(indicesUpcom);
	}

	private void changeChangeAndPercentChange(int state) {
		indices_HOSE.switchChangeAndPercentChange(state);
		indices_HNX.switchChangeAndPercentChange(state);
		indices_UPCOM.switchChangeAndPercentChange(state);
	}

	private void changeVolumeAndValue(int state) {
		indices_HOSE.switchVolumeAndValue(state);
		indices_HNX.switchVolumeAndValue(state);
		indices_UPCOM.switchVolumeAndValue(state);

	}

	private void startUpdateJIndex() {
		if (!isUpdating) {
			mainActivity.delay.post(updateJIndex);
			isUpdating = true;
		}
	}

	private void stopUpdateJIndex() {
		isUpdating = false;
	}

	private void postUpdateJIndex() {
		mainActivity.delay
				.postDelayed(clearHightLight, CLEARHIGHLIGHT_INTERVAL);
		if (isUpdating) {
			mainActivity.delay.postDelayed(updateJIndex, UPDATE_INTERVAL);

		}
	}

	public void onPause() {
		super.onPause();
		CancelTimer();
		stopUpdateJIndex();
	}

	public void onResume() {
		super.onResume();
		// CallControllerJIndex();
		CallGetAllNews();
		startUpdateJIndex();
		AsynchTaskTimer();
	}

	private void CallGetAllNews() {
		newsService.CallGetAllNews(this, NEWS);
	}

	private void CallGetNextNews() {
		String lastId = list_News.get(list_News.size() - 1).Id;
		lastNewsId = lastId;
		newsService.CallGetNextNews(lastId, null, this, GETNEXTNEWS);
	}

	private void ShowNewsDetails(NewsItem newsItem) {
		newsItem.setShortNews(true);
		mainActivity.sendArgumentToFragment(NewsDetail.class.getName(),
				newsItem);
		((MainActivity) getActivity()).navigateFragment(NewsDetail.class
				.getName());
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
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
		case NEWS:
			if (rObj.obj != null) {
				list_News.clear();
				list_News.addAll((List<NewsItem>) rObj.obj);
				adapter_News.notifyDataSetChanged();
			}
			break;
		case GETNEXTNEWS:
			if (rObj.obj != null) {
				list_News.addAll((List<NewsItem>) rObj.obj);
				adapter_News.notifyDataSetChanged();
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
		case INDEX:
			postUpdateJIndex();
			break;
		case NEWS:
			break;
		case GETNEXTNEWS:
			break;
		default:
			break;
		}
	}

	@Override
	protected void processNull(String key) {
		super.processNull(key);
		CancelTimer();
		stopUpdateJIndex();
	}

	public void AsynchTaskTimer() {
		// timer = new Timer();
		// TimerTask timertask = new TimerTask() {
		// @Override
		// public void run() {
		// mainActivity.delay.post(updateJIndex);
		// }
		// };
		// timer.schedule(timertask, UPDATE_INTERVAL);
	}

	private void CancelTimer() {
		// timer.cancel();
	}

}
