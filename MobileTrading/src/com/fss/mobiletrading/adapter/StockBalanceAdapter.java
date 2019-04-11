package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.object.StockBalanceItem;
import com.fss.mobiletrading.view.StockBalanceItemView;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;
import java.util.HashMap;
import java.util.List;

public class StockBalanceAdapter extends ArrayAdapter<StockBalanceItem> {
	Context context;
	List<StockBalanceItem> listitem;
	int item_odd_bg_color;
	int item_even_bg_color;
	SimpleAction onSellClickListener;
	SimpleAction onBuyClickListener;

	public void setOnSellClickListener(SimpleAction onSellClickListener) {
		this.onSellClickListener = onSellClickListener;
	}

	public void setOnBuyClickListener(SimpleAction onBuyClickListener) {
		this.onBuyClickListener = onBuyClickListener;
	}

	public StockBalanceAdapter(Context context, int paramInt,
			List<StockBalanceItem> object) {
		super(context, paramInt, object);
		this.context = context;
		this.listitem = object;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (convertView == null) {
			convertView = new StockBalanceItemView(this.context);
			((StockBalanceItemView) convertView)
					.setOnBuyClickListener(onBuyClickListener);
			((StockBalanceItemView) convertView)
					.setOnSellClickListener(onSellClickListener);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		((StockBalanceItemView) convertView)
				.setListView(listitem.get(position));

		return convertView;
	}
}
