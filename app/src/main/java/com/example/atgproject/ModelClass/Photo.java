package com.example.atgproject.ModelClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {

    String id;

    String owner;
    String secret;
    String server;
    long farm;
    String title;
    @SerializedName("ispublic")
    long isPublic;
    @SerializedName("isfriend")
    long isFriend;
    @SerializedName("isFamily")
    long isFamily;
    String url_s;
    int width_s;
    int height_s;

    protected Photo(Parcel in) {
        id = in.readString();
        owner = in.readString();
        secret = in.readString();
        server = in.readString();
        farm = in.readLong();
        title = in.readString();
        isPublic = in.readLong();
        isFriend = in.readLong();
        isFamily = in.readLong();
        url_s = in.readString();
        width_s = in.readInt();
        height_s = in.readInt();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl_s() {
        return url_s;
    }

    public void setUrl_s(String url_s) {
        this.url_s = url_s;
    }

    public int getWidth_s() {
        return width_s;
    }

    public void setWidth_s(int width_s) {
        this.width_s = width_s;
    }

    public int getHeight_s() {
        return height_s;
    }

    public void setHeight_s(int height_s) {
        this.height_s = height_s;
    }

    public long getFarm() {
        return farm;
    }

    public void setFarm(long farm) {
        this.farm = farm;
    }

    public long getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(long isPublic) {
        this.isPublic = isPublic;
    }

    public long getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(long isFriend) {
        this.isFriend = isFriend;
    }

    public long getIsFamily() {
        return isFamily;
    }

    public void setIsFamily(long isFamily) {
        this.isFamily = isFamily;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(owner);
        dest.writeString(secret);
        dest.writeString(server);
        dest.writeLong(farm);
        dest.writeString(title);
        dest.writeLong(isPublic);
        dest.writeLong(isFriend);
        dest.writeLong(isFamily);
        dest.writeString(url_s);
        dest.writeInt(width_s);
        dest.writeInt(height_s);
    }
}
