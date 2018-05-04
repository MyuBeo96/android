package com.fss.mobiletrading.function.cashtransfer;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonObjectService;
import com.fss.mobiletrading.object.ResultObj;

public class GetTransferFeeAndTotalService extends
		AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		return new ResultObj(EC, EM, new String[]{
				DT.getString("FEE"),
				DT.getString("TOTAL")
		});
	}

}
