package com.fss.mobiletrading.object;

import com.fss.mobiletrading.consts.StringConst;

public class StockItem {

	public final static String HNX = "HNX";
	public final static String HOSE = "HOSE";
	public final static String UPCOM = "UPCOM";
	final static String intHNX = "002";
	final static String intHOSE = "001";
	final static String intUPCOM = "005";

	private String fullstock;
	private String m;
	private String symbol;
	private String value;

	public StockItem(String label, String value, String m) {
		String[] array = label.split(" - ", 2);
		if (array.length > 1) {
			fullstock = array[1].replace("(", "").replace(")", "");
		}
		symbol = array[0];
		this.value = value;
		this.m = m;
	}

	public String getFullStock() {
		return this.fullstock;
	}

	public String getTradePlace() {
		if (m != null) {
			if (m.matches(StockItem.intHNX)) {
				return StockItem.HNX;
			} else if (m.matches(StockItem.intHOSE)) {
				return StockItem.HOSE;
			} else if (m.matches(StockItem.intUPCOM)) {
				return StockItem.UPCOM;
			}
		}
		return StringConst.EMPTY;
	}

	public String getMack() {
		return this.symbol;
	}

	public String getValue() {
		return this.value;
	}

	public String toString() {
		return this.symbol;
	}
}