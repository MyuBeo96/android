package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.UngtruocItem;

public class FindAdvanceByAffaccnoService extends
		AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		return new ResultObj(EC, EM, new UngtruocItem(DT.getHashMap()));
	}

}
