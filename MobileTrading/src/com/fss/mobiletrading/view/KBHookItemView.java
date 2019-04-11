package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcscuat.mobiletrading.R;

public class KBHookItemView extends LinearLayout {

	TextView textView;

	public KBHookItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.kbhook_item, this);
		textView = (TextView) findViewById(R.id.kbhook_item_symbol);
	}

	public void setView(String text) {
		textView.setText(text);
	}

	public String getText() {
		return textView.getText().toString();
	}
}
