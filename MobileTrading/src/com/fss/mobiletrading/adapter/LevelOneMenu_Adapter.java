package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.menu.MenuItemAction;
import com.fss.mobiletrading.view.LevelOneMenuItemView;

public class LevelOneMenu_Adapter extends ArrayAdapter<MenuItemAction> {
	List<MenuItemAction> list;

	Context context;

	public LevelOneMenu_Adapter(Context paramContext, int paramInt,
			List<MenuItemAction> pr_list) {
		super(paramContext, paramInt, pr_list);
		this.context = paramContext;
		this.list = pr_list;
	}

	public void notifyDataSetChanged(List<MenuItemAction> list) {
		this.list.clear();
		if (list != null) {
			this.list.addAll(list);
		}
		reset();
		super.notifyDataSetChanged();
	};

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public void reset() {
		// TODO Auto-generated method stub
		for (int i = 0; i < hm.size(); i++) {
			View view = hm.get(i);
			((LevelOneMenuItemView) view).setSelected(false);
		}
	}

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			LevelOneMenuItemView view = new LevelOneMenuItemView(this.context,
					this);
			hm.put(position, view);
		}
		View view = hm.get(position);
		((LevelOneMenuItemView) view).setView(list.get(position));

		return view;
	}
}
