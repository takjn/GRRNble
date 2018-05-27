package com.github.takjn.grrnble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_ENABLEBLUETOOTH = 1;
    private static final int REQUEST_CONNECTDEVICE = 2;

    private BluetoothAdapter mBluetoothAdapter;    // BluetoothAdapter : Bluetooth処理で必要
    private String mDeviceAddress = "";    // デバイスアドレス

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if system has BLE feature
        if( !getPackageManager().hasSystemFeature( PackageManager.FEATURE_BLUETOOTH_LE ) ) {
            Toast.makeText( this, R.string.ble_is_not_supported, Toast.LENGTH_SHORT ).show();
            finish();
            return;
        }

        // Get BluetoothAdapter
        BluetoothManager bluetoothManager = (BluetoothManager)getSystemService( Context.BLUETOOTH_SERVICE );
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if( mBluetoothAdapter == null ) {
            // System does not have Bluetooth service
            Toast.makeText( this, R.string.bluetooth_is_not_supported, Toast.LENGTH_SHORT ).show();
            finish();
            return;
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");

        super.onResume();
        requestBluetoothFeature();
    }

    private void requestBluetoothFeature() {
        if( mBluetoothAdapter.isEnabled() ) {
            return;
        }

        Intent enableBtIntent = new Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE );
        startActivityForResult( enableBtIntent, REQUEST_ENABLEBLUETOOTH );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        switch( requestCode ) {
            case REQUEST_ENABLEBLUETOOTH:
                if( Activity.RESULT_CANCELED == resultCode ) {
                    Toast.makeText( this, R.string.bluetooth_is_not_working, Toast.LENGTH_SHORT ).show();
                    finish();
                    return;
                }
                break;
            case REQUEST_CONNECTDEVICE:
                String strDeviceName;
                if( Activity.RESULT_OK == resultCode ) {
                    // デバイスリストアクティビティからの情報の取得
                    strDeviceName = data.getStringExtra( DeviceListActivity.EXTRAS_DEVICE_NAME );
                    mDeviceAddress = data.getStringExtra( DeviceListActivity.EXTRAS_DEVICE_ADDRESS );
                }
                else {
                    strDeviceName = "";
                    mDeviceAddress = "";
                }
                ( (TextView)findViewById( R.id.textview_devicename ) ).setText( strDeviceName );
                ( (TextView)findViewById( R.id.textview_deviceaddress ) ).setText( mDeviceAddress );
                break;
        }
        super.onActivityResult( requestCode, resultCode, data );
    }

    // オプションメニュー作成時の処理
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.activity_main, menu );
        return true;
    }

    // オプションメニューのアイテム選択時の処理
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch( item.getItemId() ) {
            case R.id.menuitem_search:
                Intent devicelistactivityIntent = new Intent( this, DeviceListActivity.class );
                startActivityForResult( devicelistactivityIntent, REQUEST_CONNECTDEVICE );
                return true;
        }
        return false;
    }

}
