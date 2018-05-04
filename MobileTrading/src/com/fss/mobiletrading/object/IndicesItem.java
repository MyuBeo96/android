package com.fss.mobiletrading.object;

import java.util.HashMap;

public class IndicesItem {
	public String MarketChange;
	public String MarketChangeP;
	public String MarketId;
	public String MarketIndex;
	public String MarketIndexStyle;
	public String MarketName;
	public String MarketVolume;
	public String MarkettotalValue;
	public String MarketStatusText;
	public String Trade;
	public String amountUp;
	public String amountDown;
	public String amountUnChange;
	public String LS;

	public IndicesItem(HashMap<String, String> hm) {
		MarketId = hm.get("MarketId");
		MarketName = hm.get("MarketName");
		MarketIndex = hm.get("MarketIndex");
		MarketIndexStyle = hm.get("MarketIndexStyle");
		MarketVolume = hm.get("MarketVolume");
		MarkettotalValue = hm.get("MarkettotalValue");
		MarketChange = hm.get("MarketChange");
		MarketChangeP = hm.get("MarketChangeP");
		MarketStatusText = hm.get("MarketStatusText");
		Trade = hm.get("Trade");
		amountUp = hm.get("Up");
		amountDown = hm.get("Down");
		amountUnChange = hm.get("Un");
		LS = hm.get("LS");
	}

}
