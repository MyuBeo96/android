package com.fss.mobiletrading.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.object.StockItem;
import com.fss.mobiletrading.view.KBHookItemView;

public class KBHookAdapter extends ArrayAdapter<String> {

	Context context;
	List<String> listItem;
	String symbol;

	public KBHookAdapter(Context context, int resource, List<String> objects) {
		super(context, resource, objects);
		this.context = context;
		listItem = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new KBHookItemView(context, null);
		}
		((KBHookItemView) convertView).setView(listItem.get(position));
		return convertView;
	}
}
