package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("citr");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("Images/logo.png")));
        stage.show();
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