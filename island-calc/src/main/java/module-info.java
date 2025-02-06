module fxworld.island.calc {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.jdbc;
    requires java.sql;
    requires org.slf4j;
    requires fxworld.island;
    opens fonts;

    opens hut.natsufumij.calc to javafx.fxml, spring.core;
    exports hut.natsufumij.calc;
}