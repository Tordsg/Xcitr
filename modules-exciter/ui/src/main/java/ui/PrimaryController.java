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

    private Exciter excite;


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private Button Like1;

    @FXML
    private Label Name1;

    @FXML
    private Label Bio1;

    @FXML
    private Label Age1;

    @FXML
    private Button Like2;

    @FXML
    private Label Name2;

    @FXML
    private Label Bio2;

    @FXML
    private Label Age2;
    @FXML
    private Button Profile;

    @FXML
    void onLike1(ActionEvent event) {
       /*excite.pressedLikeFirst();
       setUsers();
       */

    }

    @FXML
    void onLike2(ActionEvent event) {
        /*excite.pressedLikeSecond();
        setUsers();
        */


    }

    public void setUsers(){
        /*ArrayList<User> displayUsers = excite.getNextUsers();
        User user1 = displayUsers.get(0);
        User user2 = displayUsers.get(1);
        Name1.setText(user1.getName());
        Age1.setText(user1.getAge());
        Name2.setText(user2.getName());
        Age2.setText(user2.getAge());*/

    }


}
