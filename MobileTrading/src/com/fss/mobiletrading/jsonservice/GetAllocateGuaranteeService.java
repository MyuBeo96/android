package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.allocateguarantee.AllocateInfo;
import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.ResultObj;

public class GetAllocateGuaranteeService extends
		AbstractProcessJsonObjectService {
//	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
//		if (DT.length() == 0) {
//			return new ResultObj(0, EM, null);
//		}
//		AllocateInfo item = null;
//		item = new AllocateInfo(DT.getJSONObject(0).getHashMap());
//		return new ResultObj(EC, EM, item);
//	}

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		// TODO Auto-generated method stub
		if (DT.length() == 0) {
			return new ResultObj(0, EM, null);
		}
		AllocateInfo a = new AllocateInfo(DT.getHashMap());
		return new ResultObj(EC, EM, a);
	}
}
