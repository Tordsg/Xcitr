module exciter.ui {
    requires exciter.core;
    requires exciter.json;
    requires javafx.controls;
    requires javafx.fxml;

    opens ui to javafx.graphics, javafx.fxml;
}
