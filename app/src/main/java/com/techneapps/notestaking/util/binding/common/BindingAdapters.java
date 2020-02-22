package com.techneapps.notestaking.util.binding.common;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class BindingAdapters {
    //BindingAdapters are data binding methods used by views to show data

    @BindingAdapter(value = "timeStamp")
    public static void setNoteDate(TextView textView, long timeStamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM YYYY, hh:mm a", Locale.getDefault());
        textView.setText(simpleDateFormat.format(timeStamp));
    }
}
