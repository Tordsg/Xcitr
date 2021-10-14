package ui;

import core.*;
import json.*;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    @FXML
    private ImageView xcitrLogo;

    FileHandler fileHandler;
    Exciter xcitr = new Exciter();

    @FXML
    void initialize() {
        name.clear();
        age.clear();
        emailSignup.clear();
        passwordSignup.clear();
        errorMessage.setVisible(false);

    }

    @FXML
    void onSwitchToLogIn(MouseEvent event) throws IOException {
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
        User userXcitr = new User(nameReg, Integer.parseInt(ageReg), emailReg);
        userXcitr.setPassword(passwordReg);
        saveUser(userXcitr);
       
        App.setRoot("primary");

    }

    void saveUser(User user) {
        fileHandler.createFile();
        ArrayList<User> users = xcitr.getAllUsers();
        users.add(user);
        fileHandler.saveUser(users);
        
    }

}
