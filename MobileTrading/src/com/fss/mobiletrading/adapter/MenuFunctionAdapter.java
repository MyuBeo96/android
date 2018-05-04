package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.menu.MenuFunctionItemView;
import com.fss.mobiletrading.object.MenuFunctionItem;
import com.fss.mobiletrading.object.MenuItemImplement;

public class MenuFunctionAdapter extends ArrayAdapter<MenuFunctionItem> {
	Context context;
	List<MenuFunctionItem> listItem;

	public MenuFunctionAdapter(Context context, int paramInt,
			List<MenuFunctionItem> listItem) {
		super(context, paramInt, listItem);
		this.context = context;
		this.listItem = listItem;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			MenuFunctionItemView view = new MenuFunctionItemView(this.context);
			hm.put(position, view);
		}
		View view = hm.get(position);
		// ((MenuFunctionItemView) view).setView(listItem.get(position));

		return view;
	}
}
