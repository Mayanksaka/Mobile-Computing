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
    MyReceiver_Broadcast BroadcastReceiver=new MyReceiver_Broadcast();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private newsclass newsClass;
    String download_TAG ="mainactivity";


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
        Context mContext = getActivity().getApplicationContext();

        instance=this;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(BroadcastReceiver,new IntentFilter("UPDATE_RECYCLERVIEW"));

    }

    @Override
    public void onPause() {
        super.onPause();
//        getActivity().unregisterReceiver(BroadcastReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_recyclerview_fragment, container, false);
        recyclerView = v.findViewById(R.id.recycler);


        newsClass = newsclass.getNews(getContext());
//        newsList= newsClass.loadfile(0,getContext());
        newsList=newsClass.list;
        layoutManager=new LinearLayoutManager(getActivity());
        if(adapter==null){
            adapter=new NewsAdapterList(newsList);
        }else{
            adapter.notifyDataSetChanged();
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
//        recyclerView.setAdapter(newsadapter);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        System.out.println("oncreate recyclerview displayed");
        return v;
    }

//    public void updatelist(){
//        getActivity().runOnUiThread(new Runnable() {
//            public void run() {
//                adapter.notifyDataSetChanged();
////                adapter.notifyItemChanged(newsList.size()-1);
//                System.out.println("adapter updated");
//            }
//        });
//    }

    public void doit(int num){

        System.out.println("news created");
        newsList= newsClass.loadfile(num,getContext());
        Log.i("size",""+newsList.size());
        adapter.notifyDataSetChanged();

//        adapter.notifyItemChanged(newsList.size()-1);
//        updatelist();
    }


}