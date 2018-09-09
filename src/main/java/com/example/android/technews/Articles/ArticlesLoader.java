package com.example.android.technews.Articles;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.technews.Utils.QueryUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ArticlesLoader extends AsyncTaskLoader<ArrayList<Article>> {

    private String url;

    public ArticlesLoader (Context con, String url) {

        super(con);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();
    }

    @Override
    public ArrayList<Article> loadInBackground() {

        URL url = null;

        try {
            url = new URL(this.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return QueryUtil.extractArticles(url);
    }
}
