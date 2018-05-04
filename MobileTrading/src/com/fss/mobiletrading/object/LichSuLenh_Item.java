package com.fss.mobiletrading.object;

import java.util.HashMap;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.consts.StringConst;

public class LichSuLenh_Item {
	private String CommisionFee;
	private String Date;
	private String FeeRate;
	private String OrderId;
	private String Pending;
	private String PitAmount;
	private String Price;
	private String Qty;
	private String RecvAmt;
	private String STT;
	private String SellTaxAmt;
	private String Symbol;
	private String TransferAmt;
	private String Value;
	private String Via;
	private String Side;
	private String SideDesc;

	public LichSuLenh_Item(HashMap<String, String> hm) {
		STT = hm.get("STT");
		OrderId = hm.get("OrderId");
		Date = hm.get("Date");
		Symbol = hm.get("Symbol");
		Qty = hm.get("Qty");
		Price = hm.get("Price");
		Value = hm.get("Value");
		CommisionFee = hm.get("CommisionFee");
		PitAmount = hm.get("PitAmount");
		Pending = hm.get("Pending");
		FeeRate = hm.get("FeeRate");
		TransferAmt = hm.get("TransferAmt");
		RecvAmt = hm.get("RecvAmt");
		SellTaxAmt = hm.get("SellTaxAmt");
		Via = hm.get("Via");
		Side = hm.get("Side");
		SideDesc = hm.get("SideDesc");
	}

	public LichSuLenh_Item() {
	}

	public String getVia() {
		return Via;
	}

	public String getCommisionFee() {
		return CommisionFee;
	}

	public String getDate() {
		return Date;
	}

	public String getFeeRate() {
		return FeeRate;
	}

	public String getOrderId() {
		return OrderId;
	}

	public String getPending() {
		return Pending;
	}

	public String getPitAmount() {
		return PitAmount;
	}

	public String getPrice() {
		return Price;
	}

	public String getQty() {
		return Qty;
	}

	public String getRecvAmt() {
		return RecvAmt;
	}

	public String getSTT() {
		return STT;
	}

	public String getSellTaxAmt() {
		return SellTaxAmt;
	}

	public String getSymbol() {
		return Symbol;
	}

	public String getTransferAmt() {
		return TransferAmt;
	}

	public String getValue() {
		return Value;
	}

	public String getSide() {
		return Side;
	}

	public String getSideDesc() {
		return SideDesc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(Symbol.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(Side.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(SideDesc.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(Via.toLowerCase()));
		builder.append(StringConst.separator_thang);

		return builder.toString();
	}
}
