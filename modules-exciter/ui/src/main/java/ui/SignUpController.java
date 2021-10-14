package ui;

import core.*;
import json.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createAccount;
    
    @FXML
    private TextField name;

    @FXML
    private TextField age;

    @FXML
    private TextField emailSignup;

    @FXML
    private PasswordField passwordSignup;

    @FXML
    private ImageView xcitrLogo;

    @FXML
    private Text fromSignupToLogin;

    private FileHandler fileHandler = new FileHandler();
    private Exciter xcitr = new Exciter();

    @FXML
    void initialize() {
        name.clear();
        age.clear();
        emailSignup.clear();
        passwordSignup.clear();
        //errorMessage.setVisible(false);

    }

    @FXML
    void onSwitchToLogin(MouseEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    void handleCreateAccount(ActionEvent event) throws IOException {
        String nameReg = name.getText();
        String ageReg = age.getText();
        String emailReg = emailSignup.getText();
        String passwordReg = passwordSignup.getText();
        
        for (User user : fileHandler.readUsers()) {
            if (emailReg.equals(user.getEmail())) { 
                //errorMessage.setVisible(true);
                emailSignup.clear();
                passwordSignup.clear();
            }
        }

        User userXcitr = new User(nameReg, Integer.parseInt(ageReg), emailReg);
        userXcitr.setPassword(passwordReg);
        saveUser(userXcitr);

        Stage stage = (Stage) createAccount.getScene().getWindow();
        stage.close();

        openPrimary(event);

    }

    private void openPrimary(ActionEvent e) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("primary.fxml"));
		Scene scene = new Scene(parent);
		Stage window = new Stage();
		
		window.setScene(scene);
		window.show();
	}

    void saveUser(User user) {
        fileHandler.createFile();
        ArrayList<User> users = xcitr.getAllUsers();
        users.add(user);
        fileHandler.saveUser(users);
        
    }

}
