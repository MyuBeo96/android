package com.fss.mobiletrading.function.report;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonArrayService;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonObjectService;
import com.fss.mobiletrading.object.BRRptItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.RptFieldItem;

public class GetBRListParse extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		List<BRRptItem> list_BrRptItems = new ArrayList<BRRptItem>();
		if (DT == null || DT.length() == 0) {

		} else {
			int bRRptLength = DT.length();
			for (int i = 0; i < bRRptLength; i++) {
				List<RptFieldItem> list_RptFieldItems = new ArrayList<RptFieldItem>();
				MyJsonObject BRRptItem = DT.getJSONObject(i);
				MyJsonArray RptField = BRRptItem.getJSONArray("RptField");
				int rptFieldLength = RptField.length();
				for (int j = 0; j < rptFieldLength; j++) {
					MyJsonObject rptFieldItem = RptField.getJSONObject(j);
					MyJsonArray FSet = null;
					try {
						FSet = new MyJsonArray(rptFieldItem.getString("FSet"));
					} catch (JSONException e) {
					} catch (NullPointerException e) {
					}

					try {
						list_RptFieldItems.add(new RptFieldItem(rptFieldItem
								.getString("FName"), rptFieldItem
								.getString("FValue"), rptFieldItem
								.getString("FTitle"), rptFieldItem
								.getString("FType"), rptFieldItem
								.getString("FSearch"), rptFieldItem
								.getString("FFilter"), rptFieldItem
								.getBoolean("Visible"), rptFieldItem
								.getBoolean("Disable"), FSet));
					} catch (JSONException e) {
					}
				}
				list_BrRptItems.add(new BRRptItem(BRRptItem
						.getString("RptName"), BRRptItem.getString("RptId"),
						BRRptItem.getString("RptSource"), list_RptFieldItems));
			}
		}
		return new ResultObj(EC, EM, list_BrRptItems);
	}

}
