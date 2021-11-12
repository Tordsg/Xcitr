package ui;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import user.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;


/*TestFx App Test*/

public class MatchControllerTest extends ApplicationTest {

  private App app = new App();
  private MatchController controller;
  private SignUpController controller2 = new SignUpController();
  private PrimaryController primaryController = new PrimaryController();
  private User testUser = new User("rolf", 22, "test@mail.com");


  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);
  }

  public MatchController getController(){
    return controller;
  }

  @BeforeEach
  public void setUp() {
    controller2.addUser(testUser, "test");
      TextField email = lookup("#emailLogin").query();
      clickOn(email);
      write(testUser.getEmail());

      TextField password = lookup("#passwordLogin").query();
      clickOn(password);
      write("test");
      clickOn("#login");
      App.getUser().addMatch("John@mail.no");
    
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
      clickOn("#matchButton").clickOn(1,1);
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


  @AfterEach
  public void deleteUser(){
    controller2.deleteUser(new User("Ulf", 20, "ulf@mail.no"));

    
  }
  
}