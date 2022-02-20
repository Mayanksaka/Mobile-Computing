package com.mc2022.template;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ErrorHandler extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        String errorMsg = intent.getExtras().getString("msg");
        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
    }
}