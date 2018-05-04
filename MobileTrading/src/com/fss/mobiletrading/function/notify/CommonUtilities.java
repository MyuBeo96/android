package com.fss.mobiletrading.function.notify;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

	// give your server registration url here
	public static final String SERVER_URL = "http://192.168.30.49/gcm_server_php/register.php";

	// Google project id
	public static final String SENDER_ID = "1053841307978";

	/**
	 * Tag used on log messages.
	 */
	public static final String TAG = "AndroidHive GCM";

	public static final String DISPLAY_MESSAGE_ACTION = "com.fss.mobiletrading.DISPLAY_MESSAGE";

	public static final String EXTRA_MESSAGE = "message";
}
