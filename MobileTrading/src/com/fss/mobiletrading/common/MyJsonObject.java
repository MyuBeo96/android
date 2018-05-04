package com.fss.mobiletrading.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.PatternSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import com.fss.mobiletrading.consts.StringConst;

public class MyJsonObject extends JSONObject {

	public MyJsonObject() {

	}

	public MyJsonObject(String json) throws JSONException, NullPointerException {

		super(json);
	}

	@Override
	public MyJsonArray getJSONArray(String name) {

		try {
			return new MyJsonArray(this.getString(name));
		} catch (JSONException e) {

			return null;
		} catch (NullPointerException e) {
			return null;
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	@Override
	public MyJsonObject getJSONObject(String name) {

		String json = this.getString(name);
		try {
			return new MyJsonObject(json);
		} catch (JSONException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	/**
	 * Trả về giá trị được map bởi name. Nếu không tìm được key tương ứng
	 * thì trả về Common.NULL
	 */
	@Override
	public String getString(String name) {

		String str;
		try {
			str = super.getString(name);
		} catch (JSONException e) {
			return StringConst.NULL;
		}
		try {
			// check server trả về chữ null
			if ("null".matches(str)) {
				return StringConst.EMPTY;
			}
		} catch (PatternSyntaxException e) {

		}
		return str;
	}

	/**
	 * @return trả về Integer.MIN_VALUE khi không lấy được giá trị
	 */
	@Override
	public int getInt(String name) {

		try {
			return super.getInt(name);
		} catch (JSONException e) {

			// trả về -10 khi không lấy đc giá trị
			return Integer.MIN_VALUE;
		}
	}

	public HashMap<String, String> getHashMap() {
		HashMap<String, String> hm = new HashMap<String, String>();
		Iterator<String> keys = this.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			hm.put(key, this.getString(key));
		}
		return hm;
	}
}
