package com.mc2022.template;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.mc2022.template.Fragment.RecentnewsFragment;

public class Recentfive extends AppCompatActivity {
        public static String TAG="Activity";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recentfive);

            FragmentManager fragment = getSupportFragmentManager();
            FragmentTransaction fragtrans = fragment.beginTransaction();
            fragtrans.replace(R.id.frame, new RecentnewsFragment()).setReorderingAllowed(true).commit();
            Log.i(TAG, "onCreate: sucess");
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            Log.i("TAG", "onDestroy: Closed TopRecent");
        }
    }

