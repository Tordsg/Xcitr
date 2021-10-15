package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;

/*TestFx App Test*/

public class PrimaryControllerTest extends ApplicationTest {

  private PrimaryController controller = new PrimaryController();

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
    Parent root = loader.load();
    controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public PrimaryController getController() {
    return controller;
  }

  @ParameterizedTest
  @MethodSource
  public void testController(String string1, boolean excpected) {
    checkResult(string1, excpected);

  }

  private static Stream<Arguments> testController() {
    return Stream.of(Arguments.of(null, true));
  }

  // This is first method, where all you do is pass what's comes
  // from the test method to the checkResult method
  // Why you keep this one is to split up the first parameter if it's a list for
  // example
  @ParameterizedTest
  @MethodSource
  public void test1equal1(int one, int two) {
    checkResult(one, two);
  }

  // This is second method, where you keep all the values
  // you want to test. i.e. you can have a list of values
  // like name, age, etc.
  private static Stream<Arguments> test1equal1() {
    return Stream.of(Arguments.of(1, 1));
  }

  // third method, where you do the assertions
  // and you actually call the click, lookup, whatever methods
  private void checkResult(String string1, boolean excpected) {
    

  }

  private void checkResult(int one, int two) {
    Assertions.assertEquals(one, two);
  }



  // TODO: Add more tests

}