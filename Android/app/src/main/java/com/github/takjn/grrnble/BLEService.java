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

    public static BluetoothDevice mDevice;
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

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
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

        private void sendBLEIntent(String action, String uuid, String value) {
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

        mDevice = intent.getParcelableExtra("device");
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

        if (mDevice != null) {
            mDevice = null;
        }

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void sendToWatch(Context context, String message) {
        Intent intent = new Intent(context, BLEService.BLECommandIntentReceiver.class);
        intent.setAction("WRITE");
        intent.putExtra("service", BLEService.UUID_PRIVATE_SERVICE.toString());
        intent.putExtra("characteristic", BLEService.UUID_PRIVATE_CHARACTERISTIC.toString());
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }

    /**
     * BroadcastReceiver.
     * Receive a broadcast-intent and read characteristics.
     */
    public static class BLECommandIntentReceiver extends BroadcastReceiver {
        private static final String TAG = "BLECommandIntent";

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            UUID service = UUID.fromString(intent.getStringExtra("service"));
            UUID characteristic = UUID.fromString(intent.getStringExtra("characteristic"));
            String message = intent.getStringExtra("message");
            Boolean enable = intent.getBooleanExtra("enable", true);

            if (mBluetoothGatt == null || mBluetoothGatt.getService(service) == null) {
                Log.e(TAG, "mBluetoothGatt or mBluetoothGatt.getService is null");
                return;
            }

            BluetoothGattCharacteristic ble = mBluetoothGatt.getService(service).getCharacteristic(characteristic);

            switch (action) {
                case "READ":
                    Log.d(TAG, "READ");
                    mBluetoothGatt.readCharacteristic(ble);
                    break;
                case "SET_NOTIFY":
                    Log.d(TAG, "SET_NOTIFY");
                    mBluetoothGatt.setCharacteristicNotification(ble, enable);
                    BluetoothGattDescriptor descriptor = ble.getDescriptor(UUID_NOTIFY);
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    mBluetoothGatt.writeDescriptor(descriptor);
                    break;
                case "WRITE":
                    Log.d(TAG, "WRITE:" + message);
                    ble.setValue(message);
                    mBluetoothGatt.writeCharacteristic(ble);
                    break;
            }
        }
    }
}
