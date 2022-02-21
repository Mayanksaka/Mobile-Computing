package com.mc2022.template.Model;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mc2022.template.Fragment.Buttonfragment;
import com.mc2022.template.Broadcast.MessageRecieverBroadcast;
import com.mc2022.template.Broadcast.MyReceiver_Broadcast;
import com.mc2022.template.Fragment.MainFragment;
import com.mc2022.template.R;

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


public class Model_activity_2 extends Fragment {

    String newstitle=null;
    String bodyy=null;
    String imageurl=null;
    Bitmap bitmap=null;
    TextView body;
    ImageView image;
    TextView title;
    static String TAG ="news";


    int num;
    File f;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_main, container, false);
        title = v.findViewById(R.id.title);
        body = v.findViewById(R.id.Body);
        image = v.findViewById(R.id.imageView);

        f = new File(getActivity().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/recentnews.txt");
        try {


            BufferedReader br = new BufferedReader(new FileReader(f));
            String s = br.readLine();
            br.close();
            System.out.println("newsnum: "+ s);
            runn(Integer.valueOf(s)-1);

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
        File file;
        try {
            System.out.println(num);
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


        downlo d = new downlo();

        d.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,imageurl);
    }

    public String gettitle(){
        return title.getText().toString();
    }



    private class downlo extends AsyncTask<String,Void,Void> {


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