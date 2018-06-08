package com.fss.mobiletrading.function.searchstock;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.object.AcctnoItem;

public class SearchStockItemView extends LinearLayout {
	TextView tv_date;
	TextView tv_reference;
	TextView tv_sum;
	TextView tv_ceil;
	TextView tv_floor;

	public SearchStockItemView(Context context) {
		super(context);
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.search_stock_item, this);
		tv_date = ((TextView) findViewById(R.id.text_searchstock_item_tradedate));
		tv_reference = ((TextView) findViewById(R.id.text_reference));
		tv_sum = ((TextView) findViewById(R.id.text_searchstock_item_sum));
		tv_ceil = ((TextView) findViewById(R.id.text_searchstock_item_ceil));
		tv_floor = ((TextView) findViewById(R.id.text_searchstock_item_floor));
	}

	public void setView(SearchStockItem item) {
		tv_date.setText(item.tradingdate);
		tv_reference.setText(Common.formatAmount(item.reference));
		tv_sum.setText(Common.formatAmount(item.totalTrading));
		tv_ceil.setText(Common.formatAmount(item.ceiling));
		tv_floor.setText(Common.formatAmount(item.floor));
	}
}
