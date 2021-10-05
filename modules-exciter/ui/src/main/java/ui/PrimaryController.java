package ui;

import core.*;
import json.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
    private Pane leftCard, rightCard, refresh,scorePane;
    private Exciter excite = new Exciter();
    private FileHandler fileHandler = new FileHandler();
    private ImageController imageController = new ImageController();
    private ArrayList<User> displayUsers;;
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    public void saveUserData(){
        fileHandler.createFile();
        fileHandler.saveUser(excite.getCurrentUser());
    }

    void onLike1() {
        TranslateTransition tt1 = translateCardY(leftCard, leftCard.getLayoutY()-55, -400);
        TranslateTransition tt2 = translateCardY(leftCard, 400, 0);
        FadeTransition ft1 = animateScore(false, true);
        FadeTransition ft2 = animateScore(false, false);
        ParallelTransition pt1 = new ParallelTransition(tt1,ft1);
        ParallelTransition pt2 = new ParallelTransition(tt2,ft2);
        SequentialTransition st = new SequentialTransition(pt1,pt2);
        st.play();
        excite.pressedLikeSecond();
        //animateCard(leftCard,leftCard.getLayoutY()-55, -400,false,false);
    }
    void onLike2() {
        excite.pressedLikeFirst();
        animateCard(rightCard,rightCard.getLayoutY()-55, -400,false,false);
    }
    void animateCard(Pane pane,double startPosition,double endPosition, boolean lastAnimation, boolean isRefresh){
        rightCard.setDisable(true);
        leftCard.setDisable(true);
        refresh.setDisable(true);
        double duration = Math.abs(endPosition-startPosition)*1.4;
        TranslateTransition tt = new TranslateTransition(Duration.millis(duration), pane);
        tt.setFromY(startPosition);
        tt.setToY(endPosition);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);
        if(!lastAnimation){
            if(!isRefresh){
                if(pane.getId().equals("leftCard")) animateScore(true,true);
                else animateScore(false,true);
            }
            tt.setOnFinished(e -> {
                if(!isRefresh)animateCard(pane,430,0,true,false);
                else animateCard(pane,430,0,true,true);
                setNextUsers();
        });
        }
        else tt.setOnFinished(e -> {
            if(!isRefresh){
                if(pane.getId().equals("leftCard")) animateScore(true,false);
                else animateScore(false,false);
            }
            refresh.setDisable(false);
            rightCard.setDisable(false);
            leftCard.setDisable(false);
        });
        tt.play();
    }
    public TranslateTransition translateCardY(Pane pane, double start, double end){
        TranslateTransition tt = new TranslateTransition(Duration.millis(Math.abs(start-end)*1.4),pane);
        tt.setFromY(start);
        tt.setToY(end);
        tt.setCycleCount(1);
        tt.autoReverseProperty();
        return tt;
    }
    public FadeTransition animateScore(boolean onLeftCard, boolean begin){
        if(onLeftCard){
            scorePane.setLayoutX(82.5);
            int count = excite.getOnScreenUserLikeCount(excite.getOnScreenUser1());
            scoreNumber.setText(String.valueOf(count));
        }
        else {
            scorePane.setLayoutX(352.5);
            int count = excite.getOnScreenUserLikeCount(excite.getOnScreenUser2());
            scoreNumber.setText(String.valueOf(count));
        }

        FadeTransition ft = new FadeTransition(Duration.millis(100),scorePane);
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
        animateCard(leftCard, 0, -385, false,true);
        animateCard(rightCard, 0, -385, false,true);
        RotateTransition rt = new RotateTransition(Duration.millis(500),refresh);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.play();
    }
    public void setNextUsers(){
        displayUsers = excite.getOnScreenUsers();
        User user1 = displayUsers.get(0);
        User user2 = displayUsers.get(1);
        leftPicture.setFill(new ImagePattern(imageController.getImage(excite.getOnScreenUser1())));
        rightPicture.setFill(new ImagePattern(imageController.getImage(excite.getOnScreenUser2())));
        Name1.setText(user1.getName());
        Age1.setText(String.valueOf(user1.getAge()));
        Name2.setText(user2.getName());
        Age2.setText(String.valueOf(user2.getAge()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leftPicture.setFill(new ImagePattern(imageController.getImage(excite.getOnScreenUser1())));
        rightPicture.setFill(new ImagePattern(imageController.getImage(excite.getOnScreenUser2())));
        profile.setFill(new ImagePattern(new Image(this.getClass().getResourceAsStream("Images/defaultPicture.png")),30.5,62,60,95,false));
        dragY(leftCard);
        dragY(rightCard);
        setNextUsers();
    }

    double dY = 0;
    double initialY = 0;
    Boolean dragged = false;
    double y = 0;
    double lastY = 0;

    private void dragY(Pane e){
        e.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                initialY = event.getSceneY();
                lastY = initialY;
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
                            onLike1();
                        }else{
                            onLike2();
                        }
                    }else{
                        animateCard(e, e.getLayoutY()-55, 0, true,true);
                    }
                    e.setLayoutY(55);
                    dragged = false;
                }
            }
        });
    }



}


