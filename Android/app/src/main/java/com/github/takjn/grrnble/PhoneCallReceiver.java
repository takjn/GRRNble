package com.github.takjn.grrnble;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneCallReceiver extends BroadcastReceiver {

    @SuppressWarnings("unused")
    private final String TAG = getClass().getSimpleName();

    public void onReceive(Context context, Intent intent) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        MyPhoneStateListener PhoneListener = new MyPhoneStateListener(context);
        tm.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        @SuppressWarnings("unused")
        private final String TAG = getClass().getSimpleName();

        Context mContext;

        public MyPhoneStateListener(Context context) {
            mContext = context;
        }

        public void onCallStateChanged(int state, String callNumber) {
            Log.d(TAG, state + " : " + callNumber);

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    if (!callNumber.isEmpty()) {
                        String message = "Call:" + callNumber;
                        Log.d(TAG, "CALL_STATE_RINGING : " + message);

                        BLEService.writeMessage(mContext.getApplicationContext(), message);
                    }

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
        }
    }
}
