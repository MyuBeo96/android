package com.fss.mobiletrading.object;

import java.util.HashMap;

public class AcctnoDetail {
	public String Afacctno;
	public String Amount;
	public String At;
	public String BankID;
	public String BeneficiaryAfacctno;
	public String BeneficiaryCustodyCd;
	public String BeneficiaryName;
	public String CashAvailable;
	public String CustodyCd;
	public String Desc;
	public String Fee;
	public String FeeCD;
	public String Issuedon;
	public String PassportNo;
	public String Pin;
	public String SenderCustodyCd;
	public String SenderName;
	public String TotalAmount;

	public AcctnoDetail(HashMap<String, String> hm) {
		this.CustodyCd = hm.get("CustodyCd");
		this.BeneficiaryName = hm.get("BeneficiaryName");
		this.Afacctno = hm.get("Afacctno");
		this.BeneficiaryCustodyCd = hm.get("BeneficiaryCustodyCd");
		this.BeneficiaryAfacctno = hm.get("BeneficiaryAfacctno");
		this.CashAvailable = hm.get("CashAvailable");
		this.Amount = hm.get("Amount");
		this.FeeCD = hm.get("FeeCD");
		this.Fee = hm.get("Fee");
		this.Desc = hm.get("Desc");
		this.Pin = hm.get("Pin");
		this.PassportNo = hm.get("PassportNo");
		this.Issuedon = hm.get("Issuedon");
		this.At = hm.get("At");
		this.BankID = hm.get("BankID");
		this.SenderCustodyCd = hm.get("SenderCustodyCd");
		this.SenderName = hm.get("SenderName");
		this.TotalAmount = hm.get("TotalAmount");
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.AcctnoDetail JD-Core Version: 0.6.0
 */