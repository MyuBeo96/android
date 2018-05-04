package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.object.LichSuCT_Item;
import com.fss.mobiletrading.object.ResultObj;

public class CashTransferStatementService extends
		AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		// TODO Auto-generated method stub
		List<LichSuCT_Item> list = new ArrayList<LichSuCT_Item>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			list.add(new LichSuCT_Item(DT.getJSONObject(i).getHashMap()));
		}
		return new ResultObj(EC, EM, list);
	}

}
