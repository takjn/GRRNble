package com.github.takjn.grrnble;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DebugFragment.DebugListener {

    private static final String TAG = "MainActivity";

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

    private static final int REQUEST_ENABLEBLUETOOTH = 1;
    private static final long SCAN_PERIOD = 10000;  // スキャン時間。単位はミリ秒。
    private static BluetoothGatt mBluetoothGatt = null;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice = null;
    private Handler mHandler;                       // UIスレッド操作ハンドラ : 「一定時間後にスキャンをやめる処理」で必要
    private boolean mScanning = false;              // スキャン中かどうかのフラグ

    private Button mButtonScan;
    private Button mButtonConnect;
    private Button mButtonDisconnect;
    private CheckBox mCheckBoxBatteryLevel;
    private CheckBox mCheckBoxTemperature;
    private ProgressBar mProgressBar;
    private DebugFragment mFragmentDebug;

    // BluetoothGattコールバックオブジェクト
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        // 接続状態変更（connectGatt()の結果として呼ばれる。）
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (BluetoothGatt.GATT_SUCCESS != status) {
                return;
            }

            if (BluetoothProfile.STATE_CONNECTED == newState) {    // 接続完了
                mBluetoothGatt.discoverServices();    // サービス検索
                runOnUiThread(new Runnable() {
                    public void run() {
                        mButtonDisconnect.setEnabled(true);
                        mCheckBoxBatteryLevel.setEnabled(true);
                        mCheckBoxTemperature.setEnabled(true);
                    }
                });
                return;
            }
            if (BluetoothProfile.STATE_DISCONNECTED == newState) {    // 切断完了（接続可能範囲から外れて切断された）
                // 接続可能範囲に入ったら自動接続するために、mBluetoothGatt.connect()を呼び出す。
                mBluetoothGatt.connect();
                runOnUiThread(new Runnable() {
                    public void run() {
                        hideDebugFragment();
                    }
                });
                return;
            }
        }

