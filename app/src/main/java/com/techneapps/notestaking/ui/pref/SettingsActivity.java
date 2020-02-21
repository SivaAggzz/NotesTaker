package com.techneapps.notestaking.ui.pref;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.databinding.SettingsActivityBinding;
import com.techneapps.notestaking.helper.UserPreferenceGetterHelper;
import com.techneapps.notestaking.helper.UserPreferenceSetterHelper;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    SettingsActivityBinding settingsActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsActivityBinding = DataBindingUtil.setContentView(this, R.layout.settings_activity);
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settingsActivityBinding.enableSaveOnExit.addOnPrefClickListener(this::toggleEnableSaveOnExitPref);
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
            settingsActivityBinding.enableSaveOnExit.setChecked(false);
        } else {
            UserPreferenceSetterHelper.setSaveOnExit(this, true);
            settingsActivityBinding.enableSaveOnExit.setChecked(true);
        }
    }

    private void refreshPrefs() {
        settingsActivityBinding.enableSaveOnExit.setChecked(UserPreferenceGetterHelper.isSaveOnExit(this));
    }


}