package com.example.android.newsapp;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Matthew Young on 6/30/16.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> books) {
        super(context, 0, books);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View newsItemView = convertView;

        if (newsItemView == null) {
            newsItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_listview_layout, parent, false);
        }

        News currentNews = getItem(position);

        TextView newsTitleTextView = (TextView) newsItemView.findViewById(R.id.title_textView);
        newsTitleTextView.setText(Html.fromHtml(currentNews.getTitle()));

        TextView newsDescriptionTextView = (TextView) newsItemView.findViewById(R.id.description_textView);
        newsDescriptionTextView.setText(Html.fromHtml(currentNews.getDescription()));

        TextView newsURLTextView = (TextView) newsItemView.findViewById(R.id.url_textView);
        newsURLTextView.setText(currentNews.getURL());

        return newsItemView;
    }

}
