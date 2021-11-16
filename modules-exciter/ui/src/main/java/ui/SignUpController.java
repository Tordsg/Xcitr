package ui;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.rmi.ServerException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    clearFields();
    name.textProperty().addListener(event -> {
      if (name.getText().length() > 1
          && validName(name.getText()) && name.getText().charAt(0) != ' ') {
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
  /**
   * Clears the textfields.
   */

  public void clearFields() {
    name.clear();
    age.clear();
    emailSignup.clear();
    passwordSignup.clear();
  }

  private boolean validName(String str) {
    return str.matches("[a-zA-Z ]+");
  }

  /**
   * Checks if number is numeric.
   *
   * @param str number as a string
   * @return boolean
   */

  private boolean isNumeric(String str) {
    String pattern = "^[0-9]*$";
    return str.matches(pattern);
  }

  /**
   * Validates email.
   *
   * @param email email to validate
   * @return true if email is valid
   */

  private boolean emailValidator(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
        + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
        + "A-Z]{2,7}$";

    java.util.regex.Pattern pat = java.util.regex.Pattern.compile(emailRegex);
    java.util.regex.Matcher mat = pat.matcher(email);
    return mat.matches();
  }

  @FXML
  void onSwitchToLogin(MouseEvent event) {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("login.fxml"));
    Parent p;
    try {
      p = loader.load();
      Scene s = new Scene(p);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(s);
      window.show();
    } catch (IOException e) {
    }
  }

  @FXML
  void handleCreateAccount(ActionEvent event) {
    String nameReg = name.getText();
    String ageReg = age.getText();
    String emailReg = emailSignup.getText();
    String passwordReg = passwordSignup.getText();

    try {
      userXcitr = new User(nameReg, Integer.parseInt(ageReg), emailReg);
      User user = clientHandler.createAccount(userXcitr, passwordReg);
      App.setUser(user);
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("primary.fxml"));
      Parent p = loader.load();
      Scene s = new Scene(p);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(s);
      window.show();
    } catch (IllegalArgumentException | ServerException | ConnectException e) {
      errorLabel.setText(e.getMessage());
    } catch (IOException e) {
    }
  }

  /**
   * Adding a new user to the application.
   *
   * @param user User object
   * @param password string for the users password
   */

  public void addUser(User user, String password) {
    try {
      clientHandler.createAccount(user, password);
    } catch (ServerException | ConnectException e) {
    }
  }

  public TextField getAgeField() {
    return age;
  }

  public TextField getEmailField() {
    return emailSignup;
  }

}
