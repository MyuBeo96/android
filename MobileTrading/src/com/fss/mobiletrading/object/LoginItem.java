package com.fss.mobiletrading.object;

import java.util.List;

import com.fss.mobiletrading.consts.StringConst;
import com.google.android.gms.drive.internal.ag;

public class LoginItem {
	public String CurrentAcctno;
	public String Custodycd;
	public String CustName;
	public String CustEmail;
	public String TxDateString;
	public String UserName;
	public String Language;
	public boolean IsBroker;
	public List<AcctnoItem> contractList;
	public String Clazz;
	public String Agent;
	public String Mobile;
	public String IsOTPOrder;
	public String IsOTPCondOrder;
	public String IsOTPCash;
	public String IsOTPIssue;
	public String IsOTPDeposit;
	public String DisableOTPTime;
	public String IsFirstLogin;
	public String IsDigital;
	public String IsFillOTP;

	public LoginItem(String currentAcctno, String custodycd, String custName,
			String custEmail, String username, String txDateString,
			String language, String isBroker, String pr_Clazz, String pr_agent,
			String pr_mobile, List<AcctnoItem> contractList, String isotporder,
					 String isotpcondorder,String isotpcash,String isotpissue,String isotpdeposit, String disableotptime,
					 String isfirstlogin, String isDigital, String isFillOTP) {
		super();
		CurrentAcctno = currentAcctno;
		Custodycd = custodycd;
		CustName = custName;
		CustEmail = custEmail;
		TxDateString = txDateString;
		Language = language;
		this.contractList = contractList;
		IsBroker = isBroker.equals(StringConst.TRUE);
		UserName = username;
		Clazz = pr_Clazz;
		Agent = pr_agent;
		Mobile = pr_mobile;
		IsOTPOrder = isotporder;
		IsOTPCondOrder= isotpcondorder;
		IsOTPCash= isotpcash;
		IsOTPIssue= isotpissue;
		IsOTPDeposit= isotpdeposit;
		DisableOTPTime= disableotptime;
		IsFirstLogin= isfirstlogin;
		IsDigital = isDigital;
		IsFillOTP = isFillOTP;
	}

}
