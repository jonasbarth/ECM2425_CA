package com.example.exeter.ecm2425ca.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jonas on 17/03/2018.
 */

public class Preferences {

    public static final String PREFERENCES = "MyPreferences_001";

    /**
     * Method to set the shared preferences of a String, String key value pair.
     * @param key
     * @param value
     * @param context
     */
    public static void setSharedPreference(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Method to check whether the shared preferences contain a key value
     * pair for the specified String
     * @param key
     * @param context
     * @return
     */
    public static boolean preferencesContains(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return sharedPreferences.contains(key);
    }

    /**
     * Method to get the String value associated with the key
     * @param key
     * @param defaultValue
     * @param context
     * @return
     */
    public static String getSharedPreferences(String key, String defaultValue, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, 0);

        String value = sharedPreferences.getString(key, defaultValue);
        return value;
    }

    /**
     * Method to set add a String, int, key value pair to the shared
     * preferences
     * @param key
     * @param value
     * @param context
     */
    public static void setSharedPreference(String key, int value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Method to get the int value associated with the key
     * @param key
     * @param defaultValue
     * @param context
     * @return
     */
    public static int getSharedPreferences(String key, int defaultValue, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, 0);

        int value = sharedPreferences.getInt(key, defaultValue);
        return value;
    }


}
