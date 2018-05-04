package com.fss.mobiletrading.function.orderlist;

import com.fss.mobiletrading.function.placeorder.OrderSetParams;

public class AmendOrderModel extends OrderSetParams {

	String orderID;
	boolean isQuantityAmend;
	boolean isPriceAmend;

	public AmendOrderModel(String custodycd, String afacctno,
			String symbolOrder, String sideOrder, String priceOrder,
			String quantityOrder, String priceType, String splitOrder,
			boolean isquantityamend, boolean ispriceamend, String orderid) {
		super(custodycd, afacctno, symbolOrder, sideOrder, priceOrder,
				quantityOrder, priceType, splitOrder);
		isQuantityAmend = isquantityamend;
		isPriceAmend = ispriceamend;
		orderID = orderid;
	}
}
