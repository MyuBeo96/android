package com.fss.mobiletrading.object;

import java.util.HashMap;

public class MoneyInfoItem {
	public String ACCOUNTVALUE;
	public String ADVANCELINE;
	public String AVLLIMIT;
	public String AVLWITHDRAW;
	public String BALANCE;
	public String BANKAVLBAL;
	public String BANKINQIRYDT;
	public String CALLAMT;
	public String CASH_RECEIVING_T0;
	public String CASH_RECEIVING_T1;
	public String CASH_RECEIVING_T2;
	public String CIBALANCE;
	public String CIBALANCE2;
	public String DEPOFEEAMT;
	public String DFODAMT;
	public String DFQTTYAMT;
	public String HOLDBALANCE;
	public String INTBALANCE;
	public String MRAMT;
	public String MRCRLIMIT;
	public String MRCRLIMITMAX;
	public String MRQTTYAMT;
	public String MRRATE;
	public String NETASSVAL;
	public String NONMRQTTYAMT;
	public String PP0;
	public String QTTYAMT;
	public String RCVADVAMT;
	public String RCVAMT;
	public String RCVAMT1;
	public String SECUREAMT;
	public String SESECURED;
	public String SESECURED_AVL;
	public String SESECURED_BUY;
	public String T0AMT;
	public String TDBALANCE;
	public String TDODAMT;
	public String TOTALODAMT;
	public String TOTALODAMT2;
	public String TOTALSEAMT;
	public String TRFBUYAMT;
	public String AVLADVANCE;
	public String OUTSTANDING;
	public String MARGINRATE;
	public String TOTALLOAN;
	public String ADDVND;
	private String CARECEIVING;
	public String NAV;
	

	public MoneyInfoItem(HashMap<String, String> hm) {
		ADDVND = hm.get("ADDVND");
		BALANCE = hm.get("BALANCE");
		CIBALANCE = hm.get("CIBALANCE");
		TDBALANCE = hm.get("TDBALANCE");
		RCVAMT = hm.get("RCVAMT");
		INTBALANCE = hm.get("INTBALANCE");
		TOTALSEAMT = hm.get("TOTALSEAMT");
		MRQTTYAMT = hm.get("MRQTTYAMT");
		NONMRQTTYAMT = hm.get("NONMRQTTYAMT");
		DFQTTYAMT = hm.get("DFQTTYAMT");
		TOTALODAMT = hm.get("TOTALODAMT");
		TRFBUYAMT = hm.get("TRFBUYAMT");
		SECUREAMT = hm.get("SECUREAMT");
		T0AMT = hm.get("T0AMT");
		MRAMT = hm.get("MRAMT");
		RCVADVAMT = hm.get("RCVADVAMT");
		DFODAMT = hm.get("DFODAMT");
		TDODAMT = hm.get("TDODAMT");
		DEPOFEEAMT = hm.get("DEPOFEEAMT");
		NETASSVAL = hm.get("NETASSVAL");
		SESECURED = hm.get("SESECURED");
		SESECURED_AVL = hm.get("SESECURED_AVL");
		SESECURED_BUY = hm.get("SESECURED_BUY");
		ACCOUNTVALUE = hm.get("ACCOUNTVALUE");
		QTTYAMT = hm.get("QTTYAMT");
		CIBALANCE2 = hm.get("CIBALANCE2");
		MRCRLIMIT = hm.get("MRCRLIMIT");
		BANKAVLBAL = hm.get("BANKAVLBAL");
		TOTALODAMT2 = hm.get("TOTALODAMT2");
		RCVAMT1 = hm.get("RCVAMT1");
		PP0 = hm.get("PP0");
		MRRATE = hm.get("MRRATE");
		MRCRLIMITMAX = hm.get("MRCRLIMITMAX");
		ADVANCELINE = hm.get("ADVANCELINE");
		AVLLIMIT = hm.get("AVLLIMIT");
		BANKINQIRYDT = hm.get("BANKINQIRYDT");
		HOLDBALANCE = hm.get("HOLDBALANCE");
		CALLAMT = hm.get("CALLAMT");
		CASH_RECEIVING_T0 = hm.get("CASH_RECEIVING_T0");
		CASH_RECEIVING_T1 = hm.get("CASH_RECEIVING_T1");
		CASH_RECEIVING_T2 = hm.get("CASH_RECEIVING_T2");
		AVLWITHDRAW = hm.get("AVLWITHDRAW");
		AVLADVANCE = hm.get("AVLADVANCE");
		OUTSTANDING = hm.get("OUTSTANDING");
		MARGINRATE = hm.get("MARGINRATE");
		TOTALLOAN = hm.get("TOTALLOAN");
		CARECEIVING = hm.get("CARECEIVING");
		NAV = hm.get("NAV");
	}

	public String getCARECEIVING() {
		return CARECEIVING;
	}
}
