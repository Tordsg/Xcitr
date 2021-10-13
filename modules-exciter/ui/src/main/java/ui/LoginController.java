package ui;

import core.*;
import json.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {

    private FileHandler fileHandler = new FileHandler();;

    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;

    @FXML
    private Button login;

    @FXML
    private Text fromLoginToSignup;

    @FXML
    private Text errorMessage;

    @FXML
    public void initialize() {
        passwordLogin.clear();
        emailLogin.clear();
        errorMessage.setVisible(false);
    }
    
    @FXML
    public void onSwitchToSignup() throws IOException {
        App.setRoot("signup");
    }

    @FXML
    public void handleLogin() throws IOException {
        String email = emailLogin.getText();
        String password = passwordLogin.getText();

        for (User user : fileHandler.readUsers()) {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                App.setRoot("primary");
            }
        }
       
        errorMessage.setVisible(true);
        passwordLogin.clear();
        emailLogin.clear();

    }

}
