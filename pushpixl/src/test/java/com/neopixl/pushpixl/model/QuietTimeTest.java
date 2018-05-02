package com.neopixl.pushpixl.model;

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
        int startMinute = 12;
        int endHour = 7;
        int endMinute = 12;

        QuietTime quietTimeRequest = new QuietTime(startHour, startMinute, endHour, endMinute);

        //assertEquals("22:12-04:00", quietTimeRequest.getStartTime());
        //assertEquals("07:12-04:00", quietTimeRequest.getEndTime());
    }

    @Test
    public void testQuietTimeEquality() {
        int startHour = 23;
        int startMinute = 0;
        int endHour = 7;
        int endMinute = 0;

        int startHour2 = 22;

        QuietTime quietTimeRequest1 = new QuietTime(startHour, startMinute, endHour, endMinute);
        QuietTime quietTimeRequest2 = new QuietTime(startHour, startMinute, endHour, endMinute);
        QuietTime quietTimeRequest3 = new QuietTime(startHour2, startMinute, endHour, endMinute);

        assertEquals(quietTimeRequest1, quietTimeRequest2);
        assertNotSame(quietTimeRequest1, quietTimeRequest3);
        assertNotSame(quietTimeRequest2, quietTimeRequest3);
    }
}
