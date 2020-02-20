package com.techneapps.notestaking.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.techneapps.notestaking.R;

import java.util.Objects;

public class MustMethods {
    public static Dialog showBeautifiedDialog(Context context, String body) {
        Dialog beautifiedDialog = new Dialog(context, R.style.AppThemeDark_Dialog);
        beautifiedDialog.setContentView(R.layout.beautified_dialog);
        TextView text = beautifiedDialog.findViewById(R.id.text);
        text.setText(body);

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
}
