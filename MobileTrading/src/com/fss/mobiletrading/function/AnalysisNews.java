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

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.NewsAdapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.news.NewsDetail;
import com.fss.mobiletrading.jsonservice.Error;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.Login;
import com.msbuat.mobiletrading.MainActivity;

public class AnalysisNews extends AbstractFragment {

	final String GETNEWSBSC = "NewsService#GETNEWSBSC";
	final String GETNEXTNEWSBSC = "GetNextNewsService#GETNEXTNEWSBSC";
	final String GETNEWSBSCDETAIL = "GetNewsDetailsService#GETNEWSBSCDETAIL";

	NewsAdapter adapterNewsSC;
	public static List<NewsItem> listNewsSCItem;
	ListView lv_NewsSC;
	String lastNewsId;

	public static AnalysisNews newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		AnalysisNews self = new AnalysisNews();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.AnalysisNews);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.news_from_sc, container, false);
		lv_NewsSC = (ListView) view.findViewById(R.id.listview_NewsFromSC);
		init();

		adapterNewsSC = new NewsAdapter(AnalysisNews.this.getActivity(),
				android.R.layout.simple_list_item_1, listNewsSCItem);
		lv_NewsSC.setAdapter(adapterNewsSC);
		return view;
	}

	private void init() {
		// TODO Auto-generated method stub
		listNewsSCItem = new ArrayList<NewsItem>();
		initialiseListener();
	}

	private void initialiseListener() {
		// TODO Auto-generated method stub
		lv_NewsSC.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				CallGetNewsBSCDetail(listNewsSCItem.get(position).Id);

			}
		});

		lv_NewsSC.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if ((firstVisibleItem + visibleItemCount == totalItemCount)
						&& (totalItemCount > 0)
						&& ((listNewsSCItem.get(listNewsSCItem.size() - 1).Id != lastNewsId))) {
					CallGetNextNewsBSC();
				}
			}
		});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		CallGetNewsBSC();
	}

	private void CallGetNewsBSC() {
		// TODO Auto-generated method stub
		StaticObjectManager.connectServer.callHttpGetService(GETNEWSBSC, this,
				getStringResource(R.string.controller_GetNewsBSC));
	}

	private void CallGetNextNewsBSC() {
		// TODO Auto-generated method stub
		String lastId = listNewsSCItem.get(listNewsSCItem.size() - 1).Id;
		lastNewsId = lastId;
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_GetNextNewsBSC));
		}
		{
			list_key.add("lang");
			list_value.add(AppData.language);
		}
		{
			list_key.add("node");
			list_value.add(lastId);
		}

		StaticObjectManager.connectServer.callHttpPostService(GETNEXTNEWSBSC,
				this, list_key, list_value);
	}

	private void CallGetNewsBSCDetail(String newId) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_GetNewsBSCDetail));
		}
		{
			list_key.add("Id");
			list_value.add(newId);
		}
		StaticObjectManager.connectServer.callHttpPostService(GETNEWSBSCDETAIL,
				this, list_key, list_value);
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
			case GETNEWSBSC:
				if (rObj.obj != null) {
					listNewsSCItem.clear();
					List<NewsItem> list = (List<NewsItem>) rObj.obj;
					listNewsSCItem.addAll(list);
					adapterNewsSC.notifyDataSetChanged();
				}
				break;
			case GETNEXTNEWSBSC:
				if (rObj.obj != null) {
					listNewsSCItem.addAll((List<NewsItem>) rObj.obj);
					adapterNewsSC.notifyDataSetChanged();
				}
				break;
			case GETNEWSBSCDETAIL:
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
