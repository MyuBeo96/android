package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.function.rightoffregister.RightOffRegisterItem;
import com.fss.mobiletrading.object.ResultObj;

public class RightOffRegisterDetailsService extends
		AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		return new ResultObj(EC, EM, new RightOffRegisterItem(DT.getHashMap()));
	}
}
