package ui;

import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;

import core.BotUser;
import core.Exciter;
import core.User;

/*TestFx App Test*/

public class MatchControllerTest extends ApplicationTest {

  private App app = new App();
  private Exciter excite = App.exciter;
  private MatchController controller;
  private BotUser botUser = new BotUser("John", 21, "john@mail.no", true);


  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);
  }

  public MatchController getController(){
    return controller;
  }

  @BeforeEach
  public void setUp() {
    clickOn("#fromLoginToSignup");
    clickOn("#name");
    write("Ulf");
    clickOn("#age");
    write("20");
    clickOn("#emailSignup");
    write("ulf@mail");
    clickOn("#passwordSignup");
    write("ulf");
    clickOn("#createAccount");
    excite.setOnScreenUser1(botUser);
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
      User user = excite.getOnScreenUser1();
      clickOn("#matchButton");
      Assertions.assertTrue(excite.getCurrentUserMatches().isEmpty());
      clickOn("#button");
      Assertions.assertEquals(user,excite.getOnScreenUser1());
    }
    else{
    Circle profile = lookup("#profile").query();
    clickOn(profile);
    }
  }
}