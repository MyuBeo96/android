package com.fss.mobiletrading.function.watchlist;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonObjectService;
import com.fss.mobiletrading.object.ResultObj;

public class GetDefaultWatchlistService extends
		AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		String criteriaId = DT.getString("CriteriaId");
		String cName = DT.getString("CName");

		return new ResultObj(EC, EM, new FavWatchListItem(criteriaId, cName));
	}
}
