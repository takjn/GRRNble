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
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.provider.Settings;
import android.text.TextUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DebugFragment.DebugListener {

    private static final String TAG = "MainActivity";

    // 定数（Bluetooth LE Gatt UUID）
    // Private Service
    private static final UUID UUID_SERVICE_PRIVATE = UUID.fromString("3B382559-223F-48CA-81B4-E151598F661B");
    private static final UUID UUID_CHARACTERISTIC_PRIVATE1 = UUID.fromString("DB5445C4-4A70-4422-87AF-81D35456BEB5");
    private static final UUID UUID_CHARACTERISTIC_PRIVATE2 = UUID.fromString("B2332443-1DD3-407B-B3E6-5D349CAF5368");
    // for Notification
    private static final UUID UUID_NOTIFY = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private static final int REQUEST_ENABLEBLUETOOTH = 1;
    private static final int REQUEST_CONNECTDEVICE = 2;
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    private static BluetoothGatt mBluetoothGatt = null;
    private BluetoothAdapter mBluetoothAdapter;

    private static final long SCAN_PERIOD = 10000;  // スキャン時間。単位はミリ秒。
    private Handler mHandler;                       // UIスレッド操作ハンドラ : 「一定時間後にスキャンをやめる処理」で必要
    private boolean mScanning = false;              // スキャン中かどうかのフラグ
    private BluetoothDevice mDevice = null;         // 発見されたデバイス

    private Button mButtonScan;
    private Button mButtonConnect;
    private Button mButtonDisconnect;
    private DebugFragment mFragmentDebug;


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

//        // メニューの更新
//        invalidateOptionsMenu();
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
        } else {
            mButtonScan.setEnabled(true);
            Toast.makeText(this, R.string.device_is_not_found, Toast.LENGTH_SHORT).show();
        }

