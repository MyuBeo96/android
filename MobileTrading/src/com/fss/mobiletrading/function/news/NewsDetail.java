package com.fss.mobiletrading.function.news;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.Login;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.MyActionBar.Action;

public class NewsDetail extends AbstractFragment {
	final String GETNEWSDETAILS = "GetNewsDetailsService";
	GestureDetector gesture;
	public NewsItem newsItem;
	WebView webview;
	TextView tv_title;

	public static NewsDetail newInstance(MainActivity mActivity) {

		NewsDetail self = new NewsDetail();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.NewsDetails);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View view = paramLayoutInflater.inflate(R.layout.newsdetail,
				paramViewGroup, false);
		webview = ((WebView) view.findViewById(R.id.webview_newsdetail));
		tv_title = (TextView) view.findViewById(R.id.text_newsdetail_title);
		init();
		return view;
	}

	private void init() {
		webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	}

	public void onResume() {
		super.onResume();
		getNewsDetail();
	}

	@Override
	public void onPause() {
		super.onPause();
		clearWebView();
	}

	public void refresh() {
		getNewsDetail();
	}

	private void getNewsDetail() {
		if (newsItem != null) {
			if (newsItem.isShortNews()) {
				CallGetNewsDetails(newsItem.Id);
			} else {
				showDetailNewsItem(newsItem);
			}

		} else {
			clearWebView();
		}
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoAction();
	}

	private void CallGetNewsDetails(String newId) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetNewsDetail);
		}
		{
			list_key.add("newId");
			list_value.add(newId);
		}
		StaticObjectManager.connectServer.callHttpPostService(GETNEWSDETAILS,
				this, list_key, list_value);
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case GETNEWSDETAILS:
			if (rObj.obj != null) {
				newsItem = ((NewsItem) rObj.obj);
				newsItem.setShortNews(false);
				showDetailNewsItem(newsItem);
			}
			break;

		default:
			break;
		}
	}

	static final String oldChar = "<meta charset=\"utf-8\" />";
	static final String newChar = "<meta charset=\"utf-8\" /> <style type=\"text/css\"> "
			+ "@font-face {font-family: MyFont;src: url(\"file:///android_assets/fonts/AvenirNext_Regular.ttf\")}body "
			+ "{    font-family: MyFont;    font-size: medium;    text-align: justify;}</style>";

	private void showDetailNewsItem(NewsItem newsItem) {
		tv_title.setText(newsItem.Title);
		webview.getSettings().setJavaScriptEnabled(true);
		// String replace = newsItem.Content.replace(oldChar, newChar);
		webview.loadDataWithBaseURL(StringConst.EMPTY, newsItem.Content,
				"text/html", "UTF-8", StringConst.EMPTY);
	}

	private void clearWebView() {
		webview.loadUrl("about:blank");
		tv_title.setText(StringConst.EMPTY);
	}

	@Override
	public void getArgument(Object obj) {
		super.getArgument(obj);
		if (obj == null) {
			newsItem = null;
			if (isResumed()) {
				clearWebView();
			}
		} else if (obj instanceof NewsItem) {
			newsItem = (NewsItem) obj;
			if (isResumed()) {
				getNewsDetail();
			}
		}
	}
}
