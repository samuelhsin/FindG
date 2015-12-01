package com.findg.data.model;

import com.findg.common.Consts;
import com.findg.data.dao.ContactDao;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contact", daoClass = ContactDao.class)
public class Contact implements IOrmModel {

    @DatabaseField(generatedId = true, dataType = DataType.LONG)
    @SerializedName("id")
    private long id;
    @DatabaseField(dataType = DataType.STRING)
    @SerializedName("name")
    private String name;
    @DatabaseField(canBeNull = false, foreign = true)
    @SerializedName("user")
    private User user;
    @ForeignCollectionField(eager = true, maxEagerLevel = 2)
    private ForeignCollection<FriendGroup> friendGroups;

    public Contact() {
        super();
    }

    public Contact(User user) {
        this();
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isUserExistInContact(User user) {
        boolean isExist = false;
        if (friendGroups != null && !friendGroups.isEmpty() && user != null) {
            Object[] objects = friendGroups.toArray();
            for (int i = 0, size = objects.length; i < size; i++) {
                FriendGroup friendGroup = (FriendGroup) objects[i];
                if (friendGroup != null && friendGroup.getFriendGroupType() == Consts.FriendGroupType.User && friendGroup.getFriends() != null && !friendGroup.getFriends().isEmpty()) {
                    Friend friend = (Friend) friendGroup.getFriends().toArray()[0];
                    if (friend != null && friend.getUser().getId() == user.getId()) {
                        isExist = true;
                        break;
                    }
                }
            }
        }
        return isExist;
    }

    public ForeignCollection<FriendGroup> getFriendGroups() {
        return friendGroups;
    }

    public void setFriendGroups(ForeignCollection<FriendGroup> friendGroups) {
        this.friendGroups = friendGroups;
    }
}