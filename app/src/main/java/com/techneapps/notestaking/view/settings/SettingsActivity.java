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
    private UserPreferenceGetterHelper userPreferenceGetterHelper;
    private UserPreferenceSetterHelper userPreferenceSetterHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        initializeView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //color NavigationBarColor to match UI
        getWindow().setNavigationBarColor(getResources().getColor(R.color.md_grey_900));
    }

    private void initializeView() {
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activitySettingsBinding.enableSaveOnExit.addOnPrefClickListener(this::toggleEnableSaveOnExitPref);
        userPreferenceGetterHelper = new UserPreferenceGetterHelper(this);
        userPreferenceSetterHelper = new UserPreferenceSetterHelper(this);
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
        if (userPreferenceGetterHelper.isSaveOnExit()) {
            userPreferenceSetterHelper.setSaveOnExit(false);
            activitySettingsBinding.enableSaveOnExit.setChecked(false);
        } else {
            userPreferenceSetterHelper.setSaveOnExit(true);
            activitySettingsBinding.enableSaveOnExit.setChecked(true);
        }
    }

    private void refreshPrefs() {
        activitySettingsBinding.enableSaveOnExit.setChecked(userPreferenceGetterHelper.isSaveOnExit());
    }


}