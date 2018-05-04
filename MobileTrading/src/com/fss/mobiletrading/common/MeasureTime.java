package com.fss.mobiletrading.common;

import com.fss.mobiletrading.consts.StringConst;

import android.util.Log;

public class MeasureTime {
	static long mark = 0;
	static final String TAG = "watchtime";
	static final String TAG_START = "start";

	public static void measure(String detail) {
		long end = System.currentTimeMillis();
		Log.e(TAG, detail + StringConst.SPACE + (end - mark));
		mark = end;
	}

	public static void mark() {
		mark = System.currentTimeMillis();
		Log.e(TAG, TAG_START);
	}

	public static void measureNotMark(String detail) {
		long end = System.currentTimeMillis();
		Log.e(TAG, detail + StringConst.SPACE + (end - mark));
	}
}
