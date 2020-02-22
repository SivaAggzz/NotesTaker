package com.techneapps.notestaking.util.preference.preferencecontroller;

import android.content.Context;

import com.techneapps.notestaking.util.preference.prefwrapper.PreferenceHelper;

public class UserPreferenceGetterHelper {
    public static boolean isSaveOnExit(Context context) {
        return PreferenceHelper.getBoolean(context, "saveOnExit", false);
    }
}
