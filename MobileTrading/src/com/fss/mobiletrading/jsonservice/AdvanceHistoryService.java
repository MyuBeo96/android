package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.object.LichSuUT_Item;
import com.fss.mobiletrading.object.ResultObj;

public class AdvanceHistoryService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		// TODO Auto-generated method stub
		List<LichSuUT_Item> list = new ArrayList<LichSuUT_Item>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			list.add(new LichSuUT_Item(DT.getJSONObject(i).getString("date"),
					DT.getJSONObject(i).getString("recv_money"), DT
							.getJSONObject(i).getString("fee_money")));
		}
		return new ResultObj(EC, EM, list);
	}

}
