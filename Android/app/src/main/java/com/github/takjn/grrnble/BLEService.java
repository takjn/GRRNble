package com.github.takjn.grrnble;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
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
    private static final UUID UUID_BATTERY_SERVICE = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_BATTERY_LEVEL_CHARACTERISTIC = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");

    // Private Service
    private static final UUID UUID_PRIVATE_SERVICE = UUID.fromString("3B382559-223F-48CA-81B4-E151598F661B");
    private static final UUID UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC = UUID.fromString("DB5445C4-4A70-4422-87AF-81D35456BEB5");
    private static final UUID UUID_PRIVATE_CHARACTERISTIC = UUID.fromString("B2332443-1DD3-407B-B3E6-5D349CAF5368");

    // for Notification
    private static final UUID UUID_NOTIFY = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");


    private BluetoothDevice mDevice = null;
    private static BluetoothGatt mBluetoothGatt = null;

    // BluetoothGattコールバックオブジェクト
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (BluetoothGatt.GATT_SUCCESS != status) {
                return;
            }

            if (BluetoothProfile.STATE_CONNECTED == newState) {
                Log.d(TAG, "onConnectionStateChange:STATE_CONNECTED");

                mBluetoothGatt.discoverServices();    // サービス検索
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
            if (BluetoothGatt.GATT_SUCCESS != status) {
                return;
            }

            if (UUID_BATTERY_LEVEL_CHARACTERISTIC.equals(characteristic.getUuid())) {
                int battery_level = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                final String strChara = String.valueOf(battery_level) + "%";
                Log.d(TAG, "onCharacteristicRead:UUID_BATTERY_LEVEL_CHARACTERISTIC:"+ strChara);
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        mFragmentDebug.setChara1(strChara);
//                    }
//                });
                return;
            }

            if (UUID_PRIVATE_CHARACTERISTIC.equals(characteristic.getUuid())) {
                final String strChara = characteristic.getStringValue(0);
                Log.d(TAG, "onCharacteristicRead:UUID_PRIVATE_CHARACTERISTIC:"+ strChara);
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        mFragmentDebug.setChara2(strChara);
//                    }
//                });
                return;
            }
        }

        // キャラクタリスティック変更が通知されたときの処理
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (UUID_BATTERY_LEVEL_CHARACTERISTIC.equals(characteristic.getUuid())) {
                int battery_level = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                final String strChara = String.valueOf(battery_level) + "%";
                Log.d(TAG, "onCharacteristicChanged:UUID_BATTERY_LEVEL_CHARACTERISTIC:"+ strChara);

//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        ((TextView) findViewById(R.id.textview_battery_level)).setText(strChara);
//                    }
//                });
                return;
            }

            if (UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC.equals(characteristic.getUuid())) {
                int temperature = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT8, 0);
                final String strChara = String.valueOf(temperature) + "℃";
                Log.d(TAG, "onCharacteristicChanged:UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC:"+ strChara);

//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        ((TextView) findViewById(R.id.textview_temperature)).setText(strChara);
//                    }
//                });
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

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        if (mBluetoothGatt == null) {
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


//    // キャラクタリスティックの読み込み
//    private void readCharacteristic(UUID uuid_service, UUID uuid_characteristic) {
////        if (null == mBluetoothGatt) {
////            return;
////        }
////        BluetoothGattCharacteristic blechar = mBluetoothGatt.getService(uuid_service).getCharacteristic(uuid_characteristic);
////        mBluetoothGatt.readCharacteristic(blechar);
//    }
//
//    // キャラクタリスティック通知の設定
//    private void setCharacteristicNotification(UUID uuid_service, UUID uuid_characteristic, boolean enable) {
////        if (null == mBluetoothGatt) {
////            return;
////        }
////        BluetoothGattCharacteristic blechar = mBluetoothGatt.getService(uuid_service).getCharacteristic(uuid_characteristic);
////        mBluetoothGatt.setCharacteristicNotification(blechar, enable);
////        BluetoothGattDescriptor descriptor = blechar.getDescriptor(UUID_NOTIFY);
////        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
////        mBluetoothGatt.writeDescriptor(descriptor);
//    }

}
