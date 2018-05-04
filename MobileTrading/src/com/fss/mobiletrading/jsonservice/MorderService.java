package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.SolenhItem;

public class MorderService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		List<SolenhItem> list = new ArrayList<SolenhItem>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			list.add(new SolenhItem(DT.getJSONObject(i).getHashMap()));
		}
		return new ResultObj(EC, EM, list);
	}

}
