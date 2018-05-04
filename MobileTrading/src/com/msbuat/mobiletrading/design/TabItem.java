package com.msbuat.mobiletrading.design;

import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;

public class TabItem extends RelativeLayout {

	TextView text;
	View line;

	public TabItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.TabItem);
		text = (TextView) findViewById(R.id.text_tabitem);
		if (typedArray.getString(0) != null) {
			text.setText(typedArray.getString(0));
		}
		line = findViewById(R.id.line_tabitem);
		setSelected(false);
	}

	public void initView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.tabitem, this);
	}

	public void setText(String str) {
		this.text.setText(str);
		;
	}

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		text.setActivated(selected);
		if (selected) {
			line.setVisibility(VISIBLE);
		} else {
			line.setVisibility(INVISIBLE);
		}
	}
}
//
// <TextView
// android:id="@+id/text_tabselector"
// android:layout_width="match_parent"
// android:layout_height="match_parent"
// android:layout_above="@+id/line_tabselector"
// android:background="@color/header_background_color"
// android:gravity="center"
// android:textAllCaps="true" />
