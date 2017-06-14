package com.example.android.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    NewsAdapter mAdapter;

    private static final int LOADER_ID = 1;

    private TextView mEmptyStateTextView;

    private View progressBar;

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        listView = (ListView)findViewById(R.id.list);

        // Setting emptyTextView in case of that there is no data or internet connection
        mEmptyStateTextView = (TextView)findViewById(R.id.emptyStateTextView);
        listView.setEmptyView(mEmptyStateTextView);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                News currentNews = mAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getUrl());

                Intent webIntent = new Intent(Intent.ACTION_VIEW,newsUri);
                startActivity(webIntent);

            }
        });

        Spinner sourceSpinner = (Spinner)findViewById(R.id.sourceSpinner);
        final String[]sources = getResources().getStringArray(R.array.sources);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,sources);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);

        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                restartLoader(parent.getItemAtPosition(position).toString());
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID,null,this);

        }else{
            progressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }

    }
    
    private void restartLoader(String selectedItem){

        Uri baseUri = Uri.parse(getString(R.string.baseUrl));
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("source",selectedItem);
        uriBuilder.appendQueryParameter("sortBy",selectedItem);
        uriBuilder.appendQueryParameter("apiKey","1f1fcf6cf15640959e6f783d41f84ba5");

        Bundle url = new Bundle();
        url.putString("url",uriBuilder.toString());
        // To restart Loader to get new result with selected query
        getLoaderManager().restartLoader(LOADER_ID,url,this);
        listView.smoothScrollToPosition(0);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        mEmptyStateTextView.setVisibility(View.GONE);
        return new NewsLoader(this,args);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> result) {

        mAdapter.clear();

        if (result != null && !result.isEmpty()){
            mAdapter.addAll(result);
        }
        progressBar.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_result);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        mAdapter.clear();
    }
}
