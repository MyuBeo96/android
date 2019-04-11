package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.object.BoLocCoBanItem;

public class BoLocCoBanItemView extends LinearLayout {
	TextView tv_ClosePrice;
	TextView tv_EPS;
	TextView tv_PE;
	TextView tv_Symbol;

	public BoLocCoBanItemView(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.boloc_coban_item, this);
		this.tv_Symbol = ((TextView) findViewById(R.id.text_boloc_coban_item_Symbol));
		this.tv_ClosePrice = ((TextView) findViewById(R.id.text_boloc_coban_item_ClosePrice));
		this.tv_PE = ((TextView) findViewById(R.id.text_boloc_coban_item_PE));
		this.tv_EPS = ((TextView) findViewById(R.id.text_boloc_coban_item_EPS));
	}

	public void setView(BoLocCoBanItem paramBoLocCoBanItem) {
		this.tv_Symbol.setText(paramBoLocCoBanItem.SYMBOL);
		this.tv_ClosePrice.setText(paramBoLocCoBanItem.REALPRICE);
		this.tv_PE.setText(paramBoLocCoBanItem.PE);
		this.tv_EPS.setText(paramBoLocCoBanItem.EST_EPS);
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.view.BoLocCoBanItemView JD-Core Version: 0.6.0
 */