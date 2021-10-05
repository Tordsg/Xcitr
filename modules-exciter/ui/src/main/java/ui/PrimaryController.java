package ui;

import core.*;
import json.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.Node;

public class PrimaryController implements Initializable{
    @FXML
    private Rectangle leftPicture, rightPicture;
    @FXML
    private Circle profile;
    @FXML
    private Label Name1,Age1,Name2,Age2;
    @FXML
    private Pane leftCard, rightCard, refresh;
    private Exciter excite = new Exciter();
    private FileHandler fileHandler = new FileHandler();

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
        excite.pressedLikeSecond();
        animateCard(leftCard,leftCard.getLayoutY()-55, -400,false);
    }
    void onLike2() {
        excite.pressedLikeFirst();
        animateCard(rightCard,rightCard.getLayoutY()-55, -400,false);
    }
    void animateCard(Pane pane,double startPosition,double endPosition, boolean lastAnimation){
        refresh.setDisable(true);
        double duration = Math.abs(endPosition-startPosition);
        TranslateTransition tt = new TranslateTransition(Duration.millis(duration), pane);
        tt.setFromY(startPosition);
        tt.setToY(endPosition);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);
        if(!lastAnimation) tt.setOnFinished(e -> {
            animateCard(pane,430,0,true);
            setNextUsers();
        });
        else tt.setOnFinished(e -> refresh.setDisable(false));
        tt.play();
    }
    @FXML
    void refresh(){
        animateCard(leftCard, 0, -385, false);
        animateCard(rightCard, 0, -385, false);
        RotateTransition rt = new RotateTransition(Duration.millis(500),refresh);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.play();
      //  setUsers();
    }

    public void setUsers(){
        ArrayList<User> displayUsers = excite.getNextUsers();
        User user1 = displayUsers.get(0);
        User user2 = displayUsers.get(1);
        excite.setOnScreenUser(user1, user2);
        Name1.setText(user1.getName());
        Age1.setText(String.valueOf(user1.getAge()));
        Name2.setText(user2.getName());
        Age2.setText(String.valueOf(user2.getAge()));
    }
    public void setNextUsers(){
        ArrayList<User> displayUsers = excite.getOnScreenUsers();
        User user1 = displayUsers.get(0);
        User user2 = displayUsers.get(1);
        excite.setOnScreenUser(user1, user2);
        Name1.setText(user1.getName());
        Age1.setText(String.valueOf(user1.getAge()));
        Name2.setText(user2.getName());
        Age2.setText(String.valueOf(user2.getAge()));
    }
    public void updateUsers(){
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leftPicture.setFill(new ImagePattern(new Image(this.getClass().getResourceAsStream("Images/defaultPicture.png"))));
        rightPicture.setFill(new ImagePattern(new Image(this.getClass().getResourceAsStream("Images/defaultPicture.png"))));
        profile.setFill(new ImagePattern(new Image(this.getClass().getResourceAsStream("Images/defaultPicture.png")),30.5,62,60,95,false));
        dragY(leftCard);
        dragY(rightCard);
        setUsers();
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
                        animateCard(e, e.getLayoutY()-55, 0, true);
                    }
                    e.setLayoutY(55);
                    dragged = false;
                }
            }
        });
    }



}


