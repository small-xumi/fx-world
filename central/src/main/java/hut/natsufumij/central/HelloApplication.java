package hut.natsufumij.central;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        Platform.runLater(()->{
            try {
                AIOServer.bootServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        stage.show();
    }

    @Override
    public void stop() {
        System.out.println("Application is closing...");
        // Close all non-daemon threads
        System.exit(0);
    }
    public static void main(String[] args) {
        launch();
    }
}