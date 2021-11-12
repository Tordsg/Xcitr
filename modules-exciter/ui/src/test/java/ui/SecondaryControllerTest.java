package ui;


import java.util.stream.Stream;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import user.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;

/*TestFx App Test*/

public class SecondaryControllerTest extends ApplicationTest {

  private App app = new App();
  private SignUpController controller = new SignUpController();

  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);
  }

/*  @BeforeEach
  public void setUp() {
    app = new App();
    clickOn("#fromLoginToSignup");
    clickOn("#name");
    write("test");
    clickOn("#age");
    write("20");
    clickOn("#emailSignup");
    write("test@mail");
    clickOn("#passwordSignup");
    write("test");
    clickOn("#createAccount");
    clickOn("#profile");
  }
*/
  @ParameterizedTest
  @MethodSource
  public void testController(boolean excpected) {
    checkResult(excpected);

  }

  private static Stream<Arguments> testController() {
    return Stream.of(Arguments.of(true));
  }

  private void checkResult(boolean excpected) {
    app = new App();
    clickOn("#fromLoginToSignup");
    clickOn("#name");
    write("test");
    clickOn("#age");
    write("20");
    clickOn("#emailSignup");
    write("test@mail.no");
    clickOn("#passwordSignup");
    write("test");
    clickOn("#createAccount");
    clickOn("#profile");
    TextArea textField = lookup("#bio").query();
    textField.clear();
    clickOn("#bio");
    write("guitar player");
    clickOn("#save");
    Assertions.assertEquals("guitar player", App.getUser().getUserInformation());
    clickOn("#signOut");
    controller.deleteUser(new User("test", 20, "test@mail.no"));
  }

 /* @AfterEach
  public void deleteUser(){
    controller.deleteUser(new User("test", 20, "test@mail"));

    
  }*/

}
