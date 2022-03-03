package com.mc2022.template.Fragment;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mc2022.template.Broadcast.MyReceiver_Broadcast;
import com.mc2022.template.News;
import com.mc2022.template.R;
import com.mc2022.template.newsclass;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class MainFragment extends Fragment {


    Bundle b;
    Bitmap bitmap=null;
    EditText comment=null;
    RatingBar rate;
    TextView body;
    ImageView image;
    TextView title;
    int postiton;
    newsclass nclass= newsclass.getNews(getContext());

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
    }


    @Override
    public void onResume() {
        super.onResume();
//        getActivity().registerReceiver(allBroadcastReceiver, filedownload);
    }

    @Override
    public void onPause() {
        super.onPause();
        News n =nclass.list.get(postiton);
        String new_comment = comment.getText().toString();
        Float new_rating = rate.getRating();
        n.setComment(new_comment);
        n.setRating(new_rating);
        nclass.savefile(n,getActivity(),n.getId(),postiton);
        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

        getActivity().getFragmentManager().popBackStack();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_main, container, false);
        b = this.getArguments();
        title = v.findViewById(R.id.title);
        body = v.findViewById(R.id.Body);
        image = v.findViewById(R.id.imageView);
        comment = v.findViewById(R.id.comment);
        rate =v.findViewById(R.id.rate);
        postiton= Integer.valueOf(b.getString("position"));

        downloadimage d = new downloadimage();
        d.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,b.getString("image"));
        title.setText(b.getString("title"));
        body.setText(b.getString("body"));
        comment.setText(b.getString("comment"));
        rate.setRating(b.getFloat("rating"));
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class downloadimage extends AsyncTask<String,Void,Void> {


        @Override
        protected Void doInBackground(String... voids) {
            String s = voids[0];
            Log.i(TAG, "doInBackground: Image loading start");
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(s).getContent());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }


}