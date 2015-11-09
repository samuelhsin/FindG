package com.findg.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.findg.App;
import com.findg.activity.BaseFragmentActivity;
import com.findg.data.DatabaseHelper;

/**
 * Created by samuelhsin on 2015/10/30.
 */
public abstract class BaseFragment extends Fragment {

    protected App app;
    protected BaseFragmentActivity baseFragmentActivity;
    protected String title;

    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseFragmentActivity = (BaseFragmentActivity) getActivity();
        app = App.getInstance();
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public void setDatabaseHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    protected BaseFragmentActivity getBaseFragmentActivity() {
        return (BaseFragmentActivity) getActivity();
    }

}
