package com.mc2022.template;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    String newstitle=null;
    String bodyy=null;
    String imageurl=null;
    Bitmap bitmap=null;
    TextView body;
    ImageView image;
    TextView title;
    MyReceiver allBroadcastReceiver = new MyReceiver();
    IntentFilter filedownload= new IntentFilter("FILE_DOWNLOADED");
    ErrorHandler errorBroadcast=new ErrorHandler();


    int num;
    File f;
    private final String TAG ="Main_Fragment";
    public static MainFragment instance;

    public static MainFragment getInstance() {
        return instance;
    }




    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
//        getActivity().registerReceiver(allBroadcastReceiver, filedownload);


    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(allBroadcastReceiver, filedownload);


    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(allBroadcastReceiver);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_main, container, false);
        title = v.findViewById(R.id.title);
        body = v.findViewById(R.id.Body);
        image = v.findViewById(R.id.imageView);

        f = new File(getActivity().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/recentnews.txt");
        try {

            if(f.exists()==false){
                title.setText("Welcome, You have choosen one of the Best News Application");
                body.setText("Please click the start button to start the service. Your news will be updated every 10sec. Please make sure internet is working. you can stop news service anytime by clicking stop button.\n Creater: Jaspreet Saka \n Roll_No.: 2018237 \n Branch: CSAM");

            }
            else{
                BufferedReader br = new BufferedReader(new FileReader(f));
                String s = br.readLine();
                br.close();
                System.out.println("newsnum: "+ s);
                runn(Integer.valueOf(s)-1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public void runn(int num)
        {
            Log.i(TAG, "Broadcast Recieved");
            File file;
            try {
                System.out.println(num);
                String wordsline;
                String path=getActivity().getApplicationContext().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/news"+num+".json";
                file = new File(path);
                StringBuilder content = new StringBuilder();
                BufferedReader buff = new BufferedReader(new FileReader(file));
                while ((wordsline = buff.readLine()) != null) {
                    content.append(wordsline);
                    content.append('\n');
                }
                buff.close();
                JSONObject jsonObj = new JSONObject(content.toString());
                newstitle = jsonObj.getString("title");
                bodyy = jsonObj.getString("body");
                imageurl=jsonObj.getString("image-url");
                Log.i(TAG, "PreExecute: jsonobject done");

            }
            catch (IOException | JSONException e) {
                //You'll need to add proper error handling here
                Log.e("TAG", "image download fail in background: " );
            }
            Log.i(TAG, "Title: "+newstitle);
            Log.i(TAG, "News: "+num);
            Log.i(TAG, "imageurl: "+imageurl);
            Log.i(TAG, "onPreExecute: Complete");


            downloadimage d = new downloadimage();

            d.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,imageurl);

            }


    private class downloadimage extends AsyncTask<String,Void,Void> {


        @Override
        protected Void doInBackground(String... voids) {
            String s = voids[0];
            Log.i(TAG, "doInBackground: Image loading start");
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(s).getContent());

            } catch (MalformedURLException e) {
                System.out.println("++++++++++++++++++++++++");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("))))))))))))))))))))))))");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAG", "PreExecute: start");

        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Log.i(TAG, "Data fetched");
            image.setImageBitmap(bitmap);
            title.setText(newstitle);
            body.setText(bodyy);
//            Toast.makeText(getActivity().getApplicationContext(),"News Updated",Toast.LENGTH_SHORT);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }


}