package ui;

import core.*;

import java.io.File;
import java.util.HashMap;

import javafx.scene.image.Image;

public class ImageController {

    private HashMap<Integer, Image> userImages = new HashMap<>();
    private String path = "../json/src/main/resources/";
    File dir = new File(path);
    File[] directoryListing = dir.listFiles();

    public ImageController() {
        for (File file : directoryListing) {
            try {
                if(getFileExtension(file).equals(".jpg")) {
                    userImages.put(Integer.valueOf(file.getName().substring(0,file.getName().indexOf('.'))), new Image(file.toURI().toString()));
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(File file) {
        String extension = "";
        try {
            if (file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf("."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return extension;
    }

    public Image getImage(User user) {
        if (userImages.containsKey(user.getImageHashCode())) {
            return userImages.get(user.getImageHashCode());
        } else {
            return new Image(this.getClass().getResourceAsStream("Images/defaultPicture.png"));
        }
    }

}
