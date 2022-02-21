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
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.mc2022.template.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class downloadservice extends Service {
    @RequiresApi(api = Build.VERSION_CODES.O)

    String num;
    String download_TAG="Downloadservice";
    Boolean b =false;
    Boolean Inetable;
    public downloadservice() {

    }

//    @Override
    public void onDestroy() {
        super.onDestroy();
        b=false;
        Log.i(download_TAG, "onDestroy: ");

    }

    @SuppressLint("NewApi")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        b=true;
//        Newsservice s =new Newsservice(getApplicationContext());
        Newsservice s =new Newsservice();

        num = intent.getExtras().getString("num");
        s.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,Integer.valueOf(num));
//        return super.onStartCommand(intent,flags,startId);

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

    private class Newsservice extends AsyncTask<Integer , Integer, String> {
//        Context context;
//        public Newsservice(Context applicationContext) {
//            context=applicationContext;
//        }

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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Integer... value) {
//            InternetHTTP http = new InternetHTTP();
            int val = value[0];
            while(true){
                if(!b){
                    break;
                }
                String url = "https://petwear.in/mc2022/news/news_" + String.valueOf(val) + ".json";

                try {

                    NetworkInfo Info = ((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                    if(Info != null && Info.isConnected())
                        Inetable=true;
                    else
                        Inetable=false;
                    if(!Inetable){
                        sendBroadcast(new Intent("INTERNET_CONNECTIVITY").setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES).putExtra("msg", "Internet Not Available"));
                        Log.i(download_TAG, "Internet not available");
                        throw new UnsupportedOperationException("No Connectivity");
                    }
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
                    Log.i(download_TAG, "file saved");
                    Intent i = new Intent();
                    i.setAction("FILE_DOWNLOADED");
                    i.putExtra("num",val);
                    i.setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
                    sendBroadcast(i);
                    Log.i(download_TAG, "broadcast sent to fragment" );

                    val++;

                    String path= getApplicationContext().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/recentnews.txt";
                    FileOutputStream writer = new FileOutputStream(path, false);
                    writer.write(String.valueOf(val).getBytes());
                    writer.close();
//                    Thread.sleep(500);
                    Log.i(download_TAG, "Sleep started" );

                } catch (MalformedURLException e) {
                    Log.e("TAG", "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e("TAG", "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e("TAG", "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e("TAG", "Exception: " + e.getMessage());
                }finally {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }return null;
        }
    }

}