package com.findg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.findg.R;
import com.findg.data.DatabaseHelper;
import com.findg.data.model.User;
import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by samuelhsin on 2015/10/24.
 */
public class Splash extends BaseActivity {

    /**
     * The thread to process splash screen events
     */
    private Thread splashThread;

    private DatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 5000) {
                        sleep(100);
                        waited += 100;
                    }
                } catch (Exception e) {
                    // do nothing
                } finally {
                    finish();
                    User user = getDBHelper().getSelf();
                    startActivity(new Intent("com.findg.activity.Login"));//call by intent name
                }
            }
        };
        splashThread.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null && databaseHelper.isOpen()) {
            try {
                databaseHelper.close();
                OpenHelperManager.releaseHelper();
                databaseHelper = null;
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (databaseHelper != null && databaseHelper.isOpen()) {
            try {
                databaseHelper.close();
                OpenHelperManager.releaseHelper();
                databaseHelper = null;
            } catch (Exception e) {
            }
        }
    }

    private DatabaseHelper getDBHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

}
