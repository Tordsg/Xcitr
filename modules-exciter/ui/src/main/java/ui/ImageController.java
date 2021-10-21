package ui;

import core.*;
import java.io.File;
import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import org.apache.commons.io.*;



/**
 * Controller for uploading of pictures in the application.
 */

public class ImageController {

  private HashMap<Integer, ImagePattern> userImages = new HashMap<>();
  private ImagePattern defaultImage;
  private String path = "../json/src/main/resources/images/";
  File dir = new File(path);
  File[] directoryListing = dir.listFiles();

  public ImageController() throws NullPointerException {
    this.defaultImage = new ImagePattern(
                new Image(this.getClass().getResourceAsStream("Images/defaultPicture.png")));
    if(directoryListing != null){
      for (File file : directoryListing) {
        try {
          if (getFileExtension(file).equals(".jpg")) {
            userImages.put(Integer.valueOf(file.getName().substring(0, file.getName().indexOf('.'))),
            new ImagePattern(new Image(file.toURI().toString())));
          }
        } catch (Exception e) {
        e.printStackTrace();
      }
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
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return extension;
  }

  public ImagePattern getImage(User user) {
    if (userImages.containsKey(user.getImageHashCode())) {
      return userImages.get(user.getImageHashCode());
    }
    else {
      return defaultImage;
    }
  }
  /**
   * Uploas a image.
   * @param user
   * @param file
   * @return
   */

  public boolean uploadPicture(User user, File file) {
    //Only jpg files are allowed
    //No null checks since it's handled by SecondaryController
    try {
      Image image = new Image(file.toURI().toString());
      FileUtils.copyFile(file, new File(path + user.getImageHashCode() + ".jpg"));
      userImages.put(user.getImageHashCode(), new ImagePattern(image));
      return true;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

}
