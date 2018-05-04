package com.fss.mobiletrading.function.cashtransfer;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonObjectService;
import com.fss.mobiletrading.object.ResultObj;

public class GetCashTransferInfoService extends
		AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		return new ResultObj(EC, EM, new CashTransferInfo1(
				DT.getString("BALANCE"), DT.getString("ADVAMT"),
				DT.getString("FEEAMT"), DT.getString("AVLWITHDRAW")));
	}

}
