
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


//查询结果面板,显示查询事件结果时显示
public class SearchByTimeResultPane {
    private Stage stage = new Stage();
    private static Text textNoItem = new Text("该时间段没有事件");
    private static ScrollPane scrollPane = new ScrollPane();
    private static VBox vBoxLargest = new VBox();
    private BorderPane borderPane = new BorderPane();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 EEE HH:mm");

    //    构造函数,调用时初始化控件,并展示面板
    SearchByTimeResultPane(ArrayList<ToDoItemNode> arrayList) {
        getVBox(arrayList);
        stage = new Stage();
        borderPane = new BorderPane();
        borderPane.setCenter(vBoxLargest);
        stage.setScene(new Scene(borderPane));
        stage.setResizable(false);
        stage.show();
    }

    //    获取查询结果面板
    public static VBox getVBox(ArrayList<ToDoItemNode> arrayList) {
        vBoxLargest = new VBox();

        vBoxLargest.setMaxSize(500, 400);
        vBoxLargest.setMinSize(500, 400);
        scrollPane = new ScrollPane();
        textNoItem = new Text("该时间段没有事件");
        textNoItem.setFont(new Font(30));
        VBox vBox = new VBox();
        if (null == arrayList) {
            vBoxLargest.getChildren().addAll(textNoItem);
        } else {
            vBoxLargest.getChildren().addAll(scrollPane);
            VBox.setVgrow(scrollPane, Priority.ALWAYS);


            for (int i = 0; i < arrayList.size(); i++) {
                HBox hBox = new HBox();
                hBox.setSpacing(20);
                hBox.setMinSize(500, 30);
                hBox.setMaxSize(500, 30);
                ToDoItemNode toDoItemNode;
                if (arrayList.get(i) instanceof ItemAnniversary) {
                    ItemAnniversary toDoItemNode1 = (ItemAnniversary) arrayList.get(i);
                    toDoItemNode = toDoItemNode1;
                } else if (arrayList.get(i) instanceof ItemClass) {
                    ItemClass toDoItemNode1 = (ItemClass) arrayList.get(i);
                    toDoItemNode = toDoItemNode1;
                } else {
                    ToDoItemNode toDoItemNode1 = arrayList.get(i);
                    toDoItemNode = toDoItemNode1;
                }

                VBox vBoxTemp = new VBox();
                vBoxTemp.setMaxSize(500, 400);
                vBoxTemp.setMinSize(500, 400);
                vBoxTemp.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
                Text textStartTime = new Text("起始时间： " + sdf.format(toDoItemNode.getDateFrom()));
                Text textEndTime = new Text("终止时间： " + sdf.format(toDoItemNode.getDateEnd()));
                Text textDetail = new Text("事件详情： " + toDoItemNode.getItemDetail());
                Text priority = new Text();
                if (toDoItemNode.isImportant() && toDoItemNode.isUrgent()) {
                    priority.setText("紧急&重要");
                } else if ((!toDoItemNode.isImportant()) && toDoItemNode.isUrgent()) {
                    priority.setText("紧急&不重要");
                } else if ((!toDoItemNode.isUrgent()) && toDoItemNode.isImportant()) {
                    priority.setText("不紧急&重要");
                } else {
                    priority.setText("不紧急&不重要");
                }
                Text process = new Text();
                Button success = new Button("设置已完成");
                if (toDoItemNode.getCondition() == 0) {
                    hBox.getChildren().add(success);
                    process.setText("进行中");
                } else if (toDoItemNode.getCondition() == -1) {
                    process.setText("未开始");
                } else {
                    process.setText("过期");
                }
                if (toDoItemNode.getState()) {
                    process.setText("已完成");
                }
                success.setOnAction(event -> {
                    ToDoListUtil.delete(toDoItemNode);
                    toDoItemNode.setState(true);
                    ToDoListUtil.insert(toDoItemNode);
                    hBox.getChildren().remove(success);
                    process.setText("已完成");
                });
                Button button = new Button("删除");

                button.setOnAction(event -> {
                    Stage promptStage = new Stage();
                    promptStage.initModality(Modality.APPLICATION_MODAL);
                    promptStage.alwaysOnTopProperty();
                    promptStage.setResizable(false);
                    BorderPane flowPane = new BorderPane();
                    flowPane.setPrefSize(100, 100);
                    Label label = new Label("确认删除");
                    Button button3 = new Button("确定");
                    Button closeButton = new Button("取消");
                    flowPane.setTop(label);
                    flowPane.setLeft(button3);
                    flowPane.setRight(closeButton);
                    flowPane.setPadding(new Insets(20, 20, 20, 20));

                    button3.setOnAction(event1 -> {
                        if (toDoItemNode instanceof ItemClass) {
                            ItemClass itemClass = (ItemClass) toDoItemNode;
                            ArrayList<ItemClass> list = new ArrayList<>();
                            try {
                                list = itemClass.add();
                            } catch (ToItemException e) {
                                e.printStackTrace();
                            }
                            for (int j = 0; j < list.size(); j++) {
                                ToDoListUtil.delete(list.get(j));
                            }
                        }
                        if (toDoItemNode instanceof ItemAnniversary) {
                            ItemAnniversary itemAnniversary = (ItemAnniversary) toDoItemNode;
                            ArrayList<ItemAnniversary> list = new ArrayList<>();
                            try {
                                list = itemAnniversary.add();
                            } catch (ToItemException e) {
                                e.printStackTrace();
                            }
                            for (int j = 0; j < list.size(); j++) {
                                ToDoListUtil.delete(list.get(j));
                            }
                        }
                        ToDoListUtil.delete(toDoItemNode);
                        ItemPersistence.outPutItems();
                        promptStage.close();
                    });
                    closeButton.setOnAction(event1 -> promptStage.close());
                    promptStage.setScene(new Scene(flowPane));
                    promptStage.showAndWait();
                    Display.prePaintDays();
                    vBox.getChildren().removeAll(vBoxTemp);
                });
                Button addChildren = new Button("添加子事项");
                addChildren.setOnAction(event -> {
                    Stage stage = new Stage();
                    FlowPane flowPane = new FlowPane();
                    Button addItemByAppoint = new Button("按约会添加事项 ");
                    Button addItemByMeeting = new Button("按会议添加事项 ");
                    Button addItemByAnniversary = new Button("按纪念日添加事项");
                    Button addItemByInterview = new Button("按面试添加事项 ");
                    Button addItemByTravel = new Button("按旅游添加事项 ");
                    Button addItemByClass = new Button("按课程添加事项 ");
                    Button addItemByOther = new Button("按自定义添加事项");
                    flowPane.setPrefSize(300, 300);
                    flowPane.setHgap(40);
                    flowPane.setVgap(40);
                    flowPane.setPadding(new Insets(15, 15, 15, 15));
                    flowPane.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
                    switch (toDoItemNode.types) {
                        case 1:
                            flowPane.getChildren().addAll(addItemByAppoint, addItemByTravel, addItemByOther);
                            break;
                        case 2:
                            flowPane.getChildren().addAll(addItemByClass, addItemByTravel, addItemByOther);
                            break;
                        case 3:
                            flowPane.getChildren().addAll(addItemByInterview, addItemByTravel, addItemByOther);
                            break;
                        case 4:
                            flowPane.getChildren().addAll(addItemByMeeting, addItemByTravel, addItemByOther);
                            break;
                        case 5:
                            flowPane.getChildren().addAll(addItemByAppoint, addItemByAnniversary, addItemByTravel, addItemByClass, addItemByInterview, addItemByOther);
                            break;
                        case 6:
                            flowPane.getChildren().addAll(addItemByAnniversary, addItemByTravel, addItemByOther);
                            break;
                        default:
                            break;
                    }

                    flowPane.setAlignment(Pos.BASELINE_CENTER);
                    stage.setScene(new Scene(flowPane));
                    stage.setResizable(false);
                    stage.show();


                    //添加事项的触发函数

                    addItemByAnniversary.setOnAction(event1 -> {
                        AddAndSearchByTimePane pane = new AddAndSearchByTimePane("anniversary");
                        pane.setParent(toDoItemNode);
                        stage.close();
                    });
                    addItemByAppoint.setOnAction(event1 -> {
                        AddAndSearchByTimePane pane = new AddAndSearchByTimePane("appoint");
                        pane.setParent(toDoItemNode);
                        stage.close();
                    });
                    addItemByClass.setOnAction(event1 -> {
                        AddAndSearchByTimePane pane = new AddAndSearchByTimePane("class");
                        pane.setParent(toDoItemNode);
                        stage.close();
                    });
                    addItemByInterview.setOnAction(event1 -> {
                        AddAndSearchByTimePane pane = new AddAndSearchByTimePane("interview");
                        pane.setParent(toDoItemNode);
                        stage.close();
                    });
                    addItemByMeeting.setOnAction(event1 -> {
                        AddAndSearchByTimePane pane = new AddAndSearchByTimePane("meeting");
                        pane.setParent(toDoItemNode);
                        stage.close();
                    });
                    addItemByTravel.setOnAction(event1 -> {
                        AddAndSearchByTimePane pane =  new AddAndSearchByTimePane("travel");
                        pane.setParent(toDoItemNode);
                        stage.close();
                    });
                    addItemByOther.setOnAction(event1 -> {
                        AddAndSearchByTimePane pane = new AddAndSearchByTimePane("other");
                        pane.setParent(toDoItemNode);
                        stage.close();
                    });


                });
                hBox.getChildren().addAll(button, addChildren);
                vBoxTemp.getChildren().addAll(textStartTime, textEndTime, textDetail, priority, process, hBox);
                vBoxTemp.setMaxHeight(300);
                vBoxTemp.setMinSize(500, 100);
                vBox.getChildren().add(vBoxTemp);
            }
            vBox.setSpacing(20);
            vBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
            scrollPane.setContent(vBox);
        }
        return vBoxLargest;
    }

}
