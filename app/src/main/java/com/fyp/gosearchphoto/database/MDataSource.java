package com.fyp.gosearchphoto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fyp.gosearchphoto.model.DataItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anamay on 6/28/17.
 */

public class MDataSource {
    private SQLiteDatabase mDatabase;
    private Context mContext;
    SQLiteOpenHelper mDbHelper;

    //  I'm passing the context in, and that can either be an activity or
    //  it can be the application context.

    /*
    * Good coding practices using the prefix M to indicate that
    * something is a member object of the class.
    */
    public MDataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public DataItem createItem(DataItem item) {
        ContentValues values = item.toValues();
        mDatabase.insert(ItemsTable.TABLE_ITEMS, null, values);
        return item;
    }

    public long getDataItemsCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, ItemsTable.TABLE_ITEMS);
    }
    // populate db
    public void seedDatabase(List<DataItem> dataItemList) {
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
    }

    public List<DataItem> getAllItems(String category) {
        List<DataItem> dataItems = new ArrayList<>();
      /*  Cursor cursor = mDatabase.query(ItemsTable.TABLE_ITEMS, ItemsTable.ALL_COLUMNS,
                null, null, null, null, null);
       */
        Cursor cursor = null;
        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */
        category = null;
        if (category == null) {
            cursor = mDatabase.query(ItemsTable.TABLE_ITEMS, ItemsTable.ALL_COLUMNS,
                    null, null, null, null, ItemsTable.COLUMN_NAME);
        } else {
            String[] categories = {category};
            cursor = mDatabase.query(ItemsTable.TABLE_ITEMS, ItemsTable.ALL_COLUMNS,
                    ItemsTable.COLUMN_CATEGORY + "=?", categories, null, null, ItemsTable.COLUMN_NAME);
        }
        while (cursor.moveToNext()) {
            DataItem item = new DataItem();
            item.setItemId(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
            item.setItemName(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_NAME)));
            item.setDescription(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION)));
            item.setCategory(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_CATEGORY)));
            item.setSortPosition(cursor.getInt(
                    cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
            item.setPrice(cursor.getDouble(
                    cursor.getColumnIndex(ItemsTable.COLUMN_PRICE)));
            item.setImage(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)));
            dataItems.add(item);
        }
        return dataItems;
    }


    public List<DataItem> getImagesByKeyword(String keyword) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        open();
        String[] projection = {
                ItemsTable.COLUMN_ID,
                ItemsTable.COLUMN_NAME,
                ItemsTable.COLUMN_DESCRIPTION,
                ItemsTable.COLUMN_CATEGORY,
                ItemsTable.COLUMN_ID,
                ItemsTable.COLUMN_PRICE,
                ItemsTable.COLUMN_IMAGE
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemsTable.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = {"%"+keyword+"%"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemsTable.COLUMN_NAME + " ASC";

        Cursor cursor = null;
        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */
        cursor = mDatabase.query(
                ItemsTable.TABLE_ITEMS,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<DataItem> dataItems = new ArrayList<>();

        while (cursor.moveToNext()) {
            DataItem item = new DataItem();
            item.setItemId(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
            item.setItemName(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_NAME)));
            item.setDescription(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION)));
            item.setCategory(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_CATEGORY)));
            item.setSortPosition(cursor.getInt(
                    cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
            item.setPrice(cursor.getDouble(
                    cursor.getColumnIndex(ItemsTable.COLUMN_PRICE)));
            item.setImage(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)));
            dataItems.add(item);
        }

        cursor.close();
        Log.i("CDataSource", "Username retrieve success");
        close();
        return dataItems;
    }
}

