package com.fss.mobiletrading.function.guaranteelist;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.connector.IConnectServer;
import com.fss.mobiletrading.interfaces.INotifier;
import com.msbuat.mobiletrading.MSTradeAppConfig;

public class GuaranteeListService {
	public static void getGuaranteeList(String pv_afacctno, INotifier notifier, String key, IConnectServer conn){
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_getGuaranteeList);
		}
		{
			list_key.add("afAcctno");
			list_value.add(pv_afacctno);
		}
		conn.callHttpPostService(key, notifier, list_key, list_value);
	}
}
