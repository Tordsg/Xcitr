package ui;

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

/**
 * TestFX App test
 */
public class AppTest extends ApplicationTest {

    private PrimaryController controller = new PrimaryController();
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

    public Parent getRootNode() {
        return root;
    }

    private void click(String... labels) {
        for (var label : labels) {
            clickOn(LabeledMatchers.hasText(label));
        }
    }

    @ParameterizedTest
    @MethodSource
    public static void likeRightPerson() {
        users = controller.getCore().getOnScreenUsers();
        click("Like2");
        Assertions.assertFalse(users.equals(controller.getCore().getOnScreenUsers()));
    }

}
