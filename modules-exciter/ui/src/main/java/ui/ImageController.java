package ui;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.image.Image;

public class ImageController {

    private Set<Image> images = new HashSet<>();
    private String path = "../json/src/main/resources/images/";

    File dir = new File(path);
    File[] directoryListing = dir.listFiles();

    //TODO: add images to the set
}
