package com.vinfai.hotmovie;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

/**
 * Created by 12045 on 2017/2/5.
 */

public class SettingsActivity extends PreferenceActivity implements
        Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        调用保存设置选项的XML文件
        addPreferencesFromResource(R.xml.preferences);

//        调用自定义的bindPreferenceSummaryToValue()方法绑定设置选项的key
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_language_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_movieSort_key)));
    }

    private void bindPreferenceSummaryToValue(@NonNull Preference preference) {
//        设置监听器，监听设置选项是否变更
        preference.setOnPreferenceChangeListener(this);

//        发现选项变更，立即将preference中的value进行对应的变更
        onPreferenceChange(preference, PreferenceManager
                .getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, @NonNull Object value) {

        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue);
        }
        return true;
    }
}
