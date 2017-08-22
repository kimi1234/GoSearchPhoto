package com.fyp.gosearchphoto.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anamay on 8/19/17.
 */

public class DataCompany implements Parcelable {
    private int company_id;
    private String status;
    private String companyname;
    private String industry;
    private String desc;
    private int user_id;

    public DataCompany(int company_id, String status, String companyname, String industry, String desc, int user_id) {
        this.company_id = company_id;
        this.status = status;
        this.companyname = companyname;
        this.industry = industry;
        this.desc = desc;
        this.user_id = user_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.company_id);
        dest.writeString(this.status);
        dest.writeString(this.companyname);
        dest.writeString(this.industry);
        dest.writeString(this.desc);
        dest.writeInt(this.user_id);

    }

    @Override
    public String toString() {
        return "DataCompany{" +
                "company_id=" + company_id +
                ", status='" + status + '\'' +
                ", company_name='" + companyname + '\'' +
                ", Industry='" + industry + '\'' +
                ", description='" + desc + '\'' +
                ", user_id=" + user_id +
                '}';
    }

    protected DataCompany(Parcel in) {
        this.company_id = in.readInt();
        this.status = in.readString();
        this.industry = in.readString();
        this.desc = in.readString();
        this.companyname = in.readString();
        this.user_id = in.readInt();





        //    this.departmentId = in.readInt();;
    }

    public static final Parcelable.Creator<DataCompany> CREATOR = new Parcelable.Creator<DataCompany>() {
        @Override
        public DataCompany createFromParcel(Parcel source) {
            return new DataCompany(source);
        }

        @Override
        public DataCompany[] newArray(int size) {
            return new DataCompany[size];
        }
    };

}
