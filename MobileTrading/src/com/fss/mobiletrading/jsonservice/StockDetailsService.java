package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.StockDetailsItem;
import com.mtrading.mobile.graph.IndexVolumeChartPoint;

public class StockDetailsService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		HashMap<String, String> hm = null;
		String sym = DT.getString("Symbol");
		MyJsonObject value = DT.getJSONObject("value");
		if (value != null) {
			hm = value.getHashMap();
		}
		StockDetailsItem item = new StockDetailsItem(sym, hm,
				DT.getString("LS"), DT.getString("changePercent"), DT,
				DT.getInt("ratioBidVol"));
		return new ResultObj(EC, EM, item);
	}
}
