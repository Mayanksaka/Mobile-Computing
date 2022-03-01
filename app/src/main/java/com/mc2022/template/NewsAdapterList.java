package com.mc2022.template;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mc2022.template.Fragment.MainFragment;
import java.util.ArrayList;

public class NewsAdapterList extends RecyclerView.Adapter<NewsAdapterList.NewsViewHolder>{

    private ArrayList<News> NewsList;
    private MainFragment  fragment = new MainFragment();

    public  NewsAdapterList(ArrayList<News> NewsList) {
        this.NewsList = NewsList;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_viewitems, parent,false);
        NewsViewHolder holder=new NewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        News news = NewsList.get(position);
        holder.news_id.setText(String.valueOf(news.getId()));
        holder.news_title.setText(news.getTitle()+".");

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
            fragment.runn(Integer.valueOf(NewsList.get(getAdapterPosition()).getId()));
            Log.i("clicked: ", "News fragment opened" );
        }
    }
}
