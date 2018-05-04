package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.function.rightoffregister.RightOffRegisterItem;
import com.fss.mobiletrading.object.ResultObj;

public class RightOffRegisterService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		MyJsonArray rightOffs = DT.getJSONArray("rightOffs");
		List<RightOffRegisterItem> list_DkqmItems = new ArrayList<RightOffRegisterItem>();
		for (int i = 0; i < rightOffs.length(); i++) {
			list_DkqmItems.add(new RightOffRegisterItem(rightOffs.getJSONObject(i)
					.getHashMap()));
		}
		return new ResultObj(EC, EM, list_DkqmItems);
	}
}
