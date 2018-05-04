package com.msbuat.mobiletrading.design;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.msbuat.mobiletrading.R;

public class MyEditText extends EditText {

	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTextSize(context.getResources()
				.getDimension(R.dimen.m_edittext_size));

	}

}
