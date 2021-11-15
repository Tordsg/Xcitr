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
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import user.Chat;
import user.User;

/*TestFx App Test*/

public class MatchControllerTest extends ApplicationTest {

  private MatchController controller;
  private static User testUser = new User("rolf", 22, "test@mail.com");
  private static ObjectMapper mapper = new ObjectMapper();
  private static ClientAndServer server;
  private static User matchedUser = new User("matched", 21, "matched@mail.com");
  private static Chat chat = new Chat(testUser.getEmail(), matchedUser.getEmail());

  @BeforeAll
  public static void setUp() {
    server = ClientAndServer.startClientAndServer(8080);
    startMockServer();
  }

  private static void startMockServer() {
    String responseString;
    try {
      responseString = mapper.writeValueAsString(List.of(matchedUser));
      server.when(HttpRequest.request().withMethod("GET").withPath("/user/matches"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(responseString));
    } catch (JsonProcessingException e) {
    }
    try {
      responseString = mapper.writeValueAsString(chat);
      server.when(HttpRequest.request().withMethod("POST").withPath("/message"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(responseString));
    } catch (JsonProcessingException e) {
    }
  }

  @AfterAll
  public static void stopMockServer() {
    server.stop();
  }

  @Override
  public void start(final Stage stage) throws Exception {
    testUser.setId(UUID.randomUUID());
    App.setUser(testUser);
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("match.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
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
    if (excpected) {
      server.clear(HttpRequest.request().withMethod("POST").withPath("/message"));
      VBox box = controller.getMatchBox();
      clickOn(box.getChildren().get(0));
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
      }
      clickOn("#textInput");
      String responseString;
      try {
        responseString = mapper.writeValueAsString(chat);
        server.when(HttpRequest.request().withMethod("POST").withPath("/message"))
            .respond(HttpResponse.response().withStatusCode(200).withBody(responseString));
      } catch (JsonProcessingException e) {
      }
      chat.sendMeesage(testUser.getEmail(), "Hei");
      write("Hei");
      server.clear(HttpRequest.request().withMethod("POST").withPath("/message"));
      try {
        responseString = mapper.writeValueAsString(chat);
        server.when(HttpRequest.request().withMethod("POST").withPath("/message"))
        .respond(HttpResponse.response().withStatusCode(200).withBody(responseString));
      } catch (JsonProcessingException e) {
      }
      clickOn("#sendButton");
      server.clear(HttpRequest.request().withPath("/message"));
      chat.sendMeesage(matchedUser.getEmail(), "Hei tilbake");
      try {
        responseString = mapper.writeValueAsString(chat);
        server.when(HttpRequest.request().withMethod("GET").withPath("/message"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(responseString));
      } catch (JsonProcessingException e) {
      }
      clickOn("#refresh");
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
      }

      Assertions.assertEquals(2, controller.gettextBox().getChildren().size());
      HBox hBox = (HBox) controller.gettextBox().getChildren().get(0);
      Group group = (Group) hBox.getChildren().get(0);
      Text text = (Text) group.getChildren().get(1);
      Assertions.assertEquals("Hei", text.getText());
      HBox hBox2 = (HBox) controller.gettextBox().getChildren().get(1);
      Group group2 = (Group) hBox2.getChildren().get(0);
      Text text2 = (Text) group2.getChildren().get(1);
      Assertions.assertEquals("Hei tilbake", text2.getText());


    } else {
      Circle profile = lookup("#profile").query();
      clickOn(profile);
    }
  }

}