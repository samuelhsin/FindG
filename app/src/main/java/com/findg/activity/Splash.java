package com.findg.activity;

import android.content.Intent;
import android.os.Bundle;
import com.findg.R;

/**
 * Created by samuelhsin on 2015/10/24.
 */
public class Splash extends BaseActivity {

    /**
     * The thread to process splash screen events
     */
    private Thread splashThread;

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
                    startActivity(new Intent("com.findg.activity.Login"));//call by intent name
                }
            }
        };
        splashThread.start();

    }

}
