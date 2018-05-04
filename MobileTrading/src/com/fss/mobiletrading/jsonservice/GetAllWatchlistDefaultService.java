package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.fss.mobiletrading.common.MeasureTime;
import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.BangGia_Item;
import com.fss.mobiletrading.object.ResultObj;

public class GetAllWatchlistDefaultService extends
		AbstractProcessJsonObjectService {

	@Override
	public ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		List<BangGia_Item> list = new ArrayList<BangGia_Item>();
		String LS = DT.getString("LS");

		MyJsonArray WL = DT.getJSONArray("WL");

		if (WL.length() == 0) {
			return new ResultObj(0, EM, list);
		}
		int length = WL.length();
		for (int i = 0; i < length; i++) {

			// MyJsonObject obj = WL.getJSONObject(i);
			// list.add(new BangGia_Item(obj.getString("BidP1"), obj
			// .getString("BidP2"), obj.getString("BidP3"), obj
			// .getString("BidV1"), obj.getString("BidV2"), obj
			// .getString("BidV3"), obj.getString("CeilingPrice"), obj
			// .getString("Change"), obj.getString("ClosePrice"), obj
			// .getString("FloorPrice"), obj.getString("OffP1"), obj
			// .getString("OffP2"), obj.getString("OffP3"), obj
			// .getString("OffV1"), obj.getString("OffV2"), obj
			// .getString("OffV3"), obj.getString("Percent"), obj
			// .getString("RefPrice"), obj.getString("StockName"), obj
			// .getString("Symbol"), obj.getString("closeVol"), obj
			// .getString("totalTrading"), obj.getString("totalVal"), LS));

			list.add(new BangGia_Item(WL.getString(i), LS));

		}
		return new ResultObj(EC, EM, list);
	}
}
