package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.function.authorizationinfo.PermissionInfoItem;
import com.fss.mobiletrading.view.PermissionInfoItemView;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import java.util.HashMap;
import java.util.List;

public class PermissionTable_Adapter extends ArrayAdapter<PermissionInfoItem> {
	List<PermissionInfoItem> list;
	int item_odd_bg_color;
	int item_even_bg_color;
	Context context;

	public PermissionTable_Adapter(Context paramContext, int paramInt,
			List<PermissionInfoItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list = paramList;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (convertView == null) {
			convertView = new PermissionInfoItemView(this.context);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		((PermissionInfoItemView) convertView).setView(list.get(position));

		return convertView;
	}
}
