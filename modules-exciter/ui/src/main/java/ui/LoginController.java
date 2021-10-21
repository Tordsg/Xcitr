package ui;

import core.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import json.*;

/**
* Controller for login.fxml
**/

public class LoginController {

  protected final static FileHandler fileHandler = new FileHandler();
  private Exciter xcitr = App.exciter;

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

  /**
   * Sets field clear when fxml file starts to run.
  **/
  @FXML
  public void initialize() {
    passwordLogin.clear();
    emailLogin.clear();
    errorMessage.setVisible(false);
  }

  /**
   * Checks that the email belongs to a user and that the password matches the user's password.
   * @throws IOException
   */

  @FXML
  public void handleLogin() throws IOException {
    String email = emailLogin.getText();
    String password = passwordLogin.getText();

    //long sentence
    for (User user : fileHandler.readUsers()) {
      if (email.equals(user.getEmail()) && User.MD5Hash(password).equals(user.getPassword())) {
        xcitr.setCurrentUser(user);
        switchToPrimary();
      }
    }

    errorMessage.setVisible(true);
    passwordLogin.clear();
    emailLogin.clear();
  }

  private void switchToPrimary() throws IOException {
    App.setRoot("primary");
  }

  @FXML
  void onSwitchToSignup() throws IOException {
    App.setRoot("signup");
  }

}
