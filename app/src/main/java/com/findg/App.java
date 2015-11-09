package com.findg;

import android.app.Application;
import android.content.Context;
import com.findg.media.MediaPlayerManager;

public class App extends Application {

    private static App instance;
    private MediaPlayerManager soundPlayer;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
    }

    private void initImageLoader(Context context) {

    }

    public MediaPlayerManager getMediaPlayer() {
        return soundPlayer;
    }

    private void initApplication() {
        instance = this;
    }
}