package com.fss.mobiletrading.object;

import java.util.HashMap;

public class DotBienKhoiLuongItem {
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

	public DotBienKhoiLuongItem(HashMap<String, String> hm) {
		this.STT = hm.get("STT");
		this.SYMBOL = hm.get("SYMBOL");
		this.REALPRICE = hm.get("REALPRICE");
		this.REALPRICE_CHANGE = hm.get("REALPRICE_CHANGE");
		this.REALPRICE_PERCENT_CHANGE = hm.get("REALPRICE_PERCENT_CHANGE");
		this.REALPRICE_PERCENT_CHANGE_tmp = hm
				.get("REALPRICE_PERCENT_CHANGE_tmp");
		this.KLGD = hm.get("KLGD");
		this.GTGD = hm.get("GTGD");
		this.KLGD_TB = hm.get("KLGD_TB");
		this.KLGD_SV_TB = hm.get("KLGD_SV_TB");
		this.OPENPRICE = hm.get("OPENPRICE");
		this.HIGH = hm.get("HIGH");
		this.LOW = hm.get("LOW");
		this.KLGD_SV_TB_tmp = hm.get("KLGD_SV_TB_tmp");
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.DotBienKhoiLuongItem JD-Core Version: 0.6.0
 */