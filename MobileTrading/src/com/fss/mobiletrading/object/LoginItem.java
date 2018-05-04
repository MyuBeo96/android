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

	public LoginItem(String currentAcctno, String custodycd, String custName,
			String custEmail, String username, String txDateString,
			String language, String isBroker, String pr_Clazz, String pr_agent,
			String pr_mobile, List<AcctnoItem> contractList) {
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
	}

}
