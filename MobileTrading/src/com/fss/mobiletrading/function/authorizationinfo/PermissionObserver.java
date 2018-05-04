package com.fss.mobiletrading.function.authorizationinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.util.Log;

public class PermissionObserver implements Observer {

	List<PermissionInfoItem> list_PermissionInfoItem;

	public PermissionObserver() {
		list_PermissionInfoItem = new ArrayList<PermissionInfoItem>();
	}

	@Override
	public void update(Observable observable, Object data) {
		if (observable instanceof PermissionObservable) {

			setList_PermissionInfoItem(((PermissionObservable) observable)
					.getList_PermissionInfoItem());
		}
	}

	public List<PermissionInfoItem> getList_PermissionInfoItem() {
		return list_PermissionInfoItem;
	}

	public void setList_PermissionInfoItem(
			List<PermissionInfoItem> list_PermissionInfoItem) {
		this.list_PermissionInfoItem.clear();
		if (list_PermissionInfoItem != null) {
			this.list_PermissionInfoItem.addAll(list_PermissionInfoItem);
		}
	}

}
