package com.fss.mobiletrading.common;

public class Validation {
	public static boolean isVietnamese(String str) {
		for (int i = 0; i < str.length(); i++) {
			int charCode = str.codePointAt(i);
			if (charCode > 127) {
				return false;
			}
		}
		return true;
	}
}
