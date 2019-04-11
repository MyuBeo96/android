package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.function.watchlist.BangGia_View;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.view.AcctnoItemView;

public class ChonTieuKhoan_Adapter extends ArrayAdapter<AcctnoItem> {
	Context context;
	int selectedPosition;
	List<AcctnoItem> listTieuKhoan;
	HashMap<Integer, View> mapView = new HashMap<Integer, View>();

	public ChonTieuKhoan_Adapter(Context context, int paramInt,
			List<AcctnoItem> object) {
		super(context, paramInt, object);
		this.context = context;
		listTieuKhoan = object;
	}

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!mapView.containsKey(position)) {
			AcctnoItemView view = new AcctnoItemView(this.context);
			mapView.put(position, view);
		}
		View view = mapView.get(position);
		((AcctnoItemView) view).setView(listTieuKhoan.get(position));
		if (position == selectedPosition) {
			((AcctnoItemView) view).setCheck(true);
		} else {
			((AcctnoItemView) view).setCheck(false);
		}
		return view;
	}

	public void setCheck(int position) {
		for (int i = 0; i < mapView.size(); i++) {
			View view = mapView.get(i);
			((AcctnoItemView) view).setCheck(false);
		}
		if (mapView.containsKey(position)) {
			((AcctnoItemView) mapView.get(position)).setCheck(true);
		}
		selectedPosition = position;
	}
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
}
