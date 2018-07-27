package com.fscuat.mobiletrading.design;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.object.IndicesItem;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.R;

public class Indices_View extends LinearLayout {

	static final int STATE_CHANGE = 0;
	static final int STATE_PERCENTCHANGE = 1;
	static final int STATE_VOLUME = 0;
	static final int STATE_VALUE = 1;

	ImageView img_updown;
	int[] imgid;
	TextView tv_indexname;
	TextView tv_MarketStatus;
	TextView tv_MarketName;
	TextViewHightLight tv_MarketChange;
	TextViewHightLight tv_MarketChangeP;
	TextViewHightLight tv_MarketIndex;
	TextViewHightLight tv_MarketVolume;
	TextViewHightLight tv_MarketValue;
	TextViewHightLight tv_Trade;
	TextViewHightLight tv_amountUp;
	TextViewHightLight tv_amountDown;
	TextViewHightLight tv_amountUnChange;

	public Indices_View(Context context, AttributeSet attrs) {
		super(context, attrs);
		int[] arrayOfSign = new int[3];
		arrayOfSign[0] = R.drawable.ic_up;
		arrayOfSign[1] = R.drawable.ic_yellow;
		arrayOfSign[2] = R.drawable.ic_downred;
		imgid = arrayOfSign;

		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.indices_item_chiso, this);
		tv_indexname = ((TextView) findViewById(R.id.text_indexname));
		tv_MarketName = ((TextView) findViewById(R.id.text_indices_item_marketname));
		tv_MarketStatus = ((TextView) findViewById(R.id.text_indices_item_marketstatus));
		tv_MarketIndex = ((TextViewHightLight) findViewById(R.id.text_indices_item_marketindex));
		tv_MarketChange = ((TextViewHightLight) findViewById(R.id.text_indices_item_marketchange));
		tv_MarketVolume = ((TextViewHightLight) findViewById(R.id.text_indices_item_volume));
		tv_MarketChangeP = ((TextViewHightLight) findViewById(R.id.text_indices_item_marketchangeP));
		tv_MarketValue = ((TextViewHightLight) findViewById(R.id.text_indices_item_value));
		img_updown = ((ImageView) findViewById(R.id.text_indices_item_kyhieu));

		tv_Trade = (TextViewHightLight) findViewById(R.id.text_indices_item_trade);
		tv_amountUp = (TextViewHightLight) findViewById(R.id.text_indices_item_upstocks);
		tv_amountDown = (TextViewHightLight) findViewById(R.id.text_indices_item_downstocks);
		tv_amountUnChange = (TextViewHightLight) findViewById(R.id.text_indices_item_refstocks);

	}

	public void setIndexName(String name) {
		tv_indexname.setText(name);
	}

	public void setView(IndicesItem item) {
		if (item == null) {
			return;
		}
		tv_MarketName.setText(item.MarketName);
		tv_MarketStatus.setText(item.MarketStatusText);
		tv_MarketIndex.setText(item.MarketIndex);
		tv_MarketVolume.setText(item.MarketVolume);
		tv_MarketValue.setText(item.MarkettotalValue);
		Log.i("Giatri", "/n" +item.MarkettotalValue );
		tv_MarketChange.setText(item.MarketChange);
		if (DeviceProperties.isTablet) {
			tv_MarketChangeP.setText("(" + item.MarketChangeP + "%)");
		} else {
			tv_MarketChangeP.setText(item.MarketChangeP + "%");
		}

		tv_Trade.setText(item.Trade);
		tv_amountUp.setText(item.amountUp);
		tv_amountDown.setText(item.amountDown);
		tv_amountUnChange.setText(item.amountUnChange);

		float change = 0f;
		try {
			change = Float.parseFloat(item.MarketChange);
		} catch (NumberFormatException e) {
		}

		if (change < 0) {
			img_updown.setImageResource(imgid[2]);
			tv_MarketChange.setTextColor(getResources().getColor(
					R.color.color_Ban));
			tv_MarketChangeP.setTextColor(getResources().getColor(
					R.color.color_Ban));
			tv_MarketIndex.setTextColor(getResources().getColor(
					R.color.color_Ban));
		} else {
			if (change == 0) {
				img_updown.setImageResource(imgid[1]);
				tv_MarketChange.setTextColor(getResources().getColor(
						R.color.color_ThamChieu));
				tv_MarketChangeP.setTextColor(getResources().getColor(
						R.color.color_ThamChieu));
				tv_MarketIndex.setTextColor(getResources().getColor(
						R.color.color_ThamChieu));
			} else {
				img_updown.setImageResource(imgid[0]);
				tv_MarketChange.setTextColor(getResources().getColor(
						R.color.color_Mua));
				tv_MarketChangeP.setTextColor(getResources().getColor(
						R.color.color_Mua));
				tv_MarketIndex.setTextColor(getResources().getColor(
						R.color.color_Mua));
			}
		}
	}

	public void switchChangeAndPercentChange(int state) {
		if (state == STATE_CHANGE) {
			tv_MarketChange.setVisibility(VISIBLE);
			tv_MarketChangeP.setVisibility(GONE);
		} else {
			tv_MarketChange.setVisibility(GONE);
			tv_MarketChangeP.setVisibility(VISIBLE);
		}
	}

	public void switchVolumeAndValue(int state) {
		if (state == STATE_VALUE) {
			tv_MarketValue.setVisibility(VISIBLE);
			tv_MarketVolume.setVisibility(GONE);
		} else {
			tv_MarketValue.setVisibility(GONE);
			tv_MarketVolume.setVisibility(VISIBLE);
		}
	}

}
