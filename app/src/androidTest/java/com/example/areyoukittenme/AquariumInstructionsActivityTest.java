package com.example.areyoukittenme;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.test.espresso.action.ViewActions;

import org.junit.Test;
public class AquariumInstructionsActivityTest {
    @Test
    public void startButton(){

        Instrumentation mInstrumentation = getInstrumentation();
        Instrumentation.ActivityMonitor monitor = mInstrumentation.addMonitor(AquariumInstructionsActivity.class.getName(), null, false);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(mInstrumentation.getTargetContext(), AquariumInstructionsActivity.class.getName());
        mInstrumentation.startActivitySync(intent);

        monitor = mInstrumentation.addMonitor(AquariumActivity.class.getName(), null, false);
        onView(withId(R.id.startLevel)).perform(ViewActions.click());
        Activity currentActivity = getInstrumentation().waitForMonitor(monitor);

        assertEquals(AquariumActivity.class, currentActivity.getClass());
    }
}
