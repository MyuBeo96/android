package com.fss.mobiletrading.function.notify;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.consts.StringConst;
import com.msbuat.mobiletrading.R;

public class Notify_View extends LinearLayout {
	TextView tvTittleNotify, tvContentNotify, tvDateNotify;
	int UnReadColor = Color.parseColor("#60ffffff");
	LinearLayout background;

	public Notify_View(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.notify_item, this);
		tvTittleNotify = ((TextView) findViewById(R.id.tvTittleNotify));
		tvDateNotify = ((TextView) findViewById(R.id.tvDateNotify));
		background = (LinearLayout) findViewById(R.id.notify_item);
	}

	public void setListView(NotifyItem paramNotify_Item) {
		if (paramNotify_Item.isHasRead().equals(StringConst.SEQUENCE_FALSE)) {
			background.setBackgroundResource(R.color.header_background_color);
		} else {
			background.setBackgroundResource(R.color.transparent);
		}
		tvTittleNotify.setText(paramNotify_Item.getShort());
		tvDateNotify.setText(paramNotify_Item.getDate());
	}
}
