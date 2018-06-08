package com.fss.mobiletrading.view;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.function.coporateactions.CoporateActionItem;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CoporateActionView extends LinearLayout {

	TextView tv_symbol;
	TextView tv_closedate;
	TextView tv_eventtype;
	TextView tv_status;

	TextView tv_soluongchungkhoansohuu;
	TextView tv_soluongchungkhoanchove;
	TextView tv_ngaythuchien;
	TextView tv_sotienchove;
	TextView tv_tyle;

	public CoporateActionView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.coporateaction_item, this);
		tv_symbol = (TextView) findViewById(R.id.text_coporateaction_item_symbol);
		tv_closedate = (TextView) findViewById(R.id.text_coporateaction_item_closedate);
		tv_eventtype = (TextView) findViewById(R.id.text_coporateaction_item_eventtype);
		tv_status = (TextView) findViewById(R.id.text_coporateaction_item_status);
		tv_soluongchungkhoansohuu = (TextView) findViewById(R.id.text_soluongchungkhoansohuu);
		tv_soluongchungkhoanchove = (TextView) findViewById(R.id.text_soluongchungkhoanchove);
		tv_tyle = (TextView) findViewById(R.id.text_tyle);
		tv_ngaythuchien = (TextView) findViewById(R.id.text_ngaythuchien);
		tv_sotienchove = (TextView) findViewById(R.id.text_sotienchove);
	}

	public void setView(CoporateActionItem obj) {
		tv_closedate.setText(obj.getREPORTDATE());
		tv_status.setText(obj.getSTATUS());
		tv_symbol.setText(obj.getSYMBOL());
		tv_eventtype.setText(obj.getCATYPE());
		tv_soluongchungkhoansohuu.setText(Common.formatAmount(obj.getSLCKSH()));
		tv_soluongchungkhoanchove.setText(Common.formatAmount(obj.getSLCKCV()));
		// truong getTYLE() không nên format vì nó có cả định dạng %, phân số...
		tv_tyle.setText(obj.getTYLE());
		tv_ngaythuchien.setText(obj.getACTIONDATE());
		tv_sotienchove.setText(Common.formatAmount(obj.getSTCV()));

	}
}
