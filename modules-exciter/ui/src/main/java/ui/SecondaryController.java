package ui;

import java.io.IOException;
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
 * Controller for secondary.fxml.
 */

public class SecondaryController implements Initializable {

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
  private Label errorLabel;

  @FXML
  private void switchToPrimary(MouseEvent event) {
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
      e.printStackTrace();
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
          } catch (ServerException e1) {
            e1.printStackTrace();
          }
          updatePreview();
        });

      });
    });
  }

  @FXML
  void updatePreview() {
    User currentUser = App.getUser();
    previewName.setText(currentUser.getName());
    previewAge.setText(Integer.toString(currentUser.getAge()));
    previewEmail.setText(currentUser.getEmail());
    previewEmail.setLayoutX(112.5-previewEmail.getWidth()/2);
    previewBio.setText(currentUser.getUserInformation());
    picture.setFill(imageController.getImage(currentUser));
    pane.requestFocus();
  }
  /**
   * Signs out of the app and goes to the login-page.
   *
   * @throws IOException exception
   */

  @FXML
  public void signOut(MouseEvent event) {
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
      e.printStackTrace();
    }
  }

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
    name.setText(user.getName());
    bio.setText(user.getUserInformation());
    age.setText(Integer.toString(user.getAge()));
    TextFormatter<String> tf = new TextFormatter<>(c -> {
      if (c.isContentChange()) {
        Text text = new Text(c.getControlNewText());
        text.setWrappingWidth(198);
        text.setFont(new Font("Arial",12));
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
      previewEmail.setLayoutX(112.5-previewEmail.getWidth()/2);
    });
    Platform.runLater( () -> pane.requestFocus() );
    updatePreview();

  }
}