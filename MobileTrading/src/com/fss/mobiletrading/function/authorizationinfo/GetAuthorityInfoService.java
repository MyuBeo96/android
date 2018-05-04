package com.fss.mobiletrading.function.authorizationinfo;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonObjectService;
import com.fss.mobiletrading.object.AuthorizationInfoItem;
import com.fss.mobiletrading.object.ResultObj;

public class GetAuthorityInfoService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		List<AuthorizationInfoItem> list_autho = new ArrayList<AuthorizationInfoItem>();
		List<PermissionInfoItem> list_permiss = new ArrayList<PermissionInfoItem>();
		Object[] array = { list_autho, list_permiss };
		if (DT.length() == 0) {
			return new ResultObj(0, EM, array);
		}
		MyJsonArray autho = DT.getJSONArray("DI");
		MyJsonArray permiss = DT.getJSONArray("DR");
		for (int i = 0; i < autho.length(); i++) {
			list_autho.add(new AuthorizationInfoItem(autho.getJSONObject(i)
					.getHashMap()));
		}
		for (int i = 0; i < permiss.length(); i++) {
			list_permiss.add(new PermissionInfoItem(permiss.getJSONObject(i)
					.getHashMap()));
		}
		return new ResultObj(EC, EM, array);
	}
}
