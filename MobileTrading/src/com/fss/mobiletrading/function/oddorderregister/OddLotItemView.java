package com.fss.mobiletrading.function.oddorderregister;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OddLotItemView extends LinearLayout {

	TextView tv_Symbol;
	TextView tv_Quantity;
	TextView tv_Price;
	TextView tv_Tax;
	TextView tv_Receive;

	public OddLotItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.odd_order_register_item, this);
		tv_Symbol = (TextView) findViewById(R.id.text_odd_order_register_symbol);
		tv_Quantity = (TextView) findViewById(R.id.text_odd_order_register_quantity);
		tv_Price = (TextView) findViewById(R.id.text_odd_order_register_price);
		tv_Tax = (TextView) findViewById(R.id.text_odd_order_register_tax);
		tv_Receive = (TextView) findViewById(R.id.text_odd_order_register_receiveamount);
	}

	public void setView(OddLotItem item) {
		// TODO Auto-generated method stub
		tv_Symbol.setText(item.SYMBOL);
		tv_Quantity.setText(Common.formatAmount(item.QUANTITY));
		tv_Price.setText(Common.formatAmount(item.QUOTEPRICE));
		tv_Tax.setText(Common.formatAmount(item.TAXAMT));
		tv_Receive.setText(Common.formatAmount(item.AMOUNT));
	}
}
