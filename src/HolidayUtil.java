import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileNotFoundException;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by 黄文浦 on 2018/5/11.
 */
public class HolidayUtil {
    private static ArrayList<Festival> festivals = new ArrayList<>();
    private static ArrayList<CalendarDate> workdays = new ArrayList<>();
    private static volatile HolidayUtil instance;

    public HolidayUtil() {
        init();
    }

    public static HolidayUtil getInstance() {
        if (instance == null)
            instance = new HolidayUtil();
        return instance;
    }
    public void init() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./holiday.json"));
            String content = "";
            String temp = null;
            try {
                while ((temp = reader.readLine()) != null)
                    content += temp;
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = new JSONObject(content);
            String year = jsonObject.getString("year");
            JSONArray holidayMsg = jsonObject.getJSONArray("holiday");
            initHolidays(holidayMsg);
            JSONArray workdayMsg = jsonObject.getJSONArray("workday");
            initWorkdays(workdayMsg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initWorkdays(JSONArray workdayMsg) {
        for (int i = 0; i < workdayMsg.length(); i++) {
            try {
                String string = String.valueOf(workdayMsg.get(i));
                workdays.add(new CalendarDate(string));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void initHolidays(JSONArray holidayMsg) {
        for (int i = 0; i < holidayMsg.length(); i++) {
            try {
                JSONObject object = (JSONObject) holidayMsg.get(i);
                festivals.add(new Festival(object.getString("name"), object.getString("zh_name"), object.getString("holiday_time")
                        , object.getString("start_time"), object.getString("end_time")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String isFestival(CalendarDate date) {
        for (Festival festival : festivals) {
            if (festival.isFestival(date)) {
                return festival.getZhName();
            }
        }
        return null;
    }

    public String isRest(CalendarDate date) {
        for (Festival festival : festivals) {
            if (festival.isDuringDates(date)) {
                return "休";
            }
        }
        return null;
    }

    public String isWorkday(CalendarDate date) {
        for (int i = 0; i < workdays.size(); i++) {
            if (workdays.get(i).equals(date)) {
                return "班";
            }
        }
        return null;
    }


    public boolean isOrdinaryDay(CalendarDate date) {
        return isFestival(date) == null && isWorkday(date) == null && isRest(date) == null;
    }
}
