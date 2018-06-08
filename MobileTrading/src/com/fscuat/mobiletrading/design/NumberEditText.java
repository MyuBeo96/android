package com.fscuat.mobiletrading.design;

import com.fss.mobiletrading.common.Rule;
import com.fscuat.mobiletrading.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;

public class NumberEditText extends EditText {
	final int NUMBER_INTERGER = 0;
	final int NUMBER_DECIMAL = 1;

	public NumberEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithAttributeSet(attrs);
		checkRule();
	}

	private void initWithAttributeSet(AttributeSet attrs) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
				R.styleable.NumberEditText);
		this.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray
				.getDimensionPixelSize(
						R.styleable.NumberEditText_contentsize_dimen,
						getResources().getDimensionPixelSize(
								R.dimen.m_content_size)));
		setHintTextColor(getResources().getColor(R.color.text_hint_color));
	}

	private void checkRule() {
		this.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				// check price format
				if (!Rule.checkPriceFormat(s.toString())) {
					NumberEditText.this.removeTextChangedListener(this);
					s.delete(s.length() - 1, s.length());
					NumberEditText.this.addTextChangedListener(this);
				}
			}
		});
	}

	public NumberEditText(Context context) {
		super(context);
	}

	@Override
	public void setActivated(boolean activated) {
		super.setActivated(activated);
	}
}
