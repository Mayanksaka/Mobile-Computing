package com.mc2022.template;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mc2022.template.Database.SensorsDatabase;

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
    private TextView val_mag_x,val_mag_y,val_mag_z,ll,gg,nll,ngg;
    private Switch switch_acc, switch_mag;
    private Sensor sensor_accelerometer, sensor_magnetic_field;
    private RadioButton button;
    private RadioGroup query;
    private Double stride_val=1.0;
    private double old_value=0;
    private int step=0;
    private final LatLng defaultLocation = new LatLng(28.54829223068449, 77.27431552071639);
    private static final int DEFAULT_ZOOM = 120;
    private LatLng prevLocation = defaultLocation;
    private Button setlocation, getlocation;



    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    boolean islastacceleromercopied=false;
    boolean islastmagnetometercopied= false;

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
//        Places.initialize(getApplicationContext(), getString(R.string.maps_api_key));
//        placesClient = Places.createClient(this);
//
//        // Construct a FusedLocationProviderClient.
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        ll=findViewById(R.id.lati);
//        gg= findViewById(R.id.logi);
//        nll=findViewById(R.id.nlati);
//        ngg= findViewById(R.id.nlogi);
        fetchedlocation=findViewById(R.id.label_mylocation);
        userlocation= findViewById(R.id.lonlagname);
        getlocation = findViewById(R.id.getlocation);
        setlocation= findViewById(R.id.savelocation);

        query=findViewById(R.id.radiogroup);
        height= findViewById(R.id.value_height);
        stepcount= findViewById(R.id.stepcount);
        distanctravel= findViewById(R.id.distance_travel);
        switch_acc = findViewById(R.id.switch_accelerometer);
        orient= findViewById(R.id.direction);

//        scroller= findViewById(R.id.scrollview);
        smanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorsdb= SensorsDatabase.getInstance(getApplicationContext());
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
                } else {
                    smanager.unregisterListener(MainActivity.this, smanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
                    smanager.unregisterListener(MainActivity.this, smanager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));

//                    Toast.makeText(MainActivity.this, "Accelerometer stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        setlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userlocation.getText().toString().trim()==null){

                }
            }
        });

            }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sevent = sensorEvent.sensor;
        if (sevent.getType() == Sensor.TYPE_ACCELEROMETER){
            System.arraycopy(sensorEvent.values, 0, accelerometerReading, 0, sensorEvent.values.length);
            islastacceleromercopied=true;
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
                ll.setText(String.valueOf((double) prevLocation.latitude));
                gg.setText(String.valueOf((double) prevLocation.longitude));
                step++;
                double R = (double) 6378.1;
                double d = stride_length/100000;
                double current_direction= (float) Math.toRadians(currentdegree);
                double oldlati= (double) Math.toRadians(prevLocation.latitude);
                double oldlong= (double) Math.toRadians(prevLocation.longitude);
                double newlati= (double) (Math.asin(Math.sin(oldlati)*Math.cos(d/R)+Math.cos(oldlati)*Math.sin(d/R)*Math.cos(current_direction)));
                double newlong= (double)  (oldlong+Math.atan2(Math.sin(current_direction)*Math.sin(d/R)*Math.cos(oldlati), Math.cos(d/R)-Math.sin(oldlati)*Math.sin(newlati)));
                LatLng newlocation = new LatLng(Math.toDegrees(newlati),Math.toDegrees(newlong));
                nll.setText(String.valueOf((double) newlocation.latitude));
                ngg.setText(String.valueOf((double) newlocation.longitude));
                Polyline line = map.addPolyline(new PolylineOptions()
                        .add(prevLocation, newlocation)
                        .width(5)
                        .color(Color.BLUE));
                prevLocation=newlocation;
                Log.i(TAG, "step: "+step);
                stepcount.setText("Step Count: "+ Integer.valueOf(step));
                distanctravel.setText("Distance Travel: "+distance);
                }



        }
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

            System.arraycopy(sensorEvent.values, 0, magnetometerReading,0, sensorEvent.values.length);
            islastmagnetometercopied=true;

    }
        if(islastacceleromercopied && islastmagnetometercopied && System.currentTimeMillis()-lastupdatedtime>250){
            SensorManager.getRotationMatrix(rotationMatrix, null,
                    accelerometerReading, magnetometerReading);

            // "rotationMatrix" now has up-to-date information.

            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            float azimuthInradian= orientationAngles[0];
            float azimuthIndegree= (float) Math.toDegrees(azimuthInradian);
            currentdegree= azimuthIndegree;
            lastupdatedtime=System.currentTimeMillis();

            int x = (int) azimuthIndegree;
            orient.setText("Orientation: "+x+"°");

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


    }
    public void updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        // "rotationMatrix" now has up-to-date information.

        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        // "orientationAngles" now has up-to-date information.
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

        // [START_EXCLUDE]
        // [START map_current_place_set_info_window_adapter]
        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.
        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {


                return null;
            }
        });
        // [END map_current_place_set_info_window_adapter]

        // Prompt the user for permission.
        getLocationPermission();
        // [END_EXCLUDE]

        // Turn on the My Location layer and the related control on the map.
        map.moveCamera(CameraUpdateFactory
                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
        map.getUiSettings().setMyLocationButtonEnabled(false);
    }


}