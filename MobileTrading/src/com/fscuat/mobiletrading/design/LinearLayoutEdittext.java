package com.fscuat.mobiletrading.design;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.report.FieldInReport;
import com.fss.mobiletrading.function.report.ReportSearchDialog;
import com.fss.mobiletrading.object.ItemString2;
import com.fss.mobiletrading.object.RptFieldItem;

public class LinearLayoutEdittext extends LinearLayout {
	List<FieldInReport> listFieldOfReport = new ArrayList<FieldInReport>();
	public String rptParams = StringConst.EMPTY;
	LayoutParams paramsEdittext = new LayoutParams(0,
			LayoutParams.WRAP_CONTENT, 7);
	LayoutParams paramsTextview = new LayoutParams(0,
			LayoutParams.WRAP_CONTENT, 3);
	ReportSearchDialog dialog;

	public LinearLayoutEdittext(Context context,
			List<RptFieldItem> listRptField, ReportSearchDialog dialog) {

		super(context);
		this.dialog = dialog;
		setOrientation(LinearLayout.VERTICAL);
		setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		List<RptFieldItem> list_RptField = listRptField;

		StringBuilder builder = new StringBuilder();
		int rptFieldSize = list_RptField.size();
		for (int i = 0; i < rptFieldSize; i++) {

			RptFieldItem rptFieldItem = list_RptField.get(i);
			builder.append(rptFieldItem.FName + "|");
			LinearLayout lc_LayoutHozi = new LinearLayout(context);
			lc_LayoutHozi.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			lc_LayoutHozi.setOrientation(HORIZONTAL);
			lc_LayoutHozi.setGravity(Gravity.CENTER_VERTICAL);
			lc_LayoutHozi.setPadding(0, 3, 0, 2);
			initRowInFormReport(rptFieldItem, lc_LayoutHozi, context);
		}
		if (list_RptField.size() >= 0) {
			builder.deleteCharAt(builder.length() - 1);
			rptParams = builder.toString();
			// this.setVisibility(LinearLayout.GONE);
		}
	}

	private void initRowInFormReport(RptFieldItem rptFieldItem,
			LinearLayout lc_LayoutHozi, Context context) {
		if (rptFieldItem.FSet != null) {
			// initialise Spinner
			SpinnerInReport spinner = new SpinnerInReport(context);
			spinner.setChoises(rptFieldItem.FSet);
			spinner.setLabel(rptFieldItem.FTitle);
			spinner.setFName(rptFieldItem.FName);
			if (rptFieldItem.isVisible) {
				spinner.setVisibility(View.VISIBLE);
			} else {
				spinner.setVisibility(View.GONE);
			}
			spinner.setEnabled(!rptFieldItem.isDisable);
			listFieldOfReport.add(spinner);
			lc_LayoutHozi.addView(spinner);
			this.addView(lc_LayoutHozi);
			return;
		}
		if (rptFieldItem.FFilter != null && rptFieldItem.FFilter.length() > 0) {
			// initialise filter
			TextViewInReport textview = new TextViewInReport(context);
			textview.setRptFieldItem(rptFieldItem);
			textview.setFName(rptFieldItem.FName);

			int listFieldSize = listFieldOfReport.size();
			for (int i = 0; i < listFieldSize; i++) {
				FieldInReport field = listFieldOfReport.get(i);
				if (rptFieldItem.FFilter.equals(field.getFName())) {
					textview.setObservable(field);
					if (field instanceof TextViewInReport) {
						((TextViewInReport) field).setObserver(textview);
					}
					break;
				}
			}
			if (rptFieldItem.isVisible) {
				textview.setVisibility(View.VISIBLE);
			} else {
				textview.setVisibility(View.GONE);
			}
			if (!rptFieldItem.isDisable) {
				textview.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						TextViewWithRptField view = (TextViewWithRptField) v;
						if (dialog != null) {
							dialog.setCode(view.getFSearch());
							dialog.setEnableImport(false);
							dialog.setTvResult(view);
							dialog.setInputText(view.getObservableText());
							dialog.show();

						}
					}
				});
			}
			listFieldOfReport.add(textview);
			lc_LayoutHozi.addView(textview);
			this.addView(lc_LayoutHozi);
			return;
		}
		if (rptFieldItem.FSearch != null && rptFieldItem.FSearch.length() > 0) {
			// initialise Search
			TextViewInReport textview = new TextViewInReport(context);
			textview.setFName(rptFieldItem.FName);
			textview.setRptFieldItem(rptFieldItem);
			if (rptFieldItem.isVisible) {
				textview.setVisibility(View.VISIBLE);
			} else {
				textview.setVisibility(View.GONE);
			}
			if (!rptFieldItem.isDisable) {
				textview.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						TextViewWithRptField view = (TextViewWithRptField) v;
						if (dialog != null) {
							dialog.setCode(view.getFSearch());
							dialog.setInputText(view.getText().toString());
							dialog.setEnableImport(true);
							dialog.setTvResult(view);
							dialog.show();
						}
					}
				});
			}
			listFieldOfReport.add(textview);
			lc_LayoutHozi.addView(textview);
			this.addView(lc_LayoutHozi);
			return;
		}
		// initialise Edittext
		EditTextInReport editText = new EditTextInReport(context,
				rptFieldItem.FType);
		editText.setText(rptFieldItem.FValue);
		editText.setLabel(rptFieldItem.FTitle);
		editText.setFName(rptFieldItem.FName);
		if (rptFieldItem.isVisible) {
			editText.setVisibility(View.VISIBLE);
		} else {
			editText.setVisibility(View.GONE);
		}
		editText.setEnabled(!rptFieldItem.isDisable);
		listFieldOfReport.add(editText);
		lc_LayoutHozi.addView(editText);
		this.addView(lc_LayoutHozi);

	}

	public String getValue() {
		StringBuilder builder = new StringBuilder();
		int listFieldSize = listFieldOfReport.size();
		for (int i = 0; i < listFieldSize; i++) {
			FieldInReport field = listFieldOfReport.get(i);
			builder.append(field.getValue() + "|");
		}
		if (listFieldOfReport.size() > 0) {
			builder.deleteCharAt(-1 + builder.length());
			return builder.toString();
		}
		return null;

	}

	public void reset() {
		int listFieldOfReportSize = listFieldOfReport.size();
		for (int i = 0; i < listFieldOfReportSize; i++) {
			FieldInReport field = listFieldOfReport.get(i);
			field.clear();
		}
	}
}
