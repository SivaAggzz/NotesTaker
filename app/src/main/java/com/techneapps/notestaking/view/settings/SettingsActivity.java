package com.techneapps.notestaking.view.settings;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.databinding.ActivitySettingsBinding;
import com.techneapps.notestaking.util.preference.preferencecontroller.UserPreferenceGetterHelper;
import com.techneapps.notestaking.util.preference.preferencecontroller.UserPreferenceSetterHelper;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding activitySettingsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activitySettingsBinding.enableSaveOnExit.addOnPrefClickListener(this::toggleEnableSaveOnExitPref);
        refreshPrefs();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //if the back button in toolbar is Selected
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void toggleEnableSaveOnExitPref() {
        if (UserPreferenceGetterHelper.isSaveOnExit(this)) {
            UserPreferenceSetterHelper.setSaveOnExit(this, false);
            activitySettingsBinding.enableSaveOnExit.setChecked(false);
        } else {
            UserPreferenceSetterHelper.setSaveOnExit(this, true);
            activitySettingsBinding.enableSaveOnExit.setChecked(true);
        }
    }

    private void refreshPrefs() {
        activitySettingsBinding.enableSaveOnExit.setChecked(UserPreferenceGetterHelper.isSaveOnExit(this));
    }


}