package com.fscuat.mobiletrading.design;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.menu.MenuItemAction;
import com.fscuat.mobiletrading.R;

public class TabMenuLevelSecondItem extends TabItem {
	MenuItemAction action;

	public TabMenuLevelSecondItem(Context context, AttributeSet attrs,
			int weight) {
		super(context, attrs);
		int width = Common.convertDp2Px(context, 0);
		setLayoutParams(new LinearLayout.LayoutParams(width,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, weight));
	}

	@Override
	public void initView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.tabmenulevelsecond_item, this);
	}

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		if (selected) {
			text.setTextColor(getResources().getColor(
					R.color.tabselector_text_color));
		} else {
			text.setTextColor(getResources().getColor(R.color.label_text_color));
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
