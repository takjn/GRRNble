package com.github.takjn.grrnble;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DebugFragment.DebugListener {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_ENABLEBLUETOOTH = 1;
    private static final long SCAN_PERIOD = 10000;  // スキャン時間。単位はミリ秒。
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice = null;
    private Handler mHandler;                       // UIスレッド操作ハンドラ : 「一定時間後にスキャンをやめる処理」で必要
    private boolean mScanning = false;              // スキャン中かどうかのフラグ

    private Button mButtonConnect;
    private Button mButtonDisconnect;
    private CheckBox mCheckBoxBatteryLevel;
    private CheckBox mCheckBoxTemperature;
    private ProgressBar mProgressBar;
    private TextView mTextViewBatteryLevel;
    private TextView mTextViewTemperature;
    private DebugFragment mFragmentDebug;

    private BLEIntentReceiver mBLEIntentReceiver;

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

    /**
     * Start device scan
     */
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

        ScanFilter scanFilter = new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString(BLEService.UUID_PRIVATE_SERVICE)).build();
        List<ScanFilter> scanFilterList = new ArrayList();
        scanFilterList.add(scanFilter);

        ScanSettings scanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_BALANCED).build();
        mScanning = true;
        scanner.startScan(scanFilterList, scanSettings, mLeScanCallback);

        mProgressBar.setVisibility(ProgressBar.VISIBLE);
    }

    /**
     * Stop device scan
     */
    private void stopScan() {
        mScanning = false;
        mProgressBar.setVisibility(ProgressBar.GONE);

        mHandler.removeCallbacksAndMessages(null);
        android.bluetooth.le.BluetoothLeScanner scanner = mBluetoothAdapter.getBluetoothLeScanner();
        if (scanner == null) {
            return;
        }
        scanner.stopScan(mLeScanCallback);

        if (mDevice != null) {
            // Device is found
            connect();
        } else {
            mButtonConnect.setEnabled(true);
            Toast.makeText(this, R.string.device_is_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonConnect = (Button) findViewById(R.id.button_connect);
        mButtonConnect.setOnClickListener(this);

        mButtonDisconnect = (Button) findViewById(R.id.button_disconnect);
        mButtonDisconnect.setOnClickListener(this);
        mCheckBoxBatteryLevel = (CheckBox) findViewById(R.id.checkbox_battery_level);
        mCheckBoxBatteryLevel.setOnClickListener(this);
        mCheckBoxTemperature = (CheckBox) findViewById(R.id.checkbox_temperature);
        mCheckBoxTemperature.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTextViewBatteryLevel = (TextView) findViewById(R.id.textview_battery_level);
        mTextViewTemperature = (TextView) findViewById(R.id.textview_temperature);

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

        mButtonConnect.setEnabled(true);
        mButtonDisconnect.setEnabled(false);
        mCheckBoxBatteryLevel.setEnabled(false);
        mCheckBoxTemperature.setEnabled(false);
        mProgressBar.setVisibility(ProgressBar.GONE);
        hideDebugFragment();

        mBLEIntentReceiver = BLEIntentReceiver.register(getApplicationContext(), new BLEIntentReceiver.Callback() {
            @Override
            public void onEventInvoked(String action, String uuid, String value) {
                switch (action) {
                    case BLEIntentReceiver.ACTION_READ:
                        if (mFragmentDebug != null) {
                            if (uuid.equals(BLEService.UUID_BATTERY_LEVEL_CHARACTERISTIC)) {
                                mFragmentDebug.setChara1(value);

                            } else if (uuid.equals(BLEService.UUID_PRIVATE_CHARACTERISTIC)) {
                                mFragmentDebug.setChara2(value);
                            }
                        }
                        break;
                    case BLEIntentReceiver.ACTION_CHANGED:
                        if (uuid.equals(BLEService.UUID_BATTERY_LEVEL_CHARACTERISTIC)) {
                            mTextViewBatteryLevel.setText(value);
                        } else if (uuid.equals(BLEService.UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC)) {
                            mTextViewTemperature.setText(value);
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");

        super.onResume();
        requestBluetoothFeature();

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        if (mDevice == null && BLEService.mDevice != null) {
            mDevice = BLEService.mDevice;
            reconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mBLEIntentReceiver!=null) {
            mBLEIntentReceiver.unregister();
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
        if (id == R.id.action_timesync) {
            SimpleDateFormat sdf = new SimpleDateFormat("yy,MM,dd,F,HH,mm");
            Calendar cl = Calendar.getInstance();
            String datetime = "DT," + sdf.format(cl.getTime());

            Log.d(TAG, datetime);
            onWritePrivateCharacteristic(datetime);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mButtonConnect.getId()) {
            mButtonConnect.setEnabled(false);
            startScan();
            return;
        }
        if (mButtonDisconnect.getId() == v.getId()) {
            mButtonDisconnect.setEnabled(false);
            disconnect();
            return;
        }

        if (mCheckBoxBatteryLevel.getId() == v.getId()) {
            Intent intent = new Intent(getApplicationContext(), BLEService.BLECommandIntentReceiver.class);
            intent.setAction("SET_NOTIFY");
            intent.putExtra("service", BLEService.UUID_BATTERY_SERVICE);
            intent.putExtra("characteristic", BLEService.UUID_BATTERY_LEVEL_CHARACTERISTIC);
            intent.putExtra("enable", mCheckBoxBatteryLevel.isChecked());
            sendBroadcast(intent);
            return;
        }
        if (mCheckBoxTemperature.getId() == v.getId()) {
            Intent intent = new Intent(getApplicationContext(), BLEService.BLECommandIntentReceiver.class);
            intent.setAction("SET_NOTIFY");
            intent.putExtra("service", BLEService.UUID_PRIVATE_SERVICE);
            intent.putExtra("characteristic", BLEService.UUID_PRIVATE_TEMPERATURE_CHARACTERISTIC);
            intent.putExtra("enable", mCheckBoxTemperature.isChecked());
            sendBroadcast(intent);
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

        reconnect();

        Intent intent = new Intent(getApplication(), BLEService.class);
        intent.putExtra("device", mDevice);
        startService(intent);
    }

    /**
     * Reconnect to the device
     */
    private void reconnect() {
        mButtonDisconnect.setEnabled(true);
        mCheckBoxBatteryLevel.setEnabled(true);
        mCheckBoxTemperature.setEnabled(true);

        ((TextView) findViewById(R.id.textview_devicename)).setText(mDevice.getName());
        ((TextView) findViewById(R.id.textview_deviceaddress)).setText(mDevice.getAddress());

        mButtonConnect.setEnabled(false);
    }

    /**
     * Disconnect from the device
     */
    private void disconnect() {
        Intent intent = new Intent(getApplication(), BLEService.class);
        stopService(intent);

        mButtonConnect.setEnabled(true);
        mButtonDisconnect.setEnabled(false);
        mCheckBoxBatteryLevel.setEnabled(false);
        mCheckBoxBatteryLevel.setChecked(false);
        mCheckBoxTemperature.setEnabled(false);
        mCheckBoxTemperature.setChecked(false);
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
        // send a explicit broadcast intent
        BLEService.sendToWatch(getApplicationContext(), message);
    }

    @Override
    public void onReadBatteryLevelCharacteristic() {
        // send a explicit broadcast intent
        Intent intent = new Intent(getApplicationContext(), BLEService.BLECommandIntentReceiver.class);
        intent.setAction("READ");
        intent.putExtra("service", BLEService.UUID_BATTERY_SERVICE);
        intent.putExtra("characteristic", BLEService.UUID_BATTERY_LEVEL_CHARACTERISTIC);
        sendBroadcast(intent);
    }

    @Override
    public void onReadPrivateCharacteristic() {
        // send a explicit broadcast intent
        Intent intent = new Intent(getApplicationContext(), BLEService.BLECommandIntentReceiver.class);
        intent.setAction("READ");
        intent.putExtra("service", BLEService.UUID_PRIVATE_SERVICE);
        intent.putExtra("characteristic", BLEService.UUID_PRIVATE_CHARACTERISTIC);
        sendBroadcast(intent);
    }

}