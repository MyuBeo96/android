package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.ChuyenKhoanCK_Item;
import com.fss.mobiletrading.object.FindStockSenAccObj;
import com.fss.mobiletrading.object.ItemString;
import com.fss.mobiletrading.object.ResultObj;

public class FindStockSenAccService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {

		MyJsonArray acctnoListArray = DT.getJSONArray("AcctnoList");
		List<ItemString> acctnoList = new ArrayList<ItemString>();
		for (int i = 0; i < acctnoListArray.length(); i++) {
			acctnoList.add(new ItemString(acctnoListArray.getJSONObject(i)
					.getString("Text"), acctnoListArray.getJSONObject(i)
					.getString("Value")));
		}
		MyJsonArray StockListArray = DT.getJSONArray("StockList");
		List<ChuyenKhoanCK_Item> StockList = new ArrayList<ChuyenKhoanCK_Item>();
		for (int i = 0; i < StockListArray.length(); i++) {
			StockList.add(new ChuyenKhoanCK_Item(StockListArray
					.getJSONObject(i).getHashMap()));
		}
		MyJsonArray stockRecArray = DT.getJSONArray("StockRec");
		List<ItemString> stockRec = new ArrayList<ItemString>();
		for (int i = 0; i < stockRecArray.length(); i++) {
			stockRec.add(new ItemString(stockRecArray.getJSONObject(i)
					.getString("Text"), stockRecArray.getJSONObject(i)
					.getString("Value")));
		}
		return new ResultObj(EC, EM, new FindStockSenAccObj(acctnoList,
				StockList, stockRec));
	}
}
