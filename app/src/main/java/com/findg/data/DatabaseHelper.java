package com.findg.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.findg.data.model.IOrmModel;
import com.findg.data.model.User;
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

        } catch (Exception e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int arg2, int arg3) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");

            //drop all tables
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

    public User getSelf() {
        User user = null;
        Dao<User, Integer> userDao;
        try {
            userDao = this.getDao(User.class);
            //userDao.setObjectCache(true);
            QueryBuilder<User, Integer> builder = userDao.queryBuilder();
            builder.limit(1l);
            builder.orderBy("id", true);  // true for ascending, false for descending
            List<User> users = userDao.query(builder.prepare());  // returns list of ten items
            if (users != null && !users.isEmpty()) {
                // just one user
                user = users.get(0);
                if (user.isFirstLoad()) {
                    user.setFirstLoad(false);
                    userDao.update(user);
                }
            } else {
                user = new User();
                user.setFirstLoad(true);
                userDao.create(user);
            }
        } catch (SQLException e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        } finally {
            //if (userDao != null) {
            //userDao.clearObjectCache();
            //}
            //this.close();
        }
        return user;
    }

    public boolean update(IOrmModel model) {
        try {
            Dao dao = this.getDao(model.getClass());
            dao.update(model);
            return true;
        } catch (Exception e) {
            Log.e(TAG, ExceptionUtils.getStackTrace(e));
        } finally {
            //this.close();
        }
        return false;
    }

}
