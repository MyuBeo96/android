package com.fss.mobiletrading.function.report;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonObjectService;
import com.fss.mobiletrading.object.ResultObj;

public class ReportParser2 extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		String data = null;
		data = DT.getString("h");
		return new ResultObj(EC, EM, data);
	}

}
