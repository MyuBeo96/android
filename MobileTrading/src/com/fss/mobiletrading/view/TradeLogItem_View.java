package com.fss.mobiletrading.view;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.mtrading.mobile.graph.IndexVolumeChartPoint;

@SuppressLint("ResourceAsColor")
public class TradeLogItem_View extends LinearLayout {
	TextView tv_time, tv_marketcode, tv_change, tv_volume;

	public TradeLogItem_View(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.tradelog_item, this);
		tv_time = ((TextView) findViewById(R.id.time));
		tv_marketcode = ((TextView) findViewById(R.id.marketcode));
		tv_change = ((TextView) findViewById(R.id.change));
		tv_volume = ((TextView) findViewById(R.id.volume));
	}

	DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	int currentId;

	public void setListView(IndexVolumeChartPoint aPointItem, boolean isChecked) {
		if (isChecked) {
			this.setBackgroundResource(R.color.bg_listviewitemselected_2);
		} else {
			this.setBackgroundResource(R.color.transparent);
		}
		if (currentId == aPointItem.hashCode()) {
			return;
		}
		currentId = aPointItem.hashCode();
		tv_time.setText(aPointItem.getStrTime());
		tv_volume.setText(String.valueOf(decimalFormat.format(aPointItem
				.getFullvolume())));
		tv_marketcode.setText(String.valueOf(aPointItem.getIndex()));
		tv_change.setText(String.valueOf(aPointItem.getChange()));
		if (aPointItem.getChange() > 0) {
			tv_change.setTextColor(getResources().getColor(R.color.green_text));
			tv_marketcode.setTextColor(getResources().getColor(
					R.color.green_text));
		} else if (aPointItem.getChange() < 0) {
			tv_change.setTextColor(getResources().getColor(
					R.color.red_brown_text));
			tv_marketcode.setTextColor(getResources().getColor(
					R.color.red_brown_text));
		} else {
			tv_change
					.setTextColor(getResources().getColor(R.color.yellow_text));
			tv_marketcode.setTextColor(getResources().getColor(
					R.color.yellow_text));
		}
	}
}
