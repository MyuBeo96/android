package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.function.oddorderregister.OddLotItem;
import com.fss.mobiletrading.function.oddorderregister.OddLotItemView;
import com.fss.mobiletrading.function.watchlist.BangGia_View;
import com.fss.mobiletrading.object.BangGia_Item;

public class OddOrderRegisterAdapter extends ArrayAdapter<OddLotItem> {
	List<OddLotItem> list;

	Context context;

	public OddOrderRegisterAdapter(Context paramContext, int paramInt,
			List<OddLotItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list = paramList;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			OddLotItemView view = new OddLotItemView(this.context);
			hm.put(position, view);
		}
		View view = hm.get(position);
		((OddLotItemView) view).setView(list.get(position));

		return view;
	}
}
