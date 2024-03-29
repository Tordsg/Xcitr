package ui;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.rmi.ServerException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import user.User;

/**
 * Controller for profile.fxml.
 */

public class ProfileController implements Initializable {

  private ClientHandler clientHandler = new ClientHandler();

  private User user = App.getUser();

  private final ImageController imageController = PrimaryController.getImageController();

  @FXML
  private Group selectAvatar;
  @FXML
  private Group backButton;
  @FXML
  private Group signOut;
  @FXML
  private Group save;
  @FXML
  private Group group;
  @FXML
  private Group emailGroup;
  @FXML
  private TextArea bio;
  @FXML
  private TextField name;
  @FXML
  private TextField age;
  @FXML
  private Label previewName;
  @FXML
  private Label previewAge;
  @FXML
  private Label previewEmail;
  @FXML
  private Text previewBio;
  @FXML
  private Rectangle picture;
  @FXML
  private PasswordField password;
  @FXML
  private Pane pane;
  @FXML
  private Pane avatarPane;
  @FXML
  private VBox avatarVbox;
  @FXML
  private Pane lastPane = null;
  @FXML
  private Pane info;
  @FXML
  private Label errorLabel;

  /**
   * Method for switching to primary.fxml.
   *
   * @param event clicked on the back button
   *
   */

  @FXML
  private void switchToPrimary(MouseEvent event) {
    if(Thread.currentThread().getContextClassLoader() == null) {
      Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader());
    }
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("primary.fxml"));
    Parent p;
    try {
      p = loader.load();
      Scene s = new Scene(p);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(s);
      window.show();
    } catch (IOException e) {
      errorLabel.setText(e.getMessage());
    }
  }

  private void hoverButton(Group n) {
    n.setOnMouseEntered(e -> {
      n.setEffect(new Lighting());
    });
    n.setOnMouseExited(e -> {
      n.setEffect(null);
    });
  }
  
  /**
   * Opens the avatar-selection when the select avatar button is pushed.
   */

  @FXML
  private void selectAvatar() {
    if (avatarPane.isVisible()) {
      avatarPane.setVisible(false);
    } else {
      avatarPane.setVisible(true);
    }
    avatarVbox.setOnScroll(e -> {
      if (avatarVbox.getLayoutY() + avatarVbox.getHeight() + e.getDeltaY() < 406 - 80) {
        avatarVbox.setLayoutY(406 - 80 - avatarVbox.getHeight());
      } else if (avatarVbox.getLayoutY() + e.getDeltaY() > 0) {
        avatarVbox.setLayoutY(0);
      } else {
        avatarVbox.setLayoutY(e.getDeltaY() + avatarVbox.getLayoutY());
      }
    });
    avatarVbox.getChildren().forEach(e -> {
      HBox box = (HBox) e;
      box.getChildren().forEach(k -> {
        Group g = (Group) k;
        hoverButton(g);
        g.setOnMouseClicked(l -> {
          user.setImageId(Integer.parseInt(g.getId().substring(1)));
          try {
            App.setUser(clientHandler.updateInformation(user));
          } catch (ServerException | ConnectException e1) {
            errorLabel.setText(e1.getMessage());
          }
          updatePreview();
        });

      });
    });
  }

  /**
   * Updates the user.
   */

  @FXML
  void updatePreview() {
    User currentUser = App.getUser();
    previewName.setText(currentUser.getName());
    previewAge.setText(Integer.toString(currentUser.getAge()));
    previewEmail.setText(currentUser.getEmail());
    previewEmail.setLayoutX(112.5 - previewEmail.getWidth() / 2);
    previewBio.setText(currentUser.getUserInformation());
    if (previewBio.getText().isEmpty()) {
      info.setPrefHeight(65);
      group.setLayoutY(273);
      emailGroup.setLayoutY(37);
    } else {
      info.setPrefHeight(70 + previewBio.getLayoutBounds().getHeight());
      emailGroup.setLayoutY(45 + previewBio.getLayoutBounds().getHeight());
      group.setLayoutY(338 - 70 - previewBio.getLayoutBounds().getHeight());
    }
    picture.setFill(imageController.getImage(currentUser));
    pane.requestFocus();
  }

  /**
   * Signs out of the app and goes to the login.fxml.
   *
   * @throws IOException exception
   */

  @FXML
  public void signOut(MouseEvent event) {
    if(Thread.currentThread().getContextClassLoader() == null) {
      Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader());
    }
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("login.fxml"));
    Parent p;
    try {
      p = loader.load();
      Scene s = new Scene(p);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(s);
      window.show();
    } catch (IOException e) {
      System.err.println("Error loading login.fxml");
    }
  }

  /**
   * Saves the updates done on the user profile.
   */

  @FXML
  void save() {
    user.setAge(Integer.parseInt(age.getText()));
    user.setName(name.getText());
    user.setUserInformation(bio.getText());
    try {
      User infoUser = clientHandler.updateInformation(user);
      App.setUser(infoUser);
    } catch (Exception e) {
      errorLabel.setText(e.getMessage());
    }
    if (!password.getText().equals("")) {
      try {
        User passUser = clientHandler.updatePassword(user, password.getText());
        App.setUser(passUser);
      } catch (Exception e) {
        errorLabel.setText(e.getMessage());
      }
    }
    updatePreview();
  }

  /**
   * Puts in the user info when the page opens.
   */
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Platform.setImplicitExit(false);
    name.setText(user.getName());
    bio.setText(user.getUserInformation());
    age.setText(Integer.toString(user.getAge()));
    TextFormatter<String> tf = new TextFormatter<>(c -> {
      if (c.isContentChange()) {
        Text text = new Text(c.getControlNewText());
        text.setWrappingWidth(198);
        text.setFont(new Font("Arial", 12));
        if (text.getLayoutBounds().getHeight() > 64) {
          c.setText("");
        }
      }
      return c;
    });
    bio.setTextFormatter(tf);
    hoverButton(selectAvatar);
    hoverButton(backButton);
    hoverButton(signOut);
    hoverButton(save);
    pane.setOnMouseClicked(e -> {
      pane.requestFocus();
    });
    previewEmail.widthProperty().addListener((observable, oldValue, newValue) -> {
      previewEmail.setLayoutX(112.5 - previewEmail.getWidth() / 2);
    });
    Platform.runLater(() -> pane.requestFocus());
    updatePreview();

  }
}