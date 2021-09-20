package ui;

import core.Exciter;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

@FXML
private Button back;

@FXML
private ImageView picture;

@FXML
private TextArea Bio;


@FXML
private TextField mail;

@FXML
void updateSite(ActionEvent event){
    
}


}