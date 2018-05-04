package com.fss.mobiletrading.object;

import java.util.HashMap;

public class DotBienGiaItem {
	public String BVPS;
	public String EPS;
	public String GTGD;
	public String HIGH;
	public String KLGD;
	public String KLGD_tmp;
	public String LOW;
	public String PB;
	public String PE;
	public String REALPRICE;
	public String REALPRICE_CHANGE;
	public String REALPRICE_PERCENT_CHANGE;
	public String STT;
	public String SYMBOL;

	public DotBienGiaItem(HashMap<String, String> paramHashMap) {
		this.STT = ((String) paramHashMap.get("STT"));
		this.SYMBOL = ((String) paramHashMap.get("SYMBOL"));
		this.REALPRICE = ((String) paramHashMap.get("REALPRICE"));
		this.REALPRICE_CHANGE = ((String) paramHashMap.get("REALPRICE_CHANGE"));
		this.REALPRICE_PERCENT_CHANGE = ((String) paramHashMap
				.get("REALPRICE_PERCENT_CHANGE"));
		this.KLGD = ((String) paramHashMap.get("KLGD"));
		this.KLGD_tmp = ((String) paramHashMap.get("KLGD_tmp"));
		this.GTGD = ((String) paramHashMap.get("GTGD"));
		this.HIGH = ((String) paramHashMap.get("HIGH"));
		this.LOW = ((String) paramHashMap.get("LOW"));
		this.PE = ((String) paramHashMap.get("PE"));
		this.PB = ((String) paramHashMap.get("PB"));
		this.EPS = ((String) paramHashMap.get("EPS"));
		this.BVPS = ((String) paramHashMap.get("BVPS"));
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.DotBienGiaItem JD-Core Version: 0.6.0
 */