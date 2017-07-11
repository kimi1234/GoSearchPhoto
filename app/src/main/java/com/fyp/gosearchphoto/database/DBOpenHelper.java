package com.fyp.gosearchphoto.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by anamay on 6/30/17.
 */

public class DBOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "candyloopdb.sqlite";
    private static final int DATABASE_VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}