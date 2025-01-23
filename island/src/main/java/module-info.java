module fxworld.island {
    requires javafx.controls;
    requires javafx.fxml;

    opens hut.natsufumij.island to javafx.fxml;
    exports hut.natsufumij.island;
    exports hut.natsufumij.island.app;
}