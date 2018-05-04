package com.msbuat.mobiletrading.design;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.msbuat.mobiletrading.R;

public class RangeSeekBarLayout extends LinearLayout {
	int defaultMaxValue;
	int defaultMinValue;
	RangeSeekBar<Integer> seekBar;

	public RangeSeekBarLayout(Context paramContext) {
		super(paramContext);
	}

	public RangeSeekBarLayout(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		initialise(paramAttributeSet);
		this.seekBar = new RangeSeekBar(Integer.valueOf(this.defaultMinValue),
				Integer.valueOf(this.defaultMaxValue), paramContext);
		addView(this.seekBar);
	}

	private void initialise(AttributeSet paramAttributeSet) {
		TypedArray localTypedArray = getContext().obtainStyledAttributes(
				paramAttributeSet, R.styleable.RangeSeekBarLayout);
		this.defaultMinValue = localTypedArray.getInt(0,
				R.styleable.RangeSeekBarLayout_minValue);
		this.defaultMaxValue = localTypedArray.getInt(1,
				R.styleable.RangeSeekBarLayout_maxValue);
	}

	public int getDefaultMaxValue() {
		return this.defaultMaxValue;
	}

	public int getDefaultMinValue() {
		return this.defaultMinValue;
	}

	public int getMaxValue() {
		return ((Integer) this.seekBar.getSelectedMaxValue()).intValue();
	}

	public int getMinValue() {
		return ((Integer) this.seekBar.getSelectedMinValue()).intValue();
	}

	public void setMaxValue(int paramInt) {
		this.seekBar.setSelectedMaxValue(Integer.valueOf(paramInt));
	}

	public void setMinValue(int paramInt) {
		this.seekBar.setSelectedMinValue(Integer.valueOf(paramInt));
	}

	public void setOnRangeSeekBarChangeListener(
			RangeSeekBar.OnRangeSeekBarChangeListener<Integer> paramOnRangeSeekBarChangeListener) {
		this.seekBar
				.setOnRangeSeekBarChangeListener(paramOnRangeSeekBarChangeListener);
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.mobiletrading.design.RangeSeekBarLayout JD-Core Version: 0.6.0
 */