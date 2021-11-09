package ui;

import java.io.IOException;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import user.User;

public class PrimaryController implements Initializable {
  @FXML
  private Rectangle leftPicture, rightPicture;
  @FXML
  private Circle profile;
  @FXML
  private Label Name1, Age1, Name2, Age2;
  @FXML
  private Text scoreNumber;
  @FXML
  private Group matchButton;
  @FXML
  private Pane leftCard, rightCard, refresh, scorePane;

  private ClientHandler clientHandler = new ClientHandler();
  private User user = App.getUser();

  private User leftUser;
  private User rightUser;
  // Static since it's shared by the SecondaryController
  protected final static ImageController imageController = new ImageController();

  @FXML
  private void switchToSecondary() throws IOException {
    App.setRoot("profile");
  }

  @FXML
  private void switchToMatch() throws IOException {
    // MatchController.matches = excite.getCurrentUserMatches();
    App.setRoot("match");
  }

  private void hoverButton(Node n) {
    n.setOnMouseEntered(e -> {
      n.setEffect(new Lighting());
    });
    n.setOnMouseExited(e -> {
      n.setEffect(null);
    });
  }

  void onDiscardLeftCard() {
    int likeCount = 0;
    User user2 = leftUser;
    try{
      leftUser = clientHandler.discardCard(user, rightUser, leftUser);
      likeCount = clientHandler.getUserLikeCount(user, rightUser);
    } catch (ServerException e) {
      e.printStackTrace();
    }
    if (likeCount == 3) {
      List<User> users = new ArrayList<User>();
      users.add(user2);
      users.add(user);
      users.add(leftUser);
      users.add(rightUser);
      try {
        rightUser = clientHandler.getUser(user,users);
      } catch (ServerException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
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

  void onDiscardRightCard() {
    int likeCount = 0;
    User user2 = rightUser;
    try {
      rightUser = clientHandler.discardCard(user, leftUser, rightUser);
      likeCount = clientHandler.getUserLikeCount(user, leftUser);
    } catch (ServerException e) {
      e.printStackTrace();
    }
    if (likeCount == 3) {
      List<User> users = new ArrayList<User>();
      users.add(user2);
      users.add(user);
      users.add(leftUser);
      users.add(rightUser);
      try {
        rightUser = clientHandler.getUser(user,users);
      } catch (ServerException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
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

  public void cardLiked(Pane likedcard, Pane discardedcard) {
    TranslateTransition ttScore = new TranslateTransition(Duration.millis(Math.abs(-likedcard.getLayoutX() - 300)),
        scorePane);
    ttScore.setFromX(0);
    ttScore.setToX(-likedcard.getLayoutX() - 300);
    ttScore.setCycleCount(1);
    ttScore.setAutoReverse(true);
    TranslateTransition tt = translateCardY(likedcard, 400, 0, false);
    tt.setFromX(0);
    tt.setToX(0);
    TranslateTransition ttCard = new TranslateTransition(Duration.millis(Math.abs(-likedcard.getLayoutX() - 300)),
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
    TranslateTransition firstTT = translateCardY(discardedcard, discardedcard.getLayoutY() - 55, -400, false);
    firstTT.setOnFinished(e -> e.consume());
    SequentialTransition st = new SequentialTransition(
        new ParallelTransition(firstTT, animateScore(discardedcard.getId(), true)),
        new ParallelTransition(ttScore, ttCard),
        new ParallelTransition(translateCardY(discardedcard, 400, 0, false), tt, ft));
    scoreNumber.setText("3");
    st.play();
  }

  public TranslateTransition translateCardY(Pane pane, double start, double end, boolean updateOnFinish) {
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

  public FadeTransition animateScore(String discardedCard, boolean begin) {
    FadeTransition ft = new FadeTransition(Duration.millis(100), scorePane);
    if (discardedCard.equals("rightCard")) {
      ft.getNode().setLayoutX(82.5);
      Integer count = null;
      try {
        count = clientHandler.getUserLikeCount(user, leftUser);
      } catch (ServerException e) {
      }
      if(count != null) scoreNumber.setText(count.toString());
    } else {
      ft.getNode().setLayoutX(352.5);
      Integer count = null;
      try {
        count = clientHandler.getUserLikeCount(user, rightUser);
      } catch (ServerException e) {
      }
      if(count != null) scoreNumber.setText(count.toString());
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
      // TODO: handle exception
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
    Name1.setText(leftUser.getName());
    Age1.setText(String.valueOf(leftUser.getAge()));
    Name2.setText(rightUser.getName());
    Age2.setText(String.valueOf(rightUser.getAge()));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    user = App.getUser();
    try {
      List<User> users = clientHandler.getTwoUsers(user);
      leftUser = users.get(0);
      rightUser = users.get(1);
    } catch (ServerException | IndexOutOfBoundsException e) {
      // TODO: handle exception
    }
    leftPicture.setFill(imageController.getImage(leftUser));
    rightPicture.setFill(imageController.getImage(rightUser));
    profile.setFill(new ImagePattern(imageController.getImage(user).getImage(), 0, 0, 1, 1.4, true));
    dragY(leftCard);
    dragY(rightCard);
    hoverButton(refresh);
    hoverButton(matchButton);
    hoverButton(profile);
    setNextUsers();
  }

  double dY = 0;
  Boolean dragged = false;
  double y = 0;
  double lastY = 0;

  private void dragY(Pane e) {
    e.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        lastY = event.getSceneY();
      }
    });
    e.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        y = event.getSceneY();
        dY = y - lastY;
        lastY = y;
        double cardPosition = dY + e.getLayoutY();
        dragged = true;
        if (cardPosition > 55 && dY > 0) {
          double posY = e.getLayoutY() - 55;
          dY = dY * (1 / (1 + posY * posY));
          e.setLayoutY(e.getLayoutY() + dY);
        } else {
          e.setLayoutY(cardPosition);
        }
      }
    });
    e.setOnMouseReleased(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
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
      }
    });
  }

}
