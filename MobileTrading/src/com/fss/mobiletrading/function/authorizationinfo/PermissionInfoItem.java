package com.fss.mobiletrading.function.authorizationinfo;

import java.util.HashMap;

public class PermissionInfoItem {
	public String AUTOID;
	public String AFACCTNO;
	public String AUTHCUSTID;
	public String OTMNCODE;
	public String OTRIGHT;
	public String DELTD;

	public PermissionInfoItem(HashMap<String, String> hm) {
		super();
		if (hm != null) {
			AUTOID = hm.get("AUTOID");
			AFACCTNO = hm.get("AFACCTNO");
			AUTHCUSTID = hm.get("AUTHCUSTID");
			OTMNCODE = hm.get("OTMNCODE");
			OTRIGHT = hm.get("OTRIGHT");
			DELTD = hm.get("DELTD");
		}
	}

}
