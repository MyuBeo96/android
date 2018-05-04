package com.fss.mobiletrading.object;

import java.util.HashMap;

public class UngtruocItem {
	public String Afacctno;
	public String IsAdvance;
	public String advance_money;
	public String advanced_money;
	public String err_code;
	public String err_msg;
	public String fee;
	public String fee_money;
	public String receive_money;
	public String total_available_money;
	public String total_sell_money;

	public UngtruocItem(HashMap<String, String> hm) {
		IsAdvance = hm.get("IsAdvance");
		total_sell_money = hm.get("total_sell_money");
		total_available_money = hm.get("total_available_money");
		advanced_money = hm.get("advanced_money");
		advance_money = hm.get("advance_money");
		receive_money = hm.get("receive_money");
		fee_money = hm.get("fee_money");
		Afacctno = hm.get("Afacctno");
		err_code = hm.get("err_code");
		err_msg = hm.get("err_msg");
		fee = hm.get("fee");
	}
}
