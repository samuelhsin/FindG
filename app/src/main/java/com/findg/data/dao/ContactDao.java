package com.findg.data.dao;

import com.findg.data.model.Contact;
import com.findg.data.model.FriendGroup;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by samuelhsin on 2015/11/9.
 */
public class ContactDao extends BaseDaoImpl<Contact, Integer> implements IDao {
    private final Dao<FriendGroup, Integer> friendGroupDao;

    public ContactDao(ConnectionSource connectionSource, Class<Contact> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        friendGroupDao = DaoManager.createDao(connectionSource, FriendGroup.class);
    }

    @Override
    public int delete(Contact contact) throws SQLException {
        if (contact!=null && contact.getFriendGroups() != null && !contact.getFriendGroups().isEmpty()) {
            friendGroupDao.delete(contact.getFriendGroups());
        }
        return super.delete(contact);
    }

}
