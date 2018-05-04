package com.fss.mobiletrading.object;

import java.util.HashMap;

public class StockStatementItem {
	public String beginningbalance;
	public String credit;
	public String date;
	public String debit;
	public String descriptions;
	public String endingbalance;
	public String iD;
	public String status;
	public String symbol;
	public String transactionType;

	public StockStatementItem(HashMap<String, String> hm) {
		this.iD = hm.get("ID");
		this.date = hm.get("Date");
		this.symbol = hm.get("Symbol");
		this.transactionType = hm.get("TransactionType");
		this.debit = hm.get("Debit");
		this.credit = hm.get("Credit");
		this.descriptions = hm.get("Descriptions");
		this.status = hm.get("Status");
		this.beginningbalance = hm.get("Beginningbalance");
		this.endingbalance = hm.get("Endingbalance");
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.adapter.SaoKeCK_Item JD-Core Version: 0.6.0
 */