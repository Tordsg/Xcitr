open module exciter.ui {
    requires okhttp3;
    requires transitive exciter.user;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires jai.imageio.core;
    requires javafx.swing;
    requires java.desktop;
    requires java.rmi;
    requires org.apache.commons.io;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    exports ui;
}
