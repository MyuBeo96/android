package com.fss.mobiletrading.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.object.BangGia_Item;
import com.fss.mobiletrading.view.FullWatchListItemView;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;
import java.util.List;

public class FullWatchListAdapter extends ArrayAdapter<BangGia_Item> {

	public static final int BidOffer1 = 1;
	public static final int BidOffer2 = 2;
	public static final int BidOffer3 = 0;

	public int BidOffer = BidOffer1;

	List<BangGia_Item> list;
	int item_odd_bg_drawable;
	int item_even_bg_drawable;
	Context context;

	public FullWatchListAdapter(Context paramContext, int paramInt,
			List<BangGia_Item> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list = paramList;
		item_odd_bg_drawable = R.drawable.bg_listviewitem_odd_background;
		item_even_bg_drawable = R.drawable.bg_listviewitem_even_background;
	}

	public View getView(int position, View convertView, ViewGroup paramViewGroup) {
		if (convertView == null) {
			convertView = new FullWatchListItemView(this.context);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundResource(item_even_bg_drawable);
			} else {
				convertView.setBackgroundResource(item_odd_bg_drawable);
			}
		}
		((FullWatchListItemView) convertView).setView(list.get(position),
				BidOffer);
		return convertView;
	}

	public void next() {
		BidOffer++;
		BidOffer = BidOffer % 3;
		notifyDataSetChanged();
	}

	/**
	 * xóa highlight
	 */
	public void clearHighLight() {
		int listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			list.get(i).setOldItem(null);
		}
		notifyDataSetChanged();

	}

	/**
	 * đổ dữ liệu vào listview
	 */
	public void loadData() {
		notifyDataSetChanged();
	}
}
