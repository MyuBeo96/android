package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.ItemString2;
import com.fss.mobiletrading.object.ResultObj;

public class FindBranchsService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		MyJsonArray Dst = DT.getJSONArray("Dst");
		List<ItemString2> list = new ArrayList<ItemString2>();

		for (int i = 0; i < Dst.length(); i++) {
			MyJsonObject obj = Dst.getJSONObject(i);
			list.add(new ItemString2(obj.getString("Text"), obj
					.getString("Value")));
		}
		return new ResultObj(EC, EM, list);
	}
}
