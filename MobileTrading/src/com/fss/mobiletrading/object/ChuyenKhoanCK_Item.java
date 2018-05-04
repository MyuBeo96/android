package com.fss.mobiletrading.object;

import java.util.HashMap;

public class ChuyenKhoanCK_Item {
	public String SYMBOL;
	public String TRADE;
	public String BLOCKED;
	public String AFACCTNO;

	public ChuyenKhoanCK_Item(HashMap<String, String> hm) {
		SYMBOL = hm.get("SYMBOL");
		TRADE = hm.get("TRADE");
		BLOCKED = hm.get("BLOCKED");
		AFACCTNO = hm.get("AFACCTNO");
	}

	public String toString() {
		return SYMBOL;
	}
}
