package com.fss.mobiletrading.object;

import java.util.HashMap;

public class ConfirmOrderItem {
	public String ORDERID;
	public String TXDATE;
	public String CODEID;
	public String TRADEPLACE;
	public String EXECTYPE;
	public String PRICETYPE;
	public String VIA;
	public String ORDERQTTY;
	public String QUOTEPRICE;
	public String REFORDERID;
	public String SYMBOL;
	public String CONFIRMED;
	public String AFACCTNO;
	public String CUSTODYCD;
	public String FULLNAME;
	public String ROOTORDERID;

	public ConfirmOrderItem(HashMap<String, String> hm) {
		super();
		ORDERID = hm.get("ORDERID");
		TXDATE = hm.get("TXDATE");
		CODEID = hm.get("CODEID");
		TRADEPLACE = hm.get("TRADEPLACE");
		EXECTYPE = hm.get("EXECTYPE");
		PRICETYPE = hm.get("PRICETYPE");
		VIA = hm.get("VIA");
		ORDERQTTY = hm.get("ORDERQTTY");
		QUOTEPRICE = hm.get("QUOTEPRICE");
		REFORDERID = hm.get("REFORDERID");
		SYMBOL = hm.get("SYMBOL");
		CONFIRMED = hm.get("CONFIRMED");
		AFACCTNO = hm.get("AFACCTNO");
		CUSTODYCD = hm.get("CUSTODYCD");
		FULLNAME = hm.get("FULLNAME");
		ROOTORDERID = hm.get("ROOTORDERID");

	}

}
