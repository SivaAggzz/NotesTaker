package com.techneapps.notestaking.util.preference.preferencecontroller;

import android.content.Context;

import com.techneapps.notestaking.util.preference.prefwrapper.PreferenceHelper;

public class UserPreferenceSetterHelper {
    private Context context;

    public UserPreferenceSetterHelper(Context context) {
        this.context = context;
    }

    public void setSaveOnExit(boolean value) {
        PreferenceHelper.setBoolean(context, "saveOnExit", value);
    }
}
