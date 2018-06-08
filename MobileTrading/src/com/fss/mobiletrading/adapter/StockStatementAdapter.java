package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.object.StockStatementItem;
import com.fss.mobiletrading.view.StockStatementItemView;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import java.util.List;

public class StockStatementAdapter extends ArrayAdapter<StockStatementItem> {
	Context context;
	List<StockStatementItem> list_saokeckItem;
	int expandPosition = -1;
	int item_odd_bg_color;
	int item_even_bg_color;

	public StockStatementAdapter(Context paramContext, int paramInt,
			List<StockStatementItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list_saokeckItem = paramList;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
	}

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (convertView == null) {
			convertView = new StockStatementItemView(this.context);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		boolean expand = (position == expandPosition);
		((StockStatementItemView) convertView).setListView(
				list_saokeckItem.get(position), expand);

		return convertView;
	}

	public void setExpandPosition(int expandPosition) {
		if (this.expandPosition != expandPosition) {
			this.expandPosition = expandPosition;
		} else {
			this.expandPosition = -1;
		}
	}
}
