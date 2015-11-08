package com.findg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.findg.R;
import com.findg.common.ViewPagerAdapter;
import com.findg.data.DatabaseHelper;
import com.findg.fragment.*;
import com.firebase.client.Firebase;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Created by samuelhsin on 2015/10/24.
 */
public class Main extends AppCompatActivity {

    private static final String TAG = Main.class.getSimpleName();

    private DatabaseHelper databaseHelper = null;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private enum Tab {
        HOME(R.drawable.ic_home_white_24dp),
        CHAT(R.drawable.ic_message_white_24dp),
        NEARBY(R.drawable.ic_location_on_white_24dp),
        CONTACT(R.drawable.ic_tab_contacts),
        SETTINGS(R.drawable.ic_settings_white_24dp);

        private int icon = -1;

        Tab(int icon) {
            this.icon = icon;
        }

        public int getIcon() {
            return icon;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = getDBHelper();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        Firebase.setAndroidContext(this);

    }

    private void setupTabIcons() {
        if (tabLayout != null) {
            try {
                tabLayout.getTabAt(Tab.HOME.ordinal()).setIcon(Tab.HOME.getIcon());
                tabLayout.getTabAt(Tab.CHAT.ordinal()).setIcon(Tab.CHAT.getIcon());
                tabLayout.getTabAt(Tab.NEARBY.ordinal()).setIcon(Tab.NEARBY.getIcon());
                tabLayout.getTabAt(Tab.CONTACT.ordinal()).setIcon(Tab.CONTACT.getIcon());
                tabLayout.getTabAt(Tab.SETTINGS.ordinal()).setIcon(Tab.SETTINGS.getIcon());
            } catch (Exception e) {
                Log.e(TAG, ExceptionUtils.getStackTrace(e));
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("tabPosition", Tab.HOME.ordinal());
        homeFragment.setDatabaseHelper(databaseHelper);
        adapter.addFrag(homeFragment, "Home");

        ChatFragment chatFragment = new ChatFragment();
        args = new Bundle();
        args.putInt("tabPosition", Tab.CHAT.ordinal());
        chatFragment.setDatabaseHelper(databaseHelper);
        adapter.addFrag(chatFragment, "Chat");

        NearbyFragment nearbyFragment = new NearbyFragment();
        args = new Bundle();
        args.putInt("tabPosition", Tab.NEARBY.ordinal());
        nearbyFragment.setDatabaseHelper(databaseHelper);
        adapter.addFrag(nearbyFragment, "Nearby");

        ContactFragment contactFragment = new ContactFragment();
        args = new Bundle();
        args.putInt("tabPosition", Tab.CONTACT.ordinal());
        contactFragment.setDatabaseHelper(databaseHelper);
        adapter.addFrag(contactFragment, "Contact");

        SettingsFragment settingsFragment = new SettingsFragment();
        args = new Bundle();
        args.putInt("tabPosition", Tab.SETTINGS.ordinal());
        settingsFragment.setDatabaseHelper(databaseHelper);
        adapter.addFrag(settingsFragment, "Settings");

        viewPager.setAdapter(adapter);
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

    private DatabaseHelper getDBHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SettingsFragment settingsFragment = (SettingsFragment) ((ViewPagerAdapter) viewPager.getAdapter()).getItem(Tab.SETTINGS.ordinal());
        settingsFragment.onActivityResult(requestCode, resultCode, data);
    }

}
