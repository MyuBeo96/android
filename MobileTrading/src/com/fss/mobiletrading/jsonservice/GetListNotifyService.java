package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.function.notify.NotifyItem;
import com.fss.mobiletrading.object.ResultObj;

public class GetListNotifyService extends AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		List<NotifyItem> list = new ArrayList<NotifyItem>();
		NotifySequence notifySequence = new NotifySequence(list,
				DT.getString("lastSeq"));
		if (DT.getJSONArray("items").length() == 0) {
			return new ResultObj(0, EM, notifySequence);
		}
		if ("null".equals(DT.getJSONArray("items"))) {
			return new ResultObj(0, EM, null);
		}
		for (int i = 0; i < DT.getJSONArray("items").length(); i++) {
			list.add(new NotifyItem(DT.getJSONArray("items").getJSONObject(i)
					.getHashMap()));
		}
		return new ResultObj(EC, EM, notifySequence);
	}

	public class NotifySequence {
		List<NotifyItem> list;
		String lastSeq;

		public NotifySequence(List<NotifyItem> list, String lastSeq) {
			this.list = list;
			this.lastSeq = lastSeq;
		}

		public List<NotifyItem> getList() {
			return list;
		}

		public void setList(List<NotifyItem> list) {
			this.list = list;
		}

		public String getLastSeq() {
			return lastSeq;
		}

		public void setLastSeq(String lastSeq) {
			this.lastSeq = lastSeq;
		}

	}
}
