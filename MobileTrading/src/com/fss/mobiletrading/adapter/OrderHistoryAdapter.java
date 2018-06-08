package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.fss.mobiletrading.object.LichSuLenh_Item;
import com.fss.mobiletrading.view.OrderHistoryItemView;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.FilterArrayAdapter;
import java.util.HashMap;
import java.util.List;

public class OrderHistoryAdapter extends FilterArrayAdapter<LichSuLenh_Item> {
	Context context;
	List<LichSuLenh_Item> list_historyOrder;
	HashMap<Integer, View> hm = new HashMap<Integer, View>();
	int item_odd_bg_color;
	int item_even_bg_color;

	public OrderHistoryAdapter(Context paramContext, int paramInt,
			List<LichSuLenh_Item> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list_historyOrder = paramList;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new OrderHistoryItemView(context);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		((OrderHistoryItemView) convertView)
				.setListView((LichSuLenh_Item) filteredData.get(position));

		return convertView;
	}
}
