package ui;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import user.BotUser;
import user.User;

/*TestFx App Test*/

public class LoginControllerTest extends ApplicationTest {

  private LoginController controller = new LoginController();
  private App app = new App();
  private User testUser = new User("rolf", 22, "test@mail.com");
  private ObjectMapper mapper = new ObjectMapper();
  private static ClientAndServer server;

  @BeforeAll
  public static void startMockServer() {
    server = ClientAndServer.startClientAndServer(8080);
  }

  @AfterAll
  public static void stopMockServer() {
    server.stop();
  }

  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);
  }

  @BeforeEach
  public void setUp() {
    app = new App();
    testUser.setPassword("test");
    testUser.setId(UUID.randomUUID());
    controller.addUser("testUser", "test");
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
    if (excpected) {
      TextField email = lookup("#emailLogin").query();
      clickOn(email);
      write(testUser.getEmail());

      TextField password = lookup("#passwordLogin").query();
      clickOn(password);
      write("test");
      String sendString = null;


      try {
        sendString = mapper.writeValueAsString(testUser);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      server.when(HttpRequest.request().withMethod("POST")
      .withPath("/login")
      .withHeader("mail",testUser.getEmail())
      ).respond(HttpResponse.response().withStatusCode(200)
          .withHeader("Content-Type", "application/json").withBody(sendString));
          BotUser botUser = new BotUser("name", 22, "bot@mail.com", true);
          BotUser botUser2 = new BotUser("name", 22, "bot2@mail.com", true);
          try {
        sendString = mapper.writeValueAsString(List.of(botUser, botUser2));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      server.when(HttpRequest.request().withMethod("GET")).respond(HttpResponse.response().withStatusCode(200)
      .withHeader("Content-Type", "application/json").withBody(sendString));

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
