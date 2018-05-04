package com.fss.mobiletrading.function.portfolio;

import java.util.HashMap;

public class PorfolioItem {
	String SYMBOL;
	String FLOORCODE;
	String MARKETCODE;
	String FULLNAME;
	String TOTAL;
	String MARKETPRICE;
	String COSTVALUE;
	String PERCENT_PL;
	String PRICECHANGE;
	String PERCENT_PRICECHANGE;
	String MARKETVALUE;
	String UNREALIZED_PL;
	private String Item;
	private String Acctno;
	private String Trade;
	private String Receiving_Right;
	private String Receiving_T0;
	private String Receiving_T1;
	private String Receiving_T2;
	private String Receiving_T3;
	private String Secured;
	private String BasicPrice;
	private String MarketPrice;
	private String _MarketPrice;
	private String TotalBalance;
	private String _TotalBalance;
	private String AvgBuyPrice;
	private String CostValue;
	private String _CostValue;
	private String Unrealized_PL;
	private String _Unrealized_PL;
	private String Percent_PL;
	private String stt;

	public PorfolioItem(HashMap<String, String> hm) {
		SYMBOL = hm.get("SYMBOL");
		FLOORCODE = hm.get("FLOORCODE");
		MARKETCODE = hm.get("MARKETCODE");
		FULLNAME = hm.get("FULLNAME");
		TOTAL = hm.get("TOTAL");
		MARKETPRICE = hm.get("MARKETPRICE");
		COSTVALUE = hm.get("COSTVALUE");
		PERCENT_PL = hm.get("PERCENT_PL");
		PRICECHANGE = hm.get("PRICECHANGE");
		PERCENT_PRICECHANGE = hm.get("PERCENT_PRICECHANGE");
		MARKETVALUE = hm.get("MARKETVALUE");
		UNREALIZED_PL = hm.get("UNREALIZED_PL");
		Item = hm.get("Item");
		Acctno = hm.get("Acctno");
		Trade = hm.get("Trade");
		Receiving_Right = hm.get("Receiving_Right");
		Receiving_T0 = hm.get("Receiving_T0");
		Receiving_T1 = hm.get("Receiving_T1");
		Receiving_T2 = hm.get("Receiving_T2");
		Receiving_T3 = hm.get("Receiving_T3");
		Secured = hm.get("Secured");
		BasicPrice = hm.get("BasicPrice");
		MarketPrice = hm.get("MarketPrice");
		_MarketPrice = hm.get("_MarketPrice");
		TotalBalance = hm.get("TotalBalance");
		_TotalBalance = hm.get("_TotalBalance");
		AvgBuyPrice = hm.get("AvgBuyPrice");
		CostValue = hm.get("CostValue");
		_CostValue = hm.get("_CostValue");
		Unrealized_PL = hm.get("Unrealized_PL");
		_Unrealized_PL = hm.get("_Unrealized_PL");
		Percent_PL = hm.get("Percent_PL");
		stt = hm.get("stt");

	}

	public String getSYMBOL() {
		return SYMBOL;
	}

	public String getFLOORCODE() {
		return FLOORCODE;
	}

	public String getMARKETCODE() {
		return MARKETCODE;
	}

	public String getFULLNAME() {
		return FULLNAME;
	}

	public String getTOTAL() {
		return TOTAL;
	}

	public String getMARKETPRICE() {
		return MARKETPRICE;
	}

	public String getCOSTVALUE() {
		return COSTVALUE;
	}

	public String getPERCENT_PL() {
		return PERCENT_PL;
	}

	public String getPRICECHANGE() {
		return PRICECHANGE;
	}

	public String getPERCENT_PRICECHANGE() {
		return PERCENT_PRICECHANGE;
	}

	public String getMARKETVALUE() {
		return MARKETVALUE;
	}

	public String getUNREALIZED_PL() {
		return UNREALIZED_PL;
	}

	public String getItem() {
		return Item;
	}

	public String getAcctno() {
		return Acctno;
	}

	public String getTrade() {
		return Trade;
	}

	public String getReceiving_Right() {
		return Receiving_Right;
	}

	public String getReceiving_T0() {
		return Receiving_T0;
	}

	public String getReceiving_T1() {
		return Receiving_T1;
	}

	public String getReceiving_T2() {
		return Receiving_T2;
	}

	public String getReceiving_T3() {
		return Receiving_T3;
	}

	public String getSecured() {
		return Secured;
	}

	public String getBasicPrice() {
		return BasicPrice;
	}

	public String getMarketPrice() {
		return MarketPrice;
	}

	public String get_MarketPrice() {
		return _MarketPrice;
	}

	public String getTotalBalance() {
		return TotalBalance;
	}

	public String get_TotalBalance() {
		return _TotalBalance;
	}

	public String getAvgBuyPrice() {
		return AvgBuyPrice;
	}

	public String getCostValue() {
		return CostValue;
	}

	public String get_CostValue() {
		return _CostValue;
	}

	public String getUnrealized_PL() {
		return Unrealized_PL;
	}

	public String get_Unrealized_PL() {
		return _Unrealized_PL;
	}

	public String getPercent_PL() {
		return Percent_PL;
	}

	public String getStt() {
		return stt;
	}
}
