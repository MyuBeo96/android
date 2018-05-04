package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.object.SolenhItem;
import com.fss.mobiletrading.view.GTCOrderListItemView;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;

import java.util.ArrayList;
import java.util.List;

public class SolenhGTC_Adapter extends ArrayAdapter<SolenhItem> {
	Context context;
	List<SolenhItem> list;
	/**
	 * có độ dài bằng với data, chứa 1 biến kiểu boolean (quyết định item đó có
	 * được hiển thị hay không)
	 */
	List<Integer> filterItemPosition;
	SimpleAction mItemClickAction;
	SimpleAction mCancelClickAction;
	public boolean isUpdate = true;
	int item_odd_bg_color;
	int item_even_bg_color;
	int count;

	public SolenhGTC_Adapter(Context context, int paramInt,
			List<SolenhItem> object) {
		super(context, paramInt, object);
		this.context = context;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
		list = object;
		filterItemPosition = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			filterItemPosition.add(i);
		}
	}

	@Override
	public int getCount() {
		return filterItemPosition.size();
	}

	public View getView(int position, View convertView, ViewGroup ViewGroup) {

		if (convertView == null) {
			convertView = new GTCOrderListItemView(context, this,
					mItemClickAction, mCancelClickAction);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		SolenhItem item = null;
		item = list.get(filterItemPosition.get(position));
		((GTCOrderListItemView) convertView).setView(item);
		return convertView;
	}

	public void setItemClickAction(SimpleAction action) {
		mItemClickAction = action;
	}

	public void setmCancelClickAction(SimpleAction mCancelClickAction) {
		this.mCancelClickAction = mCancelClickAction;
	}

	@Override
	public void notifyDataSetChanged() {
		if (isUpdate) {
			super.notifyDataSetChanged();
		}
	}

	public List<Integer> getFilterItemPosition() {
		return filterItemPosition;
	}

	public void setFilterItemPosition(List<Integer> filterItemPosition) {
		this.filterItemPosition = filterItemPosition;
	}
}
