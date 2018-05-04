package com.msbuat.mobiletrading;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Admin on 18-07-2016.
 */
public class MyApplication extends Application {

    private static Context context;
    private static boolean activityVisible;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        MultiDex.install(this);

    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return context;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }


}
