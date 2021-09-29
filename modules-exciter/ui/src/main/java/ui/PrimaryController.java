package ui;

import core.*;
import json.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class PrimaryController implements Initializable{

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
    @FXML
    private Rectangle leftPicture, rightPicture;
    @FXML 
    private Circle profile;
    @FXML
    private Button Like1;

    @FXML
    private Label Name1;

    @FXML
    private Label Bio1;

    @FXML
    private Label Age1;

    @FXML
    private Button Like2;

    @FXML
    private Label Name2;

    @FXML
    private Label Bio2;

    @FXML
    private Label Age2;
    @FXML
    private Pane leftCard, rightCard;

    @FXML
    void onLike1() {
       excite.pressedLikeFirst();
       setUsers();


    }

    @FXML
    void onLike2() {
        excite.pressedLikeSecond();
        setUsers();



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
    double dY = 0;
    double initialY = 0;
    Boolean dragged = false;
    double y = 0;
    double lastY = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leftPicture.setFill(new ImagePattern(new Image(this.getClass().getResourceAsStream("Images/defaultPicture.png"))));
        rightPicture.setFill(new ImagePattern(new Image(this.getClass().getResourceAsStream("Images/defaultPicture.png"))));
        profile.setFill(new ImagePattern(new Image(this.getClass().getResourceAsStream("Images/defaultPicture.png")),30.5,62,60,95,false));
        dragY(leftCard);
        dragY(rightCard);
        setUsers();
    }
    private void dragY(Node e){
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
                    }
                    e.setLayoutY(55);
                    dragged = false;
                }
            }
        });
    }



}


