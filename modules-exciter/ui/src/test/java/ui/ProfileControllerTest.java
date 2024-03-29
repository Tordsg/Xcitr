package ui;

import java.util.UUID;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.User;

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

/*TestFx App Test*/

public class ProfileControllerTest extends ApplicationTest {

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
  public void start(final Stage stage) throws Exception {
    App.setUser(testUser);
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
    final Parent root = loader.load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setUp() throws JsonProcessingException{
    testUser.setId(UUID.randomUUID());
  }


  @ParameterizedTest
  @MethodSource
  public void testController(boolean excpected) throws JsonProcessingException {
    checkResult(excpected);

  }

  private static Stream<Arguments> testController() {
    return Stream.of(Arguments.of(true));
  }

  private void checkResult(boolean excpected) throws JsonProcessingException {

    TextArea textField = lookup("#bio").query();
    textField.clear();
    clickOn("#bio");
    write("guitar player");

    TextField textFieldName = lookup("#name").query();
    textFieldName.clear();
    clickOn("#name");
    write("Ulf Reidar");

    testUser.setName("Ulf Reidar");
    testUser.setUserInformation("guitar player");
    String sendString = mapper.writeValueAsString(testUser);

    server.when(HttpRequest
          .request().withMethod("POST")
          .withPath("/user/update")
          .withHeader("Authorization",testUser.getId().toString()))
        .respond(HttpResponse.response().withStatusCode(200)
          .withHeader("Content-Type", "application/json")
          .withBody(sendString));

    server.when(HttpRequest
          .request()
          .withMethod("GET"))
        .respond(HttpResponse
          .response()
          .withStatusCode(200)
          .withHeader("Content-Type", "application/json")
          .withBody(sendString));

    clickOn("#save");
    Assertions.assertEquals("guitar player", App.getUser().getUserInformation());
    Assertions.assertEquals("Ulf Reidar", App.getUser().getName());
    clickOn("#signOut");
  }


}
