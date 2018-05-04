package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.LoginItem;
import com.fss.mobiletrading.object.ResultObj;

public class LoginServiceJsonProcess extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		MyJsonArray contractList = DT.getJSONArray("contractList");
		List<AcctnoItem> list_AcctnoItems = new ArrayList<AcctnoItem>();
		for (int i = 0; i < contractList.length(); i++) {
			list_AcctnoItems.add(new AcctnoItem(contractList.getJSONObject(i)
					.getHashMap()));
		}

		LoginItem loginItem = new LoginItem(DT.getString("CurrentAcctno"),
				DT.getString("Custodycd"), DT.getString("CustName"),
				DT.getString("CustEmail"), DT.getString("UserName"),
				DT.getString("TxDateString"), DT.getString("Language"),
				DT.getString("IsBroker"), DT.getString("Class"),
				DT.getString("Agent"), DT.getString("Mobile"), list_AcctnoItems);

		return new ResultObj(EC, EM, loginItem);
	}
}
