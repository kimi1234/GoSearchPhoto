package com.fyp.gosearchphoto.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anamay on 8/22/17.
 */

public class DataStatus implements Parcelable {


    private String status;
    private int user_id;
    private String department_id;


    public DataStatus(String status, int user_id, String department_id) {
        this.status = status;
        this.user_id = user_id;
        this.department_id = department_id;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeInt(this.user_id);
        dest.writeString(this.department_id);
    }


    @Override
    public String toString() {
        return "DataStatus{" +
                "status='" + status + '\'' +
                ", user_id=" + user_id +
                ", department_id=" + department_id + '\'' +
                '}';
    }

    protected DataStatus(Parcel in) {
        this.status = in.readString();
        this.user_id = in.readInt();
        this.department_id = in.readString();
    }

    public static final Parcelable.Creator<DataStatus> CREATOR = new Parcelable.Creator<DataStatus>() {
        @Override
        public DataStatus createFromParcel(Parcel source) {
            return new DataStatus(source);
        }

        @Override
        public DataStatus[] newArray(int size) {
            return new DataStatus[size];
        }
    };

}
