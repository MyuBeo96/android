package com.fss.mobiletrading.function.pastplaceorder;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonArrayService;

import java.util.ArrayList;
import java.util.List;
import com.fss.mobiletrading.object.ResultObj;


public class PastPlaceOrderParse extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		List<PastPlaceOrderItem> list = new ArrayList<PastPlaceOrderItem>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		int DTlength = DT.length();
		for (int i = 0; i < DTlength; i++) {
			list.add(new PastPlaceOrderItem(DT.getJSONObject(i).getHashMap()));
		}
		return new ResultObj(EC, EM, list);
	}

}
