package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.object.ResultObj;

public class NewsService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		List<NewsItem> list = new ArrayList<NewsItem>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			list.add(new NewsItem(DT.getJSONObject(i).getHashMap()));
		}
		return new ResultObj(EC, EM, list);
	}

}
