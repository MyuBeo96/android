package com.fss.mobiletrading.object;

import java.util.HashMap;

public class CashStatementItem {
	private String Balance;
	private String Block;
	private String CashMustPay;
	private String Credit;
	private String Date;
	private String Debit;
	private String Deposit;
	private String Descriptions;
	private String Endbalance;
	private String Endingbalance;
	private String ID;
	private String Note;
	private String Receivable;
	private String beginningbalance;

	public CashStatementItem(HashMap<String, String> hm) {
		this.ID = hm.get("ID");
		this.Date = hm.get("Date");
		this.Descriptions = hm.get("Descriptions");
		this.Debit = hm.get("Debit");
		this.Credit = hm.get("Credit");
		this.beginningbalance = hm.get("beginningbalance");
		this.Endbalance = hm.get("Endbalance");
		this.Note = hm.get("Note");
		this.Endingbalance = hm.get("Endingbalance");
		this.Receivable = hm.get("Receivable");
		this.Block = hm.get("Block");
		this.Deposit = hm.get("Deposit");
		this.CashMustPay = hm.get("CashMustPay");
		this.Balance = hm.get("Balance");
	}

	public CashStatementItem() {
		// TODO Auto-generated constructor stub
	}

	public String getBalance() {
		return this.Balance;
	}

	public String getBeginningbalance() {
		return this.beginningbalance;
	}

	public String getBlock() {
		return this.Block;
	}

	public String getCashMustPay() {
		return this.CashMustPay;
	}

	public String getCredit() {
		return this.Credit;
	}

	public String getDate() {
		return this.Date;
	}

	public String getDebit() {
		return this.Debit;
	}

	public String getDeposit() {
		return this.Deposit;
	}

	public String getDescriptions() {
		return this.Descriptions;
	}

	public String getEndbalance() {
		return this.Endbalance;
	}

	public String getEndingbalance() {
		return this.Endingbalance;
	}

	public String getID() {
		return this.ID;
	}

	public String getNote() {
		return this.Note;
	}

	public String getReceivable() {
		return this.Receivable;
	}

	public void setBalance(String paramString) {
		this.Balance = paramString;
	}

	public void setBeginningbalance(String paramString) {
		this.beginningbalance = paramString;
	}

	public void setBlock(String paramString) {
		this.Block = paramString;
	}

	public void setCashMustPay(String paramString) {
		this.CashMustPay = paramString;
	}

	public void setCredit(String paramString) {
		this.Credit = paramString;
	}

	public void setDate(String paramString) {
		this.Date = paramString;
	}

	public void setDebit(String paramString) {
		this.Debit = paramString;
	}

	public void setDeposit(String paramString) {
		this.Deposit = paramString;
	}

	public void setDescriptions(String paramString) {
		this.Descriptions = paramString;
	}

	public void setEndbalance(String paramString) {
		this.Endbalance = paramString;
	}

	public void setEndingbalance(String paramString) {
		this.Endingbalance = paramString;
	}

	public void setID(String paramString) {
		this.ID = paramString;
	}

	public void setNote(String paramString) {
		this.Note = paramString;
	}

	public void setReceivable(String paramString) {
		this.Receivable = paramString;
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.adapter.SaoKeTien_Item JD-Core Version: 0.6.0
 */