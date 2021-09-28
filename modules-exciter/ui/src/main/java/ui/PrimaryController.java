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
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    void onLike1(ActionEvent event) {
       excite.pressedLikeFirst();
       setUsers();


    }

    @FXML
    void onLike2(ActionEvent event) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //leftPicture.setFill(new ImagePattern(new Image("../ui/src/main/resources/ui/Images/defaultPicture.png")));
        setUsers();

    }



}


