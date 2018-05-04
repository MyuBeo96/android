package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.UngtruocItem;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;

public class Advance extends AbstractFragment {
	public final String CASHADVANCECHECK = "CashAdvanceCheckService";
	public final String CASHADVANCESUBMIT = "SuccessService#1";
	public final String FINDADVANCEBYAFFACCNO = "FindAdvanceByAffaccnoService";

	Button btn_ChapNhan;
	Button btn_Clear;
	EditText edt_MaPin;
	EditText edt_SoTienNhan;
	TextView tv_PhiUngTruoc;
	TextView tv_SoTienCoTheUng;
	TextView tv_SoTienDaUng;

	UngtruocItem ungtruocInfo;
	boolean cashAdvanceCheck = false;

	public static Advance newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		Advance self = new Advance();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.Advanced);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.ungtruoc, viewGroup, false);
		initView(view);
		initData();
		initListener();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		edt_SoTienNhan = ((EditText) view
				.findViewById(R.id.edt_ungtruoc_SoTienNhan));
		edt_MaPin = ((EditText) view.findViewById(R.id.edt_ungtruoc_MaPIN));
		tv_SoTienCoTheUng = ((TextView) view
				.findViewById(R.id.text_ungtruoc_CoTheUng));
		tv_SoTienDaUng = ((TextView) view
				.findViewById(R.id.text_ungtruoc_DaUng));
		tv_PhiUngTruoc = ((TextView) view
				.findViewById(R.id.text_ungtruoc_PhiUngTruoc));
		btn_ChapNhan = ((Button) view.findViewById(R.id.btn_UngTruoc_ChapNhan));
		btn_Clear = ((Button) view.findViewById(R.id.btn_UngTruoc_Clear));
		Common.setupUI(view.findViewById(R.id.ungtruoc), getActivity());
	}

	private void initData() {
		Common.registrySeparatorNumber(edt_SoTienNhan);
	}

	private void initListener() {
		// TODO Auto-generated method stub

		btn_ChapNhan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cashAdvanceCheck) {
					if (edt_MaPin.getText().length() > 0) {
						CallCashAdvanceSubmit();
						edt_MaPin.setVisibility(EditText.GONE);
					} else {
						showDialogMessage(R.string.thong_bao, R.string.NhapPin);
					}
				} else {
					CallCashAdvanceCheck();
				}
			}
		});
		btn_Clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edt_MaPin.setText(StringConst.EMPTY);
				edt_SoTienNhan.setText(StringConst.EMPTY);
				tv_PhiUngTruoc.setText(StringConst.EMPTY);
			}
		});
		edt_SoTienNhan.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {
					CallCashAdvanceCheck();
				}
			}
		});
	}

	public void onResume() {
		super.onResume();
		CallFindAdvanceByAffaccno();
	}

	private void DisplayInfoAdvance() {
		tv_SoTienCoTheUng.setText(StringConst.EMPTY);
		tv_SoTienDaUng.setText(StringConst.EMPTY);
		tv_PhiUngTruoc.setText(StringConst.EMPTY);
		if (ungtruocInfo != null) {
			if (ungtruocInfo.IsAdvance.matches("true")) {
				tv_SoTienCoTheUng.setText(ungtruocInfo.total_available_money);
				tv_SoTienDaUng.setText(ungtruocInfo.advanced_money);
				tv_PhiUngTruoc.setText(ungtruocInfo.fee);
				edt_SoTienNhan.setEnabled(true);
				btn_ChapNhan.setEnabled(true);
				btn_Clear.setEnabled(true);
			} else {
				tv_SoTienCoTheUng.setText("-");
				tv_SoTienDaUng.setText("-");
				tv_PhiUngTruoc.setText("-");
				edt_SoTienNhan.setEnabled(false);
				btn_ChapNhan.setEnabled(false);
				btn_Clear.setEnabled(false);
				showDialogMessage(R.string.thong_bao,
						R.string.ungtruoc_message1);
			}
		}
	}

	private void DisplayCashAdvanceCheck() {
		if (ungtruocInfo != null && edt_SoTienNhan.length() > 0) {
			tv_PhiUngTruoc.setText(ungtruocInfo.fee);
			edt_MaPin.setVisibility(EditText.VISIBLE);
			cashAdvanceCheck = true;
		}
	}

	protected void CallCashAdvanceCheck() {

		if (edt_SoTienNhan.getText().length() > 0) {
			List<String> list_key = new ArrayList<String>();
			List<String> list_value = new ArrayList<String>();
			{
				list_key.add("link");
				list_value
						.add(getStringResource(R.string.controller_CashAdvanceCheck));
			}
			{
				list_key.add("receive_money");
				list_value.add(edt_SoTienNhan.getText().toString());
			}
			{
				list_key.add("Afacctno");
				list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
			}
			{
				list_key.add("total_sell_money");
				list_value.add(ungtruocInfo.total_sell_money);
			}
			{
				list_key.add("total_available_money");
				list_value.add(ungtruocInfo.total_available_money);
			}
			{
				list_key.add("advanced_money");
				list_value.add(ungtruocInfo.advanced_money);
			}
			cashAdvanceCheck = false;
			StaticObjectManager.connectServer.callHttpPostService(
					CASHADVANCECHECK, this, list_key, list_value);

		} else {
			showDialogMessage(R.string.thong_bao, R.string.ChuaNhapDL);
		}
	}

	protected void CallCashAdvanceSubmit() {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_CashAdvanceSubmit));
		}
		{
			list_key.add("receive_money");
			list_value.add(edt_SoTienNhan.getText().toString());
		}
		{
			list_key.add("Afacctno");
			list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
		}
		{
			list_key.add("total_sell_money");
			list_value.add(ungtruocInfo.total_sell_money);
		}
		{
			list_key.add("total_available_money");
			list_value.add(ungtruocInfo.total_available_money);
		}
		{
			list_key.add("advanced_money");
			list_value.add(ungtruocInfo.advanced_money);
		}
		{
			list_key.add("advance_money");
			list_value.add(ungtruocInfo.advance_money);
		}
		{
			list_key.add("fee_money");
			list_value.add(ungtruocInfo.fee_money);
		}
		{
			list_key.add("fee");
			list_value.add(ungtruocInfo.fee);
		}
		{
			list_key.add("pin");
			list_value.add(edt_MaPin.getText().toString());
		}
		StaticObjectManager.connectServer.callHttpPostService(
				CASHADVANCESUBMIT, this, list_key, list_value);
	}

	protected void CallFindAdvanceByAffaccno() {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_FindAdvanceByAffaccno));
		}
		{
			list_key.add("Afacctno");
			list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
		}
		StaticObjectManager.connectServer.callHttpPostService(
				FINDADVANCEBYAFFACCNO, this, list_key, list_value);
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		CallFindAdvanceByAffaccno();
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub

		switch (key) {
		case FINDADVANCEBYAFFACCNO:
			if (rObj.obj != null) {
				ungtruocInfo = ((UngtruocItem) rObj.obj);
				DisplayInfoAdvance();
			}
			break;
		case CASHADVANCECHECK:
			if (rObj.obj != null) {
				ungtruocInfo = ((UngtruocItem) rObj.obj);
				DisplayCashAdvanceCheck();
			}
			break;
		case CASHADVANCESUBMIT:
			edt_SoTienNhan.setText(StringConst.EMPTY);
			edt_MaPin.setText(StringConst.EMPTY);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CallFindAdvanceByAffaccno();

			break;

		default:
			break;
		}
	}

}
