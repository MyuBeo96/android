package com.fss.mobiletrading.function.rightoffregister;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fss.mobiletrading.adapter.RightOffRegister_Adapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;

public class RightOffRegister extends AbstractFragment {
	static final String RIGHTOFFREGISTER = "RightOffRegisterService";
	static final String RIGHTOFFREGISTERDETAILS = "RightOffRegisterDetailsService#1";
	static final String RIGHTOFFREGISTERDETAILS_WATCH = "RightOffRegisterDetailsService#RIGHTOFFREGISTERDETAILS_WATCH";

	public static String CODE_AVAILABLE_REGISTER = "2";
	public static String CODE_NOTAVAILABLE_REGISTER = "1";
	public static String CODE_EXPIRED_REGISTER = "3";
	public static String CODE_ALL_REGISTERED = "0";

	TextView tv_Loading;
	ListView lv_DKQM;

	List<RightOffRegisterItem> list_DKQM;
	RightOffRegister_Adapter adapter;

	public static RightOffRegister newInstance(MainActivity mActivity) {

		RightOffRegister self = new RightOffRegister();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.RightOffRegister);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.rightoffregister, viewGroup,
				false);
		lv_DKQM = ((ListView) view.findViewById(R.id.listview_DKQM));
		tv_Loading = (TextView) view.findViewById(R.id.text_DKQM_loading);
		initData();
		initListener();
		return view;
	}

	private void initData() {
		if (list_DKQM == null) {
			list_DKQM = new ArrayList<RightOffRegisterItem>();
		} else {
			list_DKQM.clear();
		}

		if (adapter == null) {
			adapter = new RightOffRegister_Adapter(getActivity(),
					android.R.layout.simple_list_item_1, list_DKQM);
			adapter.setOnRegisterListener(new OnRegisterListener() {

				@Override
				public void onRegister(RightOffRegisterItem item) {
					if (item == null) {
						return;
					}
					CallRightOffRegisterDetails(item, RIGHTOFFREGISTERDETAILS);
				}
			});

			adapter.setOnWatchRoRListener(new OnWatchRoRListener() {

				@Override
				public void onWatch(RightOffRegisterItem item) {
					if (item == null) {
						return;
					}
					CallRightOffRegisterDetails(item,
							RIGHTOFFREGISTERDETAILS_WATCH);
				}
			});
		} else {
			adapter.notifyDataSetChanged();
		}

		lv_DKQM.setAdapter(adapter);
	}

	private void initListener() {

		lv_DKQM.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				RightOffRegisterItem item = list_DKQM.get(position);
				if (item.Status
						.equals(RightOffRegister.CODE_AVAILABLE_REGISTER)) {
					CallRightOffRegisterDetails(list_DKQM.get(position),
							RIGHTOFFREGISTERDETAILS);
				} else if (item.Status
						.equals(RightOffRegister.CODE_EXPIRED_REGISTER)) {
					showDialogMessage(R.string.thong_bao,
							R.string.HetHanDangKyQuyenMua);
				} else if (item.Status
						.equals(RightOffRegister.CODE_NOTAVAILABLE_REGISTER)) {
					showDialogMessage(R.string.thong_bao,
							R.string.ChuaDenHanDangKyMua);
				} else {
					showDialogMessage(R.string.thong_bao,
							R.string.DaDangKyMuaHet);
				}
			}
		});
	}

	public void onResume() {
		super.onResume();
		CallRightOffRegister();
	}

	private void CallRightOffRegister() {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_RightOffRegister));
		}
		{
			list_key.add("Afacctno");
			list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
		}

		StaticObjectManager.connectServer.callHttpPostService(RIGHTOFFREGISTER,
				this, list_key, list_value);
		isLoading();
	}

	private void CallRightOffRegisterDetails(RightOffRegisterItem item,
			String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_RightOffRegisterDetails));
		}
		{
			list_key.add("Afacctno");
			list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
		}
		{
			list_key.add("Id");
			list_value.add(item.Id);
		}

		StaticObjectManager.connectServer.callHttpPostService(key, this,
				list_key, list_value);
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		CallRightOffRegister();
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case RIGHTOFFREGISTER:
			if (rObj.obj != null) {
				list_DKQM.clear();
				list_DKQM.addAll((List<RightOffRegisterItem>) rObj.obj);
				adapter.notifyDataSetChanged();
			}
			isLoaded();
			break;
		case RIGHTOFFREGISTERDETAILS:
			if (rObj.obj != null) {
				RightOffRegisterItem selectedItem;
				selectedItem = (RightOffRegisterItem) rObj.obj;
				showRightOffRegisterConfirm(selectedItem);
			}
			break;
		case RIGHTOFFREGISTERDETAILS_WATCH:
			if (rObj.obj != null) {
				RightOffRegisterItem selectedItem;
				selectedItem = (RightOffRegisterItem) rObj.obj;
				showRightOffRegisterInfo(selectedItem);
			}
			break;

		default:
			break;
		}
	}

	private void showRightOffRegisterConfirm(RightOffRegisterItem obj) {
		mainActivity.sendArgumentToFragment(
				RightOffRegister_Confirm.class.getName(), obj);
		mainActivity.navigateFragment(RightOffRegister_Confirm.class.getName());
	}

	private void showRightOffRegisterInfo(RightOffRegisterItem obj) {
		mainActivity.sendArgumentToFragment(
				RightOffRegister_Info.class.getName(), obj);
		mainActivity.navigateFragment(RightOffRegister_Info.class.getName());
	}

	@Override
	protected void processNull(String key) {
		super.processNull(key);
		isLoaded();
	}

	@Override
	protected void processErrorOther(String key, ResultObj rObj) {
		super.processErrorOther(key, rObj);
		isLoaded();
	}

	private void isLoading() {
		// tv_Loading.setVisibility(TextView.VISIBLE);
	}

	private void isLoaded() {
		// tv_Loading.setVisibility(TextView.GONE);
	}

}
