package ui;

import core.*;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SecondaryController {
    private Exciter excite = new Exciter();

    public SecondaryController(){
        initData();

    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    

    @FXML
    private Button Back;

    @FXML
    private TextField Name;

    @FXML
    private TextArea Bio;

    @FXML
    private TextField Mail;

    @FXML
    private TextField Age;




public void initData(){
    User currentUser = excite.getCurrentUser();
    Name.setText(currentUser.getName());
    Age.setText(String.valueOf(currentUser.getAge()));
    Bio.setText(currentUser.getUserInformation());




    


}







}