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

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    public static final String UUID_PRIVATE_MESSAGE1_CHARACTERISTIC = "b2332443-1dd3-407b-b3e6-5d349caf5368";
    public static final String UUID_PRIVATE_MESSAGE2_CHARACTERISTIC = "e2b8d854-bfa6-4e79-8a53-26b07604597d";
    public static final String UUID_PRIVATE_NOTIFY_CHARACTERISTIC = "4344eee2-c467-4318-bfab-7564815a5db8";

    // for Notification
    public static final String UUID_NOTIFY = "00002902-0000-1000-8000-00805f9b34fb";

    public static BluetoothDevice mDevice;
    private static BluetoothGatt mBluetoothGatt = null;
    private static String message1 = ""; // 長い文章を送信するときに利用

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
                case UUID_PRIVATE_MESSAGE1_CHARACTERISTIC:
                    value = characteristic.getStringValue(0);
                    Log.d(TAG, "onCharacteristicRead:UUID_PRIVATE_MESSAGE1_CHARACTERISTIC:" + value);
                    break;
                default:
                    Log.d(TAG, "onCharacteristicRead:" + characteristic.getUuid().toString().toLowerCase());
                    break;
            }

            BLEResultReceiver.sendBroadcast(getApplicationContext(), BLEResultReceiver.ACTION_READ, characteristic.getUuid().toString(), value);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (BluetoothGatt.GATT_SUCCESS != status) {
                return;
            }

            String value = "";
            BluetoothGattCharacteristic ble;

            switch (characteristic.getUuid().toString().toLowerCase()) {
                case UUID_PRIVATE_MESSAGE2_CHARACTERISTIC:
                    value = characteristic.getStringValue(0);
                    Log.d(TAG, "onCharacteristicWrite:UUID_PRIVATE_MESSAGE2_CHARACTERISTIC:" + value);

                    ble = mBluetoothGatt.getService(UUID.fromString(UUID_PRIVATE_SERVICE)).getCharacteristic(UUID.fromString(UUID_PRIVATE_MESSAGE1_CHARACTERISTIC));
                    ble.setValue(message1);
                    mBluetoothGatt.writeCharacteristic(ble);

                    break;
                case UUID_PRIVATE_MESSAGE1_CHARACTERISTIC:
                    value = characteristic.getStringValue(0);
                    Log.d(TAG, "onCharacteristicWrite:UUID_PRIVATE_MESSAGE1_CHARACTERISTIC:" + value);

                    ble = mBluetoothGatt.getService(UUID.fromString(UUID_PRIVATE_SERVICE)).getCharacteristic(UUID.fromString(UUID_PRIVATE_NOTIFY_CHARACTERISTIC));
                    ble.setValue("1");
                    mBluetoothGatt.writeCharacteristic(ble);
                    break;
                default:
                    Log.d(TAG, "onCharacteristicWrite:" + characteristic.getUuid().toString().toLowerCase());
                    break;
            }
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

            BLEResultReceiver.sendBroadcast(getApplicationContext(), BLEResultReceiver.ACTION_CHANGED, characteristic.getUuid().toString(), value);
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                timeSync(getApplicationContext());
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
    public static void writeMessage(Context context, String message) {

        message1 = leftB(message, 20);
        String[] splitStrings = message.split(message1);

        String message2 = " ";
        if (splitStrings.length > 1) {
            message2 = leftB(splitStrings[1], 20);
        }
        Intent intent = new Intent(context, BLEService.BLECommandReceiver.class);
        intent.setAction(BLECommandReceiver.ACTION_WRITE);
        intent.putExtra(BLECommandReceiver.EXTRA_SERVICE, BLEService.UUID_PRIVATE_SERVICE);
        intent.putExtra(BLECommandReceiver.EXTRA_CHARACTERISTIC, BLEService.UUID_PRIVATE_MESSAGE2_CHARACTERISTIC);
        intent.putExtra(BLECommandReceiver.EXTRA_MESSAGE, message2);
        context.sendBroadcast(intent);
    }

    public static void timeSync(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy,MM,dd,F,HH,mm");
        Calendar cl = Calendar.getInstance();
        String datetime = "DT," + sdf.format(cl.getTime());

        Log.d(TAG, datetime);
        BLEService.writeMessage(context, datetime);
    }

    public static void setBatteryNotify(Context context, boolean enable) {
        Intent intent = new Intent(context, BLECommandReceiver.class);
        intent.setAction(BLECommandReceiver.ACTION_SET_NOTIFY);
        intent.putExtra(BLECommandReceiver.EXTRA_SERVICE, BLEService.UUID_BATTERY_SERVICE);
        intent.putExtra(BLECommandReceiver.EXTRA_CHARACTERISTIC, BLEService.UUID_BATTERY_LEVEL_CHARACTERISTIC);
        intent.putExtra(BLECommandReceiver.EXTRA_ENABLE, enable);
        context.sendBroadcast(intent);
    }

    public static void setTemperatureNotify(Context context, boolean enable) {
        Intent intent = new Intent(context, BLECommandReceiver.class);
        intent.setAction(BLECommandReceiver.ACTION_SET_NOTIFY);
        intent.putExtra(BLECommandReceiver.EXTRA_SERVICE, BLEService.UUID_PRIVATE_SERVICE);
        intent.putExtra(BLECommandReceiver.EXTRA_CHARACTERISTIC, BLEService.UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC);
        intent.putExtra(BLECommandReceiver.EXTRA_ENABLE, enable);
        context.sendBroadcast(intent);
    }

    public static void readBattery(Context context) {
        Intent intent = new Intent(context, BLECommandReceiver.class);
        intent.setAction(BLECommandReceiver.ACTION_READ);
        intent.putExtra(BLECommandReceiver.EXTRA_SERVICE, BLEService.UUID_BATTERY_SERVICE);
        intent.putExtra(BLECommandReceiver.EXTRA_CHARACTERISTIC, BLEService.UUID_BATTERY_LEVEL_CHARACTERISTIC);
        context.sendBroadcast(intent);
    }

    public static void readTemperature(Context context) {
        Intent intent = new Intent(context, BLECommandReceiver.class);
        intent.setAction(BLECommandReceiver.ACTION_READ);
        intent.putExtra(BLECommandReceiver.EXTRA_SERVICE, BLEService.UUID_PRIVATE_SERVICE);
        intent.putExtra(BLECommandReceiver.EXTRA_CHARACTERISTIC, BLEService.UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC);
        context.sendBroadcast(intent);
    }

    public static void readMessage(Context context) {
        Intent intent = new Intent(context, BLECommandReceiver.class);
        intent.setAction(BLECommandReceiver.ACTION_READ);
        intent.putExtra(BLECommandReceiver.EXTRA_SERVICE, BLEService.UUID_PRIVATE_SERVICE);
        intent.putExtra(BLECommandReceiver.EXTRA_CHARACTERISTIC, BLEService.UUID_PRIVATE_MESSAGE1_CHARACTERISTIC);
        context.sendBroadcast(intent);
    }

    private static String leftB(String str, Integer len) {
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

    /**
     * BroadcastReceiver.
     * Receive a broadcast-intent and read characteristics.
     */
    public static class BLECommandReceiver extends BroadcastReceiver {
        private static final String TAG = "BLECommandReceiver";

        public static final String ACTION_READ = "com.github.takjn.grrnble.ACTION_READ";
        public static final String ACTION_SET_NOTIFY = "com.github.takjn.grrnble.ACTION_SET_NOTIFY";
        public static final String ACTION_WRITE = "com.github.takjn.grrnble.WRITE";

        public static final String EXTRA_SERVICE = "service";
        public static final String EXTRA_CHARACTERISTIC = "characteristic";
        public static final String EXTRA_MESSAGE = "message";
        public static final String EXTRA_ENABLE = "enable";

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            UUID service = UUID.fromString(intent.getStringExtra(EXTRA_SERVICE));
            UUID characteristic = UUID.fromString(intent.getStringExtra(EXTRA_CHARACTERISTIC));
            String message = intent.getStringExtra(EXTRA_MESSAGE);
            Boolean enable = intent.getBooleanExtra(EXTRA_ENABLE, true);

            if (mBluetoothGatt == null || mBluetoothGatt.getService(service) == null) {
                Log.e(TAG, "mBluetoothGatt or mBluetoothGatt.getService is null");
                return;
            }

            BluetoothGattCharacteristic ble = mBluetoothGatt.getService(service).getCharacteristic(characteristic);

            switch (action) {
                case ACTION_READ:
                    Log.d(TAG, "READ");
                    mBluetoothGatt.readCharacteristic(ble);
                    break;
                case ACTION_SET_NOTIFY:
                    Log.d(TAG, "SET_NOTIFY");
                    mBluetoothGatt.setCharacteristicNotification(ble, enable);
                    BluetoothGattDescriptor descriptor = ble.getDescriptor(UUID.fromString(UUID_NOTIFY));
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    mBluetoothGatt.writeDescriptor(descriptor);
                    break;
                case ACTION_WRITE:
                    Log.d(TAG, "WRITE:" + message);
                    ble.setValue(message);
                    mBluetoothGatt.writeCharacteristic(ble);
                    break;
            }
        }
    }
}
