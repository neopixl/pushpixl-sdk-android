package com.neopixl.pushpixl.core.domain;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

/**
 * Created by Florian ALONSO on 4/27/18.
 * For Neopixl
 */
public class QuietTimeTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testQuietTime() {
        int startHour = 22;
        int startMinute = 0;
        int endHour = 7;
        int endMinute = 0;

        QuietTime quietTime = new QuietTime(startHour, startMinute, endHour, endMinute);

        assertEquals(quietTime.getStartTime(), "22:00-04:00");
        assertEquals(quietTime.getEndTime(), "07:00-04:00");
    }

    @Test
    public void testQuietTimeEquality() {
        int startHour = 23;
        int startMinute = 0;
        int endHour = 7;
        int endMinute = 0;

        int startHour2 = 22;

        QuietTime quietTime1 = new QuietTime(startHour, startMinute, endHour, endMinute);
        QuietTime quietTime2 = new QuietTime(startHour, startMinute, endHour, endMinute);
        QuietTime quietTime3 = new QuietTime(startHour2, startMinute, endHour, endMinute);

        assertEquals(quietTime1, quietTime2);
        assertNotSame(quietTime1, quietTime3);
        assertNotSame(quietTime2, quietTime3);
    }
}
