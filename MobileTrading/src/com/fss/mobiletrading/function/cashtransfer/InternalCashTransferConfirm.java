package com.fss.mobiletrading.function.cashtransfer;

import android.os.Bundle;
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
import com.fss.mobiletrading.object.AcctnoDetail;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.MyActionBar.Action;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.LabelContentLayout;

import java.util.ArrayList;
import java.util.List;

public class InternalCashTransferConfirm extends AbstractFragment {

	static final String SUBMITINTERNALTRANSFER = "SuccessService#1";

	LabelContentLayout tv_chitiet_NguoiNhan;
	LabelContentLayout tv_chitiet_NoiDung;
	LabelContentLayout tv_chitiet_Phi;
	LabelContentLayout tv_chitiet_SoDu;
	LabelContentLayout tv_chitiet_SoLuuKyNhan;
	LabelContentLayout tv_chitiet_SoTKNhan;
	LabelContentLayout tv_chitiet_SoTienChuyen;
	LabelContentLayout chitiet_TieuKhoan;
	LabelContentLayout tv_chitiet_TongTien;
	LabelContentLayout edt_chitiet_MaPIN;
	Button btn_chitiet_ChapNhan;

	AcctnoDetail acctnoDetail;

	public static InternalCashTransferConfirm newInstance(MainActivity mActivity) {

		InternalCashTransferConfirm self = new InternalCashTransferConfirm();
		self.mainActivity = mActivity;
		self.TITLE = mActivity
				.getStringResource(R.string.InternalCashTransferDetail);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup paramViewGroup,
			Bundle paramBundle) {

		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View view = inflater.inflate(R.layout.internaltransfer_detail,
				paramViewGroup, false);
		chitiet_TieuKhoan = (LabelContentLayout) view
				.findViewById(R.id.lc_CTin_chitiet_TieuKhoan);
		tv_chitiet_NguoiNhan = ((LabelContentLayout) view
				.findViewById(R.id.text_CTin_chitiet_NguoiNhan));
		tv_chitiet_SoTKNhan = ((LabelContentLayout) view
				.findViewById(R.id.text_CTin_chitiet_SoTKNhan));
		tv_chitiet_SoLuuKyNhan = ((LabelContentLayout) view
				.findViewById(R.id.text_CTin_chitiet_SoLuuKy));
		tv_chitiet_SoDu = ((LabelContentLayout) view
				.findViewById(R.id.text_CTin_chitiet_SoDu));
		tv_chitiet_SoTienChuyen = ((LabelContentLayout) view
				.findViewById(R.id.text_CTin_chitiet_SoTienChuyen));
		tv_chitiet_Phi = ((LabelContentLayout) view
				.findViewById(R.id.text_CTin_chitiet_PhiChuyenTien));
		tv_chitiet_TongTien = ((LabelContentLayout) view
				.findViewById(R.id.text_CTin_chitiet_TongTien));
		tv_chitiet_NoiDung = ((LabelContentLayout) view
				.findViewById(R.id.text_CTin_chitiet_NoiDung));
		edt_chitiet_MaPIN = ((LabelContentLayout) view
				.findViewById(R.id.edt_CTin_chitiet_tradingcode));
		btn_chitiet_ChapNhan = ((Button) view
				.findViewById(R.id.btn_CTin_chitiet_Accept));

		if (DeviceProperties.isTablet) {
			Common.setupUI(view.findViewById(R.id.internalcashtransfer_detail),
					this.getDialog());
		} else {
			Common.setupUI(view.findViewById(R.id.internalcashtransfer_detail),
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

	private void initListener() {
		btn_chitiet_ChapNhan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CallSubmitInternalTransfer();
			}
		});
	}

