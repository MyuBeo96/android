package com.fscuat.mobiletrading.design;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.fscuat.mobiletrading.MainActivity;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.AppData;
import com.fscuat.mobiletrading.R;

import static java.util.Locale.CHINESE;

public class InputDate extends RelativeLayout {

	static final int HORIZONTAL = 0;
	static final int VERTICAL = 1;

	LabelContentLayout text;
	ImageButton btn;
	View line;
	private DatePickerDialog datePic;
	Calendar calendar;
	OnChangeDateListener onChangeDateListener;
	OnChangeTextDateListener onChangeTextDateListener;

	public InputDate(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.InputDate);
		int orien = typedArray.getInt(0, VERTICAL);
		if (orien == VERTICAL) {
			inflater.inflate(R.layout.layoutinputdate, this);
			text = (LabelContentLayout) findViewById(R.id.text_inputdate);
			btn = (ImageButton) findViewById(R.id.btn_inputdate);
			line = findViewById(R.id.line_inputdate);
		} else {
			inflater.inflate(R.layout.layoutinputdatehori, this);
			text = (LabelContentLayout) findViewById(R.id.text_inputdatehori);
			btn = (ImageButton) findViewById(R.id.btn_inputdatehori);
			line = findViewById(R.id.line_inputdatehori);
		}
		Locale locale = getResources().getConfiguration().locale;
		Locale.setDefault(locale);

		calendar = Calendar.getInstance();
		text.getContent().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialogDate();
			}
		});
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialogDate();
			}
		});
		datePic = Common.createDialogDate(context, text.getContent());
		datePic.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel),
				datePic);
		datePic.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.ok),
				datePic);
		text.getLabel().setText(typedArray.getString(1));
		line.setBackgroundColor(typedArray.getColor(3,
				getResources().getColor(R.color.lc_separation_color)));
		text.getContent().addTextChangedListener(new TextWatcher() {

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
				if (onChangeTextDateListener != null) {
					onChangeTextDateListener.onChange(null);
				}
			}
		});

	}

	public void showDialogDate() {
		datePic.show();
	}

	public void resetDate() {
		if (StaticObjectManager.loginInfo != null) {
			setDate(StaticObjectManager.loginInfo.TxDateString);
		}

	}

	public void setOnChangeDateListener(
			OnChangeDateListener onChangeDateListener) {
		this.onChangeDateListener = onChangeDateListener;
	}

	public void setDate(String date) {
		if (date == null) {
			return;
		}
		try {
			calendar.setTime(StaticObjectManager.simpleDateFormat.parse(date));
		} catch (ParseException e) {
			return;
		}
		text.getContent().setText(date);
		datePic.updateDate(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		if (onChangeDateListener != null) {
			onChangeDateListener.onChange(date);
		}
	}

	@Override
	public String toString() {
		return text.getContent().getText().toString();
	}

	public void setLabel(String str) {
		text.getLabel().setText(str);
		datePic.setTitle(str);
	}

	public interface OnChangeDateListener {
		public void onChange(String date);
	}

	public interface OnChangeTextDateListener {
		public void onChange(String date);
	}

	public void setOnChangeTextDateListener(
			OnChangeTextDateListener onChangeTextDateListener) {
		this.onChangeTextDateListener = onChangeTextDateListener;
	}
}
