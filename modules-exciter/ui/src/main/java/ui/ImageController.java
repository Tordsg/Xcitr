package ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import user.User;

/**
 * Controller for for selecting avatar images.
 */

public class ImageController {

  private List<ImagePattern> images = new ArrayList<>();


  public ImageController() throws NullPointerException {
   for (int i = 0; i < 25; i++) {
     InputStream input = getClass().getResourceAsStream("Images/" + i + ".jpg");
     if(input == null) {
       throw new NullPointerException("Image not found");
     }
     BufferedImage buffImage;
    try {
      buffImage = ImageIO.read(input);
      Image image = SwingFXUtils.toFXImage(buffImage, null);
      images.add(new ImagePattern(image));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  }


  public ImagePattern getImage(User user) {
    return images.get(user.getImageId());
  }

}
