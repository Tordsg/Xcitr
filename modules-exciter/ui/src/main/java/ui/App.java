package ui;

import core.Exciter;
import core.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import json.FileHandler;

/**
 * JavaFX App.
 */
public class App extends Application {

  private static Scene scene;
  private static Stage stage;
  static final Exciter exciter = new Exciter();
  private FileHandler fileHandler = new FileHandler();

  @Override
  public void start(Stage stage) throws IOException {
    if (fileHandler.readUsers() != null) {
      exciter.addUsers(fileHandler.readUsers());
    }
    setStage(stage);
    setScene(new Scene(loadFxml("login")));
    stage.setScene(scene);
    stage.setResizable(false);
    stage.setTitle("citr");
    stage.getIcons().add(new Image(App.class.getResourceAsStream("Images/logo.png")));
    stage.show();
  }

  private static void setScene(Scene scene) {
    App.scene = scene;
  }

  private static void setStage(Stage stage) {
    App.stage = stage;
  }

  /**
   * Makes saving the state of the app interaction free.
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
    if (fxml.equals("primary") && scene.getWidth() < 600 || fxml.equals("login") && scene.getWidth() > 600) {
      stage.hide();
      scene = new Scene(loadFxml(fxml));
      stage.setScene(scene);
      stage.show();
    }
      scene.setRoot(loadFxml(fxml));
  }

  private static Parent loadFxml(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    launch();
  }
}