package com.mc2022.template;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class newsclass {

    private static newsclass news_class;
    private newsclass(Context context) {
    }

    public static newsclass getNews(Context context){
        if(news_class==null){
            news_class= new newsclass(context);
        }
        return news_class;
    }
//    public static News instance;

    public ArrayList<News> list = new ArrayList<>();

    public void savefile(News n, Context context, int num){
        File file;
        try{
            String wordsline;
            String path=context.getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/news"+num+".json";
            file = new File(path);
            StringBuilder content = new StringBuilder();
            BufferedReader buff = new BufferedReader(new FileReader(file));
            while ((wordsline = buff.readLine()) != null) {
                content.append(wordsline);
                content.append('\n');
            }
            buff.close();
            list.set(num,n);
            JSONObject jsonObj = new JSONObject(content.toString());
            jsonObj.put("comment",n.getComment());
            jsonObj.put("rate",n.getRating());

            FileWriter output = new FileWriter(file);
            output.write(jsonObj.toString(2));
            output.flush();
            output.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public ArrayList<News> loadfile( int num, Context context){
        File file;
        try {
            System.out.println("loadfile running");
            String wordsline;
            String path=context.getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/news"+num+".json";
            file = new File(path);
            StringBuilder content = new StringBuilder();
            BufferedReader buff = new BufferedReader(new FileReader(file));
            while ((wordsline = buff.readLine()) != null) {
                content.append(wordsline);
                content.append('\n');
            }
            buff.close();
            JSONObject jsonObj = new JSONObject(content.toString());
            News n = new News();
            n.setId(num);
            n.setTitle(jsonObj.getString("title"));
            n.setBody(jsonObj.getString("body"));
            n.setImage(jsonObj.getString("image-url"));
            try{
                n.setComment(jsonObj.getString("comment"));
                n.setRating(Float.valueOf(jsonObj.getString("rate")));
            }catch (Exception e){
                Log.i("TAG", "load_file: No comment, No rate");
            }

            list.add(n);

//            updatelist();
        }
        catch (IOException | JSONException e) {
            Log.e("TAG", "image download fail in background: " );
//            e.printStackTrace();
        }
        return list;

    }
}
