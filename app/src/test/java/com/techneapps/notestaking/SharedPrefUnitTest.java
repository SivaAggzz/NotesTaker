package com.techneapps.notestaking;

import android.os.Build;

import androidx.test.platform.app.InstrumentationRegistry;

import com.techneapps.notestaking.util.preference.preferencecontroller.UserPreferenceGetterHelper;
import com.techneapps.notestaking.util.preference.preferencecontroller.UserPreferenceSetterHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class SharedPrefUnitTest {
    private UserPreferenceSetterHelper userPreferenceSetterHelper;
    private UserPreferenceGetterHelper userPreferenceGetterHelper;

    @Before
    public void setUp() {
        userPreferenceSetterHelper = new UserPreferenceSetterHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        userPreferenceGetterHelper = new UserPreferenceGetterHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @Test
    public void is_save_on_exit_true() {
        userPreferenceSetterHelper.setSaveOnExit(true);
        assertTrue(userPreferenceGetterHelper.isSaveOnExit());
    }

    @Test
    public void is_save_on_exit_false() {
        userPreferenceSetterHelper.setSaveOnExit(false);
        assertFalse(userPreferenceGetterHelper.isSaveOnExit());
    }


}
