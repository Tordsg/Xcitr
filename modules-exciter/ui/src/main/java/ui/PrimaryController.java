package ui;

import core.Exciter;

import java.io.IOException;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private Button Like1;

    @FXML
    private ImageView Image1;

    @FXML
    private Label Name1;

    @FXML
    private Label Bio1;

    @FXML
    private Button Like2;

    @FXML
    private ImageView Image2;

    @FXML
    private Label Name2;

    @FXML
    private Label Bio2;

    @FXML
    private Button Profile;

    @FXML
    void onLike1(ActionEvent event) {

    }

    @FXML
    void onLike2(ActionEvent event) {

    }


}
