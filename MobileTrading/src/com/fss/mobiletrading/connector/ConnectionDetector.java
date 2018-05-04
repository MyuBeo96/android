package com.fss.mobiletrading.connector;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class ConnectionDetector {

	public static boolean isConnectingToInternet(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager != null) {
			NetworkInfo[] info = manager.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
		}
		return false;
	}
}
