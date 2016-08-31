package com.gosuncn.cu.module.animview;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gosuncn.cu.R;

/**
 * Created by Jack Wang on 2016/5/6.
 */
public class AVLoadingIndicatorDialog extends AlertDialog {

    private TextView mMessageView;

    public AVLoadingIndicatorDialog(Context context) {
        super(context);
        View view=LayoutInflater.from(getContext()).inflate(R.layout.dialog_proress_avld,null);
        mMessageView= (TextView) view.findViewById(R.id.message);
        setView(view);
    }


    @Override
    public void setMessage(CharSequence message) {
        mMessageView.setText(message);
    }
}