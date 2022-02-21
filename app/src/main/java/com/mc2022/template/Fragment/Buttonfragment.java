package com.mc2022.template.Fragment;

import static android.content.Context.BATTERY_SERVICE;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mc2022.template.R;
import com.mc2022.template.Service.downloadservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Buttonfragment extends Fragment {


    private Boolean isrunning=false;
    private Boolean istartable=false;
    Button startbtn, stopbtn, recent;
//    View startv,stopv;
    public static Buttonfragment instance;
    public static Buttonfragment getInstance() {
        return instance;
    }
    Intent newsservice;

    public Buttonfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        instance=this;
        View v =inflater.inflate(R.layout.fragment_buttonfragment, container, false);
        startbtn = v.findViewById(R.id.start);
        stopbtn = v.findViewById(R.id.stop);
        recent = v.findViewById(R.id.recent);
//        startv = v.findViewById(R.id.start);
//        stopv= v.findViewById(R.id.stop);
        File f;
        newsservice= new Intent(getContext(), downloadservice.class);
        f = new File(getActivity().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/recentnews.txt");
        try {

            if(f.exists()==false){
                newsservice.putExtra("num","0");
            }
            else{
                BufferedReader br = new BufferedReader(new FileReader(f));
                String s = br.readLine();
                newsservice.putExtra("num",s);
                br.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ActivityManager activityManager= (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(downloadservice.class.getName().equals(service.service.getClassName())){
                startbtn.setVisibility(View.GONE);
                stopbtn.setVisibility(View.VISIBLE);
            }
        }




        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BatteryManager bm = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    bm = (BatteryManager) getActivity().getSystemService(BATTERY_SERVICE);
                }
                int percen = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = getActivity().registerReceiver(null, ifilter);
                int plugged = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean ischarging = false;
                if (plugged == BatteryManager.BATTERY_PLUGGED_AC) {
                    istartable=false;
                } else if (plugged == 0) {
                    // on battery power
                    istartable=true;
                }
                if(istartable){
                    if(percen>=10){
                        System.out.println("Charging :"+ischarging);
                        System.out.println("Percentage: "+percen);
                        isrunning=true;
                        startbtn.setVisibility(View.GONE);
                        stopbtn.setVisibility(View.VISIBLE);
                        startnews();
                    }
                }

            }
        });


        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isrunning=false;
                startbtn.setVisibility(View.VISIBLE);
                stopbtn.setVisibility(View.GONE);
                stopnews();}

        });

        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), RecentnewsFragment.Recentfive.class);
                startActivity(intent);
            }
        });
        return v;
    }
    public Boolean isrunning(){
        return isrunning;
    }

    public void startnews(){
        Boolean check=false;
        ActivityManager activityManager= (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(downloadservice.class.getName().equals(service.service.getClassName())){
                check=true;
            }
        }
        if(!check){
        Toast.makeText(getActivity().getApplicationContext(), "start", Toast.LENGTH_SHORT).show();
        getActivity().startService(newsservice);}

    }
    public void stopnews(){
        getActivity().stopService(newsservice);
        Toast.makeText(getActivity().getApplicationContext(), "stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}