package com.fss.mobiletrading.menu;

import java.util.List;

import com.fss.mobiletrading.interfaces.IStringAction;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.MainActivity_Tablet;

public class MenuItemAction implements IStringAction {

	public static final int STYLE_1 = 1;
	public static final int STYLE_2 = 2;

	public MainActivity mainActivity;
	String title;
	int style = STYLE_1;
	String className;
	Class<?> clazz;
	private List<MenuItemAction> childrenList;

	public MenuItemAction(String title, MainActivity mainActivity,
			Class<?> clazz, String className, int style) {
		this.style = style;
		this.mainActivity = mainActivity;
		this.title = title;
		this.clazz = clazz;
		this.className = className;
	}

	public List<MenuItemAction> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<MenuItemAction> childrenList) {
		this.childrenList = childrenList;
	}

	public int getStyle() {
		return style;
	}

	@Override
	public String getTitle() {

		return title;
	}

	@Override
	public void performAction() {

		if (clazz != null) {
			if (mainActivity instanceof MainActivity_Tablet) {
				((MainActivity_Tablet) mainActivity).showFullPriceBoard(false);
			}
			mainActivity.displayFragment(clazz.getName());
		}
	}

	@Override
	public String toString() {

		return title;
	}

	public String getClassName() {
		return className;
	}

}