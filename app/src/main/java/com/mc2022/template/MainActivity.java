package com.mc2022.template;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    String PREVS="LAUNCHED";
    private static final String DEBUG_TAG= "Main_activity";
    private View v;
    private RadioButton button;
    private RadioGroup query;
    private Button nextb;
    private Button clearb;
    private Button submitb;
    private TextView questiont;
    private EditText namee;
    int index=0;
    Questions q = new Questions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(DEBUG_TAG , "State of activity Activity_1 changed from "+PREVS+" to CREATED");
        Toast.makeText(this,"A1: "+PREVS+" -> CREATED", Toast.LENGTH_SHORT).show();
        PREVS="CREATED";

        nextb = findViewById(R.id.nextbutton);
        query=findViewById(R.id.radiogroup);
        clearb = findViewById(R.id.clearbutton);
        submitb = findViewById(R.id.submitbutton);
        questiont= findViewById(R.id.question);
        namee= findViewById(R.id.name);
        String symp = q.getsympton(index);
        questiont.setText("Do you have "+symp+" ?");




        clearb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                v = findViewById(R.id.nextbutton);
                v.setVisibility(View.VISIBLE);
                v =findViewById(R.id.submitbutton);
                v.setVisibility(View.INVISIBLE);
                index=0;
                String symp = q.getsympton(index);
                questiont.setText("Do you have "+symp+" ?");

                namee.setText(null);
                query.clearCheck();
            }
        });

        nextb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedid =query.getCheckedRadioButtonId();
                if(selectedid==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select answer", Toast.LENGTH_SHORT).show();
                    return;
                }
                button= findViewById(selectedid);
                String answer= button.getText().toString();

                if(answer.equals("Yes")){
                    q.setvalue(index,true);}
                else
                    q.setvalue(index,false);
                index=(index + 1) % q.map.size();

                questiont.setText("Do you have "+q.getsympton(index)+" ?");
                query.clearCheck();
                if(index==q.map.size()-1){
                    v = findViewById(R.id.nextbutton);
                    v.setVisibility(View.GONE);
                    v =findViewById(R.id.submitbutton);
                    v.setVisibility(View.VISIBLE);}
            }

        });

        submitb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(namee.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                int selectedid =query.getCheckedRadioButtonId();
                if(selectedid==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select answer", Toast.LENGTH_SHORT).show();
                    return;
                }
                button= findViewById(selectedid);
                String answer= button.getText().toString();
                if(answer.equals("Yes")){
                    q.setvalue(index,true);}
                else
                    q.setvalue(index,false);
                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                intent.putExtra("naam",namee.getText().toString());
                intent.putExtra("sympton_name",q.selected_value());
                intent.putExtra("result",q.istestneeded());
                startActivity(intent);

//                Go back to same activity without creating new activity and stack

//                v = findViewById(R.id.nextbutton);
//                v.setVisibility(View.VISIBLE);
//                v =findViewById(R.id.submitbutton);
//                v.setVisibility(View.INVISIBLE);
//                index=0;
//                String symp = q.getsympton(index);
//                questiont.setText("Do you have "+symp+" ?");
//
//                namee.setText(null);
//                query.clearCheck();

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(DEBUG_TAG , "State of activity Activity_1 changed from "+PREVS+" to STARTED");
        Toast.makeText(this,"A1: "+PREVS+" -> STARTED", Toast.LENGTH_SHORT).show();
        PREVS="STARTED";
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(DEBUG_TAG , "State of activity Activity_1 changed from "+PREVS+" to PAUSED");
        Toast.makeText(this,"A1: "+PREVS+" -> PAUSED", Toast.LENGTH_SHORT).show();
        PREVS="PAUSED";
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(DEBUG_TAG , "State of activity Activity_1 changed from "+PREVS+" to RESUMED");
        Toast.makeText(this,"A1: "+PREVS+" -> RESUMED", Toast.LENGTH_SHORT).show();
        PREVS="RESUMED";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(DEBUG_TAG , "State of activity Activity_1 changed from "+PREVS+" to RESTARTED");
        Toast.makeText(this,"A1: "+PREVS+" -> RESTARTED", Toast.LENGTH_SHORT).show();
        PREVS="RESTARTED";
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(DEBUG_TAG , "State of activity Activity_1 changed from "+PREVS+" to STOPPED");
        Toast.makeText(this,"A1: "+PREVS+" -> STOPPED", Toast.LENGTH_SHORT).show();
        PREVS="STOPPED";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(DEBUG_TAG , "State of activity Activity_1 changed from "+PREVS+" to DESTROYED");
        Toast.makeText(this,"A1: "+PREVS+" -> DESTROYED", Toast.LENGTH_SHORT).show();
        PREVS="DESTROYED";
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("indexvalue",index);
        outState.putCharSequence("naam",namee.getText());
        outState.putInt("radiobuttonid",query.getCheckedRadioButtonId());
        for(int i=0; i<=index;i++){
            outState.putBoolean(String.valueOf(i),q.map.get(q.getsympton(i)));
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        index=savedInstanceState.getInt("indexvalue");
        String symp = q.getsympton(index);
        questiont.setText("Do you have "+symp+" ?");
        namee.setText(savedInstanceState.getCharSequence("naam"));
        int value= savedInstanceState.getInt("radiobuttonid");
        if(value!=-1)
            query.check(value);
        for(int i=0;i<=index;i++){
            q.map.put(q.getsympton(i),savedInstanceState.getBoolean(String.valueOf(i)));
        }
    }
}