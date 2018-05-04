package com.fss.mobiletrading.function.accountconfig;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;
import com.msbuat.mobiletrading.design.LabelContentLayout;

public class ChangeTradingPassword extends AbstractFragment {
	static final String CHANGEPASSWORD = "SuccessService#1";
	Button btn_ChangePin;
	Button btn_Cancel;
	LabelContentLayout edt_ConfirmPin;
	LabelContentLayout edt_NewPin;
	LabelContentLayout edt_OldPin;

	public static ChangeTradingPassword newInstance(MainActivity mActivity) {

		ChangeTradingPassword self = new ChangeTradingPassword();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.DoiPIN);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.changetradingpassword, viewGroup,
				false);
		initView(view);
		initListener();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		int width = getDimenResource(R.dimen.t_dialogchangepass_width);
		if (getDialog() != null) {
			getDialog().getWindow().setLayout(width, LayoutParams.WRAP_CONTENT);
		}
	}

	private void initView(View view) {

		btn_ChangePin = ((Button) view
				.findViewById(R.id.btn_thietlaptk_ChangePin));
		btn_Cancel = (Button) view.findViewById(R.id.btn_HuyPin);
		edt_OldPin = ((LabelContentLayout) view
				.findViewById(R.id.edt_thietlaptk_OldPin));
		edt_NewPin = ((LabelContentLayout) view
				.findViewById(R.id.edt_thietlaptk_NewPin));
		edt_ConfirmPin = ((LabelContentLayout) view
				.findViewById(R.id.edt_thietlaptk_ConfirmPin));
		if (DeviceProperties.isTablet) {
			Common.setupUI(view.findViewById(R.id.changetradingpassword),
					this.getDialog());
		} else {
			Common.setupUI(view.findViewById(R.id.changetradingpassword),
					mainActivity);
		}
	}

	private void initListener() {
		btn_Cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearFieldPin();
			}
		});
		btn_ChangePin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (edt_ConfirmPin.getEditContent().length() == 0
						|| edt_NewPin.getEditContent().length() == 0
						|| edt_OldPin.getEditContent().length() == 0) {
					showDialogMessage(getStringResource(R.string.thong_bao),
							getStringResource(R.string.ChuaNhapDL));

				}
				else if(edt_NewPin.getText().toString().trim().length()<6
						||edt_ConfirmPin.getText().toString().trim().length()<6){
					showDialogMessage(getString(R.string.thong_bao),getString(R.string.ChuaNhapDungDL),null);}
				else {
					AccountConfig.CallChangePassword(
							ChangeTradingPassword.this,
							MSTradeAppConfig.controller_ChangeTradingPassword,
							edt_OldPin.getText().toString(), edt_NewPin
									.getText().toString(), edt_ConfirmPin
									.getText().toString(), CHANGEPASSWORD);
				}
			}
		});
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {

		case CHANGEPASSWORD:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM,
					new SimpleAction() {

						@Override
						public void performAction(Object obj) {
							clearFieldPin();
						}
					});
			break;
		default:
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		clearFieldPin();
	}

	protected void clearFieldPin() {
		edt_OldPin.setText(StringConst.EMPTY);
		edt_NewPin.setText(StringConst.EMPTY);
		edt_ConfirmPin.setText(StringConst.EMPTY);
	}
}
