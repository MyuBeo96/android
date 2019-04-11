package com.tcscuat.mobiletrading.design;

import java.text.ParseException;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.EditText;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.tcscuat.mobiletrading.design.InputDate.OnChangeTextDateListener;

public class DialogDate {
	EditText edt;
	private DatePickerDialog datePic;
	Calendar calendar;
	OnChangeTextDateListener onChangeTextDateListener;
	OnTouchListener edtTouchListener;
	Context context;

	public DialogDate(Context context) {
		this.context = context;
		calendar = Calendar.getInstance();
		datePic = new DatePickerDialog(context, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
			}
		}, StaticObjectManager.calendar.get(1),
				StaticObjectManager.calendar.get(2),
				StaticObjectManager.calendar.get(5));

		datePic.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				DatePicker view = datePic.getDatePicker();
				String month;
				if (1 + view.getMonth() < 10) {
					month = "0" + (1 + view.getMonth());
				} else {
					month = 1 + view.getMonth() + StringConst.EMPTY;
				}
				String str2;
				if (view.getDayOfMonth() < 10) {
					str2 = "0" + view.getDayOfMonth();
				} else {
					str2 = view.getDayOfMonth() + StringConst.EMPTY;
				}
				String str3 = str2 + "/" + month + "/" + view.getYear();
				if (edt != null) {
					edt.setText(str3);
					if (onChangeTextDateListener != null) {
						onChangeTextDateListener.onChange(null);
					}
					edt.clearFocus();
				}
			}
		});

		initListener();
	}

	private void initListener() {
		edtTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				showDialogDate();
				return false;
			}
		};
	}

	public void resetDate() {
		if (StaticObjectManager.loginInfo != null) {
			setDate(StaticObjectManager.loginInfo.TxDateString);
		}
	}

	public void showDialogDate() {
		datePic.show();
	}

	public void setOnChangeTextDateListener(
			OnChangeTextDateListener onChangeTextDateListener) {
		this.onChangeTextDateListener = onChangeTextDateListener;
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
		if (edt != null) {
			edt.setText(date);
		}
		datePic.updateDate(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
	}

	public EditText getEdt() {
		return edt;
	}

	public void setEdt(EditText edt) {
		this.edt = edt;
		if (this.edt != null) {
			this.edt.setText(StaticObjectManager.simpleDateFormat
					.format(StaticObjectManager.calendar.getTime()));
			this.edt.setOnTouchListener(edtTouchListener);
		}
	}

	public void removeEdt() {

	}

	public void setTitle(String title) {
		if (datePic != null) {
			datePic.setTitle(title);
		}
	}

}
