package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.AcctnoDetail;
import com.fss.mobiletrading.object.ResultObj;

public class FindRecAcctnoDetailService extends
		AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM,int EC) {
		AcctnoDetail acctnoDetail = new AcctnoDetail(DT.getHashMap());
		return new ResultObj(EC, EM, acctnoDetail);
	}

}
