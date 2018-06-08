package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.object.AcctnoItem;

public class AcctnoItemView extends LinearLayout {
	TextView tv_Acctno;
	View select;
	TextView tv_Fullname;

	public AcctnoItemView(Context context) {
		super(context);
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.chooseafacctno_item, this);
		tv_Acctno = ((TextView) findViewById(R.id.text_chontieukhoan_item_Acctno));
		tv_Fullname = ((TextView) findViewById(R.id.text_chontieukhoan_item_Fullname));
		select = findViewById(R.id.view_chontieukhoan_item_select);
	}

	public void setCheck(boolean isActive) {
		if (isActive) {
			select.setVisibility(VISIBLE);
		} else {
			select.setVisibility(GONE);
		}
	}

	public void setView(AcctnoItem item) {
		tv_Acctno.setText(item.ACCTNO + "." + item.TYPENAME);
		tv_Fullname.setText(item.FULLNAME);
	}
}
