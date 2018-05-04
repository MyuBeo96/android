package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.object.CashStatementItem;
import com.fss.mobiletrading.view.CashStatementView;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;
import java.util.List;

public class CashStatementAdapter extends ArrayAdapter<CashStatementItem> {
	Context context;
	List<CashStatementItem> list_saoketienItem;
	int expandPosition = -1;
	int item_odd_bg_color;
	int item_even_bg_color;

	public CashStatementAdapter(Context paramContext, int paramInt,
			List<CashStatementItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list_saoketienItem = paramList;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		boolean startendbalance = (position == 0)
				|| (position == (list_saoketienItem.size() - 1));
		if (convertView == null) {
			convertView = new CashStatementView(context);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		boolean expand = (position == expandPosition);
		((CashStatementView) convertView).setListView(
				(CashStatementItem) list_saoketienItem.get(position),
				startendbalance, expand);
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
