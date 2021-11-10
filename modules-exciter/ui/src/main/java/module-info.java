module exciter.ui {
    requires okhttp3;
    requires transitive exciter.user;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires java.rmi;
    requires org.apache.commons.io;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    opens ui to javafx.graphics, javafx.fxml;
    exports ui;
}
