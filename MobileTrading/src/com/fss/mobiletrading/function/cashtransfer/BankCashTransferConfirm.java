package com.fss.mobiletrading.function.cashtransfer;

import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tcscuat.mobiletrading.design.CustomPassLayout;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.notify.NotificationService;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.ConBankAccDetail;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MSTradeAppConfig;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.MyActionBar.Action;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;
import com.tcscuat.mobiletrading.design.LabelContentLayout;

import java.util.ArrayList;
import java.util.List;

public class BankCashTransferConfirm extends AbstractFragment {

	static final String SUBMITCASHTRANSFER = "SuccessService#1";
	final String GENOTPSMS = "SuccessService#GENOTPSMS";
	public static final String GETUNREAD = "GetUnReadService#GETUNREAD";
	int unread = 0;
	ConBankAccDetail conBankAccDetail;

	LabelContentLayout tv_chitiet_NganHang;
	LabelContentLayout tv_chitiet_NguoiNhan;
	LabelContentLayout tv_chitiet_NoiDung;
	LabelContentLayout tv_chitiet_PGD;
	LabelContentLayout tv_chitiet_Phi;
	LabelContentLayout tv_chitiet_SoDu;
	LabelContentLayout tv_chitiet_SoTKNH;
	LabelContentLayout tv_chitiet_SoTienChuyen;
	LabelContentLayout tv_chitiet_TieuKhoan;
	LabelContentLayout tv_chitiet_TinhTP;
	LabelContentLayout tv_chitiet_TongTien;
	CustomPassLayout edt_chitiet_MaPIN;
	CustomPassLayout edt_chitiet_OTPCode;
	Button btn_chitiet_ChapNhan;
	protected ImageButton checkboxTradingpass;
	protected ImageButton checkboxOTPCode;
	protected EditText etOTPCode;
	long disableOTPTime= 01;
	boolean isOTP= StaticObjectManager.loginInfo.IsOTPCash == "true";
	boolean saveOTP= false;

