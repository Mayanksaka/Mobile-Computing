package com.mc2022.template;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mc2022.template.Broadcast.MessageRecieverBroadcast;
import com.mc2022.template.Broadcast.MyReceiver_Broadcast;
import com.mc2022.template.Fragment.Buttonfragment;
import com.mc2022.template.Fragment.MainFragment;

public class Mainactivity extends AppCompatActivity {
    MyReceiver_Broadcast allBroadcastReceiver=new MyReceiver_Broadcast();
    MessageRecieverBroadcast errorBroadcast=new MessageRecieverBroadcast();
    IntentFilter lowbattery =new IntentFilter(Intent.ACTION_BATTERY_LOW);
    IntentFilter batteryokay =new IntentFilter(Intent.ACTION_BATTERY_OKAY);
    IntentFilter pconnet =new IntentFilter(Intent.ACTION_POWER_CONNECTED);
    IntentFilter pdisconnect =new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
    //    IntentFilter connectivity =new IntentFilter("INTERNET_CONNECTIVITY");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragment = getSupportFragmentManager();

        FragmentTransaction fragtrans = fragment.beginTransaction();
        fragtrans.replace(R.id.framelayout, new MainFragment()).setReorderingAllowed(true).commit();

        FragmentTransaction fragtrans2 = fragment.beginTransaction();
        fragtrans2.replace(R.id.btnframe, new Buttonfragment()).setReorderingAllowed(true).commit();



    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(allBroadcastReceiver,lowbattery );
        registerReceiver(allBroadcastReceiver, batteryokay);
        registerReceiver(allBroadcastReceiver, pconnet);
        registerReceiver(allBroadcastReceiver, pdisconnect);
        registerReceiver(errorBroadcast, new IntentFilter("INTERNET_CONNECTIVITY"));
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(allBroadcastReceiver);
        unregisterReceiver(errorBroadcast);
    }
}
