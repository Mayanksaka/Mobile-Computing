package com.mc2022.template;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mc2022.template.Database.SensorsDatabase;
import com.mc2022.template.Models.Model_GPS;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener , OnMapReadyCallback {
    private GoogleMap map;

    private String TAG= "MainActivity";
    private SensorManager smanager;
    private SensorsDatabase sensorsdb;
    private ScrollView scroller;
    private TextView stepcount,distanctravel,orient,fetchedlocation;
    private RadioGroup radiogroup;
    private RadioButton selectedbutton;
    private EditText height, userlocation;
    private Switch switch_acc, switch_mag;
    private Sensor sensor_accelerometer, sensor_magnetic_field;
    private RadioGroup query;
    private Double stride_val=1.0;
    private double old_value=0;
    private int step=0;
    private final LatLng startinglocation = new LatLng(28.5482922306844899, 77.2743155207163899);
    private static final int DEFAULT_ZOOM = 120;
    private LatLng oldLocation = startinglocation;
    private Button setlocation, getlocation,wardrive;



    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    boolean islastacc_copied =false;
    boolean islastmagn_copied = false;

    long lastupdatedtime=0;
    float currentdegree=0f;

    private boolean locationPermissionGranted;


    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        fetchedlocation=findViewById(R.id.label_mylocation);
        userlocation= findViewById(R.id.lonlagname);
        getlocation = findViewById(R.id.getlocation);
        setlocation= findViewById(R.id.savelocation);
        wardrive=findViewById(R.id.button_wardrive);
        query=findViewById(R.id.radiogroup);
        height= findViewById(R.id.value_height);
        stepcount= findViewById(R.id.stepcount);
        distanctravel= findViewById(R.id.distance_travel);
        switch_acc = findViewById(R.id.switch_accelerometer);
        orient= findViewById(R.id.direction);

        smanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorsdb= SensorsDatabase.getInstance(getApplicationContext());
        getlocation.setClickable(false);
        setlocation.setClickable(false);
        switch_acc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    sensor_magnetic_field = smanager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                    sensor_accelerometer = smanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    int selectedid =query.getCheckedRadioButtonId();
                    if(selectedid==-1)
                    {
                        Toast.makeText(MainActivity.this, "Please select Gender", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        if(selectedid==1){
                            stride_val=0.405;}
                        if(selectedid==2){
                            stride_val=0.403;
                    }
                    }

                    if (sensor_accelerometer != null) {
                        smanager.registerListener(MainActivity.this, sensor_magnetic_field, SensorManager.SENSOR_DELAY_NORMAL);
                        smanager.registerListener(MainActivity.this, sensor_accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//                        Toast.makeText(MainActivity.this, "Accelerometer sensor started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Accelerometer Not supported", Toast.LENGTH_SHORT).show();
                        Log.i(TAG,"Accelerometer Not supported");
                    }
                    getlocation.setClickable(true);
                    setlocation.setClickable(true);
                } else {
                    smanager.unregisterListener(MainActivity.this, smanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
                    smanager.unregisterListener(MainActivity.this, smanager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
                    getlocation.setClickable(false);
                    setlocation.setClickable(false);
                }
            }
        });

        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Model_GPS> data=sensorsdb.Daogps().isempty()? null: sensorsdb.Daogps().getList();
                if(data==null){
                    Toast.makeText(getApplicationContext(),"No data in database",Toast.LENGTH_SHORT).show();
                    return;}
                int smallindex=0;
                double smallestdistance= 9999;
                for(int i=0;i <data.size(); i++){
                    double difference= Math.sqrt(Math.pow(oldLocation.longitude-data.get(i).getLongitude(),2)+Math.pow(oldLocation.latitude-data.get(i).getLatitude(),2));
                    if(difference<smallestdistance){
                        smallestdistance=difference;
                        smallindex=i;
                    }
                }
                Toast.makeText(getApplicationContext(),"You are near: "+ data.get(smallindex).getName(),Toast.LENGTH_SHORT).show();
                fetchedlocation.setText(data.get(smallindex).getName());
            }
        });

        setlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userlocation.getText().toString().trim()==null){
                    Toast.makeText(MainActivity.this, "Please Enter Location", Toast.LENGTH_SHORT).show();
                    return;
                }
                int count=0;
                int latestid=sensorsdb.Daoacc().getlastid();
                if(!sensorsdb.Daoacc().isempty()){
                    count=latestid;
                    count++;
                }
                long time = System.currentTimeMillis();
                sensorsdb.Daogps().insert(new Model_GPS(count,time, oldLocation.longitude, oldLocation.latitude,userlocation.getText().toString(),""));
                Toast.makeText(MainActivity.this, "Location Saved", Toast.LENGTH_SHORT).show();

            }
        });

        wardrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),war_drive.class);
                startActivity(i);
            }
        });



            }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sevent = sensorEvent.sensor;
        if (sevent.getType() == Sensor.TYPE_ACCELEROMETER){
            System.arraycopy(sensorEvent.values, 0, accelerometerReading, 0, sensorEvent.values.length);
            islastacc_copied =true;
//            int count=0;
//            int latestid=sensorsdb.Daoacc().getlastid();
//            if(!sensorsdb.Daoacc().isempty()){
//                count=latestid;
//                count++;
//            }
//            long time = System.currentTimeMillis();

//            sensorsdb.Daoacc().insert(new Model_LinearAcceleration(count,time,sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]));
//            val_acc_x.setText("X: "+ Float.valueOf(sensorEvent.values[0]));
//            val_acc_y.setText("Y: "+ Float.valueOf(sensorEvent.values[1]));
//            val_acc_z.setText("Z: "+ Float.valueOf(sensorEvent.values[2]));
//            if(latestid>9){
//                sensorsdb.Daoacc().deleteitem(latestid-9);
//            }
            double x=sensorEvent.values[0];
            double y= sensorEvent.values[1];
            double z=sensorEvent.values[2];
            double stride_length = (double) Float.parseFloat(height.getText().toString())*stride_val;
            double value=Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
//            Log.i(TAG, "value: "+value);
//            Log.i(TAG, "Stride length: "+ stride_length);
            double difference = value- old_value;
            old_value = value;
            int distance= Integer.valueOf((int) Math.round(step*stride_length));
            if(difference>4){

                step++;
                double R = (double) 6378.1;
                double d = stride_length/100000;
                double current_direction= (float) Math.toRadians(currentdegree);
                double oldlati= (double) Math.toRadians(oldLocation.latitude);
                double oldlong= (double) Math.toRadians(oldLocation.longitude);
                double newlati= (double) (Math.asin(Math.sin(oldlati)*Math.cos(d/R)+Math.cos(oldlati)*Math.sin(d/R)*Math.cos(current_direction)));
                double newlong= (double)  (oldlong+Math.atan2(Math.sin(current_direction)*Math.sin(d/R)*Math.cos(oldlati), Math.cos(d/R)-Math.sin(oldlati)*Math.sin(newlati)));
                LatLng newlocation = new LatLng(Math.toDegrees(newlati),Math.toDegrees(newlong));

                Polyline line = map.addPolyline(new PolylineOptions()
                        .add(oldLocation, newlocation)
                        .width(5)
                        .color(Color.BLUE));
                oldLocation =newlocation;
                Log.i(TAG, "step: "+step);
                stepcount.setText("Step Count: "+ Integer.valueOf(step));
                distanctravel.setText("Distance: "+distance);
                }



        }
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

            System.arraycopy(sensorEvent.values, 0, magnetometerReading,0, sensorEvent.values.length);
            islastmagn_copied =true;

    }
        if(islastacc_copied && islastmagn_copied && System.currentTimeMillis()-lastupdatedtime>250){
            SensorManager.getRotationMatrix(rotationMatrix, null,
                    accelerometerReading, magnetometerReading);
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            float azimuthInradian= orientationAngles[0];
            float azimuthIndegree= (float) Math.toDegrees(azimuthInradian);
            currentdegree= azimuthIndegree;
            lastupdatedtime=System.currentTimeMillis();
            int x = (int) -azimuthIndegree;
            orient.setText("Direction: "+x+"°");

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.map = googleMap;

//        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            @Override
//            // Return null here, so that getInfoContents() is called next.
//            public View getInfoWindow(Marker arg0) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//
//                return null;
//            }
//        });
        // [END map_current_place_set_info_window_adapter]

        // Prompt the user for permission.
        getLocationPermission();

        map.moveCamera(CameraUpdateFactory
                .newLatLngZoom(startinglocation, DEFAULT_ZOOM));
        map.getUiSettings().setMyLocationButtonEnabled(false);
    }


}

//References
//https://developer.android.com/reference/android/net/wifi/WifiInfo#getRssi()
//https://developer.android.com/reference/android/net/wifi/ScanResult
//        https://developer.android.com/guide/topics/connectivity/wifi-scan
//        https://www.youtube.com/watch?v=o-qpVefrfVA&t=1202s
//        https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial