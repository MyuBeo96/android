package com.fss.mobiletrading.object;

import java.util.List;

public class BankAccList {
	public List<BankAccItem> listAcc;
	public List<BankAccItem> listBank;
	public List<BankAccItem> listBankMSB;

	public BankAccList(List<BankAccItem> paramList1,
			List<BankAccItem> paramList2, List<BankAccItem> paramList3) {
		this.listBank = paramList1;
		this.listAcc = paramList2;
		this.listBankMSB = paramList3;
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.BankAccList JD-Core Version: 0.6.0
 */