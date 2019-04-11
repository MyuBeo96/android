package com.fss.mobiletrading.allocateguarantee;

import com.fss.mobiletrading.connector.IConnectServer;
import com.fss.mobiletrading.interfaces.INotifier;
import com.tcscuat.mobiletrading.MSTradeAppConfig;

import java.util.ArrayList;
import java.util.List;

public class AllocateGuaranteeService {
	public static void getAllocateT0(String pv_afacctno, String pv_amt,
									 INotifier notifier, String key, IConnectServer conn) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_getAllocateT0); // /MBR/AllocateT0
		}

		{
			list_key.add("afAcctno");
			list_value.add(pv_afacctno);
		}
		{
			list_key.add("Amount");
			list_value.add(pv_amt);
		}

		conn.callHttpPostService(key, notifier, list_key, list_value);
	}

	public static void doGrantT0(String pv_Desc, INotifier notifier,
								 String key, IConnectServer conn) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_doGrantT0); // /MBR/GrantT0
		}

		{
			list_key.add("Desc");
			list_value.add(pv_Desc);
		}

		conn.callHttpPostService(key, notifier, list_key, list_value);
	}
	public static void doGrantT0(String pv_Desc, String symbolAllowed, INotifier notifier,
								 String key, IConnectServer conn) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_doGrantT0); // /MBR/GrantT0
		}

		{
			list_key.add("Desc");
			list_value.add(pv_Desc);
		}
		{
			list_key.add("Symbolamt");
			list_value.add(symbolAllowed);
		}

		conn.callHttpPostService(key, notifier, list_key, list_value);
	}

}
