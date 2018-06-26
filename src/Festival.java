/**
 * Created by 黄文浦 on 2018/5/11.
 */
public class Festival {
    private String name;
    private String zhName;
    private CalendarDate festivalDate;
    private CalendarDate startDate;
    private CalendarDate endDate;

    public Festival(String name, String zhName, String holidayTime, String startTime, String endTime) {
        this.name = name;
        this.zhName = zhName;
        try {
            this.festivalDate = new CalendarDate(holidayTime);
            this.startDate = new CalendarDate(startTime);
            this.endDate = new CalendarDate(endTime);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public CalendarDate getFestivalDate() {
        return festivalDate;
    }

    public void setFestivalDate(CalendarDate festivalDate) {
        this.festivalDate = festivalDate;
    }

    public CalendarDate getStartDate() {
        return startDate;
    }

    public void setStartDate(CalendarDate startDate) {
        this.startDate = startDate;
    }

    public CalendarDate getEndDate() {
        return endDate;
    }

    public void setEndDate(CalendarDate endDate) {
        this.endDate = endDate;
    }

    public boolean isFestival(CalendarDate date) {
        return this.festivalDate.equals(date);
    }

    public boolean isDuringDates(CalendarDate date) {
        return (this.startDate.compareTo(date) <= 0)
                && (this.endDate.compareTo(date) >= 0);
    }
}
