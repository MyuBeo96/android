package com.fss.mobiletrading.function.searchstock;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonArrayService;
import com.fss.mobiletrading.object.ResultObj;

public class StockInfoService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		// TODO Auto-generated method stub
		List<SearchStockItem> list = new ArrayList<SearchStockItem>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			MyJsonObject child = DT.getJSONObject(i);
			list.add(new SearchStockItem(child.getHashMap()));
		}
		return new ResultObj(EC, EM, list);
	}

}
