package com.fss.mobiletrading.interfaces;

import com.fss.mobiletrading.object.MenuFunctionItem;

public interface IMenuItem {
	public MenuFunctionItem getMenuFunctionItem();

	public void performAction();
}