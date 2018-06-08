package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.object.DotBienGiaKhoiLuongItem;
import com.fss.mobiletrading.view.DotBienGiaKhoiLuongItemView;
import com.fss.mobiletrading.view.DotBienGiaKhoiLuongItemView;

public class DotBienGiaKhoiLuong_Adapter extends
		ArrayAdapter<DotBienGiaKhoiLuongItem> {
	Context context;
	List<DotBienGiaKhoiLuongItem> listDotBienGiaKL;
	Map<Integer, DotBienGiaKhoiLuongItemView> mvViews = new HashMap<Integer, DotBienGiaKhoiLuongItemView>();

	public DotBienGiaKhoiLuong_Adapter(Context paramContext, int paramInt,
			List<DotBienGiaKhoiLuongItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.listDotBienGiaKL = paramList;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			DotBienGiaKhoiLuongItemView view = new DotBienGiaKhoiLuongItemView(
					this.context);
			hm.put(position, view);
			if (position % 2 == 0) {
				view.setBackgroundResource(R.drawable.background_listview_item);
			}
		}
		View view = hm.get(position);
		((DotBienGiaKhoiLuongItemView) view).setView(listDotBienGiaKL
				.get(position));

		return view;
	}
}
