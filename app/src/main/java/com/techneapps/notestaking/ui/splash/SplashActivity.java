package com.techneapps.notestaking.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.techneapps.notestaking.ui.allnotesviewer.AllNotesViewerActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //layout is loaded by styles

        //load the first activity to start after splash screen
        Intent intent = new Intent(this, AllNotesViewerActivity.class);
        startActivity(intent);
        finish();
    }

}