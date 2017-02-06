package com.addweup.mvptest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void showDialog() throws Throwable{
        final MainActivity activity = mActivityRule.getActivity();
        mActivityRule.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.showError();
                    }
                }
        );
        Thread.sleep(1000);
    }

    @Test
    public void showDialog2() throws Throwable{
        final MainActivity activity = mActivityRule.getActivity();
        mActivityRule.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.showNormal();
                    }
                }
        );
        Thread.sleep(1000);
    }
}
