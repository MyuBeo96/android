package com.fss.mobiletrading.function.market;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.service.MTradingService;
import com.fscuat.mobiletrading.MSTradeAppConfig;

public class MarketInfoService extends MTradingService {

	public void CallControllerJIndex(INotifier notifier, String key) {
		StaticObjectManager.connectServer.callHttpGetService(key, notifier,
				MSTradeAppConfig.controller_JIndex);
	}
}
