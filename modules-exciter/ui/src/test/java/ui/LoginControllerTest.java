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

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import user.BotUser;
import user.User;

/*TestFx App Test*/

public class LoginControllerTest extends ApplicationTest {

<<<<<<< HEAD
  private App app = new App();
  private static User testUser = new User("rolf", 22, "test@mail.com");
  private static ObjectMapper mapper = new ObjectMapper();
=======
  private User testUser = new User("rolf", 22, "test@mail.com");
  private ObjectMapper mapper = new ObjectMapper();
>>>>>>> 54abf9f21b3c85fe17d8c1b4b2c8bc0b02f7e018
  private static ClientAndServer server;

  @BeforeAll
  public static void setUpMock() {
    server = ClientAndServer.startClientAndServer(8080);
    startMockServer();
  }

  @AfterAll
  public static void stopMockServer() {
    server.stop();
  }

  private static void startMockServer(){
    testUser.setPassword("test");
    testUser.setId(UUID.randomUUID());
    try {
      server.when(HttpRequest.request().withMethod("POST").withPath("/createAccount"))
          .respond(HttpResponse.response().withStatusCode(200).withHeader("Content-Type", "application/json")
              .withBody(mapper.writeValueAsString(testUser)));


    } catch (JsonProcessingException e) {
    }

  }

  @Override
  public void start(Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
    final Parent root = loader.load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setUp() {
<<<<<<< HEAD
    app = new App();

=======
    testUser.setPassword("test");
    testUser.setId(UUID.randomUUID());
>>>>>>> 54abf9f21b3c85fe17d8c1b4b2c8bc0b02f7e018
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

      String sendString = mapper.writeValueAsString(testUser);

      server.when(HttpRequest.request().withMethod("POST")
      .withPath("/login")
      .withHeader("mail",testUser.getEmail())
      ).respond(HttpResponse.response().withStatusCode(200)
          .withHeader("Content-Type", "application/json").withBody(sendString));
          BotUser botUser = new BotUser("name", 22, "bot@mail.com", true);
          BotUser botUser2 = new BotUser("name", 22, "bot2@mail.com", true);

      sendString = mapper.writeValueAsString(List.of(botUser, botUser2));

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
