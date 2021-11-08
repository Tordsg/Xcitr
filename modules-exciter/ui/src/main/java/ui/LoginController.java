package ui;

import user.User;
import java.io.IOException;
import java.net.ConnectException;
import java.rmi.ServerException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * Controller for login.fxml
 **/

public class LoginController {

  private ClientHandler clientHandler = new ClientHandler();

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
   * Checks that the email belongs to a user and that the password matches the
   * user's password.
   *
   * @throws IOException
   */

  @FXML
  public void handleLogin() throws IOException {
    String email = emailLogin.getText();
    String password = passwordLogin.getText();

    try {
      User user = clientHandler.login(email, password);
      changeUser(user);
      switchToPrimary();

    } catch (ServerException e) {
      errorMessage.setText(e.getMessage());
      errorMessage.setVisible(true);
      passwordLogin.clear();
      emailLogin.clear();
    }
    catch (ConnectException e){
      errorMessage.setText(e.getMessage());
      errorMessage.setVisible(true);
    }

  }

  private static void changeUser(User user) {
    App.setUser(user);
  }

  private void switchToPrimary() throws IOException {
    App.setRoot("primary");
  }

  @FXML
  void onSwitchToSignup() throws IOException {
    App.setRoot("signup");
  }

}
