package restserver;

import core.Exciter;
import json.FileHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ExciterApplication {

  public static final Exciter excite = new Exciter();
  public static final FileHandler fileHandler = new FileHandler();

  public static void main(String[] args) {
    if (fileHandler.readUsers() != null) {
      System.out.println("here");
      excite.addUsersFromFile(fileHandler.readUsers());
    }
    SpringApplication.run(ExciterApplication.class, args);
  }

}
