package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.DanhSachChamNguong_Adapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.DanhSachChamNguongItem;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;

public class MatchAlertList extends AbstractFragment {
	final String GETALLALERT_RESULT = "GetAllAlertResultService";
	DanhSachChamNguong_Adapter adapterListView;
	List<DanhSachChamNguongItem> listListView;
	ListView lv_DanhSachChamNguong;

	public static MatchAlertList newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		MatchAlertList self = new MatchAlertList();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.MatchAlertList);
		return self;
	}

	private void CallGetALLAlert_Result() {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		list_key.add("giang");
		list_value.add(new String(
				getStringResource(R.string.controller_GetALLAlert_Result)));
		StaticObjectManager.connectServer.callHttpPostService(
				GETALLALERT_RESULT, this, list_key, list_value);
	}

	private void initialise() {
		this.listListView = new ArrayList<DanhSachChamNguongItem>();
		this.adapterListView = new DanhSachChamNguong_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listListView);
		this.lv_DanhSachChamNguong.setAdapter(this.adapterListView);
	}

	private void initialiseListener() {
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(
				R.layout.danhsachcanhbaochamnguong, paramViewGroup, false);
		this.lv_DanhSachChamNguong = ((ListView) localView
				.findViewById(R.id.listview_DSCBChamNguong));
		initialise();
		initialiseListener();
		return localView;
	}

	public void onPause() {
		super.onPause();
	}

	public void onResume() {
		super.onResume();
		CallGetALLAlert_Result();
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		switch (key) {
		case GETALLALERT_RESULT:
			if (rObj.obj != null) {
				this.listListView.clear();
				this.listListView
						.addAll((List<DanhSachChamNguongItem>) rObj.obj);
				this.adapterListView.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
	}

}
