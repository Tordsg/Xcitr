package ui;

import java.util.UUID;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

/*TestFx Test of SignUpController*/

public class SignUpControllerTest extends ApplicationTest {

  private SignUpController controller;
  private ObjectMapper mapper = new ObjectMapper();
  private static ClientAndServer server;
  private User testUser = new User("Ulf Reidar", 19, "Ulf@mail.no");

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
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }


  public SignUpController getController() {
    return controller;
  }

  @BeforeEach
  public void setUp(){
    App.setUser(null);
    testUser.setPassword("123");

  }

  @ParameterizedTest
  @MethodSource
  public void testSignup(boolean age) {
    checkResult(age);

  }

  private static Stream<Arguments> testSignup() {
    return Stream.of(Arguments.of(true));
  }

  private void checkResult(boolean excpected) {

      clickOn("#name");
      write("Ulf Reidar");

      clickOn("#age");
      write("-2");

      clickOn("#emailSignup");
      write("Ulf@mail.com");

      clickOn("#passwordSignup");
      write("123");

      Label label = lookup("#errorLabel").query();
      Assertions.assertEquals("", label.getText());
      clickOn("#createAccount");
      Assertions.assertNotEquals("", label.getText());

      controller.clearFields();
      TextField name = lookup("#name").query();
      clickOn(name);
      write(testUser.getName());

      TextField age = lookup("#age").query();
      clickOn(age);
      write(String.valueOf(testUser.getAge()));

      TextField email = lookup("#emailSignup").query();
      clickOn(email);
      write(testUser.getEmail());

      TextField password = lookup("#passwordSignup").query();
      clickOn(password);
      write("123");

      testUser.setId(UUID.randomUUID());

      String sendString = null;
      try {
        sendString = mapper.writeValueAsString(testUser);
      } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      server.when(HttpRequest.request().withMethod("POST")
      .withPath("/createAccount")
      .withHeader("Pass", testUser.getPassword())
      ).respond(HttpResponse.response().withStatusCode(200)
          .withHeader("Content-Type", "application/json").withBody(sendString));

      clickOn("#createAccount");

    
      Assertions.assertEquals(App.getUser().getEmail(), testUser.getEmail());
      controller.deleteUser(testUser);
    }

  

}