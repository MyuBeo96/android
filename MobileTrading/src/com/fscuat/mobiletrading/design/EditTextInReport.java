package com.tcscuat.mobiletrading.design;

import android.app.Service;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.function.report.FieldInReport;
import com.tcscuat.mobiletrading.R;

public class EditTextInReport extends LinearLayout implements FieldInReport {

	// a. Nếu FType là “D” thì hiển thị dạng Date Picker
	// b. Nếu FType là “N” thì dữ liệu nhập là Số
	// c. Nếu Ftype là “C” thì dữ liệu nhập là String

	final static String DATETYPE = "D";
	final static String NUMBERTYPE = "N";
	final static String CHARSEQUENCETYPE = "C";
	EditText content;
	TextView label;
	DialogDate dld_date;
	private String FName;

	public void setFName(String fName) {
		FName = fName;
	}

	public EditTextInReport(Context context, String fType) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.edittextinreport, this);
		content = (EditText) findViewById(R.id.edt_content);
		label = (TextView) findViewById(R.id.edt_label);
		switch (fType) {
		case NUMBERTYPE:
			content.setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			break;
		case CHARSEQUENCETYPE:
			content.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			break;
		case DATETYPE:
			content.setInputType(InputType.TYPE_NULL);
			dld_date = new DialogDate(context);
			dld_date.setEdt(content);
			content.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dld_date.showDialogDate();
				}
			});
			break;
		default:
			content.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			break;
		}
	}

	public void setLabel(String label) {
		content.setHint(label);
		this.label.setText(label);
		if (dld_date != null) {
			dld_date.setTitle(label);
		}
	}

	private String FValue;

	public void setText(String text) {
		this.FValue = text;
		content.setText(text);
		if (dld_date != null) {
			dld_date.setTitle(text);
		}
	}

	@Override
	public String getFName() {
		return FName;
	}

	@Override
	public String getValue() {
		return content.getText().toString();
	}

	@Override
	public void clear() {
		content.setText(FValue);
	}
}
