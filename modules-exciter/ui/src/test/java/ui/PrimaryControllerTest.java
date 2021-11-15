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
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setUser() {
    testUser.setId(UUID.randomUUID());
    App.setUser(testUser);
  }

  @BeforeAll
  public static void setUp() {
    server = ClientAndServer.startClientAndServer(8888);
    startMockServer();
  }

  @AfterAll
  public static void tearDown() {
    server.stop();
  }

  private static void startMockServer() {
    server = ClientAndServer.startClientAndServer(8080);
    try {
      server.when(HttpRequest.request().withPath("two")).respond(
          HttpResponse.response().withStatusCode(200).withBody(mapper.writeValueAsString(List.of(botUser, botUser2))));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    server.when(HttpRequest.request().withPath("user/matches")).respond(HttpResponse.response().withStatusCode(200));
    server.when(HttpRequest.request().withPath("user/likes"))
        .respond(HttpResponse.response().withStatusCode(200).withBody(String.valueOf(1)));

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

    drag("#rightCard").moveBy(0, -100).drop();
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    drag("#rightCard").moveBy(0, -100).drop();
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    drag("#rightCard").moveBy(0, -100).drop();

    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (Exception e) {
      e.printStackTrace();
    }
    drag("#leftCard").moveBy(0, -100).drop();

  }

  @ParameterizedTest
  @MethodSource
  public void testRefresh(boolean match) {
    checkRefresh(match);
  }

  public static Stream<Arguments> testRefresh() {
    return Stream.of(Arguments.of(true));
  }

  public void checkRefresh(boolean excpected) {

    User leftUser = controller.getOnScreenUsers().get(0);

    clickOn("#refresh");
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (Exception e) {
      System.out.println("here");
      e.printStackTrace();
    }

    Assertions.assertNotEquals(leftUser, controller.getOnScreenUsers().get(0));
  }
  /*
   * @AfterEach public void deleteUser(){ controller.deleteUser(new User("Ulf",
   * 20, "ulf@mail"));
   *
   *
   * }
   */

}