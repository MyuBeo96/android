package com.tcscuat.mobiletrading.design;

import com.tcscuat.mobiletrading.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class LoadingImage extends ImageView {
	private AnimationDrawable loadingViewAnim = null;

	public LoadingImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundResource(R.anim.animation_loading);
		loadingViewAnim = (AnimationDrawable) getBackground();
	}

	public void start() {
		setVisibility(View.VISIBLE);
		loadingViewAnim.start();
	}

	public void stop() {
		setVisibility(View.GONE);
		loadingViewAnim.stop();
	}

}
