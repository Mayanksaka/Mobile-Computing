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

    RecyclerView recyclerView;
    NewsAdapterList newsadapter;
    String download_TAG ="mainactivity";
    public static recyclerview_fragment instance;

    public recyclerview_fragment() {
        // Required empty public constructor
    }

    public static recyclerview_fragment getInstance() {
        return instance;
    }


    // TODO: Rename and change types and number of parameters


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
        getActivity().unregisterReceiver(BroadcastReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_recyclerview_fragment, container, false);
        recyclerView = v.findViewById(R.id.recycler);
        Intent newsservice= new Intent(getContext(), downloadservice.class);
        getActivity().startService(newsservice);
//        updatelist();
        newsadapter = new NewsAdapterList(newsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setAdapter(newsadapter);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        System.out.println("concreate recyclerview displayed");
        return v;
    }

    public void updatelist(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                newsadapter.notifyDataSetChanged();
            }
        });
    }

    public void doit(int num){


        File file;
        try {
            String wordsline;
            String path=getActivity().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/news"+num+".json";
            file = new File(path);
            StringBuilder content = new StringBuilder();
            BufferedReader buff = new BufferedReader(new FileReader(file));
            while ((wordsline = buff.readLine()) != null) {
                content.append(wordsline);
                content.append('\n');
            }
            buff.close();
            JSONObject jsonObj = new JSONObject(content.toString());
            Log.i(download_TAG, "file saved");
            News n = new News();
            n.setTitle(jsonObj.getString("title"));
            n.setBody(jsonObj.getString("body"));
            n.setImage(jsonObj.getString("image-url"));
            newsList.add(n);
            updatelist();

        }
        catch (IOException | JSONException e) {
            //You'll need to add proper error handling here
            Log.e("TAG", "image download fail in background: " );
        }



    }


}