package json;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import core.*;
import user.User;
import user.BotUser;

public class UserHandlerTest {

  UserHandler userHandler = new UserHandler();
  private User user;
  private Exciter exciter;
  private List<User> users = new ArrayList<>();
  private BotUser botUser = new BotUser("lars", 22, "lars@mail.no", true, 1);

  @BeforeEach
  public void setUp() {
    users.clear();
    exciter = new Exciter();
    userHandler.createFile();
    userHandler.saveUser(exciter.getAllUsers());
    user = new User("Ola Nordmann", 26, "Fiskesprett på søndager", "ola@mail.no");
    users.add(user);
  }

  @Test
  public void readFromFile() {
    userHandler.saveUser(users);
    List<User> userReadFromFile = userHandler.readUsers();
    Assertions.assertEquals("Ola Nordmann", userReadFromFile.get(0).getName());
    Assertions.assertEquals(26, userReadFromFile.get(0).getAge());
    Assertions.assertEquals("ola@mail.no", userReadFromFile.get(0).getEmail());
  }

  @Test
  public void readMatches() {
    user.setId(UUID.randomUUID());
    for (int i = 0; i < 3; i++) {
      exciter.likePerson(user, botUser);
    }
    userHandler.saveUser(users);
    User userReadFromFile = userHandler.readUsers().get(0);
    Assertions.assertEquals(userReadFromFile.getMatches(), user.getMatches());
    Assertions.assertTrue(userReadFromFile.getMatches().contains(botUser.getEmail()));
    Assertions.assertEquals(3, userHandler.getLikedUsers(user.getId()).get(botUser.getEmail()));

  }

  @Test
  public void getExplicitUser() {
    userHandler.saveUser(users);
    Assertions.assertEquals(users.get(0).getName(), userHandler.getUser("ola@mail.no").getName());
    Assertions.assertEquals(users.get(0).getAge(), userHandler.getUser("ola@mail.no").getAge());
    Assertions.assertEquals(users.get(0).getEmail(), userHandler.getUser("ola@mail.no").getEmail());
    Assertions.assertEquals(users.get(0).getUserInformation(), userHandler.getUser("ola@mail.no").getUserInformation());
  }

  @Test
  public void testPasswords() {
    users.add(new BotUser("bot", 24, "bot@mail.no", true, 1));
    users.get(0).setPassword("password");
    userHandler.saveUser(users);
    Assertions.assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", userHandler.getUser("ola@mail.no").getPassword());
    Assertions.assertNull(userHandler.getUser("bot@mail.no").getPassword());
  }

  @Test
  public void testNullfind() {
    Assertions.assertNull(userHandler.getUser("404notfound@mail.no"));
    // First checks while file is empty
    // Second checks when file is not empty but does not contain the user
    userHandler.saveUser(users);
    Assertions.assertNull(userHandler.getUser("404notfound@mail.no"));
  }

  @Test
  public void testFindUserById() {
    User idUser = new User("Ola Nordmann", 26, "olanrtre@mail.no");
    idUser.setId(UUID.randomUUID());
    exciter.addUser(idUser);
    userHandler.saveUser(exciter.getAllUsers());
    Assertions.assertEquals(idUser.getName(), userHandler.getUserById(idUser.getId()).getName());
  }

  @Test
  public void testLikeCounter() {
    User idUser = new User("Ola Nordmann", 26, "olanrtre@mail.no");
    idUser.setId(UUID.randomUUID());
    BotUser botUser = new BotUser("bot", 24, "bot@mail.no", true, 9);
    exciter.addUsers(List.of(idUser, botUser));
    idUser.fireOnLike(botUser.getEmail());
    userHandler.saveUser(exciter.getAllUsers());
    Assertions.assertEquals(1, userHandler.getUserById(idUser.getId()).getLikedUsers().get(botUser.getEmail()));
  }

  @Test
  public void testFindUserByUuid(){
    user.setId(UUID.randomUUID());
    exciter.addUser(user);
    userHandler.saveUser(exciter.getAllUsers());
    Assertions.assertEquals(user.getName(), userHandler.getUserById(user.getId()).getName());
    Assertions.assertNull(userHandler.getUserById(UUID.randomUUID()));
  }

}
