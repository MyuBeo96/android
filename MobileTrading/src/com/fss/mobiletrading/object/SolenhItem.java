package com.fss.mobiletrading.object;

import java.io.Serializable;
import java.util.HashMap;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;

public class SolenhItem implements Serializable {
	public String AfAcctno;
	public String BalanceInfo;
	public String BuyingInfo;
	public String CancelQtty;
	public String CustodyCd;
	public String Matched;
	public String MatchedPrice;
	public String ModifiedPrice;
	public String ModifiedQtty;
	public String OrderId;
	public String Price;
	public String PriceInfo;
	public String PriceType;
	public String Qtty;
	public String RemainQtty;
	public String RootOrderId;
	public String SEInfo;
	public String SelectedSymbol;
	public String Side;
	public String Status;
	public String Symbol;
	public String Time;
	public String TradingPassword;
	public String WarningMessage;
	public String feedbackMsg;
	public String isCancellable;
	public String isModifiable;
	public String orderIDDesc;
	public String fromDate;
	public String toDate;
	public String statusValue;
	public String ErrorCode;

	public SolenhItem(HashMap<String, String> hm) {
		OrderId = hm.get("OrderId");
		CustodyCd = hm.get("CustodyCd");
		AfAcctno = hm.get("AfAcctno");
		Symbol = hm.get("Symbol");
		PriceType = hm.get("PriceType");
		Price = hm.get("Price");
		Qtty = hm.get("Qtty");
		Side = hm.get("Side");
		Matched = hm.get("Matched");
		MatchedPrice = hm.get("MatchedPrice");
		ModifiedQtty = hm.get("ModifiedQtty");
		ModifiedPrice = hm.get("ModifiedPrice");
		Status = hm.get("Status");
		TradingPassword = hm.get("TradingPassword");
		SelectedSymbol = hm.get("SelectedSymbol");
		PriceInfo = hm.get("PriceInfo");
		BalanceInfo = hm.get("BalanceInfo");
		BuyingInfo = hm.get("BuyingInfo");
		SEInfo = hm.get("SEInfo");
		WarningMessage = hm.get("WarningMessage");
		RootOrderId = hm.get("RootOrderId");
		orderIDDesc = hm.get("orderIDDesc");
		RemainQtty = hm.get("RemainQtty");
		CancelQtty = hm.get("CancelQtty");
		statusValue = hm.get("statusValue");
		feedbackMsg = hm.get("feedbackMsg");
		Time = hm.get("Time");
		isModifiable = hm.get("isModifiable");
		isCancellable = hm.get("isCancellable");
		fromDate = hm.get("fromDate");
		toDate = hm.get("toDate");
		ErrorCode = hm.get("ErrorCode");
	}

	final static String MUA = "mua";
	final static String BAN = "ban";

	@Override
	public String toString() {
		String side = null;
		if (Side.equals(PlaceOrder.SIDE_NB)) {
			side = MUA;
		} else {
			side = BAN;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(CustodyCd.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(AfAcctno.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(side);
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(Symbol.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(Qtty.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(Price.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(Matched.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(MatchedPrice.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(Status.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(Common.convertUTF8ToLatin(PriceType.toLowerCase()));
		builder.append(StringConst.separator_thang);
		builder.append(fromDate);
		builder.append(StringConst.separator_thang);
		builder.append(toDate);

		return builder.toString();
	}
}
