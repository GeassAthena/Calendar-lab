import static java.lang.Integer.parseInt;

/**
 * We have finished part of this class yet, you should finish the rest.
 * 1. A constructor that can return a CalendarDate object through the given string.
 * 2. A method named getDayOfWeek() that can get the index of a day in a week.
 */
public class CalendarDate {
    private int year;
    private int month;
    private int day;

    public CalendarDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * a constructor that can return a CalendarDate object through the given string.
     *
     * @param dateString format: 2018-3-18
     */
    public CalendarDate(String dateString) throws DataFormatException {
        if (DateUtil.isFormatted(dateString)) {
            String[] dateArray = dateString.split("-");
            year = parseInt(dateArray[0]);
            month = parseInt(dateArray[1]);
            day = parseInt(dateArray[2]);
        } else {
            throw new DataFormatException();
        }
    }

    public int getYear() {
        return year;
    }
//    public void setYear(int year) {
//        this.year = year;
//    }

    public int getMonth() {
        return month;
    }

//    public void setMonth(int month) {
//        this.month = month;
//    }

    public int getDay() {
        return day;
    }

//    public void setDay(int day) {
//        this.day = day;
//    }

    /**
     * Get index of the day in a week for one date.
     * <p>
     * Don't use the existing implement like Calendar.setTime(),
     * try to implement your own algorithm.
     *
     * @return 1-7, 1 stands for Monday and 7 stands for Sunday
     */
    public int getDayOfWeek() {
        int result = -1;
        int year = this.year;
        int month = this.month;
        int day = this.day;
        if (DateUtil.isValid(this)) {
            if (1 == month || 2 == month) {
                month += 12;
                year--;
            }
            int c = year / 100;
            int y = year % 100;
            int W = c / 4 - 2 * c + y + y / 4 + 26 * (month + 1) / 10 + day - 1;
            if (W < 0) {
                result = (W + (Math.abs(W) / 7 + 1) * 7) % 7;
            } else {
                result = W % 7;
            }
            if (result == 0) {
                result += 7;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        int year = ((CalendarDate) obj).getYear();
        int month = ((CalendarDate) obj).getMonth();
        int day = ((CalendarDate) obj).getDay();
        return this.getYear() == year && this.getMonth() == month && this.getDay() == day;
    }

    public int compareTo(CalendarDate date) {
        if (this.year > date.getYear()) {
            return 1;
        } else if (this.year < date.getYear()) {
            return -1;
        } else if (this.month > date.getMonth()) {
            return 1;
        } else if (this.month < date.getMonth()) {
            return -1;
        } else if (this.day > date.getDay()) {
            return 1;
        } else if (this.day < date.getDay()) {
            return -1;
        } else {
            return 0;
        }
    }
}
