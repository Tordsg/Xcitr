package core;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import user.BotUser;
import user.User;

public class ExciterTest {

  private Exciter exciter;
  private User user = new User("test", 22, "test@mail.no");
  private BotUser botUser = new BotUser("Sofie", 23, "sofie@mail.no", true, 1);

  @BeforeEach
  public void setUp() {
    exciter = new Exciter();
    user = new User("test", 22, "test@mail.no");
    botUser = new BotUser("Sofie", 23, "sofie@mail.no", true, 2);
  }

  @Test
  public void likeFirst() {
    exciter.likePerson(user, botUser);
    Assertions.assertTrue(user.getLikedUsers().containsKey(botUser.getEmail()));
  }

  @Test
  public void testMatch() {
    for (int i = 0; i < 2; i++) {
      exciter.likePerson(user, botUser);
    }
    Assertions.assertTrue(exciter.likePerson(user, botUser));
    Assertions.assertEquals("sofie@mail.no", exciter.getUserMatches(user).get(0));
  }

  @Test
  public void testAddUsers() {
    User testUser = new User("Roger", 25, "RogerNew@mail.no");
    User testUser2 = new User("Bob", 25, "Bob@mail.no");
    User testUser3 = new User("Rolf", 25, "Rolf@mail.no");
    User testUser4 = new User("Bjarne", 25, "Bjarne@mail.no");
    User testUser5 = new User("Unni", 25, "Unni@mail.no");
    User testUser6 = new User("Roger", 25, "RogerNew@mail.no"); // duplicate should not be added

    List<User> testUsers = List.of(testUser, testUser2, testUser3, testUser4, testUser5, testUser6);
    int count = exciter.getAllUsers().size();
    exciter.addUsers(testUsers);
    Assertions.assertEquals(count + 5, exciter.getAllUsers().size());
  }

  @Test
  public void testGetUserByEmail() {
    exciter.addUser(user);
    Assertions.assertEquals(user, exciter.getUserByEmail(user.getEmail()));
  }

  @Test
  public void testClearUser() {
    exciter.addUser(user);
    Assertions.assertTrue(exciter.getAllUsers().contains(user));
    exciter.clearUser(user);
    Assertions.assertFalse(exciter.getAllUsers().contains(user));

  }

  @Test
  public void testPickRandom()
  {
    exciter.addUser(user);
    exciter.addUser(botUser);
    Assertions.assertTrue(exciter.getAllUsers().contains(user));
    Assertions.assertTrue(exciter.getAllUsers().contains(botUser));
    User newUser = exciter.getNextRandomUser(List.of(user,botUser));
    Assertions.assertFalse(newUser.equals(user) || newUser.equals(botUser));
    List<User> users = exciter.getTwoUniqueUsers(user);
    Assertions.assertEquals(2, users.size());
    Assertions.assertFalse(users.contains(user));
  }

  @Test
  public void testResestLike(){
    exciter.addUser(user);
    exciter.addUser(botUser);
    exciter.likePerson(user, botUser);
    Assertions.assertTrue(user.getLikedUsers().containsKey(botUser.getEmail()));
    Assertions.assertEquals(1, user.getLikedUsers().get(botUser.getEmail()));
    exciter.resetLikes(user, botUser);
    Assertions.assertEquals(0, user.getLikedUsers().get(botUser.getEmail()));
  }

}
