package ui;

import core.*;
import json.*;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SecondaryController {
    private Exciter excite;

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
   User user = excite.getCurrentUser();
    Name.setText(user.getName());
    Age.setText(user.getAge());
    Bio.setText(user.getUserInformation());


    


}


}