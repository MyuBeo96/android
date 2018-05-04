package com.fss.mobiletrading.object;

import java.util.HashMap;

import com.fss.mobiletrading.common.MyJsonObject;

public class StockDetailsItem {
	public String FloorCode;
	public String FullName;
	public String Id;
	public String LS;
	public String PRIOR_PRICE;
	public String PT_MATCH_PRICE;
	public String PT_MATCH_QTTY;
	public String PT_TOTAL_TRADED_QTTY;
	public String PT_TOTAL_TRADED_VALUE;
	public String Status;
	public String StockId;
	public String StockType;
	public String TOTAL_BID_QTTY;
	public String TOTAL_OFFER_QTTY;
	public String averagePrice;
	public String bidPrice1;
	public String bidPrice2;
	public String bidPrice3;
	public String bidVol1;
	public String bidVol2;
	public String bidVol3;
	public String ceiling;
	public String change;
	public String changePercent;
	public String closePrice;
	public String closeVol;
	public String floor;
	public String foreignBuy;
	public String foreignRemain;
	public String foreignRoom;
	public String foreignSell;
	public String high;
	// public List<StockDetailGraphPoint> listPoint;
	public MyJsonObject data;
	public String low;
	public String offerPrice1;
	public String offerPrice2;
	public String offerPrice3;
	public String offerVol1;
	public String offerVol2;
	public String offerVol3;
	public String open;
	public String priceOne;
	public String priceTwo;
	public String reference;
	public String symbol;
	public String totalTrading;
	public String totalTradingValue;
	public String tradingdate;
	public int ratioBidVol;

	public StockDetailsItem(String symbol, HashMap<String, String> hm,
			String LS, String changePercent, MyJsonObject p_data,
			int ratioBidVol) {
		if (hm != null) {
			this.Id = hm.get("Id");
			this.symbol = symbol;
			this.StockId = hm.get("StockId");
			this.FullName = hm.get("FullName");
			this.tradingdate = hm.get("tradingdate");
			this.FloorCode = hm.get("FloorCode");
			this.StockType = hm.get("StockType");
			this.ceiling = hm.get("ceiling");
			this.floor = hm.get("floor");
			this.reference = hm.get("reference");
			this.bidPrice3 = hm.get("bidPrice3");
			this.bidVol3 = hm.get("bidVol3");
			this.bidPrice2 = hm.get("bidPrice2");
			this.bidVol2 = hm.get("bidVol2");
			this.bidPrice1 = hm.get("bidPrice1");
			this.bidVol1 = hm.get("bidVol1");
			this.closePrice = hm.get("closePrice");
			this.closeVol = hm.get("closeVol");
			this.change = hm.get("change");
			this.offerPrice1 = hm.get("offerPrice1");
			this.offerVol1 = hm.get("offerVol1");
			this.offerPrice2 = hm.get("offerPrice2");
			this.offerVol2 = hm.get("offerVol2");
			this.offerPrice3 = hm.get("offerPrice3");
			this.offerVol3 = hm.get("offerVol3");
			this.totalTrading = hm.get("totalTrading");
			this.totalTradingValue = hm.get("totalTradingValue");
			this.averagePrice = hm.get("averagePrice");
			this.open = hm.get("open");
			this.high = hm.get("high");
			this.low = hm.get("low");
			this.foreignBuy = hm.get("foreignBuy");
			this.foreignSell = hm.get("foreignSell");
			this.foreignRemain = hm.get("foreignRemain");
			this.foreignRoom = hm.get("foreignRoom");
			this.TOTAL_OFFER_QTTY = hm.get("TOTAL_OFFER_QTTY");
			this.TOTAL_BID_QTTY = hm.get("TOTAL_BID_QTTY");
			this.PRIOR_PRICE = hm.get("PRIOR_PRICE");
			this.PT_MATCH_QTTY = hm.get("PT_MATCH_QTTY");
			this.PT_MATCH_PRICE = hm.get("PT_MATCH_PRICE");
			this.PT_TOTAL_TRADED_QTTY = hm.get("PT_TOTAL_TRADED_QTTY");
			this.PT_TOTAL_TRADED_VALUE = hm.get("PT_TOTAL_TRADED_VALUE");
			this.Status = hm.get("Status");
			this.priceOne = hm.get("priceOne");
			this.priceTwo = hm.get("priceTwo");

		}
		this.changePercent = changePercent;
		this.data = p_data;
		this.LS = LS;
		this.ratioBidVol = ratioBidVol;
	}

	public StockDetailsItem(String LS, MyJsonObject paramList) {
		this.data = paramList;
		this.LS = LS;
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.StockDetailsItem JD-Core Version: 0.6.0
 */