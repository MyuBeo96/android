package com.fss.mobiletrading.function.stocktransfer;

import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.ItemString;

public class StockTransferItem {
	AcctnoItem senderAfacctno;
	ItemString beneficiaryAfacctno;
	String symbol;
	String available;
	String amount;

	public StockTransferItem(AcctnoItem senderAfacctno,
			ItemString beneficiaryAfacctno, String symbol, String available,
			String amount) {
		super();
		this.senderAfacctno = senderAfacctno;
		this.beneficiaryAfacctno = beneficiaryAfacctno;
		this.symbol = symbol;
		this.available = available;
		this.amount = amount;
	}

	public AcctnoItem getSenderAfacctno() {
		return senderAfacctno;
	}

	public ItemString getBeneficiaryAfacctno() {
		return beneficiaryAfacctno;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getAvailable() {
		return available;
	}

	public String getAmount() {
		return amount;
	}

}
