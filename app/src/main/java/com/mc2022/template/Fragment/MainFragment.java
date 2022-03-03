package com.mc2022.template.Fragment;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mc2022.template.Broadcast.MyReceiver_Broadcast;
import com.mc2022.template.News;
import com.mc2022.template.R;
import com.mc2022.template.newsclass;

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


public class MainFragment extends Fragment {


    // TODO: Rename and change types of parameters
    String newstitle=null;
    Bundle b;
    String bodyy=null;
    String imageurl=null;
    Bitmap bitmap=null;
    EditText comment=null;
    RatingBar rate;
    TextView body;
    ImageView image;
    TextView title;
    Button save;
    int postiton;
    newsclass nclass= newsclass.getNews(getContext());
    MyReceiver_Broadcast allBroadcastReceiver = new MyReceiver_Broadcast();
    IntentFilter filedownload= new IntentFilter("FILE_DOWNLOADED");


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
        b = this.getArguments();
        title = v.findViewById(R.id.title);
        body = v.findViewById(R.id.Body);
        image = v.findViewById(R.id.imageView);
        comment = v.findViewById(R.id.comment);
        rate =v.findViewById(R.id.rate);
        save= v.findViewById(R.id.save);
//        System.out.println(b.getString("position"));
        postiton= Integer.valueOf(b.getString("position"));
        System.out.println("positon"+postiton);
        News n =nclass.list.get(postiton);
        System.out.println(b.getString("title"));
        System.out.println(n.getTitle());
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String new_comment = comment.getText().toString();
                Float new_rating = rate.getRating();
                n.setComment(new_comment);
                n.setRating(new_rating);
                nclass.savefile(n,getActivity(),postiton);
                getActivity().getFragmentManager().popBackStack();
            }
        });

        downloadimage d = new downloadimage();
        d.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,b.getString("image"));


//        f = new File(getActivity().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/recentnews.txt");
//        try {
//
//            if(f.exists()==false){
//                title.setText("Welcome, You have choosen one of the Best News Application");
//                body.setText("Please click the start button to start the service. Your news will be updated every 10sec. Please make sure internet is working. you can stop news service anytime by clicking stop button.\n Creater: Jaspreet Saka \n Roll_No.: 2018237 \n Branch: CSAM");
//
//            }
//            else{
//                BufferedReader br = new BufferedReader(new FileReader(f));
//                String s = br.readLine();
//                br.close();
//                System.out.println("newsnum: "+ s);
//                runn(Integer.valueOf(s)-1);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public int getcurrentnum(){
        int n=0;
        File f =new File(getActivity().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/recentnews.txt");
        try {

            if(f.exists()==false){
//                title.setText("Welcome, You have choosen one of the Best News Application");
//                body.setText("Please click the start button to start the service. Your news will be updated every 10sec. Please make sure internet is working. you can stop news service anytime by clicking stop button.\n Creater: Jaspreet Saka \n Roll_No.: 2018237 \n Branch: CSAM");
                n=0;
                System.out.println("file not exist");
            }
            else{
                BufferedReader br = new BufferedReader(new FileReader(f));
                String s = br.readLine();
                br.close();
                System.out.println("newsnum: "+ s);
//                runn(Integer.valueOf(s)-1);
                n=Integer.valueOf(s);
                System.out.println("file exist");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return n;
    }

    public void runn(int num) {
//            File file;
//            try {
//                System.out.println(num);
//                String wordsline;
//                String path=getActivity().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/news"+num+".json";
//                file = new File(path);
//                StringBuilder content = new StringBuilder();
//                BufferedReader buff = new BufferedReader(new FileReader(file));
//                while ((wordsline = buff.readLine()) != null) {
//                    content.append(wordsline);
//                    content.append('\n');
//                }
//                buff.close();
//                JSONObject jsonObj = new JSONObject(content.toString());
//                newstitle = jsonObj.getString("title");
//                bodyy = jsonObj.getString("body");
//                imageurl=jsonObj.getString("image-url");
//                Log.i(TAG, "PreExecute: jsonobject done");
//
//            }
//            catch (IOException | JSONException e) {
//                //You'll need to add proper error handling here
//                Log.e("TAG", "image download fail in background: " );
//            }
//            Log.i(TAG, "Title: "+newstitle);
//            Log.i(TAG, "News: "+num);
//            Log.i(TAG, "imageurl: "+imageurl);
//            Log.i(TAG, "onPreExecute: Complete");


//            downloadimage d = new downloadimage();
//            d.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,imageurl);
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
            title.setText(b.getString("title"));
            body.setText(b.getString("body"));
            comment.setText(b.getString("comment"));
            rate.setRating(b.getFloat("rating"));
            image.setImageBitmap(bitmap);
//            Toast.makeText(getActivity().getApplicationContext(),"News Updated",Toast.LENGTH_SHORT);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }


}