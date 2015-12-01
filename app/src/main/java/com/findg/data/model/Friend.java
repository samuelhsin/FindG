package com.findg.data.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by samuelhsin on 2015/11/10.
 */
@DatabaseTable(tableName = "friend")
public class Friend implements IOrmModel {

    @DatabaseField(generatedId = true, dataType = DataType.LONG)
    @SerializedName("id")
    private long id;
    @DatabaseField(dataType = DataType.STRING)
    @SerializedName("name")
    private String name;
    @DatabaseField(canBeNull = false, foreign = true)
    @SerializedName("friendGroup")
    private FriendGroup friendGroup;
    @DatabaseField(canBeNull = false, foreign = true)
    @SerializedName("user")
    private User user;

    public Friend() {
        super();
    }

    public Friend(FriendGroup friendGroup, User user) {
        this();
        this.friendGroup = friendGroup;
        this.user = user;
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

    public FriendGroup getFriendGroup() {
        return friendGroup;
    }

    public void setFriendGroup(FriendGroup friendGroup) {
        this.friendGroup = friendGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
