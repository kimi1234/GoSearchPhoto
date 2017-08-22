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
    public static String PREFS_COMPANY_ID = "company_id";
    public static String PREFS_USER_TYPE = "user_type";
    public static String PREFS_DEPARTMENT = "department";
    public static String PREFS_EMAIL = "user_email";
    public static String PREFS_FULLNAME = "user_full_name";

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
    public static void setFullnamePreference(String fullname, Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_FULLNAME, fullname);
        editor.commit();
    }

    public static String getFullnamePreference(Context context) {
        // O is the default value if the key is not present
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        String fullname = settings.getString(PREFS_FULLNAME, "No username found");
        return fullname;
    }


    public static void setEmailPreference(String email, Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_EMAIL, email);
        editor.commit();
    }

    public static String getEmailPreference(Context context) {
        // O is the default value if the key is not present
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        String email = settings.getString(PREFS_EMAIL, "No email found");
        return email;
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

        String password = settings.getString(PREFS_COMPANY_ID, "No password found");
        return password;
    }

    public static void setDepartment(String pass, Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_DEPARTMENT, pass);
        editor.commit();
    }

    public static String getDepartmentPreference(Context context) {
        // O is the default value if the key is not present
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        String password = settings.getString(PREFS_DEPARTMENT, "No password found");
        return password;
    }


    public static void setCompany_idPreference(int companyid, Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(PREFS_COMPANY_ID, companyid);
        editor.commit();
    }

    public static int getCompanyIdPreference(Context context) {
        // O is the default value if the key is not present
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        int password = settings.getInt(PREFS_COMPANY_ID, 000);
        return password;
    }


    public static void setUserTypePreference(String pass, Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_USER_TYPE, pass);
        editor.commit();
    }

    public static String getUserTypePreference(Context context) {
        // O is the default value if the key is not present
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        String password = settings.getString(PREFS_USER_TYPE, "No user type found");
        return password;
    }
}