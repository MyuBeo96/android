package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import android.os.SystemClock;
import android.util.Log;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.function.market.MarketChartItem;
import com.fss.mobiletrading.object.ResultObj;

public class MarketChartService extends AbstractProcessJsonObjectService {
	@Override
	public ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		List<MarketChartItem> list = new ArrayList<MarketChartItem>();
		MyJsonArray t = null;
		long starttime = System.currentTimeMillis();
		try {
			t = DT.getJSONArray("d");
		} catch (OutOfMemoryError e) {
		}
		if (t != null) {
			int length = t.length();
			for (int i = 0; i < length; i++) {
				MyJsonObject formatedValues = t.getJSONObject(i);
				list.add(new MarketChartItem(formatedValues.getHashMap()));
			}
		}
		Log.i("hhhhhhhhhhhhhhhhhhhhh marketchart stop",
				(System.currentTimeMillis() - starttime) + "");
		return new ResultObj(EC, EM, list);
	}
}
