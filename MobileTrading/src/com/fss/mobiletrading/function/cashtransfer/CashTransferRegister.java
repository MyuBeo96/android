package com.fss.mobiletrading.function.cashtransfer;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.design.MyContextMenu;
import com.fscuat.mobiletrading.design.MySpinner;

public class CashTransferRegister extends AbstractFragment {

	String INTERNAL_TRANSFER_REGISTER;
	String SC_TRANSFER_REGISTER;
	String BANK_TRANSFER_REGISTER;

	MySpinner spn_switch;
	List<String> list_spinner;
	LinearLayout lay_internaltransferregister;
	LinearLayout lay_sctransferregister;
	LinearLayout lay_banktransferregister;
	AbstractFragment internalCashTransferRegister;
	AbstractFragment scCashTransferRegister;
	AbstractFragment bankCashTransferRegister;

	public static CashTransferRegister newInstance(MainActivity mActivity) {
		CashTransferRegister sefl = new CashTransferRegister();
		sefl.mainActivity = mActivity;
		sefl.TITLE = mActivity.getStringResource(R.string.CashTransferRegister);
		return sefl;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.cashtransferregister, container,
				false);
		spn_switch = (MySpinner) view
				.findViewById(R.id.spn_cashtransferregister);
		lay_internaltransferregister = (LinearLayout) view
				.findViewById(R.id.lay_cashtransferregister_container);
		initData();
		initListener();
		return view;
	}

	private void initData() {

		INTERNAL_TRANSFER_REGISTER = getStringResource(R.string.InternalTransfer);
		SC_TRANSFER_REGISTER = getStringResource(R.string.SCTransfer);
		BANK_TRANSFER_REGISTER = getStringResource(R.string.BankTransfer);
		if (list_spinner == null) {
			list_spinner = new ArrayList<String>();
			list_spinner.add(INTERNAL_TRANSFER_REGISTER);
			list_spinner.add(SC_TRANSFER_REGISTER);
			list_spinner.add(BANK_TRANSFER_REGISTER);
		}
		spn_switch.setChoises(list_spinner);
		if (internalCashTransferRegister == null) {
			internalCashTransferRegister = mainActivity.mapFragment
					.get(InternalCashTransferRegister.class.getName());

		}
		if (scCashTransferRegister == null) {
			scCashTransferRegister = mainActivity.mapFragment
					.get(SCCashTransferRegister.class.getName());
		}
		if (bankCashTransferRegister == null) {
			bankCashTransferRegister = mainActivity.mapFragment
					.get(BankCashTransferRegister.class.getName());
		}
	}

	private void initListener() {

		spn_switch
				.setOnItemSelectedListener(new MyContextMenu.OnItemSelectedListener() {

					@Override
					public void onItemSelect(Object obj, int position) {
						if (obj instanceof String) {
							String str = (String) obj;
							if (str.equals(INTERNAL_TRANSFER_REGISTER)) {
								mainActivity.displayFragment(
										InternalCashTransferRegister.class
												.getName(),
										R.id.lay_cashtransferregister_container);
							}
							if (str.equals(SC_TRANSFER_REGISTER)) {
								mainActivity.displayFragment(
										SCCashTransferRegister.class.getName(),
										R.id.lay_cashtransferregister_container);
							}
							if (str.equals(BANK_TRANSFER_REGISTER)) {
								mainActivity.displayFragment(
										BankCashTransferRegister.class
												.getName(),
										R.id.lay_cashtransferregister_container);
							}
						}

					}
				});
	}

	@Override
	public void onResume() {
		super.onResume();
		spn_switch.setSelection(0);
	}

	@Override
	public void onShowed() {
		super.onShowed();
		internalCashTransferRegister.onShowed();
		scCashTransferRegister.onShowed();
		bankCashTransferRegister.onShowed();
	}

	@Override
	public void onHide() {
		super.onHide();
		internalCashTransferRegister.onHide();
		scCashTransferRegister.onHide();
		bankCashTransferRegister.onHide();
	}

	@Override
	public void onPause() {
		super.onPause();
		mainActivity.getSupportFragmentManager().beginTransaction()
				.remove(internalCashTransferRegister).commit();
		mainActivity.getSupportFragmentManager().beginTransaction()
				.remove(scCashTransferRegister).commit();
		mainActivity.getSupportFragmentManager().beginTransaction()
				.remove(bankCashTransferRegister).commit();
	}

	@Override
	protected void process(String key, ResultObj rObj) {

	}

	public void onSwitchClickListener() {
		spn_switch.performClick();
	}

}
