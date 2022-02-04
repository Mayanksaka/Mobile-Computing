package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    private static final String DEBUG_TAG= "Main_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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