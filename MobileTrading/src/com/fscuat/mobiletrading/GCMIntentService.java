package com.fscuat.mobiletrading;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.google.android.gcm.GCMBaseIntentService;

import java.util.ArrayList;

import static com.fss.mobiletrading.function.notify.CommonUtilities.SENDER_ID;

public class GCMIntentService extends GCMBaseIntentService {
	static int dem = 0;
	private static final String TAG = "GCMIntentService";
	static ArrayList<String> arrId = new ArrayList<String>();
	public static String GetUnReadIntentName = "GetUnRead";

	public GCMIntentService() {
		super(SENDER_ID);
	}

	/**
	 * Method called on device registered
	 **/
	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Thiết bị đã được đăng ký device token = " + registrationId);
		// register php
		// ServerUtilities.register(context, "Hiep " + Math.random() + " "
		// + Build.MODEL, "Test@gmail.com", registrationId);

		StaticObjectManager.deviceToken = registrationId;
		Log.e("GCMIntentService", registrationId);
		Intent intent = new Intent();
		intent.setAction("com.my.app.blinkled");
		intent.putExtra(MainActivity.INTENTTYPE,
				MainActivity.INTENT_REGISTEDTOKEN);
		sendBroadcast(intent);

	}

	/**
	 * Method called on device unregistered
	 * */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		// displayMessage(context, getString(R.string.gcm_unregistered));
		Toast.makeText(context, R.string.gcm_unregistered, Toast.LENGTH_SHORT)
				.show();
		// ServerUtilities.unregister(context, registrationId);
	}

	/**
	 * Method called on Receiving a new message
	 * */
	@Override
	protected void onMessage(Context context, Intent intent) {
		notifyOnReceiveNewNotify();
		String message = intent.getExtras().getString("alert");
		String id = intent.getExtras().getString("id");
		// String id = intent.getExtras().getString("alert");
		arrId.add(id);
		dem++;
		// notifies user
		generateNotification(context, message, dem);
	}

	/**
	 * Method called on receiving a deleted message
	 * */
	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		Toast.makeText(context, R.string.gcm_deleted, Toast.LENGTH_SHORT)
				.show();
		// displayMessage(context, message);
		// notifies user
		generateNotification(context, message, dem);
	}

	/**
	 * Method called on Error
	 * */
	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		// displayMessage(context, getString(R.string.gcm_error, errorId));
		Toast.makeText(context, R.string.gcm_error, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		// displayMessage(context,
		// getString(R.string.gcm_recoverable_error, errorId));
		Toast.makeText(context, R.string.gcm_recoverable_error,
				Toast.LENGTH_SHORT).show();
		return super.onRecoverableError(context, errorId);
	}

	private void generateNotification(Context context, String message, int dem) {
		String title = context.getString(R.string.app_name);
		Intent resultIntent = null;
		if (StaticObjectManager.isLogin == false) {
			StaticObjectManager.flagNotifyComeAtNotLogin = true;
			resultIntent = new Intent(context, Login.class);
		} else if (DeviceProperties.isTablet && StaticObjectManager.isLogin) {
			StaticObjectManager.flagNotifyComeAtNotLogin = false;
			resultIntent = new Intent(context, MainActivity_Tablet.class);
		} else if (!DeviceProperties.isTablet && StaticObjectManager.isLogin) {
			StaticObjectManager.flagNotifyComeAtNotLogin = false;
			resultIntent = new Intent(context, MainActivity_Mobile.class);
		}
		resultIntent.putExtra("NotifyFragment", arrId.get(dem - 1));
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
				dem, resultIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

		NotificationManager mNotifyMgr = (NotificationManager) context
				.getSystemService(NOTIFICATION_SERVICE);
		mNotifyMgr.notify(
				dem,
				genNotifyOnStatusBar(context, title, message,
						resultPendingIntent).build());
	}

	/**
	 * thiết kế 1 object notify
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param resultPendingIntent
	 */
	private NotificationCompat.Builder genNotifyOnStatusBar(Context context,
			String title, String message, PendingIntent resultPendingIntent) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_small_msbs)
				.setContentTitle(title).setContentText(message)
				.setAutoCancel(true).setTicker(message)
				.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
				.setVibrate(new long[] { 500, 500 })
				.setContentIntent(resultPendingIntent)
				.setWhen(System.currentTimeMillis());

		return mBuilder;
	}

	private void notifyOnReceiveNewNotify() {
		Intent intent = new Intent();
		intent.setAction("com.my.app.blinkled");
		intent.putExtra(MainActivity.INTENTTYPE, MainActivity.INTENT_NEWNOTIFY);
		sendBroadcast(intent);
	}

}
