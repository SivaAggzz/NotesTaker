package com.techneapps.notestaking.helper;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;
import android.view.View;

public class DeviceHelper {
    public static void vibrateDevice(View view) {
        Vibrator vibrator = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator == null) {
            // some manufacturers do not vibrate on long press
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, 80));
        } else {
            vibrator.vibrate(50);
        }

    }
}
