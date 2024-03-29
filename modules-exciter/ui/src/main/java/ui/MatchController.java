package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import user.Chat;
import user.User;

/**
 * Controller for match.fxml.
 */

public class MatchController implements Initializable {

  @FXML
  VBox matchBox;
  @FXML
  VBox textBox;
  @FXML
  Pane cardPane;
  @FXML
  Pane textPane;
  @FXML
  Pane info;
  @FXML
  Pane refresh;
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
  TextField textInput;
  @FXML
  Group backButton;
  @FXML
  Group sendButton;
  @FXML
  Group group;
  @FXML
  Group emailGroup;
  @FXML
  AnchorPane anchorPane;
  @FXML
  AnchorPane profilePane;
  @FXML
  Circle chatPic;
  @FXML
  Text nameUser;

  private int chatId;
  private static final ImageController imageController = PrimaryController.getImageController();
  private ClientHandler clientHandler = new ClientHandler();
  private User user = App.getUser();
  private User user1;
  private List<User> matches = new ArrayList<>();

  /**
   * Method to switch to match.fxml.
   *
   * @param event MouseEvent
   */

  public void switchToPrimary(MouseEvent event) {
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
      e.printStackTrace();
    }
  }

  /**
   * Checks if user has any matches and set them in the match fxml file.
   */

  public void initialize(URL arg0, ResourceBundle arg1) {
    textPane.setLayoutX(640);
    try {
      matches = clientHandler.getMatches(user);
    } catch (IOException e) {
      Label label = new Label(e.getMessage());
      label.setFont(new Font(20));
      label.setAlignment(Pos.CENTER);
      label.setLayoutX(48);
      label.setLayoutY(140);
      anchorPane.getChildren().add(label);
    }
    hoverButton(backButton);
    hoverButton(sendButton);
    hoverButton(chatPic);
    hoverButton(refresh);
    if (matches != null && !matches.isEmpty()) {
      matches.forEach(e -> matchBox.getChildren().add(createMatchCard(e)));
      matchBox.getChildren().forEach(e -> hoverButton(e));
      for (int i = 0; i < matchBox.getChildren().size(); i++) {
        clickButton((Group) matchBox.getChildren().get(i), matches.get(i), i);
      }
    }
    if (matchBox.getChildren().size() == 0) {
      Label label = new Label("You have no matches, yet.");
      label.setFont(new Font(20));
      label.setAlignment(Pos.CENTER);
      label.setLayoutX(48);
      label.setLayoutY(80);
      anchorPane.getChildren().add(label);
    } else {
      matchBox.setOnScroll(k -> {
        if (matchBox.getLayoutY() + k.getDeltaY() / 2 >= 62) {
          matchBox.setLayoutY(62);
        } else if (matchBox.getLayoutY() + matchBox.getHeight() + k.getDeltaY() / 2 <= 411) {
          matchBox.setLayoutY(411 - matchBox.getHeight());
        } else {
          matchBox.setLayoutY(matchBox.getLayoutY() + k.getDeltaY() / 2);
        }
      });
      textBox.setOnScroll(k -> {
        if (textBox.getLayoutY() + textBox.getHeight() + k.getDeltaY() / 2 < 393) {
          textBox.setLayoutY(393 - textBox.getHeight());
        } else if (textBox.getLayoutY() + k.getDeltaY() / 2 > 63 && textBox.getHeight() < 325) {
          textBox.setLayoutY(393 - textBox.getHeight());
        } else if (textBox.getLayoutY() + k.getDeltaY() / 2 > 63) {
          textBox.setLayoutY(63);
        } else {
          textBox.setLayoutY(textBox.getLayoutY() + k.getDeltaY() / 2);
        }
      });
      anchorPane.setOnKeyPressed(k -> {
        if (k.getCode().equals(KeyCode.ENTER)) {
          sendMessage();
        }
      });
    }
  }

  /**
   * Method to refresh the cards on the match page.
   */

  @FXML
  public void refresh() {
    refresh.setDisable(true);
    RotateTransition rt = new RotateTransition(Duration.millis(500), refresh);
    rt.setFromAngle(0);
    rt.setToAngle(360);
    rt.setOnFinished(e -> {
      refresh.setDisable(false);
      textBox.setLayoutY(393 - textBox.getHeight());
    });
    rt.play();
    if (user1 != null) {
      textBox.getChildren().clear();
      fillChat(user, user1);
      textBox.setLayoutY(393);
    }
  }

  /**
   * Sends a message using the text input from textInput field.
   */

  @FXML
  public void sendMessage() {
    if (textInput.getText().equals("") || matches == null || matches.isEmpty()) {
      return;
    }
    HBox hbox = createMessage(textInput.getText(), true);
    try {
      clientHandler.sendMessage(user, matches.get(chatId), textInput.getText());
    } catch (IOException e) {
      Label label = new Label(e.getMessage());
      label.setFont(new Font(20));
      label.setAlignment(Pos.CENTER);
      label.setLayoutX(48);
      label.setLayoutY(140);
      anchorPane.getChildren().add(label);
    }
    textInput.clear();
    textBox.getChildren().add(hbox);
    textBox.setLayoutY(393 - textBox.getHeight() - height);
  }

  /**
   * Method to create a message .
   *
   * @param string string value
   * @param isCurrentUser boolean
   *
   * @return hbox
   */

  private HBox createMessage(String string, Boolean isCurrentUser) {
    Group group = new Group();
    Text text = new Text(string);
    if (text.getLayoutBounds().getWidth() > 150) {
      text.setWrappingWidth(150);
    }
    Bounds b = text.getLayoutBounds();
    Color c = Color.rgb(220, 220, 220);
    Rectangle rectangle = new Rectangle(b.getWidth() + 20, b.getHeight() + 20, c);
    group.getChildren().add(rectangle);
    group.getChildren().add(text);
    height = text.getLayoutBounds().getHeight() + 35;
    if (rectangle.getHeight() < 45) {
      rectangle.setArcHeight(25);
      rectangle.setArcWidth(25);
    } else {
      rectangle.setArcHeight(40);
      rectangle.setArcWidth(40);
    }
    text.setLayoutY(rectangle.getHeight() / 2 - text.getLayoutBounds().getHeight() / 2 + 12);
    text.setLayoutX(10);
    HBox hbox = new HBox();
    hbox.getChildren().add(group);
    if (isCurrentUser) {
      rectangle.setFill(Color.rgb(0, 190, 255));
      text.setFill(Color.WHITE);
      hbox.setAlignment(Pos.TOP_RIGHT);
    }
    return hbox;
  }

  /**
   * Adds light when hovering the mouse over a button.
   *
   * @param n node
   */

  private void hoverButton(Node n) {
    n.setOnMouseEntered(e -> {
      n.setEffect(new Lighting());
    });
    n.setOnMouseExited(e -> {
      n.setEffect(null);
    });
  }

  Group lastN = null;

  /**
   * Method to click button.
   *
   * @param n group
   * @param user1 User object
   * @param i int
   */

  private void clickButton(Group n, User user1, int i) {
    this.user1 = user1;
    n.setOnMouseClicked(e -> {
      if (lastN != null) {
        if (lastN.equals(n)) {
          return;
        }
        lastN.getChildren().forEach(l -> {
          if (l.getClass().equals(Rectangle.class)) {
            Rectangle rect = (Rectangle) l;
            rect.setStrokeWidth(0);
          }
        });
      }
      lastN = n;
      n.getChildren().forEach(l -> {
        if (l.getClass().equals(Rectangle.class)) {
          Rectangle rect = (Rectangle) l;
          rect.setStrokeWidth(3);
        }
      });
      matchBox.getChildren().forEach(l -> l.setDisable(true));
      if (textPane.getTranslateX() == -320) {
        TranslateTransition ttOut = new TranslateTransition(Duration.millis(400), textPane);
        ttOut.setFromX(-320);
        ttOut.setToX(0);
        ttOut.setOnFinished(t -> {
          chatId = i;
          nameUser.setText(user1.getName());
          textBox.getChildren().clear();
          textBox.setLayoutY(63);
          updateCardPane(user1);
          fillChat(user, user1);
          textBox.setLayoutY(393);
          textInput.clear();
          chatPic.setFill(new ImagePattern(imageController
              .getImage(user1).getImage(), 0, 0, 1, 1.4, true));
        });
        TranslateTransition ttIn = new TranslateTransition(Duration.millis(400), textPane);
        ttIn.setOnFinished(l -> {
          matchBox.getChildren().forEach(h -> h.setDisable(false));
          textBox.setLayoutY(393 - textBox.getHeight());
        });
        ttIn.setFromX(0);
        ttIn.setToX(-320);
        SequentialTransition st = new SequentialTransition(ttOut,
            new TranslateTransition(Duration.millis(200)), ttIn);
        st.play();
      } else {
        chatId = i;
        nameUser.setText(user1.getName());
        textBox.getChildren().clear();
        textBox.setLayoutY(63);
        updateCardPane(user1);
        fillChat(user, user1);
        textBox.setLayoutY(393);
        textInput.clear();
        chatPic.setFill(new ImagePattern(imageController
            .getImage(user1).getImage(), 0, 0, 1, 1.4, true));
        TranslateTransition ttIn = new TranslateTransition(Duration.millis(400), textPane);
        ttIn.setOnFinished(l -> {
          matchBox.getChildren().forEach(h -> h.setDisable(false));
          textBox.setLayoutY(393 - textBox.getHeight());
        });
        ttIn.setFromX(0);
        ttIn.setToX(-320);
        ttIn.play();
      }

    });
  }

  private double height = 0;

  /**
   * Creates a match card for a User object.
   *
   * @param user User object
   *
   * @return group
   */

  protected static Group createMatchCard(User user) {
    Group group = new Group();
    group.setTranslateX(-5);
    Rectangle rectangle = new Rectangle(296, 70, Color.WHITE);
    Image image = imageController.getImage(user).getImage();
    ImagePattern ip = new ImagePattern(image, 0, 0, 1, 1.4, true);
    Circle circle = new Circle(24, ip);
    Text text = new Text(user.getName());
    group.getChildren().add(rectangle);
    group.getChildren().add(circle);
    group.getChildren().add(text);
    rectangle.setArcHeight(70);
    rectangle.setArcWidth(70);
    rectangle.setStroke(Color.GREY);
    rectangle.setStrokeWidth(0);
    circle.setEffect(new DropShadow(10, 5, 5, Color.rgb(0, 0, 0, 0.5)));
    circle.setLayoutX(38);
    circle.setLayoutY(34);
    text.setLayoutX(73);
    text.setLayoutY(32);
    text.setFont(Font.font("System", 19));
    return group;
  }

  /**
   * Method to animate the profile.
   */
  private void updateCardPane(User user1) {
    previewName.setText(user1.getName());
    previewAge.setText(Integer.toString(user1.getAge()));
    previewEmail.setText(user1.getEmail());
    previewEmail.setLayoutX(112.5 - previewEmail.getWidth() / 2);
    previewBio.setText(user1.getUserInformation());
    if (previewBio.getText().isEmpty()) {
      info.setPrefHeight(65);
      group.setLayoutY(273);
      emailGroup.setLayoutY(37);
    } else {
      info.setPrefHeight(70 + previewBio.getLayoutBounds().getHeight());
      emailGroup.setLayoutY(45 + previewBio.getLayoutBounds().getHeight());
      group.setLayoutY(338 - 70 - previewBio.getLayoutBounds().getHeight());
    }
    picture.setFill(imageController.getImage(user1));
    previewEmail.widthProperty().addListener((observable, oldValue, newValue) -> {
      previewEmail.setLayoutX(112.5 - previewEmail.getWidth() / 2);
    });
  }
  /**
   * Shows the matched users card when chatPic is clicked on.
   */

  @FXML
  public void animateProfile() {
    chatPic.setDisable(true);
    if (profilePane.getPrefHeight() != 430) {
      TranslateTransition tt = new TranslateTransition(Duration.millis(300), cardPane);
      tt.setFromX(0);
      tt.setToX(-355);
      KeyValue kv = new KeyValue(profilePane.prefHeightProperty(), 430, Interpolator.EASE_BOTH);
      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), kv));
      SequentialTransition st = new SequentialTransition(timeline, tt);
      st.setOnFinished(e -> chatPic.setDisable(false));
      st.play();
    } else {
      TranslateTransition tt = new TranslateTransition(Duration.millis(300), cardPane);
      tt.setFromX(- 355);
      tt.setToX(0);
      tt.setOnFinished(e -> {
        chatPic.setDisable(false);
      });
      KeyValue kv = new KeyValue(profilePane.prefHeightProperty(), 62, Interpolator.EASE_BOTH);
      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), kv));
      SequentialTransition st = new SequentialTransition(tt, timeline);
      st.play();
    }
  }

  private void fillChat(User user, User user1) {
    this.user1 = user1;
    try {
      Chat messages = clientHandler.getChat(user, user1);
      for (Map<String, String> map : messages.getMessages()) {
        if (map.containsKey(user1.getEmail())) {
          String s = stringFormatter(map.get(user1.getEmail()));
          textBox.getChildren().add(createMessage(s, false));
        } else {
          textBox.getChildren().add(createMessage(stringFormatter(map.get(user.getEmail())), true));
        }
      }
    } catch (IOException e) {
      Label label = new Label(e.getMessage());
      label.setFont(new Font(20));
      label.setAlignment(Pos.CENTER);
      label.setLayoutX(48);
      label.setLayoutY(140);
      anchorPane.getChildren().add(label);
    }
  }

  private String stringFormatter(String string) {
    if (string.charAt(0) == '"' && string.charAt(string.length() - 1) == '"') {
      return string.substring(1, string.length() - 1);
    } else {
      return string;
    }
  }

  public VBox getMatchBox() {
    return matchBox;
  }

  public VBox gettextBox() {
    return textBox;
  }
}
