package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.object.LichSuDKQM_Item;

public class LichSuDKQM_View extends LinearLayout {
	TextView tv_EffectiveDate;
	TextView tv_PlacingOrderDate;
	TextView tv_Status;
	TextView tv_Symbol;
	TextView tv_Volume;

	public LichSuDKQM_View(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.lichsu_dkqm_item, this);
		this.tv_Symbol = ((TextView) findViewById(R.id.text_lichsudkqm_item_Symbol));
		this.tv_EffectiveDate = ((TextView) findViewById(R.id.text_lichsudkqm_item_EffectiveDate));
		this.tv_Volume = ((TextView) findViewById(R.id.text_lichsudkqm_item_RegisteredQtty));
		this.tv_Status = ((TextView) findViewById(R.id.text_lichsudkqm_item_Status));
	}

	public void setListView(LichSuDKQM_Item paramLichSuDKQM_Item) {
		this.tv_Symbol.setText(paramLichSuDKQM_Item.symbol);
		this.tv_EffectiveDate.setText(paramLichSuDKQM_Item.effectiveDate);
		this.tv_Status.setText(paramLichSuDKQM_Item.status);
		this.tv_Volume
				.setText(Common.formatAmount(paramLichSuDKQM_Item.volume));
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.adapter.LichSuDKQM_View JD-Core Version: 0.6.0
 */