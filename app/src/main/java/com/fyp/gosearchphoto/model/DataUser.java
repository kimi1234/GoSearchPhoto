package com.fyp.gosearchphoto.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anamay on 6/30/17.
 */


public class DataUser implements Parcelable {

    private int user_id;

    private String type;
    private String fullName;
    private String email;
    private String password;
    
    private int companyId;
    private String departmentName;
    private String page_data_type;
    private String status;

    //   private int departmentId;

    public DataUser() {
    }

    public DataUser(int userId, String userType, String fullName, String email, String password
            , String departmentName, int companyId, String page_data_type, String status) {
        this.user_id = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.departmentName = departmentName;
        this.companyId = companyId;
        this.page_data_type = page_data_type;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String userType) {
        this.type = userType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public String getPage_data_type() {
        return page_data_type;
    }

    public void setPage_data_type(String page_data_type) {

        String pname = "No Page Name";
        if (page_data_type != null) {
            pname = page_data_type;
        }


        this.page_data_type = pname;
    }

    public void setDepartmentName(String departmentName) {
        String dname = "No Department";
        if (departmentName != null) {
            dname = departmentName;
        }


        this.departmentName = dname;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    /*   public int getDepartmentId() {
           return departmentId;
       }

       public void setDepartmentId(int departmentId) {
           this.departmentId = departmentId;
       }
   */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.fullName);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeInt(this.user_id);
        dest.writeString(this.type);
        dest.writeString(this.departmentName);
        dest.writeInt(this.companyId);
        dest.writeString(this.page_data_type);
        dest.writeString(this.status);


    }

    @Override
    public String toString() {
        return "DataUser{" +
                "userId=" + user_id +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", companyId=" + companyId +
                ", departmentName='" + departmentName + '\'' +
                ", page_data_type='" + page_data_type + '\'' +
                '}';
    }

    protected DataUser(Parcel in) {
        this.user_id = in.readInt();
        this.type = in.readString();
        this.fullName = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.departmentName = in.readString();
        this.companyId = in.readInt();
        this.page_data_type = in.readString();
        this.status = in.readString();
        //    this.departmentId = in.readInt();;
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
