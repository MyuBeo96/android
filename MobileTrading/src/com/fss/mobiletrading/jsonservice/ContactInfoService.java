package com.fss.mobiletrading.jsonservice;

import android.util.Log;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.ContactInfoItem;
import com.fss.mobiletrading.object.ResultObj;

public class ContactInfoService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {

		ContactInfoItem contact = new ContactInfoItem(DT.getString("facebook"),
				DT.getString("skype"), DT.getString("yahoo"),
				DT.getString("email"), DT.getString("phone"));
		return new ResultObj(EC, EM, contact);
	}
}
