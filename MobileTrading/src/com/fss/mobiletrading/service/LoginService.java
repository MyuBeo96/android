package com.fss.mobiletrading.service;

import android.telecom.ConnectionService;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.connector.ConnectServer;
import com.fss.mobiletrading.interfaces.INotifier;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.R;

public class LoginService extends MTradingService {
	public static void CallGetLinkGuide(INotifier notifier, String key) {
		callGetTransDesc(notifier, "Resource", "HDSD", "", key);
	}
	
	public static void CallGetLinkGuideBroker(INotifier notifier, String key) {
		callGetTransDesc(notifier, "Resource", "HDSDMG", "", key);
	}
	
	public static void CallGetLinkTabletGuide(INotifier notifier, String key) {
		callGetTransDesc(notifier, "Resource", "HDSDMTB", "", key);
	}
	
	public static void CallGetLinkTabletGuideBroker(INotifier notifier, String key) {
		callGetTransDesc(notifier, "Resource", "HDSDMTBMG", "", key);
	}

	public static void CallGetLinkPaymentGuide(INotifier notifier, String key) {
		callGetTransDesc(notifier, "Resource", "HDNT", "", key);
	}

	public static void CallGetErrorMessage(INotifier notifier,
			String errorcode, String lang, String key) {
		callGetTransDesc(notifier, "ErrorDef", errorcode, lang, key);
	}

	public static void CallAccountDemo(INotifier notifier, String key) {
		callGetTransDesc(notifier, "Resource", "DEMOACC", "", key);
	}

	public static void callGetTransDesc(INotifier notifier, String trans,
			String _key, String lang, String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetTransDesc);
		}
		{
			list_key.add("trans");
			list_value.add(trans);
		}
		{
			list_key.add("key");
			list_value.add(_key);
		}
		{
			list_key.add("lang");
			list_value.add(lang);
		}
		if(StaticObjectManager.connectServer == null){
			StaticObjectManager.connectServer = new ConnectServer();
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}

	public static boolean CallResetPass(String custodycd, String personId,
			String phone, String email, String lang, INotifier notifier,
			String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_ResetPass);
		}
		{
			list_key.add("custodycd");
			list_value.add(custodycd);
		}
		{
			list_key.add("personId");
			list_value.add(personId);
		}
		{
			list_key.add("phone");
			list_value.add(phone);
		}
		{

			list_key.add("email");
			list_value.add(email);
		}
		{
			list_key.add("lang");
			list_value.add(lang);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
		return true;
	}
}
