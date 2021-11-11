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
import user.User;
import javafx.scene.Node;

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
  void onSwitchToLogin(MouseEvent event) throws IOException {
    FXMLLoader Loader = new FXMLLoader();
    Loader.setLocation(getClass().getResource("login.fxml"));
    Parent p = Loader.load();
    Scene  s = new Scene(p);
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
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
      Scene  s = new Scene(p);
      Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
      window.setScene(s);
      window.show();
    } catch(IllegalArgumentException | ServerException | ConnectException e){
      errorLabel.setText(e.getMessage());
    }
  }

  public void deleteUser(User user){
    try {
      clientHandler.deleteUser(user);
    } catch (ServerException | ConnectException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
    public void addUser(User user, String password){
      
        try {
          clientHandler.createAccount(user, password);
        } catch (ServerException | ConnectException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    
  }


}
