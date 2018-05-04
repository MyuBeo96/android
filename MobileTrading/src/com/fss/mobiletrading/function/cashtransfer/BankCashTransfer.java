package com.fss.mobiletrading.function.cashtransfer;

import android.os.Bundle;
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
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.BankAccItem;
import com.fss.mobiletrading.object.BankAccList;
import com.fss.mobiletrading.object.ConBankAccDetail;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;
import com.msbuat.mobiletrading.design.LabelContentLayout;
import com.msbuat.mobiletrading.design.MyContextMenu.OnItemSelectedListener;
import com.msbuat.mobiletrading.design.MySpinner;
import com.msbuat.mobiletrading.design.NumberEditText;

import java.util.ArrayList;
import java.util.List;

public class BankCashTransfer extends AbstractFragment {
	private static final String BANKTRANSFERTYPE = "1";
	String key_trans = "cashtransNotice";
	final String CASHTRANSFERCHECK = "CashTransferCheckService";
	final String FINDCONBANKACCDETAIL = "FindConBankAccDetailService";
	// final String GETBANKACCLIST = "GetBankAccListService";

	ConBankAccDetail ConBankAccDetail;
	Button btn_ChapNhan;
	Button btn_Clear;
	NumberEditText edt_SoTienChuyen;
	List<BankAccItem> listTKNH;

	MySpinner spn_accountbank;
	MySpinner spn_SenderAfacctno;
	LabelContentLayout tv_bank;
	LabelContentLayout tv_beneficiaryName;
	LabelContentLayout tv_branch;
	LabelContentLayout tv_cashAvailable;
	LabelContentLayout tv_city;
	LabelContentLayout edt_Desc;
	List<AcctnoItem> listSender;

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
	boolean notifyChangeAfacctno = false;

