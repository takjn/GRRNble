package com.github.takjn.grrnble;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class DebugFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "DebugFragment";

    private TextView mTextView_ReadChara1;
    private TextView mTextView_ReadChara2;
    private Button mButton_ReadChara1;
    private Button mButton_ReadChara2;
    private CheckBox mCheckBox_NotifyChara1;
    private Button mButton_WriteHello;
    private Button mButton_WriteWorld;

    private DebugListener mDebugListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_debug, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextView_ReadChara1 =  ((TextView) view.findViewById(R.id.textview_readchara1));
        mTextView_ReadChara2 =  ((TextView) view.findViewById(R.id.textview_readchara2));

        mButton_ReadChara1 = (Button) view.findViewById(R.id.button_readchara1);
        mButton_ReadChara1.setOnClickListener(this);

        mButton_ReadChara2 = (Button) view.findViewById(R.id.button_readchara2);
        mButton_ReadChara2.setOnClickListener(this);

        mCheckBox_NotifyChara1 = (CheckBox) view.findViewById(R.id.checkbox_notifychara1);
        mCheckBox_NotifyChara1.setOnClickListener(this);

        mButton_WriteHello = (Button) view.findViewById(R.id.button_writehello);
        mButton_WriteHello.setOnClickListener(this);

        mButton_WriteWorld = (Button) view.findViewById(R.id.button_writeworld);
        mButton_WriteWorld.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DebugListener) {
            mDebugListener = (DebugListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDebugListener = null;
    }

    @Override
    public void onClick(View v) {
        if (mDebugListener == null) {
            return;
        }

        if (mButton_ReadChara1.getId() == v.getId()) {
            mDebugListener.onReadCharacteristic1();
            return;
        }
        if (mButton_ReadChara2.getId() == v.getId()) {
            mDebugListener.onReadCharacteristic2();
            return;
        }
        if (mCheckBox_NotifyChara1.getId() == v.getId()) {
            mDebugListener.onSetCharacteristicNotification1(mCheckBox_NotifyChara1.isChecked());
            return;
        }
        if (mButton_WriteHello.getId() == v.getId()) {
            this.enabled(false);
            mDebugListener.onWriteCharacteristic2("Hello");
            return;
        }
        if (mButton_WriteWorld.getId() == v.getId()) {
            this.enabled(false);
            mDebugListener.onWriteCharacteristic2("World");
            return;
        }
    }

    public void enabled(boolean value) {
        mButton_WriteHello.setEnabled(value);
        mButton_WriteWorld.setEnabled(value);
    }

    public void setChara1(String value) {
        mTextView_ReadChara1.setText(value);
    }

    public void setChara2(String value) {
        mTextView_ReadChara2.setText(value);
    }

    public interface DebugListener {
        public void onReadCharacteristic1();
        public void onSetCharacteristicNotification1(boolean value);
        public void onReadCharacteristic2();
        public void onWriteCharacteristic2(String message);
    }


}