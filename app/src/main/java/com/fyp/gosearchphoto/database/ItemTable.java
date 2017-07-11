package com.fyp.gosearchphoto.database;

/**
 * Created by anamay on 6/30/17.
 */

public class ItemTable {

    public static final String TABLE_ALBUM = "Album";
    public static final String TABLE_PHOTO = "Photo";
    public static final String TABLE_USER = "User";

    //   User Table
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    //   Album Table
    public static final String COLUMN_ALBUM_ID = "album_id";
    public static final String COLUMN_ALBUM_NAME = "album_name";
    public static final String COLUMN_ALBUM_TYPE = "album_type";
    public static final String COLUMN_PERMISSION_ID = "permission_id";

    //   Photo Table
    public static final String COLUMN_IMAGE_ID = "user_id";
    public static final String COLUMN_IMAGE_URL = "username";
    public static final String COLUMN_TITLE = "email";
    public static final String COLUMN_SIZE = "password";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ALBUM = "album";
    public static final String COLUMN_UPLOADED_BY = "uploaded_by";
    public static final String COLUMN_UPLOADED_DATE = "uploaded_date";
    public static final String COLUMN_IS_FAVOURITE = "isFavourite";


    public static final String[] ALL_USER_COLUMNS =
            {COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_EMAIL,
                    COLUMN_USER_PASSWORD};

    public static final String[] ALL_ALBUM_COLUMNS =
            {COLUMN_ALBUM_ID, COLUMN_ALBUM_NAME, COLUMN_ALBUM_TYPE,
                    COLUMN_PERMISSION_ID};

    public static final String[] ALL_PHOTO_COLUMNS =
            {COLUMN_IMAGE_ID, COLUMN_IMAGE_URL, COLUMN_TITLE,
                    COLUMN_SIZE, COLUMN_DESCRIPTION, COLUMN_ALBUM,
                        COLUMN_UPLOADED_BY, COLUMN_UPLOADED_DATE, COLUMN_IS_FAVOURITE};

    //  Delete or drop the table from the database
    public static final String SQL_DELETE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    public static final String SQL_DELETE_PHOTO = "DROP TABLE IF EXISTS " + TABLE_PHOTO;
    public static final String SQL_DELETE_ALBUM = "DROP TABLE IF EXISTS " + TABLE_ALBUM;

    // INSERT USER
 /*   private String insertUser(String userName, String email,String password) {
        String insert_user_sql;

        insert_user_sql ="INSERT INTO "+TABLE_USER+"("+COLUMN_USER_NAME+", "+COLUMN_USER_EMAIL+", "+COLUMN_USER_PASSWORD+") VALUES ("+
                userName+", "+email+", "+password+");";

        return insert_user_sql;
    }
*/

 /*


    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_CATEGORY + " TEXT," +
                    COLUMN_POSITION + " INTEGER," +
                    COLUMN_PRICE + " REAL," +
                    COLUMN_IMAGE + " TEXT" + ");";


*/




}
