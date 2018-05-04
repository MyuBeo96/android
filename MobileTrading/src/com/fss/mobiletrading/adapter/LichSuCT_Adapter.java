package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.object.LichSuCT_Item;
import com.fss.mobiletrading.view.LichSuCT_View;

public class LichSuCT_Adapter extends ArrayAdapter<LichSuCT_Item> {
	Context context;
	List<LichSuCT_Item> listitem;

	public LichSuCT_Adapter(Context paramContext, int paramInt,
			List<LichSuCT_Item> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.listitem = paramList;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			LichSuCT_View view = new LichSuCT_View(this.context);
			hm.put(position, view);
			if (position % 2 == 0) {
				view.setBackgroundResource(R.drawable.background_listview_item);
			}
		}
		View view = hm.get(position);
		((LichSuCT_View) view).setListview(listitem.get(position));
		return view;
	}
}
