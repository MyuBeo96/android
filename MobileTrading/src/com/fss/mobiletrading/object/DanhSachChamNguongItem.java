package com.fss.mobiletrading.object;

import java.util.HashMap;

public class DanhSachChamNguongItem {
	public String ALERTDT;
	public String ALERTID;
	public String ALERTTYPE;
	public String AUTOID;
	public String CUSTID;
	public String EMAILSTATUS;
	public String ISEMAIL;
	public String ISREAD;
	public String MESSAGE;
	public String SYMBOLS;
	public String TRADINGDATE;

	public DanhSachChamNguongItem() {
	}

	public DanhSachChamNguongItem(HashMap<String, String> paramHashMap) {
		this.AUTOID = ((String) paramHashMap.get("AUTOID"));
		this.ALERTID = ((String) paramHashMap.get("ALERTID"));
		this.ALERTTYPE = ((String) paramHashMap.get("ALERTTYPE"));
		this.ALERTDT = ((String) paramHashMap.get("ALERTDT"));
		this.SYMBOLS = ((String) paramHashMap.get("SYMBOLS"));
		this.MESSAGE = ((String) paramHashMap.get("MESSAGE"));
		this.ISEMAIL = ((String) paramHashMap.get("ISEMAIL"));
		this.ISREAD = ((String) paramHashMap.get("ISREAD"));
		this.EMAILSTATUS = ((String) paramHashMap.get("EMAILSTATUS"));
		this.TRADINGDATE = ((String) paramHashMap.get("TRADINGDATE"));
		this.CUSTID = ((String) paramHashMap.get("CUSTID"));
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.DanhSachChamNguongItem JD-Core Version: 0.6.0
 */