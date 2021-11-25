package com.example.areyoukittenme;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.action.ViewActions;

import org.junit.Test;

public class FirstActivityTest {
    @Test
    public void startButton(){
        Instrumentation mInstrumentation = getInstrumentation();
        Instrumentation.ActivityMonitor monitor = mInstrumentation.addMonitor(FirstActivity.class.getName(), null, false);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("score", 200);
        intent.setClassName(mInstrumentation.getTargetContext(), FirstActivity.class.getName());
        mInstrumentation.startActivitySync(intent);

        mInstrumentation.removeMonitor(monitor);
        monitor = mInstrumentation.addMonitor(MazeActivity.class.getName(), null, false);
        onView(withId(R.id.startLevel)).perform(ViewActions.click());
        Activity currentActivity = getInstrumentation().waitForMonitor(monitor);

        assertEquals(MazeActivity.class, currentActivity.getClass());
    }
}