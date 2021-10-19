package ui;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import json.FileHandler;

import java.util.List;
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

public class LoginControllerTest extends ApplicationTest {

  private App app = new App();
  private Exciter exciter = App.exciter;
  private User testUser = new User("rolf", 22, "test@mail");
  private FileHandler fileHandler = new FileHandler();

  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);
  }

  @BeforeEach
  public void setUp() {
    app = new App();
    testUser.setPassword("test");
    List<User> users = exciter.getAllUsers();
    users.add(testUser);
    fileHandler.saveUser(users);
  }

  @ParameterizedTest
  @MethodSource
  public void testController(boolean excpected) {
    checkResult(excpected);

  }

  private static Stream<Arguments> testController() {
    return Stream.of(Arguments.of(true));
  }
  @ParameterizedTest
  @MethodSource
  public void testControllerFail(boolean excpected) {
    checkResult(excpected);

  }

  private static Stream<Arguments> testControllerFail() {
    return Stream.of(Arguments.of(false));
  }


  private void checkResult(boolean excpected) {
    if(excpected) {
      TextField email = lookup("#emailLogin").query();
      clickOn(email);
      write(testUser.getEmail());

      TextField password = lookup("#passwordLogin").query();
      clickOn(password);
      write("test");
      clickOn("#login");

      Assertions.assertTrue(exciter.getCurrentUser().getEmail().equals(testUser.getEmail()));

    }
    else {
      TextField email = lookup("#emailLogin").query();
      clickOn(email);
      write(testUser.getEmail()+".notexisting");

      TextField password = lookup("#passwordLogin").query();
      clickOn(password);
      write("test");
      Text text = lookup("#errorMessage").query();
      Assertions.assertFalse(text.isVisible());

      clickOn("#login");


      Assertions.assertNotEquals("", text.getText());
      Assertions.assertTrue(text.isVisible());

    }
  }

}
