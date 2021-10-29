module exciter.ui {
    requires exciter.user;
    requires exciter.core;
    requires exciter.json;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;

    opens ui to javafx.graphics, javafx.fxml;
}
