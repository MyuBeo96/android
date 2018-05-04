package com.fss.mobiletrading.function.oddorderregister;

import java.util.HashMap;

public class OddLotItem {
	public String TXNUM;
	public String TXDATE;
	public String SYMBOL;
	public String QUANTITY;
	public String QUOTEPRICE;
	public String AMOUNT;
	public String FEEAMT;
	public String TAXAMT;
	public String RCVAMT;

	public OddLotItem(String tXNUM, String tXDATE, String sYMBOL,
			String qUANTITY, String qUOTEPRICE, String aMOUNT, String fEEAMT,
			String tAXAMT, String rCVAMT) {
		super();
		TXNUM = tXNUM;
		TXDATE = tXDATE;
		SYMBOL = sYMBOL;
		QUANTITY = qUANTITY;
		QUOTEPRICE = qUOTEPRICE;
		AMOUNT = aMOUNT;
		FEEAMT = fEEAMT;
		TAXAMT = tAXAMT;
		RCVAMT = rCVAMT;
	}

}
