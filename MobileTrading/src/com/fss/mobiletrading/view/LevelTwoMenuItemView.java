package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.LevelTwoMenu_Adapter;
import com.fss.mobiletrading.menu.MenuItemAction;

public class LevelTwoMenuItemView extends LinearLayout implements
		OnClickListener {
	TextView tv_Title;
	MenuItemAction action;
	LevelTwoMenu_Adapter adapter;

	public LevelTwoMenuItemView(Context context, LevelTwoMenu_Adapter adapter) {
		super(context);
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.leveltwomenu_item, this);
		tv_Title = ((TextView) findViewById(R.id.text_leveltwomenu_item_title));
		tv_Title.setOnClickListener(this);
		this.adapter = adapter;
	}

	public void setView(MenuItemAction action) {
		tv_Title.setText(action.getTitle());
		this.action = action;
	}

	@Override
	public void setSelected(boolean selected) {
		// TODO Auto-generated method stub
		tv_Title.setActivated(selected);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.text_leveltwomenu_item_title) {
			adapter.reset();
			tv_Title.setActivated(true);
			action.performAction();
		}
	}
}
