package com.findg.data.model;

import com.findg.data.dao.UserDao;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user", daoClass = UserDao.class)
public class User implements IOrmModel {
    @DatabaseField(generatedId = true, dataType = DataType.LONG)
    @SerializedName("id")
    private long id;
    @DatabaseField(dataType = DataType.STRING)
    @SerializedName("name")
    private String name;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    @SerializedName("sn")
    private String sn;
    @DatabaseField(dataType = DataType.BOOLEAN)
    @SerializedName("firstLoad")
    private boolean firstLoad;
    @DatabaseField(columnDefinition = "LONGBLOB", dataType = DataType.BYTE_ARRAY)
    @SerializedName("avatar")
    private transient byte[] avatar;
    @DatabaseField(dataType = DataType.STRING)
    @SerializedName("avatarZip")
    private String avatarZip;

    @ForeignCollectionField(eager = true, maxEagerLevel = 2)
    private ForeignCollection<Contact> contacts;

    public User() {
        super();
    }

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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public ForeignCollection<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ForeignCollection<Contact> contacts) {
        this.contacts = contacts;
    }
}
