package hut.natsufumij.calc;

import hut.natsufumij.island.HelloApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

//public class CalcApplication extends HelloApplication {
//    @Override
//    protected void prepareStage(Stage stage) {
//        prepareStart(stage);
//    }
//
//    private Label expressionDisplay;
//    private Label resultDisplay;
//    private StringBuilder expression;
//    private boolean isResultDisplayed;
//
//    public void prepareStart(Stage stage) {
//        expressionDisplay = new Label();
//        expressionDisplay.getStyleClass().add("expression");
//        expressionDisplay.setMaxWidth(Double.MAX_VALUE);
//
//        resultDisplay = new Label();
//        resultDisplay.getStyleClass().add("result");
//        resultDisplay.setMaxWidth(Double.MAX_VALUE);
//
//        expression = new StringBuilder();
//        isResultDisplayed = false;
//
//        VBox displayBox = new VBox(expressionDisplay, resultDisplay);
//        displayBox.getStyleClass().add("display-container");
//        displayBox.setSpacing(10);
//
//        GridPane root = new GridPane();
//        root.setPadding(new Insets(10));
//        root.setHgap(10);
//        root.setVgap(10);
//        root.getStyleClass().add("root");
//
//        String[] buttonLabels = {
//                "7", "8", "9", "/",
//                "4", "5", "6", "*",
//                "1", "2", "3", "-",
//                "0", ".", "=", "+"
//        };
//
//        for (int i = 0; i < buttonLabels.length; i++) {
//            String label = buttonLabels[i];
//            Button button = new Button(label);
//            button.getStyleClass().add("button");
//            button.setMinSize(50, 50);
//            button.setOnAction(e -> handleButtonAction(label));
//
//            root.add(button, i % 4, (i / 4) + 1);
//        }
//
//        Button clearButton = new Button("C");
//        clearButton.getStyleClass().add("button");
//        clearButton.setMinSize(50, 50);
//        clearButton.setOnAction(e -> clearDisplay());
//        root.add(clearButton, 0, 5); // 移动清零按钮到按钮区域
//
//        root.add(displayBox, 0, 0, 4, 1); // 调整输入框位置
//
//        Scene scene = new Scene(root, 300, 400);
//        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
//
//        stage.setTitle("JavaFX Sci-Fi Style Calculator");
//        stage.setScene(scene);
//    }
//
//    private void handleButtonAction(String label) {
//        if ("=".equals(label)) {
//            try {
//                String result = eval(expression.toString());
//                expressionDisplay.setText(expression.toString() + "=");
//                resultDisplay.setText(result);
//                expression.setLength(0); // 清空表达式
//                isResultDisplayed = true;
//            } catch (Exception e) {
//                expressionDisplay.setText("Error");
//                resultDisplay.setText("");
//                expression.setLength(0); // 清空表达式
//                isResultDisplayed = true;
//            }
//        } else {
//            if (isResultDisplayed) {
//                expressionDisplay.setText("");
//                resultDisplay.setText("");
//                isResultDisplayed = false;
//            }
//            expression.append(label);
//            expressionDisplay.setText(expression.toString());
//        }
//    }
//
//    private void clearDisplay() {
//        expressionDisplay.setText("");
//        resultDisplay.setText("");
//        expression.setLength(0);
//        isResultDisplayed = false;
//    }
//
//    private String eval(String expression) {
//        return new Object() {
//            int pos = -1, ch;
//
//            void nextChar() {
//                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
//            }
//
//            boolean eat(int charToEat) {
//                while (ch == ' ') nextChar();
//                if (ch == charToEat) {
//                    nextChar();
//                    return true;
//                }
//                return false;
//            }
//
//            double parse() {
//                nextChar();
//                double x = parseExpression();
//                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
//                return x;
//            }
//
//            double parseExpression() {
//                double x = parseTerm();
//                for (; ; ) {
//                    if (eat('+')) x += parseTerm();
//                    else if (eat('-')) x -= parseTerm();
//                    else return x;
//                }
//            }
//
//            double parseTerm() {
//                double x = parseFactor();
//                for (; ; ) {
//                    if (eat('*')) x *= parseFactor();
//                    else if (eat('/')) x /= parseFactor();
//                    else return x;
//                }
//            }
//
//            double parseFactor() {
//                if (eat('+')) return parseFactor();
//                if (eat('-')) return -parseFactor();
//
//                double x;
//                int startPos = this.pos;
//                if (eat('(')) {
//                    x = parseExpression();
//                    eat(')');
//                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
//                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
//                    x = Double.parseDouble(expression.substring(startPos, this.pos));
//                } else {
//                    throw new RuntimeException("Unexpected: " + (char) ch);
//                }
//                return x;
//            }
//
//            String eval(String expression) {
//                double result = parse();
//                return (result == (int) result) ? Integer.toString((int) result) : Double.toString(result);
//            }
//        }.eval(expression);
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}


public class CalcApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AppConfig.class);
        context = builder.run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            fxmlLoader.setControllerFactory(context::getBean);
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root,400,400);
            stage.setScene(scene);
            stage.setTitle("JavaFX with Spring Boot");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        context.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);

    }
}