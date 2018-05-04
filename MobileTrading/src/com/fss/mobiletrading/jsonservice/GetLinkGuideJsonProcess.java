package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.ResultObj;

public class GetLinkGuideJsonProcess extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		return new ResultObj(EC, EM, DT.getString("d"));
	}
}
