package com.jingdong.app.mall.bundle.xanimation;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void unit_add() {
        System.out.println("---------unit_add-------");
        assertEquals(4, UnitTestUtils.add(2, 2));
    }

    @Test
    public void unit_sub() {
        System.out.println("---------unit_sub-------");
        assertEquals(0, UnitTestUtils.sub(2, 2));
    }
}