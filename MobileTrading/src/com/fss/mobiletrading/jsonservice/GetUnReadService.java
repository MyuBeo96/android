package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.ResultObj;

public class GetUnReadService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		int unRead = DT.getInt("Count");
		return new ResultObj(EC, EM, unRead);
	}

}
