package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import user.User;

import org.junit.jupiter.api.AfterAll;
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

public class MatchControllerTest extends ApplicationTest {

  private MatchController controller;
  private SignUpController signupController = new SignUpController();
  private User testUser = new User("rolf", 22, "test@mail.com");
  private ObjectMapper mapper = new ObjectMapper();
  private static ClientAndServer server;
  private User matchedUser = new User("matched", 21, "matched@mail.com");


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
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("match.fxml"));
    final Parent root = loader.load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setUp(){
    testUser.setId(UUID.randomUUID());
    Map<String, Integer> likedUser = new HashMap<>();
    likedUser.put(matchedUser.getEmail(), 3);
    testUser.setLikedUsers(likedUser);
    testUser.checkIfMatch(matchedUser);
    signupController.addUser(testUser, "test");
    String sendString = null;

    try {
      sendString = mapper.writeValueAsString(testUser);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    server.when(HttpRequest.request().withMethod("POST")
    .withPath("/login")
    .withHeader("mail",testUser.getEmail())
    ).respond(HttpResponse.response().withStatusCode(200)
        .withHeader("Content-Type", "application/json").withBody(sendString));

    server.when(HttpRequest.request().withMethod("GET")).respond(HttpResponse.response().withStatusCode(200)
    .withHeader("Content-Type", "application/json").withBody(sendString));
    
  }

  public MatchController getController(){
    return controller;
  }


  @ParameterizedTest
  @MethodSource
  public void testMatch(boolean match) {
    checkResult(match);

  }
  private static Stream<Arguments> testMatch() {
    return Stream.of(Arguments.of(true));
  }




  // third method, where you do the assertions
  // and you actually call the click, lookup, whatever methods
  private void checkResult(boolean excpected) {
    if(excpected){
      clickOn("#matchButton");
      clickOn("#matchBox");
      clickOn("#textInput");
      write("Hei");
      clickOn("#sendButton");
      clickOn("#refresh");


      clickOn("#button");
    }
    else{
    Circle profile = lookup("#profile").query();
    clickOn(profile);
    }
  }
  
}