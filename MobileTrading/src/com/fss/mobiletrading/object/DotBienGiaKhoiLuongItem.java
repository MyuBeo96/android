package com.fss.mobiletrading.object;

import java.util.HashMap;

public class DotBienGiaKhoiLuongItem {
	public String GTGD;
	public String HIGH;
	public String KLGD;
	public String KLGD_SV_TB;
	public String KLGD_SV_TB_tmp;
	public String KLGD_TB;
	public String LOW;
	public String OPENPRICE;
	public String REALPRICE;
	public String REALPRICE_CHANGE;
	public String REALPRICE_PERCENT_CHANGE;
	public String REALPRICE_PERCENT_CHANGE_tmp;
	public String STT;
	public String SYMBOL;

	public DotBienGiaKhoiLuongItem(HashMap<String, String> paramHashMap) {
		this.STT = ((String) paramHashMap.get("STT"));
		this.SYMBOL = ((String) paramHashMap.get("SYMBOL"));
		this.REALPRICE = ((String) paramHashMap.get("REALPRICE"));
		this.REALPRICE_CHANGE = ((String) paramHashMap.get("REALPRICE_CHANGE"));
		this.REALPRICE_PERCENT_CHANGE = ((String) paramHashMap
				.get("REALPRICE_PERCENT_CHANGE"));
		this.REALPRICE_PERCENT_CHANGE_tmp = ((String) paramHashMap
				.get("REALPRICE_PERCENT_CHANGE_tmp"));
		this.KLGD = ((String) paramHashMap.get("KLGD"));
		this.GTGD = ((String) paramHashMap.get("GTGD"));
		this.KLGD_TB = ((String) paramHashMap.get("KLGD_TB"));
		this.KLGD_SV_TB = ((String) paramHashMap.get("KLGD_SV_TB"));
		this.OPENPRICE = ((String) paramHashMap.get("OPENPRICE"));
		this.HIGH = ((String) paramHashMap.get("HIGH"));
		this.LOW = ((String) paramHashMap.get("LOW"));
		this.KLGD_SV_TB_tmp = ((String) paramHashMap.get("KLGD_SV_TB_tmp"));
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.DotBienGiaKhoiLuongItem JD-Core Version: 0.6.0
 */