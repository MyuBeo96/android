package com.fss.mobiletrading.view;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.object.AuthorizationInfoItem;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AuthorizationInfoItemView extends LinearLayout {

	TextView tv_custodycd;
	TextView tv_custid;
	TextView tv_fullname;
	TextView tv_idcard;
	TextView tv_fromdate;
	TextView tv_todate;

	public AuthorizationInfoItemView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.authorization_info_item, this);
		tv_custodycd = (TextView) findViewById(R.id.text_authorizationinfo_item_custodycd);
		tv_custid = (TextView) findViewById(R.id.text_authorizationinfo_item_custid);
		tv_fullname = (TextView) findViewById(R.id.text_authorizationinfo_item_fullname);
		tv_idcard = (TextView) findViewById(R.id.text_authorizationinfo_item_idcard);
		tv_fromdate = (TextView) findViewById(R.id.text_authorizationinfo_item_fromdate);
		tv_todate = (TextView) findViewById(R.id.text_authorizationinfo_item_todate);
	}

	public void setView(AuthorizationInfoItem item) {
		tv_custodycd.setText(item.CUSTODYCD);
		tv_custid.setText(item.CUSTID);
		tv_fullname.setText(item.FULLNAME);
		tv_idcard.setText(item.IDCODE);
		tv_fromdate.setText(item.VALDATE);
		tv_todate.setText(item.EXPDATE);
	}

}