	public static BankCashTransferConfirm newInstance(MainActivity mActivity) {

		BankCashTransferConfirm self = new BankCashTransferConfirm();
		self.mainActivity = mActivity;
		self.TITLE = mActivity
				.getStringResource(R.string.BankCashTransferDetail);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup paramViewGroup,
			Bundle paramBundle) {

		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View view = inflater.inflate(R.layout.bankcashtransfer_detail,
				paramViewGroup, false);
		tv_chitiet_TieuKhoan = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_chitiet_Tieukhoan));
		tv_chitiet_NguoiNhan = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_chitiet_NguoiNhan));
		tv_chitiet_SoTKNH = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_chitiet_SoTKNH));
		tv_chitiet_NganHang = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_chitiet_NganHang));
		tv_chitiet_PGD = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_chitiet_PGD));
		tv_chitiet_TinhTP = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_chitiet_TinhTP));
		tv_chitiet_SoDu = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_chitiet_SoDu));
		tv_chitiet_SoTienChuyen = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_chitiet_SoTienChuyen));
		tv_chitiet_Phi = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_chitiet_PhiChuyenTien));
		tv_chitiet_TongTien = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_chitiet_TongTien));
		tv_chitiet_NoiDung = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_chitiet_NoiDung));
		edt_chitiet_MaPIN = ((CustomPassLayout) view
				.findViewById(R.id.edt_CTout_chitiet_tradingcode));
		checkboxTradingpass = edt_chitiet_MaPIN.getcheckbox();
		edt_chitiet_OTPCode= ((CustomPassLayout) view
				.findViewById(R.id.edt_CTout_chitiet_OTCode));
		checkboxOTPCode = edt_chitiet_OTPCode.getcheckbox();
		etOTPCode = (EditText)edt_chitiet_OTPCode.getEditContent();
		btn_chitiet_ChapNhan = ((Button) view
				.findViewById(R.id.btn_CTout_chitiet_Accept));
		if (DeviceProperties.isTablet) {
			Common.setupUI(view.findViewById(R.id.bankcashtransfer_detail),
					this.getDialog());
		} else {
			Common.setupUI(view.findViewById(R.id.bankcashtransfer_detail),
					mainActivity);
		}
		init();
		initListener();

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		int width = getDimenResource(R.dimen.cashtransferconfirm_dialog_width);
		int height = LayoutParams.WRAP_CONTENT;
		if (getDialog() != null) {
			getDialog().getWindow().setLayout(width, height);
		}
	}

	private void init() {

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
		CallUnRead(this);
	}
	public void CallUnRead(INotifier notifier) {
		String android_id = Settings.Secure.getString(getContext().getContentResolver(),
				Settings.Secure.ANDROID_ID);
		NotificationService.CallUnRead(StaticObjectManager.deviceToken, android_id,
				StaticObjectManager.loginInfo.UserName, notifier, GETUNREAD);
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
		btn_chitiet_ChapNhan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if( StaticObjectManager.loginInfo.IsDigital.equals("Y"))
				{
					showDialogMessage(getStringResource(R.string.thong_bao),
							getStringResource(R.string.CheckPolicy));
					return;

				}
				CallSubmitCashTransfer();
			}
		});
	}

	@Override
	public void setHomeLogoAction() {
		if (!DeviceProperties.isTablet) {
			mainActivity.setHomeLogoAction(new Action() {

				@Override
				public void performAction(View view) {
					backToBankTransfer();
				}

				@Override
				public int getDrawable() {

					return R.drawable.ic_back;
				}

				@Override
				public String getText() {

					return null;
				}
			});
		}
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
				edt_chitiet_MaPIN.setText(StaticObjectManager.tradingPass);
				checkboxTradingpass.setSelected(StaticObjectManager.saveTradingPass);
				edt_chitiet_MaPIN.setVisibility(View.GONE);
			} else {
				edt_chitiet_MaPIN.setText(StringConst.EMPTY);
				edt_chitiet_MaPIN.setVisibility(View.VISIBLE);
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
		showTransferDetail();
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setHomeLogoAction();
	}

	protected void CallSubmitCashTransfer() {

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
			if (edt_chitiet_MaPIN.getText().length() == 0) {
				showDialogMessage(getStringResource(R.string.thong_bao), getStringResource(R.string.NhapPin));
				edt_chitiet_MaPIN.requestFocus();
				return;
			}
		}

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_SubmitCashTransfer));
		}
		{
			list_key.add("ConBankAcc");
			list_value.add(conBankAccDetail.ConBankAcc);
		}
		{
			list_key.add("ConBankAccIndex");
			list_value.add(conBankAccDetail.ConBankAccIndex);
		}
		{
			list_key.add("BankID");
			list_value.add(conBankAccDetail.BankID);
		}
		{
			list_key.add("BeneficiaryName");
			list_value.add(conBankAccDetail.BeneficiaryName);
		}
		{
			list_key.add("BeneficiaryBank");
			list_value.add(conBankAccDetail.BeneficiaryBank);
		}
		{
			list_key.add("City");
			list_value.add(conBankAccDetail.City);
		}
		{
			list_key.add("Branch");
			list_value.add(conBankAccDetail.Branch);
		}
		{
			list_key.add("Afacctno");
			list_value.add(conBankAccDetail.Afacctno);
		}
		{
			list_key.add("BeneficiaryCustodyCd");
			list_value.add(conBankAccDetail.BeneficiaryCustodyCd);
		}
		{
			list_key.add("BeneficiaryAfacctno");
			list_value.add(conBankAccDetail.BeneficiaryAfacctno);
		}
		{
			list_key.add("CashAvailable");
			list_value.add(conBankAccDetail.CashAvailable);
		}
		{
			list_key.add("Amount");
			list_value.add(conBankAccDetail.Amount);
		}
		{
			list_key.add("Fee");
			list_value.add(conBankAccDetail.Fee);
		}
		{
			list_key.add("Desc");
			list_value.add(conBankAccDetail.Desc);
		}
		{
			list_key.add("TransferType");
			list_value.add("1");
		}
		{
			list_key.add("Pin");
			list_value.add(isOTP ? edt_chitiet_OTPCode.getText().toString() : edt_chitiet_MaPIN.getText().toString());
		}
		{
			list_key.add("saveotp");
			list_value.add(saveOTP?"Y":"N");
		}
		StaticObjectManager.connectServer.callHttpPostService(
				SUBMITCASHTRANSFER, this, list_key, list_value);
		mainActivity.loadingScreen(true);


	}

	private void showTransferDetail() {
		if (conBankAccDetail == null) {
			return;
		}
		tv_chitiet_TieuKhoan.setText(conBankAccDetail.Afacctno);
		tv_chitiet_NguoiNhan.setText(conBankAccDetail.BeneficiaryName);
		tv_chitiet_SoTKNH.setText(conBankAccDetail.ConBankAcc);
		tv_chitiet_NganHang.setText(conBankAccDetail.BeneficiaryBank);
		tv_chitiet_PGD.setText(conBankAccDetail.Branch);
		tv_chitiet_TinhTP.setText(conBankAccDetail.City);
		tv_chitiet_SoDu.setText(conBankAccDetail.CashAvailable);

		try {
			tv_chitiet_Phi.setText(Common.formatAmount(conBankAccDetail.Fee));
		} catch (NumberFormatException e) {
			tv_chitiet_Phi.setText("-");
		}
		try {
			tv_chitiet_SoTienChuyen.setText(Common
					.formatAmount(conBankAccDetail.Amount));
		} catch (NumberFormatException e) {
			tv_chitiet_SoTienChuyen.setText("-");
		}
		tv_chitiet_TongTien.setText(Common
				.formatAmount(conBankAccDetail.TotalAmount));
		tv_chitiet_NoiDung.setText(conBankAccDetail.Desc);
		//edt_chitiet_MaPIN.setText(StringConst.EMPTY);
	}
	@Override
	public void onDetach() {
		super.onDetach();
		StaticObjectManager.mLastGenOTPClickTime = 01;
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		switch (key) {
		case GETUNREAD:
			unread = (int) rObj.obj;
			mainActivity.showUnReadNotify(unread);
			break;
		case GENOTPSMS:
			String[] arrayString = rObj.EM.split(";");
			showDialogMessage(getStringResource(R.string.thong_bao), arrayString[0]);
			if (rObj.EC == 0 && StaticObjectManager.loginInfo.IsFillOTP.equals("Y"))
			{
				etOTPCode.setText(arrayString[1]);
			}
			break;
		case SUBMITCASHTRANSFER:
			clearForm();
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
			}
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
					StaticObjectManager.tradingPass = edt_chitiet_MaPIN.getText().toString();
				else
					StaticObjectManager.tradingPass = StringConst.EMPTY;
			}
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM,
					new SimpleAction() {

						@Override
						public void performAction(Object obj) {
							if (conBankAccDetail.BankID
									.startsWith(MSTradeAppConfig.MSBBankId)) {
								mainActivity.mapFragment.get(
										SCCashTransfer.class.getName())
										.refresh();
							} else {
								mainActivity.mapFragment.get(
										BankCashTransfer.class.getName())
										.refresh();
							}
							backToBankTransfer();
							btn_chitiet_ChapNhan.setEnabled(true);
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
		case SUBMITCASHTRANSFER:
			mainActivity.loadingScreen(false);
			break;

		default:
			break;
		}
	}

	private void clearForm() {
		edt_chitiet_MaPIN.setText(StringConst.EMPTY);
	}

	@Override
	public void getArgument(Object obj) {
		super.getArgument(obj);
		if (obj instanceof ConBankAccDetail) {
			conBankAccDetail = (ConBankAccDetail) obj;
		}
	}

	private void backToBankTransfer() {
		mainActivity.backNavigateFragment();
		onDismiss(getDialog());
	}

}
