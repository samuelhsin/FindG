package com.findg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import com.findg.App;
import com.findg.R;
import com.findg.common.ProgressDialog;
import com.findg.common.ViewPagerAdapter;

/**
 * Created by samuelhsin on 2015/11/8.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {

    public static final int DOUBLE_BACK_DELAY = 2000;

    protected final ProgressDialog progress;
    protected App app;
    protected boolean useDoubleBackPressed;
    protected Fragment currentFragment;
    protected boolean isNeedShowTostAboutDisconnected;
    private boolean doubleBackToExitPressedOnce;

    protected ViewPager viewPager;

    public BaseFragmentActivity() {
        progress = ProgressDialog.newInstance(R.string.msgWaitPlease);
    }

    protected abstract void setupViewPager(ViewPager viewPager);

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
        isNeedShowTostAboutDisconnected = true;
    }

    @Override
    protected void onStop() {
        isNeedShowTostAboutDisconnected = false;
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

    protected void setCurrentFragment(int position) {
        if (viewPager != null) {
            ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
            currentFragment = adapter.getItem(position);
            viewPager.setCurrentItem(position);
        }
    }

    protected void setCurrentFragment(Fragment fragment) {

        if (viewPager != null) {
            ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();

            int index = adapter.getIndex(fragment);
            if (index >= 0) {
                currentFragment = adapter.getItem(index);
            }

            if (currentFragment == null) {
                currentFragment = adapter.getItem(index);
            } else {
                // getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //FragmentTransaction transaction = buildTransaction();
                //transaction.replace(currentFragment.getId(), fragment);
                // transaction.commit();
                viewPager.setCurrentItem(index);
                currentFragment = fragment;
            }
        }

    }

    private FragmentTransaction buildTransaction() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        return transaction;
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
