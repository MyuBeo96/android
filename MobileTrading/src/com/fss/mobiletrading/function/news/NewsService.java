package com.fss.mobiletrading.function.news;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.AppData;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.service.MTradingService;
import com.fscuat.mobiletrading.Login;
import com.fscuat.mobiletrading.MSTradeAppConfig;

public class NewsService extends MTradingService {
	public void CallGetAllNews(INotifier notifier, String key) {
		StaticObjectManager.connectServer.callHttpGetService(key, notifier,
				MSTradeAppConfig.controller_GetAllNew);
	}

	public void CallGetNextNews(String lastID, String lang, INotifier notifier,
			String key) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetNextNews);
		}
		{
			list_key.add("node");
			list_value.add(lastID);
		}
		{
			list_key.add("lang");
			list_value.add(AppData.language);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}
}
