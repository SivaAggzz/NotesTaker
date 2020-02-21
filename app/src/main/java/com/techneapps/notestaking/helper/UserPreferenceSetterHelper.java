package com.techneapps.notestaking.helper;

import android.content.Context;

public class UserPreferenceSetterHelper {

    public static void setSaveOnExit(Context context, boolean value) {
        PreferenceHelper.setBoolean(context, "saveOnExit", value);
    }
}
