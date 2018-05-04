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
import com.fss.mobiletrading.object.DotBienGiaItem;
import com.fss.mobiletrading.view.DotBienGiaItemView;

public class DotBienGia_Adapter extends ArrayAdapter<DotBienGiaItem> {
	Context context;
	List<DotBienGiaItem> listDotBienGia;

	public DotBienGia_Adapter(Context paramContext, int paramInt,
			List<DotBienGiaItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.listDotBienGia = paramList;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			DotBienGiaItemView view = new DotBienGiaItemView(this.context);
			hm.put(position, view);
			if (position % 2 == 0) {
				view.setBackgroundResource(R.drawable.background_listview_item);
			}
		}
		View view = hm.get(position);
		((DotBienGiaItemView) view).setView(listDotBienGia.get(position));

		return view;
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.adapter.DotBienGia_Adapter JD-Core Version: 0.6.0
 */