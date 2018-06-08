package com.fscuat.mobiletrading.design;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

public class VerticalListview extends ListView {

	public boolean isScrollingUp = false;

	public VerticalListview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setOnTouch() {
		this.setOnTouchListener(new OnTouchListener() {

			int countup;
			int countdown;
			final int SENSITIVITY = 1;
			final int SENSITIVITY_DOWN = 10;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					countup = 0;
					countdown = 0;
					break;
				case MotionEvent.ACTION_MOVE:
					if (getChildAt(0) != null && getFirstVisiblePosition() == 0
							&& getChildAt(0).getTop() == 0) {
						countup++;
						if (countup > SENSITIVITY) {
							v.getParent().requestDisallowInterceptTouchEvent(
									false);
							return false;
						}
					}
					View view = getChildAt(getLastVisiblePosition()
							- getFirstVisiblePosition());
					if (getLastVisiblePosition() == getCount() - 1
							&& view != null
							&& view.getBottom() == getMeasuredHeight()) {
						countdown++;
						if (countdown > SENSITIVITY) {
							v.getParent().requestDisallowInterceptTouchEvent(
									false);
							return false;
						}
					}

					break;
				case MotionEvent.ACTION_CANCEL:
					break;
				default:
					break;
				}
				v.getParent().requestDisallowInterceptTouchEvent(true);
				v.onTouchEvent(event);
				return true;
			}
		});
	}
}
