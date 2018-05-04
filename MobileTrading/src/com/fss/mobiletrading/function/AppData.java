package com.fss.mobiletrading.function;

/**
 * Created by Pham Khang on 8/18/2016.
 */
public class AppData {
    public final static String LOCALE_VI_VN = "vi-VN";
    public final static String LOCALE_VI = "vi";
    public final static String LOCALE_EN = "en";
    public final static String LOCALE_ZH="zh";
    public static String language = LOCALE_VI_VN;
    private static AppData instance = new AppData();

    public static AppData getInstance() {
        return instance;
    }
}
