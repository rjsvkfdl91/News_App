package com.example.android.newsapp;

/**
 * Created by S522050 on 5/28/2017.
 */

public class News {

    private String mTitle,mDate,mUrl,mDesc,mImageUrl;

    public News(String title, String date, String url, String desc, String imageUrl) {
        mTitle = title;
        mDate = date;
        mUrl = url;
        mDesc = desc;
        mImageUrl = imageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getDesc() {
        return mDesc;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
