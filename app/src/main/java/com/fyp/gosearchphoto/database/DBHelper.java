package com.fyp.gosearchphoto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anamay on 6/28/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_FILE_NAME = "kimi.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }
    /*
        I'll pass in items table.SQLcreate.
        If my database is going to have more than one table,
        I'll call the create statements from each of the tables in turn.
    * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemsTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ItemsTable.SQL_DELETE);
        onCreate(db);
    }

}
