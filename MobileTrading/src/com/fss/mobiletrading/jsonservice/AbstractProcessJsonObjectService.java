package com.fss.mobiletrading.jsonservice;

import org.json.JSONException;

import android.util.Log;

import com.fss.mobiletrading.common.JsonProcesor;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;

public abstract class AbstractProcessJsonObjectService {

	public ResultObj getObjectResult(String json) {
		ResultObj rObj = JsonProcesor.hasErrorConnect(json);
		/*
		 * Check system error
		 */
		// check các lỗi liên quan đến đường truyền, dữ liệu
		if (rObj != null) {
			return rObj;
		}
		MyJsonObject obj = null;
		try {
			obj = new MyJsonObject(json);
		} catch (JSONException e1) {
		} catch (NullPointerException e) {
		}
		// check lỗi không tạo được JsonObject từ chuỗi trả về
		if (obj == null) {
			return JsonProcesor.getErrorProcessJson();
		}

		int EC = obj.getInt("EC");

		// check lỗi timeout
		if (EC == Error.ErrorCode_TimeOut) {
			return new ResultObj(Error.ERROR_TIMEOUT, StringConst.EMPTY, null);
		}
		int OEC = 0;
		// nếu server trả về mã lỗi khác 0 thì quy ước EC = Error.ERROR_OTHER
		if (EC != 0) {
			OEC = EC;
			EC = Error.ERROR_OTHER;
		}

		String EM = obj.getString("EM");
		// check lỗi thiếu key "EM"
		if (EM == null) {
			return JsonProcesor.getErrorProcessJson();
		}
		/*
		 * Check business error
		 */
		String strDT = obj.getString("DT");
		MyJsonObject DT = null;
		try {
			DT = new MyJsonObject(strDT);
		} catch (NullPointerException e1) {
		} catch (JSONException e1) {
		}
		// check lỗi k lấy đc JSONObject từ key "DT"
		if (DT == null) {
			return new ResultObj(EC, EM, null, OEC);
		}
		try {
			ResultObj r = processObject(DT, EM, EC);
			return r;
		} catch (Exception e) {
			// check trường hợp server trả về mã lỗi khác 0 và không trả về cấu
			// trúc DT như quy ước
			if (EC != 0) {
				return JsonProcesor.getErrorOther(EM);
			}
			return JsonProcesor.getErrorOther("Null exception");
		}

	}

	abstract protected ResultObj processObject(MyJsonObject DT, String EM,
			int EC);

}
