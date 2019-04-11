package com.fss.mobiletrading.object;

import com.tcscuat.mobiletrading.MainActivity;

public class MenuItemImplement implements com.fss.mobiletrading.interfaces.IMenuItem {

	MenuFunctionItem item;
	MainActivity mainActivity;
	Class<?> clazz;

	public MenuItemImplement(MenuFunctionItem item, MainActivity mainActivity,
			Class<?> clazz) {
		this.mainActivity = mainActivity;
		this.item = item;
		this.clazz = clazz;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return item.getTitle();
	}

	@Override
	public MenuFunctionItem getMenuFunctionItem() {
		// TODO Auto-generated method stub
		return item;
	}

	@Override
	public void performAction() {
		// TODO Auto-generated method stub
		mainActivity.displayFragment(clazz.getName());
	}

}
