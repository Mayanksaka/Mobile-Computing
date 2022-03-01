package com.mc2022.template.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mc2022.template.Fragment.Buttonfragment;
import com.mc2022.template.Fragment.MainFragment;

public class MyReceiver_Broadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Buttonfragment b= Buttonfragment.getInstance();
        Boolean stop=false;
        // TODO: This method is called when the BroadcastReceiver is receiving
        if(intent.getAction() == Intent.ACTION_POWER_DISCONNECTED)
        {
            stop=true;
            Toast.makeText(context, "Power Disconnected", Toast.LENGTH_SHORT).show();
            if(b.isrunning()==true)
                b.startnews();
        }
        else if(intent.getAction() == Intent.ACTION_POWER_CONNECTED)
        {
            stop=false;
            Toast.makeText(context, "Power Connected", Toast.LENGTH_SHORT).show();
            b.stopnews();
        }
        else if(intent.getAction() == Intent.ACTION_BATTERY_LOW)
        {
            stop=false;
            Toast.makeText(context, "Battery Low", Toast.LENGTH_SHORT).show();
            b.stopnews();

        }
        else if(intent.getAction() == Intent.ACTION_BATTERY_OKAY)
        {
            Toast.makeText(context, "Battery Okay", Toast.LENGTH_SHORT).show();
            if(b.isrunning()==true){
                Toast.makeText(context, "Service running", Toast.LENGTH_SHORT).show();
                if(!stop){
                b.startnews();
                stop=true;
            }}
        }
        else if(intent.getAction().equals("FILE_DOWNLOADED"))
        {
            MainFragment fra= MainFragment.getInstance();
            Integer num=intent.getExtras().getInt("num");
            fra.runn(num);
        }
        else
        {
            Toast.makeText(context, "Only God Know", Toast.LENGTH_SHORT).show();
        }


//        throw new UnsupportedOperationException("Not yet implemented");
    }
}