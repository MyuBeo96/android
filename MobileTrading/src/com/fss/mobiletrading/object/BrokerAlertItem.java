package com.fss.mobiletrading.object;

import java.util.HashMap;

public class BrokerAlertItem {
	public String AlertID;
	public String Date;
	public String Time;
	public String Subject;
	public String HTML;

	public BrokerAlertItem(HashMap<String, String> hm) {
		AlertID = hm.get("AlertID");
		Date = hm.get("Date");
		Time = hm.get("Time");
		Subject = hm.get("Subject");
		HTML = hm.get("HTML");
	}

}
