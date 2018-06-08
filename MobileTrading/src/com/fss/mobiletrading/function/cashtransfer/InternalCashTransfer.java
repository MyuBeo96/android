package com.fss.mobiletrading.function.cashtransfer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.AcctnoDetail;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.BankAccItem;
import com.fss.mobiletrading.object.BankAccList;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.LabelContentLayout;
import com.fscuat.mobiletrading.design.MyContextMenu.OnItemSelectedListener;
import com.fscuat.mobiletrading.design.MySpinner;
import com.fscuat.mobiletrading.design.NumberEditText;

import java.util.ArrayList;
import java.util.List;

public class InternalCashTransfer extends AbstractFragment {
	private static final String INTERNALTRANSFERTYPE = "0";
	final String FINDRECACCTNODETAIL = "FindRecAcctnoDetailService";
	final String INTERNALTRANSFERCHECK = "InternalTransferCheckService";

	AcctnoDetail acctnoDetail;
	Button btn_ChapNhan;
	Button btn_Clear;
	LabelContentLayout edt_NoiDung;
	NumberEditText edt_SoTienChuyen;
	List<BankAccItem> listAcc;
	List<AcctnoItem> listSender;
	MySpinner spn_TKNhan;
	MySpinner spn_SenderAfacctno;

	LabelContentLayout tv_NguoiNhan;
	LabelContentLayout tv_SoDu;
	LabelContentLayout tv_SoLuuKyNhan;
	/**
	 * only Tablet
	 */
	RelativeLayout lay_switch;
	/**
	 * only Tablet
	 */
	LabelContentLayout tv_SenderCustody;
	/**
	 * only Tablet
	 */
	LabelContentLayout tv_tienungtruoc;
	/**
	 * only Tablet
	 */
	LabelContentLayout tv_phiungdutinh;
	/**
	 * only Tablet
	 */
	LabelContentLayout tv_tienmat;
	/**
	 * only Tablet
	 */
	LabelContentLayout tv_sotiendichvu;
	/**
	 * only Tablet
	 */
	LabelContentLayout tv_tongtien;
	/**
	 * only Tablet
	 */
	LabelContentLayout tv_sotientoidaduocchuyen;
	TextView tv_transferdesc;
	CashTransfer cashTransfer;
	boolean notifyChangeAfacctno = true;
	static String key_trans = "cashtransInternalNotice";

	public static InternalCashTransfer newInstance(MainActivity mActivity) {

		InternalCashTransfer self = new InternalCashTransfer();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.InternalTransfer);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {

		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View localView = paramLayoutInflater.inflate(
				R.layout.internal_cashtransfer, paramViewGroup, false);
		initView(localView);
		init();
		initListener();

		return localView;
	}

	private void initView(View view) {

		edt_SoTienChuyen = ((NumberEditText) view
				.findViewById(R.id.edt_internalcashtransfer_SoTienChuyen));
		edt_NoiDung = ((LabelContentLayout) view
				.findViewById(R.id.edt_internalcashtransfer_NoiDung));
		tv_SoDu = ((LabelContentLayout) view
				.findViewById(R.id.text_internalcashtransfer_cashavailable));
		tv_NguoiNhan = ((LabelContentLayout) view
				.findViewById(R.id.text_internalcashtransfer_BeneficiaryName));
		tv_SoLuuKyNhan = ((LabelContentLayout) view
				.findViewById(R.id.text_internalcashtransfer_SoLuuKy));
		spn_SenderAfacctno = ((MySpinner) view
				.findViewById(R.id.spn_internalcashtransfer_SenderAfacctno));
		spn_TKNhan = ((MySpinner) view
				.findViewById(R.id.spn_internalcashtransfer_SoTKNhan));
		btn_ChapNhan = ((Button) view
				.findViewById(R.id.btn_internalcashtransfer_ChapNhan));
		btn_Clear = ((Button) view
				.findViewById(R.id.btn_internalcashtransfer_Clear));
		tv_SenderCustody = ((LabelContentLayout) view
				.findViewById(R.id.text_internalcashtransfer_SenderCustodyCd));
		lay_switch = (RelativeLayout) view
				.findViewById(R.id.text_internalcashtransfer_switch);
		tv_tienungtruoc = ((LabelContentLayout) view
				.findViewById(R.id.text_internalcashtransfer_advancecash));
		tv_phiungdutinh = ((LabelContentLayout) view
				.findViewById(R.id.text_internalcashtransfer_phiungdutinh));
		tv_tienmat = ((LabelContentLayout) view
				.findViewById(R.id.text_internalcashtransfer_tienmat));
		tv_sotiendichvu = ((LabelContentLayout) view
				.findViewById(R.id.text_internalcashtransfer_sotiendichvu));
		tv_tongtien = ((LabelContentLayout) view
				.findViewById(R.id.text_internalcashtransfer_amounttotal));
		tv_transferdesc = (TextView) view.findViewById(R.id.text_transferdesc);
		tv_sotientoidaduocchuyen = ((LabelContentLayout) view
				.findViewById(R.id.text_internalcashtransfer_sotientoidaduocchuyen));
		Common.setupUI(view.findViewById(R.id.internal_cashtransfer),
				getActivity());
	}

