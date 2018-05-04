package com.fss.mobiletrading.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import com.fss.mobiletrading.object.SolenhItem;
import com.msbuat.mobiletrading.MyActionBar.Action;

public class CustomHorizontalScrollView extends HorizontalScrollView {

	public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					CustomHorizontalScrollView.this.scrollTo(0, 0);
				}
			}
		});
	}

}
