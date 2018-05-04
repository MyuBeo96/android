package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.CashStatementItem;

public class CashStatementService extends AbstractProcessJsonArrayService {
	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		List<CashStatementItem> list = new ArrayList<CashStatementItem>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			list.add(new CashStatementItem(DT.getJSONObject(i).getHashMap()));
		}
		return new ResultObj(EC, EM, list);
	}
}
