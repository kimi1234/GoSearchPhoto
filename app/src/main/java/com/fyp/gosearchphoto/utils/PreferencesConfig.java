package com.fyp.gosearchphoto.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by anamay on 7/1/17.
 */

public class PreferencesConfig {
    public static String PREFS_USER_ID = "user_id";
    public static String PREFS_PASSWORD = "pass";

    public static void setUserIDPreference(int userID, Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(PREFS_USER_ID, userID);
        editor.commit();
    }

    public static int getUserIDPreference(Context context) {
        // O is the default value if the key is not present
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        int language = settings.getInt(PREFS_USER_ID, 000);
        return language;
    }

    public static void setPasswordPreference(String pass, Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_PASSWORD, pass);
        editor.commit();
    }

    public static String getPasswordPreference(Context context) {
        // O is the default value if the key is not present
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        String password = settings.getString(PREFS_PASSWORD, "No password found");
        return password;
    }
}