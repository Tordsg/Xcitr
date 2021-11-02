module exciter.ui {
    requires okhttp3;
    requires exciter.user;
    requires exciter.core;
    requires exciter.json;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires jackson.databind;
    opens ui to javafx.graphics, javafx.fxml;
}
