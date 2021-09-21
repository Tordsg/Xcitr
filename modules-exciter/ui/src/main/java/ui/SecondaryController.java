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

@FXML
void updateSite(ActionEvent event){

}

@FXML
private TextField Name;

public void initData(){
   /* User user = excite.getCurrentUser();
    Name.setText(user.getName());
    Age.setText(user.getAge());
    Bio.setText(user.getUserInformation());

*/
    


}


}