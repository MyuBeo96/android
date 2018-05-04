package com.fss.mobiletrading.function.cashtransfer;

import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.object.BankAccItem;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;

public class TransferListItemView extends LinearLayout {

	static final String space = ": ";
	TextView tv_sequenceNumber;
	TextView tv_beneficiaryname;
	TextView tv_account;
	TextView tv_subaccount;
	TextView tv_bank;
	TextView tv_Branch;
	TextView tv_City;
	LinearLayout lay_more;

	public TransferListItemView(Context context) {
		super(context);
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.cashtransferlist_item, this);
		tv_sequenceNumber = (TextView) findViewById(R.id.text_sequencenumber);
		tv_beneficiaryname = (TextView) findViewById(R.id.text_beneficiaryname);
		tv_account = (TextView) findViewById(R.id.text_account);
		tv_subaccount = (TextView) findViewById(R.id.text_subaccount);
		tv_bank = (TextView) findViewById(R.id.text_bank);
		tv_City = (TextView) findViewById(R.id.text_city);
		tv_Branch = (TextView) findViewById(R.id.text_branch);
		lay_more = (LinearLayout) findViewById(R.id.lay_bankaccitem_more);

	}

	public void setView(BankAccItem item, boolean expand, int position) {
		Resources r = getResources();
		tv_beneficiaryname.setText(item.REG_BENEFICARY_NAME);
		if (DeviceProperties.isTablet) {
			tv_sequenceNumber.setText((position + 1) + "");
			// kiểu nội bộ
			if (item.TYPE.equals("0")) {
				tv_account.setText(item.REG_CUSTODYCD);
				tv_subaccount.setText(item.REG_ACCTNO);
				tv_subaccount.setVisibility(VISIBLE);
				tv_bank.setVisibility(GONE);
				tv_Branch.setVisibility(GONE);
				tv_City.setVisibility(GONE);

			} else { // kiểu chuyển tiền ra ngoài
				tv_account.setText(item.REG_ACCTNO);
				tv_bank.setText(item.REG_BENEFICARY_INFO);
				tv_Branch.setText(item.CITYBANK);
				tv_City.setText(item.CITYEF);
				tv_subaccount.setVisibility(GONE);
				tv_bank.setVisibility(VISIBLE);
				tv_Branch.setVisibility(VISIBLE);
				tv_City.setVisibility(VISIBLE);
			}
		} else {
			// kiểu nội bộ
			if (item.TYPE.equals("0")) {
				tv_account.setText(item.REG_CUSTODYCD);
				tv_subaccount.setText(r.getString(R.string.TieuKhoan) + space
						+ item.REG_ACCTNO);
			} else { // kiểu chuyển tiền ra ngoài
				tv_account.setText(item.REG_ACCTNO);
				tv_subaccount.setText(r.getString(R.string.NganHang) + space
						+ item.REG_BENEFICARY_INFO);
			}
			if (expand) {
				lay_more.setVisibility(VISIBLE);
				tv_Branch.setText(r.getString(R.string.ChiNhanh) + space
						+ item.CITYBANK);
				tv_City.setText(r.getString(R.string.ThanhPho) + space
						+ item.CITYEF);
			} else {
				lay_more.setVisibility(GONE);
			}
		}
	}
}
