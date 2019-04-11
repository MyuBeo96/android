package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.object.DanhSachChamNguongItem;

public class DanhSachChamNguongItemView extends LinearLayout {
	TextView tv_Condition;
	TextView tv_CreateDay;
	TextView tv_Symbols;
	TextView tv_Type;

	public DanhSachChamNguongItemView(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.alert_chamnguong_item, this);
		this.tv_Symbols = ((TextView) findViewById(R.id.text_alert_chamnguong_item_Symbols));
		this.tv_CreateDay = ((TextView) findViewById(R.id.text_alert_chamnguong_item_CreateDay));
		this.tv_Condition = ((TextView) findViewById(R.id.text_alert_chamnguong_item_Condition));
		this.tv_Type = ((TextView) findViewById(R.id.text_alert_chamnguong_item_Type));
	}

	public void setView(DanhSachChamNguongItem paramDanhSachChamNguongItem) {
		this.tv_Symbols.setText(paramDanhSachChamNguongItem.SYMBOLS);
		this.tv_CreateDay.setText(paramDanhSachChamNguongItem.ALERTDT);
		this.tv_Condition.setText(paramDanhSachChamNguongItem.MESSAGE);
		this.tv_Type.setText(paramDanhSachChamNguongItem.ALERTTYPE);
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.view.DanhSachChamNguongItemView JD-Core Version: 0.6.0
 */