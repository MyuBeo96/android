package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.object.ResultObj;

public class GetNewsDetailsService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		NewsItem newsItem = new NewsItem(DT.getString("Title"),
				DT.getString("Content"));
		return new ResultObj(EC, EM, newsItem);
	}

}
