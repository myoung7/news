package com.example.android.newsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ArrayList<News> newsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        loadNews();

        Button refreshButton = (Button) findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshButtonPressed();
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        displayNewsList();
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void loadNews() {
        progressBar.setVisibility(View.VISIBLE);

        if (isOnline()) {
            GetNewsDataTask task = new GetNewsDataTask();
            String[] paramStrings = { GuardianFeedAPI.API_KEY_PARAMETER,
                    GuardianFeedAPI.API_KEY
            };
            task.execute(paramStrings);
        } else {
            Toast onlineErrorToast = Toast.makeText(this, getString(R.string.network_unavailable), Toast.LENGTH_SHORT);
            onlineErrorToast.show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void refreshButtonPressed() {
        loadNews();
    }

    public void displayNewsList() {

        NewsAdapter adapter = new NewsAdapter(this, newsArrayList);

        ListView newsListView = (ListView) findViewById(R.id.listView);
        newsListView.setAdapter(adapter);


        //Found code at http://www.ezzylearning.com/tutorial/handling-android-listview-onitemclick-event
        newsListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String urlString = ((TextView) view.findViewById(R.id.url_textView)).getText().toString();
                Uri webpage = Uri.parse(urlString);

                Intent newsIntent = new Intent(Intent.ACTION_VIEW, webpage);

                startActivity(newsIntent);
            }
        });

    }

    class GetNewsDataTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String uri = GuardianFeedAPI.constructURI(strings);
            try {
                String jsonData = GuardianFeedAPI.getNewsData(uri);
                return jsonData;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<News> newsList = NewsJSONParser.parseFeed(result);
            if (newsList != null) {
                progressBar.setVisibility(View.INVISIBLE);
                newsArrayList = newsList;
                displayNewsList();
            } else {
                Toast noResultsFoundToast = Toast.makeText(MainActivity.this,
                        getString(R.string.could_not_load_news), Toast.LENGTH_SHORT);
                noResultsFoundToast.show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

}