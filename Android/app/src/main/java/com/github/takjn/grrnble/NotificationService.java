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
            case "com.android.settings":
            case "com.android.providers.downloads":
            case "com.android.vending":
            case "com.google.android.apps.maps":
            case "com.spotify.music":
                return;
            default:
                break;
        }

        Log.d(TAG, sbn.toString());

        String message = "";
//        if (sbn.getNotification().category != null) {
//            message = sbn.getNotification().category;
//            if (message.length() > 1) {
//                message = message.substring(0, 1).toUpperCase() + message.substring(1) + ":";
//            }
//        }
        if (sbn.getNotification().tickerText != null) {
            message = message + sbn.getNotification().tickerText;
        }
        if (message.length() < 1) {
            message = sbn.getPackageName();
        }

        // send a explicit broadcast intent
        BLEService.writeMessage(getApplicationContext(), message);
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
}
