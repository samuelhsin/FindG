package com.findg.data.dao;

import com.findg.data.model.Contact;
import com.findg.data.model.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by samuelhsin on 2015/11/9.
 */
public class UserDao extends BaseDaoImpl<User, Integer> implements IDao {
    private final Dao<Contact, Integer> contactDao;

    public UserDao(ConnectionSource connectionSource, Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        contactDao = DaoManager.createDao(connectionSource, Contact.class);

    }

    @Override
    public int delete(User user) throws SQLException {
        if (user.getContacts() != null) {
            contactDao.delete(user.getContacts());
        }
        return super.delete(user);
    }

}
