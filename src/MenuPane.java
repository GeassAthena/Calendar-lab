import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.time.LocalDate;


//日历显示上方菜单部分封装类
public class MenuPane {
    private CalendarDate dateToday = DateUtil.getToday();
    private HBox menuHBox = new HBox();
    private HBox searchHBox = new HBox();
    private HBox yearAndMonthHBox = new HBox();
    private TextField searchTextField = new TextField();
    private DatePicker datePicker = new DatePicker();
    private HBox hBoxTop = new HBox();
    private Text yearText;
    private Text monthText;
    private Text todayText = new Text("今天: " + dateToday.getYear() + "-" + dateToday.getMonth() + "-" + dateToday.getDay());
    private Button searchButton = new Button("搜索日期");

    private Button lastYearButton = new Button("<");
    private Button nextYearButton = new Button(">");
    private Button lastMonthButton = new Button("<");
    private Button nextMonthButton = new Button(">");
    private Button todayButton = new Button("回到今天");
    private Button[] menuButtonArray = {searchButton, lastYearButton, nextYearButton, lastMonthButton, nextMonthButton, todayButton};

    public MenuPane() {
        yearText = new Text("" + dateToday.getYear());
        monthText = new Text("" + dateToday.getMonth());
        menuHBoxInitial();
    }

    //    菜单组件初始化并添加入菜单面板
    private void menuHBoxInitial() {
        datePicker.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(
                                        datePicker.getValue())
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };


        searchHBox.getChildren().addAll(datePicker, searchButton);
        searchHBox.setSpacing(10);
        searchHBox.setAlignment(Pos.CENTER);
        yearAndMonthHBox.getChildren().addAll(lastYearButton, yearText, nextYearButton, lastMonthButton, monthText,
                nextMonthButton);
        yearText.setTextAlignment(TextAlignment.CENTER);
        monthText.setTextAlignment(TextAlignment.CENTER);
        yearAndMonthHBox.setSpacing(10);
        yearAndMonthHBox.setAlignment(Pos.CENTER);
        menuHBox.getChildren().addAll(searchHBox, yearAndMonthHBox, todayButton, todayText);
        menuHBox.setSpacing(30);
        menuHBox.setAlignment(Pos.CENTER);
        menuHBox.setMaxHeight(80);
        menuHBox.setMinHeight(80);
        menuHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
    }

    public HBox getMenuHBox() {
        return menuHBox;
    }

    public Button[] getMenuButtonArray() {
        return menuButtonArray;
    }

    public Text getYearText() {
        return yearText;
    }

    public Text getMonthText() {
        return monthText;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }
}
