package com.fss.mobiletrading.jsonservice;

import org.json.JSONException;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.FindStock;
import com.fss.mobiletrading.object.OrderInfoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.StockInfoItem;

public class FindStockService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM,int EC)
			 {
		FindStock findStock = new FindStock(new OrderInfoItem(DT.getJSONObject(
				"orderInfo").getHashMap()), new StockInfoItem(DT.getJSONObject(
				"stockInfo").getHashMap()));
		return new ResultObj(EC, EM, findStock);
	}

}
