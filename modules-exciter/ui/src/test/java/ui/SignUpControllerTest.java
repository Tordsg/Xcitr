package ui;

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

  // This is first method, where all you do is pass what's comes
  // from the test method to the checkResult method
  // Why you keep this one is to split up the first parameter if it's a list for
  // example
  /*
   * @ParameterizedTest
   *
   * @MethodSource public void test1equal1(int one, int two) { checkResult(one,
   * two); }
   */

  // This is second method, where you keep all the values
  // you want to test. i.e. you can have a list of values
  // like name, age, etc.
  /*
   * private static Stream<Arguments> test1equal1() { return
   * Stream.of(Arguments.of(1, 1)); }
   */

  // third method, where you do the assertions
  // and you actually call the click, lookup, whatever methods
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

      Assertions.assertThrows(IllegalArgumentException.class, () -> clickOn("#createAccount"));
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

  /*
   * private void checkIfContainsUser(User user){ int number = 0;
   * if(excite.getAllUsers().contains(user)){ number = 1; } test1equal1(1,
   * number);
   *
   * }
   */

  // TODO: Add more tests

}