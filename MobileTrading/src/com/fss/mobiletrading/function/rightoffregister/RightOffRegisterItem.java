package com.fss.mobiletrading.function.rightoffregister;

import java.io.Serializable;
import java.util.HashMap;

public class RightOffRegisterItem implements Serializable {
	public String Afacctno;
	public String Amount;
	public String CashAvailable;
	public String Details;
	public String Id;
	public String Isrqaccount;
	public String OptionQtty;
	public String Pin;
	public String Price;
	public String Qtty;
	public String RegisteredQtty;
	public String SettleAmount;
	public String Status;
	public String Symbol;

	public String SecType;
	public String RightOffRate;
	public String ReportDate;
	public String ParValue;
	public String ExPrice;
	public String FromDateTransfer;
	public String ToDateTransfer;
	public String BeginDate;
	public String DueDate;
	public String StatusDesc;

	public RightOffRegisterItem(HashMap<String, String> hm) {
		Afacctno = hm.get("Afacctno");
		Id = hm.get("Id");
		Symbol = hm.get("Symbol");
		OptionQtty = hm.get("OptionQtty");
		RegisteredQtty = hm.get("RegisteredQtty");
		Price = hm.get("Price");
		SettleAmount = hm.get("SettleAmount");
		Details = hm.get("Details");
		Qtty = hm.get("Qtty");
		Amount = hm.get("Amount");
		CashAvailable = hm.get("CashAvailable");
		Pin = hm.get("Pin");
		Status = hm.get("Status");
		Isrqaccount = hm.get("Isrqaccount");

		SecType = hm.get("SecType");
		RightOffRate = hm.get("RightOffRate");
		ReportDate = hm.get("ReportDate");
		ParValue = hm.get("ParValue");
		ExPrice = hm.get("ExPrice");
		FromDateTransfer = hm.get("FromDateTransfer");
		ToDateTransfer = hm.get("ToDateTransfer");
		BeginDate = hm.get("BeginDate");
		DueDate = hm.get("DueDate");
		StatusDesc = hm.get("StatusDesc");
	}
}
