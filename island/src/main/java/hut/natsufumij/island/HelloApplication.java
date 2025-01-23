package hut.natsufumij.island;

import hut.natsufumij.island.app.AppData;
import hut.natsufumij.island.app.AppEvent;
import hut.natsufumij.island.app.AppInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class HelloApplication extends Application {

    //自定义初始化内容
    protected void initApp() {
        AIOClient.bootClient(this);
        //尝试连接FX世界中心
        System.out.println("尝试连接FX中心");
        //推送应用
        System.out.println("推送应用:" + pushAppData().toString());
        //定时推送数据
        System.out.println("定时推送数据:" + registerAppData().toString());
        //注册事件接收
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    //应用推送数据到中心
    protected AppInfo pushAppData() {
        return new AppInfo("app", "app", "0.0.1", "root", "Simple App");
    }

    //注册应用数据到中心
    protected AppData registerAppData() {
        return new AppData("app", ProcessHandle.current().pid() + "", "60", "20");
    }

    //接收到事件
    protected void receiveEvent(AppEvent event) {
    }

    @Override
    public void start(Stage stage) throws IOException {
        prepareStage(stage);
        Platform.runLater(this::initApp);
        stage.show();
    }

    protected void prepareStage(Stage stage){}

    public static void main(String[] args) {
        launch();
    }
}