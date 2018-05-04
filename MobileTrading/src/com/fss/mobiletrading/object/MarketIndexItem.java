package com.fss.mobiletrading.object;

import java.util.HashMap;
import java.util.List;

import com.fss.mobiletrading.function.market.MarketChartItem;
import com.mtrading.mobile.graph.IndexVolumeChartPoint;

public class MarketIndexItem {
	public String AVR_CHG_INDEX;
	public String AVR_MARKET_INDEX;
	public String AVR_PCT_INDEX;
	public String AVR_PRIOR_MARKET_INDEX;
	public String Id;
	public String PRV_PRIOR_MARKET_INDEX;
	public String PT_TOTAL_QTTY;
	public String PT_TOTAL_TRADE;
	public String PT_TOTAL_VALUE;
	public String advances;
	public String advancesVolumn;
	public String declines;
	public String declinesVolumn;
	public String indexChange;
	public String indexColor;
	public String indexPercentChange;
	public String indexTime;
	public List<IndexVolumeChartPoint> listPoint;
	public String marketCode;
	public String marketId;
	public String marketIndex;
	public String marketStatus;
	public String noChange;
	public String noChangeVolumn;
	public String sequenceMsg;
	public String totalTrade;
	public String totalValue;
	public String totalVolume;
	public String tradingdate;
	public String lastSeq;

	public MarketIndexItem(HashMap<String, String> hm,
			List<IndexVolumeChartPoint> paramList, String LS) {
		this.Id = hm.get("Id");
		this.sequenceMsg = hm.get("sequenceMsg");
		this.tradingdate = hm.get("tradingdate");
		this.marketIndex = hm.get("marketIndex");
		this.indexTime = hm.get("indexTime");
		this.indexColor = hm.get("indexColor");
		this.indexChange = hm.get("indexChange");
		this.indexPercentChange = hm.get("indexPercentChange");
		this.totalTrade = hm.get("totalTrade");
		this.totalVolume = hm.get("totalVolume");
		this.totalValue = hm.get("totalValue");
		this.marketStatus = hm.get("marketStatus");
		this.advances = hm.get("advances");
		this.declines = hm.get("declines");
		this.noChange = hm.get("noChange");
		this.advancesVolumn = hm.get("advancesVolumn");
		this.declinesVolumn = hm.get("declinesVolumn");
		this.noChangeVolumn = hm.get("noChangeVolumn");
		this.marketId = hm.get("marketId");
		this.marketCode = hm.get("marketCode");
		this.PRV_PRIOR_MARKET_INDEX = hm.get("PRV_PRIOR_MARKET_INDEX");
		this.AVR_MARKET_INDEX = hm.get("AVR_MARKET_INDEX");
		this.AVR_PRIOR_MARKET_INDEX = hm.get("AVR_PRIOR_MARKET_INDEX");
		this.AVR_CHG_INDEX = hm.get("AVR_CHG_INDEX");
		this.AVR_PCT_INDEX = hm.get("AVR_PCT_INDEX");
		this.PT_TOTAL_QTTY = hm.get("PT_TOTAL_QTTY");
		this.PT_TOTAL_VALUE = hm.get("PT_TOTAL_VALUE");
		this.PT_TOTAL_TRADE = hm.get("PT_TOTAL_TRADE");
		this.lastSeq = LS;
		this.listPoint = paramList;
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.MarketIndexItem JD-Core Version: 0.6.0
 */