package com.fyp.gosearchphoto.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by anamay on 7/23/17.
 */

public class DataAlbum implements Parcelable {

    private int albumId;
    private String album_name;
    private String owner_name;
    private int owner_id;
    private String privacy_type;
    private String description;
    private String page_data_type;

    private String status;
    private List<DataAlbum> albumList;


    // Duplicate
    private int album_id;
    private String name;


    public DataAlbum(int albumId, String album_name, String owner_name, int owner_id, String privacy_type, String description, String page_data_type, String status, List<DataAlbum> albumList, int album_id, String name) {
        this.albumId = albumId;
        this.album_name = album_name;
        this.owner_name = owner_name;
        this.owner_id = owner_id;
        this.privacy_type = privacy_type;
        this.description = description;
        this.page_data_type = page_data_type;
        this.status = status;
        this.albumList = albumList;
        this.album_id = album_id;
        this.name = name;
    }

    public DataAlbum() {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataAlbum> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<DataAlbum> albumList) {
        this.albumList = albumList;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getPrivacy_type() {
        return privacy_type;
    }

    public void setPrivacy_type(String privacy_type) {
        this.privacy_type = privacy_type;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.albumId);
        dest.writeInt(this.owner_id);
        dest.writeString(this.album_name);
        dest.writeString(this.owner_name);
        dest.writeString(this.privacy_type);
        dest.writeString(this.description);
        dest.writeString(this.page_data_type);


        dest.writeString(this.status);
        dest.writeString(this.name);
        dest.writeInt(this.album_id);
        dest.writeList(this.albumList);


    }

    protected DataAlbum(Parcel in) {
        this.albumId = in.readInt();;
        this.owner_id = in.readInt();
        this.album_name = in.readString();
        this.owner_name = in.readString();
        this.privacy_type = in.readString();
        this.description = in.readString();
        this.page_data_type = in.readString();

        this.status = in.readString();;
        this.name = in.readString();;
        this.album_id = in.readInt();;
        this.albumList = in.readArrayList((ClassLoader) CREATOR);

    }

    public static final Parcelable.Creator<DataAlbum> CREATOR = new Parcelable.Creator<DataAlbum>() {
        @Override
        public DataAlbum createFromParcel(Parcel source) {
            return new DataAlbum(source);
        }

        @Override
        public DataAlbum[] newArray(int size) {
            return new DataAlbum[size];
        }
    };


}
