module fxworld.island.console {
    requires javafx.controls;
    requires javafx.fxml;
    requires fxworld.island;

    opens hut.natsufumij.console to javafx.fxml;

    exports hut.natsufumij.console;
}