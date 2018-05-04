package com.fss.mobiletrading.function.watchlist;

import java.util.HashMap;

import com.fss.mobiletrading.common.Common;

public class StockDetailGraphPoint {
	private String symbol;
	private String formattedTime;
	private double formattedMatchPrice;
	private long formattedChangeValue;
	private long formattedVol;
	private long formattedAccVol;

	public StockDetailGraphPoint(HashMap<String, String> hm) {
		super();
		this.symbol = hm.get("symbol");
		this.formattedTime = hm.get("formattedTime");
		this.formattedMatchPrice = Common.parseDouble(hm
				.get("formattedMatchPrice"));
		this.formattedChangeValue = Common.parseLong(hm
				.get("formattedChangeValue"));
		this.formattedVol = Common.parseLong(hm.get("formattedVol"));
		this.formattedAccVol = Common.parseLong(hm.get("formattedAccVol"));
	}

	public String getSymbol() {
		return symbol;
	}

	public String getFormattedTime() {
		return formattedTime;
	}

	public double getFormattedMatchPrice() {
		return formattedMatchPrice;
	}

	public long getFormattedChangeValue() {
		return formattedChangeValue;
	}

	public long getFormattedVol() {
		return formattedVol;
	}

	public long getFormattedAccVol() {
		return formattedAccVol;
	}

}
