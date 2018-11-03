package com.murat.weather.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j
@UtilityClass
public class DateUtil {

    private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int MORNING_HOUR = 6;
    private int ZERO = 0;

    public Date asDate(final String dateString) {
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            log.warn("Cannot parse : {}", dateString, e);
        }
        return null;
    }

    public Date nextSixAM() {

        final Date now = new Date();

        final Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, MORNING_HOUR);
        c.set(Calendar.MINUTE, ZERO);
        c.set(Calendar.SECOND, ZERO);
        c.set(Calendar.MILLISECOND, ZERO);
        final Date todaySixAm = c.getTime();

        if (now.before(todaySixAm)) {
            return todaySixAm;
        } else {
            return addDays(todaySixAm, 1);
        }
    }

    public Date lastSixAM() {
        final Date nextSixAM = nextSixAM();
        return addDays(nextSixAM, 3);
    }

    public Date nextDayStart() {

        final Date now = new Date();

        final Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, ZERO);
        c.set(Calendar.MINUTE, ZERO);
        c.set(Calendar.SECOND, ZERO);
        c.set(Calendar.MILLISECOND, ZERO);
        final Date todaySixAm = c.getTime();

        if (now.before(todaySixAm)) {
            return todaySixAm;
        } else {
            return addDays(todaySixAm, 1);
        }
    }

    public Date lastDayStart() {
        final Date nextDayStart = nextDayStart();
        return addDays(nextDayStart, 3);
    }

    public Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
