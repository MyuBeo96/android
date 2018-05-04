package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.AccountInfo;
import com.fss.mobiletrading.object.ResultObj;

public class AccountInformationService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		AccountInfo accountInfo = new AccountInfo(DT.getHashMap());
		return new ResultObj(EC, EM, accountInfo);
	}

}
