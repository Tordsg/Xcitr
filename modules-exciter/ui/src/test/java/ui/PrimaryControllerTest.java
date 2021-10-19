package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;

import core.Exciter;
import core.User;

/*TestFx App Test*/

public class PrimaryControllerTest extends ApplicationTest {

  private App app = new App();
  private Exciter excite = App.exciter;
  private PrimaryController controller;

  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);
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
  }

  @ParameterizedTest
  @MethodSource
  public void testController(String string1, boolean excpected) {
    checkResult(string1, excpected);

  }

  private static Stream<Arguments> testController() {
    return Stream.of(Arguments.of(null, true));
  }


  // third method, where you do the assertions
  // and you actually call the click, lookup, whatever methods
  private void checkResult(String string1, boolean excpected) {
    User matchedUser = excite.getOnScreenUser1();
    drag("#Name1").moveTo("#leftPicture").drop();
    checkMatchCounter(matchedUser);

    User matchedUser2 = excite.getOnScreenUser2();
    drag("#Name2").moveTo("#rightPicture").drop();
    checkMatchCounter(matchedUser2);

    Circle profile = lookup("#profile").query();
    clickOn(profile);



  }



  private void checkMatchCounter(User user){
    User currentUser = excite.getCurrentUser();

    int number = 1;
    if(!currentUser.haveLikedUser(user)){
      number = 0;
    }
    //test1equal1(1, number);
  }



  // TODO: Add more tests

}