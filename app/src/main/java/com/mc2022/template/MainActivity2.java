package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private static final String DEBUG_TAG= "Activity_2";
    String PREVS="LAUNCHED";
    private TextView wel;
    private TextView selectedsymptons;
    private TextView suggestion;
    private Button restart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        createLogToast("CREATED");
        Intent intent = getIntent();
        String name = intent.getStringExtra("naam");
        String sympton_name= intent.getStringExtra("sympton_name");
        Boolean result= intent.getBooleanExtra("result",true);

        wel =findViewById(R.id.welcome);
        wel.setText("Hi, "+ name+" !");
        selectedsymptons=findViewById(R.id.showsymptons);
        System.out.println(sympton_name);
        selectedsymptons.setText("You have selected "+sympton_name+".");
        suggestion= findViewById(R.id.Suggestion);
        if(result==true)
            suggestion.setText("Go for RT-PCR test");
        else
            suggestion.setText("No need for RT-PCR test");

        restart=findViewById(R.id.restart);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        createLogToast("STARTED");
    }

    @Override
    protected void onPause() {
        super.onPause();
        createLogToast("PAUSED");
    }

    @Override
    protected void onResume() {
        super.onResume();
        createLogToast("RESUMED");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        createLogToast("RESTARTED");
    }

    @Override
    protected void onStop() {
        super.onStop();
        createLogToast("STOPPED");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        createLogToast("DESTROYED");
    }

    public void createLogToast(String currentstate){
        Log.i(DEBUG_TAG , "State of "+DEBUG_TAG+" changed from "+PREVS+" to "+currentstate);
        Toast.makeText(this,"A2: "+PREVS+" -> "+currentstate, Toast.LENGTH_SHORT).show();
        PREVS=currentstate;
    }
}