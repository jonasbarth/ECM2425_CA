package com.example.exeter.ecm2425ca.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

/**
 * Created by Jonas on 17/03/2018.
 */

public class NetworkUtil {


    public final static String WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/forecast?";
    public final static String QUERY_PARAM = "id";
    public final static String API_PARAM = "APPID";
    public final static String API_KEY = "dbe7d114db504397b40a929d0a9464e2";
    public final static String QUERY_MODE = "mode";
    public final static String QUERY_FORMAT = "json";


    /**
     * Method to build the a URI for the openweathermap API.
     * Mode will be json.
     * @param cityID
     * @return
     */
    public static URL buildUrl(String cityID) {
        Uri builtUri = Uri.parse(WEATHER_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, cityID)
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(QUERY_MODE, QUERY_FORMAT)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("buildUrl", url.toString());
        return url;
    }


    /**
     * Method to get the response text from the passed
     * in URL.
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Log.d("getResponseFromHttpUrl", "opening connection");

        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(in));

            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            String result = total.toString();
            return result;

        } finally {
            urlConnection.disconnect();
            Log.d("getRespone", "Disconnecting");
        }

    }

    /**
     * Method to check if an internet connection is available
     * @param context
     * @return
     */
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        /*Check if the connection is available*/
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.d("checkInternetConnection", "Internet Connection Not Present");
            return false;
        }
    }
}
