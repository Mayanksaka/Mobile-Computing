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

import com.mc2022.template.Controller.Controller;


public class MainActivity extends AppCompatActivity {

    String PREVS="LAUNCHED";
    private static final String DEBUG_TAG= "Activity_1";
    private View v;
    private RadioButton button;
    private RadioGroup query;
    private Button nextb;
    private Button clearb;
    private Button submitb;
    private TextView questiont;
    private EditText namee;

    Controller c= new Controller();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createLogToast("CREATED");

        nextb = findViewById(R.id.nextbutton);
        query=findViewById(R.id.radiogroup);
        clearb = findViewById(R.id.clearbutton);
        submitb = findViewById(R.id.submitbutton);
        questiont= findViewById(R.id.question);
        namee= findViewById(R.id.name);


        questiont.setText("Do you have "+c.sympton()+" ?");




        clearb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                v = findViewById(R.id.nextbutton);
                v.setVisibility(View.VISIBLE);
                v =findViewById(R.id.submitbutton);
                v.setVisibility(View.INVISIBLE);

                c.setquestions(-1);
                questiont.setText("Do you have "+c.sympton()+" ?");

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


                c.selectedanswer(answer);
                questiont.setText("Do you have "+c.sympton()+" ?");

                query.clearCheck();
                if(c.islastquestion()){
                    v = findViewById(R.id.nextbutton);
                    v.setVisibility(View.GONE);
                    v =findViewById(R.id.submitbutton);
                    v.setVisibility(View.VISIBLE);
                }
            }
        });


        submitb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam=namee.getText().toString().trim();
                if(naam.equals("")){
                    namee.setText(null);
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
                c.selectedanswer(answer);

                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                intent.putExtra("naam",naam);
                intent.putExtra("sympton_name",c.selected_value());
                intent.putExtra("result",c.istestneeded());
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
        Toast.makeText(this,"A1: "+PREVS+" -> "+currentstate, Toast.LENGTH_SHORT).show();
        PREVS=currentstate;
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("indexvalue",c.getindex()-1);
        outState.putCharSequence("naam",namee.getText());
        outState.putInt("radiobuttonid",query.getCheckedRadioButtonId());
        for(int i=0; i<=c.getindex();i++){
            outState.putBoolean(String.valueOf(i),c.getselectedasnwer(c.sympton(i)));
        }

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        c.setquestions(savedInstanceState.getInt("indexvalue"));
        questiont.setText("Do you have "+c.sympton()+" ?");
        namee.setText(savedInstanceState.getCharSequence("naam"));
        int value= savedInstanceState.getInt("radiobuttonid");
        if(value!=-1)
            query.check(value);
        for(int i=0;i<=c.getindex();i++){
            c.setanswer(i,savedInstanceState.getBoolean(String.valueOf(i)));
        }
        if(c.islastquestion()){
            v = findViewById(R.id.nextbutton);
            v.setVisibility(View.GONE);
            v =findViewById(R.id.submitbutton);
            v.setVisibility(View.VISIBLE);
        }

    }
}