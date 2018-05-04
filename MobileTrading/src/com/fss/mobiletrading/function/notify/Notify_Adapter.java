package com.fss.mobiletrading.function.notify;

import java.util.HashMap;
import java.util.List;

import com.msbuat.mobiletrading.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class Notify_Adapter extends ArrayAdapter<NotifyItem> {
	Context context;
	List<NotifyItem> list_Notify;
	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public Notify_Adapter(Context paramContext, int paramInt,
			List<NotifyItem> list_Notify) {
		super(paramContext, paramInt, list_Notify);
		this.context = paramContext;
		this.list_Notify = list_Notify;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (!hm.containsKey(position)) {
			Notify_View view = new Notify_View(this.context);
			hm.put(position, view);
		}
		View view = hm.get(position);
		((Notify_View) view).setListView((NotifyItem) this.list_Notify
				.get(position));
		return view;
	}

}
