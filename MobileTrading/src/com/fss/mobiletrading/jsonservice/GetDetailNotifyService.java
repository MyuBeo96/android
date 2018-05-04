package com.fss.mobiletrading.jsonservice;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.function.notify.NotifyItem;
import com.fss.mobiletrading.object.ResultObj;

public class GetDetailNotifyService extends AbstractProcessJsonObjectService{

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		NotifyItem notifyItem = new NotifyItem(DT.getString("Short"),
				DT.getString("Content"));
		return new ResultObj(EC, EM, notifyItem);
	}

}
