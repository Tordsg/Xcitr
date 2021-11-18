package restserver;

import core.Exciter;
import json.UserHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The ExciterApplication class is the main class of the restserver.
 */
@SpringBootApplication
public class ExciterApplication {

  protected static final Exciter excite = new Exciter();
  private static final UserHandler userHandler = new UserHandler();

  /**
   * The main method of the ExciterApplication class.
   */
  public static void main(String[] args) {
    if (userHandler.readUsers() != null) {
      excite.addUsers(userHandler.readUsers());
    }
    SpringApplication.run(ExciterApplication.class, args);
  }

}
