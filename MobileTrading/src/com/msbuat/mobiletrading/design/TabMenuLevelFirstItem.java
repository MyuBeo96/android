package com.msbuat.mobiletrading.design;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.fss.mobiletrading.menu.MenuItemAction;
import com.msbuat.mobiletrading.R;

public class TabMenuLevelFirstItem extends TabItem {
	MenuItemAction action;

	public TabMenuLevelFirstItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void initView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.tabmenulevelfirst_item, this);
		setLayoutParams(new LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	}

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		if (selected) {
			text.setTextColor(getResources().getColor(R.color.red_brown_text));
		} else {
			text.setTextColor(getResources().getColor(R.color.white_text));
		}
	}

	public void performAction() {
		if (action != null) {
			action.performAction();
		}
	}

	public void setAction(MenuItemAction action) {
		this.action = action;
		if (action != null) {
			text.setText(action.getTitle());
		}

	}

	public MenuItemAction getAction() {
		return action;
	}
}
//
// <TextView
// android:id="@+id/text_tabselector"
// android:layout_width="match_parent"
// android:layout_height="match_parent"
// android:layout_above="@+id/line_tabselector"
// android:background="@color/header_background_color"
// android:gravity="center"
// android:textAllCaps="true" />
