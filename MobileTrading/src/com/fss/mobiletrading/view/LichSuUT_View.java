package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.object.LichSuUT_Item;

public class LichSuUT_View extends LinearLayout {
	TextView tv_Ngay;
	TextView tv_Phi;
	TextView tv_TienNhan;

	public LichSuUT_View(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.lichsu_ungtruoc_item, this);
		this.tv_Ngay = ((TextView) findViewById(R.id.text_lichsuut_item_Ngay));
		this.tv_TienNhan = ((TextView) findViewById(R.id.text_lichsuut_item_ReceiveMoney));
		this.tv_Phi = ((TextView) findViewById(R.id.text_lichsuut_item_Fee));
	}

	public void setListView(LichSuUT_Item paramLichSuUT_Item) {
		this.tv_Ngay.setText(paramLichSuUT_Item.ngay);
		this.tv_TienNhan.setText(paramLichSuUT_Item.Tiennhan);
		this.tv_Phi.setText(paramLichSuUT_Item.Tienphi);
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.adapter.LichSuUT_View JD-Core Version: 0.6.0
 */