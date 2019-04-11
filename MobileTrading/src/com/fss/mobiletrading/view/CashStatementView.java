package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.object.CashStatementItem;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;

public class CashStatementView extends LinearLayout {
	TextView Giam;
	TextView Ngay;
	TextView Tang;
	TextView tv_Descriptions;
	TextView tv_balance;
	RelativeLayout lay_IncreaseReduced;

	public CashStatementView(Context context) {
		super(context);
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.cashstatement_item, this);
		Ngay = ((TextView) findViewById(R.id.text_Date));
		Tang = ((TextView) findViewById(R.id.text_IncreaseAmount));
		Giam = ((TextView) findViewById(R.id.text_ReducedAmount));
		tv_Descriptions = ((TextView) findViewById(R.id.text_Description));
		tv_balance = ((TextView) findViewById(R.id.text_EndBalance));
		lay_IncreaseReduced = (RelativeLayout) findViewById(R.id.lay_SaoKeTien_item_IncreaseReduced);
	}

	public void setListView(CashStatementItem item, boolean startendbalance,
			boolean expand) {

		Ngay.setText(item.getDate());
		Giam.setText(Common.formatAmount(item.getDebit()));
		Tang.setText(Common.formatAmount(item.getCredit()));
		tv_Descriptions.setText(item.getDescriptions());
		tv_balance.setText(Common.formatAmount(item.getEndbalance()));
		if (DeviceProperties.isTablet) {
			if (startendbalance) {
				tv_Descriptions.setVisibility(GONE);
				Tang.setVisibility(GONE);
				Giam.setVisibility(GONE);
				Ngay.setTextColor(getResources().getColor(R.color.green_text));
			} else {
				tv_Descriptions.setVisibility(VISIBLE);
				Tang.setVisibility(VISIBLE);
				Giam.setVisibility(VISIBLE);
				Ngay.setTextColor(getResources().getColor(R.color.white_text));
			}
		} else {
			if (startendbalance) {
				lay_IncreaseReduced.setVisibility(View.GONE);
				Ngay.setTextColor(getResources().getColor(R.color.green_text));
			} else {
				lay_IncreaseReduced.setVisibility(View.VISIBLE);
				Ngay.setTextColor(getResources().getColor(R.color.white_text));
				if (expand && item.getDescriptions().length() > 0) {
					tv_Descriptions.setVisibility(VISIBLE);
				} else {
					tv_Descriptions.setVisibility(GONE);
				}
			}
		}
	}
}
