package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.function.portfolio.PorfolioItem;
import com.fss.mobiletrading.object.ResultObj;

public class GetPorfolioService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		List<PorfolioItem> list = new ArrayList<PorfolioItem>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			list.add(new PorfolioItem(DT.getJSONObject(i).getHashMap()));
		}
		return new ResultObj(EC, EM, list);
	}

}
