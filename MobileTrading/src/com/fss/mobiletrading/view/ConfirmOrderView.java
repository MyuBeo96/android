package com.fss.mobiletrading.view;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.function.oddorderregister.OddLotItem;
import com.fss.mobiletrading.object.ConfirmOrderItem;

import android.R.bool;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConfirmOrderView extends LinearLayout {

	TextView tv_Symbol;
	TextView tv_Quantity;
	TextView tv_Price;
	TextView tv_Side;
	CheckBox checkBox;

	public ConfirmOrderView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.confirm_order_item, this);
		tv_Symbol = (TextView) findViewById(R.id.text_confirmorder_item_symbol);
		tv_Quantity = (TextView) findViewById(R.id.text_confirmorder_item_quantity);
		tv_Price = (TextView) findViewById(R.id.text_confirmorder_item_price);
		tv_Side = (TextView) findViewById(R.id.text_confirmorder_item_side);
		checkBox = (CheckBox) findViewById(R.id.checkbox_confirmorder_item_check);
		checkBox.setChecked(false);

	}

	public void setCheck(boolean isCheck) {
		// TODO Auto-generated method stub
		checkBox.setChecked(isCheck);
	}

	public boolean getCheck() {
		// TODO Auto-generated method stub
		return checkBox.isChecked();
	}

	public void setView(ConfirmOrderItem item) {
		// TODO Auto-generated method stub
		tv_Symbol.setText(item.SYMBOL);
		tv_Quantity.setText(Common.formatAmount(item.ORDERQTTY));
		tv_Price.setText(Common.formatAmount(item.QUOTEPRICE));
		tv_Side.setText(item.EXECTYPE);
	}
}
