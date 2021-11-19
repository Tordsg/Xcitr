package integration;

import java.net.ConnectException;
import java.rmi.ServerException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import restserver.ExciterApplication;
import restserver.ServerController;
import ui.App;
import ui.ClientHandler;
import user.User;

/*TestFx App Test*/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ExciterApplication.class, ServerController.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AppIntegrationTest extends ApplicationTest {

  @LocalServerPort
  int port = 8080;

  private static User testUser = new User("rolf", 22, "test@mail.no");
  private App app = new App();
  private ClientHandler clientHandler = new ClientHandler();

  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);

  }

  @ParameterizedTest
  @MethodSource
  public void testController(boolean excpected) {
    checkResult(excpected);

  }

  private static Stream<Arguments> testController() {
    return Stream.of(Arguments.of(true));
  }


  private void checkResult(boolean excpected) {
    clickOn("#fromLoginToSignup");
    clickOn("#name");
    write(testUser.getName());
    clickOn("#age");
    write(String.valueOf(testUser.getAge()));
    clickOn("#emailSignup");
    write(testUser.getEmail());
    clickOn("#passwordSignup");
    write("test");
    clickOn("#createAccount");
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (Exception e) {
    }
    drag("#rightCard").moveBy(0, -100).drop();
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (Exception e) {
    }
    Circle circle = lookup("#notification").query();
    Assertions.assertFalse(circle.isVisible());
    clickOn("#refresh");
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (Exception e) {
    }
    clickOn("#matchButton");
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (Exception e) {
    }
    clickOn("#backButton");
    clickOn("#profile");
    doubleClickOn("#name");
    write("Damian");
    doubleClickOn("#bio");
    write("I am a test");
    clickOn("#save");
    clickOn("#signOut");

    clickOn("#emailLogin");
    write(testUser.getEmail());
    clickOn("#passwordLogin");
    write("test");
    clickOn("#login");
    //Should have gone to primary and this queries the samme element again.
    circle = lookup("#notification").query();
    Assertions.assertFalse(circle.isVisible());

    try {
      clientHandler.deleteUser(testUser);
    } catch (ServerException | ConnectException e) {
    }
  }
}

