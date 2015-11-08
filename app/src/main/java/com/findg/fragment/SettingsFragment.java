package com.findg.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.findg.R;
import com.findg.activity.ImageCropping;
import com.findg.common.AvatarPreference;
import com.findg.common.GZipUtils;
import com.findg.common.ImageUtils;
import com.findg.data.model.User;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Created by samuelhsin on 2015/10/25.
 */
public class SettingsFragment extends PreferenceFragment {

    private enum RequestCode {
        PICK_AVATAR_IMAGE
    }

    public SettingsFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_settings);

        bindEvents();

    }

    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {

        User user = this.getDatabaseHelper().getSelf();
        AvatarPreference prefAvatar = (AvatarPreference) findPreference("pref_avatar");
        if (user.getAvatar() != null) {
            Bitmap avatar = ImageUtils.decodeBytes(user.getAvatar());
            prefAvatar.setAvatar(avatar);
        } else {
            //avatarImage.setImageResource(R.drawable.question_faq_icon);
        }

        return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    }

    public void bindEvents() {

        final AvatarPreference prefAvatar = (AvatarPreference) findPreference("pref_avatar");
        prefAvatar.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                //Intent intent = new Intent();
                //intent.setType("image/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                Intent imageCroppingIntent = new Intent(getContext(), ImageCropping.class);
                getActivity().startActivityForResult(imageCroppingIntent, RequestCode.PICK_AVATAR_IMAGE.ordinal());
                return true;
            }
        });

        EditTextPreference prefNickname = (EditTextPreference) findPreference("pref_nickname");
        if (prefNickname.getText() != null && !prefNickname.getText().isEmpty()) {
            prefNickname.setSummary(prefNickname.getText());
        }
        prefNickname.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue != null) {
                    String nickname = (String) newValue;
                    if (!nickname.isEmpty()) {
                        int end = nickname.indexOf('\n');
                        if (end < 0) {
                            end = nickname.length();
                        }
                        nickname = nickname.substring(0, end);
                        preference.setSummary(nickname);
                    } else {
                        preference.setSummary("display name");
                    }
                } else {
                    preference.setSummary("display name");
                }
                return true;
            }
        });

        EditTextPreference prefDescription = (EditTextPreference) findPreference("pref_description");
        if (prefDescription.getText() != null && !prefDescription.getText().isEmpty()) {
            prefDescription.setSummary(displayFirstLine(prefDescription.getText()));
        }
        prefDescription.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue != null) {
                    preference.setSummary(displayFirstLine((String) newValue));
                } else {
                    preference.setSummary("write something for yourself");
                }
                return true;
            }
        });

        SwitchPreference prefUnit = (SwitchPreference) findPreference("pref_unit");
        if (prefUnit.isChecked()) {
            prefUnit.setSummary("CM/KG");
        } else {
            prefUnit.setSummary("Inches/Pounds");
        }
        prefUnit.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue.equals(Boolean.TRUE)) {
                    preference.setSummary("CM/KG");
                } else {
                    preference.setSummary("Inches/Pounds");
                }
                return true;
            }
        });

        final ListPreference prefRace = (ListPreference) findPreference("pref_race");
        if (prefRace.getValue() != null && !prefRace.getValue().equals("unknown")) {
            prefRace.setSummary(prefRace.getEntry());
        } else {
            prefRace.setSummary("unknown");
        }
        prefRace.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int entryIndex = prefRace.findIndexOfValue((String) newValue);
                String[] entryArray = getResources().getStringArray(R.array.race_entries);
                preference.setSummary(entryArray[entryIndex]);
                return true;
            }
        });

    }

    private String displayFirstLine(String context) {
        String result = null;

        if (context != null) {
            if (!context.isEmpty()) {
                int end = context.indexOf('\n');
                if (end < 0) {
                    end = context.length();
                }
                result = context.substring(0, end);
            } else {
                result = "";
            }
        }

        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == Activity.RESULT_CANCELED) {
            // action cancelled
        } else if (resultCode == Activity.RESULT_OK) {
            if (requestCode < RequestCode.values().length) {
                switch (RequestCode.values()[requestCode]) {
                    case PICK_AVATAR_IMAGE:
                        if (intent != null) {

                            try {
                                Uri croppedUri = intent.getParcelableExtra("croppedUri");
                                Bitmap croppedBitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), croppedUri);
                                byte[] croppedBitmapBytes = ImageUtils.encodeBytes(croppedBitmap);
                                User user = this.getDatabaseHelper().getSelf();
                                user.setAvatar(croppedBitmapBytes);
                                user.setAvatarZip(GZipUtils.compressToStr(croppedBitmapBytes));
                                this.getDatabaseHelper().update(user);
                                AvatarPreference prefAvatar = (AvatarPreference) findPreference("pref_avatar");
                                prefAvatar.setAvatar(croppedBitmap);
                            } catch (Exception e) {
                                Log.e(TAG, ExceptionUtils.getStackTrace(e));
                            }

                        }
                        break;
                }
            }
        }
    }

}
