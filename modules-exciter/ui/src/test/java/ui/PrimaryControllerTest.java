package ui;


import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;

/*TestFx App Test*/

public class PrimaryControllerTest extends ApplicationTest {


  private SignUpController signupController = new SignUpController();
  private PrimaryController controller = new PrimaryController();
  private User testUser = new User("rolf", 22, "test@mail.com");
  private LoginController loginController = new LoginController();

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setUp(){
    signupController.addUser(testUser, "test");

  }

  @ParameterizedTest
  @MethodSource
  public void testMatch(boolean match) {
    checkResult(match);

  }
  private static Stream<Arguments> testMatch() {
    return Stream.of(Arguments.of(true));
  }

  private void checkResult(boolean excpected) {

    drag("#rightCard").moveBy(0, -100).drop();
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    drag("#rightCard").moveBy(0, -100).drop();
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    drag("#rightCard").moveBy(0, -100).drop();



    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (Exception e) {
      e.printStackTrace();
    }
    drag("#leftCard").moveBy(0, -100).drop();
    signupController.deleteUser(testUser);

  }

    @ParameterizedTest
    @MethodSource
    public void testRefresh(boolean match) {
      checkRefresh(match);
    }

    public static Stream<Arguments> testRefresh() {
      return Stream.of(Arguments.of(true));
    }

    public void checkRefresh(boolean excpected) {
      
      User leftUser = controller.getOnScreenUsers().get(0);
      

      clickOn("#refresh");
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (Exception e) {
        System.out.println("here");
        e.printStackTrace();
      }

      Assertions.assertNotEquals(leftUser, controller.getOnScreenUsers().get(0)); 
    }
 /*@AfterEach
    public void deleteUser(){
      controller.deleteUser(new User("Ulf", 20, "ulf@mail"));

  
    }*/


}