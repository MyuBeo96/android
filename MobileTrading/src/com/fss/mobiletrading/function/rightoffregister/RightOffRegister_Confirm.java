package com.fss.mobiletrading.function.rightoffregister;

import android.app.Dialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;

import com.fscuat.mobiletrading.design.CustomPassLayout;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.LabelContentLayout;
import com.fscuat.mobiletrading.design.SelectorImageView;

import java.util.ArrayList;
import java.util.List;

public class RightOffRegister_Confirm extends AbstractFragment {

	static final String RIGHTOFFREGISTERSUBMIT = "SuccessService#1";
	static final String RIGHTOFFREGISTERCONFIRM = "RightOffRegisterConfirmService";
	final String GENOTPSMS = "SuccessService#GENOTPSMS";

	LabelContentLayout tv_chitiet_Gia;
	LabelContentLayout tv_chitiet_Afaactno;
	LabelContentLayout tv_chitiet_MaCK;
	LabelContentLayout tv_chitiet_SLduocmua;
	LabelContentLayout tv_chitiet_SoDu;
	LabelContentLayout tv_chitiet_TongTien;
	Button btn_chitiet_ChapNhan;
	/**
	 * only tablet
	 */
	Button btn_close;

	CustomPassLayout edt_chitiet_MaPin;
	CustomPassLayout edt_chitiet_OTPCode;
	protected ImageButton checkboxTradingpass;
	protected ImageButton checkboxOTPCode;
	LabelContentLayout edt_chitiet_SLMua;
	RightOffRegisterItem rightOffRegisterItem;
	/**
	 * only tablet
	 */
	SelectorImageView isAcceptPolicy;
	boolean changeAfacctno;

	long disableOTPTime= 01;
	boolean isOTP= StaticObjectManager.loginInfo.IsOTPIssue == "true";
	boolean saveOTP= false;

