package com.findg.fragment;

import android.support.v4.app.Fragment;
import com.findg.data.DatabaseHelper;

/**
 * Created by samuelhsin on 2015/10/30.
 */
public abstract class BaseFragment extends Fragment {

    private DatabaseHelper databaseHelper;

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public void setDatabaseHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
}
