package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.object.LichSuCT_Item;

public class LichSuCT_View extends LinearLayout {
	TextView Description;
	TextView NgayThucHien;
	TextView SoTienChuyen;
	TextView TKNhan;
	TextView TrangThai;

	public LichSuCT_View(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.lichsu_chuyentien_item, this);
		this.NgayThucHien = ((TextView) findViewById(R.id.text_LichSuCT_EffectiveDate));
		this.Description = ((TextView) findViewById(R.id.text_LichSuCT_Description));
		this.SoTienChuyen = ((TextView) findViewById(R.id.text_LichSuCT_Amount));
		this.TKNhan = ((TextView) findViewById(R.id.text_LichSuCT_BeneficiaryAcctno));
		this.TrangThai = ((TextView) findViewById(R.id.text_LichSuCT_Status));
	}

	public void setListview(LichSuCT_Item paramLichSuCT_Item) {
		this.NgayThucHien.setText(paramLichSuCT_Item.getEffectiveDate());
		this.Description.setText(paramLichSuCT_Item.getTransferType());
		this.TKNhan.setText(paramLichSuCT_Item.getBeneficiaryAcctno());
		this.TrangThai.setText(paramLichSuCT_Item.getStatus());
		this.SoTienChuyen.setText(Common.formatAmount(paramLichSuCT_Item
				.getAmount()));
	}
}
