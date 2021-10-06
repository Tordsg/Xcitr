package ui;

import core.*;

import java.io.File;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class ImageController {

    private HashMap<Integer, ImagePattern> userImages = new HashMap<>();
    private ImagePattern defaultImage;
    private String path = "../json/src/main/resources/";
    File dir = new File(path);
    File[] directoryListing = dir.listFiles();

    public ImageController() {
        this.defaultImage =  new ImagePattern(new Image(this.getClass().getResourceAsStream("/images/defaultPicture.png")));
        for (File file : directoryListing) {
            try {
                if(getFileExtension(file).equals(".jpg")) {
                    userImages.put(Integer.valueOf(file.getName().substring(0,file.getName().indexOf('.'))), new ImagePattern(new Image(file.toURI().toString())));
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

    public ImagePattern getImage(User user) {
        if (userImages.containsKey(user.getImageHashCode())) {
            return userImages.get(user.getImageHashCode());
        } else {
            return defaultImage;
        }
    }

}
