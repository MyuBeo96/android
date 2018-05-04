package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.FindBankAccountNoItem;
import com.fss.mobiletrading.object.ResultObj;

public class FindBankAccountnoService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		FindBankAccountNoItem item = new FindBankAccountNoItem(DT.getHashMap());
		return new ResultObj(EC, EM, item);
	}
}
