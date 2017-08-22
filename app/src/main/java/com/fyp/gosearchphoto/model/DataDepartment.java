package com.fyp.gosearchphoto.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by anamay on 7/21/17.
 */

public class DataDepartment implements Parcelable {

    private int department_id;
    private int company_id;
    private String department_name;
    private String description;

    // duplicate for JSON
    private String name;
    private String desc;
    private List<DataDepartment> departmentList;
    private String status;

    private String page_data_type;


    public DataDepartment() {
    }

    public DataDepartment(int department_id, int DataDepartment, String department_name, String description, String page_data_type) {
        this.department_id = department_id;
        this.company_id = DataDepartment;
        this.department_name = department_name;
        this.description = description;
        this.page_data_type = page_data_type;
    }



    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int owner_id) {
        this.company_id = company_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<DataDepartment> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<DataDepartment> departmentList) {
        this.departmentList = departmentList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPage_data_type() {
        return page_data_type;
    }

    public void setPage_data_type(String page_data_type) {
        this.page_data_type = page_data_type;
    }

    @Override
    public String toString() {
        return "DataDepartment{" +
                "department_id=" + department_id +
                ", company_id=" + company_id +
                ", department_name='" + department_name + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", departmentList=" + departmentList +
                ", status='" + status + '\'' +
                ", page_data_type='" + page_data_type + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.company_id);
        dest.writeInt(this.department_id);
        dest.writeString(this.department_name);
        dest.writeString(this.description);
        dest.writeString(this.page_data_type);
        dest.writeString(this.desc);
        dest.writeString(this.name);
        dest.writeString(this.status);
        dest.writeList(this.departmentList);


    }
    protected DataDepartment(Parcel in) {
        this.department_id = in.readInt();;
        this.description = in.readString();
        this.department_name = in.readString();
        this.company_id = in.readInt();;
        this.page_data_type = in.readString();
        this.desc = in.readString();
        this.status = in.readString();
        this.name = in.readString();
        this.departmentList  = in.readArrayList((ClassLoader) CREATOR);

    }

    public static final Parcelable.Creator<DataDepartment> CREATOR = new Parcelable.Creator<DataDepartment>() {
        @Override
        public DataDepartment createFromParcel(Parcel source) {
            return new DataDepartment(source);
        }

        @Override
        public DataDepartment[] newArray(int size) {
            return new DataDepartment[size];
        }
    };
}
