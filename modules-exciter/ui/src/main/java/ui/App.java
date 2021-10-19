package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import json.FileHandler;

import core.Exciter;
import core.User;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    protected static Exciter exciter = new Exciter();
    private FileHandler fileHandler = new FileHandler();

    @Override
    public void start(Stage stage) throws IOException {
        if(fileHandler.readUsers() != null) {
            exciter.addUsers(fileHandler.readUsers());
        }
        App.stage = stage;
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("citr");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("Images/logo.png")));
        stage.show();
    }

    /**
     * Makes saving the state of the app interaction free
     */
    @Override
    public void stop() throws Exception {
        List<User> users = new ArrayList<>();
        users.addAll(exciter.getAllUsers());
        users.add(exciter.getCurrentUser());

        fileHandler.saveUser(users);
        super.stop();
    }

    static void setRoot(String fxml) throws IOException {
        if(fxml.equals("primary") && stage.getWidth()!=656){
            stage.hide();
            stage.setHeight(469);
            stage.setWidth(656);
            scene.setRoot(loadFXML(fxml));
            stage.show();
        } else if(fxml.equals("login") && stage.getWidth()==656){
            stage.hide();
            stage.setHeight(587);
            stage.setWidth(381);
            scene.setRoot(loadFXML(fxml));
            stage.show();
        } else scene.setRoot(loadFXML(fxml));

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}