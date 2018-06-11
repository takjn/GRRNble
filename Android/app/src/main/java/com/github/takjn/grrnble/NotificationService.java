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

        String message = "";
        message = sbn.getNotification().category;
        message = message.substring(0, 1).toUpperCase() + message.substring(1) + ":";
        message = message + sbn.getNotification().tickerText;
        message = leftB(message, 20);

        // send a explicit broadcast intent
        Intent intent = new Intent(getApplicationContext(), BLEService.BLECommandIntentReceiver.class);
        intent.setAction("SEND_TO_WATCH");
        intent.putExtra("message", message);
        sendBroadcast(intent);
    }

//    @Override
//    public void onNotificationRemoved(StatusBarNotification sbn) {
//        Log.d(TAG, "onNotificationRemoved");
//    }

    private String leftB(String str, Integer len){
        StringBuffer sb = new StringBuffer();
        int cnt = 0;

        try{
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
        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

}
