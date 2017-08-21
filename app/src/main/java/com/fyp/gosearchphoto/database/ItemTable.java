package com.fyp.gosearchphoto.database;

/**
 * Created by anamay on 6/30/17.
 */

public class ItemTable {

    public static final String TABLE_ALBUM = "ProjectAlbum";
    public static final String TABLE_PHOTO = "Photo";
    public static final String TABLE_USER = "User";
    public static final String TABLE_COMPANY = "Company";
    public static final String TABLE_DEPARTMENT = "Department";
    public static final String TABLE_DEPARTMENT_USER = "DepartmentUser";
    public static final String TABLE_DEPARTMENT_ALBUM ="DepartmentAlbum";

    public static final String TABLE_GROUP = "Groups";
    public static final String TABLE_GROUP_USER = "GroupUser";

    //   Company Table
    public static final String COLUMN_COMPANY_ID = "company_id";
    public static final String COLUMN_COMPANY_OWNER = "user_id";
    public static final String COLUMN_COMPANY_NAME = "company_name";
    public static final String COLUMN_COMPANY_INDUSTRY= "industry";
    public static final String COLUMN_COMPANY_DESCRIPTION= "description";

    //   User Table
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_FULLNAME = "fullname";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_TYPE = "type";
    public static final String COLUMN_USER_COMPANYID = "company_id";
    public static final String COLUMN_USER_DEPARTMENT = "departmentName";

    //   Album Table
    public static final String COLUMN_ALBUM_ID = "album_id";
    public static final String COLUMN_ALBUM_NAME = "album_name";
    public static final String COLUMN_ALBUM_TYPE = "privacy_type";
    public static final String COLUMN_ALBUM_OWNER_NAME = "owner_name";
    public static final String COLUMN_ALBUM_OWNER_ID = "owner_id";
    public static final String COLUMN_ALBUM_DESC = "description";
    public static final String COLUMN_ALBUUM_PERMISSION_ID = "permission_id";


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


    // Department Table
    public static final String COLUMN_DEPT_ID = "department_id";
    public static final String COLUMN_DEPT_NAME = "department_name";
    public static final String COLUMN_DEPT_COMPANY_ID = "company_id";
    public static final String COLUMN_DEPT_DESC = "description";

    // Department User
    public static final String COLUMN_DEPTUSER_ID = "department_id";
    public static final String COLUMN_DEPTUSER_USER_ID = "user_id";
    public static final String COLUMN_DEPTUSER_USER_NAME = "user_name";

    // Group Table
    public static final String COLUMN_GRP_ID = "group_id";
    public static final String COLUMN_GRP_NAME = "group_name";
    public static final String COLUMN_GRP_OWNER_ID = "owner_id";
    public static final String COLUMN_GRP_OWNER_NAME = "owner_name";

    // Group User
    public static final String COLUMN_GRPUSER_ID = "group_id";
    public static final String COLUMN_GRPUSER_USER_ID = "user_id";
    public static final String COLUMN_GRPUSER_USER_NAME = "user_name";


    //Dept Album Table
    public static final String COLUMN_DEPTALBUM_DEPT_ID = "department_id";
    public static final String COLUMN_DEPTALBUM_ALBUM_ID = "album_id";
    public static final String COLUMN_DEPTALBUM_ALBUM_NAME = "album_name";



    public static final String[] ALL_DEPT_ALBUM_COLUMNS =
            {COLUMN_DEPTALBUM_DEPT_ID, COLUMN_DEPTALBUM_ALBUM_ID,
                    COLUMN_DEPTALBUM_ALBUM_NAME};

    public static final String[] ALL_DEPT_USER_COLUMNS =
            {COLUMN_DEPTUSER_ID, COLUMN_DEPTUSER_USER_ID,
                    COLUMN_DEPTUSER_USER_NAME};


    public static final String[] ALL_GROUP_COLUMNS =
            {COLUMN_GRP_ID, COLUMN_GRP_NAME, COLUMN_GRP_OWNER_ID,
                    COLUMN_GRP_OWNER_NAME};

    public static final String[] ALL_GROUP_USER_COLUMNS =
            {COLUMN_GRPUSER_ID, COLUMN_GRPUSER_USER_ID,
                    COLUMN_GRPUSER_USER_NAME};

    public static final String[] ALL_USER_COLUMNS =
            {COLUMN_USER_ID, COLUMN_USER_FULLNAME, COLUMN_USER_EMAIL,
                    COLUMN_USER_PASSWORD, COLUMN_USER_COMPANYID, COLUMN_USER_TYPE};

    public static final String[] ALL_ALBUM_COLUMNS =
            {COLUMN_ALBUM_ID, COLUMN_ALBUM_NAME, COLUMN_ALBUM_TYPE,
                    COLUMN_ALBUM_OWNER_NAME, COLUMN_ALBUM_OWNER_ID,COLUMN_ALBUM_DESC};


    public static final String[] ALL_PHOTO_COLUMNS =
            {COLUMN_IMAGE_ID, COLUMN_IMAGE_URL, COLUMN_TITLE,
                    COLUMN_SIZE, COLUMN_DESCRIPTION, COLUMN_ALBUM,
                        COLUMN_UPLOADED_BY, COLUMN_UPLOADED_DATE, COLUMN_IS_FAVOURITE};

    public static final String[] ALL_DEPARTMENT_COLUMNS =
            {COLUMN_DEPT_ID, COLUMN_DEPT_NAME, COLUMN_DEPT_COMPANY_ID,
                    COLUMN_DEPT_DESC};

    //  Delete or drop the table from the database
    public static final String SQL_DELETE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    public static final String SQL_DELETE_PHOTO = "DROP TABLE IF EXISTS " + TABLE_PHOTO;
    public static final String SQL_DELETE_ALBUM = "DROP TABLE IF EXISTS " + TABLE_ALBUM;
    public static final String SQL_DELETE_COMPANY = "DROP TABLE IF EXISTS " + TABLE_COMPANY;

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
