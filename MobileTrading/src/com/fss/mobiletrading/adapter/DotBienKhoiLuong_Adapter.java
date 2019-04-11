package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.object.DotBienKhoiLuongItem;
import com.fss.mobiletrading.view.DotBienKhoiLuongItemView;

public class DotBienKhoiLuong_Adapter extends
		ArrayAdapter<DotBienKhoiLuongItem> {
	List<DotBienKhoiLuongItem> list;
	Context context;

	public DotBienKhoiLuong_Adapter(Context paramContext, int paramInt,
			List<DotBienKhoiLuongItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list = paramList;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			DotBienKhoiLuongItemView view = new DotBienKhoiLuongItemView(
					this.context);
			hm.put(position, view);
			if (position % 2 == 0) {
				view.setBackgroundResource(R.drawable.background_listview_item);
			}
		}
		View view = hm.get(position);
		((DotBienKhoiLuongItemView) view).setView(list.get(position));

		return view;
	}
}
