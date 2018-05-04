package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.object.ListFavItem;
import com.fss.mobiletrading.object.ResultObj;

public class ListFavService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		// TODO Auto-generated method stub
		List<ListFavItem> list = new ArrayList<ListFavItem>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			list.add(new ListFavItem(DT.getJSONObject(i)
					.getString("favGroupId"), DT.getJSONObject(i).getString(
					"favGroupName"), DT.getJSONObject(i).getString(
					"favGroupMem")));
		}
		return new ResultObj(EC, EM, list);
	}
}
