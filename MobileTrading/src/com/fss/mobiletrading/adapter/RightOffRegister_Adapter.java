package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.function.rightoffregister.OnRegisterListener;
import com.fss.mobiletrading.function.rightoffregister.OnWatchRoRListener;
import com.fss.mobiletrading.function.rightoffregister.RightOffRegisterItem;
import com.fss.mobiletrading.function.rightoffregister.RightOffRegisterItemView;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import java.util.List;

public class RightOffRegister_Adapter extends
		ArrayAdapter<RightOffRegisterItem> {
	Context context;
	List<RightOffRegisterItem> list_DKQM;
	OnRegisterListener onRegisterListener;
	OnWatchRoRListener onWatchRoRListener;
	int item_odd_bg_color;
	int item_even_bg_color;

	public RightOffRegister_Adapter(Context context, int paramInt,
			List<RightOffRegisterItem> object) {
		super(context, paramInt, object);
		this.context = context;
		this.list_DKQM = object;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
	}

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (convertView == null) {
			convertView = new RightOffRegisterItemView(this.context);
			((RightOffRegisterItemView) convertView)
					.setOnRegisterListener(onRegisterListener);
			((RightOffRegisterItemView) convertView)
					.setOnWatchRoRListener(onWatchRoRListener);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		((RightOffRegisterItemView) convertView).setView(
				list_DKQM.get(position), position);

		return convertView;
	}

	public void setOnRegisterListener(OnRegisterListener onRegisterListener) {
		this.onRegisterListener = onRegisterListener;
	}

	public void setOnWatchRoRListener(OnWatchRoRListener onWatchRoRListener) {
		this.onWatchRoRListener = onWatchRoRListener;
	}
}
