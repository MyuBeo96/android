package com.fss.mobiletrading.object;

import java.util.HashMap;

public class DanhSachCanhBaoItem {
	public String Condition;
	public String CreateDate;
	public String Symbols;
	public String id;

	public DanhSachCanhBaoItem() {
	}

	public DanhSachCanhBaoItem(HashMap<String, String> paramHashMap) {
		this.id = ((String) paramHashMap.get("id"));
		this.Condition = ((String) paramHashMap.get("Condition"));
		this.Symbols = ((String) paramHashMap.get("Symbols"));
		this.CreateDate = ((String) paramHashMap.get("CreateDate"));
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.DanhSachCanhBaoItem JD-Core Version: 0.6.0
 */