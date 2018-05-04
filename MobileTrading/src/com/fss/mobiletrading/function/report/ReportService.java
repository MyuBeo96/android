package com.fss.mobiletrading.function.report;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.interfaces.INotifier;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.design.LinearLayoutEdittext;

public class ReportService {
	public static void CallGetDataSource(String code, String filter,
			String lastSeq, INotifier notifier, String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetDataSource);
		}
		{
			list_key.add("code");
			list_value.add(code);
		}
		{
			list_key.add("filter");
			list_value.add(filter);
		}
		{
			list_key.add("lastSeq");
			list_value.add(lastSeq);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}

	public static void CallGetBRList(INotifier notifier, String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetBRList);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}

	public static boolean CallReport(String rptName, String rptParams,
			String rptValues, INotifier notifier, String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_Report);
		}
		{
			list_key.add("rptName");
			list_value.add(rptName);
		}
		{
			list_key.add("rptParams");
			list_value.add(rptParams);
		}
		{
			list_key.add("rptValues");
			list_value.add(rptValues);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value, true);
		return true;
	}
}
