package com.mc2022.template.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.mc2022.template.R;
import com.mc2022.template.newsclass;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class downloadservice extends Service {
    private static Timer Timetask ;
    @RequiresApi(api = Build.VERSION_CODES.O)
    newsclass newsClass;
    Integer num;
    String download_TAG="Downloadservice";
    Boolean b =false;
    Boolean Inetable;
    String TAG="Sercice";
    public downloadservice() {

    }

//    @Override
    public void onDestroy() {
        super.onDestroy();
        b=false;
        Log.i(download_TAG, "onDestroy: ");
        if (Timetask!=null){
            Timetask.cancel();
        }

    }

    @SuppressLint("NewApi")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        b=true;
        newsClass = newsclass.getNews(getApplicationContext());

        num = Integer.valueOf(intent.getExtras().getString("num"));
        Log.i(TAG, "onStartCommand: "+num);
        Timetask = new Timer();
        Timetask.scheduleAtFixedRate(new DownTimer(), 0, 10000);

        NotificationChannel notificationChannel = new NotificationChannel("News Service","News Service", NotificationManager.IMPORTANCE_DEFAULT);
        getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);

        Notification.Builder builder = new Notification.Builder(this, "News Service").
                setContentText("Service is running")
                .setContentTitle("News Service")
                .setSmallIcon(R.drawable.ic_launcher_background);
        startForeground(21010, builder.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    private class DownTimer extends TimerTask{

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            Newsservice s =new Newsservice();
            s.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,Integer.valueOf(num));
            num++;

        }
    }

    private class Newsservice extends AsyncTask<Integer, Integer, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            NetworkInfo Info = ((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if(Info != null && Info.isConnected())
                Inetable=true;
            else
                Inetable=false;
            if(Inetable){
                Log.i(download_TAG, "Internet available");
                sendBroadcast(new Intent("INTERNET_CONNECTIVITY").setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES).putExtra("msg", "Internet Available"));

            }
            else{
                sendBroadcast(new Intent("INTERNET_CONNECTIVITY").setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES).putExtra("msg", "Internet Not Available"));
                Log.i(download_TAG, "Internet not available");
            }
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Integer... value) {
            int val = value[0];
            String url = "https://petwear.in/mc2022/news/news_" + String.valueOf(val) + ".json";
            try {

                URL url2 = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                conn.setRequestMethod("GET");
                // read the response

                InputStream urldata = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(urldata));
                File downloadfile = new File(getApplicationContext().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/news"+val+".json");
                OutputStream output = new FileOutputStream(downloadfile);
                byte[] bytes = new byte[1024];
                while((urldata.read(bytes))!=-1){
                    output.write(bytes);
                }
                output.close();
                reader.close();
                urldata.close();
                conn.disconnect();
                Intent i = new Intent();
                i.setAction("UPDATE_RECYCLERVIEW");
                Log.i(download_TAG, "intentcreated ");
                i.putExtra("num",val);
                i.setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
                sendBroadcast(i);
                Log.i(download_TAG, "broadcast sent to fragment" );
                newsClass.loadfile(val,getApplicationContext());
                String path= getApplicationContext().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/recentnews.txt";
                FileOutputStream writer = new FileOutputStream(path, false);
                writer.write(String.valueOf(val+1).getBytes());
                writer.close();

            } catch (Exception e) {
                Log.e("Serviceclass: ", "Exception: " + e.getMessage());
            }finally {
                Log.i("Serviceclass val: ", String.valueOf(val));
            }

            return null;
        }
    }

}