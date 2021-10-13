package ui;

import core.*;
import json.*;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {

    User user;
    FileHandler fileHandler;

    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;

    @FXML
    private Button login;

    @FXML
    private Text fromLoginToSignup;

    @FXML
    void initialize() {
        fileHandler = new FileHandler();
        passwordLogin.clear();
        emailLogin.clear();
    }

    @FXML
    public void onSwitchToSignup() throws IOException {
        App.setRoot("signup");
    }

    @FXML
    public void handleLogin() throws IOException {
        String user = emailLogin.getText();
        String password = passwordLogin.getText();


        
        App.setRoot("primary");


    }


    
}
