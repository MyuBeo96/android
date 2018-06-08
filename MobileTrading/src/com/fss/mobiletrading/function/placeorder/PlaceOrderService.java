package com.fss.mobiletrading.function.placeorder;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fscuat.mobiletrading.MSTradeAppConfig;
import com.fscuat.mobiletrading.R;

public class PlaceOrderService {
	public static boolean CallCheckOrder(String afacctno, String custodycd,
			String side, String symbol, String pricetype, String qtty,
			String price, String splitQtty, INotifier notifier, String key) {

		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_CheckOrder);
		}
		{
			list_key.add("AFACCTNO");
			list_value.add(afacctno);
		}
		{
			list_key.add("CUSTODYCD");
			list_value.add(custodycd);
		}
		{
			list_key.add("SIDE");
			list_value.add(side);
		}
		{
			list_key.add("SYMBOL");
			list_value.add(symbol);
		}
		{
			list_key.add("PRICETYPE");
			list_value.add(pricetype);
		}
		{
			list_key.add("QTTY");
			list_value.add(qtty);
		}
		{
			list_key.add("PRICE");
			list_value.add(price);
		}
		{
			list_key.add("SplitQtty");
			list_value.add(splitQtty);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
		return true;
	}

	public static boolean CallCheckGTCOrder(String afacctno, String custodycd,
			String side, String symbol, String pricetype, String qtty,
			String price, String fromdate, String todate, INotifier notifier,
			String key) {

		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_CheckGTCOrder);
		}
		{
			list_key.add("AFACCTNO");
			list_value.add(afacctno);
		}
		{
			list_key.add("CUSTODYCD");
			list_value.add(custodycd);
		}
		{
			list_key.add("SIDE");
			list_value.add(side);
		}
		{
			list_key.add("SYMBOL");
			list_value.add(symbol);
		}
		{
			list_key.add("PRICETYPE");
			list_value.add(pricetype);
		}
		{
			list_key.add("QTTY");
			list_value.add(qtty);
		}
		{
			list_key.add("PRICE");
			list_value.add(price);
		}
		{
			list_key.add("fromDate");
			list_value.add(fromdate);
		}
		{
			list_key.add("toDate");
			list_value.add(todate);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
		return true;
	}

	public static boolean CallFindStock(String symbol, String side,
			String price, String qty, String ordertype, String afacctno,
			INotifier notifier, String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_FindStock);
		}
		{
			list_key.add("symbol");
			list_value.add(symbol);
		}
		{
			list_key.add("side");
			list_value.add(side);
		}
		{
			list_key.add("price");
			list_value.add(price);
		}
		{
			list_key.add("qty");
			list_value.add(qty);
		}
		{
			list_key.add("ordertype");
			list_value.add(ordertype);
		}
		{
			list_key.add("afacctno");
			list_value.add(afacctno);
		}

		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
		return true;
	}

	public static boolean CallGetExchangebysymbol(String symbol,
			INotifier notifier, String key) {
		if (symbol != null && symbol.length() > 0) {
			List<String> list_key = new ArrayList<String>();
			List<String> list_value = new ArrayList<String>();
			{
				list_key.add("link");
				list_value.add(MSTradeAppConfig.controller_GetExchangebysymbol);
			}
			{
				list_key.add("symbol");
				list_value.add(symbol);
			}
			StaticObjectManager.connectServer.callHttpPostService(key,
					notifier, list_key, list_value);
			return true;
		}
		return false;
	}

	public static boolean CallGetStocksByAffaccno(String afacctno,
			INotifier notifier, String key) {

		if (afacctno == null || afacctno.length() <= 0) {
			return false;
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetStocksByAffaccno);
		}
		{
			list_key.add("Afacctno");
			list_value.add(afacctno);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
		return true;
	}
}
