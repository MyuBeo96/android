package com.fss.mobiletrading.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.view.TradeLogItem_View;
import com.mtrading.mobile.graph.IndexVolumeChartPoint;

public class TradeLog_Adapter extends ArrayAdapter<IndexVolumeChartPoint> {
	Context context;
	List<IndexVolumeChartPoint> list_point;
	int itemCheckedPosition;

	public TradeLog_Adapter(Context paramContext, int paramInt,
			List<IndexVolumeChartPoint> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list_point = paramList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new TradeLogItem_View(this.context);
		}
		boolean isChecked = (position == itemCheckedPosition);
		((TradeLogItem_View) convertView).setListView(
				(IndexVolumeChartPoint) this.list_point.get(position),
				isChecked);

		return convertView;
	}

	public void setItemCheckedPosition(int itemCheckedPosition) {
		this.itemCheckedPosition = itemCheckedPosition;
		super.notifyDataSetChanged();
	}
}
