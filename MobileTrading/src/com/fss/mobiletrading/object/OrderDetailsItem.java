package com.fss.mobiletrading.object;

import java.util.HashMap;

public class OrderDetailsItem {
	public String orderID;
	public String orderIDDesc;
	public String priceMatched;
	public String priceOrder;
	public String qtyCancel;
	public String qtyMatched;
	public String qtyModified;
	public String qtyOrder;
	public String qtyRemain;
	public String rootOrderID;
	public String status;
	public String time;
	public String txDate;

	public OrderDetailsItem(HashMap<String, String> hm) {
		rootOrderID = hm.get("rootOrderID");
		orderID = hm.get("orderID");
		orderIDDesc = hm.get("orderIDDesc");
		time = hm.get("time");
		txDate = hm.get("txDate");
		qtyOrder = hm.get("qtyOrder");
		priceOrder = hm.get("priceOrder");
		qtyMatched = hm.get("qtyMatched");
		priceMatched = hm.get("priceMatched");
		qtyRemain = hm.get("qtyRemain");
		qtyCancel = hm.get("qtyCancel");
		qtyModified = hm.get("qtyModified");
		status = hm.get("status");
	}
}
