package com.mc2022.template;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mc2022.template.Broadcast.MyReceiver_Broadcast;
import com.mc2022.template.Fragment.MainFragment;
import com.mc2022.template.Service.downloadservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class recyclerview_fragment extends Fragment {
    ArrayList<News> newsList= new ArrayList<>();
    private RecyclerView recycler_view;
    private RecyclerView.LayoutManager layMan;
    private RecyclerView.Adapter adapter;
    private newsclass newsClass;
    String TAG ="mainactivity";

    public static recyclerview_fragment instance;

    public recyclerview_fragment() {
        // Required empty public constructor
    }

    public static recyclerview_fragment getInstance() {
        return instance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_recyclerview_fragment, container, false);
        recycler_view = v.findViewById(R.id.recycler);
        newsClass = newsclass.getNews(getContext());
        newsList=newsClass.list;
        if(adapter==null){
        adapter=new NewsAdapterList(newsList);}
        recycler_view.setAdapter(adapter);
        layMan=new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(layMan);
        System.out.println("oncreate recyclerview displayed");
        return v;
    }

    public void doit(int num){
        System.out.println("news created");
        newsList= newsClass.loadfile(num,getContext());
        Log.i("size",""+newsList.size());
        adapter.notifyDataSetChanged();
    }

}