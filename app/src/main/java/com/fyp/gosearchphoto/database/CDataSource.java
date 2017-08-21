package com.fyp.gosearchphoto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fyp.gosearchphoto.fragment.DeptAlbumFragment;
import com.fyp.gosearchphoto.fragment.DeptUserFragment;
import com.fyp.gosearchphoto.fragment.GroupAlbumFragment;
import com.fyp.gosearchphoto.fragment.GroupProfileFragment;
import com.fyp.gosearchphoto.fragment.UserAlbumFragment;
import com.fyp.gosearchphoto.model.DataAlbum;
import com.fyp.gosearchphoto.model.DataDepartment;
import com.fyp.gosearchphoto.model.DataGroup;
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
        values.put(ItemTable.COLUMN_USER_FULLNAME, userName);
        values.put(ItemTable.COLUMN_USER_EMAIL, email);
        values.put(ItemTable.COLUMN_USER_PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = mDatabase.insert(ItemTable.TABLE_USER, null, values);
        Log.i("CDataSource", "Username " + userName + " has been successfully registered, PK value: " + newRowId);
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

    public List<DataUser> getUser(String email) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_USER_ID,
                ItemTable.COLUMN_USER_FULLNAME,
                ItemTable.COLUMN_USER_EMAIL,
                ItemTable.COLUMN_USER_PASSWORD,
                ItemTable.COLUMN_USER_COMPANYID,
                ItemTable.COLUMN_USER_DEPARTMENT,
                ItemTable.COLUMN_USER_TYPE
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_USER_FULLNAME + " ASC";

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
            item.setFullName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_FULLNAME)));
            item.setEmail(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_EMAIL)));
            item.setPassword(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_PASSWORD)));

            item.setCompanyId(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_COMPANYID)));
            item.setType(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_TYPE)));
            item.setType(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_DEPARTMENT)));

            dataItems.add(item);
        }

        cursor.close();
        close();
        Log.i("CDataSource", "Username retrieve success");

        return dataItems;
    }

    public List<DataUser> getUsersByName(String fullname, int cid, String page_name) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_USER_ID,
                ItemTable.COLUMN_USER_FULLNAME,
                ItemTable.COLUMN_USER_EMAIL,
                ItemTable.COLUMN_USER_PASSWORD,
                ItemTable.COLUMN_USER_COMPANYID,
                ItemTable.COLUMN_USER_DEPARTMENT,
                ItemTable.COLUMN_USER_TYPE
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_USER_EMAIL + " LIKE ? AND "+ItemTable.COLUMN_USER_COMPANYID+"= ?";
        String[] selectionArgs = {"%" + fullname + "%", Integer.toString(cid)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_USER_FULLNAME + " ASC";

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
            item.setFullName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_FULLNAME)));
            item.setEmail(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_EMAIL)));
            item.setPassword(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_PASSWORD)));

            item.setCompanyId(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_COMPANYID)));
            item.setType(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_TYPE)));
            item.setDepartmentName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_DEPARTMENT)));

            item.setPage_data_type(page_name);
            dataItems.add(item);
        }

        cursor.close();
        close();
        Log.i("CDataSource", "Username retrieve success");

        return dataItems;
    }


    public List<DataUser> getUsersByNameFrag(String fullname, int cid, String page_name, GroupProfileFragment gfrag, DeptUserFragment dufrag) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_USER_ID,
                ItemTable.COLUMN_USER_FULLNAME,
                ItemTable.COLUMN_USER_EMAIL,
                ItemTable.COLUMN_USER_PASSWORD,
                ItemTable.COLUMN_USER_COMPANYID,
                ItemTable.COLUMN_USER_DEPARTMENT,
                ItemTable.COLUMN_USER_TYPE
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_USER_FULLNAME + " LIKE ? AND "+ItemTable.COLUMN_USER_COMPANYID+"= ?";
        String[] selectionArgs = {"%" + fullname + "%", Integer.toString(cid)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_USER_FULLNAME + " ASC";

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
            item.setFullName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_FULLNAME)));
            item.setEmail(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_EMAIL)));
            item.setPassword(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_PASSWORD)));

            item.setCompanyId(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_COMPANYID)));
            item.setType(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_TYPE)));
            item.setDepartmentName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_DEPARTMENT)));

            item.setPage_data_type(page_name);

            if(page_name.equals("CreateGroupUserTop")) {
                if (gfrag.checkBottomItemExists(item.getUserId())) {
                    Log.i("item already exist", "in the bottom list ID:" + item.getUserId());

                } else {
                    dataItems.add(item);
                }
            }else if (page_name.equals("CreateDeptUserTop")){
                if (dufrag.checkBottomItemExists(item.getUserId())) {
                    Log.i("item already exist", "in the bottom list ID:" + item.getUserId());

                } else {
                    dataItems.add(item);
                }
            }else{
                dataItems.add(item);
            }

        }

        cursor.close();
        close();
        Log.i("CDataSource", "Username retrieve success");

        return dataItems;
    }

    public List<DataUser> getAllUserByCompanyId(int company_id, String page_name) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_USER_ID,
                ItemTable.COLUMN_USER_FULLNAME,
                ItemTable.COLUMN_USER_EMAIL,
                ItemTable.COLUMN_USER_PASSWORD,
                ItemTable.COLUMN_USER_COMPANYID,
                ItemTable.COLUMN_USER_DEPARTMENT,
                ItemTable.COLUMN_USER_TYPE
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_USER_COMPANYID + " = ?";
        String[] selectionArgs = {Integer.toString(company_id)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_USER_FULLNAME + " ASC";

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
            item.setFullName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_FULLNAME)));
            item.setEmail(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_EMAIL)));
            item.setPassword(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_PASSWORD)));

            item.setCompanyId(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_COMPANYID)));
            item.setType(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_TYPE)));
            item.setDepartmentName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_DEPARTMENT)));

            item.setPage_data_type(page_name);
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
                ItemTable.COLUMN_USER_FULLNAME,
                ItemTable.COLUMN_USER_EMAIL,
                ItemTable.COLUMN_USER_PASSWORD
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {Integer.toString(userid)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_USER_FULLNAME + " ASC";

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
            item.setFullName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_USER_FULLNAME)));
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
        values.put(ItemTable.COLUMN_USER_FULLNAME, userName);
        values.put(ItemTable.COLUMN_USER_EMAIL, email);

        // Which row to update, based on the title
        String selection = ItemTable.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {Integer.toString(userId)};

        int count = mDatabase.update(
                ItemTable.TABLE_USER,
                values,
                selection,
                selectionArgs);

        Log.i("CDataSource", "Username " + userName + " has been successfully updated,  Count:" + count);
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

        Log.i("CDataSource", "UserID " + userId + " has successfully change password to " + password + ", Count:" + count);
        close();
    }

    public void deleteUser(int userId) {
        // Define 'where' part of query.
        String selection = ItemTable.COLUMN_USER_ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {Integer.toString(userId)};
        // Issue SQL statement.
        mDatabase.delete(ItemTable.TABLE_USER, selection, selectionArgs);
        Log.i("CDataSource", "UserID " + userId + " has benn successfully deleted");

    }


    public boolean checkLogIn(String username, String password) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_USER_ID,
                ItemTable.COLUMN_USER_FULLNAME,
                ItemTable.COLUMN_USER_EMAIL,
                ItemTable.COLUMN_USER_PASSWORD
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_USER_EMAIL + " = ? AND " + ItemTable.COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_USER_FULLNAME + " ASC";

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
        if (exists == true) {
            Log.i("CDataSource", "User exists");

        } else {
            Log.i("CDataSource", "User does not exists");
        }
        return exists;
    }

    public boolean checkEmailExists(String email) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_USER_ID,
                ItemTable.COLUMN_USER_FULLNAME,
                ItemTable.COLUMN_USER_EMAIL,
                ItemTable.COLUMN_USER_PASSWORD
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_USER_FULLNAME + " ASC";

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
        if (exists == true) {
            Log.i("CDataSource", "User exists");

        } else {
            Log.i("CDataSource", "User does not exists");
        }
        return exists;
    }


    //
    //  DEPARTMENT TABLE
    //
    public List<DataDepartment> getAllDeptItems(int companyId) {
        List<DataDepartment> dataItems = new ArrayList<>();

        openRead();

        Cursor cursor = null;

        String selection = ItemTable.COLUMN_DEPT_COMPANY_ID + " = ?";
        String[] selectionArgs = {Integer.toString(companyId)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_DEPT_NAME + " ASC";

        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */


        cursor = mDatabase.query(
                ItemTable.TABLE_DEPARTMENT,
                ItemTable.ALL_DEPARTMENT_COLUMNS,
                selection, selectionArgs, null, null,
                ItemTable.COLUMN_DEPT_NAME);

        while (cursor.moveToNext()) {
            DataDepartment item = new DataDepartment();
            item.setDepartment_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_ID)));
            item.setCompany_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_COMPANY_ID)));
            item.setDepartment_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_NAME)));
            item.setDescription(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_DESC)));

            dataItems.add(item);
        }
        close();
        return dataItems;
    }
    //
    // ALBUM TABLE
    //

    public List<DataAlbum> getAllAlbumItems(int ownerId, String pageDataType) {
        String getDataType = pageDataType;
        List<DataAlbum> dataItems = new ArrayList<>();

        openRead();

        Cursor cursor = null;

        String selection = ItemTable.COLUMN_ALBUM_OWNER_ID + " = ?";
        String[] selectionArgs = {Integer.toString(ownerId)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_ALBUM_NAME + " ASC";

        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */


        cursor = mDatabase.query(
                ItemTable.TABLE_ALBUM,
                ItemTable.ALL_ALBUM_COLUMNS,
                selection, selectionArgs, null, null,
                ItemTable.COLUMN_ALBUM_NAME);

        while (cursor.moveToNext()) {
            DataAlbum item = new DataAlbum();
            item.setAlbumId(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_ID)));
            item.setOwner_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_OWNER_ID)));
            item.setAlbum_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_NAME)));
            item.setOwner_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_OWNER_NAME)));
            item.setDescription(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_DESC)));
            item.setPrivacy_type(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_TYPE)));


            // SET what page or screen is the album displayed
            item.setPage_data_type(getDataType);

            Log.i("get data type", item.getPage_data_type());
            Log.i("owner id", "This is the OWNER ID : "+ownerId);
            dataItems.add(item);
        }
        close();
        return dataItems;
    }
    public ArrayList<DataAlbum> getAllAlbumByNameFrag(int ownerId, String pageDataType, String albumName, UserAlbumFragment dataAlbumAdapter, GroupAlbumFragment gafrag, DeptAlbumFragment dafrag){
        String getDataType = pageDataType;
        ArrayList<DataAlbum> dataItems = new ArrayList<DataAlbum>();
        Log.i("~~~getDataType", getDataType);
        Log.i("~~~albumName", albumName);
        openRead();

        Cursor cursor = null;

        String selection = ItemTable.COLUMN_ALBUM_OWNER_ID + " = ? AND "+ItemTable.COLUMN_ALBUM_NAME+" LIKE ?";
        String[] selectionArgs = {Integer.toString(ownerId), "%"+albumName+ "%"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_ALBUM_NAME + " ASC";

        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */


        cursor = mDatabase.query(
                ItemTable.TABLE_ALBUM,
                ItemTable.ALL_ALBUM_COLUMNS,
                selection, selectionArgs, null, null,
                ItemTable.COLUMN_ALBUM_NAME);

        while (cursor.moveToNext()) {
            DataAlbum item = new DataAlbum();
            item.setAlbumId(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_ID)));
            item.setOwner_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_OWNER_ID)));
            item.setAlbum_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_NAME)));
            item.setOwner_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_OWNER_NAME)));
            item.setDescription(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_DESC)));
            item.setPrivacy_type(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_ALBUM_TYPE)));


            // SET what page or screen is the album displayed
            item.setPage_data_type(getDataType);

            if(pageDataType.equals("DBExistingAlbumTop")) {
                if (dataAlbumAdapter.checkBottomItemExists(item.getAlbumId())) {
                    Log.i("item already exist", "in the bottom list ID:" + item.getAlbumId());

                } else {
                    dataItems.add(item);
                }
            }

            if(pageDataType.equals("GroupExistingAlbumTop")) {
                Log.i("pageDataType", pageDataType);


                if (gafrag.checkBottomItemExists(item.getAlbumId())) {
                    Log.i("item already exist", "in the bottom list ID:" + item.getAlbumId());

                } else {
                    dataItems.add(item);
                }
            }

            if(pageDataType.equals("DAExistingAlbumTop")) {
                if (dafrag.checkBottomItemExists(item.getAlbumId())) {
                    Log.i("item already exist", "in the bottom list ID:" + item.getAlbumId());

                } else {
                    dataItems.add(item);
                }
            }
            if(pageDataType.equals("ManageAlbum")){
                dataItems.add(item);

            }
            Log.i("get data type", item.getPage_data_type());
            Log.i("owner id", "This is the OWNER ID : "+ownerId);
        }


        close();
        return dataItems;
    }

    //
    //   GROUP TABLE
    //

    public List<DataGroup> getAllGroupByOwnerId(int ownerId, String pageDataType) {
        String getDataType = pageDataType;
        List<DataGroup> dataItems = new ArrayList<>();

        openRead();

        Cursor cursor = null;

        String selection = ItemTable.COLUMN_GRP_OWNER_ID + " = ?";
        String[] selectionArgs = {Integer.toString(ownerId)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_GRP_NAME + " ASC";

        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */


        cursor = mDatabase.query(
                ItemTable.TABLE_GROUP,
                ItemTable.ALL_GROUP_COLUMNS,
                selection, selectionArgs, null, null,
                ItemTable.COLUMN_GRP_NAME);

        while (cursor.moveToNext()) {
            DataGroup item = new DataGroup();
            item.setGroup_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_GRP_ID)));
            item.setGroup_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_GRP_NAME)));
            item.setOwner_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_GRP_OWNER_ID)));
            item.setOwner_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_GRP_OWNER_NAME)));


            // SET what page or screen is the album displayed
            item.setPage_data_type(getDataType);

            Log.i("get data type", item.getPage_data_type());
            Log.i("owner id", "This is the OWNER ID : "+ownerId);
            dataItems.add(item);
        }
        close();
        return dataItems;
    }




    public List<DataUser> getAllGroupUserByGroupId(int group_id, String page_name) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_GRPUSER_ID,
                ItemTable.COLUMN_GRPUSER_USER_ID,
                ItemTable.COLUMN_GRPUSER_USER_NAME
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_GRPUSER_ID + " = ?";
        String[] selectionArgs = {Integer.toString(group_id)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_GRPUSER_USER_NAME + " ASC";

        Cursor cursor = null;
        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */
        cursor = mDatabase.query(
                ItemTable.TABLE_GROUP_USER,                     // The table to query
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
                    cursor.getColumnIndex(ItemTable.COLUMN_GRPUSER_USER_ID)));
            item.setFullName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_GRPUSER_USER_NAME)));
            item.setPage_data_type(page_name);
            dataItems.add(item);
        }

        cursor.close();
        close();
        Log.i("CDataSource", "Username retrieve success");

        return dataItems;
    }


    public List<DataGroup> getAllGroupUserByName(int group_id, String keyword, String page_name) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String getDataType = page_name;
        List<DataGroup> dataItems = new ArrayList<>();

        openRead();

        Cursor cursor = null;

        String selection = ItemTable.COLUMN_GRP_OWNER_ID + " = ? AND "+ItemTable.COLUMN_GRP_NAME +" LIKE ?";
        String[] selectionArgs = {Integer.toString(group_id), "%"+keyword+"%"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_GRP_NAME + " ASC";

        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */


        cursor = mDatabase.query(
                ItemTable.TABLE_GROUP,
                ItemTable.ALL_GROUP_COLUMNS,
                selection, selectionArgs, null, null,
                ItemTable.COLUMN_GRP_NAME);

        while (cursor.moveToNext()) {
            DataGroup item = new DataGroup();
            item.setGroup_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_GRP_ID)));
            item.setGroup_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_GRP_NAME)));
            item.setOwner_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_GRP_OWNER_ID)));
            item.setOwner_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_GRP_OWNER_NAME)));


            // SET what page or screen is the album displayed
            item.setPage_data_type(getDataType);

            Log.i("get data type", item.getPage_data_type());
            Log.i("grp id", "This is the OWNER ID : "+group_id);
            dataItems.add(item);
        }
        close();
        return dataItems;
    }
    //
    //  Department
    //

    public List<DataDepartment> getAllDepartmentByCompanyId(int company_id, String page_name) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_DEPT_COMPANY_ID,
                ItemTable.COLUMN_DEPT_ID,
                ItemTable.COLUMN_DEPT_NAME,
                ItemTable.COLUMN_DEPT_DESC
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_DEPT_COMPANY_ID + " = ?";
        String[] selectionArgs = {Integer.toString(company_id)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_DEPT_NAME + " ASC";

        Cursor cursor = null;
        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */
        cursor = mDatabase.query(
                ItemTable.TABLE_DEPARTMENT,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<DataDepartment> dataItems = new ArrayList<DataDepartment>();

        while (cursor.moveToNext()) {
            DataDepartment item = new DataDepartment();
            item.setDepartment_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_ID)));
            item.setCompany_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_COMPANY_ID)));
            item.setDepartment_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_NAME)));
            item.setDescription(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_DESC)));

            item.setPage_data_type(page_name);
            dataItems.add(item);
        }

        cursor.close();
        close();
        Log.i("CDataSource", "Username retrieve success");

        return dataItems;
    }






    ///" LIKE ? AND "+ItemTable.COLUMN_USER_COMPANYID+"= ?";

    public List<DataDepartment> getDepartmentByName(int company_id, String page_name, String keyword) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_DEPT_COMPANY_ID,
                ItemTable.COLUMN_DEPT_ID,
                ItemTable.COLUMN_DEPT_NAME,
                ItemTable.COLUMN_DEPT_DESC
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_DEPT_COMPANY_ID + " = ? AND "+ItemTable.COLUMN_DEPT_NAME+" LIKE ?";
        String[] selectionArgs = {Integer.toString(company_id), "%"+keyword+"%"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_DEPT_NAME + " ASC";

        Cursor cursor = null;
        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */
        cursor = mDatabase.query(
                ItemTable.TABLE_DEPARTMENT,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<DataDepartment> dataItems = new ArrayList<DataDepartment>();

        while (cursor.moveToNext()) {
            DataDepartment item = new DataDepartment();
            item.setDepartment_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_ID)));
            item.setCompany_id(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_COMPANY_ID)));
            item.setDepartment_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_NAME)));
            item.setDescription(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPT_DESC)));

            item.setPage_data_type(page_name);
            dataItems.add(item);
        }

        cursor.close();
        close();
        Log.i("CDataSource", "Username retrieve success");

        return dataItems;
    }


    //
    // DEPARTMENT USER TABLES
    //
    public List<DataUser> getAllUserByDepartmentId(int department_id, String page_name) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        openRead();
        String[] projection = {
                ItemTable.COLUMN_DEPTUSER_ID,
                ItemTable.COLUMN_DEPTUSER_USER_ID,
                ItemTable.COLUMN_DEPTUSER_USER_NAME
        };
        // Filter results WHERE "title" = 'username'
        String selection = ItemTable.COLUMN_DEPTUSER_ID + " = ?";
        String[] selectionArgs = {Integer.toString(department_id)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_DEPTUSER_USER_NAME + " ASC";

        Cursor cursor = null;
        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */
        cursor = mDatabase.query(
                ItemTable.TABLE_DEPARTMENT_USER,                     // The table to query
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
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPTUSER_USER_ID)));
            item.setFullName(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPTUSER_USER_NAME)));


            item.setPage_data_type(page_name);
            dataItems.add(item);
        }

        cursor.close();
        close();
        Log.i("CDataSource", "Username retrieve success");

        return dataItems;
    }


    //
    // DEPARTMENT ALBUM TABLE
    //

    public List<DataAlbum> getAlbumByDepartmentID(int department_id, String pageDataType) {
        String getDataType = pageDataType;
        List<DataAlbum> dataItems = new ArrayList<>();

        openRead();

        Cursor cursor = null;

        String selection = ItemTable.COLUMN_DEPTALBUM_DEPT_ID + " = ?";
        String[] selectionArgs = {Integer.toString(department_id)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemTable.COLUMN_DEPTALBUM_ALBUM_NAME + " ASC";

        /**
         *  If you want to sort by more than one column,
         *  pass in a comma delimited list of column names.
         *  Or if you want to sort in descending order,
         *  just pass in the appropriate SQL keyword desc.
         *  Whatever string you pass in just becomes a part of the SQL statement
         */


        cursor = mDatabase.query(
                ItemTable.TABLE_DEPARTMENT_ALBUM,
                ItemTable.ALL_DEPT_ALBUM_COLUMNS,
                selection, selectionArgs, null, null,
                ItemTable.COLUMN_DEPTALBUM_ALBUM_NAME);

        while (cursor.moveToNext()) {
            DataAlbum item = new DataAlbum();
            item.setAlbumId(cursor.getInt(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPTALBUM_ALBUM_ID)));

            item.setAlbum_name(cursor.getString(
                    cursor.getColumnIndex(ItemTable.COLUMN_DEPTALBUM_ALBUM_NAME)));


            // SET what page or screen is the album displayed
            item.setPage_data_type(getDataType);

            Log.i("get data type", item.getPage_data_type());
            dataItems.add(item);
        }
        close();
        return dataItems;
    }
}






