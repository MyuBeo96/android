package com.fss.mobiletrading.function.orderlist;

import java.util.List;

import com.fss.mobiletrading.object.SolenhItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ResultCancelAllOrderAdapter extends ArrayAdapter<SolenhItem> {

	Context context;
	List<SolenhItem> list;

	public ResultCancelAllOrderAdapter(Context context, int resource,
			List<SolenhItem> objects) {
		super(context, resource, objects);
		this.context = context;
		list = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new ResultCancelAllOrderItemView(context);
		}
		((ResultCancelAllOrderItemView) convertView)
				.setView(list.get(position));
		return convertView;
	}

}
