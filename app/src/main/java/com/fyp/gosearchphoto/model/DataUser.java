package com.fyp.gosearchphoto.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anamay on 6/30/17.
 */

public class DataUser implements Parcelable{

    private int userId;
    private String userName;
    private String email;
    private String password;

    public DataUser() {
    }
    public DataUser(int userId, String userName, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.userName);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeInt(this.userId);

    }

    protected DataUser(Parcel in) {
        this.userId = in.readInt();;
        this.userName = in.readString();;
        this.email = in.readString();;
        this.password = in.readString();;

    }

    public static final Parcelable.Creator<DataUser> CREATOR = new Parcelable.Creator<DataUser>() {
        @Override
        public DataUser createFromParcel(Parcel source) {
            return new DataUser(source);
        }

        @Override
        public DataUser[] newArray(int size) {
            return new DataUser[size];
        }
    };
}
