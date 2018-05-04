package com.fss.mobiletrading.function.authorizationinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import android.util.Log;

public class PermissionObservable extends Observable {
	List<PermissionInfoItem> list_PermissionInfoItem;

	public PermissionObservable() {
		super();
		list_PermissionInfoItem = new ArrayList<PermissionInfoItem>();
	}

	public List<PermissionInfoItem> getList_PermissionInfoItem() {
		return list_PermissionInfoItem;
	}

	public void addAll(List<PermissionInfoItem> list) {
		list_PermissionInfoItem.addAll(list);
		setChanged();
	}

	public void add(PermissionInfoItem item) {
		list_PermissionInfoItem.add(item);
		setChanged();
	}

	public void clearList() {
		list_PermissionInfoItem.clear();
		setChanged();
	}

}
