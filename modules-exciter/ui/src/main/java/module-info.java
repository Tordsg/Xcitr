module exciter.ui {
    requires okhttp3;
    requires exciter.user;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires org.apache.commons.io;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    opens ui to javafx.graphics, javafx.fxml;
}
