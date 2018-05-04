package com.fss.mobiletrading.function.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonObjectService;
import com.fss.mobiletrading.object.ItemString2;
import com.fss.mobiletrading.object.ResultObj;

public class GetDataSourceParse extends AbstractProcessJsonObjectService {
	static final String key = "Key";
	static final String val = "Val";

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		List<ItemString2> list = new ArrayList<ItemString2>();
		String lastSeq = DT.getString("lastSeq");
		list.add(new ItemString2(lastSeq, lastSeq));
		MyJsonArray array = DT.getJSONArray("d");
		int arrayLength = array.length();
		for (int i = 0; i < arrayLength; i++) {
			MyJsonObject item = array.getJSONObject(i);
			list.add(new ItemString2(item.getString(val), item.getString(key)));
		}

		return new ResultObj(EC, EM, list);
	}

}
