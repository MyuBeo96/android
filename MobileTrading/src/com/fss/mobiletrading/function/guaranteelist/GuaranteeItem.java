package com.fss.mobiletrading.function.guaranteelist;

import java.util.HashMap;

public class GuaranteeItem {
	public String custodycd;
	public String afacctno;
	public String fullname;
	public String realass;
	public String od_amt;
	public String t0amt;
	public String t0_in_day;
	public String totalbuyqtty;
	public String balance;
	public String marginrate;
	public String marginrat2;
	public GuaranteeItem(HashMap<String, String> hm){
		custodycd = hm.get("CUSTODYCD");
		afacctno = hm.get("AFACCTNO");
		fullname = hm.get("FULLNAME");
		realass = hm.get("REALASS");
		od_amt = hm.get("OD_AMT");
		t0amt = hm.get("T0AMT");
		t0_in_day = hm.get("T0_IN_DAY");
		totalbuyqtty = hm.get("TOTALBUYQTTY");
		balance = hm.get("BALANCE");
		marginrate = hm.get("MARGINRATE");
		marginrat2 = hm.get("MARGINRAT2");
	}
}
