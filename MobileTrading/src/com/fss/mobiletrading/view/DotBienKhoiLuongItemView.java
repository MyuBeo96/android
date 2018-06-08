package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.object.DotBienKhoiLuongItem;

public class DotBienKhoiLuongItemView extends LinearLayout {
	TextView tv_KLGD;
	TextView tv_KLGDTB;
	TextView tv_KLGD_SV_TB;
	TextView tv_Symbol;

	public DotBienKhoiLuongItemView(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.boloc_dotbienkhoiluong_item, this);
		this.tv_Symbol = ((TextView) findViewById(R.id.text_bolockhoiluong_item_Symbol));
		this.tv_KLGD = ((TextView) findViewById(R.id.text_bolockhoiluong_item_KLGD));
		this.tv_KLGDTB = ((TextView) findViewById(R.id.text_bolockhoiluong_item_KLGDTB));
		this.tv_KLGD_SV_TB = ((TextView) findViewById(R.id.text_bolockhoiluong_item_ChangeKLGDTB));
	}

	public void setView(DotBienKhoiLuongItem paramDotBienKhoiLuongItem) {
		this.tv_Symbol.setText(paramDotBienKhoiLuongItem.SYMBOL);
		this.tv_KLGDTB.setText(paramDotBienKhoiLuongItem.KLGD_TB);
		this.tv_KLGD_SV_TB.setText(paramDotBienKhoiLuongItem.KLGD_SV_TB);
		this.tv_KLGD.setText(paramDotBienKhoiLuongItem.KLGD);
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.view.DotBienKhoiLuongItemView JD-Core Version: 0.6.0
 */