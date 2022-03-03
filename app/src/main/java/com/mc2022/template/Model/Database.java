package com.mc2022.template.Model;


import com.mc2022.template.News;

import java.util.ArrayList;

public class Database {

    ArrayList<News> newsList= new ArrayList<>();

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<News> newsList) {
        this.newsList = newsList;
    }

    public void savelist(){

    }
    public void fetchlist(){

    }
}
