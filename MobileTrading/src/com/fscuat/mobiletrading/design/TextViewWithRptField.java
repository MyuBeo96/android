package com.tcscuat.mobiletrading.design;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.report.FieldInReport;
import com.fss.mobiletrading.object.RptFieldItem;

public class TextViewWithRptField extends TextView {
	public TextViewWithRptField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	FieldInReport observable;

	public String getObservableText() {
		if (observable != null) {
			return observable.getValue();
		}
		return StringConst.EMPTY;
	}

	public void setObservable(FieldInReport observable) {
		this.observable = observable;
	}

	private String FSearch;

	public String getFSearch() {
		return FSearch;
	}

	public void setFSearch(String fSearch) {
		FSearch = fSearch;
	}
}
