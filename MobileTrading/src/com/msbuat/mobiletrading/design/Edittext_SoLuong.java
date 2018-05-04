package com.msbuat.mobiletrading.design;

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
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.StockItem;
import com.msbuat.mobiletrading.R;

public class Edittext_SoLuong extends LinearLayout {

	public String tradeplace;
	Context context;
	ImageButton btn_Giam, btn_Tang;
	EditText edittext;

	public Edittext_SoLuong(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.edittext_soluong, this);
		btn_Giam = (ImageButton) findViewById(R.id.btn_EdittextSoLuong_Giam);
		btn_Tang = (ImageButton) findViewById(R.id.btn_EdittextSoLuong_Tang);
		edittext = (EditText) findViewById(R.id.edt_EdittextSoLuong);
		edittext.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.onTouchEvent(event);
				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return true;
			}
		});
		edittext.addTextChangedListener(new TextWatcher() {

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
				int value = 0;
				try {
					value = Integer.parseInt(s.toString().replace(",",
							StringConst.EMPTY));
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
				if (value < 0) {
					edittext.removeTextChangedListener(this);
					s.clear();
					s.append("0");
					edittext.addTextChangedListener(this);
				} else {
					// if
					// (!Rule.checkQuantityMaxRule(Edittext_SoLuong.tradeplace,
					// value)) {
					// edittext.removeTextChangedListener(this);
					// s.clear();
					// s.append("19,900");
					// edittext.addTextChangedListener(this);
					// }
				}
				edittext.setSelection(edittext.length());
			}
		});
		Common.registrySeparatorNumber(edittext);
		btn_Giam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int soluong = 0;
				try {
					soluong = Integer.parseInt(edittext.getText().toString()
							.replace(",", StringConst.EMPTY));
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (tradeplace != null) {
					switch (tradeplace) {
					case StockItem.HOSE:
						soluong -= 10;
						if (soluong >= 0) {
							edittext.setText(String.valueOf(soluong));
						}
						break;
					case StockItem.HNX:
						soluong -= 100;
						if (soluong >= 0) {
							edittext.setText(String.valueOf(soluong));
						}
						break;
					case StockItem.UPCOM:
						soluong -= 100;
						if (soluong >= 0) {
							edittext.setText(String.valueOf(soluong));
						}
						break;

					default:
						break;
					}
				}

			}
		});

		btn_Tang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int soluong = 0;
				try {
					soluong = Integer.parseInt(edittext.getText().toString()
							.replace(",", StringConst.EMPTY));
				} catch (Exception e) {
				}
				if (tradeplace != null) {

					switch (tradeplace) {
					case StockItem.HOSE:
						soluong += 10;
						edittext.setText(String.valueOf(soluong));
						break;
					case StockItem.HNX:
						soluong += 100;
						edittext.setText(String.valueOf(soluong));
						break;
					case StockItem.UPCOM:
						soluong += 100;
						edittext.setText(String.valueOf(soluong));
						break;

					default:
						break;
					}
				}

			}
		});
	}

	public void setText(String str) {
		edittext.setText(str);
	}

	public Editable getText() {
		return edittext.getText();
	}

	public void addTextChangedListener(TextWatcher textWatcher) {
		// TODO Auto-generated method stub
		edittext.addTextChangedListener(textWatcher);
	}

	public void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
		edittext.setOnFocusChangeListener(listener);
	}

	public EditText toEditText() {
		// TODO Auto-generated method stub
		return edittext;
	}

	public void setKeyListener(KeyListener input) {
		// TODO Auto-generated method stub
		edittext.setKeyListener(input);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		edittext.setEnabled(enabled);
		btn_Giam.setEnabled(enabled);
		btn_Tang.setEnabled(enabled);
	}

}
