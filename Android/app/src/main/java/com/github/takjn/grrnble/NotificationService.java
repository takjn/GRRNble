package com.github.takjn.grrnble;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationService extends NotificationListenerService {

    private static final String TAG = "NotificationService";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d(TAG, "onNotificationPosted");
        Log.d(TAG, "Id:" + sbn.getId() + " PackageName:" + sbn.getPackageName() + " TickerText:" + sbn.getNotification().tickerText + " PostTime:" + sbn.getPostTime());

        super.onNotificationPosted(sbn);

        String pn = sbn.getPackageName();

        switch (pn) {
            case "android":
            case "com.android.incallui":
            case "com.google.android.googlequicksearchbox":
            case "com.kddi.android.cmail":
                return;
            default:
                break;
        }

        String message = "";
        if (sbn.getNotification().category != null) {
            message = sbn.getNotification().category;
            if (message.length() > 1) {
                message = message.substring(0, 1).toUpperCase() + message.substring(1) + ":";
            }
        }
        if (sbn.getNotification().tickerText != null) {
            message = message + sbn.getNotification().tickerText;
        }
        if (message.length() < 1) {
            message = sbn.getPackageName();
        }
        message = leftB(message, 20);

        // send a explicit broadcast intent
        BLEService.sendToWatch(getApplicationContext(), message);
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(new NotificationChannel("notification_lisner", "GRRNble", NotificationManager.IMPORTANCE_MIN));

            PendingIntent pendingIntent =
                    PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new Notification.Builder(this, "notification_lisner")
                    .setContentTitle(getString(R.string.notification_content_title))
                    .setSmallIcon(android.R.drawable.stat_sys_data_bluetooth)
                    .setContentText(getString(R.string.notification_content_text))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .build();

            startForeground(1, notification);
        }

        return super.onBind(intent);
    }

//    @Override
//    public void onNotificationRemoved(StatusBarNotification sbn) {
//        Log.d(TAG, "onNotificationRemoved");
//    }

    private String leftB(String str, Integer len) {
        StringBuffer sb = new StringBuffer();
        int cnt = 0;

        try {
            for (int i = 0; i < str.length(); i++) {
                String tmpStr = str.substring(i, i + 1);
                byte[] b = tmpStr.getBytes("UTF-8");
                if (cnt + b.length > len) {
                    return sb.toString();
                } else {
                    sb.append(tmpStr);
                    cnt += b.length;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
