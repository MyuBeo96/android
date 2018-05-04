package com.msbuat.mobiletrading.design;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fss.designcontrol.SymbolEdittext;
import com.fss.mobiletrading.consts.StringConst;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;

public class SearchTextUI extends LinearLayout {

	SymbolEdittext edt;
	Button btn;
	boolean isVisible;
	Context context;
	ImageView clearField;

	public SearchTextUI(Context pv_context, AttributeSet attrs) {
		super(pv_context, attrs);
		context = pv_context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.searchtextui, this);
		edt = (SymbolEdittext) findViewById(R.id.edt_searchtextui_search);
		edt.setSymbolEdittextType(SymbolEdittext.SymbolEdittextType.Single);
		clearField = (ImageView) findViewById(R.id.img_searchtextui_clearfield);
		btn = (Button) findViewById(R.id.btn_searchtextui_cancelsearch);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edt.setText(StringConst.EMPTY);
				hide();
			}
		});
		setOnEdittextFocusChangeListener();
		clearField.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edt.getText().clear();
			}
		});
		if (this.getVisibility() == VISIBLE) {
			isVisible = true;
		} else {
			isVisible = false;
		}
	}

	public void setOnEdittextFocusChangeListener() {
		edt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (context instanceof MainActivity) {
					MainActivity mainActivity = (MainActivity) context;
					if (hasFocus) {
						mainActivity.showKeyboard(v);
					} else {
						mainActivity.hideKeyboard(v);
					}
				}
			}
		});
	}

	public void hide() {
		if (onHideListener != null) {
			onHideListener.onhide();
		}
		this.setVisibility(GONE);
		isVisible = false;
		edt.getText().clear();
		edt.clearFocus();
	}

	public void show() {
		this.setVisibility(VISIBLE);
		edt.requestFocus();
		isVisible = true;
	}

	public EditText getEditText() {
		return edt;
	}

	public void setVisibleButton(boolean isVisible) {
		if (isVisible) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.GONE);
		}
	}

	public void setVisibleClearField(boolean isVisible) {
		if (isVisible) {
			clearField.setVisibility(View.VISIBLE);
		} else {
			clearField.setVisibility(View.GONE);
		}
	}

	OnHideListener onHideListener;

	public void setOnHideListener(OnHideListener onHideListener) {
		this.onHideListener = onHideListener;
	}

	public void setClearButton() {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edt.getText().clear();
			}
		});
	}

	public void setTextColor(int color) {
		edt.setTextColor(color);
	}

	public boolean isVisible() {
		return isVisible;
	}

	public interface OnHideListener {
		public void onhide();
	}

}
