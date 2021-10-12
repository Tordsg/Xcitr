package ui;

import core.*;

import java.io.File;
import java.io.IOException;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondaryController implements Initializable{
    private Exciter excite = new Exciter();
    private FileChooser fileChooser = new FileChooser();
    private ImageController imageController = PrimaryController.imageController;


    @FXML
    private void switchToPrimary(MouseEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void uploadPicture () throws IOException {
        File file = fileChooser.showOpenDialog(null);
        if (file != null && getFileExtension(file).equals(".jpg")) {
            imageController.uploadPicture(excite.getCurrentUser(), file);
        }
    }

    private String getFileExtension(File file) {
        String extension = "";
        try {
            if (file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf("."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return extension;
    }

    @FXML
    private Label Name;

    @FXML
    private Label Bio;


    @FXML
    private Label Age;

    @FXML
    private Rectangle ProfileImage;





public void initData(){
    User currentUser = excite.getCurrentUser();
    Name.setText(currentUser.getName());
    Age.setText(String.valueOf(currentUser.getAge()));
    Bio.setText(currentUser.getUserInformation());
    ProfileImage.setFill(imageController.getImage(excite.getCurrentUser()));
    

}
@Override
public void initialize(URL location, ResourceBundle resources) {
    initData();

}







}