	private void init() {
		if (cashTransfer == null) {
			cashTransfer = (CashTransfer) mainActivity
					.getFragmentByName(CashTransfer.class.getName());
		}
		if (listSender == null) {
			listSender = StaticObjectManager.loginInfo.contractList;
		}
		if (listAcc == null) {
			listAcc = new ArrayList<BankAccItem>();
		}
		Common.registrySeparatorNumber(edt_SoTienChuyen);
		spn_TKNhan.setChoises(listAcc);
		spn_SenderAfacctno.setChoises(listSender);
	}

	private void initListener() {

		// for tablet
		if (DeviceProperties.isTablet) {
			lay_switch.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					cashTransfer.onSwitchClickListener();
				}
			});
			edt_SoTienChuyen
					.setOnFocusChangeListener(new OnFocusChangeListener() {

						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							if (!hasFocus) {
								if (spn_SenderAfacctno.getContextMenu()
										.getSelectedItem() == null
										|| spn_TKNhan.getContextMenu()
												.getSelectedItem() == null) {
									return;
								}
								Log.i("hhhhhhhhhhhhhhh",
										((AcctnoItem) spn_SenderAfacctno
												.getContextMenu()
												.getSelectedItem()).ACCTNO
												+ ""
												+ ((BankAccItem) spn_TKNhan
														.getContextMenu()
														.getSelectedItem()).AFACCTNO);
								CashTransfer.CallGetTransferFeeAndTotal(
										INTERNALTRANSFERTYPE, edt_SoTienChuyen
												.getText().toString(), null,
										((AcctnoItem) spn_SenderAfacctno
												.getContextMenu()
												.getSelectedItem()).ACCTNO,
										((BankAccItem) spn_TKNhan
												.getContextMenu()
												.getSelectedItem()).REG_ACCTNO,
										InternalCashTransfer.this);
							}
						}
					});
		}
		spn_TKNhan.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelect(Object obj, int position) {
				CallFindRecAcctnoDetail();
			}
		});

		spn_SenderAfacctno
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelect(Object obj, int position) {
						if (obj instanceof AcctnoItem) {
							AcctnoItem acctnoItem = (AcctnoItem) obj;
							((CashTransfer) mainActivity.mapFragment
									.get(CashTransfer.class.getName()))
									.CallGetBankAccList(acctnoItem.ACCTNO,
											InternalCashTransfer.this);
							if (DeviceProperties.isTablet) {
								CashTransfer.CallGetCashTransferInfo(
										acctnoItem.ACCTNO,
										InternalCashTransfer.this);
							}
						}
					}
				});
		btn_ChapNhan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (edt_SoTienChuyen.length() == 0) {
					showDialogMessage(R.string.thong_bao, R.string.ChuaNhapDL);
					return;
				}
				if (listAcc.size() > 0)
					CallInternalTransferCheck();
				else {
					showDialogMessage(R.string.thong_bao,
							R.string.chuyentienrangoai_khongcotk);
				}
			}
		});
		btn_Clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				clearField();
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public void onResume() {
		super.onResume();
		cashTransfer.callGetTransDesc(CashTransfer.trans, key_trans, this,
				CashTransfer.GETTRANSDESC);
		clearField();
		changeSenderAfacctno();
	}

	@Override
	public void onShowed() {
		super.onShowed();
		if (notifyChangeAfacctno) {
			changeSenderAfacctno();
			notifyChangeAfacctno = false;
		}
	}

	/**
	 * chọn theo tiểu khoản đang chọn của chương trình
	 * spn_SenderAfacctno.setSelection(listSender
	 * .indexOf(StaticObjectManager.acctnoItem));
	 */
	private void changeSenderAfacctno() {
		spn_SenderAfacctno.setSelection(listSender
				.indexOf(StaticObjectManager.acctnoItem));
	}

	@Override
	public void refresh() {
		super.refresh();
		UpdateListAcc();
		if (this.isResumed()) {
			CallFindRecAcctnoDetail();
			clearField();
		}
	}

	private void clearField() {
		edt_SoTienChuyen.setText(StringConst.EMPTY);
		edt_NoiDung.setText(StringConst.EMPTY);
		if (DeviceProperties.isTablet) {
			tv_sotiendichvu.setText(StringConst.EMPTY);
			tv_tongtien.setText(StringConst.EMPTY);
		}
	}

	private void CallFindRecAcctnoDetail() {
		if (spn_SenderAfacctno.getContextMenu().getSelectedItem() == null
				|| spn_TKNhan.getContextMenu().getSelectedItem() == null) {
			return;
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_FindRecAcctnoDetail));
		}
		{
			list_key.add("transferType");
			list_value.add(INTERNALTRANSFERTYPE);
		}
		{
			list_key.add("pv_SourceAfacctno");
			list_value.add(((AcctnoItem) spn_SenderAfacctno.getContextMenu()
					.getSelectedItem()).ACCTNO);
		}
		{
			list_key.add("pv_Acctno");
			list_value.add(((BankAccItem) spn_TKNhan.getContextMenu()
					.getSelectedItem()).REG_ACCTNO);
		}
		StaticObjectManager.connectServer.callHttpPostService(
				FINDRECACCTNODETAIL, this, list_key, list_value);
	}

	protected void CallInternalTransferCheck() {
		if (acctnoDetail != null) {
			List<String> list_key = new ArrayList<String>();
			List<String> list_value = new ArrayList<String>();
			{
				list_key.add("link");
				list_value
						.add(getStringResource(R.string.controller_InternalTransferCheck));
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
				list_value.add(edt_SoTienChuyen.getText().toString());
			}
			{
				list_key.add("Desc");
				list_value.add(edt_NoiDung.getText().toString());
			}
			{
				list_key.add("SenderCustodyCd");
				list_value.add(acctnoDetail.SenderCustodyCd);
			}
			{
				list_key.add("SenderName");
				list_value.add(acctnoDetail.SenderName);
			}

			StaticObjectManager.connectServer.callHttpPostService(
					INTERNALTRANSFERCHECK, this, list_key, list_value);
			btn_ChapNhan.setEnabled(false);
		}
	}

	private void displayInfoBeneficiary() {
		if (acctnoDetail != null) {
			tv_SoDu.setText(acctnoDetail.CashAvailable);
			tv_NguoiNhan.setText(acctnoDetail.BeneficiaryName);
			tv_SoLuuKyNhan.setText(acctnoDetail.BeneficiaryCustodyCd);
			if (DeviceProperties.isTablet) {
				tv_SenderCustody.setText(acctnoDetail.SenderCustodyCd);
			}
			clearField();
		}
	}

	private void clearInfoBeneficiary() {
		if (acctnoDetail != null) {
			tv_SoDu.setText(StringConst.EMPTY);
			tv_NguoiNhan.setText(StringConst.EMPTY);
			tv_SoLuuKyNhan.setText(StringConst.EMPTY);
			if (DeviceProperties.isTablet) {
				tv_SenderCustody.setText(StringConst.EMPTY);
			}
			clearField();
		}
	}

	protected void UpdateListAcc() {
		listAcc.clear();
		if (StaticObjectManager.listBankAcc != null) {
			listAcc.addAll(StaticObjectManager.listBankAcc.listAcc);
		}
		spn_TKNhan.setChoises(listAcc);
		// gọi hàm CallFindRecAcctnoDetail() để lấy số dư tiền
		// CallFindRecAcctnoDetail();

	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		notifyChangeAfacctno = true;
		if (isResumed()) {
			changeSenderAfacctno();
		}

	}

	@Override
	protected void processErrorOther(String key, ResultObj rObj) {
		super.processErrorOther(key, rObj);
		switch (key) {
		case CashTransfer.GETBANKACCLIST:
			break;
		case FINDRECACCTNODETAIL:
			clearInfoBeneficiary();
			break;
		case INTERNALTRANSFERCHECK:
			break;

		default:
			break;
		}
	}

	@Override
	protected void processNull(String key) {
		super.processNull(key);
	}

	@Override
	protected void isReceivedResponse(String key) {
		super.isReceivedResponse(key);
		switch (key) {
		case INTERNALTRANSFERCHECK:
			btn_ChapNhan.setEnabled(true);
			break;

		default:
			break;
		}
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		switch (key) {
		case CashTransfer.GETBANKACCLIST:
			if (rObj.obj != null) {
				StaticObjectManager.listBankAcc = (BankAccList) rObj.obj;
				refresh();
			}
			UpdateListAcc();
			break;
		case FINDRECACCTNODETAIL:
			if (rObj.obj != null) {
				acctnoDetail = ((AcctnoDetail) rObj.obj);
				displayInfoBeneficiary();
			}
			break;
		case INTERNALTRANSFERCHECK:
			if (rObj.obj != null) {
				acctnoDetail = ((AcctnoDetail) rObj.obj);
				showInternalCashTransferDetail(acctnoDetail);
			}
			break;
		case CashTransfer.GETCASHTRANSFERINFO:
			if (rObj.obj != null) {
				CashTransferInfo1 cashTransferInfo = ((CashTransferInfo1) rObj.obj);
				displayBalance(cashTransferInfo);
			}
			break;
		case CashTransfer.GETTRANSFERFEEANDTOTAL:
			if (rObj.obj != null) {
				String[] array = (String[]) rObj.obj;
				displayFeeAndTotal(array[0], array[1]);
			}
			break;
		case CashTransfer.GETTRANSDESC:
			if (rObj.obj != null) {
				tv_transferdesc.setText((String) rObj.obj);
			}
			break;
		default:
			break;
		}
	}

	private void showInternalCashTransferDetail(AcctnoDetail pr_acctnodetail) {
		mainActivity.sendArgumentToFragment(
				InternalCashTransferConfirm.class.getName(), pr_acctnodetail);
		mainActivity.navigateFragment(InternalCashTransferConfirm.class
				.getName());
	}

	private void displayBalance(CashTransferInfo1 cashTransferInfo) {
		tv_tienungtruoc.setText(cashTransferInfo.getADVAMT());
		tv_phiungdutinh.setText(cashTransferInfo.getFEEAMT());
		tv_tienmat.setText(cashTransferInfo.getBALANCE());
		tv_sotientoidaduocchuyen.setText(cashTransferInfo.getAVLWITHDRAW());
	}

	private void displayFeeAndTotal(String fee, String total) {
		tv_sotiendichvu.setText(fee);
		tv_tongtien.setText(total);
	}

	@Override
	public void getArgument(Object obj) {
		super.getArgument(obj);
	}

}
