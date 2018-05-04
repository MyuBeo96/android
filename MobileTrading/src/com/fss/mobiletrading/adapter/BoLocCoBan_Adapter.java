package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.function.watchlist.BangGia_View;
import com.fss.mobiletrading.object.BoLocCoBanItem;
import com.fss.mobiletrading.view.BoLocCoBanItemView;

public class BoLocCoBan_Adapter extends ArrayAdapter<BoLocCoBanItem> {
	Context context;
	List<BoLocCoBanItem> listItem;

	public BoLocCoBan_Adapter(Context paramContext, int paramInt,
			List<BoLocCoBanItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.listItem = paramList;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			BoLocCoBanItemView view = new BoLocCoBanItemView(this.context);
			hm.put(position, view);
			if (position % 2 == 0) {
				view.setBackgroundResource(R.drawable.background_listview_item);
			}
		}
		View view = hm.get(position);
		((BoLocCoBanItemView) view).setView(listItem.get(position));

		return view;
	}
}
