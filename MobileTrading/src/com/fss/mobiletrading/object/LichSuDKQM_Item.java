package com.fss.mobiletrading.object;

import java.util.HashMap;

public class LichSuDKQM_Item {
	
	public String effectiveDate;
	public String id;
	public String note;
	public String orderType;
	public String placingOrderDate;
	public String status;
	public String symbol;
	public String volume;

	public LichSuDKQM_Item(HashMap<String, String> hm) {
		this.id = hm.get("Id");
		this.placingOrderDate = hm.get("PlacingOrderDate");
		this.effectiveDate = hm.get("EffectiveDate");
		this.orderType = hm.get("OrderType");
		this.symbol = hm.get("Symbol");
		this.volume = hm.get("Volume");
		this.status = hm.get("Status");
		this.note = hm.get("Note");
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.adapter.LichSuDKQM_Item JD-Core Version: 0.6.0
 */