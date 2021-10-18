package ui;

import core.*;

import java.io.File;
import java.io.IOException;
import javafx.fxml.Initializable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class SecondaryController implements Initializable{
    private Exciter excite = LoginController.xcitr;
    private FileChooser fileChooser = new FileChooser();
    private ImageController imageController = PrimaryController.imageController;


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");

    }
    private void hoverButton(Group n){
        n.setOnMouseEntered(e -> {
            n.setEffect(new Lighting());
        });
        n.setOnMouseExited(e -> {
            n.setEffect(null);
        });
    }
    @FXML
    private void uploadPicture () throws IOException {
        File file = fileChooser.showOpenDialog(null);
        if (file != null && getFileExtension(file).equals(".jpg")) {
            imageController.uploadPicture(excite.getCurrentUser(), file);
        }
        updatePreview();
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
    private Group upload, backButton, signOut, save;
    
    @FXML
    private TextArea bio;

    @FXML
    private TextField name, age;
    @FXML
    private PasswordField password;
    @FXML
    private Pane pane;
    json.FileHandler fileHandler = PrimaryController.fileHandler;
    private Pane lastPane = null;

    @FXML
    void updatePreview() {
        User currentUser = excite.getCurrentUser();
        Pane currentPane = MatchController.createCard(currentUser);
        if(pane.getChildren().contains(lastPane)) pane.getChildren().remove(lastPane);
        pane.getChildren().add(currentPane);
        currentPane.setLayoutX(70);
        currentPane.setLayoutY(70);
        lastPane = currentPane;
    }

    @FXML
    public void signOut() throws IOException{
        fileHandler.createFile();
        ArrayList<User> users = excite.getAllUsers();
        boolean hasUser = users.stream().anyMatch(e -> e.getClass().getName().equals("core.User"));
        if(!hasUser) users.add(excite.getCurrentUser());
        fileHandler.saveUser(users);
        App.setRoot("login");
    }
    @FXML
    void save(){
        excite.getCurrentUser().setAge(Integer.parseInt(age.getText()));
        excite.getCurrentUser().setName(name.getText());
        if(!password.getText().equals("")) excite.getCurrentUser().setPassword(password.getText());
        excite.getCurrentUser().setUserInformation(bio.getText());
        pane.requestFocus();
        updatePreview();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(excite.getCurrentUser().getName());
        bio.setText(excite.getCurrentUser().getUserInformation());
        age.setText(Integer.toString(excite.getCurrentUser().getAge()));
        hoverButton(upload);
        hoverButton(backButton);
        hoverButton(signOut);
        hoverButton(save);
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e)
            {
                pane.requestFocus();
            }
        };
  
        // when enter is pressed
        pane.setOnMouseClicked(event);
  
        updatePreview();

    }
}