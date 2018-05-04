package com.fss.mobiletrading.common;

import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.jsonservice.Error;
import com.fss.mobiletrading.object.ResultObj;

public class JsonProcesor {

	public static ResultObj getErrorProcessJson() {
		ResultObj rObj = new ResultObj(Error.ERROR_PROCESSJSON,
				StringConst.EMPTY, null);
		return rObj;
	}

	public static ResultObj getErrorOther(String str) {
		ResultObj rObj = new ResultObj(Error.ERROR_OTHER, str, null);
		return rObj;
	}

	public static ResultObj hasErrorConnect(String paramString) {
		ResultObj rObj = new ResultObj();
		if (paramString.equals("error1") || paramString.equals("null")
				|| paramString.equals("no_data")) {
			rObj.EC = -1;
			rObj.EM = StringConst.EMPTY;
			rObj.obj = null;
			return new ResultObj(Error.ERROR_CONNECT_SERVER, StringConst.EMPTY,
					null);
		} else {
			return null;
		}
	}

	// public static JSONObject createJSONObject(String json) {
	// try {
	// JSONObject obj = new JSONObject(json);
	// return obj;
	// } catch (Exception localException) {
	// return null;
	// }
	// }
	//
	// public static JSONArray getJSONArray(JSONObject obj, String key) {
	// try {
	// return obj.getJSONArray(key);
	// } catch (JSONException e) {
	// // TODO: handle exception
	// return null;
	// }
	// }
	//
	// public static JSONObject getJSONObject(JSONObject obj, String key) {
	// try {
	// return obj.getJSONObject(key);
	// } catch (JSONException e) {
	// // TODO: handle exception
	// return null;
	// }
	// }
	//
	// public static JSONObject getJSONObject(JSONArray array, int position) {
	// try {
	// return array.getJSONObject(position);
	// } catch (JSONException e) {
	// // TODO: handle exception
	// return null;
	// }
	// }
	//
	// public static String getString(JSONObject obj, String key) {
	// try {
	// return validate(obj.getString(key));
	// } catch (JSONException e) {
	// // TODO: handle exception
	// return null;
	// }
	// }
	//
	// public static int getInt(JSONObject obj, String key) {
	// try {
	// return obj.getInt(key);
	// } catch (JSONException e) {
	// // TODO: handle exception
	// // trả về 1 nếu gặp lỗi
	// return 1;
	// }
	// }

	// public static String validate(String str) {
	// try {
	// if ("null".matches(str)) {
	// str = StringConst.CHARACTER_EMPTY;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// return str;
	//
	// }
	//
	// return str;
	// }
}
