package com.msbuat.mobiletrading.design;

import com.msbuat.mobiletrading.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SelectorImageView extends ImageView {

	int activeResourceId;
	int inActiveResourceId;

	public SelectorImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.SelectorImageView);
		activeResourceId = typedArray.getResourceId(
				R.styleable.SelectorImageView_active_drawable, 0);
		inActiveResourceId = typedArray.getResourceId(
				R.styleable.SelectorImageView_inactive_drawable, 0);
		setActivated(typedArray.getBoolean(
				R.styleable.SelectorImageView_inactive_drawable, false));
	}

	@Override
	public void setActivated(boolean activated) {
		super.setActivated(activated);
		if (activated) {
			setImageResource(activeResourceId);
		} else {
			setImageResource(inActiveResourceId);
		}
	}

	public void setImageResourceActivated() {
		setImageResource(activeResourceId);
	}

	public void setImageResourceInActivated() {
		setImageResource(inActiveResourceId);
	}

}
