package com.github.takjn.grrnble;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationService extends NotificationListenerService {

    private static final String TAG = "NotificationService";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d(TAG, "onNotificationPosted");
        Log.d(TAG, "Id:" + sbn.getId() + " PackageName:" + sbn.getPackageName() + " TickerText:" + sbn.getNotification().tickerText + " PostTime:" + sbn.getPostTime());

        super.onNotificationPosted(sbn);

        String packageName = sbn.getPackageName();
        String title = "Unknown";
        String body = "";

        if (packageName.equals("com.google.android.apps.inbox")) {
            title = "Email";
        }
        else if (packageName.startsWith("com.facebook")) {
            title = "Facebook";
        }
        else {
            body = packageName;
        }

        // send a explicit broadcast intent
        Intent intent = new Intent(getApplicationContext(), BLEService.WritePrivateCharacteristicIntentReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        sendBroadcast(intent);
    }

//    @Override
//    public void onNotificationRemoved(StatusBarNotification sbn) {
//        Log.d(TAG, "onNotificationRemoved");
//    }

}
