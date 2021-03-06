package com.github.takjn.grrnble;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DebugFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "DebugFragment";

    private TextView mTextViewReadChara1;
    private TextView mTextViewReadChara2;
    private Button mButtonReadChara1;
    private Button mButtonReadChara2;
    private Button mButtonWriteHello;
    private Button mButtonWriteWorld;

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

        mButtonReadChara1 = (Button) view.findViewById(R.id.button_readchara1);
        mButtonReadChara1.setOnClickListener(this);

        mButtonReadChara2 = (Button) view.findViewById(R.id.button_readchara2);
        mButtonReadChara2.setOnClickListener(this);

        mButtonWriteHello = (Button) view.findViewById(R.id.button_writehello);
        mButtonWriteHello.setOnClickListener(this);

        mButtonWriteWorld = (Button) view.findViewById(R.id.button_writeworld);
        mButtonWriteWorld.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if (mButtonReadChara1.getId() == v.getId()) {
            BLEService.readBattery(getContext());
            return;
        }
        if (mButtonReadChara2.getId() == v.getId()) {
            BLEService.readMessage(getContext());
            return;
        }
        if (mButtonWriteHello.getId() == v.getId()) {
            BLEService.writeMessage(getContext(), "Hello, world.01234567890");
            return;
        }
        if (mButtonWriteWorld.getId() == v.getId()) {
            BLEService.writeMessage(getContext(), "あいうえおかきくけこさしすせそたちつてと");
            return;
        }
    }

    public void setChara1(String value) {
        mTextViewReadChara1.setText(value);
    }

    public void setChara2(String value) {
        mTextViewReadChara2.setText(value);
    }

}