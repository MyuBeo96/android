package com.fss.mobiletrading.menu;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcscuat.mobiletrading.R;

public class MenuFunctionItemView extends LinearLayout {

	public static final int LEVEL_0 = 0;
	public static final int LEVEL_1 = 1;

	ImageView imgview_icon;
	ImageView imgview_group;
	// TextView tv_Space;
	TextView tv_Title;

	public MenuFunctionItemView(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.menu_function_item, this);
		imgview_icon = ((ImageView) findViewById(R.id.img_menu_function_item_icon));
		imgview_group = ((ImageView) findViewById(R.id.img_menu_function_item_group));
		tv_Title = ((TextView) findViewById(R.id.text_menu_function_item_title));
		// tv_Space = ((TextView)
		// findViewById(R.id.text_menu_function_item_space));
	}

	public void setView(SlideMenuItem item) {
		this.setActivated(item.isHighLight);
		if (item.isGroupItem) {
			imgview_group.setVisibility(VISIBLE);
			if (item.isExpand) {
				imgview_group.setRotation(-90);
			} else {
				imgview_group.setRotation(0);
			}
		} else {
			imgview_group.setVisibility(GONE);
		}
		tv_Title.setText(item.getTitle());
		if (item.getIcon() != 0) {
			imgview_icon.setVisibility(VISIBLE);
			imgview_icon.setImageResource(item.getIcon());
		} else {
			imgview_icon.setVisibility(INVISIBLE);
		}
	}
}
