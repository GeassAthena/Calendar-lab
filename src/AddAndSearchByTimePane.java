import exception.DataErrorException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

////按时间添加和查询事件的面板，点击时间段按钮后展现
public class AddAndSearchByTimePane {
    private DatePicker datePickerFrom = new DatePicker();
    private DatePicker datePickerEnd = new DatePicker();
    private ComboBox comboBoxHourFrom = new ComboBox();
    private ComboBox comboBoxMinuteFrom = new ComboBox();
    private ComboBox comboBoxHourEnd = new ComboBox();
    private ComboBox comboBoxMinuteEnd = new ComboBox();
    private RadioButton urgent = new RadioButton("紧急");
    private RadioButton important = new RadioButton("重要");
    private ItemManager itemManager = ItemManager.getInstance();

    ToDoItemNode toDoItemNode = new ToDoItemNode();
    String type = "";

    private Stage stage = new Stage();
    private BorderPane borderPane = new BorderPane();
    private HBox hBoxTopFrom = new HBox();
    private HBox hBoxTopEnd = new HBox();
    private VBox vBoxTop = new VBox();
    private HBox hBoxCenter = new HBox();
    private HBox hBoxBottom = new HBox();

    private Button closeButton = new Button("关闭");
    private Button searchButton = new Button("查询");
    private Button addButton = new Button("添加");
    private Text textFrom = new Text("开始时间");
    private Text textEnd = new Text("结束时间");
    private Text textDetail = new Text("详情或备注");
    private TextArea itemDetail = new TextArea();
    private Text textCompany = new Text("公司");
    private TextField companyDetail = new TextField();
    private Text textPlace = new Text("地点");
    private TextField placeDetail = new TextField();
    private Text textJob = new Text("职业");
    private TextField jobDetail = new TextField();
    private Text textPerson = new Text("对象");
    private TextField personDetail = new TextField();
    private Text textTheme = new Text("主题");
    private TextField themeDetail = new TextField();
    private Text textType = new Text("类型");
    private Text textWay = new Text("交通方式");
    private ComboBox cb = new ComboBox();
    private Text textFlight = new Text("航班车次");
    private TextField flightDetail = new TextField();
    private RadioButton doing = new RadioButton("设置时间");
    private Text textCourse = new Text("课程");
    private TextField courseDetail = new TextField();
    private Text textContent = new Text("内容");
    private TextArea contentDetail = new TextArea();
    private Text textTeacher = new Text("教师");
    private TextField teacherDetail = new TextField();
    private Text textContinued = new Text("持续周数");
    private TextField continuedDetail = new TextField();
    private ComboBox cb2 = new ComboBox();
    private ComboBox cb3 = new ComboBox();

    private ToDoItemNode parent = null;

    //      构造函数，调用时初始化组件，并展示面板
    AddAndSearchByTimePane(String type) {
        this.type = type;
        initialControl(type);
        if (type.equals("search")) {
            initialBySearch();
        } else if (type.equals("anniversary")) {
            initialByAnniversary();
        } else if (type.equals("appoint")) {
            initialByAppoint();
        } else if (type.equals("class")) {
            initialByClass();
        } else if (type.equals("interview")) {
            initialByInterview();
        } else if (type.equals("meeting")) {
            initialByMeeting();
        } else if (type.equals("travel")) {
            initialByTravel();
        } else if (type.equals("other")) {
            initialByOther();
        }


        setAction();
        stage.setScene(new Scene(borderPane));
        stage.setResizable(false);
        stage.show();
    }

    public void setParent(ToDoItemNode parent) {
        this.parent = parent;
    }

