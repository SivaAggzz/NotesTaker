package com.techneapps.notestaking.helper;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class BindingAdapters {
    @BindingAdapter(value = "timeStamp")
    public static void setNoteDate(TextView textView, long timeStamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM YYYY, hh:mm a", Locale.getDefault());
        textView.setText(simpleDateFormat.format(timeStamp));
    }
}
