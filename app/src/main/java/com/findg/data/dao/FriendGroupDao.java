package com.findg.data.dao;

import com.findg.data.model.Friend;
import com.findg.data.model.FriendGroup;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by samuelhsin on 2015/11/9.
 */
public class FriendGroupDao extends BaseDaoImpl<FriendGroup, Integer> implements IDao {
    private final Dao<Friend, Integer> friendDao;

    public FriendGroupDao(ConnectionSource connectionSource, Class<FriendGroup> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        friendDao = DaoManager.createDao(connectionSource, Friend.class);
    }

    @Override
    public int delete(FriendGroup friendGroup) throws SQLException {
        if (friendGroup != null && friendGroup.getFriends() != null && !friendGroup.getFriends().isEmpty()) {
            friendDao.delete(friendGroup.getFriends());
        }
        return super.delete(friendGroup);
    }

}
