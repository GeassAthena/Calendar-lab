import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.xml.soap.Name;
import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


//日历下方添加按钮菜单面板,负责初始化该面板
public class ToDoItemMenuPane {
    private Button addItem = new Button("添加事项");
    private Button searchByTime = new Button("按时间段查询事项");
    private Label label = new Label("现在没有待办事项需要提醒");
    private VBox vBox = new VBox();
    private HBox toDoHox = new HBox();
    private HBox hBox = new HBox();

    //    构造函数,调用时初始化控件
    ToDoItemMenuPane() {
        toDoPaneInitial();
        setButtonAction();
    }

    //    初始化函数,初始化控件
    private void toDoPaneInitial() {
        toDoHox.getChildren().addAll(addItem, searchByTime);
        vBox.getChildren().addAll(toDoHox, label);
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);
        toDoHox.setSpacing(60);
        toDoHox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(vBox);
        hBox.setSpacing(60);
        hBox.setMaxHeight(80);
        hBox.setMinHeight(80);
        hBox.setAlignment(Pos.CENTER);
        hBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
    }


    //    设置事件函数，为按钮设置事件
    private void setButtonAction() {
        addItem.setOnAction(event -> {
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
            flowPane.getChildren().addAll(addItemByAnniversary, addItemByAppoint, addItemByClass, addItemByInterview, addItemByMeeting,
                    addItemByTravel, addItemByOther);

            flowPane.setAlignment(Pos.BASELINE_CENTER);
            stage.setScene(new Scene(flowPane));
            stage.setResizable(false);
            stage.show();


            //添加事项的触发函数

            addItemByAnniversary.setOnAction(event1 -> {
                new AddAndSearchByTimePane("anniversary");
                stage.close();
            });
            addItemByAppoint.setOnAction(event1 -> {
                new AddAndSearchByTimePane("appoint");
                stage.close();
            });
            addItemByClass.setOnAction(event1 -> {
                new AddAndSearchByTimePane("class");
                stage.close();
            });
            addItemByInterview.setOnAction(event1 -> {
                new AddAndSearchByTimePane("interview");
                stage.close();
            });
            addItemByMeeting.setOnAction(event1 -> {
                new AddAndSearchByTimePane("meeting");
                stage.close();
            });
            addItemByTravel.setOnAction(event1 -> {
                new AddAndSearchByTimePane("travel");
                stage.close();
            });
            addItemByOther.setOnAction(event1 -> {
                new AddAndSearchByTimePane("other");
                stage.close();
            });
        });
        searchByTime.setOnAction(event -> {
            new AddAndSearchByTimePane("search");
        });
    }

    public void refresh() {
        ArrayList<String> arrayList = new ArrayList(ToDoListUtil.getRemindList());
        String string = "";
        if (arrayList.size() != 0) {
            for (int i = 0; i < arrayList.size(); i+=2) {
                string += arrayList.get(i) + "\n";
            }
            label.setText(string);
        }
    }

    public HBox getToDoHox() {
        return hBox;
    }
}
