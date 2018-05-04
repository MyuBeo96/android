package com.fss.mobiletrading.object;

import java.util.HashMap;
import java.util.List;

public class TransferRegisterBankItem {
	public String AfAcctno;
	public String BankAccountNo;
	public String BeneficiaryAfacctno;
	public String BeneficiaryBank;
	public String BeneficiaryCustodyCd;
	public String BeneficiaryName;
	public String Branch;
	public String BranchCode;
	public String CustodyCd;
	public String ProvinceCity;
	public String ProvinceCityCode;
	public String TransferType;
	public List<ItemString2> listTKNhan;

	public TransferRegisterBankItem(HashMap<String, String> hm,
			List<ItemString2> paramList) {
		CustodyCd = hm.get("CustodyCd");
		AfAcctno = hm.get("AfAcctno");
		TransferType = hm.get("TransferType");
		BeneficiaryName = hm.get("BeneficiaryName");
		BankAccountNo = hm.get("BankAccountNo");
		BeneficiaryBank = hm.get("BeneficiaryBank");
		Branch = hm.get("Branch");
		ProvinceCity = hm.get("ProvinceCity");
		BeneficiaryCustodyCd = hm.get("BeneficiaryCustodyCd");
		BeneficiaryAfacctno = hm.get("BeneficiaryAfacctno");
		BranchCode = hm.get("BranchCode");
		ProvinceCityCode = hm.get("ProvinceCityCode");
		listTKNhan = paramList;
	}
}
