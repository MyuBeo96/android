package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.object.OrderDetailsItem;

public class SoLenhCT_View extends LinearLayout {
	TextView tv_Con;
	TextView tv_Dat;
	TextView tv_Gia;
	TextView tv_Khop;
	TextView tv_Time;

	public SoLenhCT_View(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.solenhct_item, this);
		tv_Time = ((TextView) findViewById(R.id.text_solenhct_item_Time));
		tv_Khop = ((TextView) findViewById(R.id.text_solenhct_item_Khop));
		tv_Gia = ((TextView) findViewById(R.id.text_solenhct_item_Gia));
		tv_Dat = ((TextView) findViewById(R.id.text_solenhct_item_Dat));
		tv_Con = ((TextView) findViewById(R.id.text_solenhct_item_Con));
	}

	public void setView(OrderDetailsItem item) {
		tv_Time.setText(item.time);
		tv_Khop.setText(Common.formatAmount(item.qtyMatched));
		tv_Gia.setText(item.priceOrder);
		tv_Dat.setText(Common.formatAmount(item.qtyOrder));
		tv_Con.setText(Common.formatAmount(item.qtyRemain));
	}
}
