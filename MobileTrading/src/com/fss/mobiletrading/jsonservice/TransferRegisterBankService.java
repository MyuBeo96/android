package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.ItemString2;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.TransferRegisterBankItem;

public class TransferRegisterBankService extends
		AbstractProcessJsonObjectService {

	@Override
	protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
		List<ItemString2> list = new ArrayList<ItemString2>();
		MyJsonArray Bank = DT.getJSONArray("Bank");
		for (int i = 0; i < Bank.length(); i++) {
			list.add(new ItemString2(Bank.getJSONObject(i).getString("Text"),
					Bank.getJSONObject(i).getString("Value")));
		}
		TransferRegisterBankItem item = new TransferRegisterBankItem(DT
				.getJSONObject("Src").getHashMap(), list);
		return new ResultObj(EC, EM, item);
	}
}
