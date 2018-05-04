package com.fss.mobiletrading.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Mr.Incredible on 6/23/2016.
 */
public class NotificationHandler {
    static String COUNT_NOTIFICATIONS = "COUNT_NOTIFICATIONS";
    static final String TITLE = "HFT KBSV";
    static final String NAMEOFIMAGE = "ic_msbs";
    static int resId;
    static NotificationCompat.Builder mBuilder;
    static NotificationHandler handler = new NotificationHandler();

    /*Thay ID của notifications bằng giá trị count tăng dần*/
    public int count = 0;
    static SharedPreferences pre;

    public synchronized static NotificationHandler newInstance(Context context) {
        if (pre == null) {
            pre = context.getSharedPreferences(COUNT_NOTIFICATIONS, Context.MODE_PRIVATE);
        }
        resId = context.getResources().getIdentifier(NAMEOFIMAGE, "drawable", context.getPackageName());
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(resId).setContentTitle(TITLE);
        return handler;
    }

    public synchronized static NotificationHandler newInstance(Context context, boolean isEffect) {
        if (isEffect) {
            resId = context.getResources().getIdentifier(NAMEOFIMAGE, "drawable", context.getPackageName());
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setSmallIcon(resId).setContentTitle(TITLE);

            Notification notification = new Notification();
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            mBuilder.setDefaults(notification.defaults);

            mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            mBuilder.setLights(Color.RED, 3000, 3000);
        }
        return handler;
    }

    /*Create notifications*/

    public void showNotification(Context context, String message) {
        countNotifications();
        if (mBuilder != null) {
            mBuilder.setContentText(message);
        }
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(count, mBuilder.build());
    }

    /*Thay ID của notifications bằng giá trị count tăng dần*/
    private void countNotifications() {
        count++;
        if (count > Integer.MAX_VALUE) {
            count = 0;
        }
    }

    /*Set action for notification*/
    public void showNotificationWithAction(Context context, Intent intent, String messages) {
        countNotifications();
        int requestID = (int) System.currentTimeMillis();
        int flags = PendingIntent.FLAG_CANCEL_CURRENT;
        PendingIntent pIntent = PendingIntent.getActivity(context, requestID, intent, flags);

        if (mBuilder != null) {
            mBuilder.setContentText(messages).setAutoCancel(true).setContentIntent(pIntent);
        }

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(count, mBuilder.build());
    }

    public void showNotificationWithManyAction(Context context, Intent intent, String messages, Intent deleteIntent) {
        countNotifications();
        int requestID = (int) System.currentTimeMillis();
        int flags = PendingIntent.FLAG_CANCEL_CURRENT;
        PendingIntent pIntent = PendingIntent.getActivity(context, requestID, intent, flags);

        PendingIntent sIntent = PendingIntent.getActivity(context, requestID, intent, flags);
        PendingIntent nIntent = PendingIntent.getBroadcast(context, requestID, deleteIntent, flags);
        if (mBuilder != null) {
            mBuilder.setContentText(messages).setContentIntent(pIntent)
                    .addAction(0, "Share", sIntent)
                    .addAction(0, "Search", nIntent);
        }
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(count, mBuilder.build());
    }

    public void showNotificationExpand(Context context, String message, String expandTitle) {
        countNotifications();
        if (mBuilder != null) {
            mBuilder.setContentText(message).setAutoCancel(true);
        }
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = {"Loc Nguyen 1", "Loc Nguyen 2", "Loc Nguyen 3", "Loc Nguyen 4",};
        inboxStyle.setBigContentTitle(expandTitle);
        //Moves events into the expanded layout
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        mBuilder.setStyle(inboxStyle);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(count, mBuilder.build());

    }

    public void cancelNotifications(Context context) {
        if (Context.NOTIFICATION_SERVICE != null) {
            NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.cancel(count);
            count--;
            if (count < Integer.MIN_VALUE) {
                count = 0;
            }
        }
    }

    public static void setResId(int resId) {
        NotificationHandler.resId = resId;
        if (mBuilder != null) {
            mBuilder.setSmallIcon(resId).setContentTitle(TITLE);
        }
    }

    private void savedSharePreferences(Context context, boolean remember) {
        if (pre == null) {
            pre = context.getSharedPreferences(COUNT_NOTIFICATIONS,
                    Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = pre.edit();
        editor.putInt(COUNT_NOTIFICATIONS, count);
        editor.commit();
    }

    private void readSharePreferences(Context context) {
        if (pre == null) {
            pre = context.getSharedPreferences(COUNT_NOTIFICATIONS,
                    Context.MODE_PRIVATE);
        }
        if (pre.contains(COUNT_NOTIFICATIONS)) {
            count = pre.getInt(COUNT_NOTIFICATIONS, 0);
        }
    }

    public void resetCount() {
        if (count > 0) {
            count = 0;
        }
    }

    public int getCount() {
        return count;
    }

}
