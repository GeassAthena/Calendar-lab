import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.RunnableFuture;

/*
* You need to implement Calendar GUI here!
* show the calendar of month of today.
* jump to last/next month's calendar
* jump to last/next year's calendar
*
* jump to one specific day's calendar
* */
public class Display extends Application {
    private MenuPane menuPane;
    private ToDoItemMenuPane toDoItemMenuPane;
    private static CenterCalendarPane centerCalendarPane;
    private CalendarDate calendarDateToday = DateUtil.getToday();
    private static CalendarDate calendarDateNowDisplay = DateUtil.getToday();
    private HBox menuHBox = new HBox();
    private HBox toDoHBox = new HBox();
    private GridPane calendarGridPane = new GridPane();
    private BorderPane borderPane = new BorderPane();
    private Button[] menuButtonArray;
    private TextField searchTextField;
    private static Text yearText;
    private static Text monthText;

    public Display() throws Exception {
    }

    public Display(String[] args) throws Exception {
        launch(args);
    }

    //日历显示开始函数，调用其他实例，获取 Pane，并生成UI界面
    @Override
    public void start(Stage primaryStage) throws Exception {

        new ToDoListUtil();
        ItemPersistence.inPutItems();
        menuPane = new MenuPane();
        centerCalendarPane = new CenterCalendarPane();
        toDoItemMenuPane = new ToDoItemMenuPane();
        controlsInitial();
        buttonSetAction();
        remind();
        paintDays(calendarDateToday);
        borderPane.setTop(menuHBox);
        borderPane.setCenter(calendarGridPane);
        borderPane.setBottom(toDoHBox);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    toDoItemMenuPane.refresh();
                });
            }
        }, 0, 10000);
        primaryStage.setScene(new Scene(borderPane, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Calendar");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        primaryStage.show();
    }

    //提醒：
    private void remind() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                //提醒：
                Date date = new Date();
                ArrayList<ToDoItemNode> arrayList = new ArrayList(ToDoListUtil.getToDoItemList());
                for (int i = 0; i < arrayList.size(); i++) {
                    ToDoItemNode toDoItemNode = arrayList.get(i);
//                    System.out.println(toDoItemNode.getRemindTime() + ";" + toDoItemNode.getRemindInterval() + ";" + toDoItemNode.getItemDetail() + ";" +
//                            toDoItemNode.getDateFrom() + ";" + toDoItemNode.getRemindWay() + ";" + toDoItemNode.getDateEnd() + ";" + date);
                    if (toDoItemNode.getRemindInterval() != 0) {
//                        System.out.println("test1");
                        if (toDoItemNode.getRemindTime().after(toDoItemNode.getDateFrom())) {
                            toDoItemNode.setRemindInteral(0);
//                            System.out.println("test2");
                        } else if (Math.abs(toDoItemNode.getRemindTime().getTime() - date.getTime()) <= 10000) {
                            if (toDoItemNode.getRemindWay() == 1) {
                                JOptionPane.showMessageDialog(null, toDoItemNode.getItemDetail(), "提醒", JOptionPane.ERROR_MESSAGE);
                                toDoItemNode.setRemindTime(AddRemidaDate(toDoItemNode.getRemindTime(), toDoItemNode.getRemindInterval()));
//                                System.out.println("test3");
                            } else if (toDoItemNode.getRemindWay() == 2) {
                                String string = toDoItemNode.getItemDetail();
                                ToDoListUtil.setRemindList(string);
                                toDoItemNode.setRemindTime(AddRemidaDate(toDoItemNode.getRemindTime(), toDoItemNode.getRemindInterval()));
//                                System.out.println("test4");
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }, 0, 10000);//1000为一秒
    }

    //初始化 UI 组件
    private void controlsInitial() {
        calendarGridPane = centerCalendarPane.getCalendarGridPane();
        menuHBox = menuPane.getMenuHBox();
        menuButtonArray = menuPane.getMenuButtonArray();
        searchTextField = menuPane.getSearchTextField();
        yearText = menuPane.getYearText();
        monthText = menuPane.getMonthText();
        toDoHBox = toDoItemMenuPane.getToDoHox();
    }

    /**
     * Init the UI Windows here. For example, the frame, some panels and buttons.
     */
//    private static void init(){}

    /**
     * paint the days of whole current month on the frame with the given CalendarDate
     *
     * @param date a valid CalendarDate param.
     */
    private static void paintDays(CalendarDate date) {
        if (date == null) {
            return;
        }
        calendarDateNowDisplay = date;
        centerCalendarPane.paintDays(date);
    }

    //为组件设置动作
    private void buttonSetAction() {
        menuButtonArray[0].setOnAction(event -> {
            try {
                searchButtonAction();
            } catch (DataFormatException e) {
                new ErrorScene("unFormat");
            }
        });
        menuButtonArray[1].setOnAction(event -> lastOrNextYearButtonAction("last"));
        menuButtonArray[2].setOnAction(event -> lastOrNextYearButtonAction("next"));
        menuButtonArray[3].setOnAction(event -> lastOrNextMonthButtonAction("last"));
        menuButtonArray[4].setOnAction(event -> lastOrNextMonthButtonAction("next"));
        menuButtonArray[5].setOnAction(event -> todayButtonAction());
    }

    //搜索按钮动作函数
    private void searchButtonAction() throws DataFormatException {
        String inputString = searchTextField.getText();
        searchTextField.setText("");
        CalendarDate calendarDateInput = new CalendarDate(inputString);
        if (!DateUtil.isValid(calendarDateInput)) {
            new ErrorScene("inValid");
            return;
        }
        if (calendarDateInput.getYear() < 1800 || calendarDateInput.getYear() > 2300) {
            new ErrorScene("notInRange");
            return;
        }
        calendarDateNowDisplay = calendarDateInput;
        prePaintDays();
    }

    //年份切换按钮动作函数
    private void lastOrNextYearButtonAction(String type) {
        int year = calendarDateNowDisplay.getYear();
        int month = calendarDateNowDisplay.getMonth();
        int day = calendarDateNowDisplay.getDay();
        if ((year <= 1800 && type.equals("last")) || (year >= 2300 && type.equals("next"))) {
            new ErrorScene("notInRange");
            return;
        }
        if (type.equals("next")) {
            year++;
        } else {
            year--;
        }
        calendarDateNowDisplay = new CalendarDate(year, month, 1);
        prePaintDays();
    }

    //月份切换按钮动作函数
    private void lastOrNextMonthButtonAction(String type) {
        int year = calendarDateNowDisplay.getYear();
        int month = calendarDateNowDisplay.getMonth();
        int day = calendarDateNowDisplay.getDay();
        boolean lastBoolean = type.equals("last") && month == 1 && year <= 1800;
        boolean nextBoolean = type.equals("next") && month == 12 && year >= 2300;
        if (lastBoolean || nextBoolean) {
            new ErrorScene("notInRange");
            return;
        }
        if (type.equals("last")) {
            if (month == 1) {
                month = 12;
                year--;
            } else {
                month--;
            }
        } else {
            if (month == 12) {
                month = 1;
                year++;
            } else {
                month++;
            }
        }
        calendarDateNowDisplay = new CalendarDate(year, month, 1);
        prePaintDays();
    }

    // 回到今天按钮动作函数
    private void todayButtonAction() {
        calendarDateNowDisplay = DateUtil.getToday();
        prePaintDays();
    }

    // 调用paintDay函数前设置显示的年份和月份
    public static void prePaintDays() {
        yearText.setText(calendarDateNowDisplay.getYear() + "");
        monthText.setText(calendarDateNowDisplay.getMonth() + "");
        paintDays(calendarDateNowDisplay);
    }

    public Date getRemidaDate(Date date, long minute) {
        long sum = date.getTime();
        long time = sum - minute;
        return new Date(time);
    }

    public Date AddRemidaDate(Date date, long minute) {
        long sum = date.getTime();
        long time = sum + minute;
        return new Date(time);
    }
}
