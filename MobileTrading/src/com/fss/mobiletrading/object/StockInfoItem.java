package com.fss.mobiletrading.object;

import java.util.HashMap;

public class StockInfoItem {
	public String AvailableMORTAGE;
	public String AvailableStrade;
	public String CashAvaiable;
	public String CeilPrice;
	public String CeilPriceColor;
	public String ClosePrice;
	public String ClosePriceColor;
	public String FloorPrice;
	public String FloorPriceColor;
	public String Receiving;
	public String RefPrice;
	public String Side;
	public String Symbo;
	public String bid1Color;
	public String bid1ColorValue;
	public String bid2Color;
	public String bid2ColorValue;
	public String bid3Color;
	public String bid3ColorValue;
	public String bidvol1Color;
	public String bidvol1ColorValue;
	public String bidvol2Color;
	public String bidvol2ColorValue;
	public String bidvol3Color;
	public String bidvol3ColorValue;
	public String changeColor;
	public String changeColorValue;
	public String lastChangeTick;
	public String matchpriceColor;
	public String matchpriceColorValue;
	public String matchvolColor;
	public String matchvolColorValue;
	public String offer1Color;
	public String offer1ColorValue;
	public String offer2Color;
	public String offer2ColorValue;
	public String offer3Color;
	public String offer3ColorValue;
	public String offervol1Color;
	public String offervol1ColorValue;
	public String offervol2Color;
	public String offervol2ColorValue;
	public String offervol3Color;
	public String offervol3ColorValue;
	public String stockname;
	public String totaltradingColor;
	public String totaltradingColorValue;
	public String foreignRemain;
	public String foreignSell;
	public String foreignBuy;
	public String floorCode;

	public StockInfoItem(HashMap<String, String> hm) {
		this.CashAvaiable = hm.get("CashAvaiable");
		this.CeilPrice = hm.get("CeilPrice");
		this.CeilPriceColor = hm.get("CeilPriceColor");
		this.RefPrice = hm.get("RefPrice");
		this.ClosePrice = hm.get("ClosePrice");
		this.ClosePriceColor = hm.get("ClosePriceColor");
		this.FloorPrice = hm.get("FloorPrice");
		this.FloorPriceColor = hm.get("FloorPriceColor");
		this.bid1Color = hm.get("bid1Color");
		this.bid2Color = hm.get("bid2Color");
		this.bid3Color = hm.get("bid3Color");
		this.bid1ColorValue = hm.get("bid1ColorValue");
		this.bid2ColorValue = hm.get("bid2ColorValue");
		this.bid3ColorValue = hm.get("bid3ColorValue");
		this.offer1Color = hm.get("offer1Color");
		this.offer2Color = hm.get("offer2Color");
		this.offer3Color = hm.get("offer3Color");
		this.Symbo = hm.get("Symbo");
		this.offer1ColorValue = hm.get("offer1ColorValue");
		this.offer2ColorValue = hm.get("offer2ColorValue");
		this.offer3ColorValue = hm.get("offer3ColorValue");
		this.AvailableStrade = hm.get("AvailableStrade");
		this.AvailableMORTAGE = hm.get("AvailableMORTAGE");
		this.Receiving = hm.get("Receiving");
		this.Side = hm.get("Side");
		this.bidvol1Color = hm.get("bidvol1Color");
		this.bidvol2Color = hm.get("bidvol2Color");
		this.bidvol3Color = hm.get("bidvol3Color");
		this.bidvol1ColorValue = hm.get("bidvol1ColorValue");
		this.bidvol2ColorValue = hm.get("bidvol2ColorValue");
		this.bidvol3ColorValue = hm.get("bidvol3ColorValue");
		this.offervol1Color = hm.get("offervol1Color");
		this.offervol2Color = hm.get("offervol2Color");
		this.offervol3Color = hm.get("offervol3Color");
		this.offervol1ColorValue = hm.get("offervol1ColorValue");
		this.offervol2ColorValue = hm.get("offervol2ColorValue");
		this.offervol3ColorValue = hm.get("offervol3ColorValue");
		this.matchpriceColor = hm.get("matchpriceColor");
		this.matchpriceColorValue = hm.get("matchpriceColorValue");
		this.matchvolColor = hm.get("matchvolColor");
		this.matchvolColorValue = hm.get("matchvolColorValue");
		this.changeColor = hm.get("changeColor");
		this.changeColorValue = hm.get("changeColorValue");
		this.stockname = hm.get("stockname");
		this.lastChangeTick = hm.get("lastChangeTick");
		this.totaltradingColorValue = hm.get("totaltradingColorValue");
		this.totaltradingColor = hm.get("totaltradingColor");
		this.foreignRemain =  hm.get("foreignRemain");
		this.foreignSell = hm.get("foreignSell");
		this.foreignBuy = hm.get("foreignBuy");
		this.floorCode = hm.get("FloorCode");
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.StockInfo JD-Core Version: 0.6.0
 */