package com.example.android.newsapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Matthew Young on 6/30/16.
 */
public final class GuardianFeedAPI {
    private static final String GUARDIAN_BASE_URL = "http://content.guardianapis.com/world/france?";

    public static final String API_KEY_PARAMETER = "api-key";
    public static final String API_KEY = "test";

    public static String constructURI(String...params) {

        String mutableURI = GUARDIAN_BASE_URL;

        for(int i = 0; i < params.length; i += 2) {
            mutableURI += params[i] + "=" + params[i+1];
            if(params.length - (i + 2) >= 2) {
                mutableURI += "&";
            }
        }

        return mutableURI;
    }

    /*
     * Got help from:
     * https://www.youtube.com/watch?v=YL0-TsEICdw&list=PLVTeA3WccG_7Kgey8VtmFLCEVR5jeZNFK&index=8
     */

    public static String getNewsData(String uri) throws IOException {

        try {
            URL url = new URL(uri);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

    }
}
