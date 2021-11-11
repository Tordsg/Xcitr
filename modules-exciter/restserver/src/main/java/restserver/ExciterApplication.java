package restserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import core.Exciter;
import json.FileHandler;

@SpringBootApplication
public class ExciterApplication {

  public static final Exciter excite = new Exciter();
  public static final FileHandler fileHandler = new FileHandler();

  public static void main(String[] args) {
    if (fileHandler.readUsers() != null) {
      excite.addUsers(fileHandler.readUsers());
    }
    SpringApplication.run(ExciterApplication.class, args);
  }

}
