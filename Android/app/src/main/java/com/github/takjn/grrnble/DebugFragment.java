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

    private TextView mTextViewReadChara1;
    private TextView mTextViewReadChara2;
    private TextView mTextViewNotifyChara1;
    private Button mButtonReadChara1;
    private Button mButtonReadChara2;
    private CheckBox mCheckBoxNotifyChara1;
    private Button mButtonWriteHello;
    private Button mButtonWriteWorld;

    private DebugListener mDebugListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_debug, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextViewReadChara1 = ((TextView) view.findViewById(R.id.textview_readchara1));
        mTextViewReadChara2 = ((TextView) view.findViewById(R.id.textview_readchara2));
        mTextViewNotifyChara1 = ((TextView) view.findViewById(R.id.textview_notifychara1));

        mButtonReadChara1 = (Button) view.findViewById(R.id.button_readchara1);
        mButtonReadChara1.setOnClickListener(this);

        mButtonReadChara2 = (Button) view.findViewById(R.id.button_readchara2);
        mButtonReadChara2.setOnClickListener(this);

        mCheckBoxNotifyChara1 = (CheckBox) view.findViewById(R.id.checkbox_notifychara1);
        mCheckBoxNotifyChara1.setOnClickListener(this);

        mButtonWriteHello = (Button) view.findViewById(R.id.button_writehello);
        mButtonWriteHello.setOnClickListener(this);

        mButtonWriteWorld = (Button) view.findViewById(R.id.button_writeworld);
        mButtonWriteWorld.setOnClickListener(this);
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

        if (mButtonReadChara1.getId() == v.getId()) {
            mDebugListener.onReadCharacteristic1();
            return;
        }
        if (mButtonReadChara2.getId() == v.getId()) {
            mDebugListener.onReadCharacteristic2();
            return;
        }
        if (mCheckBoxNotifyChara1.getId() == v.getId()) {
            mDebugListener.onSetCharacteristicNotification1(mCheckBoxNotifyChara1.isChecked());
            return;
        }
        if (mButtonWriteHello.getId() == v.getId()) {
            this.enabled(false);
            mDebugListener.onWriteCharacteristic2("Hello");
            return;
        }
        if (mButtonWriteWorld.getId() == v.getId()) {
            this.enabled(false);
            mDebugListener.onWriteCharacteristic2("World");
            return;
        }
    }

    public void enabled(boolean value) {
        mButtonWriteHello.setEnabled(value);
        mButtonWriteWorld.setEnabled(value);
    }

    public void setChara1(String value) {
        mTextViewReadChara1.setText(value);
    }

    public void setChara2(String value) {
        mTextViewReadChara2.setText(value);
    }

    public void setNotifyChara1(String value) {
        mTextViewNotifyChara1.setText(value);
    }

    public interface DebugListener {
        public void onReadCharacteristic1();

        public void onSetCharacteristicNotification1(boolean value);

        public void onReadCharacteristic2();

        public void onWriteCharacteristic2(String message);
    }


}