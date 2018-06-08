package com.fss.mobiletrading.function.cashtransfer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fscuat.mobiletrading.Login;
import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.design.LabelContentLayout;
import com.fscuat.mobiletrading.design.MySpinner;
import com.fscuat.mobiletrading.design.NumberEditText;

public class SCCashTransfer extends BankCashTransfer {
	static String key_trans = "cashtransMSBlNotice";

	public static SCCashTransfer newInstance(MainActivity mActivity) {

		SCCashTransfer self = new SCCashTransfer();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.SCTransfer);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup paramViewGroup,
			Bundle paramBundle) {
		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View localView = inflater.inflate(R.layout.sccashtransfer,
				paramViewGroup, false);
		initView(localView);
		initialise();
		initListener();
		return localView;
	}

	private void initView(View view) {
		this.edt_SoTienChuyen = ((NumberEditText) view
				.findViewById(R.id.edt_CToutSC_SoTienChuyen));
		this.edt_Desc = ((LabelContentLayout) view
				.findViewById(R.id.edt_CToutSC_NoiDung));
		this.spn_accountbank = ((MySpinner) view
				.findViewById(R.id.spn_CToutSC_SoTKNH));
		this.tv_cashAvailable = ((LabelContentLayout) view
				.findViewById(R.id.text_CToutSC_SoDu));
		this.tv_bank = ((LabelContentLayout) view
				.findViewById(R.id.text_CToutSC_NganHang));
		this.tv_beneficiaryName = ((LabelContentLayout) view
				.findViewById(R.id.text_CToutSC_NguoiNhan));
		this.tv_branch = ((LabelContentLayout) view
				.findViewById(R.id.text_CToutSC_PGD));
		this.tv_city = ((LabelContentLayout) view
				.findViewById(R.id.text_CToutSC_TinhThanhPho));
		this.btn_ChapNhan = ((Button) view
				.findViewById(R.id.btn_CToutSC_ChapNhan));
		this.btn_Clear = ((Button) view.findViewById(R.id.btn_CToutSC_Clear));
		spn_SenderAfacctno = (MySpinner) view
				.findViewById(R.id.spn_CToutSC_sender);
		tv_SenderCustody = ((LabelContentLayout) view
				.findViewById(R.id.text_sccashtransfer_SenderCustodyCd));
		lay_switch = (RelativeLayout) view
				.findViewById(R.id.text_sccashtransfer_switch);
		tv_tienungtruoc = ((LabelContentLayout) view
				.findViewById(R.id.text_sccashtransfer_advancecash));
		tv_phiungdutinh = ((LabelContentLayout) view
				.findViewById(R.id.text_sccashtransfer_phiungdutinh));
		tv_tienmat = ((LabelContentLayout) view
				.findViewById(R.id.text_sccashtransfer_tienmat));
		tv_sotiendichvu = ((LabelContentLayout) view
				.findViewById(R.id.text_sccashtransfer_sotiendichvu));
		tv_tongtien = ((LabelContentLayout) view
				.findViewById(R.id.text_sccashtransfer_amounttotal));
		tv_transferdesc = (TextView) view.findViewById(R.id.text_transferdesc);
		tv_sotientoidaduocchuyen = ((LabelContentLayout) view
				.findViewById(R.id.text_CToutSC_sotientoidaduocchuyen));
		Common.setupUI(view.findViewById(R.id.SCcashtransfer), getActivity());
	}

	public void onResume() {
		super.onResume();
		setBeneAcctBank();
	}

	protected void setBeneAcctBank() {
		// CallGetBankAccList(StaticObjectManager.acctnoItem.ACCTNO);
		listTKNH.clear();
		if (StaticObjectManager.listBankAcc != null) {
			listTKNH.addAll(StaticObjectManager.listBankAcc.listBankMSB);
		}
		spn_accountbank.setChoises(listTKNH);
		// gọi hàm CallFindConBankAccDetail() để lấy số dư tiền
		CallFindConBankAccDetail();
	}

	@Override
	public String getKey_trans() {
		return key_trans;
	}

	@Override
	public void refresh() {
		super.refresh();
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		setBeneAcctBank();
	}

}
