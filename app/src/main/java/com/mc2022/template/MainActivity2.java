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

    private TextView wel;
    private TextView selectedsymptons;
    private TextView suggestion;
    private Button restart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
        Log.d(DEBUG_TAG, "ON_START");
        Toast.makeText(this,"ON_START", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(DEBUG_TAG, "ON_PAUSE");
        Toast.makeText(this,"ON_PAUSE", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(DEBUG_TAG, "ON_RESUME");
        Toast.makeText(this,"ON_RESUME", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(DEBUG_TAG, "ON_RESTART");
        Toast.makeText(this,"ON_RESTART", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(DEBUG_TAG, "ON_STOP");
        Toast.makeText(this,"ON_STOP", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(DEBUG_TAG, "ON_DESTROY");
        Toast.makeText(this,"ON_DESTROY", Toast.LENGTH_SHORT).show();
    }
}