//        // サービス検索が完了したときの処理（mBluetoothGatt.discoverServices()の結果として呼ばれる。）
//        @Override
//        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
//            if (BluetoothGatt.GATT_SUCCESS != status) {
//                return;
//            }
//
//            // 発見されたサービスのループ
//            for (BluetoothGattService service : gatt.getServices()) {
//                // サービスごとに個別の処理
//                if ((null == service) || (null == service.getUuid())) {
//                    continue;
//                }
//                if (UUID_PRIVATE_SERVICE.equals(service.getUuid())) {    // プライベートサービス
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            showDebugFragment();
//                        }
//                    });
//                    continue;
//                }
//            }
//        }

        // キャラクタリスティックが読み込まれたときの処理
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (BluetoothGatt.GATT_SUCCESS != status) {
                return;
            }

            if (UUID_BATTERY_LEVEL_CHARACTERISTIC.equals(characteristic.getUuid())) {
                int battery_level = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                final String strChara = String.valueOf(battery_level) + "%";
                runOnUiThread(new Runnable() {
                    public void run() {
                        mFragmentDebug.setChara1(strChara);
                    }
                });
                return;
            }

            if (UUID_PRIVATE_CHARACTERISTIC.equals(characteristic.getUuid())) {
                final String strChara = characteristic.getStringValue(0);
                runOnUiThread(new Runnable() {
                    public void run() {
                        mFragmentDebug.setChara2(strChara);
                    }
                });
                return;
            }
        }

        // キャラクタリスティック変更が通知されたときの処理
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (UUID_BATTERY_LEVEL_CHARACTERISTIC.equals(characteristic.getUuid())) {
                int battery_level = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                final String strChara = String.valueOf(battery_level) + "%";
                runOnUiThread(new Runnable() {
                    public void run() {
                        ((TextView) findViewById(R.id.textview_battery_level)).setText(strChara);
                    }
                });
                return;
            }

            if (UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC.equals(characteristic.getUuid())) {
                int temperature = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT8, 0);
                final String strChara = String.valueOf(temperature) + "℃";
                runOnUiThread(new Runnable() {
                    public void run() {
                        ((TextView) findViewById(R.id.textview_temperature)).setText(strChara);
                    }
                });
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
                runOnUiThread(new Runnable() {
                    public void run() {
                        mFragmentDebug.enabled(true);
                    }
                });
                return;
            }
        }
    };
    /**
     * Device scan callback
     */
    private ScanCallback mLeScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, final ScanResult result) {
            super.onScanResult(callbackType, result);
            mDevice = result.getDevice();

            Log.d(TAG, mDevice.getName());
            Log.d(TAG, mDevice.getAddress());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    stopScan();
                }
            });
        }
    };

    // スキャンの開始
    private void startScan() {
        if (mScanning) return;

        final android.bluetooth.le.BluetoothLeScanner scanner = mBluetoothAdapter.getBluetoothLeScanner();

        if (scanner == null) {
            return;
        }

        // スキャン開始（一定時間後にスキャン停止する）
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopScan();
            }
        }, SCAN_PERIOD);

        ScanFilter scanFilter = new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString("3B382559-223F-48CA-81B4-E151598F661B")).build();
        List<ScanFilter> scanFilterList = new ArrayList();
        scanFilterList.add(scanFilter);

        ScanSettings scanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_BALANCED).build();
        mScanning = true;
        scanner.startScan(scanFilterList, scanSettings, mLeScanCallback);

        mProgressBar.setVisibility(ProgressBar.VISIBLE);
    }

    // スキャンの停止
    private void stopScan() {
        mScanning = false;

        mHandler.removeCallbacksAndMessages(null);
        android.bluetooth.le.BluetoothLeScanner scanner = mBluetoothAdapter.getBluetoothLeScanner();
        if (scanner == null) {
            return;
        }
        scanner.stopScan(mLeScanCallback);

        if (mDevice != null) {
            ((TextView) findViewById(R.id.textview_devicename)).setText(mDevice.getName());
            ((TextView) findViewById(R.id.textview_deviceaddress)).setText(mDevice.getAddress());

            mButtonScan.setEnabled(false);
            mButtonConnect.setEnabled(true);
            mButtonConnect.callOnClick();
        } else {
            mButtonScan.setEnabled(true);
            Toast.makeText(this, R.string.device_is_not_found, Toast.LENGTH_SHORT).show();
        }

        mProgressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonScan = (Button) findViewById(R.id.button_scan);
        mButtonScan.setOnClickListener(this);

        mButtonConnect = (Button) findViewById(R.id.button_connect);
        mButtonConnect.setOnClickListener(this);
        mButtonDisconnect = (Button) findViewById(R.id.button_disconnect);
        mButtonDisconnect.setOnClickListener(this);
        mCheckBoxBatteryLevel = (CheckBox) findViewById(R.id.checkbox_battery_level);
        mCheckBoxBatteryLevel.setOnClickListener(this);
        mCheckBoxTemperature = (CheckBox) findViewById(R.id.checkbox_temperature);
        mCheckBoxTemperature.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        FragmentManager fragmentManager = getFragmentManager();
        mFragmentDebug = (DebugFragment) fragmentManager.findFragmentById(R.id.fragment_debug);

        mHandler = new Handler();

        // If the user did not turn the notification listener service on we prompt him to do so
        // Got it from: https://github.com/Chagall/notification-listener-service-example/blob/master/app/src/main/java/com/github/chagall/notificationlistenerexample/MainActivity.java
        if (!isNotificationServiceEnabled()) {
            buildNotificationServiceAlertDialog().show();
        }

        // Check if system has BLE feature
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_is_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Get BluetoothAdapter
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            // System does not have Bluetooth service
            Toast.makeText(this, R.string.bluetooth_is_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // GUIアイテムの有効無効の設定
        mButtonScan.setEnabled(true);
        mButtonConnect.setEnabled(false);
        mButtonDisconnect.setEnabled(false);
        mCheckBoxBatteryLevel.setEnabled(false);
        mCheckBoxTemperature.setEnabled(false);
        mProgressBar.setVisibility(ProgressBar.GONE);
        hideDebugFragment();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");

        super.onResume();
        requestBluetoothFeature();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
    }

    private void requestBluetoothFeature() {
        if (mBluetoothAdapter.isEnabled()) {
            return;
        }

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLEBLUETOOTH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLEBLUETOOTH:
                if (Activity.RESULT_CANCELED == resultCode) {
                    Toast.makeText(this, R.string.bluetooth_is_not_working, Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_checkbox) {
            // チェックボックスの状態変更を行う
            item.setChecked(!item.isChecked());

            // 反映後の状態を取得する
            boolean checked = item.isChecked();
            if (checked) {
                showDebugFragment();
            } else {
                hideDebugFragment();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mButtonScan.getId()) {
            mButtonScan.setEnabled(false);
            startScan();
            return;
        }
        if (mButtonConnect.getId() == v.getId()) {
            mButtonConnect.setEnabled(false);
            connect();
            return;
        }
        if (mButtonDisconnect.getId() == v.getId()) {
            mButtonDisconnect.setEnabled(false);
            disconnect();
            return;
        }
        if (mCheckBoxBatteryLevel.getId() == v.getId()) {
            setCharacteristicNotification(UUID_BATTERY_SERVICE, UUID_BATTERY_LEVEL_CHARACTERISTIC, mCheckBoxBatteryLevel.isChecked());
            return;
        }
        if (mCheckBoxTemperature.getId() == v.getId()) {
            setCharacteristicNotification(UUID_PRIVATE_SERVICE, UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC, mCheckBoxTemperature.isChecked());
            return;
        }
    }

    /**
     * Connect to the device
     */
    private void connect() {
        if (mDevice == null) {
            // Device is not found
            return;
        }

        if (mBluetoothGatt != null) {
            // Device is connected
            return;
        }

        mBluetoothGatt = mDevice.connectGatt(this, false, mGattCallback);
    }

    /**
     * Disconnect from the device
     */
    private void disconnect() {
        if (mBluetoothGatt == null) {
            return;
        }

        mBluetoothGatt.close();
        mBluetoothGatt = null;

        mButtonConnect.setEnabled(true);
        mButtonDisconnect.setEnabled(false);
        mCheckBoxBatteryLevel.setEnabled(false);
        mCheckBoxBatteryLevel.setChecked(false);
        mCheckBoxTemperature.setEnabled(false);
        mCheckBoxTemperature.setChecked(false);
        hideDebugFragment();
    }

    // キャラクタリスティックの読み込み
    private void readCharacteristic(UUID uuid_service, UUID uuid_characteristic) {
        if (null == mBluetoothGatt) {
            return;
        }
        BluetoothGattCharacteristic blechar = mBluetoothGatt.getService(uuid_service).getCharacteristic(uuid_characteristic);
        mBluetoothGatt.readCharacteristic(blechar);
    }

    // キャラクタリスティック通知の設定
    private void setCharacteristicNotification(UUID uuid_service, UUID uuid_characteristic, boolean enable) {
        if (null == mBluetoothGatt) {
            return;
        }
        BluetoothGattCharacteristic blechar = mBluetoothGatt.getService(uuid_service).getCharacteristic(uuid_characteristic);
        mBluetoothGatt.setCharacteristicNotification(blechar, enable);
        BluetoothGattDescriptor descriptor = blechar.getDescriptor(UUID_NOTIFY);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        mBluetoothGatt.writeDescriptor(descriptor);
    }

    // キャラクタリスティックの書き込み
    private void writeCharacteristic(UUID uuid_service, UUID uuid_characteristic, String string) {
        if (null == mBluetoothGatt) {
            return;
        }
        BluetoothGattCharacteristic blechar = mBluetoothGatt.getService(uuid_service).getCharacteristic(uuid_characteristic);
        blechar.setValue(string);
        mBluetoothGatt.writeCharacteristic(blechar);
    }

    private void showDebugFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(mFragmentDebug);
        fragmentTransaction.commit();
    }

    private void hideDebugFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(mFragmentDebug);
        fragmentTransaction.commit();
    }

    /**
     * Is Notification Service Enabled.
     * Verifies if the notification listener service is enabled.
     * Got it from: https://github.com/kpbird/NotificationListenerService-Example/blob/master/NLSExample/src/main/java/com/kpbird/nlsexample/NLService.java
     *
     * @return True if eanbled, false otherwise.
     */
    private boolean isNotificationServiceEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Build Notification Listener Alert Dialog.
     * Builds the alert dialog that pops up if the user has not turned
     * the Notification Listener Service on yet.
     *
     * @return An alert dialog which leads to the notification enabling screen
     */
    private AlertDialog buildNotificationServiceAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.notification_listener_service);
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation);
        alertDialogBuilder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                    }
                });
        alertDialogBuilder.setNegativeButton(R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If you choose to not enable the notification listener
                        // the app. will not work as expected
                        finish();
                        return;
                    }
                });
        return (alertDialogBuilder.create());
    }

    /**
     * DebugListener.
     */
    @Override
    public void onWritePrivateCharacteristic(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        writeCharacteristic(UUID_PRIVATE_SERVICE, UUID_PRIVATE_CHARACTERISTIC, message);
    }

    @Override
    public void onReadBatteryLevelCharacteristic() {
        readCharacteristic(UUID_BATTERY_SERVICE, UUID_BATTERY_LEVEL_CHARACTERISTIC);
    }

    @Override
    public void onReadPrivateCharacteristic() {
        readCharacteristic(UUID_PRIVATE_SERVICE, UUID_PRIVATE_CHARACTERISTIC);
    }

    /**
     * BroadcastReceiver.
     * Receive a broadcast-intent and write characteristics.
     */
    public static class WriteCharacteristicIntentReceiver extends BroadcastReceiver {
        private static final String TAG = "WriteCharacteristic";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");

            String title = intent.getStringExtra("title");
            String body = intent.getStringExtra("body");
            String message = title + "," + body;

//            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

            if (null != mBluetoothGatt) {
                Log.d(TAG, "send message via BLE: " + message);

                BluetoothGattCharacteristic blechar = mBluetoothGatt.getService(UUID_PRIVATE_SERVICE).getCharacteristic(UUID_PRIVATE_CHARACTERISTIC);
                blechar.setValue(message);
                mBluetoothGatt.writeCharacteristic(blechar);
            }
        }
    }

}
