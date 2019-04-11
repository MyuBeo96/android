package com.fss.mobiletrading.function.balance;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.interfaces.INotifier;
import com.tcscuat.mobiletrading.MSTradeAppConfig;

public class BalanceService {
	public static boolean CallGetMoneyInfo(String acctno, INotifier notifier,
			String key) {
		if (acctno == null || acctno.length() == 0) {
			return false;
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetMoneyInfo);
		}
		{
			list_key.add("Afacctno");
			list_value.add(acctno);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
		return true;
	}
}
