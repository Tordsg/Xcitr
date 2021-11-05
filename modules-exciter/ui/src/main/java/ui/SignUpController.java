package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import user.User;

/**
 * Controller for signup.fxml.
 */

public class SignUpController {

  private ClientHandler clientHandler = new ClientHandler();

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

    try {
      userXcitr = new User(nameReg, Integer.parseInt(ageReg), emailReg);
      User user = clientHandler.createAccount(userXcitr, passwordReg);
      App.user = user;
      switchToPrimary();

    } catch (IllegalArgumentException e) {
      errorLabel.setText(e.getMessage());
    }

    switchToPrimary();
  }

  private void switchToPrimary() throws IOException {
    App.setRoot("primary");
  }

}
