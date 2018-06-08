package com.fscuat.mobiletrading.design;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.report.FieldInReport;
import com.fss.mobiletrading.object.RptFieldItem;
import com.fscuat.mobiletrading.R;

/**
 * @author Admin
 * 
 */
public class TextViewInReport extends LinearLayout implements FieldInReport {

	private TextViewWithRptField content;
	private TextView label;
	private String FName;
	private String FValue;
	private TextViewInReport observer;

	public TextViewInReport(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.textviewinreport, this);
		content = (TextViewWithRptField) findViewById(R.id.edt_content);
		label = (TextView) findViewById(R.id.edt_label);
		content.addTextChangedListener(new TextWatcher() {

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
				if (observer != null) {
					observer.clearText();
				}
			}
		});
	}

	public void setObservable(FieldInReport observable) {
		content.setObservable(observable);
	}

	public void setObserver(TextViewInReport observer) {
		this.observer = observer;
	}

	public void setFName(String fName) {
		FName = fName;
	}

	public void setRptFieldItem(RptFieldItem rptFieldItem) {
		content.setFSearch(rptFieldItem.FSearch);
		label.setText(rptFieldItem.FTitle);
		content.setText(rptFieldItem.FValue);
		this.FValue = rptFieldItem.FValue;
	}

	public void setLabel(String label) {
		this.label.setText(label);
	}

	public void setText(String text) {
		content.setText(text);
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		content.setOnClickListener(l);
	}

	@Override
	public String getFName() {
		return FName;
	}

	@Override
	public String getValue() {
		return content.getText().toString();
	}

	public void clearText() {
		if (content != null) {
			content.setText(StringConst.EMPTY);
		}
	}

	@Override
	public void clear() {
		content.setText(FValue);
	}
}
