package com.fss.mobiletrading.menu;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.interfaces.IStringAction;
import com.fscuat.mobiletrading.MainActivity;

public class MenuItemActionWithImage {

	static String drawable = "drawable";
	MainActivity mainActivity;
	public Class<?> clazz;
	String className;
	String parameter;
	private List<MenuItemActionWithImage> childrenList;
	public boolean isExpand = false;
	SlideMenuItem slideMenuItem;
	public List<Integer> locations;

	public MenuItemActionWithImage(String icon, String title,
			MainActivity mainActivity, Class<?> clazz, String para) {
		this.mainActivity = mainActivity;
		this.clazz = clazz;
		this.parameter = para;
		int iconResource;
		try {
			if (icon != null || icon.length() > 0) {
				iconResource = mainActivity.getResources().getIdentifier(icon,
						drawable, mainActivity.getPackageName());
			} else {
				iconResource = 0;
			}
		} catch (Exception e) {
			iconResource = 0;
		}
		locations = new ArrayList<Integer>();
		if (clazz != null) {
			className = clazz.getName();
		}
		slideMenuItem = new SlideMenuItem(iconResource, title, className,
				locations);
	}

	public List<MenuItemActionWithImage> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<MenuItemActionWithImage> childrenList) {
		if (childrenList != null && childrenList.size() > 0) {
			slideMenuItem.isGroupItem = true;
		} else {
			slideMenuItem.isGroupItem = false;
		}
		this.childrenList = childrenList;
	}

	public void performAction() {
		if (clazz != null) {
			mainActivity.displayFragment(clazz.getName());
			if (parameter != null) {
				mainActivity.sendArgumentToFragment(clazz.getName(), parameter);
			}
		}
	}

	public SlideMenuItem getSlideMenuItem() {
		return slideMenuItem;
	}
}