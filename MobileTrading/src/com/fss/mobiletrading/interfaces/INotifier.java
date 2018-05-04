package com.fss.mobiletrading.interfaces;

import com.fss.mobiletrading.object.ResultObj;

public interface INotifier {

	public void notifyChangeAcctNo();

	public void notifyResult(String key, ResultObj rObj);
}
