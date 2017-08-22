package com.fyp.gosearchphoto.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by anamay on 6/30/17.
 */


public class DataUser implements Parcelable {

    private int user_id;
    private String type;
    private String fullname;
    private String email;
    private String password;
    
    private int company_id;
    private String departmentName;
    private String page_data_type;
    private String status;

    private List<DataUser> cUserList;
    //   private int departmentId;

    //Duplicate
    private String fullName;
    private int id;
    private String department;

    public DataUser() {
    }

    public DataUser(int userId, String userType, String fullName, String email, String password
            , String departmentName, int companyId, String page_data_type, String status) {
        this.user_id = userId;
        this.fullname = fullName;
        this.email = email;
        this.password = password;
        this.departmentName = departmentName;
        this.company_id = companyId;
        this.page_data_type = page_data_type;
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<DataUser> getcUserList() {
        return cUserList;
    }

    public void setcUserList(List<DataUser> cUserList) {
        this.cUserList = cUserList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String userType) {
        this.type = userType;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
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

        dest.writeString(this.fullname);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeInt(this.user_id);
        dest.writeInt(this.id);
        dest.writeString(this.type);
        dest.writeString(this.departmentName);
        dest.writeInt(this.company_id);
        dest.writeString(this.page_data_type);
        dest.writeString(this.status);
        dest.writeList(this.cUserList);
        dest.writeString(this.fullName);
        dest.writeString(this.department);


    }

    public static Creator<DataUser> getCREATOR() {
        return CREATOR;
    }

    protected DataUser(Parcel in) {

        this.user_id = in.readInt();
        this.type = in.readString();
        this.fullname = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.departmentName = in.readString();
        this.department = in.readString();
        this.company_id = in.readInt();
        this.page_data_type = in.readString();
        this.status = in.readString();
        this.cUserList  = in.readArrayList((ClassLoader) CREATOR);
        this.fullName = in.readString();
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
