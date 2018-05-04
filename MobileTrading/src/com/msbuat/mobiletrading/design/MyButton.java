package com.msbuat.mobiletrading.design;

import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.msbuat.mobiletrading.R;

public class MyButton extends LinearLayout {

	TextView text;
	ImageView img_loading;

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.mybutton, this);
		TypedArray TypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.MyButton);
		text = (TextView) findViewById(R.id.text_mybutton);
		img_loading = (ImageView) findViewById(R.id.imgv_mybutton_loading);
		text.setText(TypedArray.getString(0));
	}

	public void setLoading(boolean loading) {
		if (loading) {
			this.setEnabled(false);
			rotate(true);
		} else {
			this.setEnabled(true);
			rotate(false);
		}
	}

	private void rotate(boolean rorate) {
		if (rorate) {
			isLoginLoading();
		} else {
			isLoginLoaded();
		}
	}

	public void setText(String str) {
		text.setText(str);
	}

	private void isLoginLoading() {
		img_loading.setVisibility(ImageView.VISIBLE);
		img_loading.bringToFront();
		img_loading.setAnimation(Common.getRotateAnimation());
	}

	private void isLoginLoaded() {
		img_loading.setVisibility(ImageView.GONE);
		img_loading.setAnimation(null);
	}

}
