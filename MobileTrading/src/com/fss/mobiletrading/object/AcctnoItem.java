package com.fss.mobiletrading.object;

import java.util.HashMap;

import com.fss.mobiletrading.consts.StringConst;

public class AcctnoItem {
	public String ACCTNO = StringConst.EMPTY;
	public String AFTYPE = StringConst.EMPTY;
	public String AUTOADV = StringConst.EMPTY;
	public String BANKACCTNO = StringConst.EMPTY;
	public String BANKNAME = StringConst.EMPTY;
	public String COREBANK = StringConst.EMPTY;
	public String CUSTID = StringConst.EMPTY;
	public String CUSTODYCD = StringConst.EMPTY;
	public String EMAIL = StringConst.EMPTY;
	public String EXPIRED = StringConst.EMPTY;
	public String FAX1 = StringConst.EMPTY;
	public String FULLNAME = StringConst.EMPTY;
	public String LINKAUTH = StringConst.EMPTY;
	public String OWNER = StringConst.EMPTY;
	public String STATUS = StringConst.EMPTY;
	public String TRADEONLINE = StringConst.EMPTY;
	public String TYPENAME = StringConst.EMPTY;
	public String DESCNAME = StringConst.EMPTY;
	public String DESCFULLNAME = StringConst.EMPTY;
	public String Clazz;
	public String Agent;
	public String Mobile;

	public AcctnoItem() {
	}

	public AcctnoItem(String fullName, String afacctno) {
		this.FULLNAME = fullName;
		ACCTNO = afacctno;
	}

	public AcctnoItem(String descname) {
		this.DESCNAME = descname;
	}

	public AcctnoItem(HashMap<String, String> paramHashMap) {
		this.ACCTNO = paramHashMap.get("ACCTNO");
		this.OWNER = paramHashMap.get("OWNER");
		this.CUSTID = paramHashMap.get("CUSTID");
		this.CUSTODYCD = paramHashMap.get("CUSTODYCD");
		this.FULLNAME = paramHashMap.get("FULLNAME");
		this.LINKAUTH = paramHashMap.get("LINKAUTH");
		this.BANKACCTNO = paramHashMap.get("BANKACCTNO");
		this.BANKNAME = paramHashMap.get("BANKNAME");
		this.COREBANK = paramHashMap.get("COREBANK");
		this.STATUS = paramHashMap.get("STATUS");
		this.TYPENAME = paramHashMap.get("TYPENAME");
		this.EXPIRED = paramHashMap.get("EXPIRED");
		this.EMAIL = paramHashMap.get("EMAIL");
		this.FAX1 = paramHashMap.get("FAX1");
		this.TRADEONLINE = paramHashMap.get("TRADEONLINE");
		this.AFTYPE = paramHashMap.get("AFTYPE");
		this.AUTOADV = paramHashMap.get("AUTOADV");
		this.DESCNAME = paramHashMap.get("DESCNAME");
		this.DESCFULLNAME = paramHashMap.get("DESCFULLNAME");
		
		Clazz = paramHashMap.get("Class");
		Agent = paramHashMap.get("Agent");
		Mobile = paramHashMap.get("Mobile");
	}

	public String toString() {
		return DESCNAME;
	}
}
