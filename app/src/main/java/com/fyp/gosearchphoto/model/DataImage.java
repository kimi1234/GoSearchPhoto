package com.fyp.gosearchphoto.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by anamay on 8/23/17.
 */

public class DataImage implements Parcelable{
    private String status;
    private int image_id;
    private String image_url;
    private String title;
    private String size;
    private List<DataImage> imageinfo;
    private List<DataImage> tag;
    private String description;
    private String album;
    private int album_id;
    private String uploaded_by;
    private String permission_type;
    private String tag_name;
    private String page_data_type;
    private String isFavourite;
    private String uploadDateTime;


    //Duplicate
    private List<DataImage> image_info;

    public DataImage(String status, int image_id, String image_url, String title, String size, List<DataImage> imageinfo, String description, String album, int album_id, String uploaded_by, String permission_type, String tag_name, String page_data_type) {
        this.status = status;
        this.image_id = image_id;
        this.image_url = image_url;
        this.title = title;
        this.size = size;
        this.imageinfo = imageinfo;
        this.description = description;
        this.album = album;
        this.album_id = album_id;
        this.uploaded_by = uploaded_by;
        this.permission_type = permission_type;
        this.tag_name = tag_name;
        this.page_data_type = page_data_type;
    }

    public DataImage() {
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<DataImage> getImageinfo() {
        return imageinfo;
    }

    public void setImageinfo(List<DataImage> imageinfo) {
        this.imageinfo = imageinfo;
    }

    public List<DataImage> getTag() {
        return tag;
    }

    public void setTag(List<DataImage> tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(String uploaded_by) {
        this.uploaded_by = uploaded_by;
    }

    public String getPermission_type() {
        return permission_type;
    }

    public void setPermission_type(String permission_type) {
        this.permission_type = permission_type;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getPage_data_type() {
        return page_data_type;
    }

    public void setPage_data_type(String page_data_type) {
        this.page_data_type = page_data_type;
    }

    public String getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(String uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    public List<DataImage> getImage_info() {
        return image_info;
    }

    public void setImage_info(List<DataImage> image_info) {
        this.image_info = image_info;
    }

    public String isFavourite() {
        return isFavourite;
    }

    public void setFavourite(String favourite) {
        isFavourite = favourite;
    }

    @Override
    public String toString() {
        return "DataImage{" +
                "status='" + status + '\'' +
                ", image_id=" + image_id +
                ", image_url='" + image_url + '\'' +
                ", title='" + title + '\'' +
                ", size='" + size + '\'' +
                ", imageinfo=" + imageinfo +
                ", image_info=" + image_info +
                ", tag=" + tag +
                ", description='" + description + '\'' +
                ", album='" + album + '\'' +
                ", album_id=" + album_id +
                ", uploaded_by='" + uploaded_by + '\'' +
                ", permission_type='" + permission_type + '\'' +
                ", tag_name='" + tag_name + '\'' +
                ", page_data_type='" + page_data_type + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.image_id);
        dest.writeString(this.image_url);
        dest.writeString(this.title);
        dest.writeString(this.size);
        dest.writeString(this.description);
        dest.writeInt(this.album_id);
        dest.writeString(this.album);
        dest.writeString(this.uploaded_by);
        dest.writeString(this.permission_type);
        dest.writeString(this.tag_name);
        dest.writeString(this.isFavourite);
        dest.writeString(this.status);
        dest.writeList(this.imageinfo);
        dest.writeList(this.image_info);
        dest.writeList(this.tag);
        dest.writeString(this.page_data_type);
        dest.writeString(this.uploadDateTime);

    }

    public static Parcelable.Creator<DataImage> getCREATOR() {
        return CREATOR;
    }

    protected DataImage(Parcel in) {

        this.image_id = in.readInt();
        this.album_id = in.readInt();
        this.image_url = in.readString();
        this.title = in.readString();
        this.size = in.readString();
        this.description = in.readString();
        this.album = in.readString();
        this.uploaded_by = in.readString();
        this.permission_type = in.readString();
        this.page_data_type = in.readString();
        this.status = in.readString();
        this.isFavourite = in.readString();
        this.imageinfo  = in.readArrayList((ClassLoader) CREATOR);
        this.image_info  = in.readArrayList((ClassLoader) CREATOR);
        this.tag  = in.readArrayList((ClassLoader) CREATOR);
        this.tag_name = in.readString();
        this.uploadDateTime = in.readString();

        //    this.departmentId = in.readInt();;
    }

    public static final Parcelable.Creator<DataImage> CREATOR = new Parcelable.Creator<DataImage>() {
        @Override
        public DataImage createFromParcel(Parcel source) {
            return new DataImage(source);
        }

        @Override
        public DataImage[] newArray(int size) {
            return new DataImage[size];
        }
    };
}
