package restserver;

import core.Exciter;
import json.FileHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The ExciterApplication class is the main class of the restserver.
 */
@SpringBootApplication
public class ExciterApplication {

  public static final Exciter excite = new Exciter();
  public static final FileHandler fileHandler = new FileHandler();

  /**
   * The main method of the ExciterApplication class.
   */
  public static void main(String[] args) {
    if (fileHandler.readUsers() != null) {
      excite.addUsers(fileHandler.readUsers());
    }
    SpringApplication.run(ExciterApplication.class, args);
  }

}
