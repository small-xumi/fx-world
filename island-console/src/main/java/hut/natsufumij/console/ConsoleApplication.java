package hut.natsufumij.console;

import hut.natsufumij.island.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConsoleApplication extends HelloApplication {

    @Override
    protected void initApp() {
        super.initApp();
        System.out.println("Console APp..");
    }

    public static void main(String[] args) {
        launch();
    }
}