	@Override
	public void setHomeLogoAction() {
		if (!DeviceProperties.isTablet) {
			mainActivity.setHomeLogoAction(new Action() {

				@Override
				public void performAction(View view) {
					backToInternalTransfer();
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

	@Override
	public void onResume() {
		super.onResume();
		showInternalTransferDetail();
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setHomeLogoAction();
	}

	protected void CallSubmitInternalTransfer() {
		if (!btn_chitiet_ChapNhan.isEnabled() || acctnoDetail == null) {
			return;
		}
		if (!acctnoDetail.CustodyCd.matches(acctnoDetail.BeneficiaryCustodyCd)
				&& edt_chitiet_MaPIN.getText().length() == 0) {
			showDialogMessage(R.string.thong_bao, R.string.NhapPin);
			return;
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_SubmitInternalTransfer));
		}
		{
			list_key.add("CustodyCd");
			list_value.add(acctnoDetail.CustodyCd);
		}
		{
			list_key.add("BeneficiaryName");
			list_value.add(acctnoDetail.BeneficiaryName);
		}
		{
			list_key.add("Afacctno");
			list_value.add(acctnoDetail.Afacctno);
		}
		{
			list_key.add("BeneficiaryCustodyCd");
			list_value.add(acctnoDetail.BeneficiaryCustodyCd);
		}
		{
			list_key.add("BeneficiaryAfacctno");
			list_value.add(acctnoDetail.BeneficiaryAfacctno);
		}
		{
			list_key.add("CashAvailable");
			list_value.add(acctnoDetail.CashAvailable);
		}
		{
			list_key.add("Amount");
			list_value.add(acctnoDetail.Amount);
		}
		{
			list_key.add("Desc");
			list_value.add(acctnoDetail.Desc);
		}
		{
			list_key.add("SenderCustodyCd");
			list_value.add(acctnoDetail.SenderCustodyCd);
		}
		{
			list_key.add("SenderName");
			list_value.add(acctnoDetail.SenderName);
		}
		{
			if (edt_chitiet_MaPIN.getVisibility() == View.VISIBLE) {
				list_key.add("Pin");
				list_value.add(edt_chitiet_MaPIN.getText().toString());
			}
		}

		StaticObjectManager.connectServer.callHttpPostService(
				SUBMITINTERNALTRANSFER, this, list_key, list_value);
		mainActivity.loadingScreen(true);
	}

	private void showInternalTransferDetail() {
		if (acctnoDetail == null) {
			return;
		}
		chitiet_TieuKhoan.getContent().setText(acctnoDetail.Afacctno);
		tv_chitiet_NguoiNhan.setText(acctnoDetail.BeneficiaryName);
		tv_chitiet_SoTKNhan.setText(acctnoDetail.BeneficiaryAfacctno);
		tv_chitiet_SoLuuKyNhan.setText(acctnoDetail.BeneficiaryCustodyCd);
		tv_chitiet_SoDu.setText(acctnoDetail.CashAvailable);

		tv_chitiet_SoTienChuyen.setText(Common
				.formatAmount(acctnoDetail.Amount));
		tv_chitiet_Phi.setText(Common.formatAmount(acctnoDetail.Fee));
		tv_chitiet_TongTien.setText(Common
				.formatAmount(acctnoDetail.TotalAmount));

		if (acctnoDetail.BeneficiaryCustodyCd != null
				&& acctnoDetail.CustodyCd != null
				&& acctnoDetail.BeneficiaryCustodyCd
						.equals(acctnoDetail.CustodyCd)) {
			edt_chitiet_MaPIN.setVisibility(View.GONE);
		} else {
			edt_chitiet_MaPIN.setVisibility(View.VISIBLE);
		}
		edt_chitiet_MaPIN.setText(StringConst.EMPTY);
		tv_chitiet_NoiDung.setText(acctnoDetail.Desc);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		switch (key) {
		case SUBMITINTERNALTRANSFER:
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
			}
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM,
					new SimpleAction() {

						@Override
						public void performAction(Object obj) {
							mainActivity.mapFragment.get(
									InternalCashTransfer.class.getName())
									.refresh();
							backToInternalTransfer();
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
		case SUBMITINTERNALTRANSFER:
			mainActivity.loadingScreen(false);
			break;

		default:
			break;
		}
	}

	@Override
	public void getArgument(Object obj) {
		super.getArgument(obj);
		if (obj instanceof AcctnoDetail) {
			acctnoDetail = (AcctnoDetail) obj;
		}
	}

	private void backToInternalTransfer() {
		mainActivity.backNavigateFragment();
		onDismiss(getDialog());
	}
}
