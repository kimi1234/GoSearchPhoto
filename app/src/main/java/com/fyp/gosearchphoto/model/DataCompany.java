package com.fyp.gosearchphoto.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anamay on 8/19/17.
 */

public class DataCompany implements Parcelable {
    private int company_id;
    private String status;
    private String company_name;
    private String industry;
    private String description;
    private int owner_id;


    public DataCompany(int company_id, String status, String company_name, String industry, String description, int owner_id) {
        this.company_id = company_id;
        this.status = status;
        this.company_name = company_name;
        industry = industry;
        this.description = description;
        this.owner_id = owner_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        industry = industry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.company_id);
        dest.writeString(this.status);
        dest.writeString(this.company_name);
        dest.writeString(this.industry);
        dest.writeInt(this.owner_id);

    }

    @Override
    public String toString() {
        return "DataCompany{" +
                "company_id=" + company_id +
                ", status='" + status + '\'' +
                ", company_name='" + company_name + '\'' +
                ", Industry='" + industry + '\'' +
                ", description='" + description + '\'' +
                ", owner_id=" + owner_id +
                '}';
    }

    protected DataCompany(Parcel in) {
        this.company_id = in.readInt();
        this.status = in.readString();
        this.industry = in.readString();
        this.description = in.readString();
        this.company_name = in.readString();
        this.owner_id = in.readInt();





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
