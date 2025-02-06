module fxworld.island.calc {
    requires javafx.fxml;
    requires javafx.controls;
    requires fxworld.island;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.jdbc;
    requires java.sql;
    requires spring.tx;
    requires org.slf4j;
    opens fonts;

    opens hut.natsufumij.calc to javafx.fxml, spring.core;
    exports hut.natsufumij.calc;
}