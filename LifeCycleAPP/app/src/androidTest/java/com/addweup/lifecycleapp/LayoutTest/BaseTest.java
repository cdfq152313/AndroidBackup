package com.addweup.lifecycleapp.LayoutTest;

import android.app.Activity;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by cdfq1 on 2016/12/22.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BaseTest<BaseActivity extends Activity> {

    public void initalize(Class<BaseActivity> clazz){
        mActivityRule = new ActivityTestRule<>(clazz);
    }

    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule;

    @Test
    public void uiDisplay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}