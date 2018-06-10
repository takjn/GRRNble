package com.github.takjn.grrnble;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.UUID;

public class BLEService extends Service {
    private static final String TAG = "BLEService";

    // Bluetooth LE Gatt UUID
    // Battery Service
    public static final UUID UUID_BATTERY_SERVICE = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_BATTERY_LEVEL_CHARACTERISTIC = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");

    // Private Service
    public static final UUID UUID_PRIVATE_SERVICE = UUID.fromString("3B382559-223F-48CA-81B4-E151598F661B");
    public static final UUID UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC = UUID.fromString("DB5445C4-4A70-4422-87AF-81D35456BEB5");
    public static final UUID UUID_PRIVATE_CHARACTERISTIC = UUID.fromString("B2332443-1DD3-407B-B3E6-5D349CAF5368");

    // for Notification
    public static final UUID UUID_NOTIFY = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private static BluetoothGatt mBluetoothGatt = null;

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (BluetoothGatt.GATT_SUCCESS != status) {
                return;
            }

            if (BluetoothProfile.STATE_CONNECTED == newState) {
                Log.d(TAG, "onConnectionStateChange:STATE_CONNECTED");
                mBluetoothGatt.discoverServices();
                return;
            }
            if (BluetoothProfile.STATE_DISCONNECTED == newState) {
                Log.d(TAG, "onConnectionStateChange:STATE_DISCONNECTED");
                mBluetoothGatt.connect();
                return;
            }
        }

        // キャラクタリスティックが読み込まれたときの処理
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.d("TAG", "onCharacteristicRead");
//            Log.d(TAG, "service:" + service + ", characteristic:" + characteristic + ", action:" + action);

            if (BluetoothGatt.GATT_SUCCESS != status) {
                return;
            }

            if (UUID_BATTERY_LEVEL_CHARACTERISTIC.equals(characteristic.getUuid())) {
                int battery_level = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                final String value = String.valueOf(battery_level) + "%";
                Log.d(TAG, "onCharacteristicRead:UUID_BATTERY_LEVEL_CHARACTERISTIC:" + value);

                sendBLEIntent("READ", characteristic.getUuid().toString(), value);
                return;
            }

            if (UUID_PRIVATE_CHARACTERISTIC.equals(characteristic.getUuid())) {
                final String value = characteristic.getStringValue(0);
                Log.d(TAG, "onCharacteristicRead:UUID_PRIVATE_CHARACTERISTIC:" + value);

                sendBLEIntent("READ", characteristic.getUuid().toString(), value);
                return;
            }
        }

        // キャラクタリスティック変更が通知されたときの処理
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (UUID_BATTERY_LEVEL_CHARACTERISTIC.equals(characteristic.getUuid())) {
                int battery_level = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                final String value = String.valueOf(battery_level) + "%";
                Log.d(TAG, "onCharacteristicChanged:UUID_BATTERY_LEVEL_CHARACTERISTIC:" + value);

                sendBLEIntent("CHANGED", characteristic.getUuid().toString(), value);
                return;
            }

            if (UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC.equals(characteristic.getUuid())) {
                int temperature = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT8, 0);
                final String value = String.valueOf(temperature) + "℃";
                Log.d(TAG, "onCharacteristicChanged:UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC:" + value);

                sendBLEIntent("CHANGED", characteristic.getUuid().toString(), value);
                return;
            }
        }

        // キャラクタリスティックが書き込まれたときの処理
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (BluetoothGatt.GATT_SUCCESS != status) {
                return;
            }

            if (UUID_PRIVATE_CHARACTERISTIC.equals(characteristic.getUuid())) {
                Log.d(TAG, "onCharacteristicWrite:UUID_PRIVATE_CHARACTERISTIC");

//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        mFragmentDebug.enabled(true);
//                    }
//                });
                return;
            }
        }

        private void sendBLEIntent(String action, String uuid, String value) {
            Log.d(TAG, "sendBLEIntent");
            Intent intent = new Intent(getApplicationContext(), MainActivity.BLEIntentReceiver.class);
            intent.setAction(action);
            intent.putExtra("uuid", uuid);
            intent.putExtra("value", value);
            sendBroadcast(intent);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        BluetoothDevice mDevice = intent.getParcelableExtra("device");
        Log.d(TAG, mDevice.getName());
        Log.d(TAG, mDevice.getAddress());

        mBluetoothGatt = mDevice.connectGatt(this, false, mGattCallback);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * BroadcastReceiver.
     * Receive a broadcast-intent and write characteristics.
     */
    public static class WritePrivateCharacteristicIntentReceiver extends BroadcastReceiver {
        private static final String TAG = "WPCIntentReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");

            String title = intent.getStringExtra("title");
            String body = intent.getStringExtra("body");
            String message = title + "," + body;

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

            if (mBluetoothGatt != null) {
                Log.d(TAG, "send message via BLE: " + message);

                BluetoothGattCharacteristic blechar = mBluetoothGatt.getService(UUID_PRIVATE_SERVICE).getCharacteristic(UUID_PRIVATE_CHARACTERISTIC);
                blechar.setValue(message);
                mBluetoothGatt.writeCharacteristic(blechar);
            }
        }
    }

    /**
     * BroadcastReceiver.
     * Receive a broadcast-intent and read characteristics.
     */
    public static class BLECommandIntentReceiver extends BroadcastReceiver {
        private static final String TAG = "BLECommandIntent";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");

            String action = intent.getStringExtra("action");
            String service = intent.getStringExtra("service");
            String characteristic = intent.getStringExtra("characteristic");
            Boolean enable = intent.getBooleanExtra("enable", true);


            if (mBluetoothGatt != null && service != null && characteristic != null) {
                if (action.equals("READ")) {
                    Log.d(TAG, "READ");
                    BluetoothGattCharacteristic ble = mBluetoothGatt.getService(UUID.fromString(service)).getCharacteristic(UUID.fromString(characteristic));
                    mBluetoothGatt.readCharacteristic(ble);
                } else if (action.equals("SET_NOTIFY")) {
                    Log.d(TAG, "SET_NOTIFY");
                    BluetoothGattCharacteristic ble = mBluetoothGatt.getService(UUID.fromString(service)).getCharacteristic(UUID.fromString(characteristic));
                    mBluetoothGatt.setCharacteristicNotification(ble, enable);
                    BluetoothGattDescriptor descriptor = ble.getDescriptor(UUID_NOTIFY);
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    mBluetoothGatt.writeDescriptor(descriptor);
                }

            }
        }
    }

}
