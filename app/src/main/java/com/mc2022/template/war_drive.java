package com.mc2022.template;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.ColorSpace;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mc2022.template.Database.SensorsDatabase;
import com.mc2022.template.Models.Model_wardrive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class war_drive extends AppCompatActivity {
    private WifiManager wifimanager;
    private WifiReciever wifiReciver;
    private RecyclerView recyclerview;
    private WifiAdapter wifiadapter;
    private List<ScanResult> wifilist;
    private LinearLayoutManager layMan;
    private List<ScanResult> desired;
    private EditText locationname, locationroomnumber;
    private Map<String,Integer> routers = new HashMap<>();
    private Button addplace, mylocation, deletedatabase;
    private SensorsDatabase sensorsdb;
    private List<Model_wardrive> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_war_drive);
        sensorsdb= SensorsDatabase.getInstance(getApplicationContext());

        recyclerview = findViewById(R.id.recyclerview);
        addplace= findViewById(R.id.button_add_place);
        mylocation=findViewById(R.id.button_find_my_location);
        deletedatabase= findViewById(R.id.button_delete_database);
        locationname= findViewById(R.id.location_name);
        locationroomnumber= findViewById(R.id.location_room_number);


        wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(!wifimanager.isWifiEnabled()){
            Toast.makeText(this,"Please Enable WIFI",Toast.LENGTH_SHORT).show();
            finish();
        }
        wifimanager.startScan();

        wifiReciver= new WifiReciever();
        registerReceiver(wifiReciver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},0);
            finish();
        }


        calladapter();
        addplace.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                wifilist= scanSuccess();
                desired = new ArrayList<>();
                for(ScanResult i: wifilist){
                    if(i.SSID.equals("STUDENTS-M") && String.valueOf(i.frequency).charAt(0)=='5'){
                        desired.add(i);
//                      System.out.println("\n BSSID:"+i.BSSID+"\n SSID:"+i.SSID+"\n level:"+i.level+"\n frequency:"+i.frequency+"\n timestamp:"+i.timestamp);
                    }
                }                if(locationname.getText().toString().trim().matches("")){
                    Toast.makeText(getApplicationContext(), "Please add name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(locationroomnumber.getText().toString().trim().matches("")){
                    Toast.makeText(getApplicationContext(), "Please add room number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    int count = 0;
                    int latestid = sensorsdb.Daowardrive().getlastid();
                    if (!sensorsdb.Daowardrive().isempty()) {
                        count = latestid;
                        count++;
                    }
                    routers.put("00:2f:5c:64:ce:ca",0);
                    routers.put("48:8b:0a:01:b2:2a",0);
                    routers.put("48:8b:0a:3e:fa:aa",0);
                    routers.put("84:f1:47:0d:cc:ca",0);
                    for(ScanResult i : desired){
                        routers.replace(String.valueOf(i.BSSID),i.level);
                    }
                    ArrayList<Integer> arr= new ArrayList<>();
                    for(int i: routers.values()){
                        arr.add(i);
                    }

                    Model_wardrive m=new Model_wardrive(count,locationname.getText().toString(),locationroomnumber.getText().toString(),arr.get(0),arr.get(1),arr.get(2),arr.get(3));
                    sensorsdb.Daowardrive().insert(m);
                    System.out.println(m);
                    locationroomnumber.setText(null);
                    locationname.setText(null);

                    Toast.makeText(getApplicationContext(), "Place Added", Toast.LENGTH_SHORT).show();
                    data=sensorsdb.Daowardrive().isempty()? null: sensorsdb.Daowardrive().getList();
                    wifiadapter.notifyDataSetChanged();
                }
            }
        });
//        System.out.println("start---------");
        deletedatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorsdb.Daowardrive().deletewardrivetable();
                data=sensorsdb.Daowardrive().isempty()? null: sensorsdb.Daowardrive().getList();
                wifiadapter.notifyDataSetChanged();
            }
        });

        mylocation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                wifilist= scanSuccess();
                desired = new ArrayList<>();
                for(ScanResult i: wifilist){
                    if(i.SSID.equals("STUDENTS-M") && String.valueOf(i.frequency).charAt(0)=='5'){
                        desired.add(i);
//                      System.out.println("\n BSSID:"+i.BSSID+"\n SSID:"+i.SSID+"\n level:"+i.level+"\n frequency:"+i.frequency+"\n timestamp:"+i.timestamp);
                    }
                }
                data=sensorsdb.Daowardrive().isempty()? null: sensorsdb.Daowardrive().getList();
                if(data==null){
                    Toast.makeText(getApplicationContext(),"No data in wardrive",Toast.LENGTH_SHORT).show();

                }else{
                    routers.put("00:2f:5c:64:ce:ca",0);
                    routers.put("48:8b:0a:01:b2:2a",0);
                    routers.put("48:8b:0a:3e:fa:aa",0);
                    routers.put("84:f1:47:0d:cc:ca",0);
                    for(ScanResult i : desired){
                        routers.replace(String.valueOf(i.BSSID),i.level);
                    }
                    ArrayList<Integer> arr= new ArrayList<>();
                    for(int i: routers.values()){
                        arr.add(i);
                    }
                    int smallindex=0;
                    double smallestdistance= 9999;

                    for(int i=0;i <data.size(); i++){
                        double difference= Math.sqrt(Math.pow(arr.get(0)-data.get(i).getSsid_1(),2)+Math.pow(arr.get(1)-data.get(i).getSsid_2(),2)+Math.pow(arr.get(2)-data.get(i).getSsid_3(),2)+Math.pow(arr.get(3)-data.get(i).getSsid_4(),2));
                        if(difference<smallestdistance){
                            smallestdistance=difference;
                            smallindex=i;
                        }
                    }
                    Toast.makeText(getApplicationContext(),"You are in room: "+ data.get(smallindex).getRoom_number(),Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
//    public void scanwifi(){
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//    }
private List<ScanResult> scanSuccess() {
    List<ScanResult> results = wifimanager.getScanResults();
    return results;
}
    public void calladapter(){
        data=sensorsdb.Daowardrive().isempty()? null: sensorsdb.Daowardrive().getList();
        if(wifiadapter==null){
            wifiadapter= new WifiAdapter(getApplicationContext(),data);
        }else{
            wifiadapter.notifyDataSetChanged();
        }
        layMan=new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(layMan);
        recyclerview.setAdapter(wifiadapter);


    }

    class WifiReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}