package com.fss.mobiletrading.function.authorizationinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.PermissionTable_Adapter;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.google.android.gms.drive.internal.ad;

public class PermissionTable extends AbstractFragment {

	ListView lv_PermissionTable;

	PermissionObserver permissionObserver;
	PermissionTable_Adapter adapter_PermissionTable;

	public PermissionTable() {
	}

	public static PermissionTable newInstance(MainActivity pr_mainActivity) {
		PermissionTable permissionTable = new PermissionTable();
		permissionTable.mainActivity = pr_mainActivity;
		if (permissionTable.permissionObserver == null) {
			permissionTable.permissionObserver = new PermissionObserver();
		}
		if (permissionTable.mainActivity != null) {
			AuthorizationInfo authorizationInfo = (AuthorizationInfo) permissionTable.mainActivity.mapFragment
					.get(AuthorizationInfo.class.getName());
			if (authorizationInfo != null) {
				authorizationInfo.permissionObservable
						.addObserver(permissionTable.permissionObserver);
			}
		}
		permissionTable.TITLE = pr_mainActivity
				.getStringResource(R.string.PermissionTable);
		return permissionTable;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.permission_table, container,
				false);
		lv_PermissionTable = (ListView) view
				.findViewById(R.id.listview_permission_table);
		initialise();
		return view;
	}

	private void initialise() {

		initialiseData();
		initialiseListener();
	}

	private void initialiseData() {

		if (adapter_PermissionTable == null) {
			adapter_PermissionTable = new PermissionTable_Adapter(mainActivity,
					android.R.layout.simple_list_item_1,
					permissionObserver.getList_PermissionInfoItem());
		} else {
			adapter_PermissionTable.notifyDataSetChanged();
		}

		lv_PermissionTable.setAdapter(adapter_PermissionTable);
	}

	private void initialiseListener() {

	}

	@Override
	public void onResume() {
		super.onResume();
		adapter_PermissionTable.notifyDataSetChanged();
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoAction();
	}

	@Override
	protected void process(String key, ResultObj rObj) {

	}

}
