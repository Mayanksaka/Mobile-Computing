package com.mc2022.template;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.mc2022.template.Broadcast.MessageRecieverBroadcast;
import com.mc2022.template.Broadcast.MyReceiver_Broadcast;
import com.mc2022.template.Fragment.Buttonfragment;


public class Mainactivity extends AppCompatActivity {
    MyReceiver_Broadcast allBroadcastReceiver=new MyReceiver_Broadcast();
    MessageRecieverBroadcast errorBroadcast=new MessageRecieverBroadcast();
    IntentFilter lowbattery =new IntentFilter(Intent.ACTION_BATTERY_LOW);
    IntentFilter batteryokay =new IntentFilter(Intent.ACTION_BATTERY_OKAY);
    IntentFilter pconnet =new IntentFilter(Intent.ACTION_POWER_CONNECTED);
    IntentFilter pdisconnect =new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragment = getSupportFragmentManager();
        Fragment temp = fragment.findFragmentById(R.id.framelayout);
        if(temp==null){
            temp = new recyclerview_fragment();
            fragment.beginTransaction().add(R.id.framelayout, temp).commit();
        }
        temp = fragment.findFragmentById(R.id.btnframe);
        if(temp==null){
            temp = new Buttonfragment();
            fragment.beginTransaction().add(R.id.btnframe, temp).commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(allBroadcastReceiver,lowbattery );
        registerReceiver(allBroadcastReceiver, batteryokay);
        registerReceiver(allBroadcastReceiver, pconnet);
        registerReceiver(allBroadcastReceiver, pdisconnect);
        registerReceiver(errorBroadcast, new IntentFilter("INTERNET_CONNECTIVITY"));
//        registerReceiver(allBroadcastReceiver,new IntentFilter("UPDATE_RECYCLERVIEW"));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(allBroadcastReceiver);
        unregisterReceiver(errorBroadcast);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

}
