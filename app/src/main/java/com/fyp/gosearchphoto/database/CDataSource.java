package com.fyp.gosearchphoto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fyp.gosearchphoto.model.DataUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anamay on 6/30/17.
 */

public class CDataSource {
    private SQLiteOpenHelper mDbHelper;
    private SQLiteDatabase mDatabase;
    private static CDataSource instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     */
    private CDataSource(Context context) {
        this.mDbHelper = new DBOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static CDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new CDataSource(context);
        }
        return instance;
    }

    public void openWrite() {
        // Gets the data repository in write mode
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void openRead() {
        // Gets the data repository in write mode
        mDatabase = mDbHelper.getReadableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    // INSERT USER
    public void insertUser(String userName, String email, String password) {
        openWrite();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ItemTable.COLUMN_USER_NAME, userName);
        values.put(ItemTable.COLUMN_USER_EMAIL, email);
        values.put(ItemTable.COLUMN_USER_PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = mDatabase.insert(ItemTable.TABLE_USER, null, values);
        Log.i("CDataSource", "Username "+userName+" has been successfully registered, PK value: "+newRowId);
        close();
    }


    public long getDataUserCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, ItemTable.TABLE_USER);
    }

 /*   public void seedDatabase(List<DataItem> dataItemList) {
        long numItems = getDataItemsCount();
        if (numItems == 0) {
            for (DataItem item :
                    dataItemList) {
                try {
                    createItem(item);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    public List<DataUser> getUser(String username) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_USER_ID,
                ItemTable.COLUMN_USER_NAME,
                ItemTable.COLUMN_USER_EMAIL,
                ItemTable.COLUMN_USER_PASSWORD
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_USER_NAME + " = ?";
        String[] selectionArgs = { username };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_USER_NAME + " ASC";

        Cursor cursor = null;
        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */
         cursor = mDatabase.query(
                ItemTable.TABLE_USER,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<DataUser> dataItems = new ArrayList<>();

        while (cursor.moveToNext()) {
            DataUser item = new DataUser();
            item.setUserId(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_ID)));
            item.setUserName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_NAME)));
            item.setEmail(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_EMAIL)));
            item.setPassword(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_PASSWORD)));

            dataItems.add(item);
        }

        cursor.close();
        close();
        Log.i("CDataSource", "Username retrieve success");

        return dataItems;
    }


    public List<DataUser> getUserInfoByID(int userid) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_USER_ID,
                ItemTable.COLUMN_USER_NAME,
                ItemTable.COLUMN_USER_EMAIL,
                ItemTable.COLUMN_USER_PASSWORD
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {Integer.toString(userid)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_USER_NAME + " ASC";

        Cursor cursor = null;
        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */
        cursor = mDatabase.query(
                ItemTable.TABLE_USER,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<DataUser> dataItems = new ArrayList<>();

        while (cursor.moveToNext()) {
            DataUser item = new DataUser();
            item.setUserId(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_ID)));
            item.setUserName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_NAME)));
            item.setEmail(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_EMAIL)));
            item.setPassword(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_PASSWORD)));

            dataItems.add(item);
        }

        cursor.close();
        close();
        Log.i("CDataSource", "Username retrieve success");

        return dataItems;
    }

    // INSERT USER INFO
    public void updateUserInfo(String userName, String email, int userId) {
        openRead();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(ItemTable.COLUMN_USER_NAME, userName);
        values.put(ItemTable.COLUMN_USER_EMAIL, email);

        // Which row to update, based on the title
        String selection = ItemTable.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {Integer.toString(userId)};

        int count = mDatabase.update(
                ItemTable.TABLE_USER,
                values,
                selection,
                selectionArgs);

        Log.i("CDataSource", "Username "+userName+" has been successfully updated,  Count:"+count);
        close();
    }


    // UPDATE USER PASSWORD
    public void updatePassword(String password, int userId) {
        openRead();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(ItemTable.COLUMN_USER_PASSWORD, password);

        // Which row to update, based on the title
        String selection = ItemTable.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {Integer.toString(userId)};

        int count = mDatabase.update(
                ItemTable.TABLE_USER,
                values,
                selection,
                selectionArgs);

        Log.i("CDataSource", "UserID "+userId+" has successfully change password to "+password+", Count:"+count);
        close();
    }

    public void deleteUser(int userId){
        // Define 'where' part of query.
        String selection = ItemTable.COLUMN_USER_ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {Integer.toString(userId)};
        // Issue SQL statement.
        mDatabase.delete(ItemTable.TABLE_USER, selection, selectionArgs);
        Log.i("CDataSource", "UserID "+userId+" has benn successfully deleted");

    }


    public boolean checkLogIn(String username, String password) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_USER_ID,
                ItemTable.COLUMN_USER_NAME,
                ItemTable.COLUMN_USER_EMAIL,
                ItemTable.COLUMN_USER_PASSWORD
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_USER_NAME + " = ? AND "+ItemTable.COLUMN_USER_PASSWORD+" = ?";
        String[] selectionArgs = {username, password};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_USER_NAME + " ASC";

        Cursor cursor = null;
        String limit = "1";
        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */
        cursor = mDatabase.query(
                ItemTable.TABLE_USER,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null,                                 // The sort order
                limit
        );


        boolean exists = (cursor.getCount() > 0);

        cursor.close();
        close();
        if (exists== true){
            Log.i("CDataSource","User exists");

        }else{
            Log.i("CDataSource","User does not exists");
        }
        return exists;
    }
    public boolean checkUsernameExists(String username) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_USER_ID,
                ItemTable.COLUMN_USER_NAME,
                ItemTable.COLUMN_USER_EMAIL,
                ItemTable.COLUMN_USER_PASSWORD
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_USER_NAME + " = ?";
        String[] selectionArgs = {username};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_USER_NAME + " ASC";

        Cursor cursor = null;
        String limit = "1";
        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */
        cursor = mDatabase.query(
                ItemTable.TABLE_USER,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null,                                 // The sort order
                limit
        );


        boolean exists = (cursor.getCount() > 0);

        cursor.close();
        close();
        if (exists== true){
            Log.i("CDataSource","User exists");

        }else{
            Log.i("CDataSource","User does not exists");
        }
        return exists;
    }


    }





