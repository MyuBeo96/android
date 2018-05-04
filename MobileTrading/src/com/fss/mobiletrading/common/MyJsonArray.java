package com.fss.mobiletrading.common;

import java.util.regex.PatternSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;

import com.fss.mobiletrading.consts.StringConst;

public class MyJsonArray extends JSONArray {

	public MyJsonArray() {
	}

	public MyJsonArray(String json) throws JSONException, NullPointerException {
		super(json);
	}

	@Override
	public MyJsonArray getJSONArray(int index) {
		try {
			return new MyJsonArray(this.getString(index));
		} catch (JSONException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	@Override
	public MyJsonObject getJSONObject(int index) {
		try {
			return new MyJsonObject(this.getString(index));
		} catch (JSONException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		} catch (OutOfMemoryError e) {
			return null;
		}

	}

	@Override
	public String getString(int index) {
		String str;
		try {
			str = super.getString(index);
		} catch (JSONException e) {

			return null;
		}
		try {
			if ("null".matches(str)) {
				return StringConst.EMPTY;
			}
		} catch (PatternSyntaxException e) {

		}
		return str;
	}
}
