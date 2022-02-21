package com.mc2022.template;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecentnewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentnewsFragment extends Fragment {
    public static String TAG ="Recent_Fragment";
    Button prev;
    Button next;
    int s;
    int upper;
    int lower;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecentnewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecentnewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecentnewsFragment newInstance(String param1, String param2) {
        RecentnewsFragment fragment = new RecentnewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: Done");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: start");
        View v =inflater.inflate(R.layout.fragment_recentnews, container, false);
        Log.i(TAG, "onCreateView: View loaded");
        next = v.findViewById(R.id.button1);
        prev = v.findViewById(R.id.button2);
        File f = new File(getActivity().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/recentnews.txt");
        Log.i(TAG, "onCreateView: File code");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            s = Integer.valueOf(br.readLine())-1;
            upper=s-1;
            lower=s-4;
            br.close();
            Log.i(TAG, "onCreateView: recent_number_load");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        top fra = new top();
        FragmentManager fragment = getActivity().getSupportFragmentManager();
        FragmentTransaction fragtrans = fragment.beginTransaction();
        fragtrans.replace(R.id.fragmentContainerView, fra).setReorderingAllowed(true).commit();
        Log.i(TAG, "onCreateView: Fragment addedd");
//        fra.runn(s);
        System.out.println("Number: "+ s);
        Log.i(TAG, "onCreateView: Fragmentdisplayed");

        s--;
        prev.setVisibility(View.INVISIBLE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prev.setVisibility(View.VISIBLE);

                if(lower<=s){
                    System.out.println("Next: "+ s);
                    fra.runn(s);
                    s--;
                }
                if(lower>s){
                    s++;
                    next.setVisibility(View.INVISIBLE);
                }

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next.setVisibility(View.VISIBLE);

                if(s<=upper){
                    s++;
                    System.out.println("Prev: "+ s);
                    fra.runn(s);


                }
                if(upper<s){
                    s--;
                    prev.setVisibility(View.INVISIBLE);
                }
            }
        });

        return v;
    }


}