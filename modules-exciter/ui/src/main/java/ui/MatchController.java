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
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
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
  VBox matchBox, textBox;
  @FXML
  Pane textPane, refresh;
  @FXML
  TextField textInput;
  @FXML
  Group backButton, sendButton;
  @FXML
  AnchorPane anchorPane, profilePane;
  @FXML
  Circle chatPic;
  @FXML
  Text nameUser;

  private int chatId;
  private final static ImageController imageController = new ImageController();
  private ClientHandler clientHandler = new ClientHandler();
  private User user = App.getUser();
  private User user1;
  private List<User> matches = new ArrayList<>();
  private Pane cardPane;

  public void switchToPrimary(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("primary.fxml"));
    Parent p = loader.load();
    Scene s = new Scene(p);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(s);
    window.show();
  }

  /**
   * checks if user has any matches and set them in the match fxml file.
   */

  public void initialize(URL arg0, ResourceBundle arg1) {
    textPane.setLayoutX(640);
    try {
      matches = clientHandler.getMatches(user);
    } catch (IOException e) {
      Label label = new Label();
      label.setText(e.getMessage());
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
      fillChat(user, matches.get(0));
      nameUser.setText(matches.get(0).getName());
    }

    if (matches != null && !matches.isEmpty()) {
      matches.forEach(e -> matchBox.getChildren().add(createMatchCard(e)));
      matchBox.getChildren().forEach(e -> hoverButton(e));
      for (int i = 0; i < matchBox.getChildren().size(); i++) {
        clickButton((Group) matchBox.getChildren().get(i), matches.get(i), i);
      }
    }
    if (matchBox.getChildren().size() == 0) {
      Label label = new Label();
      label.setText("You have not matches, yet.");
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
        } else
          matchBox.setLayoutY(matchBox.getLayoutY() + k.getDeltaY() / 2);
      });
      textBox.setOnScroll(k -> {
        if (textBox.getLayoutY() + textBox.getHeight() + k.getDeltaY() / 2 < 393) {
          textBox.setLayoutY(393 - textBox.getHeight());
        } else if (textBox.getLayoutY() + k.getDeltaY() / 2 > 63 && textBox.getHeight() < 325)
          textBox.setLayoutY(393 - textBox.getHeight());
        else if (textBox.getLayoutY() + k.getDeltaY() / 2 > 63) {
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

  @FXML
  public void sendMessage() {
    if (textInput.getText().equals("") || matches == null || matches.isEmpty())
      return;
    HBox hBox = createMessage(textInput.getText(), true);
    try {
      clientHandler.sendMessage(user, matches.get(chatId), textInput.getText());
    } catch (IOException e) {
      Label label = new Label();
      label.setText(e.getMessage());
      label.setFont(new Font(20));
      label.setAlignment(Pos.CENTER);
      label.setLayoutX(48);
      label.setLayoutY(140);
      anchorPane.getChildren().add(label);    }
    textInput.clear();
    textBox.getChildren().add(hBox);
    textBox.setLayoutY(393 - textBox.getHeight() - height);
  }

  private HBox createMessage(String string, Boolean isCurrentUser) {
    HBox hBox = new HBox();
    Group group = new Group();
    Rectangle rectangle = new Rectangle();
    Text text = new Text();
    group.getChildren().add(rectangle);
    group.getChildren().add(text);
    rectangle.setFill(Color.rgb(220, 220, 220));
    text.setText(string);
    if (text.getLayoutBounds().getWidth() > 150) {
      text.setWrappingWidth(150);
    }
    rectangle.setWidth(text.getLayoutBounds().getWidth() + 20);
    rectangle.setHeight(text.getLayoutBounds().getHeight() + 20);
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
    hBox.getChildren().add(group);
    if (isCurrentUser) {
      rectangle.setFill(Color.rgb(0, 190, 255));
      text.setFill(Color.WHITE);
      hBox.setAlignment(Pos.TOP_RIGHT);
    }
    return hBox;
  }

  private void hoverButton(Node n) {
    n.setOnMouseEntered(e -> {
      n.setEffect(new Lighting());
    });
    n.setOnMouseExited(e -> {
      n.setEffect(null);
    });
  }

  Group lastN = null;

  private void clickButton(Group n, User user1, int i) {
    this.user1 = user1;
    n.setOnMouseClicked(e -> {
      if (lastN != null) {
        if (lastN.equals(n))
          return;
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
          if (profilePane.getPrefHeight() == 430) {
            if(cardPane!=null)profilePane.getChildren().remove(cardPane);
            cardPane = SecondaryController.createCard(user1);
            profilePane.getChildren().add(cardPane);
            cardPane.setLayoutY(70);
            cardPane.setLayoutX(45);
          }
          fillChat(user, user1);
          textBox.setLayoutY(393);
          textInput.clear();
          chatPic.setFill(new ImagePattern(imageController.getImage(user1).getImage(), 0, 0, 1, 1.4, true));
        });
        TranslateTransition ttIn = new TranslateTransition(Duration.millis(400), textPane);
        ttIn.setOnFinished(l -> {
          matchBox.getChildren().forEach(h -> h.setDisable(false));
          textBox.setLayoutY(393 - textBox.getHeight());
        });
        ttIn.setFromX(0);
        ttIn.setToX(-320);
        SequentialTransition st = new SequentialTransition(ttOut, new TranslateTransition(Duration.millis(200)), ttIn);
        st.play();
      } else {
        chatId = i;
        nameUser.setText(user1.getName());
        textBox.getChildren().clear();
        textBox.setLayoutY(63);
        if (profilePane.getPrefHeight() == 430) {
          if(cardPane!=null)profilePane.getChildren().remove(cardPane);
          cardPane = SecondaryController.createCard(user1);
          profilePane.getChildren().add(cardPane);
          cardPane.setLayoutY(70);
          cardPane.setLayoutX(45);
        }
        fillChat(user, user1);
        textBox.setLayoutY(393);
        textInput.clear();
        chatPic.setFill(new ImagePattern(imageController.getImage(user1).getImage(), 0, 0, 1, 1.4, true));
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

  double height = 0;

  protected static Group createMatchCard(User user) {
    Group group = new Group();
    group.setTranslateX(-5);
    Rectangle rectangle = new Rectangle();
    Circle circle = new Circle();
    Text text = new Text();
    Label label = new Label();
    group.getChildren().add(rectangle);
    group.getChildren().add(circle);
    group.getChildren().add(text);
    group.getChildren().add(label);
    rectangle.setWidth(296);
    rectangle.setHeight(70);
    rectangle.setArcHeight(70);
    rectangle.setArcWidth(70);
    rectangle.setStroke(Color.GREY);
    rectangle.setStrokeWidth(0);
    rectangle.setFill(javafx.scene.paint.Color.WHITE);
    DropShadow dropShadow = new DropShadow();
    dropShadow.setOffsetX(5);
    dropShadow.setOffsetY(5);
    dropShadow.setColor(Color.rgb(0, 0, 0, 0.5));
    circle.setEffect(dropShadow);
    circle.setRadius(24);
    circle.setLayoutX(38);
    circle.setLayoutY(34);
    circle.setFill(new ImagePattern(imageController.getImage(user).getImage(), 0, 0, 1, 1.4, true));
    text.setLayoutX(73);
    text.setLayoutY(32);
    text.setText(user.getName());
    text.setFont(Font.font("System", 19));
    label.setLayoutX(73);
    label.setLayoutY(43);
    label.setPrefWidth(200);
    label.setMaxWidth(label.getPrefWidth());
    return group;
  }

  @FXML
  public void animateProfile() {
    chatPic.setDisable(true);
    if (profilePane.getPrefHeight() != 430) {
      KeyValue kv = new KeyValue(profilePane.prefHeightProperty(), 430, Interpolator.EASE_BOTH);
      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), kv));
      cardPane = SecondaryController.createCard(user1);
      profilePane.getChildren().add(cardPane);
      cardPane.setLayoutY(70);
      TranslateTransition tt = new TranslateTransition(Duration.millis(300), cardPane);
      tt.setFromX(400);
      tt.setToX(45);
      SequentialTransition st = new SequentialTransition(timeline, tt);
      st.setOnFinished(e -> chatPic.setDisable(false));
      st.play();
    } else {
      KeyValue kv = new KeyValue(profilePane.prefHeightProperty(), 62, Interpolator.EASE_BOTH);
      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), kv));
      cardPane = (Pane) profilePane.getChildren().get(1);
      TranslateTransition tt = new TranslateTransition(Duration.millis(300), cardPane);
      tt.setFromX(45);
      tt.setToX(400);
      tt.setOnFinished(e -> {
        profilePane.getChildren().remove(cardPane);
        chatPic.setDisable(false);
      });
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
          String string = map.get(user1.getEmail());
          String string2 = string.substring(0, string.length());
          HBox hBox = createMessage(string2, false);
          textBox.getChildren().add(hBox);
        } else {
          String string = map.get(user.getEmail());
          String string2 = string.substring(1, string.length() - 1);
          HBox hBox = createMessage(string2, true);
          textBox.getChildren().add(hBox);
        }
      }
    } catch (IOException e) {
      Label label = new Label();
      label.setText(e.getMessage());
      label.setFont(new Font(20));
      label.setAlignment(Pos.CENTER);
      label.setLayoutX(48);
      label.setLayoutY(140);
      anchorPane.getChildren().add(label);    }
  }
}
