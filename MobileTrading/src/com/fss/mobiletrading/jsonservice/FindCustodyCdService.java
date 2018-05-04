package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.ResultObj;

public class FindCustodyCdService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		// TODO Auto-generated method stub
		List<AcctnoItem> list = new ArrayList<AcctnoItem>();
		if (DT.length() == 0) {
			return new ResultObj(EC, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			list.add(new AcctnoItem(DT.getJSONObject(i).getHashMap()));
		}
		return new ResultObj(EC, EM, list);
	}
}
