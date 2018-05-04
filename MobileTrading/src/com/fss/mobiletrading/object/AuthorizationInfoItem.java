package com.fss.mobiletrading.object;

import java.util.HashMap;

public class AuthorizationInfoItem {
	public String VALDATE;
	public String EXPDATE;
	public String CUSTODYCD;
	public String IDCODE;
	public String CUSTID;
	public String FULLNAME;

	public AuthorizationInfoItem(HashMap<String, String> hm) {
		super();
		if (hm != null) {
			VALDATE = hm.get("VALDATE");
			EXPDATE = hm.get("EXPDATE");
			CUSTODYCD = hm.get("CUSTODYCD");
			IDCODE = hm.get("IDCODE");
			CUSTID = hm.get("CUSTID");
			FULLNAME = hm.get("FULLNAME");
		}
	}

}
