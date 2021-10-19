package ui;

import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;

import core.BotUser;
import core.Exciter;

/*TestFx App Test*/

public class PrimaryControllerTest extends ApplicationTest {

  private App app = new App();
  private Exciter excite = App.exciter;
  private PrimaryController controller = new PrimaryController();
  private BotUser botUser = new BotUser("John", 21, "john@mail.no", true);


  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);
  }

  public PrimaryController getController(){
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
    
    drag("#Name2").moveTo("#rightPicture").drop();
    
    drag("#Name2").moveTo("#rightPicture").drop();
    drag("#Name2").moveTo("#rightPicture").drop();

    Assertions.assertTrue(excite.getCurrentUser().getMatches().contains(botUser));}

    else{
    Circle profile = lookup("#profile").query();
    clickOn(profile);
  }
    


  }




  // TODO: Add more tests

}