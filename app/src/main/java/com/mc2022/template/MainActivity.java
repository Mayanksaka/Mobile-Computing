package com.mc2022.template;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MyReceiver allBroadcastReceiver=new MyReceiver();
    ErrorHandler errorBroadcast=new ErrorHandler();
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
//        unregisterReceiver(allBroadcastReceiver);
//        unregisterReceiver(errorBroadcast);
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
    }
}