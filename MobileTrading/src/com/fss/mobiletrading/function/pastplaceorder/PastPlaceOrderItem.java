package com.fss.mobiletrading.function.pastplaceorder;

import java.util.HashMap;

public class PastPlaceOrderItem {
	// "CUSTODYCD": "009C222222",
	// "AFACCTNO": "0001000068",
	// "ORDERID": "8000180615000006",
	// "TXDATE": "6/18/2015 12:00:00 AM",
	// "SYMBOL": "SSI",
	// "TRADEPLACE": "HOSE",
	// "VIA": "VCBS-Trade",
	// "EXECTYPE": "NB",
	// "ORDERQTTY": "1000",
	// "QUOTEPRICE": "27000",
	// "OQTTY": "27000",
	// "EXECQTTY": "0",
	// "EXECPRICE": "0",
	// "EXECAMT": "0",
	// "ORSTATUS": "Hết hiệu lực",
	// "FEEACR": "0",
	// "CMSFEE": "",
	// "SELLTAXAMT": "0",
	// "FEERATE": "0.15",
	// "QUOTEQTTY": "0",
	// "CONFIRMED": "N",
	// "QPRICE": "27,000",
	// "ExType": "Mua"
	// "TDATE": "18/06/2015",

	private String CUSTODYCD;
	private String AFACCTNO;
	private String ORDERID;
	private String TXDATE;
	private String TDATE;
	private String SYMBOL;
	private String TRADEPLACE;
	private String VIA;
	private String EXECTYPE;
	private String ORDERQTTY;
	private String QUOTEPRICE;
	private String EXECQTTY;
	private String EXECPRICE;
	private String EXECAMT;
	private String ORSTATUS;
	private String FEEACR;
	private String CMSFEE;
	private String SELLTAXAMT;
	private String FEERATE;
	private String QUOTEQTTY;
	private String CONFIRMED;
	private String QPRICE;
	private String ExType;
	private String OQTTY;
	private String TXTIME;
	private String PRICETYPE;

	public PastPlaceOrderItem(HashMap<String, String> hm) {
		super();
		CUSTODYCD = hm.get("CUSTODYCD");
		AFACCTNO = hm.get("AFACCTNO");
		ORDERID = hm.get("ORDERID");
		TXDATE = hm.get("TXDATE");
		SYMBOL = hm.get("SYMBOL");
		TRADEPLACE = hm.get("TRADEPLACE");
		VIA = hm.get("VIA");
		EXECTYPE = hm.get("EXECTYPE");
		ORDERQTTY = hm.get("ORDERQTTY");
		QUOTEPRICE = hm.get("QUOTEPRICE");
		EXECQTTY = hm.get("EXECQTTY");
		EXECPRICE = hm.get("EXECPRICE");
		EXECAMT = hm.get("EXECAMT");
		ORSTATUS = hm.get("ORSTATUS");
		FEEACR = hm.get("FEEACR");
		CMSFEE = hm.get("CMSFEE");
		SELLTAXAMT = hm.get("SELLTAXAMT");
		FEERATE = hm.get("FEERATE");
		QUOTEQTTY = hm.get("QUOTEQTTY");
		CONFIRMED = hm.get("CONFIRMED");
		QPRICE = hm.get("QPRICE");
		ExType = hm.get("ExType");
		TDATE = hm.get("TDATE");
		OQTTY = hm.get("OQTTY");
		TXTIME = hm.get("TXTIME");
		PRICETYPE = hm.get("PRICETYPE");

	}

	public String getCUSTODYCD() {
		return CUSTODYCD;
	}

	public String getAFACCTNO() {
		return AFACCTNO;
	}

	public String getORDERID() {
		return ORDERID;
	}

	public String getTDATE() {
		return TDATE;
	}

	public String getTXDATE() {
		return TXDATE;
	}

	public String getSYMBOL() {
		return SYMBOL;
	}

	public String getTRADEPLACE() {
		return TRADEPLACE;
	}

	public String getVIA() {
		return VIA;
	}

	public String getEXECTYPE() {
		return EXECTYPE;
	}

	public String getORDERQTTY() {
		return ORDERQTTY;
	}

	public String getQUOTEPRICE() {
		return QUOTEPRICE;
	}

	public String getEXECQTTY() {
		return EXECQTTY;
	}

	public String getEXECPRICE() {
		return EXECPRICE;
	}

	public String getEXECAMT() {
		return EXECAMT;
	}

	public String getORSTATUS() {
		return ORSTATUS;
	}

	public String getFEEACR() {
		return FEEACR;
	}

	public String getCMSFEE() {
		return CMSFEE;
	}

	public String getSELLTAXAMT() {
		return SELLTAXAMT;
	}

	public String getFEERATE() {
		return FEERATE;
	}

	public String getQUOTEQTTY() {
		return QUOTEQTTY;
	}

	public String getCONFIRMED() {
		return CONFIRMED;
	}

	public String getQPRICE() {
		return QPRICE;
	}

	public String getExType() {
		return ExType;
	}

	public String getOQTTY() {
		return OQTTY;
	}

	public String getTXTIME() {
		return TXTIME;
	}

	public String getPRICETYPE() {
		return PRICETYPE;
	}

}
