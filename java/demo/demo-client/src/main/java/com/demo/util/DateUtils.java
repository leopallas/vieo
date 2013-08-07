package com.demo.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {
    // private static Logger logger = Logger.getLogger(DateUtils.class);
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String getSystemTimePattern() {
        return "HH:mm";
    }

    public static Date parseDate(String str, String pattern) {
        if (str == null || str.trim().equals(""))
            return null;

        Date dt = null;
        DateFormat dtFmt = new SimpleDateFormat(pattern, Locale.US);
        try {
            new Date();
            dt = new Date(dtFmt.parse(str).getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
            // logger.error("Parser Exception: Invalid Date or pattern!");
        }
        return dt;
    }

    public static Date parseDate(Timestamp time) {
        if (time == null)
            return null;

        return new Date(time.getTime());
    }

    public static Timestamp parseTimestamp(String str, String pattern) {
        if (str == null || str.equals(""))
            return null;

        Timestamp dt = null;
        try {
            DateFormat dtFmt = new SimpleDateFormat(pattern, Locale.US);
            dt = new Timestamp(dtFmt.parse(str).getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
            // logger.error("Parser Exception: Invalid Timestamp or pattern!");
        }
        return dt;
    }

    public static Timestamp parseTimestamp(Date date) {
        if (date == null)
            return null;

        return new Timestamp(date.getTime());
    }

    public static String toTimeString(Date date) {
        String pattern = getSystemTimePattern();
        return toString(date, pattern);
    }

    public static String toTimeString(Timestamp timestamp) {
        String pattern = getSystemTimePattern();
        return toString(timestamp, pattern);
    }

    public static String toString(Date date, String pattern) {
        if (date == null)
            return "";
        DateFormat dtFmt = new SimpleDateFormat(pattern, Locale.US);
        return dtFmt.format(date);
    }

    public static String toString(Timestamp timestamp, String pattern) {
        if (timestamp == null)
            return "";
        DateFormat dtFmt = new SimpleDateFormat(pattern, Locale.US);
        return dtFmt.format(timestamp);
    }

    public static Date add(Date datetime, int datepart, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.add(datepart, number);
        return new Date(calendar.getTimeInMillis());
    }

    public static Timestamp add(Timestamp datetime, int datepart, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(datetime.getTime());
        calendar.add(datepart, number);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Date roll(Date datetime, int datepart, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.roll(datepart, number);
        return new Date(calendar.getTimeInMillis());
    }

    public static Timestamp roll(Timestamp datetime, int datepart, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(datetime.getTime());
        calendar.roll(datepart, number);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static long getDatetimeIntGap(Date headDate, Date tailDate, int datepart) throws Exception {
        long millisecond = tailDate.getTime() - headDate.getTime();
        long value = 0;

        switch (datepart) {
            case Calendar.MILLISECOND: {
                value = millisecond;
                break;
            }
            case Calendar.SECOND: {
                value = millisecond / 1000;
                break;
            }
            case Calendar.MINUTE: {
                value = millisecond / 1000 / 60;
                break;
            }
            case Calendar.HOUR: {
                value = millisecond / 1000 / 60 / 60;
                break;
            }
            case Calendar.DATE: {
                value = millisecond / 1000 / 60 / 60 / 24;
                break;
            }
            case Calendar.WEEK_OF_YEAR: {
                value = millisecond / 1000 / 60 / 60 / 24 / 7;
                break;
            }
            case Calendar.MONTH: {
                Calendar headCal = Calendar.getInstance();
                Calendar tailCal = Calendar.getInstance();
                headCal.setTime(headDate);
                tailCal.setTime(tailDate);
                value = (tailCal.get(Calendar.YEAR) - headCal.get(Calendar.YEAR)) * 12
                        + (tailCal.get(Calendar.MONTH) - headCal.get(Calendar.MONTH));
                break;
            }
            case Calendar.YEAR: {
                Calendar headCal = Calendar.getInstance();
                Calendar tailCal = Calendar.getInstance();
                headCal.setTime(headDate);
                tailCal.setTime(tailDate);
                value = tailCal.get(Calendar.YEAR) - headCal.get(Calendar.YEAR);
                break;
            }
            default: {
                throw new Exception();
            }
        }

        return value;
    }

    public static double getDatetimeFltGap(Date headDate, Date tailDate, int datepart) throws Exception {
        double millisecond = Double.longBitsToDouble(tailDate.getTime() - headDate.getTime());
        double value = 0;

        switch (datepart) {
            case Calendar.MILLISECOND: {
                value = millisecond;
                break;
            }
            case Calendar.SECOND: {
                value = millisecond / 1000;
                break;
            }
            case Calendar.MINUTE: {
                value = millisecond / 1000 / 60;
                break;
            }
            case Calendar.HOUR: {
                value = millisecond / 1000 / 60 / 60;
                break;
            }
            case Calendar.DATE: {
                value = millisecond / 1000 / 60 / 60 / 24;
                break;
            }
            case Calendar.WEEK_OF_YEAR: {
                value = millisecond / 1000 / 60 / 60 / 24 / 7;
                break;
            }
            case Calendar.MONTH: {
                value = millisecond / 1000 / 60 / 60 / 24 / 30.5;
                break;
            }
            case Calendar.YEAR: {
                value = millisecond / 1000 / 60 / 60 / 24 / 365;
                break;
            }
            default: {
                throw new Exception();
            }
        }

        return value;
    }

    public static int getDateTimePart(Date datetime, int datepart) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datetime.getTime());
        return cal.get(datepart);
    }

    public static void setDateTimePart(Date datetime, int datepart, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datetime.getTime());
        cal.set(datepart, value);
        datetime.setTime(cal.getTimeInMillis());
    }

    public static boolean isAfterToday(Date date) {
        if (date == null)
            return false;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        Calendar now = Calendar.getInstance();

        if (cal.get(Calendar.YEAR) > now.get(Calendar.YEAR)
                || (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) && cal.get(Calendar.DAY_OF_YEAR) > now
                        .get(Calendar.DAY_OF_YEAR))) {
            return true;
        } else
            return false;
    }

    public static boolean isAfterEqualToday(Date date, int dayIncrement) {
        if (date == null)
            return false;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        Calendar now = Calendar.getInstance();

        if (dayIncrement == 0) {
            if (cal.get(Calendar.YEAR) >= now.get(Calendar.YEAR)
                    || (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) && cal.get(Calendar.DAY_OF_YEAR) >= now
                            .get(Calendar.DAY_OF_YEAR))) {
                return true;
            } else {
                return false;
            }
        } else {
            if (cal.get(Calendar.YEAR) >= now.get(Calendar.YEAR)
                    || (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) && cal.get(Calendar.DAY_OF_YEAR) >= now
                            .get(Calendar.DAY_OF_YEAR + dayIncrement))) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean isBeforeToday(Date date) {
        if (date == null)
            return false;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        Calendar now = Calendar.getInstance();

        if (cal.get(Calendar.YEAR) < now.get(Calendar.YEAR)
                || (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) && cal.get(Calendar.DAY_OF_YEAR) < now
                        .get(Calendar.DAY_OF_YEAR))) {
            return true;
        } else
            return false;
    }

    public static boolean isBeforeEqualToday(Date date) {
        if (date == null)
            return false;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        Calendar now = Calendar.getInstance();

        if (cal.get(Calendar.YEAR) <= now.get(Calendar.YEAR)
                || (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) && cal.get(Calendar.DAY_OF_YEAR) <= now
                        .get(Calendar.DAY_OF_YEAR))) {
            return true;
        } else
            return false;
    }

    public static boolean isAfterNow(Timestamp time) {
        if (time == null)
            return false;

        if (time.after(new Timestamp(System.currentTimeMillis())))
            return true;
        else
            return false;
    }

    public static boolean isBeforeNow(Timestamp time) {
        if (time == null)
            return false;

        if (time.before(new Timestamp(System.currentTimeMillis())))
            return true;
        else
            return false;
    }

    public static boolean isBefore10Mins(Date time) {
        if (time == null)
            return false;

        if (new Timestamp(System.currentTimeMillis() - 600000).before(time))
            return true;
        else
            return false;
    }

    public static boolean isLeapYear(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(date.getTime());
        return cal.isLeapYear(cal.get(Calendar.YEAR));
    }

    public static long compare(Date headDate, Date tailDate) {
        return tailDate.getTime() - headDate.getTime();
    }

    public static Calendar addDate(int to) {
        Calendar strDate = Calendar.getInstance();
        strDate.add(Calendar.DATE, to);
        return strDate;
    }

    public static String getDurationDate(Date start, Date end) throws Exception {
        if (start == null || end == null || compare(start, end) < 0)
            return null;

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);

        int durationYear = 0;
        int durationMonth = 0;
        int durationDay = 0;

        // Compute Year Value
        startCalendar.add(Calendar.YEAR, 1);
        while (startCalendar.before(endCalendar) || SameDay(startCalendar, endCalendar)) {
            durationYear++;
            startCalendar.add(Calendar.YEAR, 1);
        }
        startCalendar.add(Calendar.YEAR, -1);

        // Compute Month Value
        startCalendar.add(Calendar.MONTH, 1);
        while (startCalendar.before(endCalendar) || SameDay(startCalendar, endCalendar)) {
            durationMonth++;
            startCalendar.add(Calendar.MONTH, 1);
        }
        startCalendar.add(Calendar.MONTH, -1);

        // Compute Day Value
        startCalendar.add(Calendar.DAY_OF_YEAR, 1);
        durationDay++;
        while (startCalendar.before(endCalendar) || SameDay(startCalendar, endCalendar)) {
            durationDay++;
            startCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        StringBuffer duration = new StringBuffer();
        duration.append(durationYear < 10 ? "0" + durationYear : "" + durationYear);
        duration.append(durationMonth < 10 ? "0" + durationMonth : "" + durationMonth);
        duration.append(durationDay < 10 ? "0" + durationDay : "" + durationDay);
        return duration.toString();
    }

    private static boolean SameDay(Calendar startCalendar, Calendar endCalendar) {
        if (startCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR)
                && startCalendar.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH)
                && startCalendar.get(Calendar.DAY_OF_MONTH) == endCalendar.get(Calendar.DAY_OF_MONTH)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDate(String str) {
        try {
            parseDate(str, DEFAULT_DATE_PATTERN);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Date getSuggestionDate(Date startDt, Date endDt) {
        long sdt = startDt.getTime();
        long edt = endDt.getTime();
        long incVal = Math.round((edt - sdt) / 2.00);

        long tmp = sdt + incVal;

        String tmpDt = DateUtils.toString(new Date(tmp), "yyyy-MM-dd HH:mm");

        Date date = DateUtils.parseDate(tmpDt, "yyyy-MM-dd HH:mm");

        return date;
    }
}
