import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Bing Chen on 2018/4/14.
 */
//按天添加和查询事件的面板，点击日期按钮后展现
public class AddAndSearchByDayPane {
    private VBox vBoxSearchResult;
    private Stage stage = new Stage();
    private GridPane gridPane = new GridPane();
    private Button addButton = new Button("添加事件");
    private Button closeButton = new Button("关闭");
    private HBox hBoxCenter = new HBox();
    private HBox hBoxTop = new HBox();
    private Text selectDate = new Text();
    private TextArea itemDetail = new TextArea();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
    private Date dateFrom;
    private Date dateEnd;

    //     构造函数，调用时初始化组件，并展示面板
    AddAndSearchByDayPane(Date dateFrom, Date dateEnd) {
        ArrayList<ToDoItemNode> arrayList = ToDoListUtil.search(dateFrom, dateEnd);
        this.dateFrom = dateFrom;
        this.dateEnd = dateEnd;
        initialControl(arrayList);
        setAction();
        Scene scene = new Scene(gridPane, 500, 560);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    //    初始化函数，初始化面板控件
    private void initialControl(ArrayList<ToDoItemNode> arrayList) {
        vBoxSearchResult = SearchByTimeResultPane.getVBox(arrayList);
        selectDate.setText("当前选择日期： " + sdf.format(dateFrom));

        hBoxTop.getChildren().addAll(selectDate, closeButton);
        hBoxTop.setSpacing(50);
        hBoxTop.setMinWidth(550);
        hBoxTop.setMaxHeight(200);
        hBoxTop.setMinHeight(80);
        hBoxTop.setAlignment(Pos.CENTER);
        hBoxTop.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));

        itemDetail.setPrefRowCount(2);
        itemDetail.setPrefColumnCount(25);
        hBoxCenter.getChildren().addAll(itemDetail, addButton);
        hBoxCenter.setSpacing(30);
        hBoxCenter.setMinWidth(500);
        hBoxCenter.setMaxHeight(200);
        hBoxCenter.setMinHeight(80);
        hBoxCenter.setAlignment(Pos.CENTER);
        hBoxCenter.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));

        gridPane.add(hBoxTop, 0, 0);
        gridPane.add(hBoxCenter, 0, 1);
        gridPane.add(vBoxSearchResult, 0, 2);
    }

    //    设置事件函数，按钮事件统一设置
    private void setAction() {
        closeButton.setOnAction(event -> {
            stage.close();
        });
        addButton.setOnAction(event -> {
            try {
                setAddButtonAction();
            } catch (ToItemException e) {
                new ErrorScene("toDoAddError");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    //    按天添加事件的按钮事件
    private void setAddButtonAction() throws ToItemException, IOException {
        ToDoItemNode toDoItemNode = new ToDoItemNode(dateFrom, dateEnd, itemDetail.getText());
        ToDoListUtil.insert(toDoItemNode);
        ItemPersistence.outPutItems();
        Display.prePaintDays();
        gridPane.getChildren().removeAll(vBoxSearchResult);
        ArrayList<ToDoItemNode> arrayList = ToDoListUtil.search(dateFrom, dateEnd);
        vBoxSearchResult.setMinWidth(550);
        vBoxSearchResult = SearchByTimeResultPane.getVBox(arrayList);

        gridPane.add(vBoxSearchResult, 0, 2);
    }
}
