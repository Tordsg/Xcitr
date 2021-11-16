package ui;

import java.io.IOException;
import java.net.URL;
import java.rmi.ServerException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
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

  private final static ImageController imageController = new ImageController();
  @FXML
  private Group selectAvatar;
  @FXML
  private Group backButton;
  @FXML
  private Group signOut;
  @FXML
  private Group save;
  @FXML
  private Group i1;
  @FXML
  private Group i2;
  @FXML
  private Group i3;
  @FXML
  private Group i4;
  @FXML
  private Group i5;
  @FXML
  private Group i6;
  @FXML
  private Group i7;
  @FXML
  private Group i8;
  @FXML
  private Group i9;
  @FXML
  private Group i10;
  @FXML
  private Group i11;
  @FXML
  private Group i12;
  @FXML
  private Group i13;
  @FXML
  private Group i14;
  @FXML
  private Group i15;
  @FXML
  private Group i16;
  @FXML
  private Group i17;
  @FXML
  private Group i18;
  @FXML
  private Group i19;
  @FXML
  private Group i20;
  @FXML
  private Group i21;
  @FXML
  private Group i22;
  @FXML
  private Group i23;
  @FXML
  private Group i24;
  @FXML
  private TextArea bio;
  @FXML
  private TextField name;
  @FXML
  private TextField age;
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
    Pane currentPane = createCard(currentUser);
    if (pane.getChildren().contains(lastPane)) {
      pane.getChildren().remove(lastPane);
    }
    pane.getChildren().add(currentPane);
    currentPane.setLayoutX(70);
    currentPane.setLayoutY(70);
    lastPane = currentPane;
    pane.requestFocus();
  }

  protected static Pane createCard(User user) {
    Pane pane = new Pane();
    pane.setPrefHeight(338);
    pane.setPrefWidth(245);
    Rectangle rect = new Rectangle();
    pane.getChildren().add(rect);
    rect.setArcHeight(45);
    rect.setArcWidth(45);
    rect.setHeight(338);
    rect.setWidth(225);
    rect.setStrokeWidth(2);
    rect.setStroke(Color.BLACK);
    rect.setStrokeType(StrokeType.INSIDE);
    rect.setFill(imageController.getImage(user));
    Pane pane1 = new Pane();
    pane.getChildren().add(pane1);
    pane1.setCacheShape(false);
    pane1.setLayoutY(217);
    pane1.setPrefHeight(121);
    pane1.setMinHeight(121);
    pane1.setPrefWidth(225);
    pane1.setStyle("""
        -fx-background-color: rgba(255, 255, 255, .4);
        -fx-background-radius: 0 0 22 22; -fx-border-radius: 0 0 21 21;
        -fx-border-width: 0 2 2 2; -fx-border-color: black;
        """);
    Label age = new Label();
    pane1.getChildren().add(age);
    age.setAlignment(Pos.CENTER);
    age.setContentDisplay(ContentDisplay.CENTER);
    age.setLayoutX(170);
    age.setLayoutY(-3);
    age.setPrefHeight(45);
    age.setPrefWidth(58);
    age.setText(Integer.toString(user.getAge()));
    age.setFont(new Font(30));
    Label name = new Label();
    pane1.getChildren().add(name);
    name.setLayoutX(5);
    name.setLayoutY(-3);
    name.setPrefHeight(17);
    name.setPrefWidth(160);
    name.setText(user.getName());
    name.setFont(new Font(30));
    name.setOpacity(1);
    Label email = new Label();
    pane1.getChildren().add(email);
    email.setOpacity(1);
    email.setLayoutX(0);
    email.setLayoutY(100);
    email.setPrefHeight(12);
    email.setPrefWidth(225);
    email.setText(user.getEmail());
    email.setFont(new Font(12));
    email.setAlignment(Pos.CENTER);
    Line line = new Line();
    pane1.getChildren().add(line);
    line.setOpacity(1);
    line.setStartX(50);
    line.setEndX(175);
    line.setLayoutX(0);
    line.setLayoutY(97);
    Text text = new Text();
    pane1.getChildren().add(text);
    text.setOpacity(1);
    text.setLayoutX(6);
    text.setLayoutY(46);
    text.setWrappingWidth(214);
    text.setText(user.getUserInformation());
    DropShadow effect = new DropShadow();
    effect.setOffsetX(2);
    effect.setOffsetY(2);
    effect.setColor(new Color(0, 0, 0, 0.5));
    pane.setEffect(effect);
    return pane;
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
        if (c.getControlNewText().endsWith("\n")) {
          c.setText("");
        }
        Text text = new Text(c.getControlNewText());
        text.setWrappingWidth(214);
        if (text.getLayoutBounds().getHeight() > 69) {
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
    // when enter is pressed
    pane.setOnMouseClicked(e -> {
      pane.requestFocus();
    });
    updatePreview();

  }
}