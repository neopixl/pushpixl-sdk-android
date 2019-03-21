package com.neopixl.pushpixl.model;

import com.neopixl.pushpixl.exception.IncorrectConfigurationException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class QuietTime {

    private Date mStartTime;
    private Date mEndTime;

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return mStartTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return mEndTime;
    }

    /**
     * Returns a new QuietTimeRequest object initialized with startTime (startHour:startMinute + Time Zone) and endTime(endHour:endMinute + TimeZone).
     * The time used is the one set in the smartphone.
     * <p>
     * This method always returns immediately, whether or not the
     * image exists. When this applet attempts to draw the image on
     * the screen, the data will be loaded. The graphics primitives
     * that draw the image will incrementally paint on the screen.
     *
     * @param  startHour  	the start hour used for the quiet time
     * @param  startMinute 	the start minute used for the quiet time
     * @param  endHour 		the end hour used for the quiet time
     * @param  endMinute 	the end minute used for the quiet time
     * @return     	 		a new QuietTimeRequest object initialized with startTime(startHour+startMinue), endTime (endHour+endMinute) and the current time zone set on the smartphone
     * @see                QuietTime
     */

    public QuietTime(int startHour, int startMinute, int endHour, int endMinute) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        if(startHour<0 || startHour>=24) {
            throw new IncorrectConfigurationException("startHour value should should be equal or greater than 0 and less than 24.");
        }

        if(startMinute<0 || startMinute>=60) {
            throw new IncorrectConfigurationException("startMinute value should be equal or greater than 0 and less than 60.");
        }

        if(endHour<0 || endHour>=24) {
            throw  new IncorrectConfigurationException("endHour value should should be equal or greater than 0 and less than 24.");
        }

        if(endMinute<0 || endMinute>=60) {
            throw new IncorrectConfigurationException("endMinute value should be equal or greater than 0 and less than 60.");
        }

        int year = gregorianCalendar.get(Calendar.YEAR);
        int month = gregorianCalendar.get(Calendar.MONTH);
        int day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);

        GregorianCalendar startCalendar = new GregorianCalendar(year, month, day, startHour, startMinute, 00);
        GregorianCalendar endCalendar = new GregorianCalendar(year, month, day, endHour, endMinute, 00);

        mStartTime = startCalendar.getTime();
        mEndTime = endCalendar.getTime();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof QuietTime) {
            QuietTime otherQuietTimeRequest = (QuietTime)o;

            return mStartTime.equals(otherQuietTimeRequest.getStartTime()) && mEndTime.equals(otherQuietTimeRequest.getEndTime());
        }

        return false;
    }

    @Override
    public String toString() {
        return mStartTime+"<>"+mEndTime;
    }

}
