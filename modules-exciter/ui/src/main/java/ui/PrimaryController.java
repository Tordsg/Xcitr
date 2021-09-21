package ui;

import core.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class PrimaryController implements Initializable{

    private Exciter excite = new Exciter();



    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");

    }
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
        setUsers();
        
    }


    
}


