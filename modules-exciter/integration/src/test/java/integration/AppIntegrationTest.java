package integration;

import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import restserver.ExciterApplication;
import restserver.ServerController;
import ui.App;
import user.User;

/*TestFx App Test*/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ExciterApplication.class, ServerController.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppIntegrationTest extends ApplicationTest {

  private static User testUser = new User("rolf", 22, "test@mail.com");
  private App app = new App();

  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);
  }

  @ParameterizedTest
  @MethodSource
  public void testController(boolean excpected) throws JsonProcessingException {
    checkResult(excpected);

  }

  private static Stream<Arguments> testController() {
    return Stream.of(Arguments.of(true));
  }

  @ParameterizedTest
  @MethodSource
  public void testControllerFail(boolean excpected) throws JsonProcessingException {
    checkResult(excpected);

  }

  private static Stream<Arguments> testControllerFail() {
    return Stream.of(Arguments.of(false));
  }

  private void checkResult(boolean excpected) throws JsonProcessingException {
    if (excpected) {
      TextField email = lookup("#emailLogin").query();
      clickOn(email);
      write(testUser.getEmail());

      TextField password = lookup("#passwordLogin").query();
      clickOn(password);
      write("test");

      clickOn("#login");
      Assertions.assertEquals(testUser.getEmail(), App.getUser().getEmail());

    } else {
      TextField email = lookup("#emailLogin").query();
      clickOn(email);
      write(testUser.getEmail() + ".notexisting");

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
