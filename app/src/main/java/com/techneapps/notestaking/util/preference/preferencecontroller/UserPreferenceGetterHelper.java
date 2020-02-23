package com.techneapps.notestaking.util.preference.preferencecontroller;

import android.content.Context;

import com.techneapps.notestaking.util.preference.prefwrapper.PreferenceHelper;

public class UserPreferenceGetterHelper {
    private Context context;

    public UserPreferenceGetterHelper(Context context) {
        this.context = context;
    }

    public boolean isSaveOnExit() {
        return PreferenceHelper.getBoolean(context, "saveOnExit", false);
    }
}
