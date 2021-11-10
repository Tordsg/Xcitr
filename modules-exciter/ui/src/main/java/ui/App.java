package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import user.User;

/**
 * JavaFX App.
 */
public class App extends Application {

  private static Scene scene;
  private static Stage stage;
  //Enables controller to share user data
  private static User user;

  @Override
  public void start(Stage stage) throws IOException {
    Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
    stage.setScene(new Scene(parent));
    stage.setScene(scene);
    stage.setResizable(false);
    stage.setTitle("citr");
    stage.getIcons().add(new Image(App.class.getResourceAsStream("Images/logo.png")));
    stage.show();
  }


  public static void setUser(User user) {
    App.user = user;
  }

  public static User getUser() {
    return user;
  }

  @Override
  public void stop() throws Exception {
    super.stop();
  }
/*
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
*/
  public static void main(String[] args) {
    launch(App.class, args);
  }
}