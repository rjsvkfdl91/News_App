package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.util.List;

/**
 * Created by S522050 on 5/29/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private Bundle mUrl;

    public NewsLoader(Context context, Bundle url) {
        super(context);
        mUrl = url;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {

        if (mUrl == null){
            return null;
        }
        List<News> newsList = QueryUtils.fetchNewsData(mUrl.getString("url"));
        return newsList;
    }
}
