package com.fscuat.mobiletrading.design;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fss.mobiletrading.consts.StringConst;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;

public class EdittextSearchUI extends LinearLayout {

	EditText edt;
	Button btn;
	boolean isVisible;
	Context context;

	public EdittextSearchUI(Context pv_context, AttributeSet attrs) {
		super(pv_context, attrs);
		context = pv_context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.searchtextui, this);
		edt = (EditText) findViewById(R.id.edt_searchtextui_search);
		btn = (Button) findViewById(R.id.btn_searchtextui_cancelsearch);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edt.setText(StringConst.EMPTY);
				hide();
			}
		});
		setOnEdittextFocusChangeListener();
		
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

	OnHideListener onHideListener;

	public void setOnHideListener(OnHideListener onHideListener) {
		this.onHideListener = onHideListener;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public interface OnHideListener {
		public void onhide();
	}

}
