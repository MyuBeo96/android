package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.object.LichSuUT_Item;
import com.fss.mobiletrading.view.LichSuUT_View;

public class LichSuUT_Adapter extends ArrayAdapter<LichSuUT_Item> {
	Context context;
	List<LichSuUT_Item> list_historyOrder;

	public LichSuUT_Adapter(Context paramContext, int paramInt,
			List<LichSuUT_Item> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list_historyOrder = paramList;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			LichSuUT_View view = new LichSuUT_View(this.context);
			hm.put(position, view);
			if (position % 2 == 0) {
				view.setBackgroundResource(R.drawable.background_listview_item);
			}
		}
		View view = hm.get(position);
		((LichSuUT_View) view).setListView(list_historyOrder.get(position));

		return view;
	}
}
