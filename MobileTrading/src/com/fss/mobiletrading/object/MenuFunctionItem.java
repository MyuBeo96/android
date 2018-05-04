package com.fss.mobiletrading.object;

import com.fss.mobiletrading.interfaces.IMenuItem;
import com.msbuat.mobiletrading.MainActivity;

public class MenuFunctionItem implements IMenuItem {

	MainActivity mainActivity;
	Class<?> clazz;

	public MenuFunctionItem(int level, String title, int icon, int key) {
		this.level = level;
		this.title = title;
		this.icon = icon;
		this.keyId = key;
	}

	public MenuFunctionItem(int level, String title, int icon, int key,
			MainActivity mainActivity, Class<?> clazz) {
		this.level = level;
		this.title = title;
		this.icon = icon;
		this.keyId = key;
		this.clazz = clazz;
		this.mainActivity = mainActivity;
	}

	private int icon;
	private int keyId;
	private int level;
	private String title;

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public MenuFunctionItem getMenuFunctionItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void performAction() {
		// TODO Auto-generated method stub
		if (clazz != null) {
			mainActivity.displayFragment(clazz.getName());
		}

	}
}
