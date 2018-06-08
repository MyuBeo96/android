package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.object.BrokerAlertItem;
import com.fss.mobiletrading.object.NewsItem;

public class BrokerAlertItemView extends LinearLayout {
	TextView tv_PostDate;
	TextView tv_Title;

	public BrokerAlertItemView(Context context) {
		super(context);
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.broker_alert_item, this);
		this.tv_Title = ((TextView) findViewById(R.id.text_brokeralert_item_title));
		this.tv_PostDate = ((TextView) findViewById(R.id.text_brokeralert_item_PostDate));
	}

	public void setView(BrokerAlertItem item) {
		this.tv_Title.setText(item.Subject);
		this.tv_PostDate.setText(item.Date);
	}
}
