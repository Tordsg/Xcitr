package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import json.FileHandler;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;

import core.Exciter;

/*TestFx App Test*/

public class SecondaryControllerTest extends ApplicationTest {

  private SecondaryController controller;
  private App app = new App();
  private Exciter exciter = App.exciter;
  private FileHandler fileHandler = new FileHandler();

  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);
  }

  @BeforeEach
  public void setUp() {
    app = new App();
    if (fileHandler.getUser("test@mail") != null) {
      clickOn("#emailLogin");
      write("test@mail");
      clickOn("#passwordLogin");
      write("test");
      clickOn("#login");
      clickOn("#profile");
    } else {
      clickOn("#fromLoginToSignup");
      clickOn("#name");
      write("test");
      clickOn("#age");
      write("20");
      clickOn("#emailSignup");
      write("test@mail");
      clickOn("#passwordSignup");
      write("test");
      clickOn("#createAccount");
      clickOn("#profile");
    }
  }

  @ParameterizedTest
  @MethodSource
  public void testController(String string1, boolean excpected) {
    checkResult(string1, excpected);

  }

  private static Stream<Arguments> testController() {
    return Stream.of(Arguments.of(null, true));
  }

  @ParameterizedTest
  @MethodSource
  public void test1equal1(int one, int two) {
    checkResult(one, two);
  }

  private static Stream<Arguments> test1equal1() {
    return Stream.of(Arguments.of(1, 1));
  }

  // third method, where you do the assertions
  // and you actually call the click, lookup, whatever methods
  private void checkResult(String string1, boolean excpected) {
    SVGPath edit = lookup("#UpdateButton").query();
    clickOn(edit);

    TextField bio = lookup("#UpdateBio").query();
    clickOn(bio);
    write("ulf@mail");

    SVGPath save = lookup("#SaveButton").query();
    clickOn(save);
    Assertions.assertNull(string1);
  }

  private void checkResult(int one, int two) {
    Assertions.assertEquals(one, two);
  }

  // TODO: Add more tests

}
