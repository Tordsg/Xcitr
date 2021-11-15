package ui;


import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.BotUser;
import user.User;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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

public class PrimaryControllerTest extends ApplicationTest {


  private SignUpController signupController = new SignUpController();
  private PrimaryController controller = new PrimaryController();
  private User testUser = new User("rolf", 22, "test@mail.com");
  private LoginController loginController = new LoginController();
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

  public void serverRespone() {
    server.when(HttpRequest.request().withMethod("POST")
    .withPath("http://localhost:8080/createAccount")
    .withHeader("Pass")
    ).respond(HttpResponse.response().withStatusCode(200).withHeader("Content-Type", "application/json")
        .withBody("{\n" + "  \"id\": 6ab169ce-31e7-481d-bb6f-fa5dfa95a3b5,\n" + "  \"name\": \"rolf\",\n"
            + "  \"age\": 22,\n" + "  \"email\": \"test@mail.com\"\n" + "  \"userinformation\": \"\"\n"
            + "  \"likedUsers\": {}\n" + "  \"matches\": []\n" + "  \"imageId\": 4"));

  }

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setUp() throws IOException{
    serverRespone();
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
      likeUser();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    drag("#rightCard").moveBy(0, -100).drop();
    try {
      TimeUnit.SECONDS.sleep(2);
      likeUser();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    drag("#rightCard").moveBy(0, -100).drop();



    try {
      TimeUnit.SECONDS.sleep(2);
      likeUser();
    } catch (Exception e) {
      e.printStackTrace();
    }
    drag("#leftCard").moveBy(0, -100).drop();
    signupController.deleteUser(testUser);

  }

  private void likeUser(){
    List<User> onsScreenUsers = controller.getOnScreenUsers();
    String sendString = null;
      try {
        sendString = mapper.writeValueAsString(onsScreenUsers);
      } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      server.when(HttpRequest.request().withMethod("POST")
      .withPath("/like")
      .withHeader("Authorization", testUser.getId().toString())
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
 @AfterEach
    public void deleteUser(){
      signupController.deleteUser(testUser);

  
    }


}