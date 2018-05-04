package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.object.MoneyInfoItem;
import com.fss.mobiletrading.object.ResultObj;

public class GetMoneyInfoService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {

		if (DT.length() == 0) {
			return new ResultObj(0, EM, null);
		}
		MoneyInfoItem item = null;
		item = new MoneyInfoItem(DT.getJSONObject(0).getHashMap());
		return new ResultObj(EC, EM, item);
	}
}
