package ui;

import core.*;

import java.io.File;
import javafx.scene.image.Image;

public class ImageController {

    private String path = "../json/src/main/resources/";
    File dir = new File(path);
    File[] directoryListing = dir.listFiles();

    public ImageController() {

    }

    private boolean imageExists(String imageName) {
        try {
            File file = new File(imageName);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Image getImage(User user) {
        if (imageExists(path + user.getImageHashCode() + ".jpg")) {
            File file = new File(path + user.getImageHashCode() + ".jpg");
            return new Image(file.toURI().toString());
        } else {
            return new Image(this.getClass().getResourceAsStream("Images/defaultPicture.png"));
        }
    }

}
