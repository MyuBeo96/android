package com.fss.mobiletrading.function.watchlist;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonObjectService;
import com.fss.mobiletrading.object.ResultObj;

public class ListCriteriaService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		List<FavWatchListItem> listFav = new ArrayList<FavWatchListItem>();
		MyJsonArray array = DT.getJSONArray("Subs");
		for (int i = 0; i < array.length(); i++) {
			listFav.add(new FavWatchListItem(array.getJSONObject(i)
					.getHashMap()));
		}
		return new ResultObj(EC, EM, listFav);
	}

}
