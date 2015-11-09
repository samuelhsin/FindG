package com.findg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Window;
import com.findg.App;
import com.findg.R;
import com.findg.common.ProgressDialog;

public abstract class BaseActivity extends Activity {

    public static final int DOUBLE_BACK_DELAY = 2000;

    protected final ProgressDialog progress;
    protected App app;
    protected boolean useDoubleBackPressed;
    protected boolean isNeedShowToastAboutDisconnected;
    private boolean doubleBackToExitPressedOnce;

    public BaseActivity() {
        progress = ProgressDialog.newInstance(R.string.msgWaitPlease);
    }

    public synchronized void showProgress() {
        if (!progress.isAdded()) {
            progress.show(getFragmentManager(), null);
        }
    }

    public synchronized void hideProgress() {
        if (progress != null && progress.getActivity() != null) {
            progress.dismissAllowingStateLoss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        app = App.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isNeedShowToastAboutDisconnected = true;
    }

    @Override
    protected void onStop() {
        isNeedShowToastAboutDisconnected = false;
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce || !useDoubleBackPressed) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, DOUBLE_BACK_DELAY);
    }

    protected void navigateToParent() {
        Intent intent = NavUtils.getParentActivityIntent(this);
        if (intent == null) {
            finish();
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T _findViewById(int viewId) {
        return (T) findViewById(viewId);
    }

    protected void onFailAction(String action) {
    }

    protected void onSuccessAction(String action) {

    }

}