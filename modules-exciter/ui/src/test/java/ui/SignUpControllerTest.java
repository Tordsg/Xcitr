package ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;

import core.Exciter;
import core.User;

/*TestFx App Test*/

public class SignUpControllerTest extends ApplicationTest {

  private SignUpController controller = new SignUpController();
  private App app = new App();
  private Exciter excite = App.exciter;

  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);
  }

  public SignUpController getController() {
    return controller;
  }

  @BeforeEach
  public void setup() {
    app = new App();
    clickOn("#fromLoginToSignup");
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

      User currentUser = new User(name.getText(), Integer.parseInt(age.getText()), email.getText());

      TextField password = lookup("#passwordSignup").query();
      clickOn(password);
      write("123");

      clickOn("#createAccount");

      Assertions.assertTrue(excite.getCurrentUser().getEmail().equals(currentUser.getEmail()));
    } else {
      TextField name = lookup("#name").query();
      clickOn(name);
      write("Ulf Reidar");

      TextField age = lookup("#age").query();
      clickOn(age);
      write("-2");

      TextField email = lookup("#emailSignup").query();
      clickOn(email);
      write("Ulf@mail.com");

      TextField password = lookup("#passwordSignup").query();
      clickOn(password);
      write("123");

      Label label = lookup("#errorLabel").query();
      Assertions.assertEquals("", label.getText());
      clickOn("#createAccount");
      Assertions.assertNotEquals("", label.getText());
    }
  }

  @ParameterizedTest
  @MethodSource
  public void testIllegal(boolean age) {
    checkResult(age);
  }

  public static Stream<Arguments> testIllegal() {
    return Stream.of(Arguments.of(false));
  }


}