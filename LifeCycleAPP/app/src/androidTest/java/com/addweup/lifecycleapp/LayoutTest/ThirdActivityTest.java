package com.addweup.lifecycleapp.LayoutTest;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.addweup.lifecycleapp.ThirdActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by cdfq1 on 2016/12/22.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ThirdActivityTest {
    @Rule
    public ActivityTestRule<ThirdActivity> mActivityRule = new ActivityTestRule<>(ThirdActivity.class);

    @Test
    public void uiDisplay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
