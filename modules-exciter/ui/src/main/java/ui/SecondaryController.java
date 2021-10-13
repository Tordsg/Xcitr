package ui;

import core.*;

import java.io.File;
import java.io.IOException;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
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
    private Label Age, Email, Bio, Name, SaveLabel, EditLabel;

    @FXML
    private Rectangle ProfileImage;
    
    @FXML
    private TextArea UpdateBio;

    @FXML
    private TextField UpdateName, UpdateEmail, UpdateAge;

    @FXML
    private SVGPath SaveButton, UpdateButton;




    @FXML
    void UpdateInfo(MouseEvent event) {
        UpdateBio.setVisible(true);
        UpdateAge.setVisible(true);
        UpdateEmail.setVisible(true);
        UpdateName.setVisible(true);
        SaveButton.setVisible(true);
        SaveLabel.setVisible(true);
        UpdateButton.setVisible(false);
        EditLabel.setVisible(false);

    

    }
    @FXML
    void SaveInfo(MouseEvent event) {
        User currentUser = excite.getCurrentUser();
        currentUser.setName(UpdateName.getText());
        currentUser.setEmail(UpdateEmail.getText());
        currentUser.setUserInformation(UpdateBio.getText());
        currentUser.setAge(Integer.parseInt(UpdateAge.getText()));
        excite.setCurrentUser(currentUser);
        initData();
        UpdateBio.setVisible(false);
        UpdateAge.setVisible(false);
        UpdateEmail.setVisible(false);
        UpdateName.setVisible(false);
        SaveButton.setVisible(false);
        SaveLabel.setVisible(false);
        UpdateButton.setVisible(true);
        EditLabel.setVisible(true);



    }



public void initData(){
    User currentUser = excite.getCurrentUser();
    Name.setText(currentUser.getName());
    Age.setText(String.valueOf(currentUser.getAge()));
    Bio.setText(currentUser.getUserInformation());
    ProfileImage.setFill(imageController.getImage(excite.getCurrentUser()));
    Email.setText(currentUser.getEmail());
    UpdateBio.setText(currentUser.getUserInformation());
    UpdateName.setText(currentUser.getName());
    UpdateAge.setText(String.valueOf(currentUser.getAge()));
    UpdateEmail.setText(currentUser.getEmail());
    

}
@Override
public void initialize(URL location, ResourceBundle resources) {
    initData();

}







}