package ui;

import core.*;
import json.*;

import java.io.IOException;
import javafx.fxml.FXML;

public class SignUpController {

    @FXML
    public void switchToLogIn() throws IOException {
        App.setRoot("");
    }
}
