package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/*TestFx App Test*/

public class AppTest extends ApplicationTest {

  private LoginController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

    @Test 
    public void testController(){
        assertNotNull(this.controller);

    }

    //Click on må gjøres om til lookup

    /*@Test
    public void testLogin(){
        /*clickOn(lookup("#emailLogin").query()).write("Ola@mail");
        verifyThat("#emailLogin", hasText("Ola@mail"));
        clickOn("#PasswordField").write("123qwe");
        verifyThat("#PasswordField", hasText("123qwe"));
        clickOn("#login");*/

        
    


    //TODO: Add more tests

}
