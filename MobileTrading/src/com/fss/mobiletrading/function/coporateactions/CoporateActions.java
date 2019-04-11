package com.fss.mobiletrading.function.coporateactions;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fss.mobiletrading.adapter.CoporateAction_Adapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.Login;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.design.InputDate;
import com.tcscuat.mobiletrading.design.InputDate.OnChangeTextDateListener;

public class CoporateActions extends AbstractFragment {

	static final String GETRIGHTINFO = "GetRightInfoService#GETRIGHTINFO";

	Button btn_Search;
	InputDate edt_dateend;
	InputDate edt_datestart;
	List<CoporateActionItem> list_CoporateActionItems;
	CoporateAction_Adapter mAdapterCoporateAction;
	ListView lv_CoporateAction;

	public static CoporateActions newInstance(MainActivity mActivity) {

		CoporateActions self = new CoporateActions();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.CoporateAction);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.coporate_actions, container,
				false);
		edt_datestart = ((InputDate) view
				.findViewById(R.id.edt_coporate_actions_fromdate));
		edt_dateend = ((InputDate) view
				.findViewById(R.id.edt_coporate_actions_todate));
		btn_Search = ((Button) view
				.findViewById(R.id.btn_coporate_actions_Search));
		lv_CoporateAction = ((ListView) view
				.findViewById(R.id.listview_coporate_actions));

		init();
		initListener();
		return view;
	}
	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoActionMenu();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	private void init() {
		if (list_CoporateActionItems == null) {
			list_CoporateActionItems = new ArrayList<CoporateActionItem>();
		} else {
			list_CoporateActionItems.clear();
		}
		if (mAdapterCoporateAction == null) {
			mAdapterCoporateAction = new CoporateAction_Adapter(mainActivity,
					android.R.layout.simple_list_item_1,
					list_CoporateActionItems);
		} else {
			mAdapterCoporateAction.notifyDataSetChanged();
		}
		lv_CoporateAction.setAdapter(this.mAdapterCoporateAction);
		edt_datestart.setLabel(getStringResource(R.string.TuNgay));
		edt_dateend.setLabel(getStringResource(R.string.DenNgay));
	}

	private void initListener() {
		edt_datestart
				.setOnChangeTextDateListener(new OnChangeTextDateListener() {

					@Override
					public void onChange(String date) {
						CallGetRightInfo();
					}
				});
		edt_dateend.setOnChangeTextDateListener(new OnChangeTextDateListener() {

			@Override
			public void onChange(String date) {
				CallGetRightInfo();
			}
		});
		btn_Search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CallGetRightInfo();
			}
		});
		lv_CoporateAction.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showCoporateActionDetails(((CoporateActionItem) lv_CoporateAction
						.getItemAtPosition(position)));
			}
		});
	}

	protected void showCoporateActionDetails(
			CoporateActionItem coporateActionItem) {
		mainActivity.sendArgumentToFragment(
				CoporateActionsDetail.class.getName(), coporateActionItem);
		mainActivity.navigateFragment(CoporateActionsDetail.class.getName());
	}

	@Override
	public void onResume() {
		super.onResume();
		edt_datestart.resetDate();
		edt_dateend.resetDate();
	}

	private void isLoaded() {
	}

	private void isLoading() {
	}

	protected void CallGetRightInfo() {
		isLoading();
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(getStringResource(R.string.controller_GetRightInfo));
		}
		{
			list_key.add("acctno");
			list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
		}
		{
			list_key.add("fromdate");
			list_value.add(edt_datestart.toString());
		}
		{
			list_key.add("todate");
			list_value.add(edt_dateend.toString());
		}

		StaticObjectManager.connectServer.callHttpPostService(GETRIGHTINFO,
				this, list_key, list_value);
	}

	@Override
	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		edt_datestart.resetDate();
		edt_dateend.resetDate();
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		list_CoporateActionItems.clear();
		list_CoporateActionItems.addAll(((List<CoporateActionItem>) rObj.obj));
		mAdapterCoporateAction.notifyDataSetChanged();
		isLoaded();
	}

	@Override
	protected void processNull(String key) {
		super.processNull(key);
		isLoaded();
	}

	@Override
	protected void processErrorOther(String key, ResultObj rObj) {
		super.processErrorOther(key, rObj);
		switch (key) {
		case GETRIGHTINFO:
			list_CoporateActionItems.clear();
			mAdapterCoporateAction.notifyDataSetChanged();
			break;

		default:
			break;
		}
		isLoaded();
	}
}
