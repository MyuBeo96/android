package com.fscuat.mobiletrading.design;

import com.fscuat.mobiletrading.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class CusRelativeLayout extends ViewGroup {

	public CusRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CusRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CusRelativeLayout(Context context) {
		super(context);
	}

	private void readAttributeSet(AttributeSet attrs) {
		// TypedArray localTypedArray =
		// getContext().obtainStyledAttributes(attrs,
		// R.styleable.CusRelativeLayout);
		// TODO doc cac thuoc tinh paddingPercentage, set padding cho view
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	}

}
