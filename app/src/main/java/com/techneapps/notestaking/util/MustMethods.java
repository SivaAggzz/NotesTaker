package com.techneapps.notestaking.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.database.models.NoteObj;

import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MustMethods {
    public static Dialog showBeautifiedDialog(Context context, String body, String title) {
        Dialog beautifiedDialog = new Dialog(context, R.style.AppThemeDark_Dialog);
        beautifiedDialog.setContentView(R.layout.beautified_dialog);
        TextView bodyText = beautifiedDialog.findViewById(R.id.text);
        bodyText.setText(body);

        TextView titleText = beautifiedDialog.findViewById(R.id.title);
        titleText.setText(title);


        Button cancelBtn = beautifiedDialog.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(view -> beautifiedDialog.dismiss());
        Objects.requireNonNull(beautifiedDialog.getWindow()).setLayout(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
        beautifiedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        beautifiedDialog.getWindow().setGravity(Gravity.CENTER);
        return beautifiedDialog;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void startActivityWithExtra(Context context, NoteObj noteObj, Class className) {
        Intent newActivityIntentWithExtra = new Intent(context, className);
        newActivityIntentWithExtra.setFlags(FLAG_ACTIVITY_NEW_TASK);
        newActivityIntentWithExtra.putExtra("savedNote", noteObj);
        context.startActivity(newActivityIntentWithExtra);
    }

    public static void startActivity(Context context, Class className) {
        Intent newActivityIntent = new Intent(context, className);
        newActivityIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newActivityIntent);
    }

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
