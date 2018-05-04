package com.fss.mobiletrading.object;

import java.util.HashMap;

public class StockBalanceItem {
	private String RECEIVING_T0;
	private String RECEIVING_T1;
	private String RECEIVING_T2;
	private String RECEIVING_T3;
	private String ITEM;
	private String TRADE;
	private String RECEIVING_RIGHT;
	private String SECURED;
	private String CURRPRICE;
	private String MARKETAMT;
	private String TOTAL_QTTY;
	private String FULLTRADE;

	public StockBalanceItem(HashMap<String, String> hm) {
		ITEM = hm.get("ITEM");
		TRADE = hm.get("TRADE");
		RECEIVING_T0 = hm.get("RECEIVING_T0");
		RECEIVING_T1 = hm.get("RECEIVING_T1");
		RECEIVING_T2 = hm.get("RECEIVING_T2");
		RECEIVING_T3 = hm.get("RECEIVING_T3");
		RECEIVING_RIGHT = hm.get("RECEIVING_RIGHT");
		SECURED = hm.get("SECURED");
		CURRPRICE = hm.get("CURRPRICE");
		MARKETAMT = hm.get("MARKETAMT");
		TOTAL_QTTY = hm.get("TOTAL_QTTY");
		FULLTRADE = hm.get("FULLTRADE");
	}

	public String getFULLTRADE() {
		return FULLTRADE;
	}

	public String toString() {
		return ITEM;
	}

	public String getRECEIVING_T0() {
		return RECEIVING_T0;
	}

	public String getRECEIVING_T1() {
		return RECEIVING_T1;
	}

	public String getRECEIVING_T2() {
		return RECEIVING_T2;
	}

	public String getRECEIVING_T3() {
		return RECEIVING_T3;
	}

	public String getITEM() {
		return ITEM;
	}

	public String getTRADE() {
		return TRADE;
	}

	public String getRECEIVING_RIGHT() {
		return RECEIVING_RIGHT;
	}

	public String getSECURED() {
		return SECURED;
	}

	public String getCURRPRICE() {
		return CURRPRICE;
	}

	public String getMARKETAMT() {
		return MARKETAMT;
	}

	public String getTOTAL_QTTY() {
		return TOTAL_QTTY;
	}

}
