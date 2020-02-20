package com.techneapps.notestaking.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.techneapps.notestaking.ui.allnotesviewer.AllNotesViewerActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, AllNotesViewerActivity.class);
        startActivity(intent);
        finish();
    }

}