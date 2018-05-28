package com.github.takjn.grrnble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.UUID;

public class NotificationService extends NotificationListenerService {

    private static final String TAG = "NotificationService";
    // TODO: きちんと実装する
    public static BluetoothGatt mBluetoothGatt = null;

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

        showLog(sbn);

        int notificationCode = matchNotificationCode(sbn);

        if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {
            Log.d(TAG, "InterceptedNotificationCode:" + notificationCode);

            // TODO: きちんと実装する
            if (null != mBluetoothGatt) {
                writeCharacteristic("N:" + notificationCode);
            }
//            if (bluetoothChatFragment != null) {
//                bluetoothChatFragment.sendMessage("N:" + notificationCode + ":" + sbn.getNotification().tickerText);
//            }
        }

        super.onNotificationPosted(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(TAG, "onNotificationRemoved");
        showLog(sbn);
    }

    private void showLog(StatusBarNotification sbn) {
        int id = sbn.getId();
        String name = sbn.getPackageName();
        long time = sbn.getPostTime();
        boolean clearable = sbn.isClearable();
        boolean playing = sbn.isOngoing();
        CharSequence text = sbn.getNotification().tickerText;

        Log.d(TAG, "id:" + id + " name:" + name + " time:" + time);
        Log.d(TAG, "isClearable:" + clearable + " isOngoing:" + playing + " tickerText:" + text);
    }

    private int matchNotificationCode(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();

        if (packageName.equals(ApplicationPackageNames.FACEBOOK_PACK_NAME)
                || packageName.equals(ApplicationPackageNames.FACEBOOK_MESSENGER_PACK_NAME)) {
            return (InterceptedNotificationCode.FACEBOOK_CODE);
        } else if (packageName.equals(ApplicationPackageNames.INBOX_PACK_NAME)) {
            return (InterceptedNotificationCode.INBOX_CODE);
        } else {
            return (InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE);
        }
    }

    // TODO:きちんと実装する
    private void writeCharacteristic(String string) {
        Log.d(TAG, "writeCharacteristic");
        final UUID UUID_SERVICE_PRIVATE = UUID.fromString("3B382559-223F-48CA-81B4-E151598F661B");
        final UUID UUID_CHARACTERISTIC_PRIVATE2 = UUID.fromString("B2332443-1DD3-407B-B3E6-5D349CAF5368");

        if (null == mBluetoothGatt) {
            return;
        }
        BluetoothGattCharacteristic blechar = mBluetoothGatt.getService(UUID_SERVICE_PRIVATE).getCharacteristic(UUID_CHARACTERISTIC_PRIVATE2);
        blechar.setValue(string);
        mBluetoothGatt.writeCharacteristic(blechar);
    }

    //    https://github.com/Chagall/notification-listener-service-example/blob/master/app/src/main/java/com/github/chagall/notificationlistenerexample/NotificationListenerExampleService.java
    /*
    These are the package names of the apps. for which we want to
    listen the notifications
     */
    private static final class ApplicationPackageNames {
        public static final String INBOX_PACK_NAME = "com.google.android.apps.inbox";
        public static final String FACEBOOK_PACK_NAME = "com.facebook.katana";
        public static final String FACEBOOK_MESSENGER_PACK_NAME = "com.facebook.orca";
    }

    /*
    These are the return codes we use in the method which intercepts
    the notifications, to decide whether we should do something or not
     */
    public static final class InterceptedNotificationCode {
        public static final int OTHER_NOTIFICATIONS_CODE = 0; // We ignore all notification with code == 0
        public static final int FACEBOOK_CODE = 1;
        public static final int INBOX_CODE = 2;
    }
}
