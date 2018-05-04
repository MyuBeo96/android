package com.fss.mobiletrading.jsonservice;

import org.json.JSONException;

import com.fss.mobiletrading.object.ResultObj;

public class ATService {

	public ResultObj processObject(String DT, String EM) {
		return new ResultObj(0, EM, DT);

	}
}