	public static RightOffRegister_Confirm newInstance(MainActivity mActivity) {

		RightOffRegister_Confirm self = new RightOffRegister_Confirm();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.RightOffRegister);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.rightoffregister_confirm,
				viewGroup, false);
		tv_chitiet_Afaactno = ((LabelContentLayout) view
				.findViewById(R.id.text_DKQM_chitiet_Afacctno));
		tv_chitiet_MaCK = ((LabelContentLayout) view
				.findViewById(R.id.text_DKQM_chitiet_MaCK));
		tv_chitiet_SLduocmua = ((LabelContentLayout) view
				.findViewById(R.id.text_DKQM_chitiet_SLduocmua));
		tv_chitiet_Gia = ((LabelContentLayout) view
				.findViewById(R.id.text_DKQM_chitiet_Gia));
		tv_chitiet_SoDu = ((LabelContentLayout) view
				.findViewById(R.id.text_DKQM_chitiet_SoDu));
		edt_chitiet_SLMua = ((LabelContentLayout) view
				.findViewById(R.id.edt_DKQM_chitiet_SLMua));
		tv_chitiet_TongTien = ((LabelContentLayout) view
				.findViewById(R.id.text_DKQM_chitiet_TongTien));
		edt_chitiet_MaPin = ((CustomPassLayout) view
				.findViewById(R.id.edt_DKQM_chitiet_tradingcode));
		edt_chitiet_OTPCode = ((CustomPassLayout) view
				.findViewById(R.id.edt_DKQM_chitiet_OTCode));
		checkboxTradingpass = edt_chitiet_MaPin.getcheckbox();
		checkboxOTPCode = edt_chitiet_OTPCode.getcheckbox();
		btn_chitiet_ChapNhan = ((Button) view
				.findViewById(R.id.btn_DKQM_chitiet_Accept));

		btn_close = ((Button) view.findViewById(R.id.btn_alertdialog_negative));
		isAcceptPolicy = ((SelectorImageView) view
				.findViewById(R.id.img_rightoffregister_switch));

		Common.registrySeparatorNumber(edt_chitiet_SLMua.getEditContent());
		if (DeviceProperties.isTablet) {
			Common.setupUI(view.findViewById(R.id.rightoffregister_confirm),
					getDialog());
		} else {
			Common.setupUI(view.findViewById(R.id.rightoffregister_confirm),
					mainActivity);
		}
		initListener();
		return view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog d = super.onCreateDialog(savedInstanceState);
		Window w = d.getWindow();
		w.setBackgroundDrawableResource(R.drawable.bg_dialogfragment_top_bottom);
		return d;
	}

	@Override
	public void onStart() {
		super.onStart();
		int width = getDimenResource(R.dimen.dialogRoR_width);
		if (getDialog() != null) {
			getDialog().getWindow().setLayout(width, LayoutParams.WRAP_CONTENT);
		}
	}
	protected  void GenOTPSMS(){
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(getStringResource(R.string.controller_GenOTPSMS));
		}
		{
			list_key.add("afacctno");
			list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
		}
		{
			list_key.add("otptype");
			list_value.add(StaticObjectManager.otpType);
		}

		StaticObjectManager.connectServer.callHttpPostService(GENOTPSMS,
				this, list_key, list_value);
	}
	private void initListener() {
		checkboxTradingpass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				checkboxTradingpass.setSelected(!checkboxTradingpass.isSelected());
			}
		});
		checkboxOTPCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				checkboxOTPCode.setSelected(!checkboxOTPCode.isSelected());
			}
		});
		edt_chitiet_OTPCode.getbtn().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if ((SystemClock.elapsedRealtime() - StaticObjectManager.mLastGenOTPClickTime) < disableOTPTime) {
					return;
				}
				StaticObjectManager.mLastGenOTPClickTime=SystemClock.elapsedRealtime();
				GenOTPSMS();
			}
		});
		if (DeviceProperties.isTablet) {
			isAcceptPolicy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					isAcceptPolicy.setActivated(!isAcceptPolicy.isActivated());
				}
			});
			btn_close.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					RightOffRegister_Confirm.this.getDialog().dismiss();
				}
			});
		}

		edt_chitiet_SLMua.getEditContent().addTextChangedListener(
				new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						calculatorTotalAmount(s.toString(), tv_chitiet_Gia
								.getText().toString());
					}
				});

		btn_chitiet_ChapNhan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (edt_chitiet_MaPin.getVisibility() != View.VISIBLE) {
					String validate = validateRightOffRegisterConfirm();
					if (validate.equals(StringConst.TRUE)) {
						CallRightOffRegisterConfirm();
					} else {
						showDialogMessage(mainActivity
								.getStringResource(R.string.thong_bao),
								validate);
					}
				} else {
					String validate = validateRightOffRegisterSubmit();
					if (validate.equals(StringConst.TRUE)) {
						CallRightOffRegisterSubmit();
					} else {
						showDialogMessage(mainActivity
								.getStringResource(R.string.thong_bao),
								validate);
					}
				}
			}
		});
	}
	private void customDisplay(){
		if(isOTP){
			if(StaticObjectManager.saveOTP) {
				edt_chitiet_OTPCode.setText(StaticObjectManager.strOTP);
				checkboxOTPCode.setSelected(StaticObjectManager.saveOTP);
				edt_chitiet_OTPCode.setVisibility(View.GONE);
				saveOTP= StaticObjectManager.saveOTP;
			}
			else {
				edt_chitiet_OTPCode.setText(StringConst.EMPTY);
				edt_chitiet_OTPCode.setVisibility(View.VISIBLE);
			}
		}
		else {
			if (StaticObjectManager.saveTradingPass) {
				edt_chitiet_MaPin.setText(StaticObjectManager.tradingPass);
				checkboxTradingpass.setSelected(StaticObjectManager.saveTradingPass);
				edt_chitiet_MaPin.setVisibility(View.GONE);
			} else {
				edt_chitiet_MaPin.setText(StringConst.EMPTY);
				edt_chitiet_MaPin.setVisibility(View.VISIBLE);
			}
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		customDisplay();
		try {
			disableOTPTime= Long.parseLong(StaticObjectManager.loginInfo.DisableOTPTime)*1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		ShowDialogChiTiet();
	}

	@Override
	public void onShowed() {
		super.onShowed();
		if (changeAfacctno) {
			changeAfacctno = false;
			mainActivity.backNavigateFragment();
		}
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoAction();
	}

	private void CallRightOffRegisterConfirm() {

		if (edt_chitiet_SLMua.getText().length() == 0) {
			tv_chitiet_TongTien.setText(StringConst.EMPTY);
			return;
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(mainActivity
							.getStringResource(R.string.controller_RightOffRegisterConfirm));
		}
		{
			list_key.add("Afacctno");
			list_value.add(rightOffRegisterItem.Afacctno);
		}
		{
			list_key.add("Id");
			list_value.add(rightOffRegisterItem.Id);
		}
		{
			list_key.add("Symbol");
			list_value.add(rightOffRegisterItem.Symbol);
		}
		{
			list_key.add("OptionQtty");
			list_value.add(rightOffRegisterItem.OptionQtty);
		}
		{
			list_key.add("RegisteredQtty");
			list_value.add(rightOffRegisterItem.RegisteredQtty);
		}
		{
			list_key.add("Price");
			list_value.add(rightOffRegisterItem.Price);
		}
		{
			list_key.add("Qtty");
			list_value.add(edt_chitiet_SLMua.getText().toString());
		}
		{
			list_key.add("CashAvailable");
			list_value.add(rightOffRegisterItem.CashAvailable);
		}
		StaticObjectManager.connectServer.callHttpPostService(
				RIGHTOFFREGISTERCONFIRM, this, list_key, list_value);
	}

	private String validateRightOffRegisterSubmit() {
		if (edt_chitiet_MaPin.getEditContent().length() == 0) {
			return mainActivity.getStringResource(R.string.NhapPin);
		} else {
			return StringConst.TRUE;
		}
	}

	private String validateRightOffRegisterConfirm() {
		if (DeviceProperties.isTablet) {
			if (edt_chitiet_SLMua.getEditContent().length() <= 0) {
				return mainActivity
						.getStringResource(R.string.SoLuongMuaKhongDuocDeTrong);
			}

			if (edt_chitiet_SLMua.getText().toString().equals("0")) {
				return mainActivity
						.getStringResource(R.string.SoLuongPhaiLonHon0);
			}

			if (!isAcceptPolicy.isActivated()) {
				return mainActivity.getStringResource(R.string.RoRPolicy_Error);
			}

			return StringConst.TRUE;
		} else {
			if (edt_chitiet_SLMua.getEditContent().length() <= 0) {
				return mainActivity
						.getStringResource(R.string.SoLuongMuaKhongDuocDeTrong);
			}

			if (edt_chitiet_SLMua.getText().toString().equals("0")) {
				return mainActivity
						.getStringResource(R.string.SoLuongPhaiLonHon0);
			}

			return StringConst.TRUE;
		}
	}

	private void CallRightOffRegisterSubmit() {
		if(isOTP) {
			if (edt_chitiet_OTPCode.getText().length() == 0) {
				showDialogMessage(
						getResources().getString(
								R.string.thong_bao),
						getResources().getString(
								R.string.requireOTP));
				edt_chitiet_OTPCode.requestFocus();
				return;
			}
		}
		else {
			if (edt_chitiet_MaPin.getText().length() == 0) {
				showDialogMessage(getStringResource(R.string.thong_bao), getStringResource(R.string.NhapPin));
				edt_chitiet_MaPin.requestFocus();
				return;
			}
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(mainActivity
							.getStringResource(R.string.controller_RightOffRegisterSubmit));
		}
		{
			list_key.add("Afacctno");
			list_value.add(rightOffRegisterItem.Afacctno);
		}
		{
			list_key.add("Id");
			list_value.add(rightOffRegisterItem.Id);
		}
		{
			list_key.add("Symbol");
			list_value.add(rightOffRegisterItem.Symbol);
		}
		{
			list_key.add("OptionQtty");
			list_value.add(rightOffRegisterItem.OptionQtty);
		}
		{
			list_key.add("RegisteredQtty");
			list_value.add(rightOffRegisterItem.RegisteredQtty);
		}
		{
			list_key.add("Price");
			list_value.add(rightOffRegisterItem.Price);
		}
		{
			list_key.add("Qtty");
			list_value.add(edt_chitiet_SLMua.getText().toString());
		}
		{
			list_key.add("CashAvailable");
			list_value.add(rightOffRegisterItem.CashAvailable);
		}
		{
			list_key.add("Pin");
			list_value.add(isOTP ? edt_chitiet_OTPCode.getText().toString() : edt_chitiet_MaPin.getText().toString());
		}
		{
			list_key.add("saveotp");
			list_value.add(saveOTP?"Y":"N");
		}

		StaticObjectManager.connectServer.callHttpPostService(
				RIGHTOFFREGISTERSUBMIT, this, list_key, list_value);
		mainActivity.loadingScreen(true);
	}

	private void ShowDialogChiTiet() {
		if (rightOffRegisterItem != null) {
			tv_chitiet_Afaactno.setText(rightOffRegisterItem.Afacctno);
			//edt_chitiet_MaPin.setText(StringConst.EMPTY);
			edt_chitiet_SLMua.setText(StringConst.EMPTY);
			tv_chitiet_TongTien.setText(StringConst.EMPTY);
			tv_chitiet_MaCK.setText(rightOffRegisterItem.Symbol);
			tv_chitiet_SLduocmua.setText(Common
					.formatAmount(rightOffRegisterItem.OptionQtty));
			tv_chitiet_Gia.setText(Common
					.formatAmount(rightOffRegisterItem.Price));
			tv_chitiet_SoDu.setText(rightOffRegisterItem.CashAvailable);
		}
	}

	private void calculatorTotalAmount(String amount, String price) {
		int amountInt = 0;
		int priceInt = 0;
		try {
			amountInt = Integer.parseInt(amount.replace(",", ""));
			priceInt = Integer.parseInt(price.replace(",", ""));
			long total = amountInt * priceInt;
			tv_chitiet_TongTien.setText(Common.formatAmount(total));
		} catch (NumberFormatException e) {
			tv_chitiet_TongTien.setText(StringConst.EMPTY);
		}

	}

	@Override
	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		changeAfacctno = true;
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		switch (key) {
		case GENOTPSMS:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			break;
		case RIGHTOFFREGISTERCONFIRM:
			if (rObj.obj != null) {
				rightOffRegisterItem = (RightOffRegisterItem) rObj.obj;
				edt_chitiet_MaPin.setVisibility(View.VISIBLE);
			}
			break;
		case RIGHTOFFREGISTERSUBMIT:
			if(isOTP){
				StaticObjectManager.saveOTP= checkboxOTPCode.isSelected();
				if(StaticObjectManager.saveOTP)
					StaticObjectManager.strOTP = edt_chitiet_OTPCode.getText().toString();
				else
					StaticObjectManager.strOTP = StringConst.EMPTY;
			}
			else {
				StaticObjectManager.saveTradingPass = checkboxTradingpass.isSelected();
				if (StaticObjectManager.saveTradingPass)
					StaticObjectManager.tradingPass = edt_chitiet_MaPin.getText().toString();
				else
					StaticObjectManager.tradingPass = StringConst.EMPTY;
			}
			RightOffRegister rightOffRegister = (RightOffRegister) mainActivity
					.getFragmentByName(RightOffRegister.class.getName());
			rightOffRegister.onResume();
			showDialogMessage(
					mainActivity.getStringResource(R.string.thong_bao),
					mainActivity.getStringResource(R.string.Giaodichthanhcong),
					new SimpleAction() {

						@Override
						public void performAction(Object obj) {

							mainActivity.backNavigateFragment();
							onDismiss(getDialog());
						}
					});
			break;

		default:
			break;
		}
	}

	@Override
	protected void isReceivedResponse(String key) {
		super.isReceivedResponse(key);
		switch (key) {
		case RIGHTOFFREGISTERSUBMIT:
			mainActivity.loadingScreen(false);
			break;

		default:
			break;
		}
	}

	@Override
	public void getArgument(Object obj) {
		super.getArgument(obj);
		if (obj instanceof RightOffRegisterItem) {
			rightOffRegisterItem = (RightOffRegisterItem) obj;
		}
	}

}
