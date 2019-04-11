package com.tcscuat.mobiletrading;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class CallAnotherAppManager {

	Context context;
	PackageManager packageManager;

	public CallAnotherAppManager(Context context) {
		super();
		this.context = context;
		packageManager = this.context.getPackageManager();
	}

	/**
	 * go to link in web
	 * 
	 * @param url
	 */
	public boolean goToUrl(String url) {
		if (url != null) {
			Uri uriUrl = Uri.parse(url);
			Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
			try {
				context.startActivity(launchBrowser);
			} catch (ActivityNotFoundException e) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * go to the link in youtube app
	 * 
	 * @param urlYoutube
	 */
	public void gotoYoutube(String id) {
		Uri uri = Uri.parse("vnd.youtube:" + id);
		Intent videoIntent = new Intent(Intent.ACTION_VIEW);
		videoIntent.setData(uri);
		if (checkAppInstalled("com.google.android.youtube")) {
			context.startActivity(videoIntent);
		} else {
			goToUrl("https://www.youtube.com/watch?v=" + id);
		}
	}

	private boolean checkAppInstalled(String packageName) {
		try {
			packageManager.getPackageInfo(packageName,
					PackageManager.GET_ACTIVITIES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}

	}
}
