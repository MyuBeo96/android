package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.ConBankAccDetail;
import com.fss.mobiletrading.object.ResultObj;

public class FindConBankAccDetailService extends
		AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM,int EC) {
		// TODO Auto-generated method stub
		ConBankAccDetail conBankAccDetail = new ConBankAccDetail(
				DT.getHashMap());
		return new ResultObj(EC, EM, conBankAccDetail);
	}

}
