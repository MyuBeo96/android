package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.object.LichSuLenh_Item;

public class OrderHistoryItemView extends LinearLayout {
	TextView tv_Gia;
	TextView tv_MaCk;
	TextView tv_Ngay;
	TextView tv_Side;
	TextView tv_Soluong;
	TextView tv_Fee;
	TextView tv_Tax;
	TextView tv_MatchedValue;
	TextView tv_Method;
	static final String Mua = "NB";
	int buycolor;
	int sellcolor;

	public OrderHistoryItemView(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.orderhistory_item, this);
		tv_Ngay = ((TextView) findViewById(R.id.text_LichSuLenh_item_Ngay));
		tv_MaCk = ((TextView) findViewById(R.id.text_LichSuLenh_item_MaCK));
		tv_Soluong = ((TextView) findViewById(R.id.text_LichSuLenh_item_SoLuong));
		tv_Side = ((TextView) findViewById(R.id.text_LichSuLenh_item_Side));
		tv_Gia = ((TextView) findViewById(R.id.text_LichSuLenh_item_Gia));
		tv_Fee = ((TextView) findViewById(R.id.text_fee));
		tv_Tax = ((TextView) findViewById(R.id.text_tax));
		tv_MatchedValue = ((TextView) findViewById(R.id.text_matchedvalue));
		tv_Method = ((TextView) findViewById(R.id.text_method));
		buycolor = getResources().getColor(R.color.color_Mua);
		sellcolor = getResources().getColor(R.color.color_Ban);
	}

	public void setListView(LichSuLenh_Item item) {
		tv_MaCk.setText(item.getSymbol());
		tv_Side.setText(item.getSideDesc());

		String side = item.getSide();
		if (side.equals(Mua)) {
			tv_Side.setTextColor(buycolor);
		} else {
			tv_Side.setTextColor(sellcolor);
		}
		tv_Ngay.setText(item.getDate());
		tv_Soluong.setText(Common.formatAmount(item.getQty()));
		tv_Gia.setText(Common.formatAmount(item.getPrice()));
		tv_Fee.setText(Common.formatAmount(item.getCommisionFee()));
		tv_Tax.setText(Common.formatAmount(item.getSellTaxAmt()));
		tv_Method.setText(item.getVia());
		tv_MatchedValue.setText(Common.formatAmount(item.getValue()));
	}
}
