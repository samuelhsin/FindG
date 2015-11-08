package com.findg.data.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class User implements IOrmModel {
    @DatabaseField(generatedId = true, dataType = DataType.LONG)
    @SerializedName("id")
    private long id;
    @DatabaseField(dataType = DataType.STRING)
    @SerializedName("name")
    private String name;
    @DatabaseField(dataType = DataType.BOOLEAN)
    @SerializedName("firstLoad")
    private boolean firstLoad;
    @DatabaseField(columnDefinition = "LONGBLOB", dataType = DataType.BYTE_ARRAY)
    @SerializedName("avatar")
    private byte[] avatar;
    @DatabaseField(dataType = DataType.STRING)
    @SerializedName("avatarZip")
    private String avatarZip;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFirstLoad() {
        return firstLoad;
    }

    public void setFirstLoad(boolean firstLoad) {
        this.firstLoad = firstLoad;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarZip() {
        return avatarZip;
    }

    public void setAvatarZip(String avatarZip) {
        this.avatarZip = avatarZip;
    }
}
