package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.FindAfacctnosItem;
import com.fss.mobiletrading.object.ItemString;
import com.fss.mobiletrading.object.ResultObj;

public class FindAfacctnosService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		List<ItemString> list = new ArrayList<ItemString>();
		MyJsonArray Dst = DT.getJSONArray("Dst");
		for (int i = 0; i < Dst.length(); i++) {
			MyJsonObject obj = Dst.getJSONObject(i);
			list.add(new ItemString(obj.getString("Text"), obj
					.getString("Value")));
		}
		FindAfacctnosItem item = new FindAfacctnosItem(DT.getJSONObject("Src")
				.getHashMap(), list);
		return new ResultObj(EC, EM, item);
	}
}
