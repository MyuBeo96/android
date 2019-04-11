package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.function.coporateactions.CoporateActionItem;
import com.fss.mobiletrading.view.CoporateActionView;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;
import java.util.HashMap;
import java.util.List;

public class CoporateAction_Adapter extends ArrayAdapter<CoporateActionItem> {

	Context context;
	List<CoporateActionItem> list;
	int item_odd_bg_color;
	int item_even_bg_color;

	public CoporateAction_Adapter(Context context, int paramInt,
			List<CoporateActionItem> object) {
		super(context, paramInt, object);
		this.context = context;
		list = object;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new CoporateActionView(context);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		((CoporateActionView) convertView).setView(list.get(position));
		return convertView;
	}
}
