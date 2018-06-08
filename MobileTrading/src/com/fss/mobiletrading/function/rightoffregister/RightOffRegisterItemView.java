package com.fss.mobiletrading.function.rightoffregister;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.consts.StringConst;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;

public class RightOffRegisterItemView extends LinearLayout {
	TextView tv_seq;
	TextView tv_MaCK;
	TextView tv_SLDaDK;
	TextView tv_SoLuong;
	TextView tv_Giamua;
	TextView tv_Sotienmua;

	TextView tv_dkqm_item_register;
	TextView tv_dkqm_item_info;
	RightOffRegisterItem item;

	OnRegisterListener onRegisterListener;
	OnWatchRoRListener onWatchRoRListener;

	public RightOffRegisterItemView(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.rightoffregister_item, this);
		this.tv_seq = ((TextView) findViewById(R.id.text_sequence));
		this.tv_SLDaDK = ((TextView) findViewById(R.id.text_dkqm_item_SoLuongDaDK));
		this.tv_MaCK = ((TextView) findViewById(R.id.text_dkqm_item_MaCK));
		this.tv_SoLuong = ((TextView) findViewById(R.id.text_dkqm_item_SoLuong));
		this.tv_Giamua = ((TextView) findViewById(R.id.text_dkqm_item_Giamua));
		this.tv_Sotienmua = ((TextView) findViewById(R.id.text_dkqm_item_Sotienmua));
		this.tv_dkqm_item_register = ((TextView) findViewById(R.id.text_dkqm_item_dangky));
		this.tv_dkqm_item_info = ((TextView) findViewById(R.id.text_dkqm_item_info));
		if (DeviceProperties.isTablet) {
			tv_dkqm_item_register.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onRegisterListener.onRegister(item);
				}
			});
			tv_dkqm_item_info.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onWatchRoRListener.onWatch(item);
				}
			});
		}
	}

	public void setView(RightOffRegisterItem item, int position) {
		this.item = item;
		this.tv_MaCK.setText(item.Symbol);
		this.tv_SLDaDK.setText(Common.formatAmount(item.RegisteredQtty));
		this.tv_SoLuong.setText(Common.formatAmount(item.OptionQtty));

		if (DeviceProperties.isTablet) {
			tv_seq.setText((position + 1) + "");
			this.tv_Giamua.setText(Common.formatAmount(item.Price));
			this.tv_Sotienmua.setText(Common.formatAmount(item.SettleAmount));
			if (item.Status.equals(RightOffRegister.CODE_AVAILABLE_REGISTER)
					|| item.Status.equals(RightOffRegister.CODE_ALL_REGISTERED)) {
				tv_dkqm_item_register.setEnabled(true);
				tv_dkqm_item_register.setText(getResources().getString(
						R.string.DangKy));
			} else if (item.Status
					.equals(RightOffRegister.CODE_EXPIRED_REGISTER)) {
				tv_dkqm_item_register.setEnabled(false);
				tv_dkqm_item_register.setText(getResources().getString(
						R.string.HetHan));
			} else {
				tv_dkqm_item_register.setEnabled(false);
				tv_dkqm_item_register.setText(StringConst.EMPTY);
			}
		}
	}

	public void setOnRegisterListener(OnRegisterListener onRegisterListener) {
		this.onRegisterListener = onRegisterListener;
	}

	public void setOnWatchRoRListener(OnWatchRoRListener onWatchRoRListener) {
		this.onWatchRoRListener = onWatchRoRListener;
	}
}
