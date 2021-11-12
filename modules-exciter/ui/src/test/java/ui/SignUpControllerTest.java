package ui;

import java.util.stream.Stream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;

/*TestFx App Test*/

public class SignUpControllerTest extends ApplicationTest {

  private SignUpController controller;

  @Override
  public void start(Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }


  public SignUpController getController() {
    return controller;
  }

  @BeforeEach
  public void setUp(){

  }

  @ParameterizedTest
  @MethodSource
  public void testSignup(boolean age) {
    checkResult(age);

  }

  private static Stream<Arguments> testSignup() {
    return Stream.of(Arguments.of(true));
  }

  private void checkResult(boolean excpected) {
    if (excpected) {

      TextField name = lookup("#name").query();
      clickOn(name);
      write("Ulf Reidar");

      TextField age = lookup("#age").query();
      clickOn(age);
      write("19");

      TextField email = lookup("#emailSignup").query();
      clickOn(email);
      write("Ulf@mail.no");

      TextField password = lookup("#passwordSignup").query();
      clickOn(password);
      write("123");

      clickOn("#createAccount");

      Assertions.assertEquals(App.getUser().getEmail(), "Ulf@mail.no");
      App.setUser(null);
      controller.deleteUser(new User("Ulf Reidar", 19, "Ulf@mail.no"));

    } else {
      clickOn("#name");
      write("Ulf Reidar");

      clickOn("#age");
      write("-2");

      clickOn("#emailSignup");
      write("Ulf@mail.com");

      clickOn("#passwordSignup");
      write("123");

      Label label = lookup("#errorLabel").query();
      Assertions.assertEquals("", label.getText());
      clickOn("#createAccount");
      Assertions.assertNotEquals("", label.getText());
    }
  }


}