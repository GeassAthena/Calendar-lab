/*
* This class provides some utils that may help you to finish this lab.
* getToday() is finished, you can use this method to get the current date.
* The other four methods getDaysInMonth(), isValid(), isFormatted() and isLeapYear() are not finished,
* you should implement them before you use.
*
* */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    /**
     * get a CalendarDate instance point to today
     *
     * @return a CalendarDate object
     */
    public static CalendarDate getToday() {
        Calendar calendar = Calendar.getInstance();
        return new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * get all dates in the same month with given date
     *
     * @param date the given date
     * @return a list of days in a whole month
     */
    public static List<CalendarDate> getDaysInMonth(CalendarDate date) {
        if (date == null || !isValid(date)) {
            return null;
        }
        List<CalendarDate> calendarDateList = new ArrayList<CalendarDate>();
        int year = date.getYear();
        int month = date.getMonth();
        for (int i = 0; i < getTotalDayOfMonth(month, year); i++) {
            calendarDateList.add(new CalendarDate(year, month, i + 1));
        }
        return calendarDateList;
    }

    /**
     * Judge whether the input date is valid. For example, 2018-2-31 is not valid
     *
     * @param date the input date
     * @return true if the date is valid, false if the date is not valid.
     */
    public static boolean isValid(CalendarDate date) {
        if (date == null) {
            return false;
        }
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1) {
            return false;
        }
        if (day > getTotalDayOfMonth(month, year)) {
            return false;
        }
        return true;
    }

    //获取这个月份的总天数
    public static int getTotalDayOfMonth(int month, int year) {
        if (month < 1 || month > 12) {
            return 0;
        }
        if (isBigMonth(month)) {
            return 31;
        } else if (isSmallMonth(month)) {
            return 30;
        } else if (isLeapYear(year)) {
            return 29;
        } else {
            return 28;
        }
    }

    //判断是否为大月
    public static boolean isBigMonth(int month) {
        int[] monthArray = {1, 3, 5, 7, 8, 10, 12};
        for (int i = 0; i < monthArray.length; i++) {
            if (month == monthArray[i]) {
                return true;
            }
        }
        return false;
    }

    //判断是否为小月
    public static boolean isSmallMonth(int month) {
        int[] monthArray = {4, 6, 9, 11};
        for (int i = 0; i < monthArray.length; i++) {
            if (month == monthArray[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Judge whether the input is formatted.
     * For example, 2018/2/1 is not valid and 2018-2-1 is valid.
     *
     * @param dateString
     * @return true if the input is formatted, false if the input is not formatted.
     */
    public static boolean isFormatted(String dateString) {
        String pattern = "^\\d{1,4}-\\d{1,2}-\\d{1,2}$";
        return dateString != null && dateString.matches(pattern);
    }

    /**
     * Judge whether the input year is a leap year or not.
     * For example, year 2000 is a leap year, and 1900 is not.
     *
     * @param year
     * @return true if the input year is a leap year, false if the input is not.
     */
    public static boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }

    public static boolean before(Date a, Date b) {
        if (a == null || b == null) {
            return false;
        }
        long timeA = a.getTime() / 1000;
        long timeB = b.getTime() / 1000;
        return timeA < timeB;
    }

    public static boolean after(Date a, Date b) {
        if (a == null || b == null) {
            return false;
        }
        long timeA = a.getTime() / 1000;
        long timeB = b.getTime() / 1000;
        return timeA > timeB;
    }

}

