package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.object.DanhSachCanhBaoItem;

public class DanhSachCanhBaoItemView extends LinearLayout {
	TextView tv_Condition;
	TextView tv_CreateDay;
	TextView tv_Symbols;

	public DanhSachCanhBaoItemView(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.alert_coban_item, this);
		this.tv_Symbols = ((TextView) findViewById(R.id.text_alert_coban_item_Symbols));
		this.tv_CreateDay = ((TextView) findViewById(R.id.text_alert_coban_item_CreateDay));
		this.tv_Condition = ((TextView) findViewById(R.id.text_alert_coban_item_Condition));
	}

	public void setView(DanhSachCanhBaoItem paramDanhSachCanhBaoItem) {
		this.tv_Symbols.setText(paramDanhSachCanhBaoItem.Symbols);
		this.tv_CreateDay.setText(paramDanhSachCanhBaoItem.CreateDate);
		this.tv_Condition.setText(paramDanhSachCanhBaoItem.Condition);
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.view.DanhSachCanhBaoItemView JD-Core Version: 0.6.0
 */