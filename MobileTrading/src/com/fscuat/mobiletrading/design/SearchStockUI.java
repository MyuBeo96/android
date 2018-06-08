package com.fscuat.mobiletrading.design;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fscuat.mobiletrading.MainActivity;

public class SearchStockUI extends SearchTextUI {

	public SearchStockUI(Context pv_context, AttributeSet attrs) {
		super(pv_context, attrs);
		edt.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
				| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
	}

	OnFocusChangeListenerCustom onFocusChangeListenerCustom;

	public void setOnFocusChangeListenerCustom(
			OnFocusChangeListenerCustom onFocusChangeListenerCustom) {
		this.onFocusChangeListenerCustom = onFocusChangeListenerCustom;
	}

	@Override
	public void setOnEdittextFocusChangeListener() {
		edt.setInputType(InputType.TYPE_NULL);
		edt.setRawInputType(InputType.TYPE_CLASS_TEXT);
		edt.setTextIsSelectable(true);
		edt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (context instanceof MainActivity) {
					MainActivity mainActivity = (MainActivity) context;
					if (hasFocus) {
						mainActivity.showKBoardHook(true, v, StaticObjectManager.getListAllStock());
						mainActivity.showKBoardSymbol(true, v);
					} else {
						mainActivity.showKBoardHook(false, null, null);
						mainActivity.showKBoardSymbol(false, null);
					}
				} else {
					if (onFocusChangeListenerCustom != null) {
						onFocusChangeListenerCustom.onFocusChange(v, hasFocus);
					}
				}
			}
		});
	}

	public interface OnFocusChangeListenerCustom {
		public void onFocusChange(View v, boolean hasFocus);
	}

}
