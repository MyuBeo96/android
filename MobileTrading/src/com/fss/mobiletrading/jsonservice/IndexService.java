package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.IndicesItem;
import com.fss.mobiletrading.object.ResultObj;

public class IndexService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {

		MyJsonArray ListIndices = DT.getJSONArray("ListIndices");
		List<IndicesItem> list_IndicesItems = new ArrayList<IndicesItem>();
		for (int i = 0; i < ListIndices.length(); i++) {
			list_IndicesItems.add(new IndicesItem(ListIndices.getJSONObject(i)
					.getHashMap()));
		}
		return new ResultObj(EC, EM, list_IndicesItems);
	}
}
