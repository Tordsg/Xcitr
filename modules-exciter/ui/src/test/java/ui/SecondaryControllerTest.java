package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
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
  public void testController(boolean excpected) {
    checkResult(excpected);

  }

  private static Stream<Arguments> testController() {
    return Stream.of(Arguments.of(true));
  }


  private void checkResult(boolean excpected) {
    TextArea textField = lookup("#bio").query();
    textField.clear();
    clickOn("#bio");
    write("guitar player");
    clickOn("#save");
    Assertions.assertEquals("guitar player", exciter.getCurrentUser().getUserInformation());
  }

}
