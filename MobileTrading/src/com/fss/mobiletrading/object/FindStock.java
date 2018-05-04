package com.fss.mobiletrading.object;

public class FindStock {
	private int seq;
	public OrderInfoItem orderInfo;
	public StockInfoItem stockInfo;

	public FindStock(OrderInfoItem paramOrderInfo, StockInfoItem paramStockInfo) {
		this.orderInfo = paramOrderInfo;
		this.stockInfo = paramStockInfo;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getSeq() {
		return seq;
	}
}
