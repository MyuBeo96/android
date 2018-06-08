package com.fss.mobiletrading.function.cashtransfer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.common.Validation;
import com.fss.mobiletrading.object.ItemString2;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.TransferRegisterBankItem;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.LabelContentLayout;
import com.fscuat.mobiletrading.design.MyContextMenu;
import com.fscuat.mobiletrading.design.MySpinner;

import java.util.ArrayList;
import java.util.List;

public class BankCashTransferRegister extends AbstractFragment {
	final String FINDBRANCHS = "FindBranchsService";
	final String TRANSFERREGISTERBANK = "TransferRegisterBankService";
	final String TRANSFERREGISTERBANK2 = "SuccessService#TRANSFERREGISTERBANK2";
	Button btn_ChapNhan;
	LabelContentLayout edt_City;
	LabelContentLayout edt_NguoiNhan;
	LabelContentLayout edt_TKNganHang;
	List<ItemString2> listBeneficiaryBank;
	List<ItemString2> listBranch;
	MySpinner spn_BeneficiaryBank;
	MySpinner spn_Branch;
	/**
	 * only Tablet
	 */
	RelativeLayout lay_switch;
	/**
	 * only Tablet
	 */
	Button btn_cancel;
	CashTransferRegister cashTransferRegister;

	public static BankCashTransferRegister newInstance(MainActivity mActivity) {

		BankCashTransferRegister self = new BankCashTransferRegister();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.BankTransferRegister);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View view = inflater.inflate(R.layout.banktransfer_register, viewGroup,
				false);
		initView(view);
		initialise();
		initialiseListener();
		Common.setupUI(view.findViewById(R.id.bankcashtransfer_register),
				getActivity());
		return view;
	}

	private void initView(View view) {

		edt_NguoiNhan = ((LabelContentLayout) view
				.findViewById(R.id.edt_banktransferregis_beneficiaryname));
		edt_TKNganHang = ((LabelContentLayout) view
				.findViewById(R.id.edt_banktransferregis_accountbank));
		spn_BeneficiaryBank = ((MySpinner) view
				.findViewById(R.id.spn_banktransferregis_beneficiarybank));
		spn_Branch = ((MySpinner) view
				.findViewById(R.id.spn_banktransferregis_branch));
		edt_City = ((LabelContentLayout) view
				.findViewById(R.id.edt_banktransferregis_city));
		btn_ChapNhan = ((Button) view
				.findViewById(R.id.btn_DKCT_rangoai_ChapNhan));
		btn_cancel = (Button) view.findViewById(R.id.btn_DKCT_rangoai_Clear);
		lay_switch = (RelativeLayout) view
				.findViewById(R.id.text_bankcashtransfer_register_switch);
	}

	private void initialise() {
		if (cashTransferRegister == null) {
			cashTransferRegister = (CashTransferRegister) mainActivity
					.getFragmentByName(CashTransferRegister.class.getName());
		}
		if (listBeneficiaryBank == null) {
			listBeneficiaryBank = new ArrayList<ItemString2>();
			spn_BeneficiaryBank.setChoises(listBeneficiaryBank);
		}
		if (listBranch == null) {
			listBranch = new ArrayList<ItemString2>();
			spn_Branch.setChoises(listBranch);
		}
	}

	private void initialiseListener() {
		// for tablet
		if (DeviceProperties.isTablet) {
			lay_switch.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					cashTransferRegister.onSwitchClickListener();
				}
			});
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					clearInput();
				}
			});
		}
		edt_TKNganHang.getEditContent().addTextChangedListener(
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

						if (s.length() > 0) {
							int lastcharcode = Character.codePointAt(s,
									s.length() - 1);
							if ((lastcharcode >= 48 && lastcharcode <= 57)
									|| (lastcharcode >= 65 && lastcharcode <= 90)
									|| (lastcharcode >= 97 && lastcharcode <= 122)) {

							} else {
								s.delete(s.length() - 1, s.length());
							}
						}
					}
				});
		btn_ChapNhan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				CallTransferRegisterBank();
			}
		});
		spn_BeneficiaryBank
				.setOnItemSelectedListener(new MyContextMenu.OnItemSelectedListener() {

					@Override
					public void onItemSelect(Object obj, int position) {
						if (obj instanceof ItemString2) {
							ItemString2 item = (ItemString2) obj;
							CallFindBranchs(item.getValue());
						}
					}
				});
	}

	public void onPause() {
		super.onPause();
	}

	public void onResume() {
		super.onResume();
		clearInput();
		CallGetRegisterBank();
	}

	private void CallFindBranchs(String paramString) {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(getStringResource(R.string.controller_FindBranchs));
		}
		{
			list_key.add("pv_bankNo");
			list_value.add(paramString);
		}
		StaticObjectManager.connectServer.callHttpPostService(FINDBRANCHS,
				this, list_key, list_value);
	}

	private void CallGetRegisterBank() {
		StaticObjectManager.connectServer.callHttpGetService(
				TRANSFERREGISTERBANK, this,
				getStringResource(R.string.controller_TransferRegisterBank));
	}

	private void CallTransferRegisterBank() {

		if (spn_BeneficiaryBank.getContextMenu().getSelectedItem() == null
				|| spn_Branch.getContextMenu().getSelectedItem() == null) {
			return;
		}

		if (!Validation.isVietnamese(edt_NguoiNhan.getEditContent().getText()
				.toString())
				|| !Validation.isVietnamese(edt_City.getEditContent().getText()
						.toString())) {
			showDialogMessage(R.string.thong_bao,
					R.string.YeuCauNhapTiengVietKhongDau);
			return;
		}

		if ((edt_TKNganHang.getEditContent().getText().toString().length() > 0)
				&& (edt_NguoiNhan.getEditContent().getText().toString()
						.length() > 0)
				&& (edt_City.getEditContent().getText().toString().length() > 0)) {
			ArrayList<String> list_key = new ArrayList<String>();
			ArrayList<String> list_value = new ArrayList<String>();
			{
				list_key.add("link");
				list_value
						.add(getStringResource(R.string.controller_TransferRegisterBank));
			}
			{
				list_key.add("TransferType");
				list_value.add("1");
			}
			{
				list_key.add("AfAcctno");
				list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
			}
			{
				list_key.add("CustodyCd");
				list_value.add(StaticObjectManager.acctnoItem.CUSTODYCD);
			}
			{
				list_key.add("BeneficiaryName");
				list_value.add(edt_NguoiNhan.getEditContent().getText()
						.toString());
			}
			{
				list_key.add("ProvinceCity");
				list_value.add(edt_City.getEditContent().getText().toString());
			}
			{
				list_key.add("BeneficiaryBank");
				list_value.add(((ItemString2) spn_BeneficiaryBank
						.getContextMenu().getSelectedItem()).getValue());
			}
			{
				list_key.add("BranchCode");
				list_value.add(((ItemString2) spn_Branch.getContextMenu()
						.getSelectedItem()).getValue());
			}
			{
				list_key.add("Branch");
				list_value.add(spn_Branch.getContextMenu().getSelectedItem()
						.toString());
			}
			{
				list_key.add("BankAccountNo");
				list_value.add(edt_TKNganHang.getEditContent().getText()
						.toString());
			}
			StaticObjectManager.connectServer.callHttpPostService(
					TRANSFERREGISTERBANK2, this, list_key, list_value);
			btn_ChapNhan.setEnabled(false);
		} else {
			showDialogMessage(getStringResource(R.string.thong_bao),
					getStringResource(R.string.ChuaNhapDL));
		}
	}

	private void clearInput() {
		edt_NguoiNhan.getEditContent().getText().clear();
		edt_TKNganHang.getEditContent().getText().clear();
		edt_City.getEditContent().getText().clear();
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case FINDBRANCHS:
			if (rObj.obj != null) {
				listBranch.clear();
				listBranch.addAll((List<ItemString2>) rObj.obj);
				spn_Branch.setChoises(listBranch);
			}
			break;
		case TRANSFERREGISTERBANK:
			if (rObj.obj != null) {
				TransferRegisterBankItem transferRegisterBankItem = (TransferRegisterBankItem) rObj.obj;
				listBeneficiaryBank.clear();
				listBeneficiaryBank.addAll(transferRegisterBankItem.listTKNhan);
				spn_BeneficiaryBank.setChoises(listBeneficiaryBank);
			}
			break;
		case TRANSFERREGISTERBANK2:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			clearInput();
			break;

		default:
			break;
		}
	}

	@Override
	protected void isReceivedResponse(String key) {
		super.isReceivedResponse(key);
		btn_ChapNhan.setEnabled(true);
	}
}
