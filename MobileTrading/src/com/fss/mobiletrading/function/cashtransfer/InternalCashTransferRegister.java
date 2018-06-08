package com.fss.mobiletrading.function.cashtransfer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.FindAfacctnosItem;
import com.fss.mobiletrading.object.ItemString;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.LabelContentLayout;
import com.fscuat.mobiletrading.design.MySpinner;

import java.util.ArrayList;
import java.util.List;

public class InternalCashTransferRegister extends AbstractFragment {
	final String FINDAFACCTNOS = "FindAfacctnosService";
	final String TRANSFERREGISTERINTERNAL = "SuccessService#TRANSFERREGISTERINTERNAL";
	Button btn_ChapNhan;
	LabelContentLayout edt_CustodyCd;
	FindAfacctnosItem findAfacctnosItem;
	List<ItemString> listTieuKhoan;
	MySpinner spn_AfAcctno;
	LabelContentLayout tv_BeneficiaryName;
	/**
	 * only Tablet
	 */
	RelativeLayout lay_switch;
	/**
	 * only tablet
	 */
	Button btn_cancel;
	CashTransferRegister cashTransferRegister;

	public static InternalCashTransferRegister newInstance(
			MainActivity mActivity) {

		InternalCashTransferRegister self = new InternalCashTransferRegister();
		self.mainActivity = mActivity;
		self.TITLE = mActivity
				.getStringResource(R.string.InternalTransferRegister);
		return self;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View view = inflater.inflate(R.layout.internaltransfer_register,
				viewGroup, false);
		edt_CustodyCd = ((LabelContentLayout) view
				.findViewById(R.id.edt_DKCT_noibo_SoLuuKy));
		tv_BeneficiaryName = ((LabelContentLayout) view
				.findViewById(R.id.text_DKCT_noibo_NguoiNhan));
		spn_AfAcctno = ((MySpinner) view
				.findViewById(R.id.spn_DKCT_noibo_SoTieuKhoan));
		btn_cancel = (Button) view.findViewById(R.id.btn_DKCT_noibo_Clear);
		btn_ChapNhan = ((Button) view
				.findViewById(R.id.btn_DKCT_noibo_ChapNhan));
		tv_BeneficiaryName.setEnabled(false);
		lay_switch = (RelativeLayout) view
				.findViewById(R.id.text_internalcashtransfer_register_switch);
		Common.setupUI(view.findViewById(R.id.internalcashtransfer_register),
				getActivity());
		initData();

		initListener();
		return view;
	}

	private void initData() {
		if (cashTransferRegister == null) {
			cashTransferRegister = (CashTransferRegister) mainActivity
					.getFragmentByName(CashTransferRegister.class.getName());
		}
		if (listTieuKhoan == null) {
			listTieuKhoan = new ArrayList<ItemString>();
			spn_AfAcctno.setChoises(listTieuKhoan);
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
		btn_ChapNhan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CallTransferRegisterInternal();
			}
		});
		edt_CustodyCd.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					CallFindAfacctnos();
				}
			}
		});
	}

	public void onPause() {
		super.onPause();
	}

	public void onResume() {
		super.onResume();
		ClearField();
	}

	private void CallFindAfacctnos() {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_FindAfacctnos));
		}
		{
			list_key.add("pv_CustodyCd");
			list_value.add(edt_CustodyCd.getText().toString());
		}
		StaticObjectManager.connectServer.callHttpPostService(FINDAFACCTNOS,
				this, list_key, list_value);

	}

	private void CallTransferRegisterInternal() {
		if (edt_CustodyCd.getText().toString().length() > 0) {
			if ((spn_AfAcctno.getContextMenu().getSelectedItem() != null)
					&& (findAfacctnosItem != null)) {
				ArrayList<String> list_key = new ArrayList<String>();
				ArrayList<String> list_value = new ArrayList<String>();
				{
					list_key.add("link");
					list_value
							.add(getStringResource(R.string.controller_TransferRegisterInternal));
				}
				{
					list_key.add("TransferType");
					list_value.add("0");
				}
				{
					list_key.add("AfAcctno");
					list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
				}
				{
					list_key.add("BeneficiaryName");
					list_value.add(findAfacctnosItem.BeneficiaryName);
				}
				{
					list_key.add("BeneficiaryCustodyCd");
					list_value.add(findAfacctnosItem.BeneficiaryCustodyCd);
				}
				{
					list_key.add("BeneficiaryAfacctno");
					list_value.add(((ItemString) spn_AfAcctno.getContextMenu()
							.getSelectedItem()).getValue());
				}
				{
					list_key.add("CustodyCd");
					list_value.add(StaticObjectManager.acctnoItem.CUSTODYCD);
				}
				StaticObjectManager.connectServer.callHttpPostService(
						TRANSFERREGISTERINTERNAL, this, list_key, list_value);
				btn_ChapNhan.setEnabled(false);
			} else {
				showDialogMessage(R.string.thong_bao,
						R.string.SoTaiKhoanKhongTonTai);
			}
		} else {
			showDialogMessage(R.string.thong_bao, R.string.ChuaNhapDL);
		}
	}

	private void ClearField() {
		edt_CustodyCd.setText(StringConst.EMPTY);
		tv_BeneficiaryName.setText(StringConst.EMPTY);
		listTieuKhoan.clear();
		spn_AfAcctno.setChoises(listTieuKhoan);
	}

	private void clearInput() {
		edt_CustodyCd.setText(StringConst.EMPTY);
		tv_BeneficiaryName.setText(StringConst.EMPTY);
		spn_AfAcctno.clear();
	}

	private void DisplayView() {
		if (findAfacctnosItem != null) {
			tv_BeneficiaryName.setText(findAfacctnosItem.BeneficiaryName);
			listTieuKhoan.clear();
			listTieuKhoan.addAll(findAfacctnosItem.listTKNhan);
			spn_AfAcctno.setChoises(listTieuKhoan);
		}
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case FINDAFACCTNOS:
			if (rObj.obj != null) {
				findAfacctnosItem = ((FindAfacctnosItem) rObj.obj);
				DisplayView();
				if (findAfacctnosItem.listTKNhan.size() < 0) {
					showDialogMessage(getStringResource(R.string.thong_bao),
							getStringResource(R.string.SoTaiKhoanKhongTonTai));
				}
			}
			break;
		case TRANSFERREGISTERINTERNAL:

			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM,
					new SimpleAction() {

						@Override
						public void performAction(Object obj) {
							ClearField();
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
		btn_ChapNhan.setEnabled(true);
	}
}
