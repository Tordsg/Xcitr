package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import core.BotUser;
import core.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MatchController implements Initializable{

    @FXML
    HBox hBox;
    @FXML
    Group button;
    @FXML
    AnchorPane anchorPane;
    private ImageController imageController = PrimaryController.imageController;
    public void switchToPrimary() throws IOException{
        App.setRoot("primary");
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        dragMatches();
        hoverButton(button);
        if(hBox.getChildren().size()==0){
            Label label = new Label();
            label.setText("You have not matches, yet.");
            label.setFont(new Font(30));
            label.setAlignment(Pos.CENTER);
            label.setLayoutX(140);
            label.setLayoutY(210);
            anchorPane.getChildren().add(label);
        }
    }

    private void hoverButton(Node n){
        n.setOnMouseEntered(e -> {
            n.setEffect(new Lighting());
        });
        n.setOnMouseExited(e -> {
            n.setEffect(null);
        });
    }
    private Pane createCard(User user){
        Pane pane = new Pane();
        pane.setPrefHeight(338);
        pane.setPrefWidth(245);
        Rectangle rect = new Rectangle();
        pane.getChildren().add(rect);
        rect.setArcHeight(45);
        rect.setArcWidth(45);
        rect.setHeight(338);
        rect.setWidth(225);
        rect.setStrokeWidth(2);
        rect.setStroke(Color.BLACK);
        rect.setStrokeType(StrokeType.INSIDE);
        rect.setFill(imageController.getImage(user));
        Pane pane1 = new Pane();
        pane.getChildren().add(pane1);
        pane1.setCacheShape(false);
        pane1.setLayoutY(217);
        pane1.setPrefHeight(121);
        pane1.setMinHeight(121);
        pane1.setPrefWidth(225);
        pane1.setStyle("-fx-background-color: rgba(255, 255, 255, .4); -fx-background-radius: 0 0 22 22; -fx-border-radius: 0 0 21 21; -fx-border-width: 0 2 2 2; -fx-border-color: black;");
        Label age = new Label();
        pane1.getChildren().add(age);
        age.setAlignment(Pos.CENTER);
        age.setContentDisplay(ContentDisplay.CENTER);
        age.setLayoutX(170);
        age.setLayoutY(0);
        age.setPrefHeight(45);
        age.setPrefWidth(58);
        age.setText(Integer.toString(user.getAge()));
        age.setFont(new Font(30));
        Label name = new Label();
        pane1.getChildren().add(name);
        name.setLayoutX(5);
        name.setLayoutY(-3);
        name.setPrefHeight(17);
        name.setPrefWidth(119);
        name.setText(user.getName());
        name.setFont(new Font(30));
        name.setOpacity(1);
        Label email = new Label();
        pane1.getChildren().add(email);
        email.setOpacity(1);
        email.setLayoutX(0);
        email.setLayoutY(100);
        email.setPrefHeight(12);
        email.setPrefWidth(225);
        email.setText(user.getEmail());
        email.setFont(new Font(12));
        email.setAlignment(Pos.CENTER);
        Line line = new Line();
        pane1.getChildren().add(line);
        line.setOpacity(1);
        line.setStartX(50);
        line.setEndX(175);
        line.setLayoutX(0);
        line.setLayoutY(97);
        Text text = new Text();
        pane1.getChildren().add(text);
        text.setOpacity(1);
        text.setLayoutX(6);
        text.setLayoutY(50);
        text.setWrappingWidth(215);
        text.setText(user.getUserInformation());
        DropShadow effect = new DropShadow();
        effect.setOffsetX(2);
        effect.setOffsetY(2);
        effect.setColor(new Color(0, 0, 0, 0.5));
        pane.setEffect(effect);
        return pane;
    }


    double x;
    double dX;
    double lastX;
    private void dragMatches(){
        hBox.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                lastX = event.getSceneX();
                
            }
        });
        hBox.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                x = event.getSceneX();
                dX = x - lastX;
                lastX = x;
                double hBoxPosition = dX + hBox.getLayoutX();
                if (hBoxPosition>20&&dX>0){
                    hBox.setLayoutX(hBox.getLayoutX());
                } else if (hBoxPosition + hBox.getWidth() < 640 && dX<0){
                    hBox.setLayoutX(hBox.getLayoutX());
                }
                else {
                    hBox.setLayoutX(hBoxPosition);
                }
            }
        });
    }

}