	public static BankCashTransfer newInstance(MainActivity mActivity) {
		BankCashTransfer self = new BankCashTransfer();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.BankTransfer);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View localView = paramLayoutInflater.inflate(R.layout.bankcashtransfer,
				paramViewGroup, false);
		initView(localView);
		initialise();
		initListener();
		return localView;
	}

	private void initView(View view) {
		edt_SoTienChuyen = ((NumberEditText) view
				.findViewById(R.id.edt_CTout_amount));
		edt_Desc = ((LabelContentLayout) view.findViewById(R.id.edt_CTout_desc));
		spn_accountbank = ((MySpinner) view
				.findViewById(R.id.spn_CTout_accountbank));
		tv_cashAvailable = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_cashavailable));
		tv_bank = ((LabelContentLayout) view.findViewById(R.id.text_CTout_bank));
		tv_beneficiaryName = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_beneficiaryName));
		tv_branch = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_branch));
		tv_city = ((LabelContentLayout) view.findViewById(R.id.text_CTout_city));
		spn_SenderAfacctno = (MySpinner) view
				.findViewById(R.id.spn_CTout_Afacctno);
		btn_ChapNhan = ((Button) view.findViewById(R.id.btn_CTout_ChapNhan));
		btn_Clear = ((Button) view.findViewById(R.id.btn_CTout_Clear));
		tv_SenderCustody = ((LabelContentLayout) view
				.findViewById(R.id.text_bankcashtransfer_SenderCustodyCd));
		lay_switch = (RelativeLayout) view
				.findViewById(R.id.text_bankcashtransfer_switch);
		tv_tienungtruoc = ((LabelContentLayout) view
				.findViewById(R.id.text_bankcashtransfer_advancecash));
		tv_phiungdutinh = ((LabelContentLayout) view
				.findViewById(R.id.text_bankcashtransfer_phiungdutinh));
		tv_tienmat = ((LabelContentLayout) view
				.findViewById(R.id.text_bankcashtransfer_tienmat));
		tv_sotiendichvu = ((LabelContentLayout) view
				.findViewById(R.id.text_bankcashtransfer_sotiendichvu));
		tv_tongtien = ((LabelContentLayout) view
				.findViewById(R.id.text_bankcashtransfer_amounttotal));
		tv_transferdesc = (TextView) view.findViewById(R.id.text_transferdesc);
		tv_sotientoidaduocchuyen = ((LabelContentLayout) view
				.findViewById(R.id.text_CTout_sotientoidaduocchuyen));
		Common.setupUI(view.findViewById(R.id.chuyentienrangoai), getActivity());
	}

	protected void initialise() {
		if (cashTransfer == null) {
			cashTransfer = (CashTransfer) mainActivity
					.getFragmentByName(CashTransfer.class.getName());
		}
		if (listTKNH == null) {
			listTKNH = new ArrayList<BankAccItem>();
		}
		spn_accountbank.setChoises(listTKNH);
		if (listSender == null) {
			listSender = StaticObjectManager.loginInfo.contractList;
		}
		spn_SenderAfacctno.setChoises(listSender);
		Common.registrySeparatorNumber(edt_SoTienChuyen);
	}

	protected void initListener() {
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
										|| spn_accountbank.getContextMenu()
												.getSelectedItem() == null) {
									return;
								}
								CashTransfer.CallGetTransferFeeAndTotal(
										BANKTRANSFERTYPE, edt_SoTienChuyen
												.getText().toString(),
										ConBankAccDetail.BankID,
										((AcctnoItem) spn_SenderAfacctno
												.getContextMenu()
												.getSelectedItem()).ACCTNO,
										((BankAccItem) spn_accountbank
												.getContextMenu()
												.getSelectedItem()).REG_ACCTNO,
										BankCashTransfer.this);
							}
						}
					});
		}

		spn_SenderAfacctno
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelect(Object obj, int position) {
						if (obj instanceof AcctnoItem) {
							if (DeviceProperties.isTablet) {
								tv_SenderCustody
										.setText(StaticObjectManager.acctnoItem.CUSTODYCD);
							}
							AcctnoItem acctnoItem = (AcctnoItem) obj;
							((CashTransfer) mainActivity.mapFragment
									.get(CashTransfer.class.getName()))
									.CallGetBankAccList(acctnoItem.ACCTNO,
											BankCashTransfer.this);
							if (DeviceProperties.isTablet) {
								CashTransfer.CallGetCashTransferInfo(
										acctnoItem.ACCTNO,
										BankCashTransfer.this);
							}
						}
					}
				});
		spn_accountbank.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelect(Object obj, int position) {
				CallFindConBankAccDetail();
			}
		});

		btn_ChapNhan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (edt_SoTienChuyen.length() == 0) {
					showDialogMessage(R.string.thong_bao, R.string.ChuaNhapDL);
					return;
				}
				if (listTKNH.size() > 0)
					CallCashTransferCheck();
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

	public String getKey_trans() {
		return key_trans;
	}

	public void onResume() {
		super.onResume();
		cashTransfer.callGetTransDesc(CashTransfer.trans, getKey_trans(), this,
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
		// setListBeneficiaryAcctBank();
	}

	@Override
	public void refresh() {
		super.refresh();
		setBeneAcctBank();
		if (this.isResumed()) {
			clearField();
			CallFindConBankAccDetail();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	private void clearField() {
		edt_SoTienChuyen.getText().clear();
		edt_Desc.getEditContent().getText().clear();
		if (DeviceProperties.isTablet) {
			tv_sotiendichvu.setText(StringConst.EMPTY);
			tv_tongtien.setText(StringConst.EMPTY);
		}
	}

	protected void CallFindConBankAccDetail() {
		if (spn_SenderAfacctno.getContextMenu().getSelectedItem() == null
				|| spn_accountbank.getContextMenu().getSelectedItem() == null) {
			ConBankAccDetail = null;
			clearInfoBank();
			return;
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_FindConBankAccDetail));
		}
		{
			list_key.add("transferType");
			list_value.add(BANKTRANSFERTYPE);
		}
		{
			list_key.add("pv_SourceAfacctno");
			list_value.add(((AcctnoItem) spn_SenderAfacctno.getContextMenu()
					.getSelectedItem()).ACCTNO);
		}
		{
			list_key.add("pv_bankNo");
			list_value.add(((BankAccItem) spn_accountbank.getContextMenu()
					.getSelectedItem()).REG_ACCTNO);
		}
		{
			list_key.add("pv_bankIndex");
			list_value.add(((BankAccItem) spn_accountbank.getContextMenu()
					.getSelectedItem()).REFID);
		}
		StaticObjectManager.connectServer.callHttpPostService(
				FINDCONBANKACCDETAIL, this, list_key, list_value);
	}

	protected void CallCashTransferCheck() {
		if (spn_accountbank.getContextMenu().getSelectedItem() == null) {
			return;
		}
		try {
			List<String> list_key = new ArrayList<String>();
			List<String> list_value = new ArrayList<String>();
			{
				list_key.add("link");
				list_value
						.add(getStringResource(R.string.controller_CashTransferCheck));
			}
			{
				list_key.add("ConBankAcc");
				list_value.add(ConBankAccDetail.ConBankAcc);
			}
			{
				list_key.add("ConBankAccIndex");
				list_value.add(ConBankAccDetail.ConBankAccIndex);
			}
			{
				list_key.add("BeneficiaryName");
				list_value.add(ConBankAccDetail.BeneficiaryName);
			}
			{
				list_key.add("BeneficiaryBank");
				list_value.add(ConBankAccDetail.BeneficiaryBank);
			}
			{
				list_key.add("City");
				list_value.add(ConBankAccDetail.City);
			}
			{
				list_key.add("Branch");
				list_value.add(ConBankAccDetail.Branch);
			}
			{
				list_key.add("Afacctno");
				list_value.add(ConBankAccDetail.Afacctno);
			}
			{
				list_key.add("BeneficiaryCustodyCd");
				list_value.add(((BankAccItem) spn_accountbank.getContextMenu()
						.getSelectedItem()).REG_CUSTODYCD);
			}
			{
				list_key.add("BeneficiaryAfacctno");
				list_value.add(((BankAccItem) spn_accountbank.getContextMenu()
						.getSelectedItem()).REG_ACCTNO);
			}
			{
				list_key.add("CashAvailable");
				list_value.add(ConBankAccDetail.CashAvailable);
			}
			{
				list_key.add("Amount");
				list_value.add(edt_SoTienChuyen.getText().toString());
			}
			{
				list_key.add("Fee");
				list_value.add(ConBankAccDetail.Fee);
			}
			{
				list_key.add("Desc");
				list_value.add(edt_Desc.getEditContent().getText().toString());
			}
			{
				list_key.add("TransferType");
				list_value.add("1");
			}
			StaticObjectManager.connectServer.callHttpPostService(
					CASHTRANSFERCHECK, this, list_key, list_value);
			btn_ChapNhan.setEnabled(false);
		} catch (ClassCastException e) {
		}
	}

	private void displayInfoBank() {
		if (ConBankAccDetail != null) {
			tv_cashAvailable.getContent().setText(
					ConBankAccDetail.CashAvailable);
			tv_beneficiaryName.getContent().setText(
					ConBankAccDetail.BeneficiaryName);
			tv_bank.getContent().setText(ConBankAccDetail.BeneficiaryBank);
			tv_city.getContent().setText(ConBankAccDetail.City);
			tv_branch.getContent().setText(ConBankAccDetail.Branch);
			clearField();
		}
	}

	private void clearInfoBank() {
		tv_cashAvailable.getContent().setText(StringConst.EMPTY);
		tv_beneficiaryName.getContent().setText(StringConst.EMPTY);
		tv_bank.getContent().setText(StringConst.EMPTY);
		tv_city.getContent().setText(StringConst.EMPTY);
		tv_branch.getContent().setText(StringConst.EMPTY);
		clearField();
	}

	protected void setBeneAcctBank() {
		listTKNH.clear();
		if (StaticObjectManager.listBankAcc != null) {
			listTKNH.addAll(StaticObjectManager.listBankAcc.listBank);
		}
		spn_accountbank.setChoises(listTKNH);
		// gọi hàm CallFindConBankAccDetail() để lấy số dư tiền
		// CallFindConBankAccDetail();
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		if (isResumed()) {
			changeSenderAfacctno();
		}
		notifyChangeAfacctno = true;
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case CashTransfer.GETBANKACCLIST:
			if (rObj.obj != null) {
				StaticObjectManager.listBankAcc = (BankAccList) rObj.obj;
				refresh();
			}
			setBeneAcctBank();
			break;
		case FINDCONBANKACCDETAIL:
			if (rObj.obj != null) {
				ConBankAccDetail = ((ConBankAccDetail) rObj.obj);
				displayInfoBank();
			}
			break;
		case CASHTRANSFERCHECK:
			if (rObj.obj != null) {
				ConBankAccDetail = ((ConBankAccDetail) rObj.obj);
				showBankCashTransferDetail();
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

	@Override
	protected void processErrorOther(String key, ResultObj rObj) {
		super.processErrorOther(key, rObj);
		switch (key) {
		case CashTransfer.GETBANKACCLIST:
			break;
		case FINDCONBANKACCDETAIL:
			clearInfoBank();
			break;
		case CASHTRANSFERCHECK:
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

	@Override
	protected void isReceivedResponse(String key) {
		super.isReceivedResponse(key);
		switch (key) {
		case CASHTRANSFERCHECK:
			btn_ChapNhan.setEnabled(true);
			break;

		default:
			break;
		}

	}

	private void showBankCashTransferDetail() {
		mainActivity.sendArgumentToFragment(
				BankCashTransferConfirm.class.getName(), ConBankAccDetail);
		mainActivity.navigateFragment(BankCashTransferConfirm.class.getName());
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
