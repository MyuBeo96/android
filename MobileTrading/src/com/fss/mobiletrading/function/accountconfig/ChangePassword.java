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
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MSTradeAppConfig;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;
import com.tcscuat.mobiletrading.design.LabelContentLayout;

public class ChangePassword extends AbstractFragment {
	static final String CHANGEPASSWORD = "SuccessService#1";

	Button btn_ChangePass;
	Button btn_Cancel;
	LabelContentLayout edt_ConfirmPass;
	LabelContentLayout edt_NewPass;
	LabelContentLayout edt_OldPass;
	View viewsp;

	public static ChangePassword newInstance(MainActivity mActivity) {
		ChangePassword self = new ChangePassword();
		self.mainActivity = mActivity;
		if (StaticObjectManager.loginInfo.IsBroker) {
			self.TITLE = mActivity.getStringResource(R.string.DoiMatKhau);
		} else {
			self.TITLE = mActivity
					.getStringResource(R.string.DoiMatKhauDangNhap);
		}
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.changepassword, viewGroup, false);
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

		btn_ChangePass = ((Button) view
				.findViewById(R.id.btn_thietlaptk_ChangePass));
		edt_OldPass = ((LabelContentLayout) view
				.findViewById(R.id.edt_thietlaptk_OldPass));
		edt_NewPass = ((LabelContentLayout) view
				.findViewById(R.id.edt_thietlaptk_NewPass));
		edt_ConfirmPass = ((LabelContentLayout) view
				.findViewById(R.id.edt_thietlaptk_ConfirmPass));
		btn_Cancel = (Button) view.findViewById(R.id.btn_HuyPass);
		viewsp = (View) view.findViewById(R.id.viewsp);
		if (DeviceProperties.isTablet) {
			Common.setupUI(view.findViewById(R.id.changepassword),
					this.getDialog());
			viewsp.setVisibility(View.VISIBLE);
		} else {
			Common.setupUI(view.findViewById(R.id.changepassword), mainActivity);
		}
	}

	private void initListener() {
		btn_Cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearFieldPassword();
			}
		});
		btn_ChangePass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (edt_ConfirmPass.getEditContent().length() == 0
						|| edt_NewPass.getEditContent().length() == 0
						|| edt_OldPass.getEditContent().length() == 0) {
					showDialogMessage(getStringResource(R.string.thong_bao),
							getStringResource(R.string.ChuaNhapDL));

				}
				else if(edt_NewPass.getText().toString().trim().length()<6
						||edt_ConfirmPass.getText().toString().trim().length()<6)
				{showDialogMessage(getString(R.string.thong_bao),getString(R.string.ChuaNhapDungDL),null);}
				else {
					if (StaticObjectManager.loginInfo.IsBroker) {
						AccountConfig
								.CallChangePassword(
										ChangePassword.this,
										MSTradeAppConfig.controller_ChangePasswordBroker,
										edt_OldPass.getText().toString(),
										edt_NewPass.getText().toString(),
										edt_ConfirmPass.getText().toString(),
										CHANGEPASSWORD);
					} else {
						AccountConfig.CallChangePassword(ChangePassword.this,
								MSTradeAppConfig.controller_ChangePassword,
								edt_OldPass.getText().toString(), edt_NewPass
										.getText().toString(), edt_ConfirmPass
										.getText().toString(), CHANGEPASSWORD);
					}
				}
			}
		});
	}
	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoActionMenu();
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {

		case CHANGEPASSWORD:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM,
					new SimpleAction() {

						@Override
						public void performAction(Object obj) {
							clearFieldPassword();
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
		clearFieldPassword();
	}

	protected void clearFieldPassword() {
		edt_OldPass.setText(StringConst.EMPTY);
		edt_NewPass.setText(StringConst.EMPTY);
		edt_ConfirmPass.setText(StringConst.EMPTY);
	}

}
