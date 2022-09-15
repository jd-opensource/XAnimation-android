package com.jingdong.app.mall.bundle.xanimation;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void packageNameUnitTest() {
        System.out.println("----------packageNameUnitTest----------");
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals(appContext.getPackageName(), appContext.getPackageName());
    }
    @Test
    public void activityUnitTest() {
        System.out.println("----------activityUnitTest----------");
        assertEquals(true, MainActivity.isMainActivity());
    }

}