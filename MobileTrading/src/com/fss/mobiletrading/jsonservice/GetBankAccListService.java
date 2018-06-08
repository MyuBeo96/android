package com.fss.mobiletrading.jsonservice;

import java.util.ArrayList;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.object.BankAccItem;
import com.fss.mobiletrading.object.BankAccList;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.MSTradeAppConfig;

public class GetBankAccListService extends AbstractProcessJsonArrayService {

	@Override
	public ResultObj processArray(MyJsonArray DT, String EM, int EC) {
		// TODO Auto-generated method stub

		List<BankAccItem> listAcc = new ArrayList<BankAccItem>();
		List<BankAccItem> listBank = new ArrayList<BankAccItem>();
		List<BankAccItem> listBankMSB = new ArrayList<BankAccItem>();

		if (DT.length() == 0) {
			return new ResultObj(EC, EM, new BankAccList(listBank, listAcc,
					listBankMSB));
		}

		for (int i = 0; i < DT.length(); i++) {
			if (DT.getJSONObject(i).getString("TYPE").equals("0")) {
				listAcc.add(new BankAccItem(DT.getJSONObject(i).getHashMap()));
			} else {
				if (DT.getJSONObject(i).getString("TYPE").equals("1")) {
					if (DT.getJSONObject(i).getString("BANKID")
							.startsWith(MSTradeAppConfig.MSBBankId)) {
						listBankMSB.add(new BankAccItem(DT.getJSONObject(i)
								.getHashMap()));
					} else {
						listBank.add(new BankAccItem(DT.getJSONObject(i)
								.getHashMap()));
					}
				}
			}
		}

		BankAccList bankAccList = new BankAccList(listBank, listAcc,
				listBankMSB);

		return new ResultObj(EC, EM, bankAccList);
	}
}
