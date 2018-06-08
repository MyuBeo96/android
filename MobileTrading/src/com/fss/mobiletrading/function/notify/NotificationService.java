package com.fss.mobiletrading.function.notify;

import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fscuat.mobiletrading.MSTradeAppConfig;

public class NotificationService {
	static final String DeviceType = "ANDROID";

	public static void CallListNotify(String token, String username,
			String lastSeq, INotifier notifier, String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetList);
		}
		{
			list_key.add("token");
			list_value.add(token);
		}
		{
			list_key.add("username");
			list_value.add(username);
		}
		{
			list_key.add("lastSeq");
			list_value.add(lastSeq);
		}

		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}

	public static void CallUnRead(String token, String deviceID, String username,
			INotifier notifier, String key) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetUnRead);
		}
		{
			list_key.add("token");
			list_value.add(token);
		}
		{
			list_key.add("username");
			list_value.add(username);
		}
		{
			list_key.add("device_id");
			list_value.add(deviceID);
		}
		{
			list_key.add("deviceType");
			list_value.add(DeviceType);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}
}
