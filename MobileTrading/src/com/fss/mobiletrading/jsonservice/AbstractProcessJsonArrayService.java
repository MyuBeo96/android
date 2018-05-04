package com.fss.mobiletrading.jsonservice;

import org.json.JSONException;

import com.fss.mobiletrading.common.JsonProcesor;
import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;

public abstract class AbstractProcessJsonArrayService {

	public ResultObj getArrayResult(String json) {
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

		// nếu server trả về mã lỗi khác 0 thì quy ước EC = Error.ERROR_OTHER
		if (EC != 0) {
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
		MyJsonArray DT = null;
		try {
			DT = new MyJsonArray(strDT);
		} catch (NullPointerException e1) {
		} catch (JSONException e1) {
		}
		// check lỗi k lấy đc JSONObject từ key "DT"
		if (DT == null) {
			return new ResultObj(EC, EM, null);
		}
		try {
			return processArray(DT, EM, EC);
		} catch (Exception e) {
			if (EC != 0) {
				return JsonProcesor.getErrorOther(EM);
			}
			return JsonProcesor.getErrorOther("Null exception");
		}

	}

	abstract public ResultObj processArray(MyJsonArray DT, String EM, int EC);
}
