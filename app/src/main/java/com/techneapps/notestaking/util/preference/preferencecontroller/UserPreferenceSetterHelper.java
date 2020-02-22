package com.techneapps.notestaking.util.preference.preferencecontroller;

import android.content.Context;

import com.techneapps.notestaking.util.preference.prefwrapper.PreferenceHelper;

public class UserPreferenceSetterHelper {

    public static void setSaveOnExit(Context context, boolean value) {
        PreferenceHelper.setBoolean(context, "saveOnExit", value);
    }
}
