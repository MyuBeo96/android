package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.function.rightoffregister.RightOffRegisterItem;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;

public class DKQM_View extends LinearLayout {
	TextView tv_MaCK;
	TextView tv_SLDaDK;
	TextView tv_SoLuong;

	TextView tv_Giamua;
	TextView tv_Sotienmua;

	public DKQM_View(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.rightoffregister_item, this);
		this.tv_SLDaDK = ((TextView) findViewById(R.id.text_dkqm_item_SoLuongDaDK));
		this.tv_MaCK = ((TextView) findViewById(R.id.text_dkqm_item_MaCK));
		this.tv_SoLuong = ((TextView) findViewById(R.id.text_dkqm_item_SoLuong));

		this.tv_Giamua = ((TextView) findViewById(R.id.text_dkqm_item_Giamua));
		this.tv_Sotienmua = ((TextView) findViewById(R.id.text_dkqm_item_Sotienmua));
	}

	public void setView(RightOffRegisterItem item, int vt) {
		this.tv_MaCK.setText(item.Symbol);
		this.tv_SLDaDK.setText(Common.formatAmount(item.RegisteredQtty));
		this.tv_SoLuong.setText(Common.formatAmount(item.OptionQtty));

		if (DeviceProperties.isTablet) {
			this.tv_Giamua.setText(Common.formatAmount(item.Price));
			this.tv_Sotienmua.setText(Common.formatAmount(item.SettleAmount));
		}
	}
}
