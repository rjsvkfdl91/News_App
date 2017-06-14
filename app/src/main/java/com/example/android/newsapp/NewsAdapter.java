package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by S522050 on 5/28/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private static final String DATE_SEPARATOR = "T";

    public NewsAdapter (Context context, ArrayList<News> news){
        super(context, 0, news);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        if (listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.item_list,parent,false);
        }

        News currentNews = getItem(position);

        // Setting Title
        TextView title = (TextView)listItem.findViewById(R.id.title);
        title.setText(currentNews.getTitle());

        // Setting Description
        TextView desc = (TextView)listItem.findViewById(R.id.desc);
        desc.setText(currentNews.getDesc());

        // Setting Image
        ImageView image = (ImageView)listItem.findViewById(R.id.image);
        Glide.with(getContext()).load(currentNews.getImageUrl()).into(image);

        // Setting Date
        TextView date = (TextView)listItem.findViewById(R.id.date);
        String originalDate = currentNews.getDate();
        String currentDate;

        if (originalDate.contains(DATE_SEPARATOR)){

            String [] parts = originalDate.split(DATE_SEPARATOR);

            currentDate = parts[0];

            date.setText(currentDate);
        }else{
            date.setText(R.string.no_date);
        }

        return listItem;
    }
}
