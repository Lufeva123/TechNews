package com.example.android.technews.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.technews.Articles.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class QueryUtil {

    public static ArrayList<Article> extractArticles(URL url) {

        ArrayList<Article> articles = new ArrayList<>();

        JSONObject jsonObject = null;
        JSONObject jsonResponse = null;
        JSONArray results;

        try {
            jsonObject = new JSONObject(makeHttpRequest(url));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonObject == null) {

            return articles;
        }

        jsonResponse = jsonObject.optJSONObject("response");


        results = jsonResponse.optJSONArray("results");

        for (int i = 0; i < results.length(); i++) {

            JSONObject article = null;

            try {

                article = results.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String sectionName = article.optString("sectionName");
            String title = article.optString("webTitle");
            String date = article.optString("webPublicationDate");
            String webUrl = article.optString("webUrl");

            JSONArray tags = article.optJSONArray("tags");

            JSONObject authorTag = tags.optJSONObject(0);

            String author = "";

            if (authorTag != null) {

                author = authorTag.optString("webTitle");
            }

            articles.add(new Article(sectionName, date, title, webUrl, author));
        }

        return articles;
    }

    private static String  makeHttpRequest (URL url) throws IOException {

        String jsonREsponse = "";

        if (url == null) {

            return jsonREsponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {

                inputStream = urlConnection.getInputStream();
                jsonREsponse = readFromStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (urlConnection != null) {

                urlConnection.disconnect();
            }

            if (inputStream != null) {

                inputStream.close();
            }
        }

        return jsonREsponse;
    }

    private static String readFromStream (InputStream in) throws IOException {

        StringBuilder builder = new StringBuilder();

        if (in != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            while (line != null) {

                builder.append(line);
                line = bufferedReader.readLine();
            }
        }

        return builder.toString();
    }

    public static boolean hasInternetConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return actNetworkInfo != null && actNetworkInfo.isConnectedOrConnecting();
    }
}
