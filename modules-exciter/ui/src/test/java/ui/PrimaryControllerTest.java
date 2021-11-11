package ui;


import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import javafx.stage.Stage;
import user.BotUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;

/*TestFx App Test*/

public class PrimaryControllerTest extends ApplicationTest {

  private App app = new App();
  private BotUser botUser = new BotUser("John", 21, "john@mail.no", true,1);


  @Override
  public void start(Stage stage) throws Exception {
    app.start(stage);
  }

  @BeforeEach
  public void setUp() {
    app = new App();
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

      clickOn("#refresh");
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (Exception e) {
        System.out.println("here");
        e.printStackTrace();
      }
    }

}