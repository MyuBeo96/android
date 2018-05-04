package com.fss.mobiletrading.menu;

import java.util.List;

public class SlideMenuItem {
	int icon;
	String title;
	String className;
	public boolean isExpand = false;
	public boolean isGroupItem = false;
	public boolean isHighLight = false;
	List<Integer> locations;

	public SlideMenuItem(int icon, String title, String className,
			List<Integer> locations) {
		this.title = title;
		this.icon = icon;
		this.locations = locations;
		this.className = className;
	}

	public String getTitle() {
		return title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return title;
	}

	public List<Integer> parentPositions() {
		return locations;
	}

	public void setParentPositions(List<Integer> parentPositions) {
		this.locations = parentPositions;
	}
}
