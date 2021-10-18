package ui;

import core.*;
import json.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;

public class PrimaryController implements Initializable{
    @FXML
    private Rectangle leftPicture, rightPicture;
    @FXML
    private Circle profile;
    @FXML
    private Label Name1,Age1,Name2,Age2;
    @FXML
    private Text scoreNumber;
    @FXML
    private Group matchButton;
    @FXML
    private Pane leftCard, rightCard, refresh,scorePane;
    private Exciter excite = App.exciter;
    protected static FileHandler fileHandler = LoginController.fileHandler;
    //Static since it's shared by the SecondaryController
    protected static ImageController imageController = new ImageController();
    protected static List<User> matches;
    private List<User> displayUsers;
    @FXML
    private void switchToSecondary() throws IOException {
        saveUserData();
        App.setRoot("profile");
    }
    @FXML
    private void switchToMatch() throws IOException {
        saveUserData();
        //MatchController.matches = excite.getCurrentUserMatches();
        App.setRoot("match");
    }
    @FXML
    public void saveUserData(){
        fileHandler.createFile();
        List<User> users = excite.getAllUsers();
        boolean hasUser = users.stream().anyMatch(e -> e.getClass().getName().equals("core.User"));
        if(!hasUser) users.add(excite.getCurrentUser());
        fileHandler.saveUser(users);
    }
    private void hoverButton(Node n){
        n.setOnMouseEntered(e -> {
            n.setEffect(new Lighting());
        });
        n.setOnMouseExited(e -> {
            n.setEffect(null);
        });
    }
    void onDiscardLeftCard() {
        if(excite.discardFirst()) {
            cardLiked(rightCard,leftCard);
            return;
        }
        leftCard.setDisable(true);
        rightCard.setDisable(true);
        refresh.setDisable(true);
        TranslateTransition tt1 = translateCardY(leftCard, leftCard.getLayoutY()-55, -400,true);
        TranslateTransition tt2 = translateCardY(leftCard, 400, 0,false);
        FadeTransition ft1 = animateScore("leftCard", true);
        FadeTransition ft2 = animateScore("leftCard", false);
        ParallelTransition pt1 = new ParallelTransition(tt1,ft1);
        SequentialTransition st = new SequentialTransition(pt1,tt2,ft2);
        st.play();
    }
    void onDiscardRightCard() {
        leftCard.setDisable(true);
        rightCard.setDisable(true);
        refresh.setDisable(true);
        if(excite.discardSecond()) {
            cardLiked(leftCard,rightCard);
            return;
        }
        TranslateTransition tt1 = translateCardY(rightCard, rightCard.getLayoutY()-55, -400,true);
        TranslateTransition tt2 = translateCardY(rightCard, 400, 0,false);
        FadeTransition ft1 = animateScore("rightCard", true);
        FadeTransition ft2 = animateScore("rightCard", false);
        ParallelTransition pt1 = new ParallelTransition(tt1,ft1);
        SequentialTransition st = new SequentialTransition(pt1,tt2,ft2);
        st.play();
    }
    public void cardLiked(Pane likedcard, Pane discardedcard){
        TranslateTransition ttScore = new TranslateTransition(Duration.millis(Math.abs(-likedcard.getLayoutX()-300)),scorePane);
        ttScore.setFromX(0);
        ttScore.setToX(-likedcard.getLayoutX()-300);
        ttScore.setCycleCount(1);
        ttScore.setAutoReverse(true);
        TranslateTransition tt = translateCardY(likedcard, 400, 0,false);
        tt.setFromX(0);
        tt.setToX(0);
        TranslateTransition ttCard = new TranslateTransition(Duration.millis(Math.abs(-likedcard.getLayoutX()-300)),likedcard);
        ttCard.setFromX(0);
        ttCard.setToX(-likedcard.getLayoutX()-300);
        ttCard.setCycleCount(1);
        ttCard.setAutoReverse(true);
        ttCard.setOnFinished(e -> {
            setNextUsers();
        }
        );
        FadeTransition ft = animateScore(discardedcard.getId(), false);
        ft.setOnFinished(e-> {
            ft.getNode().setTranslateX(0);
            ft.getNode().setLayoutX(-200);
        });
        TranslateTransition firstTT = translateCardY(discardedcard,discardedcard.getLayoutY()-55,-400,false);
        firstTT.setOnFinished(e-> e.consume());
        SequentialTransition st = new SequentialTransition(
            new ParallelTransition(
                firstTT,
                animateScore(discardedcard.getId(), true))
            ,
            new ParallelTransition(
                ttScore,ttCard)
            ,

            new ParallelTransition(
                translateCardY(discardedcard, 400, 0,false),
                tt,ft)
            );
        st.play();
    }
    public TranslateTransition translateCardY(Pane pane, double start, double end, boolean updateOnFinish){
        TranslateTransition tt = new TranslateTransition(Duration.millis(Math.abs(start-end)),pane);
        tt.setFromY(start);
        tt.setToY(end);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);
        pane.setLayoutY(55);
        if(updateOnFinish){
            tt.setOnFinished(e -> setNextUsers());
        } else {
            tt.setOnFinished(e -> {leftCard.setDisable(false);
            rightCard.setDisable(false);
            refresh.setDisable(false);
            });
        }
        return tt;
    }
    public FadeTransition animateScore(String discardedCard, boolean begin){
        FadeTransition ft = new FadeTransition(Duration.millis(100),scorePane);
        if(discardedCard.equals("rightCard")){
            ft.getNode().setLayoutX(82.5);
            int count = excite.getOnScreenUserLikeCount(excite.getOnScreenUser1());
            scoreNumber.setText(String.valueOf(count));
        }
        else {
            ft.getNode().setLayoutX(352.5);
            int count = excite.getOnScreenUserLikeCount(excite.getOnScreenUser2());
            scoreNumber.setText(String.valueOf(count));
        }
        if(begin){
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
    @FXML
    void refresh(){
        excite.refreshUsers();
        scorePane.setDisable(true);
        leftCard.setDisable(true);
        rightCard.setDisable(true);
        refresh.setDisable(true);
        TranslateTransition ltt1 = translateCardY(leftCard, leftCard.getLayoutY()-55, -400,true);
        TranslateTransition ltt2 = translateCardY(leftCard, 400, 0,false);
        TranslateTransition rtt1 = translateCardY(rightCard, rightCard.getLayoutY()-55, -400,true);
        TranslateTransition rtt2 = translateCardY(rightCard, 400, 0,false);
        ParallelTransition pt1 = new ParallelTransition(ltt1,rtt1);
        ParallelTransition pt2 = new ParallelTransition(ltt2,rtt2);
        SequentialTransition st = new SequentialTransition(pt1,pt2);
        st.play();
        RotateTransition rt = new RotateTransition(Duration.millis(500),refresh);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.play();
    }
    public void setNextUsers(){
        displayUsers = excite.getOnScreenUsers();
        User user1 = displayUsers.get(0);
        User user2 = displayUsers.get(1);
        leftPicture.setFill(imageController.getImage(excite.getOnScreenUser1()));
        rightPicture.setFill(imageController.getImage(excite.getOnScreenUser2()));
        Name1.setText(user1.getName());
        Age1.setText(String.valueOf(user1.getAge()));
        Name2.setText(user2.getName());
        Age2.setText(String.valueOf(user2.getAge()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leftPicture.setFill(imageController.getImage(excite.getOnScreenUser1()));
        rightPicture.setFill(imageController.getImage(excite.getOnScreenUser2()));
        profile.setFill(imageController.getImage(excite.getCurrentUser()));
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

    private void dragY(Pane e){
        e.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                lastY = event.getSceneY();

            }
        });
        e.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                y = event.getSceneY();
                dY = y - lastY;
                lastY = y;
                double cardPosition = dY + e.getLayoutY();
                dragged = true;
                if (cardPosition>55&&dY>0){
                    double posY = e.getLayoutY()-55;
                    dY = dY*(1/(1+posY*posY));
                    e.setLayoutY(e.getLayoutY() + dY);
                } else {
                    e.setLayoutY(cardPosition);
                }
            }
        });
        e.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if(dragged){
                    if(e.getLayoutY()<0){
                        if(e.getId().equals("leftCard")){
                            onDiscardLeftCard();
                        }else{
                            onDiscardRightCard();
                        }
                    }else{
                        translateCardY(e, e.getLayoutY()-55, 0, false).play();
                    }
                    dragged = false;
                }
            }
        });
    }



}


