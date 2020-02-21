package com.techneapps.notestaking.helper;

import android.content.Context;

public class UserPreferenceGetterHelper {
    public static boolean isSaveOnExit(Context context) {
        return PreferenceHelper.getBoolean(context, "saveOnExit", false);
    }
}
