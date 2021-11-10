package ui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import user.User;

/**
 * Controller for for selecting avatar images.
 */

public class ImageController {

  private List<ImagePattern> images = new ArrayList<>();


  public ImageController() throws NullPointerException {
   for (int i = 0; i < 24; i++) {
     URL input = getClass().getResource("images/" + i + ".jpg");
     if(input == null) {
       throw new NullPointerException("Image not found");
     }
     File file = new File(input.getFile());
     images.add(new ImagePattern(new Image(file.toURI().toString())));
   }
  }


  public ImagePattern getImage(User user) {
    return images.get(user.getImageId());
  }

}
