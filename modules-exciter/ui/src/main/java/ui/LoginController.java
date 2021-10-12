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

    @FXML
    private TextField emailLogin;
    private PasswordField passwordLogin;
    private Button loginBtn;
    private Text fromLoginToSignup;

    
    @FXML
    public void switchToSignUp() throws IOException {
        App.setRoot("signup");
    }




    
}
