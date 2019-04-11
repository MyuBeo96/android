package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.object.StockStatementItem;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;

public class StockStatementItemView extends LinearLayout {
	TextView tv_Date;
	TextView tv_Descriptions;
	TextView tv_IncreaseAmount;
	TextView tv_ReducedAmount;
	TextView tv_Symbol;

	public StockStatementItemView(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.stockstatement_item, this);
		this.tv_Symbol = ((TextView) findViewById(R.id.text_SaoKeCKItem_Symbol));
		this.tv_Date = ((TextView) findViewById(R.id.text_SaoKeCKItem_Date));
		this.tv_IncreaseAmount = ((TextView) findViewById(R.id.text_SaoKeCKItem_IncreaseAmount));
		this.tv_ReducedAmount = ((TextView) findViewById(R.id.text_SaoKeCKItem_ReducedAmount));
		this.tv_Descriptions = ((TextView) findViewById(R.id.text_SaoKeCKItem_Descriptions));
	}

	public void setListView(StockStatementItem item, boolean expand) {
		this.tv_Symbol.setText(item.symbol);
		this.tv_Date.setText(item.date);
		if (item.credit.equals("0")) {
			this.tv_ReducedAmount.setText(Common.formatAmount(item.debit));
			this.tv_IncreaseAmount.setText("0");
		} else {
			this.tv_ReducedAmount.setText("0");
			this.tv_IncreaseAmount.setText(Common.formatAmount(item.credit));
		}
		tv_Descriptions.setText(item.descriptions);
		if (DeviceProperties.isTablet) {

		} else {
			if (expand && item.descriptions.length() > 0) {
				tv_Descriptions.setVisibility(VISIBLE);
			} else {
				tv_Descriptions.setVisibility(GONE);
			}
		}
	}
}
