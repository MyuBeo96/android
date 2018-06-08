package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.function.watchlist.BangGia_View;
import com.fss.mobiletrading.object.BrokerAlertItem;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.view.BrokerAlertItemView;
import com.fss.mobiletrading.view.NewsItemView;

public class BrokerAlertAdapter extends ArrayAdapter<BrokerAlertItem> {
	Context context;
	List<BrokerAlertItem> list_BrokerAlert;

	public BrokerAlertAdapter(Context context, int resource,
			List<BrokerAlertItem> object) {
		super(context, resource, object);
		this.context = context;
		this.list_BrokerAlert = object;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			BrokerAlertItemView view = new BrokerAlertItemView(this.context);
			hm.put(position, view);
			if (position % 2 == 0) {
				view.setBackgroundResource(R.drawable.background_listview_item);
			}
		}
		View view = hm.get(position);
		((BrokerAlertItemView) view).setView(list_BrokerAlert.get(position));

		return view;
	}

}
