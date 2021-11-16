package ui;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import javafx.stage.Stage;
import user.BotUser;
import user.User;

/*TestFx App Test*/

public class PrimaryControllerTest extends ApplicationTest {

  private PrimaryController controller;
  private User testUser = new User("rolf", 22, "test@mail.com");
  private static BotUser botUser = new BotUser("name", 22, "email@mail.com", true);
  private static BotUser botUser2 = new BotUser("name", 22, "email2@mail.com", true);
  private static ObjectMapper mapper = new ObjectMapper();
  private static ClientAndServer server;

  @Override
  public void start(final Stage stage) throws Exception {
    testUser.setId(UUID.randomUUID());
    App.setUser(testUser);
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeAll
  public static void setup() {
    server = ClientAndServer.startClientAndServer(8080);
    startMockServer();
  }

  @AfterAll
  public static void tearDown() {
    server.stop();
  }

  private static void startMockServer() {
    server.when(HttpRequest.request().withPath("/user/matches"))
        .respond(HttpResponse.response().withStatusCode(200).withBody("[]"));
    try {
      server.when(HttpRequest.request().withMethod("GET").withPath("/two"))
          .respond(HttpResponse.response().withStatusCode(200).withHeader("Content-Type", "application/json")
              .withBody(mapper.writeValueAsString(List.of(botUser, botUser2))));
    } catch (JsonProcessingException e) {
    }

  }

  @ParameterizedTest
  @MethodSource
  public void testMatch(boolean match) {
    checkResult(match);

  }

  private static Stream<Arguments> testMatch() {
    return Stream.of(Arguments.of(true));
  }

  private void checkResult(boolean excpected) {

    // Should be true after the first match
    Assertions.assertFalse(controller.getNotificationCircle().isVisible());

    server.clear(HttpRequest.request().withPath("/user/matches"));
    server.clear(HttpRequest.request().withMethod("GET").withPath("/two"));

    try {
      server.when(HttpRequest.request().withMethod("POST").withPath("/like"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(mapper.writeValueAsString(botUser)));
    } catch (JsonProcessingException e) {
    }
    try {
      server.when(HttpRequest.request().withPath("/user/likes"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(mapper.writeValueAsString(1)));
    } catch (JsonProcessingException e1) {
    }
    drag("#rightCard").moveBy(0, -100).drop();
    server.clear(HttpRequest.request().withMethod("POST").withPath("/like"));
    server.clear(HttpRequest.request().withPath("/user/likes"));
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
    }
    try {
      server.when(HttpRequest.request().withMethod("POST").withPath("/like"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(mapper.writeValueAsString(botUser)));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    try {
      server.when(HttpRequest.request().withPath("/user/likes"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(mapper.writeValueAsString(2)));
    } catch (JsonProcessingException e1) {
      e1.printStackTrace();
    }
    drag("#rightCard").moveBy(0, -100).drop();
    server.clear(HttpRequest.request().withMethod("POST").withPath("/like"));
    server.clear(HttpRequest.request().withPath("/user/likes"));
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    try {
      server.when(HttpRequest.request().withMethod("POST").withPath("/like"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(mapper.writeValueAsString(botUser)));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    try {
      server.when(HttpRequest.request().withPath("/user/likes"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(mapper.writeValueAsString(3)));
    } catch (JsonProcessingException e1) {
      e1.printStackTrace();
    }
    try {
      server.when(HttpRequest.request().withMethod("POST").withPath("/user/new"))
          .respond(HttpResponse.response().withStatusCode(200).withHeader("Content-Type", "application/json")
              .withBody(mapper.writeValueAsString(botUser)));
    } catch (JsonProcessingException e) {
    }
    try {
      server.when(HttpRequest.request().withPath("/user/matches"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(mapper.writeValueAsString(List.of(botUser))));
    } catch (JsonProcessingException e2) {
    }

    drag("#rightCard").moveBy(0, -100).drop();
    server.clear(HttpRequest.request().withMethod("POST").withPath("/like"));
    server.clear(HttpRequest.request().withPath("/user/likes"));

    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    server.clear(HttpRequest.request().withMethod("POST").withPath("/user/new"));
    server.clear(HttpRequest.request().withPath("/user/matches"));
    try {
      server.when(HttpRequest.request().withMethod("POST").withPath("/like"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(mapper.writeValueAsString(botUser)));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    try {
      server.when(HttpRequest.request().withPath("/user/likes"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(mapper.writeValueAsString(1)));
    } catch (JsonProcessingException e1) {
      e1.printStackTrace();
    }
    drag("#leftCard").moveBy(0, -100).drop();
    server.clear(HttpRequest.request().withMethod("POST").withPath("/like"));
    server.clear(HttpRequest.request().withPath("/user/likes"));
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
    }

    try {
      server.when(HttpRequest.request().withMethod("GET").withPath("/two"))
          .respond(HttpResponse.response().withStatusCode(200).withHeader("Content-Type", "application/json")
              .withBody(mapper.writeValueAsString(List.of(botUser, botUser2))));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    clickOn("#refresh");
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Assertions.assertTrue(controller.getNotificationCircle().isVisible());
    // If ui whould have crashed. The following would be null.
    Assertions.assertNotNull(controller.getOnScreenUsers().get(0));
  }

}