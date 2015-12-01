package com.findg.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.findg.data.model.*;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.sql.SQLException;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "ormlite.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {

            // create tables
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Friend.class);
            TableUtils.createTable(connectionSource, FriendGroup.class);
            TableUtils.createTable(connectionSource, Contact.class);

        } catch (Exception e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int arg2, int arg3) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");

            //drop all tables
            TableUtils.dropTable(connectionSource, Contact.class, true);
            TableUtils.dropTable(connectionSource, FriendGroup.class, true);
            TableUtils.dropTable(connectionSource, Friend.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);

            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);

        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }

    }

    public <C extends IOrmModel> List<C> getAllRecords(Class<C> model) {
        List<C> records = null;
        try {
            Dao<C, Integer> dao = this.getDao(model);
            records = dao.queryForAll();
        } catch (Exception e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        }
        return records;
    }

    public <C extends IOrmModel> boolean deleteAllRecords(Class<C> model) {
        List<C> list = getAllRecords(model);
        if (list != null && !list.isEmpty()) {
            try {
                Dao<C, Integer> dao = this.getDao(model);
                for (C item : list) {
                    dao.delete(item);
                }
            } catch (Exception e) {
                Log.e(TAG, ExceptionUtils.getStackTrace(e));
            }
        }
        return true;
    }

    public User createSelf(String sn) {
        User user = null;
        try {
            if (sn != null && !sn.isEmpty()) {
                //create self
                user = new User();
                user.setFirstLoad(true);
                user.setSn(sn);
                this.create(user);
                //create contact
                this.create(new Contact(user));
                user = this.queryTopByObject(User.class, "sn", sn);
            } else {
                throw new SQLException("error user sn!");
            }
        } catch (SQLException e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        }
        return user;
    }

    public User getTestFriend() {
        return getUser("28c2e7d0-f7a7-4e3d-9541-4dc71d2f68f0");
    }

    public User getSelf(String sn) {
        return getUser(sn);
    }

    private User getUser(String sn) {
        User user = null;
        try {
            if (sn != null && !sn.isEmpty()) {
                user = this.queryTopByObject(User.class, "sn", sn);
                if (user != null) {
                    if (user.isFirstLoad()) {
                        user.setFirstLoad(false);
                        this.update(user);
                    }
                } else {
                    user = createSelf(sn);
                }
            } else {
                throw new SQLException("error user sn!");
            }
        } catch (SQLException e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        }
        return user;
    }

    public <T extends IOrmModel> List<T> queryByObject(Class<T> clz, String field, Object foreignObject) {
        try {
            Dao dao = this.getDao(clz);
            QueryBuilder<User, Integer> builder = dao.queryBuilder();
            builder.orderBy("id", true);  // true for ascending, false for descending
            builder.where().eq(field, foreignObject);
            return dao.query(builder.prepare());  // returns list of ten items
        } catch (Exception e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public <T extends IOrmModel> T queryTopByObject(Class<T> clz, String field, Object foreignObject) {
        try {
            Dao dao = this.getDao(clz);
            QueryBuilder<User, Integer> builder = dao.queryBuilder();
            builder.limit(1l);
            builder.orderBy("id", true);  // true for ascending, false for descending
            builder.where().eq(field, foreignObject);
            List<T> list = dao.query(builder.prepare());  // returns list of ten items
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public boolean delete(IOrmModel model) {
        try {
            Dao dao = this.getDao(model.getClass());
            dao.delete(model);
            return true;
        } catch (Exception e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

    public IOrmModel create(IOrmModel model) {
        try {
            Dao dao = this.getDao(model.getClass());
            dao.create(model);
            return model;
        } catch (Exception e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public boolean update(IOrmModel model) {
        try {
            Dao dao = this.getDao(model.getClass());
            dao.update(model);
            return true;
        } catch (Exception e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

}
