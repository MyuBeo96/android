package com.fscuat.mobiletrading.design;

import com.fss.mobiletrading.common.MTradingColor;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewHightLight extends TextView {
	public TextViewHightLight(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		CharSequence oldText = getText();
		if (oldText == null || oldText.length() == 0 || text == null
				|| text.length() == 0) {
			setBackgroundColor(Color.TRANSPARENT);
			super.setText(text, type);
			return;
		}
		if (oldText.equals(text)) {
			setBackgroundColor(Color.TRANSPARENT);
		} else {
			setBackgroundColor(MTradingColor.highlight_color);
		}
		super.setText(text, type);
	}
}
