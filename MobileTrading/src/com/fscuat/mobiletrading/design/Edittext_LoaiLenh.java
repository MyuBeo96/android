package com.fscuat.mobiletrading.design;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.R;

public class Edittext_LoaiLenh extends LinearLayout {

	Context context;
	ImageButton btn_Giam, btn_Tang;
	TextView textview;
	List<String> listExchange;
	int currentItem = 0;

	public Edittext_LoaiLenh(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.edittext_loailenh, this);

		listExchange = new ArrayList<String>();
		btn_Giam = (ImageButton) findViewById(R.id.btn_EdittextLoaiLenh_Giam);
		btn_Tang = (ImageButton) findViewById(R.id.btn_EdittextLoaiLenh_Tang);
		textview = (EditText) findViewById(R.id.edt_EdittextLoaiLenh);
		textview.setKeyListener(null);
		// textview.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// v.onTouchEvent(event);
		// InputMethodManager imm = (InputMethodManager) v.getContext()
		// .getSystemService(Context.INPUT_METHOD_SERVICE);
		// if (imm != null) {
		// imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		// }
		// return true;
		// }
		// });
		btn_Giam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentItem > 0) {
					currentItem--;
					textview.setText(listExchange.get(currentItem));
					if (onClickChangeButton != null) {
						onClickChangeButton.onClick(v);
					}
				}
			}
		});
		btn_Tang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentItem < listExchange.size() - 1) {
					currentItem++;
					textview.setText(listExchange.get(currentItem));
					if (onClickChangeButton != null) {
						onClickChangeButton.onClick(v);
					}
				}
			}
		});

	}

	public CharSequence getText() {
		return textview.getText();
	}

	public void setText(String str) {
		if (listExchange.contains(str)) {
			textview.setText(str);
			currentItem = listExchange.indexOf(str);
		}
	}

	public void setPriceType(String str) {
		if (listExchange.contains(str)) {
			textview.setText(str);
			currentItem = listExchange.indexOf(str);
			if (onClickChangeButton != null) {
				onClickChangeButton.onClick(null);
			}
		}
	}

	/**
	 * hàm này dùng để set cứng loại lệnh, dùng cho những trường hợp ô loại lệnh
	 * chỉ nhận 1 loại duy nhất không thay đổi đc
	 */
	public void setTextWhenDisable(String str) {
		textview.setText(str);
	}

	public void setListItem(List<String> listExchange) {
		this.listExchange = listExchange;
		currentItem = 0;
		textview.setText(listExchange.get(currentItem));
	}

	public void setKeyListener(KeyListener input) {
		// TODO Auto-generated method stub
		textview.setKeyListener(input);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		textview.setEnabled(enabled);
		btn_Giam.setEnabled(enabled);
		btn_Tang.setEnabled(enabled);
	}

	OnClickListener onClickChangeButton;

	public void setOnClickChangeButton(OnClickListener onClickChangeButton) {
		this.onClickChangeButton = onClickChangeButton;
	}

	public TextView getEditContext() {
		return textview;
	}
}
