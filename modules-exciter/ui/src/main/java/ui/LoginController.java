package ui;

import user.User;
import java.io.IOException;
import java.net.ConnectException;
import java.rmi.ServerException;

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
import javafx.scene.Node;

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
  public void handleLogin(ActionEvent event) throws IOException {
    String email = emailLogin.getText();
    String password = passwordLogin.getText();

    try {
      User user = clientHandler.login(email, password);
      changeUser(user);
      FXMLLoader Loader = new FXMLLoader();
      Loader.setLocation(getClass().getResource("primary.fxml"));
      Parent p = Loader.load();
      Scene  s = new Scene(p);
      Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
      window.setScene(s);
      window.show();

    } catch (ServerException | ConnectException e) {
      errorMessage.setText(e.getMessage());
      errorMessage.setVisible(true);
      passwordLogin.clear();
      emailLogin.clear();
    }

  }

  private static void changeUser(User user) {
    App.setUser(user);
  }

  @FXML
  void onSwitchToSignup(MouseEvent event) throws IOException {
    FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getResource("signup.fxml"));
		Parent p = Loader.load();
		Scene  s = new Scene(p);
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(s);
		window.show();
  }

}
