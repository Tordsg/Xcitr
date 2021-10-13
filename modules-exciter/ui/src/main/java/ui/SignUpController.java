package ui;

import core.*;
import json.*;

import java.io.IOException;

import org.dom4j.Text;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SignUpController {

    @FXML
    Button createAccount;
    
    @FXML
    TextField name;

    @FXML
    TextField age;

    @FXML
    TextField emailSignup;

    @FXML
    PasswordField passwordSignup;

    @FXML
    Text errorMessage;

    FileHandler fileHandler;
    User user;

    @FXML
    void initialize() {
        name.clear();
        age.clear();
        emailSignup.clear();
        passwordSignup.clear();
        errorMessage.setVisible(false);

    }

    @FXML
    void onSwitchToLogIn() throws IOException {
        App.setRoot("login");
    }

    @FXML
    void handleCreateAccount() throws IOException {
        String nameReg = name.getText();
        String ageReg = age.getText();
        String emailReg = emailSignup.getText();
        String passwordReg = passwordSignup.getText();
        
        for (User user : fileHandler.readUsers()) {
            if (emailReg.equals(user.getEmail())) { 
                errorMessage.setVisible(true);
                emailSignup.clear();
                passwordSignup.clear();
            }
        }
        this.user = new User(nameReg, Integer.parseInt(ageReg), emailReg);
        user.setPassword(passwordReg);

        fileHandler.saveUser(user);
       
        App.setRoot("primary");

    }

}
