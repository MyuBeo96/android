package com.fss.mobiletrading.function.watchlist;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonObjectService;
import com.fss.mobiletrading.object.ResultObj;

public class GetWLSymbolsParse extends AbstractProcessJsonObjectService{

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		String d = DT.getString("d");
		return new ResultObj(EC, EM, d);
	}

}
