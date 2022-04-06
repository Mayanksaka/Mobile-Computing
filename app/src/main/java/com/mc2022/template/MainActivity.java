package com.mc2022.template;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.mc2022.template.Database.SensorsDatabase;
import com.mc2022.template.Models.Model_GPS;
import com.mc2022.template.Models.Model_Gyroscope;
import com.mc2022.template.Models.Model_Light;
import com.mc2022.template.Models.Model_Linear_Accelerometer;
import com.mc2022.template.Models.Model_Orientation;
import com.mc2022.template.Models.Model_Proximity;
import com.mc2022.template.Models.Model_Temperature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    private SensorManager smanager;
    private TextView val_acc_x,val_acc_y,val_acc_z, status;
    private TextView val_orien_x,val_orien_y,val_orien_z;
    private TextView val_gyro_x,val_gyro_y,val_gyro_z;
    private TextView val_gps_lati,val_gps_longi,val_gps_name,val_gps_address,nearby;
    private Button addplace,findplace;
    private TextView val_light, val_temp, val_proxi;
    private LocationManager locationManager;
    private SensorsDatabase sensorsdb;
    private Sensor sensor_accelerometer,sensor_light,sensor_temp, sensor_gps,sensor_gyro,sensor_proxi,sensor_orien;
    private Switch switch_acc,switch_gps,switch_light,switch_gyro,switch_proxi,switch_orien,switch_temp;
    private String TAG= "MainActivity";
    private LineChart chart_proxi, chart_accleration;
    private Boolean activatedproxy=false, activatedorien=false;
    private long lastwave=0;
    private ScrollView scroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scroller= findViewById(R.id.scrollview);
        smanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorsdb= SensorsDatabase.getInstance(getApplicationContext());

        switch_gps = (Switch) findViewById(R.id.switch_gps);
        val_gps_lati = findViewById(R.id.value_gps_x);
        val_gps_longi = findViewById(R.id.value_gps_y);
        val_gps_name = findViewById(R.id.gps_name);
        val_gps_address= findViewById(R.id.gps_address);
        addplace = findViewById(R.id.addaplace);
        findplace = findViewById(R.id.findmyplace);
        nearby = findViewById(R.id.nearbyplaces);
        findplace.setEnabled(false);
        addplace.setEnabled(false);
        switch_gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
                        switch_gps.setChecked(false);
                        return;
                    }
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, -1, -1,MainActivity.this);
                    Toast.makeText(MainActivity.this, "GPS started", Toast.LENGTH_SHORT).show();
                }
                else if (!isChecked){
                    val_gps_longi.setText(null);
                    val_gps_lati.setText(null);
                    val_gps_name.setText(null);
                    val_gps_address.setText(null);
                    locationManager.removeUpdates(MainActivity.this);
                    findplace.setEnabled(false);
                    addplace.setEnabled(false);

                    Toast.makeText(MainActivity.this, "GPS stopped", Toast.LENGTH_SHORT).show();

                }
            }
        });
        addplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(val_gps_name.getText().toString().trim().matches("")){
                    Toast.makeText(MainActivity.this, "Please add name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(val_gps_address.getText().toString().trim().matches("")){
                    Toast.makeText(MainActivity.this, "Please add address", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                int count=0;
                int latestid=sensorsdb.Daogps().getlastid();
                if(!sensorsdb.Daogps().isempty()){
                    count=latestid;
                    count++;
                }
                long time = System.currentTimeMillis();
                sensorsdb.Daogps().insert(new Model_GPS(count,time,Double.parseDouble(val_gps_longi.getText().toString().substring(6)),Double.parseDouble(val_gps_lati.getText().toString().substring(6)),val_gps_name.getText().toString(),val_gps_address.getText().toString()));
                val_gps_name.setText(null);
                val_gps_address.setText(null);
                Toast.makeText(MainActivity.this, "Place Added", Toast.LENGTH_SHORT).show();}

            }
        });

        findplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double longi = Double.parseDouble(val_gps_longi.getText().toString().substring(6));
                double lati = Double.parseDouble(val_gps_lati.getText().toString().substring(6));
                List<Model_GPS> list = sensorsdb.Daogps().getList();
                int sizee = list.size();
                if (sizee > 0) {
                    Map<Double, Model_GPS> distancemodel = new HashMap<>();
                    ArrayList<Double> keyvalues = new ArrayList<>();
                    for (Model_GPS item : list) {
                        double dis = getdistance(item, longi, lati);
                        distancemodel.put(dis, item);
                        keyvalues.add(dis);
                    }
                    Collections.sort(keyvalues);

                    if (sizee < 3) {
                        if (sizee == 2) {
                            nearby.setText("1. " + distancemodel.get(keyvalues.get(0)) + "\n\n2. " + distancemodel.get(keyvalues.get(1)));
                        }
                        if (sizee == 1) {
                            nearby.setText("1. " + distancemodel.get(keyvalues.get(0)));
                        }
                    } else {
                        nearby.setText("1. " + distancemodel.get(keyvalues.get(0)) + "\n\n2. " + distancemodel.get(keyvalues.get(1)) + "\n\n3. " + distancemodel.get(keyvalues.get(2)));
                    }
                }else{
                    nearby.setText("Places Not Added");

                }

            }
        });

        switch_light = (Switch) findViewById(R.id.switch_light);
        val_light = findViewById(R.id.value_light);
        switch_light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    sensor_light = smanager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    if (sensor_light != null) {
                        smanager.registerListener(MainActivity.this, sensor_light, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Light started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Light Not Supported", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    smanager.unregisterListener(MainActivity.this, smanager.getDefaultSensor(Sensor.TYPE_LIGHT));
                    val_light.setText(null);
                    Toast.makeText(MainActivity.this, "Light stopped", Toast.LENGTH_SHORT).show();

                }
            }
        });

        switch_gyro = (Switch) findViewById(R.id.switch_gyroscope);
        val_gyro_x = findViewById(R.id.value_gyroscope_x);
        val_gyro_y = findViewById(R.id.value_gyroscope_y);
        val_gyro_z = findViewById(R.id.value_gyroscope_z);
        switch_gyro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    sensor_gyro = smanager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                    if (sensor_gyro != null) {

                        smanager.registerListener(MainActivity.this, sensor_gyro, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Gyroscope started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Gyroscope Not supported", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    smanager.unregisterListener(MainActivity.this, smanager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
                    val_gyro_x.setText(null);
                    val_gyro_y.setText(null);
                    val_gyro_z.setText(null);
                    Toast.makeText(MainActivity.this, "Gyroscope stopped", Toast.LENGTH_SHORT).show();

                }
            }
        });

        switch_proxi = (Switch) findViewById(R.id.switch_proximity);
        val_proxi = findViewById(R.id.value_proximity);
        chart_proxi =findViewById(R.id.chart_proximity);
        chart_proxi.setDragEnabled(true);
        chart_proxi.setScaleEnabled(false);
        switch_proxi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    sensor_proxi = smanager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                    if (sensor_proxi != null) {

                        smanager.registerListener(MainActivity.this, sensor_proxi, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Proximity started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Proximity Not supported", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    smanager.unregisterListener(MainActivity.this, smanager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
                    val_proxi.setText(null);
                    Toast.makeText(MainActivity.this, "Proximity stopped", Toast.LENGTH_SHORT).show();

                }
            }
        });

        switch_orien = (Switch) findViewById(R.id.switch_orientation);
        val_orien_x = findViewById(R.id.value_orientation_x);
        val_orien_y = findViewById(R.id.value_orientation_y);
        val_orien_z = findViewById(R.id.value_orientation_z);
        switch_orien.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    sensor_orien = smanager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
                    if (sensor_orien != null) {

                        smanager.registerListener(MainActivity.this, sensor_orien, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Orientation started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Orientation Not supported", Toast.LENGTH_SHORT).show();

                        Log.i(TAG,"Orientation Not supported");
                    }
                } else {
                    smanager.unregisterListener(MainActivity.this, smanager.getDefaultSensor(Sensor.TYPE_ORIENTATION));
                    val_orien_x.setText(null);
                    val_orien_y.setText(null);
                    val_orien_z.setText(null);
                    Toast.makeText(MainActivity.this, "Orientation stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switch_temp = (Switch) findViewById(R.id.switch_temperature);
        val_temp = findViewById(R.id.value_temperature);
        switch_temp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    sensor_temp = smanager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                    if (sensor_temp != null) {

                        smanager.registerListener(MainActivity.this, sensor_temp, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Temperature started", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        Toast.makeText(MainActivity.this, "Temperature Not supported", Toast.LENGTH_SHORT).show();
                        Log.i(TAG,"Temperature Not supported");
                        switch_temp.setChecked(false);
                    }
                } else {
                    smanager.unregisterListener(MainActivity.this, smanager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE));
                    val_temp.setText(null);
                    Toast.makeText(MainActivity.this, "Temperature stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switch_acc = (Switch) findViewById(R.id.switch_accelerometer);
        val_acc_x = findViewById(R.id.value_accelerometer_x);
        val_acc_y = findViewById(R.id.value_accelerometer_y);
        val_acc_z = findViewById(R.id.value_accelerometer_z);
        status = findViewById(R.id.status_accelerometer_movement);
        chart_accleration= findViewById(R.id.chart_accelerometer);
        chart_accleration.setDragEnabled(true);
        chart_accleration.setScaleEnabled(false);
        switch_acc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    sensor_accelerometer = smanager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                    if (sensor_accelerometer != null) {

                        smanager.registerListener(MainActivity.this, sensor_accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Accelerometer sensor started", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        Toast.makeText(MainActivity.this, "Accelerometer Not supported", Toast.LENGTH_SHORT).show();

                        Log.i(TAG,"Accelerometer Not supported");
                    }
                } else {
                    smanager.unregisterListener(MainActivity.this, smanager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
                    val_acc_x.setText(null);
                    val_acc_y.setText(null);
                    val_acc_z.setText(null);
                    Toast.makeText(MainActivity.this, "Accelerometer stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });
        creategraph("proxy");
        creategraph("acc");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sevent = sensorEvent.sensor;
        if(sevent.getType() == Sensor.TYPE_LIGHT){
            int count=0;
            int latestid=sensorsdb.Daolight().getlastid();
            if(!sensorsdb.Daolight().isempty()){
                count=latestid;
                count++;
            }
            long time = System.currentTimeMillis();
            sensorsdb.Daolight().insert(new Model_Light(count,time,sensorEvent.values[0]));
            val_light.setText("Lux: "+ Float.toString(sensorEvent.values[0]));
            if(latestid>9){
                sensorsdb.Daolight().deleteitem(latestid-9);
            }

            if(sensorEvent.values[0]<10){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                chart_accleration.setBackgroundColor(Color.LTGRAY);
                chart_proxi.setBackgroundColor(Color.LTGRAY);
                Toast.makeText(MainActivity.this, "Dark Mode Activated", Toast.LENGTH_SHORT).show();


            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                chart_accleration.setBackgroundColor(Color.WHITE);
                chart_proxi.setBackgroundColor(Color.WHITE);
                Toast.makeText(MainActivity.this, "Dark Mode Deactivated", Toast.LENGTH_SHORT).show();

            }



//            System.out.println(sensorsdb.Daolight().getList());

        }
        else if (sevent.getType() == Sensor.TYPE_GYROSCOPE){
            int count=0;
            int latestid=sensorsdb.Daogyro().getlastid();
            if(!sensorsdb.Daogyro().isempty()){
                count=latestid;
                count++;
            }
            long time = System.currentTimeMillis();
            sensorsdb.Daogyro().insert(new Model_Gyroscope(count,time,sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]));
            val_gyro_x.setText("X: "+ Float.toString(sensorEvent.values[0]));
            val_gyro_y.setText("Y: "+ Float.toString(sensorEvent.values[1]));
            val_gyro_z.setText("Z: "+ Float.toString(sensorEvent.values[2]));

            if(latestid>9){
                sensorsdb.Daogyro().deleteitem(latestid-9);
            }

        }
        else if(sevent.getType() == Sensor.TYPE_PROXIMITY){
            int count=0;
            int latestid=sensorsdb.Daoproxi().getlastid();
            if(!sensorsdb.Daoproxi().isempty()){
                count=latestid;
                count++;
            }

            long time = System.currentTimeMillis();
            sensorsdb.Daoproxi().insert(new Model_Proximity(count,time,sensorEvent.values[0]));
            val_proxi.setText("Distance: "+ Float.toString(sensorEvent.values[0]));
            if(latestid>9){
                sensorsdb.Daoproxi().deleteitem(latestid-9);
            }
            List<Model_Proximity> list = sensorsdb.Daoproxi().getList();
            int size= list.size();
            if(size>9){
                System.out.println(list.get(9).getTime()-list.get(5).getTime());

            if(list.get(9).getTime()-list.get(6).getTime()<10000){
                if(activatedproxy==false && list.get(9).getTime()-lastwave>15000){
                    switch_gyro.setChecked(false);
                    switch_acc.setChecked(false);
                    switch_temp.setChecked(false);
                    switch_light.setChecked(false);
                    switch_orien.setChecked(false);
                    switch_gps.setChecked(false);

                    activatedproxy=true;
                    lastwave= System.currentTimeMillis();
                    System.out.println("Sensors_stopped");
                    Toast.makeText(MainActivity.this, "Wave Detected", Toast.LENGTH_SHORT).show();
                }
                if(activatedproxy==true&& list.get(9).getTime()-lastwave>10000){
                    switch_gyro.setChecked(true);
                    switch_acc.setChecked(true);
                    switch_temp.setChecked(true);
                    switch_light.setChecked(true);
                    switch_orien.setChecked(true);
                    switch_gps.setChecked(true);
                    activatedproxy=false;
                    lastwave= System.currentTimeMillis();
                    System.out.println("Sensors_start");
                    Toast.makeText(MainActivity.this, "Wave Detected", Toast.LENGTH_SHORT).show();


                }
            }


            }

            creategraph("proxy");
        }
        else if (sevent.getType() == Sensor.TYPE_ORIENTATION) {
            int count = 0;
            int latestid = sensorsdb.Daoorien().getlastid();
            if (!sensorsdb.Daoorien().isempty()) {
                count = latestid;
                count++;
            }
            long time = System.currentTimeMillis();
            sensorsdb.Daoorien().insert(new Model_Orientation(count, time, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]));
            val_orien_x.setText("A: " + Float.toString(sensorEvent.values[0]));
            val_orien_y.setText("P: " + Float.toString(sensorEvent.values[1]));
            val_orien_z.setText("R: " + Float.toString(sensorEvent.values[2]));
            if (latestid > 9) {
                sensorsdb.Daoorien().deleteitem(latestid - 9);
            }
            double a = sensorEvent.values[0];
            double p = sensorEvent.values[1];
            double r = sensorEvent.values[2];

            if(activatedorien==false) {
                if (a > 1.4 && a < 1.7 ) {
                    switch_gyro.setChecked(false);
                    switch_acc.setChecked(false);
                    switch_temp.setChecked(false);
                    switch_light.setChecked(false);
                    switch_proxi.setChecked(false);
                    switch_gps.setChecked(false);
                    activatedorien = true;
                    lastwave = System.currentTimeMillis();
                    System.out.println("Sensors_stopped");
                    Toast.makeText(MainActivity.this, "Phone Flipped", Toast.LENGTH_SHORT).show();
                }
            }
            if(activatedorien==true){
                if( a >-1.7 && a<-1.4 ){
                    switch_gyro.setChecked(true);
                    switch_acc.setChecked(true);
                    switch_temp.setChecked(true);
                    switch_light.setChecked(true);
                    switch_proxi.setChecked(true);
                    switch_gps.setChecked(true);
                    activatedorien=false;
                    lastwave= System.currentTimeMillis();
                    System.out.println("Sensors_start");
                    Toast.makeText(MainActivity.this, "Phone Unflipped", Toast.LENGTH_SHORT).show();


                }
            }
        }
        else if(sevent.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            int count=0;
            int latestid=sensorsdb.Daotemp().getlastid();
            if(!sensorsdb.Daotemp().isempty()){
                count=latestid;
                count++;
            }
            long time = System.currentTimeMillis();
            sensorsdb.Daotemp().insert(new Model_Temperature(count,time,sensorEvent.values[0]));
            val_temp.setText("Temp: "+ Float.toString(sensorEvent.values[0]));

            if(latestid>9){
                sensorsdb.Daotemp().deleteitem(latestid-9);
            }
        }
        else if (sevent.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            int count=0;
            int latestid=sensorsdb.Daoacc().getlastid();
            if(!sensorsdb.Daoacc().isempty()){
                count=latestid;
                count++;
            }
            long time = System.currentTimeMillis();

            sensorsdb.Daoacc().insert(new Model_Linear_Accelerometer(count,time,sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]));
            val_acc_x.setText("X: "+ Float.valueOf(sensorEvent.values[0]));
            val_acc_y.setText("Y: "+ Float.valueOf(sensorEvent.values[1]));
            val_acc_z.setText("Z: "+ Float.valueOf(sensorEvent.values[2]));
            if(latestid>9){
                sensorsdb.Daoacc().deleteitem(latestid-9);
            }
            double x=sensorEvent.values[0];
            double y= sensorEvent.values[1];
            double z=sensorEvent.values[2];
            double isstat=Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
            if(isstat>0.2){
                status.setText("Moving");
                status.setTextColor(Color.RED);
            }
            else{
                status.setText("Stationary");
                status.setTextColor(Color.GREEN);

            }

            creategraph("acc");

        }
    }

    private void creategraph(String name) {
        ArrayList<Entry> data = new ArrayList<>();

        if(name.equals("proxy")){
            int cc=1;
            for( Model_Proximity item : sensorsdb.Daoproxi().getList()){
                data.add(new Entry(cc,item.getPvalue()));
                cc++;
            }
            LineDataSet lineset = new LineDataSet(data,"Proximity Value");
            lineset.setColor(Color.RED);
            ArrayList<ILineDataSet> dataset= new ArrayList<>();
            dataset.add(lineset);
            LineData linedata = new LineData(dataset);
            Description desc = new Description();
            desc.setText("");
//            chart_proxi.setBackgroundColor(Color.WHITE);
            chart_proxi.post(new Runnable() {
                @Override
                public void run() {
                    chart_proxi.setDescription(desc);
                    chart_proxi.setData(linedata);
                    chart_proxi.notifyDataSetChanged();
                    chart_proxi.invalidate();
                }
            });

        }
        else{
            int cc=1;
            for( Model_Linear_Accelerometer item : sensorsdb.Daoacc().getList()){
                double value= (item.getX_axis()+ item.getY_axis()+ item.getZ_axis())/3;
                Double Dval = Double.valueOf(value);
                Float fval = Dval.floatValue();
                data.add(new Entry(cc,fval));
                cc++;
            }
            LineDataSet lineset = new LineDataSet(data,"Average Value of Linear Accelerometer");
            lineset.setColor(Color.RED);
            ArrayList<ILineDataSet> dataset= new ArrayList<>();
            dataset.add(lineset);
            LineData linedata = new LineData(dataset);
            Description desc = new Description();
            desc.setText("");
//            chart_accleration.setBackgroundColor(Color.WHITE);
            chart_accleration.post(new Runnable() {
                @Override
                public void run() {
                    chart_accleration.setDescription(desc);
                    chart_accleration.setData(linedata);
                    chart_accleration.notifyDataSetChanged();
                    chart_accleration.invalidate();
                }
            });

        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        findplace.setEnabled(true);
        addplace.setEnabled(true);
        val_gps_longi.setText("Long: "+String.valueOf(location.getLongitude()));
        val_gps_lati.setText("Lati: "+String.valueOf(location.getLatitude()));
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                outState.putInt("scroll_x",scroller.getScrollX());
                outState.putInt("scroll_y",scroller.getScrollY());
                outState.putBoolean("gyro",switch_gyro.isChecked());
                outState.putBoolean("acc",switch_acc.isChecked());
                outState.putBoolean("temp",switch_temp.isChecked());
                outState.putBoolean("light",switch_light.isChecked());
                outState.putBoolean("proxi",switch_proxi.isChecked());
                outState.putBoolean("orien",switch_orien.isChecked());
                outState.putBoolean("gps",switch_gps.isChecked());
                outState.putBoolean("actiproxy",activatedproxy);
                outState.putBoolean("actiorien",activatedorien);
                outState.putString("name",val_gps_name.getText().toString());
                outState.putString("address",val_gps_address.getText().toString());
                outState.putString("nearby",nearby.getText().toString());

                switch_gyro.setChecked(false);
                switch_acc.setChecked(false);
                switch_temp.setChecked(false);
                switch_light.setChecked(false);
                switch_proxi.setChecked(false);
                switch_orien.setChecked(false);
                switch_gps.setChecked(false);
            }
        });


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch_gyro.setChecked(savedInstanceState.getBoolean("gyro"));
                switch_acc.setChecked(savedInstanceState.getBoolean("acc"));
                switch_temp.setChecked(savedInstanceState.getBoolean("temp"));
                switch_light.setChecked(savedInstanceState.getBoolean("light"));
                switch_proxi.setChecked(savedInstanceState.getBoolean("proxi"));
                switch_orien.setChecked(savedInstanceState.getBoolean("orien"));
                switch_gps.setChecked(savedInstanceState.getBoolean("gps"));
                activatedproxy= savedInstanceState.getBoolean("actiproxy");
                activatedorien= savedInstanceState.getBoolean("actiorien");
                val_gps_name.setText(savedInstanceState.getString("name"));
                val_gps_address.setText(savedInstanceState.getString("address"));
                nearby.setText(savedInstanceState.getString("nearby"));
                int x=savedInstanceState.getInt("scroll_x");
                int y=savedInstanceState.getInt("scroll_y");
                scroller.scrollTo(x,y);

            }
        });

    }

    public double getdistance(Model_GPS model, double longi, double lati){
        double mlong= model.getLongitude();
        double mlati= model.getLatitude();
        double distance=Math.sqrt(Math.pow((longi-mlong),2)+Math.pow((lati-mlati),2));
        return distance;
    }


}


// references: tutorial, class lectuers, MPCart:  https://medium.com/@shehanatuk/how-to-use-mpandroidchart-in-android-studio-c01a8150720f also https://www.youtube.com/watch?v=N-_X6G1KgAY
//
