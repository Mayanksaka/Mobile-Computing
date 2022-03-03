package com.mc2022.template;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.mc2022.template.Fragment.MainFragment;

import java.util.ArrayList;

public class NewsAdapterList extends RecyclerView.Adapter<NewsAdapterList.NewsViewHolder>{

    private ArrayList<News> NewsList;

    public NewsAdapterList(ArrayList<News> NewsList) {
        this.NewsList = NewsList;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_display_item, parent,false);
        NewsViewHolder holder=new NewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        News news = NewsList.get(position);
        System.out.println("adapter running");
        holder.news_id.setText(position+".");
        holder.news_title.setText(news.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("position",String.valueOf(position));
                b.putString("title",news.getTitle());
                b.putString("body",news.getBody());
                b.putString("image",news.getImage());
                b.putString("comment", news.getComment());
                b.putFloat("rating",news.getRating());
                AppCompatActivity activity= (AppCompatActivity) view.getContext();
                MainFragment f = new MainFragment();
                f.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,f).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }


    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView news_id, news_title;
        public NewsViewHolder(View itemView)
        {
            super(itemView);
            news_id = itemView.findViewById(R.id.news_num);
            news_title = itemView.findViewById(R.id.news_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            fragment.runn(Integer.valueOf(getAdapterPosition()));
            Log.i("clicked: ", "News fragment opened" );
        }


    }
}
