package ui;

import core.Exciter;
import user.User;
import java.util.stream.Stream;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;

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

      Assertions.assertEquals(excite.getCurrentUser().getEmail(), currentUser.getEmail());
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