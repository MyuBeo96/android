package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.object.DanhSachChamNguongItem;
import com.fss.mobiletrading.object.ResultObj;

public class GetAllAlertResultService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		// TODO Auto-generated method stub
		List<DanhSachChamNguongItem> list = new ArrayList<DanhSachChamNguongItem>();
		if (DT.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		for (int i = 0; i < DT.length(); i++) {
			list.add(new DanhSachChamNguongItem(DT.getJSONObject(i)
					.getHashMap()));
		}
		return new ResultObj(EC, EM, list);
	}

}
