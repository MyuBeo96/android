package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.map.BankMapPositionItem;
import com.fss.mobiletrading.object.ResultObj;

public class MMapService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		List<BankMapPositionItem> list = new ArrayList<BankMapPositionItem>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			list.add(new BankMapPositionItem(DT.getJSONObject(i).getHashMap()));
		}
		return new ResultObj(EC, EM, list);
	}

}
