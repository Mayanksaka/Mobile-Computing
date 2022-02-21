package com.mc2022.template.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MessageRecieverBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        String Msg = intent.getExtras().getString("msg");
        Toast.makeText(context, Msg, Toast.LENGTH_SHORT).show();
    }
}