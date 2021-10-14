package ui;

import core.*;
import json.*;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private ImageView xcitrLogo;

    @FXML
    public void initialize() {
        passwordLogin.clear();
        emailLogin.clear();
        errorMessage.setVisible(false);
    }


    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
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

    @FXML
    void onSwitchToSignup(MouseEvent event) throws IOException {
        App.setRoot("signup");
    }

}
