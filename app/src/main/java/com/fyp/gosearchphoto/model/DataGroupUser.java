package com.fyp.gosearchphoto.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.fyp.gosearchphoto.database.ItemTable;

/**
 * Created by anamay on 7/27/17.
 */

public class DataGroupUser implements Parcelable {
    // This will be primary id
    private int group_id;
    private int user_id;
    private String user_name;
    private String page_data_type;

    public DataGroupUser() {
    }

    public DataGroupUser(int group_id, int user_id, String user_name, String page_data_type) {
        this.group_id = group_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.page_data_type = page_data_type;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPage_data_type() {
        return page_data_type;
    }

    public void setPage_data_type(String page_data_type) {
        this.page_data_type = page_data_type;
    }

    public ContentValues toValues() {
        // I have seven columns
        // Add each of the field's values to the values object.
        // The key should be the name of the column in the database.
        // And I'm going to use my constants from the table class for that.
        ContentValues values = new ContentValues(7);

        values.put(ItemTable.COLUMN_GRPUSER_ID, group_id);
        values.put(ItemTable.COLUMN_GRPUSER_USER_NAME, user_name);
        values.put(ItemTable.COLUMN_GRPUSER_USER_ID, user_id);
        return values;
    }

    /* by making this a parcelable object we can now pass objects
        that are instances of this class, data item, between activities as intent extras.
    * */
    @Override
    public int describeContents() {
        return 0;
    }


    @Override

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.group_id);
        dest.writeString(this.user_name);
        dest.writeInt(this.user_id);
        dest.writeString(this.page_data_type);

    }

    protected DataGroupUser(Parcel in) {
        this.group_id = in.readInt();
        this.user_name = in.readString();
        this.user_id = in.readInt();
        this.page_data_type = in.readString();
    }

    public static final Parcelable.Creator<com.fyp.gosearchphoto.model.DataGroupUser> CREATOR = new Parcelable.Creator<com.fyp.gosearchphoto.model.DataGroupUser>() {
        @Override
        public com.fyp.gosearchphoto.model.DataGroupUser createFromParcel(Parcel source) {
            return new com.fyp.gosearchphoto.model.DataGroupUser(source);
        }

        @Override
        public com.fyp.gosearchphoto.model.DataGroupUser[] newArray(int size) {
            return new com.fyp.gosearchphoto.model.DataGroupUser[size];
        }
    };


}
