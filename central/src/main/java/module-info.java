module fxworld.central {
    requires javafx.controls;
    requires javafx.fxml;


    opens hut.natsufumij.central to javafx.fxml;
    exports hut.natsufumij.central;
}