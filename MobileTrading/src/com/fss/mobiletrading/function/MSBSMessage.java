package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.BrokerAlertAdapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.news.NewsDetail;
import com.fss.mobiletrading.object.BrokerAlertItem;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;

public class MSBSMessage extends AbstractFragment {

	final String GETBROKERALERTLIST = "GetBrokerAlertListService#GETBROKERALERTLIST";

	BrokerAlertAdapter adapterThongDiepMSBS;
	public static List<BrokerAlertItem> listLichSKItem;
	ListView lv_LichSK;

	String lastNewsId;

	public static MSBSMessage newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		MSBSMessage self = new MSBSMessage();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.MSINotification);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.thongdiep_msbs, container, false);
		lv_LichSK = (ListView) view.findViewById(R.id.listview_ThongDiepMSBS);
		init();

		adapterThongDiepMSBS = new BrokerAlertAdapter(
				MSBSMessage.this.getActivity(),
				android.R.layout.simple_list_item_1, listLichSKItem);
		lv_LichSK.setAdapter(adapterThongDiepMSBS);

		return view;
	}

	private void init() {
		// TODO Auto-generated method stub
		listLichSKItem = new ArrayList<BrokerAlertItem>();
		initialiseListener();
	}

	private void initialiseListener() {
		// TODO Auto-generated method stub
		lv_LichSK.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ShowNewsDetails(new NewsItem(
						listLichSKItem.get(position).Subject, listLichSKItem
								.get(position).HTML));
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
						&& ((listLichSKItem.get(listLichSKItem.size() - 1).AlertID != lastNewsId))) {
					CallgetBrokerAlertList(listLichSKItem.get(listLichSKItem
							.size() - 1).AlertID);
				}
			}
		});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		CallgetBrokerAlertList(StringConst.EMPTY);
	}

	private void CallgetBrokerAlertList(String node) {
		// TODO Auto-generated method stub
		if (listLichSKItem.size() > 0) {
			lastNewsId = listLichSKItem.get(listLichSKItem.size() - 1).AlertID;
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_GetBrokerAlertList));
		}
		{
			list_key.add("pv_SourceAfacctno");
			list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
		}
		{
			list_key.add("node");
			list_value.add(node);
		}

		StaticObjectManager.connectServer.callHttpPostService(
				GETBROKERALERTLIST, this, list_key, list_value);
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
			case GETBROKERALERTLIST:
				if (rObj.obj != null) {
					listLichSKItem.addAll((List<BrokerAlertItem>) rObj.obj);
					adapterThongDiepMSBS.notifyDataSetChanged();
				}
				break;
			default:
				break;
			}
		}
	}

}
