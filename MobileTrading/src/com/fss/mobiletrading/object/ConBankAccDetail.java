package com.fss.mobiletrading.object;

import java.util.HashMap;

public class ConBankAccDetail {
	public String Afacctno;
	public String Amount;
	public String At;
	public String BankID;
	public String BeneficiaryAfacctno;
	public String BeneficiaryBank;
	public String BeneficiaryCustodyCd;
	public String BeneficiaryName;
	public String Branch;
	public String CashAvailable;
	public String City;
	public String ConBankAcc;
	public String ConBankAccIndex;
	public String Confirm;
	public String Desc;
	public String Fee;
	public String FeeCD;
	public String FeeType;
	public String FeeVat;
	public String Issuedon;
	public String PassportNo;
	public String Pin;
	public String ReceiveAmount;
	public String SenderCustodyCd;
	public String TransferType;
	public String balance;
	public String cashReceiving;
	public String TotalAmount;

	public ConBankAccDetail(HashMap<String, String> hm) {
		this.ConBankAcc = hm.get("ConBankAcc");
		this.ConBankAccIndex = hm.get("ConBankAccIndex");
		this.BeneficiaryName = hm.get("BeneficiaryName");
		this.BeneficiaryBank = hm.get("BeneficiaryBank");
		this.City = hm.get("City");
		this.Branch = hm.get("Branch");
		this.Afacctno = hm.get("Afacctno");
		this.BeneficiaryCustodyCd = hm.get("BeneficiaryCustodyCd");
		this.BeneficiaryAfacctno = hm.get("BeneficiaryAfacctno");
		this.CashAvailable = hm.get("CashAvailable");
		this.Amount = hm.get("Amount");
		this.Fee = hm.get("Fee");
		this.FeeCD = hm.get("FeeCD");
		this.Desc = hm.get("Desc");
		this.Pin = hm.get("Pin");
		this.PassportNo = hm.get("PassportNo");
		this.Issuedon = hm.get("Issuedon");
		this.At = hm.get("At");
		this.BankID = hm.get("BankID");
		this.FeeType = hm.get("FeeType");
		this.TransferType = hm.get("TransferType");
		this.SenderCustodyCd = hm.get("SenderCustodyCd");
		this.cashReceiving = hm.get("cashReceiving");
		this.balance = hm.get("balance");
		this.ReceiveAmount = hm.get("ReceiveAmount");
		this.Confirm = hm.get("Confirm");
		this.FeeVat = hm.get("FeeVat");
		this.TotalAmount = hm.get("TotalAmount");
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.ConBankAccDetail JD-Core Version: 0.6.0
 */