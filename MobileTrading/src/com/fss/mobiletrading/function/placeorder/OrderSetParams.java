package com.fss.mobiletrading.function.placeorder;

public class OrderSetParams {
	public String custodycd;
	public String afacctno;
	public boolean isGTCOrder = false;
	public String sideOrder;
	public String symbolOrder;
	public String quantityOrder;
	public String priceOrder;
	public String priceType;
	public String splitOrder;
	public String fromDate;
	public String toDate;

	public OrderSetParams(String custodycd, String afacctno,
			String symbolOrder, String sideOrder, String priceOrder,
			String quantityOrder) {
		super();
		this.custodycd = custodycd;
		this.afacctno = afacctno;
		this.symbolOrder = symbolOrder;
		this.sideOrder = sideOrder;
		this.priceOrder = priceOrder;
		this.quantityOrder = quantityOrder;
	}

	/**
	 * lệnh thường
	 * 
	 * @param custodycd
	 * @param afacctno
	 * @param symbolOrder
	 * @param sideOrder
	 * @param priceOrder
	 * @param quantityOrder
	 * @param priceType
	 * @param splitOrder
	 */
	public OrderSetParams(String custodycd, String afacctno,
			String symbolOrder, String sideOrder, String priceOrder,
			String quantityOrder, String priceType, String splitOrder) {
		super();
		this.custodycd = custodycd;
		this.afacctno = afacctno;
		this.symbolOrder = symbolOrder;
		this.sideOrder = sideOrder;
		this.priceOrder = priceOrder;
		this.quantityOrder = quantityOrder;
		this.priceType = priceType;
		this.splitOrder = splitOrder;
	}

	/**
	 * lệnh gtc
	 * 
	 * @param custodycd
	 * @param afacctno
	 * @param symbolOrder
	 * @param sideOrder
	 * @param priceOrder
	 * @param quantityOrder
	 * @param priceType
	 * @param fromDate
	 * @param toDate
	 */
	public OrderSetParams(String custodycd, String afacctno,
			String symbolOrder, String sideOrder, String priceOrder,
			String quantityOrder, String priceType, String fromDate,
			String toDate) {
		super();
		this.custodycd = custodycd;
		this.afacctno = afacctno;
		this.symbolOrder = symbolOrder;
		this.sideOrder = sideOrder;
		this.priceOrder = priceOrder;
		this.quantityOrder = quantityOrder;
		this.priceType = priceType;
		this.fromDate = fromDate;
		this.toDate = toDate;
		isGTCOrder = true;
	}
}