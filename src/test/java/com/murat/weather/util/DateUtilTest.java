package com.murat.weather.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateUtilTest {

    @Test
    public void asDateTest() {
        final String date = "2018-11-08 12:15:54";

        final Date converted = DateUtil.asDate(date);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(converted);

        assertEquals(12, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(54, cal.get(Calendar.SECOND));
        //adding one since calendar get month returns indexed months
        //starting from 0
        assertEquals(11, cal.get(Calendar.MONTH)+1);
    }
}
