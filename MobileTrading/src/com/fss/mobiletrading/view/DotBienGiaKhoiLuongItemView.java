package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.object.DotBienGiaKhoiLuongItem;

public class DotBienGiaKhoiLuongItemView extends LinearLayout {
	TextView tv_ClosePrice;
	TextView tv_KLGD;
	TextView tv_KLGDTB;
	TextView tv_Symbol;

	public DotBienGiaKhoiLuongItemView(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.boloc_dotbiengiakhoiluong_item, this);
		this.tv_Symbol = ((TextView) findViewById(R.id.text_dotbiengiakhoiluong_item_symbol));
		this.tv_KLGD = ((TextView) findViewById(R.id.text_dotbiengiakhoiluong_item_KLGD));
		this.tv_KLGDTB = ((TextView) findViewById(R.id.text_dotbiengiakhoiluong_item_KLGDTB));
		this.tv_ClosePrice = ((TextView) findViewById(R.id.text_dotbiengiakhoiluong_item_closeprice));
	}

	public void setView(DotBienGiaKhoiLuongItem paramDotBienGiaKhoiLuongItem) {
		this.tv_Symbol.setText(paramDotBienGiaKhoiLuongItem.SYMBOL);
		this.tv_KLGDTB.setText(paramDotBienGiaKhoiLuongItem.KLGD_TB);
		this.tv_KLGD.setText(paramDotBienGiaKhoiLuongItem.KLGD);
		this.tv_ClosePrice.setText(paramDotBienGiaKhoiLuongItem.REALPRICE);
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.view.DotBienGiaKhoiLuongItemView JD-Core Version: 0.6.0
 */