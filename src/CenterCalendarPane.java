import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

//日历显示面板中日历部分封装类
public class CenterCalendarPane {
    private GridPane calendarGridPane = new GridPane();
    private CalendarDate calendarDateToday = DateUtil.getToday();
    private Button[] calendarButtonArray = new Button[49];
    private CalendarDate calendarDateDisplayNow;

    public CenterCalendarPane() {
        calendarGridPaneInitial();
    }

    //初始化日历的 pane，设置pane中的按钮以及设置星期显示 ，并将 gridPane中的全部按钮存入数组，方面后面更改日期
    private void calendarGridPaneInitial() {
        int count = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Button button = new Button(count + "");
                button.setFont(Font.font("", FontWeight.BOLD, FontPosture.ITALIC, 15));
                button.setMaxSize(115, 60);
                button.setMinSize(115, 60);
                button.setOnAction(event -> {
                    if (button.getText().equals("")) {
                        doNothing();
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        String str = button.getText().split(" ")[0];
                        calendar.set(calendarDateDisplayNow.getYear(), calendarDateDisplayNow.getMonth() - 1, parseInt(str), 0, 0, 0);
                        Date dateFrom = calendar.getTime();
                        calendar.set(calendarDateDisplayNow.getYear(), calendarDateDisplayNow.getMonth() - 1, parseInt(str), 23, 59, 59);
                        Date dateEnd = calendar.getTime();
                        new AddAndSearchByDayPane(dateFrom, dateEnd);
                    }
                });
                calendarButtonArray[count] = button;
                count++;
                calendarGridPane.add(button, j, i);
            }
        }
        String[] weekString = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        for (int i = 0; i < 7; i++) {
            calendarButtonArray[i].setText(weekString[i]);
            calendarButtonArray[i].setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
            calendarButtonArray[i].setDisable(true);
        }
    }

    public GridPane getCalendarGridPane() {
        return calendarGridPane;
    }

    //根据输入的 CalendarDate 实例，绘制日历，设置高亮，此处的实例为合法实例
    public void paintDays(CalendarDate date) {
        cleanCalendarGridPane();
        calendarDateDisplayNow = date;
        int firstDayOfWeek = new CalendarDate(date.getYear(), date.getMonth(), 1).getDayOfWeek();
        int totalDayOfMonth = DateUtil.getTotalDayOfMonth(date.getMonth(), date.getYear());
        Calendar calendar = Calendar.getInstance();
        Date dateFrom;
        Date dateEnd;

        for (int i = 0, j = firstDayOfWeek + 6; i < totalDayOfMonth; i++, j++) {
            List<CalendarDate> calendarDateList = DateUtil.getDaysInMonth(date);
            String festival = HolidayUtil.getInstance().isFestival(calendarDateList.get(i));
            String rest = HolidayUtil.getInstance().isRest(calendarDateList.get(i));
            String workday = HolidayUtil.getInstance().isWorkday(calendarDateList.get(i));
            boolean ordinaryDay = HolidayUtil.getInstance().isOrdinaryDay(calendarDateList.get(i));

            if (rest!=null){
                calendarButtonArray[j].setText(i + 1 + " "+ "\n" + rest);
                calendarButtonArray[j].setStyle("-fx-text-fill:red");
            }
            if (festival!=null){
                calendarButtonArray[j].setText(i + 1 + " "+ "\n" + festival);
                calendarButtonArray[j].setStyle("-fx-text-fill:red");
            }
            if (workday!=null){
                calendarButtonArray[j].setText(i + 1 + " "+ "\n" + workday);
                calendarButtonArray[j].setStyle("-fx-text-fill:yellow");
            }
            if (ordinaryDay){
                calendarButtonArray[j].setText(i + 1 + " ");
            }
            calendar.set(date.getYear(), date.getMonth() - 1, i + 1, 0, 0, 0);
            dateFrom = calendar.getTime();
            calendar.set(date.getYear(), date.getMonth() - 1, i + 1, 23, 59, 59);
            dateEnd = calendar.getTime();
            if (ToDoListUtil.search(dateFrom, dateEnd) != null) {
                calendarButtonArray[j].setStyle("-fx-base:lightblue");
            }
            if (i + 1 == calendarDateToday.getDay() && date.getYear() == calendarDateToday.getYear() && date.getMonth() == calendarDateToday.getMonth()) {
                calendarButtonArray[j].setStyle("-fx-base:green");
            }
        }
    }

    public void cleanCalendarGridPane() {
        for (int i = 7; i < 49; i++) {
            calendarButtonArray[i].setText("");
            calendarButtonArray[i].setStyle("-fx-base:rgb(255,255,255)");
        }
    }

    private void doNothing() {
    }
}
