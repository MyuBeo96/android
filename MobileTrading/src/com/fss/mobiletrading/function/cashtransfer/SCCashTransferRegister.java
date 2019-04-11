package com.fss.mobiletrading.function.cashtransfer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.FindBankAccountNoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;
import com.tcscuat.mobiletrading.design.LabelContentLayout;

import java.util.ArrayList;

public class SCCashTransferRegister extends AbstractFragment {
	final String FINDBANKACCOUNTNO = "FindBankAccountnoService";
	final String TRANSFERREGISTERDIRECT = "SuccessService#TRANSFERREGISTERDIRECT";
	Button btn_ChapNhan;
	LabelContentLayout edt_bankNo;
	FindBankAccountNoItem findBankAccountNoItem;
	LabelContentLayout tv_BeneficiaryName;
	LabelContentLayout tv_Branch;
	LabelContentLayout tv_ProvinceCity;
	/**
	 * only Tablet
	 */
	RelativeLayout lay_switch;
	/**
	 * only Tablet
	 */
	Button btn_cancel;
	CashTransferRegister cashTransferRegister;

	public static SCCashTransferRegister newInstance(MainActivity mActivity) {

		SCCashTransferRegister self = new SCCashTransferRegister();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.SCTransferRegister);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View view = inflater.inflate(R.layout.sccashtransfer_register,
				viewGroup, false);
		initView(view);
		initData();
		initListener();
		return view;
	}

	private void initView(View view) {

		edt_bankNo = ((LabelContentLayout) view
				.findViewById(R.id.edt_sctransferregis_accountbank));
		tv_BeneficiaryName = ((LabelContentLayout) view
				.findViewById(R.id.text_sctransferregis_beneficiaryname));
		tv_Branch = ((LabelContentLayout) view
				.findViewById(R.id.text_sctransferregis_branch));
		tv_ProvinceCity = ((LabelContentLayout) view
				.findViewById(R.id.text_sctransferregis_city));
		btn_ChapNhan = ((Button) view.findViewById(R.id.btn_DKCT_MSB_ChapNhan));
		btn_cancel = ((Button) view.findViewById(R.id.btn_DKCT_MSB_Clear));
		lay_switch = (RelativeLayout) view
				.findViewById(R.id.text_sccashtransfer_register_switch);
		Common.setupUI(view.findViewById(R.id.sccashtransfer_register),
				getActivity());
	}

	private void initData() {
		if (cashTransferRegister == null) {
			cashTransferRegister = (CashTransferRegister) mainActivity
					.getFragmentByName(CashTransferRegister.class.getName());
		}
	}

	private void initListener() {
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
		edt_bankNo.getEditContent().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (s.length() > 0) {
					int lastcharcode = Character.codePointAt(s, s.length() - 1);
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
				CallTransferRegisterDirect();
			}
		});
		edt_bankNo.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					if (edt_bankNo.getEditContent().length() == 0) {
						clearField();
					} else {
						CallFindBankAccountNo();
					}

				}
			}
		});
	}

	public void onPause() {
		super.onPause();
	}

	public void onResume() {
		super.onResume();
		clearField();
	}

	private void CallFindBankAccountNo() {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_FindBankAccountNo));
		}
		{
			list_key.add("pv_bankNo");
			list_value.add(edt_bankNo.getText().toString());
		}
		StaticObjectManager.connectServer.callHttpPostService(
				FINDBANKACCOUNTNO, this, list_key, list_value);
	}

	private void CallTransferRegisterDirect() {
		if ((edt_bankNo.getText().toString().length() > 0)
				&& (findBankAccountNoItem != null)) {
			ArrayList<String> list_key = new ArrayList<String>();
			ArrayList<String> list_value = new ArrayList<String>();
			{
				list_key.add("link");
				list_value
						.add(getStringResource(R.string.controller_TransferRegisterDirect));
			}
			{
				list_key.add("TransferType");
				list_value.add("2");
			}
			{
				list_key.add("CustodyCd");
				list_value.add(StaticObjectManager.acctnoItem.CUSTODYCD);
			}
			{
				list_key.add("AfAcctno");
				list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
			}
			{
				list_key.add("BeneficiaryName");
				list_value.add(findBankAccountNoItem.BeneficiaryName);
			}
			{
				list_key.add("ProvinceCity");
				list_value.add(findBankAccountNoItem.ProvinceCity);
			}
			{
				list_key.add("BranchCode");
				list_value.add(findBankAccountNoItem.BranchCode);
			}
			{
				list_key.add("Branch");
				list_value.add(findBankAccountNoItem.Branch);
			}
			{
				list_key.add("BankAccountNo");
				list_value.add(findBankAccountNoItem.BankAccountNo);
			}
			StaticObjectManager.connectServer.callHttpPostService(
					TRANSFERREGISTERDIRECT, this, list_key, list_value);
			btn_ChapNhan.setEnabled(false);
		} else {
			showDialogMessage(R.string.thong_bao, R.string.ChuaNhapDL);
		}
	}

	private void clearField() {
		edt_bankNo.setText(StringConst.EMPTY);
		tv_BeneficiaryName.setText(StringConst.EMPTY);
		tv_Branch.setText(StringConst.EMPTY);
		tv_ProvinceCity.setText(StringConst.EMPTY);
	}

	private void clearInput() {
		edt_bankNo.setText(StringConst.EMPTY);
	}

	private void DisplayView() {
		if (findBankAccountNoItem != null) {
			tv_BeneficiaryName.setText(findBankAccountNoItem.BeneficiaryName);
			tv_Branch.setText(findBankAccountNoItem.Branch);
			tv_ProvinceCity.setText(findBankAccountNoItem.ProvinceCity);
		}
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case FINDBANKACCOUNTNO:
			if (rObj.obj != null) {
				findBankAccountNoItem = ((FindBankAccountNoItem) rObj.obj);
				DisplayView();
			}
			break;
		case TRANSFERREGISTERDIRECT:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			clearField();
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
