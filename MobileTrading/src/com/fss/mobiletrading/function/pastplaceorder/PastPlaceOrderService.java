package com.fss.mobiletrading.function.pastplaceorder;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fscuat.mobiletrading.MSTradeAppConfig;


import java.util.ArrayList;
import java.util.List;


public class PastPlaceOrderService {
	public static void CallPastPlaceOrder(String fromDate, String toDate,
										  String acctno, INotifier notifier, String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_PastPlaceOrders);
		}
		{
			list_key.add("fromDate");
			list_value.add(fromDate);
		}
		{
			list_key.add("toDate");
			list_value.add(toDate);
		}
		{
			list_key.add("Afacctno");
			list_value.add(acctno);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}

	public static void CallPastPlaceOrder(String fromDate, String toDate,
			String acctno, String Symbol, INotifier notifier, String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_PastPlaceOrders);
		}
		{
			list_key.add("fromDate");
			list_value.add(fromDate);
		}
		{
			list_key.add("toDate");
			list_value.add(toDate);
		}
		{
			list_key.add("Afacctno");
			list_value.add(acctno);
		}
		{
			list_key.add("Symbol");
			list_value.add(Symbol);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}
}
