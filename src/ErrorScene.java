import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.*;
import javafx.stage.Stage;


//针对用户输入错误，给出提示类
public class ErrorScene {
    private Stage stage = new Stage();
    private Text textError = new Text();
    private Text textHelp = new Text();
    private Button button = new Button("确定");
    private BorderPane borderPane = new BorderPane();
    private HBox topHBox = new HBox();
    private HBox bottomHBox = new HBox();

    //    调用构造函数时，初始化组件，并调用显示函数
    public ErrorScene(String errorInformation) {
        setErrorMessage(errorInformation);
        setButtonAction();
        showErrorStage();
    }

    //    根据传入的信息，判断错误类型，设置对应的提示信息
    private void setErrorMessage(String errorInformation) {
        textHelp.setFont(Font.font(20));
        textError.setFont(Font.font(20));
        switch (errorInformation) {
            case "unFormat":
                textError.setText("输入日期格式错误");
                textHelp.setText("正确格式：YYYY-MM-DD");
                break;
            case "inValid":
                textError.setText("输入日期不合法");
                textHelp.setText("请输入存在的日期");
                break;
            case "toDoAddError":
                textError.setText("添加事项失败");
                textHelp.setText("请检查输入信息是否有误");
                break;
            default:
                textError.setText("目标日期不在显示范围内之间");
                textHelp.setText("正确范围：1800-2300");
        }
    }

    //    设置按钮动作，关闭提示界面
    private void setButtonAction() {
        button.setFont(Font.font("", FontWeight.BOLD, FontPosture.ITALIC, 10));
        button.setAlignment(Pos.CENTER);
        button.setMaxSize(60, 40);
        button.setMinSize(60, 40);
        button.setOnAction(event -> {
            stage.close();
        });
    }

    //    为面板添加组件，显示提示信息
    private void showErrorStage() {
        topHBox.getChildren().addAll(textError);
        topHBox.setAlignment(Pos.CENTER);
        bottomHBox.getChildren().addAll(button);
        bottomHBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topHBox);
        borderPane.setCenter(textHelp);
        borderPane.setBottom(bottomHBox);
        borderPane.setStyle("-fx-background-color: red");
        stage.setScene(new Scene(borderPane, 300, 150));
        stage.setTitle("ErrorMassage");
        stage.show();
    }
}
