package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.MarketIndexItem;
import com.fss.mobiletrading.object.ResultObj;
import com.mtrading.mobile.graph.IndexVolumeChartPoint;

public class MarketIndexService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		List<IndexVolumeChartPoint> listMarketChart = new ArrayList<IndexVolumeChartPoint>();
		MyJsonArray t = null;
		try {
			t = DT.getJSONArray("d");
		} catch (OutOfMemoryError e) {
		}
		if (t != null) {

			for (int i = 0; i < t.length(); i++) {
				MyJsonObject formatedValues = t.getJSONObject(i);
				try {
					listMarketChart.add(new IndexVolumeChartPoint(
							formatedValues.getString("Time"), formatedValues
									.getDouble("Index"), formatedValues
									.getDouble("Vol"), formatedValues
									.getDouble("Change")));
				} catch (JSONException e) {
				}
			}
		}
		String lastSeq = DT.getString("LS");

		MarketIndexItem item = new MarketIndexItem(DT.getJSONObject("i")
				.getHashMap(), listMarketChart, lastSeq);

		return new ResultObj(EC, EM, item);
	}
}
