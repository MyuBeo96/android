package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.function.watchlist.BangGia_View;
import com.fss.mobiletrading.object.LichSuDKQM_Item;
import com.fss.mobiletrading.view.LichSuDKQM_View;

public class LichSuDKQM_Adapter extends ArrayAdapter<LichSuDKQM_Item> {
	Context context;
	List<LichSuDKQM_Item> list_historyOrder;

	public LichSuDKQM_Adapter(Context paramContext, int paramInt,
			List<LichSuDKQM_Item> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list_historyOrder = paramList;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			LichSuDKQM_View view = new LichSuDKQM_View(this.context);
			hm.put(position, view);
			if (position % 2 == 0) {
				view.setBackgroundResource(R.drawable.background_listview_item);
			}
		}
		View view = hm.get(position);
		((LichSuDKQM_View) view).setListView(list_historyOrder.get(position));

		return view;
	}
}
