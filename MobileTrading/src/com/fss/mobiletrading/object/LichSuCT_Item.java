package com.fss.mobiletrading.object;

import java.util.HashMap;

public class LichSuCT_Item {
	private String Acctno;
	private String Amount;
	private String BeneficiaryAcctno;
	private String BeneficiaryBank;
	private String BeneficiaryName;
	private String CreateDate;
	private String Detail;
	private String EffectiveDate;
	private String Id;
	private String OrderingPlace;
	private String Status;
	private String TransferType;

	public LichSuCT_Item(HashMap<String, String> hm) {
		Id = hm.get("Id");
		BeneficiaryName = hm.get("BeneficiaryName");
		Acctno = hm.get("Acctno");
		BeneficiaryAcctno = hm.get("BeneficiaryAcctno");
		BeneficiaryBank = hm.get("BeneficiaryBank");
		TransferType = hm.get("TransferType");
		Amount = hm.get("Amount");
		Detail = hm.get("Detail");
		Status = hm.get("Status");
		CreateDate = hm.get("CreateDate");
		EffectiveDate = hm.get("EffectiveDate");
		OrderingPlace = hm.get("OrderingPlace");
	}

	public String getAcctno() {
		return Acctno;
	}

	public String getAmount() {
		return Amount;
	}

	public String getBeneficiaryAcctno() {
		return BeneficiaryAcctno;
	}

	public String getBeneficiaryBank() {
		return BeneficiaryBank;
	}

	public String getBeneficiaryName() {
		return BeneficiaryName;
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public String getDetail() {
		return Detail;
	}

	public String getEffectiveDate() {
		return EffectiveDate;
	}

	public String getId() {
		return Id;
	}

	public String getOrderingPlace() {
		return OrderingPlace;
	}

	public String getStatus() {
		return Status;
	}

	public String getTransferType() {
		return TransferType;
	}

	public void setAcctno(String paramString) {
		Acctno = paramString;
	}

	public void setAmount(String paramString) {
		Amount = paramString;
	}

	public void setBeneficiaryAcctno(String paramString) {
		BeneficiaryAcctno = paramString;
	}

	public void setBeneficiaryBank(String paramString) {
		BeneficiaryBank = paramString;
	}

	public void setBeneficiaryName(String paramString) {
		BeneficiaryName = paramString;
	}

	public void setCreateDate(String paramString) {
		CreateDate = paramString;
	}

	public void setDetail(String paramString) {
		Detail = paramString;
	}

	public void setEffectiveDate(String paramString) {
		EffectiveDate = paramString;
	}

	public void setId(String paramString) {
		Id = paramString;
	}

	public void setOrderingPlace(String paramString) {
		OrderingPlace = paramString;
	}

	public void setStatus(String paramString) {
		Status = paramString;
	}

	public void setTransferType(String paramString) {
		TransferType = paramString;
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.adapter.LichSuCT_Item JD-Core Version: 0.6.0
 */