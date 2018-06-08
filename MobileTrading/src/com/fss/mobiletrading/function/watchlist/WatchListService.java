package com.fss.mobiletrading.function.watchlist;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fscuat.mobiletrading.MSTradeAppConfig;
import com.fscuat.mobiletrading.R;

public class WatchListService {
	public static void CallGetAllWatchListDefault(String lastSequence,
			String criteriaId, String marketId, INotifier notifier,
			String pr_key) {
		String key = pr_key + StringConst.AND + criteriaId + StringConst.AND
				+ marketId;
		CallGetAllWatchList(lastSequence, criteriaId, marketId, null, notifier,
				key);
	}

	public static void CallGetAllWatchListDefaultPeriodic(String lastSequence,
			String criteriaId, String marketId, INotifier notifier,
			String pr_key) {
		String key = pr_key + StringConst.AND + criteriaId + StringConst.AND
				+ marketId;
		CallGetAllWatchList(lastSequence, criteriaId, marketId, null, notifier,
				key);
	}

	public static void CallGetWatchListBySyms(String criteriaId,
			String marketId, String syms, INotifier notifier, String pr_key) {
		String key = pr_key + StringConst.AND + criteriaId + StringConst.AND
				+ marketId;
		CallGetAllWatchList(null, criteriaId, marketId, syms, notifier, key);
	}

	public static void CallGetDefaultWatchList(INotifier notifier, String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetDefaultWatchList);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}

	public static void CallRmFrWL(String symbol, String id, INotifier notifier,
			String key) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_RmFrWL);
		}
		{
			list_key.add("symbol");
			list_value.add(symbol);
		}
		{
			list_key.add("Id");
			list_value.add(id);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}

	private static void CallGetAllWatchList(String lastSequence,
			String criteriaId, String marketId, String symbols,
			INotifier notifier, String key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetAllWatchListDefault);
		}
		{
			list_key.add("lastSeq");
			list_value.add(lastSequence);
		}
		{
			list_key.add("criteriaId");
			list_value.add(criteriaId);
		}
		{
			list_key.add("marketId");
			list_value.add(marketId);
		}
		{
			list_key.add("symbols");
			list_value.add(symbols);
		}
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}

	public static void CallGetWLSymbols(String criteriaId, String marketId,
			INotifier notifier, String pr_key) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_GetWLSymbols);
		}
		{
			list_key.add("criteriaId");
			list_value.add(criteriaId);
		}
		{
			list_key.add("marketId");
			list_value.add(marketId);
		}
		String key = pr_key + StringConst.AND + criteriaId + StringConst.AND
				+ marketId;
		StaticObjectManager.connectServer.callHttpPostService(key, notifier,
				list_key, list_value);
	}

}
