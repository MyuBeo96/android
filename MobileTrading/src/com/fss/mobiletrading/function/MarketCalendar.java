package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.NewsAdapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.news.NewsDetail;
import com.fss.mobiletrading.jsonservice.Error;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.Login;
import com.fscuat.mobiletrading.MSTradeAppConfig;
import com.fscuat.mobiletrading.MainActivity;

public class MarketCalendar extends AbstractFragment {

	final String CALENDARMARKET = "NewsService#CALENDARMARKET";
	final String GETNEXTNEWSCALENDARMARKET = "GetNextNewsService#GETNEXTNEWSCALENDARMARKET";
	final String GETCALENDARMARKETINFOR = "GetNewsDetailsService#GETCALENDARMARKETINFOR";

	NewsAdapter adapterLichSK;
	public static List<NewsItem> listLichSKItem;
	ListView lv_LichSK;
	String lastNewsId;

	public static MarketCalendar newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		MarketCalendar self = new MarketCalendar();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.MarketCalendar);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.lich_su_kien, container, false);
		lv_LichSK = (ListView) view.findViewById(R.id.listview_LichSuKien);
		init();

		adapterLichSK = new NewsAdapter(MarketCalendar.this.getActivity(),
				android.R.layout.simple_list_item_1, listLichSKItem);
		lv_LichSK.setAdapter(adapterLichSK);
		lv_LichSK.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				CallgetCalendarMarketInfor(listLichSKItem.get(position).Id);

			}
		});

		lv_LichSK.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if ((firstVisibleItem + visibleItemCount == totalItemCount)
						&& (totalItemCount > 0)
						&& ((listLichSKItem.get(listLichSKItem.size() - 1).Id != lastNewsId))) {
					CallGetNextNewsCalendarMarket();
				}
			}
		});

		return view;
	}

	private void init() {
		// TODO Auto-generated method stub
		listLichSKItem = new ArrayList<NewsItem>();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		CallCalendarMarket();
	}

	private void CallCalendarMarket() {
		// TODO Auto-generated method stub
		StaticObjectManager.connectServer.callHttpGetService(CALENDARMARKET,
				this, getStringResource(R.string.controller_CalendarMarket));
	}

	private void CallGetNextNewsCalendarMarket() {
		// TODO Auto-generated method stub
		String lastId = listLichSKItem.get(listLichSKItem.size() - 1).Id;
		lastNewsId = lastId;
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_GetNextNewsCalendarMarket));
		}
		{
			list_key.add("lang");
			list_value.add(AppData.language);
		}
		{
			list_key.add("node");
			list_value.add(lastId);
		}

		StaticObjectManager.connectServer.callHttpPostService(
				GETNEXTNEWSCALENDARMARKET, this, list_key, list_value);
	}

	private void CallgetCalendarMarketInfor(String newId) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetNewsDetail);
		}
		{
			list_key.add("Id");
			list_value.add(newId);
		}
		StaticObjectManager.connectServer.callHttpPostService(
				GETCALENDARMARKETINFOR, this, list_key, list_value);
	}

	private void ShowNewsDetails(NewsItem newsItem) {
		// TODO Auto-generated method stub
		newsItem.setShortNews(true);
		mainActivity.sendArgumentToFragment(NewsDetail.class.getName(),
				newsItem);
		MainActivity mActivity = (MainActivity) getActivity();
		mActivity.navigateFragment(NewsDetail.class.getName());
	}

	@Override
	public void notifyChangeAcctNo() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		if (rObj == null) {

		} else {
			switch (key) {
			case CALENDARMARKET:
				if (rObj.obj != null) {
					listLichSKItem.clear();
					List<NewsItem> list = (List<NewsItem>) rObj.obj;
					listLichSKItem.addAll(list);
					adapterLichSK.notifyDataSetChanged();
				}
				break;
			case GETNEXTNEWSCALENDARMARKET:
				if (rObj.obj != null) {
					listLichSKItem.addAll((List<NewsItem>) rObj.obj);
					adapterLichSK.notifyDataSetChanged();
				}
				break;
			case GETCALENDARMARKETINFOR:
				if (rObj.obj != null) {
					NewsItem newsItem = ((NewsItem) rObj.obj);
					ShowNewsDetails(newsItem);
				}
				break;
			default:
				break;
			}
		}
	}

}
