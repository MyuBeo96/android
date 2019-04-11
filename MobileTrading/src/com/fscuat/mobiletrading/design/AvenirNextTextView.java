package com.tcscuat.mobiletrading.design;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class AvenirNextTextView extends TextView {

	public AvenirNextTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/AvenirNext_Regular.ttf");
		setTypeface(tf);
	}

}
