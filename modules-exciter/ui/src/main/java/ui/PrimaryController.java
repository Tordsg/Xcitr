package ui;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import user.User;

/**
 * Controller class for primary.fxml.
 */

public class PrimaryController implements Initializable {
  @FXML
  private Rectangle leftPicture;
  @FXML
  private Rectangle rightPicture;
  @FXML
  private Circle profile;
  @FXML
  private Circle notification;
  @FXML
  private Label name1;
  @FXML
  private Label age1;
  @FXML
  private Label name2;
  @FXML
  private Label age2;
  @FXML
  private Text bio1;
  @FXML
  private Text bio2;
  @FXML
  private Label errorLabel;
  @FXML
  private Text scoreNumber;
  @FXML
  private Group matchButton;
  @FXML
  private Pane leftCard;
  @FXML
  private Pane rightCard;
  @FXML
  private Pane refresh;
  @FXML
  private Pane scorePane;
  @FXML
  private Pane info1;
  @FXML
  private Pane info2;
  @FXML
  private Group group1;
  @FXML
  private Group group2;

  private ClientHandler clientHandler = new ClientHandler();
  private User user = App.getUser();
  private int numMatches = 0;
  private User leftUser = new User();
  private User rightUser = new User();
  // Static since it's shared by the ProfileController
  private static final ImageController imageController = new ImageController();

  /**
   * Method for switching to the profile.fxml.
   *
   * @param event MouseEvent object.
   */

