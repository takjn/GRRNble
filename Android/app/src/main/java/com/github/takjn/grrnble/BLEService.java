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
    public static final String UUID_BATTERY_SERVICE = "0000180f-0000-1000-8000-00805f9b34fb";
    public static final String UUID_BATTERY_LEVEL_CHARACTERISTIC = "00002a19-0000-1000-8000-00805f9b34fb";

    // Private Service
    public static final String UUID_PRIVATE_SERVICE = "3b382559-223f-48ca-81b4-e151598f661b";
    public static final String UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC = "db5445c4-4a70-4422-87af-81d35456beb5";
    public static final String UUID_PRIVATE_CHARACTERISTIC = "b2332443-1dd3-407b-b3e6-5d349caf5368";

    // for Notification
    public static final String UUID_NOTIFY = "00002902-0000-1000-8000-00805f9b34fb";

    public static BluetoothDevice mDevice;
    private static BluetoothGatt mBluetoothGatt = null;

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (BluetoothGatt.GATT_SUCCESS != status) {
                Log.e(TAG, "GATT status is not success.");
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

            String value = "";

            switch (characteristic.getUuid().toString().toLowerCase()) {
                case UUID_BATTERY_LEVEL_CHARACTERISTIC:
                    int battery_level = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                    value = String.valueOf(battery_level) + "%";
                    Log.d(TAG, "onCharacteristicRead:UUID_BATTERY_LEVEL_CHARACTERISTIC:" + value);
                    break;
                case UUID_PRIVATE_CHARACTERISTIC:
                    value = characteristic.getStringValue(0);
                    Log.d(TAG, "onCharacteristicRead:UUID_PRIVATE_CHARACTERISTIC:" + value);
                    break;
                default:
                    Log.d(TAG, "onCharacteristicRead:" + characteristic.getUuid().toString().toLowerCase());
                    break;
            }

            sendBLEIntent("READ", characteristic.getUuid().toString(), value);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

            String value = "";

            switch (characteristic.getUuid().toString().toLowerCase()) {
                case UUID_BATTERY_LEVEL_CHARACTERISTIC:
                    int battery_level = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                    value = String.valueOf(battery_level) + "%";
                    Log.d(TAG, "onCharacteristicChanged:UUID_BATTERY_LEVEL_CHARACTERISTIC:" + value);
                    break;
                case UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC:
                    int temperature = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT8, 0);
                    value = String.valueOf(temperature) + "℃";
                    Log.d(TAG, "onCharacteristicChanged:UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC:" + value);
                    break;
                default:
                    Log.d(TAG, "onCharacteristicChanged:" + characteristic.getUuid().toString().toLowerCase());
                    break;
            }

            sendBLEIntent("CHANGED", characteristic.getUuid().toString(), value);
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

    /**
     * Helper method
     * Send a explicit broadcast intent to write private characteristic
     * @param context Application context
     * @param message Message to send
     */
    public static void sendToWatch(Context context, String message) {
        Intent intent = new Intent(context, BLEService.BLECommandIntentReceiver.class);
        intent.setAction("WRITE");
        intent.putExtra("service", BLEService.UUID_PRIVATE_SERVICE);
        intent.putExtra("characteristic", BLEService.UUID_PRIVATE_CHARACTERISTIC);
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
                    BluetoothGattDescriptor descriptor = ble.getDescriptor(UUID.fromString(UUID_NOTIFY));
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
