package ui;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.*;
import javafx.fxml.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.*;
import core.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * TestFX App test
 */
public class AppTest extends ApplicationTest {

    private LoginController controller;
    private Parent root;
    List<User> users = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testLogin(){
        clickOn("#emailLogin").write("Ola@mail");
        verifyThat("#emailLogin", hasText("Ola@mail"));
        clickOn("#PasswordField").write("123qwe");
        verifyThat("#PasswordField", hasText("123qwe"));
        clickOn("#login");
        
        
    }
    //TODO: Add more tests

}
