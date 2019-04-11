package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.object.OrderDetailsItem;
import com.fss.mobiletrading.view.SoLenhCT_View;
import com.fss.mobiletrading.view.SoLenhCT_View;

public class SolenhCT_Adapter extends ArrayAdapter<OrderDetailsItem> {
	Context context;
	List<OrderDetailsItem> list;

	public SolenhCT_Adapter(Context context, int paramInt,
			List<OrderDetailsItem> paramList) {
		super(context, paramInt, paramList);
		this.context = context;
		this.list = paramList;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			SoLenhCT_View view = new SoLenhCT_View(this.context);
			hm.put(position, view);
			if (position % 2 == 0) {
				view.setBackgroundResource(R.drawable.background_listview_item);
			}
		}
		View view = hm.get(position);
		((SoLenhCT_View) view).setView(list.get(position));

		return view;
	}
}
