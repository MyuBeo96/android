package com.fss.mobiletrading.function.cashtransfer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.object.BankAccItem;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;

import java.util.List;

public class TransferListAdapter extends ArrayAdapter<BankAccItem> {
	Context context;
	List<BankAccItem> list;
	int expandPosition = -1;
	int item_odd_bg_color;
	int item_even_bg_color;

	public TransferListAdapter(Context context, int resource,
			List<BankAccItem> object) {
		super(context, resource, object);
		this.context = context;
		this.list = object;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
	}

	public View getView(int position, View convertView, ViewGroup ViewGroup) {

		if (convertView == null) {
			convertView = new TransferListItemView(this.context);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		boolean expand = (position == expandPosition) || DeviceProperties.isTablet;
		((TransferListItemView) convertView).setView(list.get(position),
				expand, position);
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
