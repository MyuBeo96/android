package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.function.guaranteelist.GuaranteeItem;
import com.fss.mobiletrading.object.ResultObj;

public class GetGuaranteeListService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		List<GuaranteeItem> list = new ArrayList<GuaranteeItem>();
		if (DT != null && DT.length() > 0) {
			GuaranteeItem item = null;
			item = new GuaranteeItem(DT.getJSONObject(0).getHashMap());
			list.add(item);
		}
		return new ResultObj(EC, EM, list);
	}

	// @Override
	// protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
	// if(DT.length() == 0){
	// return new ResultObj(0,EM,null);
	// }
	// GuaranteeItem item = null;
	// item = new GuaranteeItem(DT.getHashMap());
	// return new ResultObj(EC,EM,item);
	//
	// }

}
