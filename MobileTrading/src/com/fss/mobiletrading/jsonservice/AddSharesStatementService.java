package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.object.LichSuDKQM_Item;
import com.fss.mobiletrading.object.ResultObj;

public class AddSharesStatementService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		// TODO Auto-generated method stub
		List<LichSuDKQM_Item> list = new ArrayList<LichSuDKQM_Item>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}

		for (int i = 0; i < DT.length(); i++) {
			list.add(new LichSuDKQM_Item(DT.getJSONObject(i).getHashMap()));
		}
		return new ResultObj(EC, EM, list);
	}

}
