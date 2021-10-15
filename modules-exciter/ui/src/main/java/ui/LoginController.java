package ui;

import core.*;
import json.*;

import java.io.IOException;

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

        Stage stage = (Stage) login.getScene().getWindow();
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

    @FXML
    void onSwitchToSignup(MouseEvent event) throws IOException {
        App.setRoot("signup");
    }

}
