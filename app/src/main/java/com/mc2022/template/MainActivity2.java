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
    private static final String DEBUG_TAG= "Main_activity_2";
    String PREVS="LAUNCHED";
    private TextView wel;
    private TextView selectedsymptons;
    private TextView suggestion;
    private Button restart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.i(DEBUG_TAG , "State of activity Activity_2 changed from "+PREVS+" to CREATED");
        Toast.makeText(this,"A2: "+PREVS+" -> CREATED", Toast.LENGTH_SHORT).show();
        PREVS="CREATED";
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

    protected void onStart() {
        super.onStart();
        Log.i(DEBUG_TAG , "State of activity Activity_2 changed from "+PREVS+" to STARTED");
        Toast.makeText(this,"A2: "+PREVS+" -> STARTED", Toast.LENGTH_SHORT).show();
        PREVS="STARTED";
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(DEBUG_TAG , "State of activity Activity_2 changed from "+PREVS+" to PAUSED");
        Toast.makeText(this,"A2: "+PREVS+" -> PAUSED", Toast.LENGTH_SHORT).show();
        PREVS="PAUSED";
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(DEBUG_TAG , "State of activity Activity_2 changed from "+PREVS+" to RESUMED");
        Toast.makeText(this,"A2: "+PREVS+" -> RESUMED", Toast.LENGTH_SHORT).show();
        PREVS="RESUMED";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(DEBUG_TAG , "State of activity Activity_2 changed from "+PREVS+" to RESTARTED");
        Toast.makeText(this,"A2: "+PREVS+" -> RESTARTED", Toast.LENGTH_SHORT).show();
        PREVS="RESTARTED";
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(DEBUG_TAG , "State of activity Activity_2 changed from "+PREVS+" to STOPPED");
        Toast.makeText(this,"A2: "+PREVS+" -> STOPPED", Toast.LENGTH_SHORT).show();
        PREVS="STOPPED";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(DEBUG_TAG , "State of activity Activity_2 changed from "+PREVS+" to DESTROYED");
        Toast.makeText(this,"A2: "+PREVS+" -> DESTROYED", Toast.LENGTH_SHORT).show();
        PREVS="DESTROYED";
    }
}