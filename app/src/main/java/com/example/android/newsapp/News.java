package com.example.android.newsapp;

/**
 * Created by Matthew Young on 6/30/16.
 */
public class News {

    private String mTitle;
    private String mDescription;
    private String mURL;

    public News(String title, String description, String url) {
        mTitle = title;
        mDescription = description;
        mURL = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String url) {
        mURL = url;
    }

    @Override
    public String toString() {
        return "News{" +
                "mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mURL='" + mURL + '\'' +
                '}';
    }
}
