package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.OrderDetailsItem;
import com.fss.mobiletrading.object.ResultObj;

public class OrderDetailsService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		MyJsonArray OrderDetails = DT.getJSONArray("OrderDetails");
		List<OrderDetailsItem> list_OrderDetailsItems = new ArrayList<OrderDetailsItem>();
		for (int i = 0; i < OrderDetails.length(); i++) {
			list_OrderDetailsItems.add(new OrderDetailsItem(OrderDetails
					.getJSONObject(i).getHashMap()));
		}
		return new ResultObj(EC, EM, list_OrderDetailsItems);
	}
}
