package com.fss.mobiletrading.consts;

public class StringConst {
	public static final String NULL = "NullValue";
	public static final String SPACE = " ";
	public static final String EMPTY = "";
	public static final String TRUE = "true";
	public static final String SEQUENCE_FALSE = "false";
	public static final String SEQ_Login = "Login";
	public static final String SEMI = ",";
	public static final String AND = "&";
	public static final String separator_thang = "#";
	public static final String CHARACTER_PERCENT = "%";
	public static final String QUOTES = "\"";

	public static String validateNull(String str) {
		if (str == null) {
			return EMPTY;
		}
		return str;
	}
}
