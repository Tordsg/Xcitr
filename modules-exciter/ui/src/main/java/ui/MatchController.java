package ui;

import user.User;
import java.io.IOException;
import java.net.URL;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Controller for match.fxml.
 */
public class MatchController implements Initializable {

  @FXML
  VBox matchBox, textBox;
  @FXML
  TextField textInput;
  @FXML
  Group backButton, sendButton;
  @FXML
  AnchorPane anchorPane, profilePane;

  protected final static ImageController imageController = PrimaryController.imageController;
  private ClientHandler clientHandler = new ClientHandler();
  private User user = App.getUser();
  private List<User> matches = new ArrayList<>();

  public void switchToPrimary() throws IOException {
    App.setRoot("primary");
  }

  /**
   * checks if user has any matches and set them in the match fxml file.
   */

  public void initialize(URL arg0, ResourceBundle arg1) {
    try {
      matches = clientHandler.getMatches(user);
    } catch (ServerException e) {
    }
    hoverButton(backButton);
    hoverButton(sendButton);
    hoverButton(profilePane);
    HBox one = createMessage("halllllllllaaaaaa heiii", false);
    HBox two = createMessage("a", true);
    HBox three = createMessage("Hei jeg synes du virker som en veldig artig person. Noen ganger tenker jeg at du ikke tenker like mye på andre som du egentlig burde ha gjort.", true);
    textBox.getChildren().add(one);
    textBox.getChildren().add(two);
    textBox.getChildren().add(three);
    if (matches != null && !matches.isEmpty()) {
      matches.forEach(e -> matchBox.getChildren().add(createMatchCard(e)));
      matchBox.getChildren().forEach(e -> hoverButton(e));
    }
    if (matchBox.getChildren().size() == 0) {
      Label label = new Label();
      label.setText("You have not matches, yet.");
      label.setFont(new Font(30));
      label.setAlignment(Pos.CENTER);
      label.setLayoutX(50);
      label.setLayoutY(100);
      anchorPane.getChildren().add(label);
    } else {
      matchBox.setOnScroll(new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent event) {
          matchBox.setLayoutY(matchBox.getLayoutY() + event.getDeltaY()/2);
        }
      });
      textBox.setOnScroll(new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent event) {
          if(textBox.getLayoutY() + textBox.getHeight() + event.getDeltaY()/2<393){
            textBox.setLayoutY(393-textBox.getHeight());
          } else if(textBox.getLayoutY() + event.getDeltaY()/2>63){
          }
            else {
          textBox.setLayoutY(textBox.getLayoutY() + event.getDeltaY()/2);
          }
        }
      });
    }
  }
  @FXML
  public void sendMessage(){
    if(textInput.getText().equals(""))return;
    HBox hBox = createMessage(textInput.getText(),true);
    textBox.getChildren().add(hBox);
    textInput.clear();
    if(textBox.getLayoutY() + textBox.getHeight()<393){
      textBox.setLayoutY(393-textBox.getHeight());
    }
    
  }
  private HBox createMessage(String string, Boolean isCurrentUser){
    HBox hBox = new HBox();
    Group group = new Group();
    Rectangle rectangle = new Rectangle();
    Text text = new Text();
    group.getChildren().add(rectangle);
    group.getChildren().add(text);
    rectangle.setFill(Color.rgb(220, 220, 220));
    text.setText(string);
    if(text.getLayoutBounds().getWidth()>150){
      text.setWrappingWidth(150);
    }
    rectangle.setWidth(text.getLayoutBounds().getWidth()+20);
    rectangle.setHeight(text.getLayoutBounds().getHeight()+20);
    if(rectangle.getHeight()<45){
      rectangle.setArcHeight(25);
      rectangle.setArcWidth(25);
    } else {
      rectangle.setArcHeight(40);
      rectangle.setArcWidth(40);
    }
    text.setLayoutY(rectangle.getHeight()/2-text.getLayoutBounds().getHeight()/2+12);
    text.setLayoutX(10);
    hBox.getChildren().add(group);
    if(isCurrentUser){
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

  protected static Group createMatchCard(User user) {
    Group group = new Group();
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
    rectangle.setFill(javafx.scene.paint.Color.WHITE);
    DropShadow dropShadow = new DropShadow();
    dropShadow.setOffsetX(5);
    dropShadow.setOffsetY(5);
    dropShadow.setColor(Color.rgb(0, 0, 0, 0.5));
    circle.setEffect(dropShadow);
    circle.setRadius(24);
    circle.setLayoutX(38);
    circle.setLayoutY(34);
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
}
