package com.fss.mobiletrading.object;

import java.util.ArrayList;
import java.util.List;

public class FindStockSenAccObj {
	private List<ItemString> acctnoList;
	private List<ChuyenKhoanCK_Item> stockList;
	private List<ItemString> stockRec;

	public FindStockSenAccObj(List<ItemString> acctnoList,
			List<ChuyenKhoanCK_Item> stockList, List<ItemString> stockRec) {
		super();
		this.acctnoList = acctnoList;
		this.stockList = stockList;
		this.stockRec = stockRec;
	}

	public List<ItemString> getAcctnoList() {
		return this.acctnoList;
	}

	public List<ChuyenKhoanCK_Item> getStockList() {
		// Lọc các mã chứng khoán chờ về (trade =0)
		List<ChuyenKhoanCK_Item> filterList = new ArrayList<ChuyenKhoanCK_Item>();
		for (ChuyenKhoanCK_Item item : stockList) {
			if (!item.TRADE.matches("0")) {
				filterList.add(item);
			}
		}
		return filterList;
	}

	public List<ItemString> getStockRec() {
		return this.stockRec;
	}

	public void setAcctnoList(List<ItemString> paramList) {
		this.acctnoList = paramList;
	}

	public void setStockList(List<ChuyenKhoanCK_Item> paramList) {
		this.stockList = paramList;
	}

	public void setStockRec(List<ItemString> paramList) {
		this.stockRec = paramList;
	}
}
