package com.techneapps.notestaking;

import android.app.Activity;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.techneapps.notestaking.view.addnote.AddNewNoteActivity;
import com.techneapps.notestaking.view.settings.SettingsActivity;
import com.techneapps.notestaking.view.viewnote.allnotes.AllNotesViewerActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class FirstActivityTest {
    @Rule
    public ActivityTestRule<AllNotesViewerActivity> mActivityRule =
            new ActivityTestRule<>(AllNotesViewerActivity.class);

    @Test
    public void testAddNewNoteScenario() {
        Espresso.onView(ViewMatchers.withId(R.id.addNoteFab)).perform(ViewActions.click());
        Assert.assertEquals(getActivityInstance().getClass(), AddNewNoteActivity.class);
    }

    @Test
    public void testSettingsActivityOpeningScenario() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Espresso.onView(withText("Settings")).perform(ViewActions.click());
        Assert.assertEquals(getActivityInstance().getClass(), SettingsActivity.class);
    }


    private Activity getActivityInstance() {
        final Activity[] currentActivity = {null};
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Collection resumedActivities = ActivityLifecycleMonitorRegistry
                    .getInstance().getActivitiesInStage(Stage.RESUMED);
            if (resumedActivities.iterator().hasNext()) {
                currentActivity[0] = (Activity) resumedActivities.iterator().next();
            }
        });
        return currentActivity[0];
    }
}