//        // メニューの更新
//        invalidateOptionsMenu();
    }


    // BluetoothGattコールバックオブジェクト
    private final BluetoothGattCallback mGattcallback = new BluetoothGattCallback() {
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
                        // GUIアイテムの有効無効の設定
                        // 切断ボタンを有効にする
                        mButtonDisconnect.setEnabled(true);
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

        // サービス検索が完了したときの処理（mBluetoothGatt.discoverServices()の結果として呼ばれる。）
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (BluetoothGatt.GATT_SUCCESS != status) {
                return;
            }

            // 発見されたサービスのループ
            for (BluetoothGattService service : gatt.getServices()) {
                // サービスごとに個別の処理
                if ((null == service) || (null == service.getUuid())) {
                    continue;
                }
                if (UUID_SERVICE_PRIVATE.equals(service.getUuid())) {    // プライベートサービス
                    runOnUiThread(new Runnable() {
                        public void run() {
                            showDebugFragment();
                        }
                    });
                    continue;
                }
            }
        }

        // キャラクタリスティックが読み込まれたときの処理
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (BluetoothGatt.GATT_SUCCESS != status) {
                return;
            }
            // キャラクタリスティックごとに個別の処理
            if (UUID_CHARACTERISTIC_PRIVATE1.equals(characteristic.getUuid())) {    // キャラクタリスティック１：データサイズは、2バイト（数値を想定。0～65,535）
                byte[] byteChara = characteristic.getValue();
                ByteBuffer bb = ByteBuffer.wrap(byteChara);
                final String strChara = String.valueOf(bb.getShort());
                runOnUiThread(new Runnable() {
                    public void run() {
                        mFragmentDebug.setChara1(strChara);
                    }
                });
                return;
            }
            if (UUID_CHARACTERISTIC_PRIVATE2.equals(characteristic.getUuid())) {    // キャラクタリスティック２：データサイズは、8バイト（文字列を想定。半角文字8文字）
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
            // キャラクタリスティックごとに個別の処理
            if (UUID_CHARACTERISTIC_PRIVATE1.equals(characteristic.getUuid())) {    // キャラクタリスティック１：データサイズは、2バイト（数値を想定。0～65,535）
                byte[] byteChara = characteristic.getValue();
                ByteBuffer bb = ByteBuffer.wrap(byteChara);
                final String strChara = String.valueOf(bb.getShort());
                runOnUiThread(new Runnable() {
                    public void run() {
                        mFragmentDebug.setNotifyChara1(strChara);
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
            // キャラクタリスティックごとに個別の処理
            if (UUID_CHARACTERISTIC_PRIVATE2.equals(characteristic.getUuid())) {    // キャラクタリスティック２：データサイズは、8バイト（文字列を想定。半角文字8文字）
                runOnUiThread(new Runnable() {
                    public void run() {
                        mFragmentDebug.enabled(true);
                    }
                });
                return;
            }
        }
    };

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
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");

        super.onResume();
        requestBluetoothFeature();

        // GUIアイテムの有効無効の設定
        mButtonConnect.setEnabled(false);
        mButtonDisconnect.setEnabled(false);
        hideDebugFragment();

        // デバイスアドレスが空でなければ、接続ボタンを有効にする。
        if (mDevice != null) {
            mButtonConnect.setEnabled(true);
        }

        // 接続ボタンを押す
        mButtonConnect.callOnClick();
    }

    // 別のアクティビティ（か別のアプリ）に移行したことで、バックグラウンドに追いやられた時
    @Override
    protected void onPause() {
        super.onPause();

        // 切断
        disconnect();
    }

    // アクティビティの終了直前
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mBluetoothGatt) {
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
            // TODO: NotificationListnerの確認
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // オプションメニュー作成時の処理
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    // オプションメニューのアイテム選択時の処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_search:
                Intent devicelistactivityIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(devicelistactivityIntent, REQUEST_CONNECTDEVICE);
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mButtonScan.getId()) {
            mButtonScan.setEnabled(false);
            startScan();
            return;
        }
        if (mButtonConnect.getId() == v.getId()) {
            mButtonConnect.setEnabled(false);    // 接続ボタンの無効化（連打対策）
            connect();            // 接続
            return;
        }
        if (mButtonDisconnect.getId() == v.getId()) {
            mButtonDisconnect.setEnabled(false);    // 切断ボタンの無効化（連打対策）
            disconnect();            // 切断
            return;
        }
    }

    // 接続
    private void connect() {
        if (mDevice == null) {
            return;
        }

        if (null != mBluetoothGatt) {
            // mBluetoothGattがnullでないなら接続済みか、接続中。
            return;
        }

        // 接続
        mBluetoothGatt = mDevice.connectGatt(this, false, mGattcallback);
    }

    // 切断
    private void disconnect() {
        if (null == mBluetoothGatt) {
            return;
        }

        // 切断
        //   mBluetoothGatt.disconnect()ではなく、mBluetoothGatt.close()しオブジェクトを解放する。
        //   理由：「ユーザーの意思による切断」と「接続範囲から外れた切断」を区別するため。
        //   ①「ユーザーの意思による切断」は、mBluetoothGattオブジェクトを解放する。再接続は、オブジェクト構築から。
        //   ②「接続可能範囲から外れた切断」は、内部処理でmBluetoothGatt.disconnect()処理が実施される。
        //     切断時のコールバックでmBluetoothGatt.connect()を呼んでおくと、接続可能範囲に入ったら自動接続する。
        mBluetoothGatt.close();
        mBluetoothGatt = null;
        // GUIアイテムの有効無効の設定
        // 接続ボタンのみ有効にする
        mButtonConnect.setEnabled(true);
        mButtonDisconnect.setEnabled(false);
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
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
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
                        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
                    }
                });
        alertDialogBuilder.setNegativeButton(R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If you choose to not enable the notification listener
                        // the app. will not work as expected
                    }
                });
        return (alertDialogBuilder.create());
    }

    /**
     * DebugListener.
     */
    @Override
    public void onWriteCharacteristic2(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        writeCharacteristic(UUID_SERVICE_PRIVATE, UUID_CHARACTERISTIC_PRIVATE2, message);
    }

    @Override
    public void onReadCharacteristic1() {
        readCharacteristic(UUID_SERVICE_PRIVATE, UUID_CHARACTERISTIC_PRIVATE1);
    }

    @Override
    public void onReadCharacteristic2() {
        readCharacteristic(UUID_SERVICE_PRIVATE, UUID_CHARACTERISTIC_PRIVATE2);
    }

    @Override
    public void onSetCharacteristicNotification1(boolean value) {
        setCharacteristicNotification(UUID_SERVICE_PRIVATE, UUID_CHARACTERISTIC_PRIVATE1, value);
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

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.show();

            final UUID UUID_SERVICE_PRIVATE = UUID.fromString("3B382559-223F-48CA-81B4-E151598F661B");
            final UUID UUID_CHARACTERISTIC_PRIVATE2 = UUID.fromString("B2332443-1DD3-407B-B3E6-5D349CAF5368");

            if (null != mBluetoothGatt) {
                Log.d(TAG, "send message via BLE: " + message);

                BluetoothGattCharacteristic blechar = mBluetoothGatt.getService(UUID_SERVICE_PRIVATE).getCharacteristic(UUID_CHARACTERISTIC_PRIVATE2);
                blechar.setValue(message);
                mBluetoothGatt.writeCharacteristic(blechar);
            }
        }
    }

}
