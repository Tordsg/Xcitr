package ui;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.rmi.ServerException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
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
    name.textProperty().addListener(event -> {
      if (name.getText().length() > 1 && validName(name.getText())) {
        name.setStyle("-fx-control-inner-background: white;");
      } else {
        name.setStyle("-fx-control-inner-background: #ff9999;");
      }
    });
    age.textProperty().addListener(event -> {
      if (isNumeric(age.getText())) {
        age.setStyle("-fx-control-inner-background: white;");
      } else {
        age.setStyle("-fx-control-inner-background: #ff9999;");
      }
    });
    emailSignup.textProperty().addListener(event -> {
      if (emailValidator(emailSignup.getText())) {
        emailSignup.setStyle("-fx-control-inner-background: white;");
      } else {
        emailSignup.setStyle("-fx-control-inner-background: #ff9999;");
      }
    });

  }

  private boolean validName(String str) {
    return str.matches("[a-zA-Z ]+");
  }

  /**
   * Checks if number is numeric.
   *
   * @param str
   * @return boolean
   */
  private boolean isNumeric(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Validates email.
   *
   * @param email email to validate
   * @return true if email is valid
   */
  private boolean emailValidator(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
        + "A-Z]{2,7}$";

    java.util.regex.Pattern pat = java.util.regex.Pattern.compile(emailRegex);
    java.util.regex.Matcher mat = pat.matcher(email);
    return mat.matches();
  }

  @FXML
  void onSwitchToLogin(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("login.fxml"));
    Parent p = loader.load();
    Scene s = new Scene(p);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(s);
    window.show();
  }

  @FXML
  void handleCreateAccount(ActionEvent event) throws IOException {
    String nameReg = name.getText();
    String ageReg = age.getText();
    String emailReg = emailSignup.getText();
    String passwordReg = passwordSignup.getText();

    try {
      userXcitr = new User(nameReg, Integer.parseInt(ageReg), emailReg);
      User user = clientHandler.createAccount(userXcitr, passwordReg);
      App.setUser(user);
      FXMLLoader Loader = new FXMLLoader();
      Loader.setLocation(getClass().getResource("primary.fxml"));
      Parent p = Loader.load();
      Scene s = new Scene(p);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(s);
      window.show();
    } catch (IllegalArgumentException | ServerException | ConnectException e) {
      errorLabel.setText(e.getMessage());
    }
  }

}
