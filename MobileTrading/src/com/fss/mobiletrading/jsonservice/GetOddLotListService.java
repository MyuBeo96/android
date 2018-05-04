package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.oddorderregister.OddLotItem;
import com.fss.mobiletrading.object.ResultObj;

public class GetOddLotListService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		// TODO Auto-generated method stub
		List<OddLotItem> list = new ArrayList<OddLotItem>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			MyJsonObject obj = DT.getJSONObject(i);
			if (obj != null) {
				list.add(new OddLotItem(StringConst.EMPTY, StringConst.EMPTY, obj.getString("SYMBOL"), obj
						.getString("QUANTITY"), obj.getString("QUOTEPRICE"),
						obj.getString("AMOUNT"), obj.getString("FEEAMT"), obj
								.getString("TAXAMT"), obj.getString("RCVAMT")));
			}

		}
		return new ResultObj(EC, EM, list);
	}
}
