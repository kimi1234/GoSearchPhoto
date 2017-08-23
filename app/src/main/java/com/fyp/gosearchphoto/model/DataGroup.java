package com.fyp.gosearchphoto.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by anamay on 7/27/17.
 */

public class DataGroup implements Parcelable {
    // This will be primary id
    private int group_id;
    private int owner_id;
    private String owner_name;
    private String group_name;
    private String page_data_type;

    private String status;
    private List<DataGroup> groupList;
    private List<DataGroup> groupUserList;

    //duplicate
    private String groupName;
    private int user_id;
    private String userName;





    public DataGroup() {
    }

    public DataGroup(int group_id, int owner_id, String owner_name, String group_name, String page_data_type, String status, List<DataGroup> groupList, List<DataGroup> groupUserList, String groupName, int user_id, String userName) {
        this.group_id = group_id;
        this.owner_id = owner_id;
        this.owner_name = owner_name;
        this.group_name = group_name;
        this.page_data_type = page_data_type;
        this.status = status;
        this.groupList = groupList;
        this.groupUserList = groupUserList;
        this.groupName = groupName;
        this.user_id = user_id;
        this.userName = userName;
    }

    public List<DataGroup> getGroupUserList() {
        return groupUserList;
    }

    public void setGroupUserList(List<DataGroup> groupUserList) {
        this.groupUserList = groupUserList;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getPage_data_type() {
        return page_data_type;
    }

    public void setPage_data_type(String page_data_type) {
        this.page_data_type = page_data_type;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<DataGroup> groupList) {
        this.groupList = groupList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /*   public ContentValues toValues() {
            // I have seven columns
            // Add each of the field's values to the values object.
            // The key should be the name of the column in the database.
            // And I'm going to use my constants from the table class for that.
            ContentValues values = new ContentValues(7);

            values.put(ItemTable.COLUMN_GRP_OWNER_ID, group_id);
            values.put(ItemTable.COLUMN_GRP_NAME, group_name);
            values.put(ItemTable.COLUMN_GRP_OWNER_ID, owner_id);
            values.put(ItemTable.COLUMN_GRP_OWNER_NAME, owner_name);
            return values;
        }
    */
    /* by making this a parcelable object we can now pass objects
        that are instances of this class, data item, between activities as intent extras.
    * */

    @Override
    public String toString() {
        return "DataGroup{" +
                "group_id=" + group_id +
                ", owner_id=" + owner_id +
                ", owner_name='" + owner_name + '\'' +
                ", group_name='" + group_name + '\'' +
                ", page_data_type='" + page_data_type + '\'' +
                ", status='" + status + '\'' +
                ", groupList=" + groupList +
                ", groupUserList=" + groupUserList +
                ", groupName='" + groupName + '\'' +
                ", user_id='" + user_id + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.group_id);
        dest.writeString(this.group_name);
        dest.writeInt(this.owner_id);
        dest.writeString(this.owner_name);
        dest.writeString(this.page_data_type);
        dest.writeString(this.status);
        dest.writeString(this.groupName);
        dest.writeList(this.groupList);
        dest.writeList(this.groupUserList);
        dest.writeString(this.userName);
        dest.writeInt(this.user_id);

    }

    protected DataGroup(Parcel in) {
        this.group_id = in.readInt();
        this.group_name = in.readString();
        this.owner_id = in.readInt();
        this.owner_name = in.readString();
        this.page_data_type= in.readString();
        this.groupName= in.readString();
        this.status= in.readString();
        this.groupList  = in.readArrayList((ClassLoader) CREATOR);
        this.groupUserList  = in.readArrayList((ClassLoader) CREATOR);
        this.user_id = in.readInt();
        this.userName = in.readString();

    }

    public static final Parcelable.Creator<com.fyp.gosearchphoto.model.DataGroup> CREATOR = new Parcelable.Creator<com.fyp.gosearchphoto.model.DataGroup>() {
        @Override
        public com.fyp.gosearchphoto.model.DataGroup createFromParcel(Parcel source) {
            return new com.fyp.gosearchphoto.model.DataGroup(source);
        }

        @Override
        public com.fyp.gosearchphoto.model.DataGroup[] newArray(int size) {
            return new com.fyp.gosearchphoto.model.DataGroup[size];
        }
    };

}