  @FXML
  private void switchToProfile(MouseEvent event) {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("profile.fxml"));
    Parent p;
    try {
      p = loader.load();
      Scene s = new Scene(p);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(s);
      window.show();
    } catch (IOException e) {
      System.err.println("Error loading profile.fxml");
    }
  }

  /**
   * Method for switching to the match.fxml.
   *
   * @param event MouseEvent object
   */

  @FXML
  private void switchToMatch(MouseEvent event) {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("match.fxml"));
    Parent p;
    try {
      p = loader.load();
      Scene s = new Scene(p);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(s);
      window.show();
    } catch (IOException e) {
      System.err.println("Error loading match.fxml");
    }
  }

  /**
   * Adds lighting effect when hovering a button with the mouse.
   *
   * @param n node object
   */

  private void hoverButton(Node n) {
    n.setOnMouseEntered(e -> {
      n.setEffect(new Lighting());
    });
    n.setOnMouseExited(e -> {
      n.setEffect(null);
    });
  }

  /**
   * Method to describe what happens when the left card gets swiped away.
   */

  void onDiscardLeftCard() {
    int likeCount = 0;
    User user2 = leftUser;
    try {
      leftUser = clientHandler.discardCard(user, rightUser, leftUser);
      likeCount = clientHandler.getUserLikeCount(user, rightUser);
    } catch (ServerException e) {
      errorLabel.setText(e.getMessage());
    } catch (IOException e) {
      errorLabel.setText(e.getMessage());
    }
    if (likeCount == 3) {
      List<User> users = new ArrayList<User>();
      users.add(user2);
      users.add(user);
      users.add(leftUser);
      users.add(rightUser);
      try {
        rightUser = clientHandler.getUser(user, users);
      } catch (ServerException | ConnectException e) {
        errorLabel.setText(e.getMessage());
      }
      cardLiked(rightCard, leftCard);
      return;
    }
    leftCard.setDisable(true);
    rightCard.setDisable(true);
    refresh.setDisable(true);
    TranslateTransition tt1 = translateCardY(leftCard, leftCard.getLayoutY() - 55, -400, true);
    TranslateTransition tt2 = translateCardY(leftCard, 400, 0, false);
    FadeTransition ft1 = animateScore("leftCard", true);
    FadeTransition ft2 = animateScore("leftCard", false);
    ParallelTransition pt1 = new ParallelTransition(tt1, ft1);
    SequentialTransition st = new SequentialTransition(pt1, tt2, ft2);
    st.play();
  }

  /**
   * Method to describe what happens when the right card gets swiped away.
   */
  void onDiscardRightCard() {
    int likeCount = 0;
    User user2 = rightUser;
    try {
      rightUser = clientHandler.discardCard(user, leftUser, rightUser);
      likeCount = clientHandler.getUserLikeCount(user, leftUser);
    } catch (ServerException e) {
      errorLabel.setText(e.getMessage());
    } catch (IOException e) {
      errorLabel.setText(e.getMessage());
    }
    if (likeCount == 3) {
      List<User> users = new ArrayList<User>();
      users.add(user2);
      users.add(user);
      users.add(leftUser);
      users.add(rightUser);
      try {
        leftUser = clientHandler.getUser(user, users);
      } catch (ServerException | ConnectException e) {
        errorLabel.setText(e.getMessage());
      }
      cardLiked(leftCard, rightCard);
      return;
    }
    leftCard.setDisable(true);
    rightCard.setDisable(true);
    refresh.setDisable(true);
    TranslateTransition tt1 = translateCardY(rightCard, rightCard.getLayoutY() - 55, -400, true);
    TranslateTransition tt2 = translateCardY(rightCard, 400, 0, false);
    FadeTransition ft1 = animateScore("rightCard", true);
    FadeTransition ft2 = animateScore("rightCard", false);
    ParallelTransition pt1 = new ParallelTransition(tt1, ft1);
    SequentialTransition st = new SequentialTransition(pt1, tt2, ft2);
    st.play();
  }

  /**
   * Indication for what card is liked.
   *
   * @param likedcard Pane object
   * @param discardedcard Pane object
   */

  public void cardLiked(Pane likedcard, Pane discardedcard) {
    TranslateTransition ttScore = new TranslateTransition(Duration
        .millis(Math.abs(- likedcard.getLayoutX() - 300)),
        scorePane);
    ttScore.setFromX(0);
    ttScore.setToX(- likedcard.getLayoutX() - 300);
    ttScore.setCycleCount(1);
    ttScore.setAutoReverse(true);
    TranslateTransition tt = translateCardY(likedcard, 400, 0, false);
    tt.setFromX(0);
    tt.setToX(0);
    tt.setOnFinished(e -> {
      try {
        int num = clientHandler.getMatches(user).size();
        if (num > numMatches) {
          numMatches = num;
          notification.setVisible(true);
        }
      } catch (ServerException | ConnectException e1) {
        errorLabel.setText(e1.getMessage());
      }
    });
    TranslateTransition ttCard = new TranslateTransition(Duration
        .millis(Math.abs(-likedcard.getLayoutX() - 300)),
        likedcard);
    ttCard.setFromX(0);
    ttCard.setToX(-likedcard.getLayoutX() - 300);
    ttCard.setCycleCount(1);
    ttCard.setAutoReverse(true);
    ttCard.setOnFinished(e -> {
      setNextUsers();
    });
    FadeTransition ft = animateScore(discardedcard.getId(), false);
    ft.setOnFinished(e -> {
      ft.getNode().setTranslateX(0);
      ft.getNode().setLayoutX(-200);
    });
    TranslateTransition firstTt = translateCardY(discardedcard,
        discardedcard.getLayoutY() - 55, -400, false);
    firstTt.setOnFinished(e -> e.consume());
    SequentialTransition st = new SequentialTransition(
        new ParallelTransition(firstTt, animateScore(discardedcard.getId(), true)),
        new ParallelTransition(ttScore, ttCard),
        new ParallelTransition(translateCardY(discardedcard, 400, 0, false), tt, ft));
    scoreNumber.setText("3");
    st.play();
  }

  /**
   * Moving card along the y-axis.
   *
   * @param pane pane object
   * @param start double
   * @param end double
   * @param updateOnFinish boolean
   *
   * @return translate transition
   */
  public TranslateTransition translateCardY(Pane pane,
      double start, double end, boolean updateOnFinish) {
    TranslateTransition tt = new TranslateTransition(Duration.millis(Math.abs(start - end)), pane);
    tt.setFromY(start);
    tt.setToY(end);
    tt.setCycleCount(1);
    tt.setAutoReverse(true);
    pane.setLayoutY(55);
    if (updateOnFinish) {
      tt.setOnFinished(e -> setNextUsers());
    } else {
      tt.setOnFinished(e -> {
        leftCard.setDisable(false);
        rightCard.setDisable(false);
        refresh.setDisable(false);
      });
    }
    return tt;
  }

  /**
   * Fading transition for swiping the cards on the match page.
   *
   * @param discardedCard string
   * @param begin boolean
   *
   * @return fade transition
   */
  public FadeTransition animateScore(String discardedCard, boolean begin) {
    FadeTransition ft = new FadeTransition(Duration.millis(100), scorePane);
    if (discardedCard.equals("rightCard")) {
      ft.getNode().setLayoutX(82.5);
      Integer count = null;
      try {
        count = clientHandler.getUserLikeCount(user, leftUser);
      } catch (ServerException e) {
        errorLabel.setText(e.getMessage());
      } catch (IOException e) {
        errorLabel.setText(e.getMessage());
      }
      if (count != null) {
        scoreNumber.setText(count.toString());
      }
    } else {
      ft.getNode().setLayoutX(352.5);
      Integer count = null;
      try {
        count = clientHandler.getUserLikeCount(user, rightUser);
      } catch (ServerException | ConnectException e) {
        errorLabel.setText(e.getMessage());
      }
      if (count != null) {
        scoreNumber.setText(count.toString());
      }
    }
    if (begin) {
      ft.setFromValue(0);
      ft.setToValue(1);
    } else {
      ft.setFromValue(1);
      ft.setToValue(0);
      ft.setOnFinished(e -> scorePane.setLayoutX(-200));
    }
    ft.setCycleCount(1);
    ft.setAutoReverse(true);
    return ft;
  }

  /**
   * When refresh is pushed new possible matches will appear.
   */

  @FXML
  void refresh() {
    try {
      List<User> users = clientHandler.getTwoUsers(user);
      leftUser = users.get(0);
      rightUser = users.get(1);
    } catch (ServerException | IndexOutOfBoundsException e) {
      errorLabel.setText(e.getMessage());
    } catch (IOException e) {
      errorLabel.setText(e.getMessage());

    }
    scorePane.setDisable(true);
    leftCard.setDisable(true);
    rightCard.setDisable(true);
    refresh.setDisable(true);
    TranslateTransition ltt1 = translateCardY(leftCard, leftCard.getLayoutY() - 55, -400, true);
    TranslateTransition ltt2 = translateCardY(leftCard, 400, 0, false);
    TranslateTransition rtt1 = translateCardY(rightCard, rightCard.getLayoutY() - 55, -400, true);
    TranslateTransition rtt2 = translateCardY(rightCard, 400, 0, false);
    ParallelTransition pt1 = new ParallelTransition(ltt1, rtt1);
    ParallelTransition pt2 = new ParallelTransition(ltt2, rtt2);
    SequentialTransition st = new SequentialTransition(pt1, pt2);
    st.play();
    RotateTransition rt = new RotateTransition(Duration.millis(500), refresh);
    rt.setFromAngle(0);
    rt.setToAngle(360);
    rt.play();
  }

  /**
   * Sets which users that will be on the matchcards.
   */

  public void setNextUsers() {
    leftPicture.setFill(imageController.getImage(leftUser));
    rightPicture.setFill(imageController.getImage(rightUser));
    name1.setText(leftUser.getName());
    age1.setText(String.valueOf(leftUser.getAge()));
    bio1.setText(leftUser.getUserInformation());
    if(bio1.getText().isEmpty()){
      info1.setPrefHeight(40);
      group1.setLayoutY(298);
    } else {
      info1.setPrefHeight(45 + bio1.getLayoutBounds().getHeight());
      Platform.runLater(()-> group1.setLayoutY(338-info1.getHeight()));
    }
    info1.setPrefHeight(bio1.getLayoutBounds().getHeight() + 50);
    info1.setLayoutY(leftPicture.getHeight() - info1.getHeight());
    name2.setText(rightUser.getName());
    age2.setText(String.valueOf(rightUser.getAge()));
    bio2.setText(rightUser.getUserInformation());
    if(bio2.getText().isEmpty()){
      info2.setPrefHeight(40);
      group2.setLayoutY(298);
    } else {
      info2.setPrefHeight(45 + bio2.getLayoutBounds().getHeight());
      Platform.runLater(()-> group2.setLayoutY(338-info2.getHeight()));
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    user = App.getUser();
    if(imageController.getListSize() < 25) {
      imageController.fillUpPicture();
    }
    try {
      numMatches = clientHandler.getMatches(user).size();
      List<User> users = clientHandler.getTwoUsers(user);
      leftUser = users.get(0);
      rightUser = users.get(1);
    } catch (ServerException | IndexOutOfBoundsException e) {
      errorLabel.setText(e.getMessage());
    } catch (IOException e) {
      errorLabel.setText(e.getMessage());

    }
    profile.setFill(new ImagePattern(imageController
        .getImage(user).getImage(), 0, 0, 1, 1.4, true));
    dragY(leftCard);
    dragY(rightCard);
    hoverButton(refresh);
    hoverButton(matchButton);
    hoverButton(profile);
    setNextUsers();
  }

  double variabledY = 0;
  Boolean dragged = false;
  double yvariable = 0;
  double lastY = 0;

  private void dragY(Pane e) {
    e.setOnMousePressed(k -> {
      lastY = k.getSceneY();

    });
    e.setOnMouseDragged(k -> {
      yvariable = k.getSceneY();
      variabledY = yvariable - lastY;
      lastY = yvariable;
      double cardPosition = variabledY + e.getLayoutY();
      dragged = true;
      if (cardPosition > 55 && variabledY > 0) {
        double posY = e.getLayoutY() - 55;
        variabledY = variabledY * (1 / (1 + posY * posY));
        e.setLayoutY(e.getLayoutY() + variabledY);
      } else {
        e.setLayoutY(cardPosition);
      }
    });
    e.setOnMouseReleased(k -> {
      if (dragged) {
        if (e.getLayoutY() < 0) {
          if (e.getId().equals("leftCard")) {
            onDiscardLeftCard();
          } else {
            onDiscardRightCard();
          }
        } else {
          translateCardY(e, e.getLayoutY() - 55, 0, false).play();
        }
        dragged = false;
      }
    });
  }

  public List<User> getOnScreenUsers() {
    return List.of(leftUser, rightUser);
  }

  public Circle getNotificationCircle() {
    return notification;
  }

  public static ImageController getImageController() {
    return imageController;
  }

}
