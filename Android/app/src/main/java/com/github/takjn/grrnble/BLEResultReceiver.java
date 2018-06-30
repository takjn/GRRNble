package com.github.takjn.grrnble;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * BroadcastReceiver.
 * Receive a broadcast-intent and read characteristics.
 * https://qiita.com/kazhida/items/91a15a1cf8ec0c443dbb
 */
public class BLEResultReceiver extends BroadcastReceiver {
    private static final String TAG = "BluetoothResultReceiver";

    public static final String ACTION_READ = "com.github.takjn.grrnble.ACTION_READ";
    public static final String ACTION_CHANGED = "com.github.takjn.grrnble.ACTION_CHANGED";

    private static final String EXTRA_UUID = "uuid";
    private static final String EXTRA_VALUE = "value";

    public interface Callback {
        void onEventInvoked(String action, String uuid, String value);
    }

    private Callback callback;
    private LocalBroadcastManager manager;


    private BLEResultReceiver(Context context, Callback callback) {
        super();
        this.callback = callback;
        manager = LocalBroadcastManager.getInstance(context.getApplicationContext());

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_READ);
        filter.addAction(ACTION_CHANGED);

        manager.registerReceiver(this, filter);
    }

    public static BLEResultReceiver register(Context context, Callback callback) {
        return new BLEResultReceiver(context, callback);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");

        String uuid = intent.getStringExtra(EXTRA_UUID);
        String value = intent.getStringExtra(EXTRA_VALUE);
        String action = intent.getAction();

        callback.onEventInvoked(action, uuid, value);
    }

    public static void sendBroadcast(Context context, String action, String uuid, String value) {
        Log.d(TAG, "sendBroadcast");

        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_UUID, uuid);
        intent.putExtra(EXTRA_VALUE, value);

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context.getApplicationContext());
        manager.sendBroadcast(intent);
    }

    public void unregister() {
        manager.unregisterReceiver(this);
    }

}