    //    初始化函数，初始化控件
    private void initialControl(String type) {
        itemDetail.setPrefRowCount(3);
        String text;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                text = "" + 0 + i;
            } else {
                text = "" + i;
            }
            comboBoxHourFrom.getItems().add(text);
            comboBoxHourEnd.getItems().add(text);
        }
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                text = "" + 0 + i;
            } else {
                text = "" + i;
            }
            comboBoxMinuteFrom.getItems().add(text);
            comboBoxMinuteEnd.getItems().add(text);
        }
        comboBoxHourFrom.getSelectionModel().select(0);
        comboBoxHourEnd.getSelectionModel().select(23);
        comboBoxMinuteFrom.getSelectionModel().select(0);
        comboBoxMinuteEnd.getSelectionModel().select(59);

        datePickerFrom.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(
                                        datePickerFrom.getValue())
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };

        itemDetail.setPrefColumnCount(2);

        datePickerEnd.setDayCellFactory(dayCellFactory);
        datePickerEnd.setValue(datePickerFrom.getValue());
        if (type == "search") {
            hBoxTopFrom.getChildren().addAll(textFrom, datePickerFrom, comboBoxHourFrom, comboBoxMinuteFrom);
            hBoxTopEnd.getChildren().addAll(textEnd, datePickerEnd, comboBoxHourEnd, comboBoxMinuteEnd);
        } else {
            hBoxTopFrom.getChildren().addAll(textFrom, datePickerFrom, comboBoxHourFrom, comboBoxMinuteFrom, urgent);
            hBoxTopEnd.getChildren().addAll(textEnd, datePickerEnd, comboBoxHourEnd, comboBoxMinuteEnd, important);
        }
        hBoxTopFrom.setMinHeight(100);
        hBoxTopFrom.setMaxHeight(100);
        hBoxTopFrom.setSpacing(30);
        hBoxTopFrom.setAlignment(Pos.CENTER);
        hBoxTopEnd.setMaxHeight(100);
        hBoxTopEnd.setMinHeight(100);
        hBoxTopEnd.setSpacing(30);
        hBoxTopEnd.setAlignment(Pos.CENTER);

        vBoxTop.getChildren().addAll(hBoxTopFrom, hBoxTopEnd);
        vBoxTop.setSpacing(0);
        vBoxTop.setMaxHeight(200);
        vBoxTop.setMinHeight(200);
        vBoxTop.setMinWidth(500);
        vBoxTop.setAlignment(Pos.CENTER);
        vBoxTop.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));

        hBoxBottom.setMinHeight(80);
        hBoxBottom.setMaxHeight(80);
        hBoxBottom.setSpacing(50);
        hBoxBottom.setAlignment(Pos.CENTER);

        borderPane.setTop(vBoxTop);
        borderPane.setBottom(hBoxBottom);
    }

    //    初始化函数，按时间段添加事件时调用
    private void initialByOther() {
        hBoxBottom.getChildren().addAll(addButton, closeButton);
        hBoxCenter.getChildren().addAll(doing, textDetail, itemDetail);
        itemDetail.setMaxWidth(200);
        itemDetail.setMinWidth(200);
        hBoxCenter.setMinHeight(60);
        hBoxCenter.setMaxHeight(60);
        hBoxCenter.setSpacing(30);
        hBoxCenter.setAlignment(Pos.CENTER);
        hBoxCenter.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        borderPane.setCenter(hBoxCenter);
    }

    private void initialByAnniversary() {
        cb3.getItems().addAll("节日", "生日", "结婚纪念日");
        cb3.setPromptText("选择纪念日类型");
        hBoxBottom.getChildren().addAll(addButton, closeButton);
        hBoxCenter.getChildren().addAll(textType, cb3, textPerson, personDetail, textDetail, itemDetail);
        itemDetail.setMaxWidth(200);
        itemDetail.setMinWidth(200);
        hBoxCenter.setMinHeight(60);
        hBoxCenter.setMaxHeight(60);
        hBoxCenter.setSpacing(20);
        hBoxCenter.setAlignment(Pos.CENTER);
        hBoxCenter.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        borderPane.setCenter(hBoxCenter);
    }

    private void initialByAppoint() {
        hBoxBottom.getChildren().addAll(addButton, closeButton);
        hBoxCenter.getChildren().addAll(textPerson, personDetail, textPlace, placeDetail, textDetail, itemDetail);
        itemDetail.setMaxWidth(200);
        itemDetail.setMinWidth(200);
        hBoxCenter.setMinHeight(60);
        hBoxCenter.setMaxHeight(60);
        hBoxCenter.setSpacing(20);
        hBoxCenter.setAlignment(Pos.CENTER);
        hBoxCenter.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        borderPane.setCenter(hBoxCenter);

    }

    private void initialByClass() {
        cb2.setPromptText("课程重复周天，1~7对应周一到周日");
        cb2.getItems().addAll("1", "2", "3", "4", "5", "6", "7");
        hBoxBottom.getChildren().addAll(addButton, closeButton);


        contentDetail.setPrefRowCount(5);

        HBox hBox1 = new HBox(textCourse, courseDetail, textContent, contentDetail, textPlace, placeDetail, textContinued, continuedDetail);
        HBox hBox2 = new HBox(textTeacher, teacherDetail, textDetail, itemDetail, cb2);
        hBox1.setSpacing(20);
        hBox2.setSpacing(20);
        hBox2.setAlignment(Pos.BASELINE_CENTER);
        VBox vBox = new VBox(hBox1, hBox2);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(15, 15, 15, 15));
        hBoxCenter.getChildren().addAll(vBox);

        itemDetail.setMaxWidth(150);
        itemDetail.setMinWidth(150);
        hBoxCenter.setMinHeight(100);
        hBoxCenter.setMaxHeight(100);
        hBoxCenter.setSpacing(20);
        hBoxCenter.setAlignment(Pos.CENTER);
        hBoxCenter.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        borderPane.setCenter(hBoxCenter);

    }

    private void initialByInterview() {
        hBoxBottom.getChildren().addAll(addButton, closeButton);
        hBoxCenter.getChildren().addAll(textCompany, companyDetail, textPlace, placeDetail, textJob, jobDetail, textDetail, itemDetail);
        itemDetail.setMaxWidth(200);
        itemDetail.setMinWidth(200);
        hBoxCenter.setMinHeight(60);
        hBoxCenter.setMaxHeight(60);
        hBoxCenter.setSpacing(20);
        hBoxCenter.setAlignment(Pos.CENTER);
        hBoxCenter.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        borderPane.setCenter(hBoxCenter);
    }

    private void initialByMeeting() {

        hBoxBottom.getChildren().addAll(addButton, closeButton);
        hBoxCenter.getChildren().addAll(textPlace, placeDetail, textTheme, themeDetail, textDetail, itemDetail);
        itemDetail.setMaxWidth(200);
        itemDetail.setMinWidth(200);
        hBoxCenter.setMinHeight(60);
        hBoxCenter.setMaxHeight(60);
        hBoxCenter.setSpacing(20);
        hBoxCenter.setAlignment(Pos.CENTER);
        hBoxCenter.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        borderPane.setCenter(hBoxCenter);

    }

    private void initialByTravel() {
        cb.getItems().addAll("飞机", "火车", "大巴");
        cb.setPromptText("交通方式");
        cb.setEditable(true);
        hBoxBottom.getChildren().addAll(addButton, closeButton);
        hBoxCenter.getChildren().addAll(textPlace, placeDetail, textWay, cb, textFlight, flightDetail, textDetail, itemDetail);
        itemDetail.setMaxWidth(200);
        itemDetail.setMinWidth(200);
        hBoxCenter.setMinHeight(60);
        hBoxCenter.setMaxHeight(60);
        hBoxCenter.setSpacing(20);
        hBoxCenter.setAlignment(Pos.CENTER);
        hBoxCenter.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        borderPane.setCenter(hBoxCenter);
    }


    //    初始化函数，按时间段查询事件调用
    private void initialBySearch() {
        hBoxBottom.getChildren().addAll(searchButton, closeButton);
    }

    //    设置事件函数，按钮事件统一设置
    private void setAction() {
        closeButton.setOnAction(event -> {
            stage.close();
        });
        addButton.setOnAction(event -> {
            stage.close();
            try {
                if (addButtonAction()) {
                    Stage promptStage = new Stage();
                    promptStage.initModality(Modality.APPLICATION_MODAL);
                    promptStage.alwaysOnTopProperty();
                    promptStage.setResizable(false);

                    BorderPane flowPane = new BorderPane();
                    flowPane.setPrefSize(100, 100);
                    Label label = new Label("是否需要提醒");
                    Button button = new Button("确定");
                    Button closeButton = new Button("取消");
                    flowPane.setTop(label);
                    flowPane.setLeft(button);
                    flowPane.setRight(closeButton);
                    flowPane.setPadding(new Insets(20, 20, 20, 20));

                    //按钮事件
                    button.setOnAction(event1 -> {
                        remindButtonAction();
                        promptStage.close();
                    });
                    closeButton.setOnAction(event1 -> promptStage.close());

                    promptStage.setScene(new Scene(flowPane));
                    promptStage.showAndWait();
                    Display.prePaintDays();
                }
            } catch (ToItemException e) {
                new ErrorScene("toDoAddError");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        searchButton.setOnAction(event -> searchButtonAction());
    }

    //提醒按钮的事件
    private void remindButtonAction() {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefSize(100, 100);
        flowPane.setHgap(5);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(20, 20, 20, 20));


        Button button1 = new Button("确定");

        Label label = new Label("最早提醒时间:");
        Label label1 = new Label("提醒策略:");
        Label label2 = new Label("提醒方式");

        ComboBox<String> myComboBox = new ComboBox<String>();
        myComboBox.getItems().addAll("提前1小时", "提前5小时", "提前1天", "提前1周");
        myComboBox.setValue("提前1小时");
        ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.getItems().addAll("每5分钟提醒一次", "每1小时提醒一次", "每天提醒一次");
        comboBox.setValue("每5分钟提醒一次");
        ComboBox<Integer> remindMode = new ComboBox<>();
        remindMode.getItems().addAll(1, 2);
        remindMode.setValue(1);


        VBox vBox = new VBox();
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        vBox.getChildren().addAll(label, myComboBox);
        vBox1.getChildren().addAll(label1, comboBox);
        vBox2.getChildren().addAll(label2, remindMode);


        button1.setOnAction(event -> {
            String myComboBoxValue = myComboBox.getValue();
            String comboBoxValue = comboBox.getValue();
            int remindWay = remindMode.getValue();
            long remindTime = 0;
            long remindInterval = 1;
            //remindTime
            if (myComboBoxValue.equals("提前1小时")) {
                remindTime = 60 * 60 * 1000;
            } else if (myComboBoxValue.equals("提前5小时")) {
                remindTime = 5 * 60 * 60 * 1000;
            } else if (myComboBoxValue.equals("提前1天")) {
                remindTime = 24 * 60 * 60 * 1000;
            } else {
                remindTime = 24 * 60 * 60 * 1000 * 7;
            }
            //remindInterval
            if (comboBoxValue.equals("每5分钟提醒一次")) {
                remindInterval = 5 * 60 * 1000;
            } else if (comboBoxValue.equals("每1小时提醒一次")) {
                remindInterval = 60 * 60 * 1000;
            } else {
                remindInterval = 24 * 60 * 60 * 1000;
            }
            Date remindDate = getRemindDate(toDoItemNode.getDateFrom(), remindTime);
            toDoItemNode.setRemindTime(remindDate);
            toDoItemNode.setRemindInteral(remindInterval);
            toDoItemNode.setRemindWay(remindWay);
            stage.close();
        });

        flowPane.getChildren().addAll(vBox, vBox1, vBox2, button1);
        stage.setScene(new Scene(flowPane));
        stage.setResizable(false);
        stage.show();
    }

    private Date getRemindDate(Date date, long mimue) {
        long sum = date.getTime();
        long time = sum - mimue;
        return new Date(time);
    }

    private void bindEvent(ToDoItemNode node) {
        node.setId(Config.getId());
        if (parent != null) {
            node.setParentId(parent.getId());
            parent.addChild(node.getId());
        }
        if (urgent.isSelected()) {
            toDoItemNode.setUrgent(true);
        }
        if (important.isSelected()) {
            toDoItemNode.setImportant(true);
        }
    }

    //    添加按钮的事件
    private boolean addButtonAction() throws ToItemException, IOException {
        Date dateFrom = getDateFrom();
        Date dateEnd = getDateEnd();
        String detail = itemDetail.getText();

        try {
            if (type.equals("anniversary")) {
                String type_detail = cb3.getSelectionModel().getSelectedItem().toString();
                String name = personDetail.getText();
                toDoItemNode = new ItemAnniversary(dateFrom, dateEnd, type_detail, name, detail);
                bindEvent(toDoItemNode);
                return itemManager.addItem(toDoItemNode);
            } else if (type.equals("appoint")) {
                String person = personDetail.getText();
                String place = placeDetail.getText();
                toDoItemNode = new ItemAppoint(dateFrom, dateEnd, person, place, detail);
                bindEvent(toDoItemNode);
                return itemManager.addItem(toDoItemNode);
            } else if (type.equals("class")) {
                String course = courseDetail.getText();
                String place = placeDetail.getText();
                String content = contentDetail.getText();
                String teacher = teacherDetail.getText();
                String remark = textDetail.getText();
                int continued = Integer.parseInt(continuedDetail.getText());
                int week = Integer.parseInt(cb2.getSelectionModel().getSelectedItem().toString());
                toDoItemNode = new ItemClass(dateFrom, dateEnd, course, place, content, teacher, remark, continued, week);
                bindEvent(toDoItemNode);
                return itemManager.addItem(toDoItemNode);
            } else if (type.equals("interview")) {
                String place = placeDetail.getText();
                String company = companyDetail.getText();
                String job = jobDetail.getText();
                toDoItemNode = new ItemInterview(dateFrom, dateEnd, place, company, job, detail);
                bindEvent(toDoItemNode);
                return itemManager.addItem(toDoItemNode);
            } else if (type.equals("meeting")) {
                String place = placeDetail.getText();
                String theme = themeDetail.getText();
                toDoItemNode = new ItemMeeting(dateFrom, dateEnd, place, theme, detail);
                bindEvent(toDoItemNode);
                return itemManager.addItem(toDoItemNode);
            } else if (type.equals("travel")) {
                String place = placeDetail.getText();
                String way = cb.getSelectionModel().getSelectedItem().toString();
                String flight = flightDetail.getText();
                toDoItemNode = new ItemTravel(dateFrom, dateEnd, way, place, flight, detail);
                bindEvent(toDoItemNode);
                return itemManager.addItem(toDoItemNode);
            } else {
                ItemOther other = new ItemOther(dateFrom, dateEnd, detail, true);
                doing.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    other.setHasTime(newValue);
                });
                bindEvent(other);
                return itemManager.addItem(other);
            }
        } catch (DataErrorException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    new ButtonType("确定", ButtonBar.ButtonData.YES)).show();
        }
        return false;
    }


    //    查询按钮的事件
    private void searchButtonAction() {
        Date dateFrom = getDateFrom();
        Date dateEnd = getDateEnd();
        ArrayList<ToDoItemNode> arrayList = ToDoListUtil.search(dateFrom, dateEnd);
        new SearchByTimeResultPane(arrayList);
    }

    //    获取用户输入的开始时间
    private Date getDateFrom() {
        LocalDate localDate = datePickerFrom.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth(), comboBoxHourFrom.getSelectionModel().getSelectedIndex(), comboBoxMinuteFrom.getSelectionModel().getSelectedIndex(), 0);
        return calendar.getTime();
    }

    //    获取用户输入的结束时间
    private Date getDateEnd() {
        LocalDate localDate = datePickerEnd.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth(), comboBoxHourEnd.getSelectionModel().getSelectedIndex(), comboBoxMinuteEnd.getSelectionModel().getSelectedIndex(), 0);
        return calendar.getTime();
    }
}
