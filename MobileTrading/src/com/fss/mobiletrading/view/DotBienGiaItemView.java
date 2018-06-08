package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.object.DotBienGiaItem;

public class DotBienGiaItemView extends LinearLayout {
	TextView tv_ClosePrice;
	TextView tv_KLGD;
	TextView tv_PE;
	TextView tv_Symbol;

	public DotBienGiaItemView(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.boloc_dotbiengia_item, this);
		this.tv_Symbol = ((TextView) findViewById(R.id.text_dotbiengia_item_Symbol));
		this.tv_KLGD = ((TextView) findViewById(R.id.text_dotbiengia_item_KLGD));
		this.tv_ClosePrice = ((TextView) findViewById(R.id.text_dotbiengia_item_ClosePrice));
		this.tv_PE = ((TextView) findViewById(R.id.text_dotbiengia_item_PE));
	}

	public void setView(DotBienGiaItem paramDotBienGiaItem) {
		this.tv_Symbol.setText(paramDotBienGiaItem.SYMBOL);
		this.tv_KLGD.setText(paramDotBienGiaItem.KLGD);
		this.tv_ClosePrice.setText(paramDotBienGiaItem.REALPRICE);
		this.tv_PE.setText(paramDotBienGiaItem.PE);
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.view.DotBienGiaItemView JD-Core Version: 0.6.0
 */