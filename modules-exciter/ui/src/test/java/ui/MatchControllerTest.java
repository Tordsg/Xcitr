package ui;

import java.util.stream.Stream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import user.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;


/*TestFx App Test*/

public class MatchControllerTest extends ApplicationTest {

  private MatchController controller;
  private SignUpController signupController = new SignUpController();
  private PrimaryController primaryController = new PrimaryController();
  private User testUser = new User("rolf", 22, "test@mail.com");
  


  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("match.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setUp(){
    signupController.addUser(testUser, "test");
    App.setUser(testUser);

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
  
}