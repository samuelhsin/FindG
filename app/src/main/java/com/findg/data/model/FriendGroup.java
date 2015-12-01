package com.findg.data.model;

import com.findg.common.Consts;
import com.findg.data.dao.FriendGroupDao;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by samuelhsin on 2015/11/10.
 */
@DatabaseTable(tableName = "friendGroup", daoClass = FriendGroupDao.class)
public class FriendGroup implements IOrmModel {

    @DatabaseField(generatedId = true, dataType = DataType.LONG)
    @SerializedName("id")
    private long id;
    @DatabaseField(dataType = DataType.STRING)
    @SerializedName("name")
    private String name;
    @DatabaseField(dataType = DataType.ENUM_STRING)
    @SerializedName("friendGroupType")
    private Consts.FriendGroupType friendGroupType;
    @DatabaseField(canBeNull = false, foreign = true)
    @SerializedName("contact")
    private Contact contact;
    @ForeignCollectionField(eager = true, maxEagerLevel = 2)
    private ForeignCollection<Friend> friends;

    public FriendGroup() {
        super();
    }

    public FriendGroup(Contact contact) {
        this();
        this.contact = contact;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Consts.FriendGroupType getFriendGroupType() {
        return friendGroupType;
    }

    public void setFriendGroupType(Consts.FriendGroupType friendGroupType) {
        this.friendGroupType = friendGroupType;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public ForeignCollection<Friend> getFriends() {
        return friends;
    }

    public void setFriends(ForeignCollection<Friend> friends) {
        this.friends = friends;
    }
}
