package com.fss.mobiletrading.function.searchstock;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;

import java.util.List;

public class SearchStock_Adapter extends ArrayAdapter<SearchStockItem> {
	List<SearchStockItem> list;
	int item_odd_bg_color;
	int item_even_bg_color;
	Context context;

	public SearchStock_Adapter(Context paramContext, int paramInt,
			List<SearchStockItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list = paramList;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
	}

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (convertView == null) {
			convertView = new SearchStockItemView(this.context);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		((SearchStockItemView) convertView).setView(list.get(position));

		return convertView;
	}
}
