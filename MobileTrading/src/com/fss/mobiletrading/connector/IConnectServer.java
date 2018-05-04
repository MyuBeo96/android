package com.fss.mobiletrading.connector;

import java.util.List;

import com.fss.mobiletrading.interfaces.INotifier;

public interface IConnectServer {
	public void callHttpPostService(String key, INotifier notifier,
			List<String> list_key, List<String> list_value);

	public void callHttpGetService(String key, INotifier iNotifier, String link);
}
