package ui;


import core.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import json.*;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label errorLabel;

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
    private Text fromSignupToLogin;

    FileHandler fileHandler = LoginController.fileHandler;
    private Exciter excite = App.exciter;
    private User userXcitr;

    @FXML
    void initialize() {
        name.clear();
        age.clear();
        emailSignup.clear();
        passwordSignup.clear();


    }

    @FXML
    void onSwitchToLogin() throws IOException {
        App.setRoot("login");
    }


    @FXML
    void handleCreateAccount() throws IOException {
        String nameReg = name.getText();
        String ageReg = age.getText();
        String emailReg = emailSignup.getText();
        String passwordReg = passwordSignup.getText();

        try{
            userXcitr = new User(nameReg, Integer.parseInt(ageReg), emailReg);
            userXcitr.setPassword(passwordReg);
            saveUser(userXcitr);
            excite.setCurrentUser(userXcitr);
            excite.removeFromAllUsers(userXcitr);

            switchToPrimary();

        } catch(IllegalArgumentException e){
            errorLabel.setText(e.getMessage());
       }



    excite.setCurrentUser(userXcitr);
    excite.removeFromAllUsers(userXcitr);

    switchToPrimary();
  }

  private void switchToPrimary() throws IOException {
    App.setRoot("primary");
  }

  void saveUser(User user) {
    fileHandler.createFile();
    List<User> users = excite.getAllUsers();
    excite.setCurrentUser(user);
    fileHandler.saveUser(users);
  }

}
