package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.object.ConfirmOrderItem;
import com.fss.mobiletrading.view.ConfirmOrderView;

public class ConfirmOrder_Adapter extends ArrayAdapter<ConfirmOrderItem> {

	List<ConfirmOrderItem> list;
	Context context;
	StringBuilder builder;

	public ConfirmOrder_Adapter(Context paramContext, int paramInt,
			List<ConfirmOrderItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list = paramList;
		builder = new StringBuilder();
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public void setCheckAll(boolean isChecked) {
		for (Integer key : hm.keySet()) {
			View view = hm.get(key);
			((ConfirmOrderView) view).setCheck(isChecked);
		}
	}

	public void setClickCheckBox(int position) {
		View view = hm.get(position);
		boolean isChecking = ((ConfirmOrderView) view).getCheck();
		((ConfirmOrderView) view).setCheck(!isChecking);
	}

	public String getSubmitOrderIds() {
		// TODO Auto-generated method stub
		builder.setLength(0);
		for (Integer key : hm.keySet()) {
			View view = hm.get(key);
			boolean check = ((ConfirmOrderView) view).getCheck();
			if (check && list.get(key).ORDERID != null) {
				builder.append(list.get(key).ORDERID + ",");
			}
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			ConfirmOrderView view = new ConfirmOrderView(this.context);
			hm.put(position, view);
		}
		View view = hm.get(position);
		((ConfirmOrderView) view).setView(list.get(position));

		return view;
	}
}
