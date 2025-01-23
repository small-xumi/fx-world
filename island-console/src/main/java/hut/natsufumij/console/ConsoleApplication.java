package hut.natsufumij.console;

import hut.natsufumij.island.HelloApplication;
import hut.natsufumij.island.app.AppInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConsoleApplication extends HelloApplication {

    private TextArea consoleField;
    private String envCmd;
    private BufferedWriter writer;
    private Process process;
    private AtomicBoolean isTimer = new AtomicBoolean(false);
    private Timer time = new Timer();

    private void timeCall() {
        if (isTimer.get()) {
            isTimer.set(true);
            consoleField.appendText("$ ");
        }
    }

    @Override
    public void prepareStage(Stage primaryStage) {
        Thread cmdThread = new Thread(() -> {
            try {
                process = Runtime.getRuntime().exec(envCmd);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    consoleField.appendText(line + "\n");
                    if (isTimer.get()) {
                        time.cancel();
                        time = new Timer();
                    } else {
                        isTimer.set(true);
                    }
                    time.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            timeCall();
                        }
                    }, 2000);
                }
            } catch (Exception e) {
                consoleField.appendText("Error executing command: " + e.getMessage() + "\n");
            }
        });


        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            envCmd = "cmd.exe";
        } else {
            envCmd = "sh";
        }
        consoleField = new TextArea();
        consoleField.appendText("$ ");
//        consoleField.setEditable(false);
        consoleField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    int length = consoleField.getText().split("\n").length;
                    String command = consoleField.getText().split("\n")[length - 1];
                    if (command != null && !command.isBlank() && command.startsWith("$ ")) {
                        executeCommand(command.substring(2), consoleField);
                    }
                    break;
                default:
                    break;
            }
        });
        cmdThread.start();
        consoleField.setPrefSize(600, 400);
        VBox root = new VBox(10, consoleField);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("JavaFX Console");
        primaryStage.setScene(scene);
    }

    private void executeCommand(String command, TextArea consoleField) {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write(command);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            consoleField.appendText("Error executing command: " + e.getMessage() + "\n");
        }
    }

    @Override
    protected AppInfo pushAppData() {
        return new AppInfo("console","FX控制台","0.0.1","root","简单控制台");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
