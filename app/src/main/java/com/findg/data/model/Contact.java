package com.findg.data.model;

import com.findg.data.dao.ContactDao;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "contact", daoClass = ContactDao.class)
public class Contact implements IOrmModel {

    @DatabaseField(generatedId = true, dataType = DataType.LONG)
    @SerializedName("id")
    private long id;
    @DatabaseField(dataType = DataType.STRING)
    @SerializedName("name")
    private String name;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    @SerializedName("userList")
    private transient List<FriendGroup> friendGroups;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    @SerializedName("allCreatedFriendGroupsInThisContact")
    private transient List<FriendGroup> allCreatedFriendGroupsInThisContact = new ArrayList<>();

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

    public void addUserList(List<FriendGroup> friendGroups) {
        this.friendGroups.addAll(friendGroups);
    }

    public void removeFriendsFromList(List<FriendGroup> friendGroups) {
        this.friendGroups.removeAll(friendGroups);
    }

    public List<FriendGroup> getFriendGroups() {
        return friendGroups;
    }

    public void setFriendGroups(List<FriendGroup> friendGroups) {
        this.friendGroups = friendGroups;
    }

    public List<FriendGroup> getAllCreatedFriendGroupsInThisContact() {
        return allCreatedFriendGroupsInThisContact;
    }

    public void setAllCreatedFriendGroupsInThisContact(List<FriendGroup> allCreatedFriendGroupsInThisContact) {
        this.allCreatedFriendGroupsInThisContact = allCreatedFriendGroupsInThisContact;
    }
}