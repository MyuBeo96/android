package com.fss.mobiletrading.function.market;

import java.util.HashMap;

public class MarketChartItem {
	public String Time;
	public Double Index = 0d;;
	public Double Value = 0d;
	public Long Vol = 0l;
	public Double Change = 0d;
	public String lastSeq;

	public MarketChartItem(HashMap<String, String> hm) {
		super();
		Time = hm.get("Time");
		try {
			Index = Double.parseDouble(hm.get("Index"));
		} catch (Exception e) {
		}
		try {
			Value = Double.parseDouble(hm.get("Value"));
		} catch (Exception e) {
		}
		try {
			Change = Double.parseDouble(hm.get("Change"));
		} catch (Exception e) {
		}
		try {
			Vol = Long.parseLong(hm.get("Vol"));
		} catch (Exception e) {
		}
		lastSeq = hm.get("lastSeq");
	}

	public MarketChartItem(String time, Double index, Double value,
			Double change, Long vol, String ls) {
		Time = time;
		Index = index;
		Value = value;
		Change = change;
		Vol = vol;
		lastSeq = ls;
	}

}
