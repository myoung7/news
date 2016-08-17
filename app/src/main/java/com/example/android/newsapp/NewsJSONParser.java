package com.example.android.newsapp;

import android.text.Html;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Matthew Young on 6/30/16.
 */
public class NewsJSONParser {

    private static final String TITLE_RESPONSE_KEY = "webTitle";
    private static final String DESCRIPTION_RESPONSE_KEY = "sectionName";
    private static final String URL_RESPONSE_KEY = "webUrl";

    public static ArrayList<News> parseFeed(String content) {

        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONObject responseDataObject = jsonObject.getJSONObject("response");

            JSONArray entriesArray = responseDataObject.getJSONArray("results");

            ArrayList<News> newsList = new ArrayList<>();

            for (int i = 0; i < entriesArray.length(); i++) {

                JSONObject object = entriesArray.getJSONObject(i);

                String title = object.getString(TITLE_RESPONSE_KEY);
                String description = object.getString(DESCRIPTION_RESPONSE_KEY);
                String url = object.getString(URL_RESPONSE_KEY);

                News news = new News(title, description, url);

                newsList.add(news);
                System.out.println(news.toString());
            }

            return newsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
